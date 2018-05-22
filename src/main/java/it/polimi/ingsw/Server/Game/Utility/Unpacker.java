package it.polimi.ingsw.Server.Game.Utility;

import it.polimi.ingsw.Server.Game.Cards.*;
import it.polimi.ingsw.Server.Game.Components.Boards.BoardRound;
import it.polimi.ingsw.Server.Game.Components.Boards.DraftPool;
import it.polimi.ingsw.Server.Game.Components.Dice;
import it.polimi.ingsw.Server.Game.GameRules.GameContext;
import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import it.polimi.ingsw.Server.Game.GameRules.Player;

import java.io.FileNotFoundException;
import java.util.*;

public class Unpacker {


    private static Hashtable<String,Drawable>   WPDeck;
    public static Hashtable<String,Drawable>   TCDeck;
    private static Hashtable<String,Drawable>   PODeck;
    public static Hashtable<String,Drawable>   PRDeck;
    private static GameStatus gs;

    public static void setUpUnpacker(){
        AbstractCardFactory WPfactory = new WindowPatternCardFactory(CONSTANT.windowPatternfile);
        AbstractCardFactory TCFactory = new ToolCardFactory(CONSTANT.toolcardfile);
        AbstractCardFactory PBOFactory = new PublicObjectiveCardFactory(CONSTANT.publicObjectivefile);
        AbstractCardFactory PROFactory = new PrivateObjectiveCardFactory(CONSTANT.privateObjectivefile);

        try {
            WPDeck= WPfactory.getNewCardDeck();
            TCDeck =TCFactory.getNewCardDeck();
            PODeck = PBOFactory.getNewCardDeck();
            PRDeck = PROFactory.getNewCardDeck();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


    /*
    * Geneal Syntax of the packets
    *
    * <I>       d    <username>     d   <ID_WP>      d      <WP>        d       <ID_pr>     ...     d   <DP>            d       <I>              d         <ALD>            ....    d   <TC> d  <TC>  d <TC>
    *
    *  num of         player            id of               wp                   id of                 the dp                   number of                   ArrayList                      3 id of toolcards
    *  player         username           wp                 to packet            private               to packet                round already               of Dice
    *  following                                                                 card                                           played                      to packet
    * */

    //This class returns the game status.
    //NB this should be used only on to rebuild the gamestatus on the client because the player in this gamestatu are not totally setup
    public static GameStatus getGameStatus(String packet){
        GameStatus gameStatus ;
        String[] objectPacket = packet.split(CONSTANT.ObjectDelimeter);


        int num_of_player = Integer.parseInt(objectPacket[0]);

        int draftSize = Integer.parseInt(objectPacket[5+ (num_of_player-1)*4]);

        DraftPool draftPool = DP_fromPacket(objectPacket[6+ (num_of_player-1)*4]);





        HashMap<Player,List<Drawable>> playerInfo = new HashMap<>();
        for (int i=0 ; i < num_of_player ; i++){
            Player p = new Player(objectPacket[1+ i*4],null);
            WindowPatternCard  wp = WP_fromPacket(objectPacket[2+ i*4],objectPacket[3+ i*4]);
            PrivateObjectiveCard privateObjectiveCard = PR_FromId(objectPacket[4+ i*4]);

            GameContext gc = new GameContext(draftPool,null,null,wp,privateObjectiveCard);
            p.setGameContext(gc);

            ArrayList<Drawable> list = new ArrayList<>();
            list.add(0,wp);
            list.add(1,privateObjectiveCard);
            playerInfo.put(p,list);


        }

        BoardRound boardRound = new BoardRound(new ArrayList<>(playerInfo.keySet()));

        int boardRoundCursor =7+ (num_of_player-1)*4;

        int num_of_round = Integer.parseInt(objectPacket[boardRoundCursor]);

        for (int i = 0 ; i < num_of_round;i++){

           ArrayList<Dice> arrayDices= arrayList_FromPacket(objectPacket[boardRoundCursor+i+1]);

           boardRound.addDices(arrayDices);
        }

        gameStatus = new GameStatus(playerInfo,boardRound);

        gameStatus.setDraftPool(draftPool);


        int TC_cursor = boardRoundCursor+num_of_round;




        gameStatus.addTC( TC_FromId(objectPacket[TC_cursor+1]));
        gameStatus.addTC( TC_FromId(objectPacket[TC_cursor+2]));
        gameStatus.addTC( TC_FromId(objectPacket[TC_cursor+3]));


        return gameStatus;
    }






    public static WindowPatternCard WP_FromId(String id) {

        return (WindowPatternCard) WPDeck.get(id);
    }

    private static ToolCard TC_FromId(String id){
        return (ToolCard) TCDeck.get(id);
    }

    public static PublicObjectiveCard PB_FromId(String id){
        return (PublicObjectiveCard) PODeck.get(id);
    }

    private static PrivateObjectiveCard PR_FromId(String id){
        return (PrivateObjectiveCard) PRDeck.get(id);
    }






   private static WindowPatternCard WP_fromPacket(String id , String packet){

        WindowPatternCard wp = (WindowPatternCard) WPDeck.get(id);

        String[] elements =packet.split("\\"+CONSTANT.ElenemtsDelimenter);


        for (int i =0 ; i < elements.length;i++){
            if (!elements[i].equals("null")){

                wp.placeDice( new Dice(elements[i]),i,true,true,true);
            }
        }

        return wp;
   }

   private static DraftPool DP_fromPacket(String packet){
        DraftPool draftPool = new DraftPool(null);

       String[] elements =packet.split("\\"+CONSTANT.ElenemtsDelimenter);

       for (int i =0 ; i < elements.length;i++){
           draftPool.putDice( new Dice(elements[i]));
       }


        return draftPool;
   }

   private static ArrayList<Dice> arrayList_FromPacket(String packet){

        ArrayList<Dice> ar = new ArrayList<>();

       String[] elements =packet.split("\\"+CONSTANT.ElenemtsDelimenter);

       for (int i =0 ; i < elements.length;i++){
           ar.add( new Dice(elements[i]));
       }

       return ar;
   }

}
