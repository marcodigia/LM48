package it.polimi.ingsw.Server.Game.Utility;

import it.polimi.ingsw.Server.Game.Cards.*;
import it.polimi.ingsw.Server.Game.Components.Boards.BoardRound;
import it.polimi.ingsw.Server.Game.Components.Boards.DraftPool;
import it.polimi.ingsw.Server.Game.Components.Dice;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Actions;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Basic.UseToolCardBasic;
import it.polimi.ingsw.Server.Game.GameRules.GameContext;
import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import it.polimi.ingsw.Server.Game.GameRules.Player;

import java.io.FileNotFoundException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Unpacker {


     public static Hashtable<String,Drawable> WPDeck;
    public static Hashtable<String,Drawable> TCDeck;
    private static Hashtable<String,Drawable> PODeck;
    public static Hashtable<String,Drawable> PRDeck;
    private static GameStatus gs;

    /**
     * setUpUnpacker must be called before the first usage of the static class.
     * Its aim is to load all the decks of the card, in order to rebuild the cards from the net-packet
     */
    public static void setUpUnpacker(){
        AbstractCardFactory WPfactory = new WindowPatternCardFactory(CONSTANT.windowPatternfile);
        AbstractCardFactory TCFactory = new ToolCardFactory(CONSTANT.toolcardfile);
        AbstractCardFactory PBOFactory = new PublicObjectiveCardFactory(CONSTANT.publicObjectivefile);
        AbstractCardFactory PROFactory = new PrivateObjectiveCardFactory(CONSTANT.privateObjectivefile);
        try {
            CardManager.setWPCards(CONSTANT.windowPatternfile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            WPDeck= WPfactory.getNewCardDeck();
            TCDeck =TCFactory.getNewCardDeck();
            PODeck = PBOFactory.getNewCardDeck();
            PRDeck = PROFactory.getNewCardDeck();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


    public static void setGameStatus(GameStatus gameStatus){
        gs = gameStatus;
    }

    /*
    * Geneal Syntax of the packets
    *
    * <I>       d   <username.b.b>  d   <ID_WP>      d      <WP>        d       <ID_pr>     ...     d   <DP>            d       <I>              d         <ALD>            ....    d   <TC> d  <TC>  d <TC>
    *
    *  num of         player            id of               wp                   id of                 the dp                   number of                   ArrayList                      3 id of toolcards
    *  player         username           wp                 to packet            private               to packet                round already               of Dice
    *  following      and action state   or wp in case                           card                                           played                      to packet
    *                                    of custom card
    * */

    /**
     * @param packet is the net-packet cointaining all the necessary information required to rebuild the GameStatus. it has the following syntax
     *
     * General Syntax of the packets
     *
     * <I>       d   <username.b.b>  d   <ID_WP>      d      <WP>        d       <ID_pr>     ...     d   <DP>            d       <I>              d         <ALD>            ....    d   <TC> d  <TC>  d <TC>
     *
     *  num of         player            id of               wp                   id of                 the dp                   number of                   ArrayList                      3 id of toolcards
     *  player         username           wp                 to packet            private               to packet                round already               of Dice
     *  following      and action state                                           card                                           played                      to packet

     *                  where b,b are
     *                  boolean
     *
     *
     *@return the method returns a GameStatus object rebuilt from the net-packet
     */
    //This class returns the game status.
    //NB this should be used only on to rebuild the gamestatus on the client because the player in this gamestatu are not totally setup
    public static GameStatus getGameStatus(String packet){
        GameStatus gameStatus ;
        String[] objectPacket = packet.split(CONSTANT.ObjectDelimeter);


        int num_of_player = Integer.parseInt(objectPacket[0]);

        int draftSize = Integer.parseInt(objectPacket[5+ (num_of_player-1)*4]);

        DraftPool draftPool = DP_fromPacket(objectPacket[6+ (num_of_player-1)*4]);





        LinkedHashMap<Player,List<Drawable>> playerInfo = new LinkedHashMap<>();
        for (int i=0 ; i < num_of_player ; i++){


           ArrayList<String> pinfo = player_InfoFromPacket(objectPacket[1+ i*4]);
           //for (int j = 0 ; j < objectPacket.length;j++)
               //System.out.println(objectPacket[j]);
            Player p = new Player(pinfo.get(0),null);
            p.setBasicActionState(Boolean.parseBoolean(pinfo.get(1)),Boolean.parseBoolean(pinfo.get(2)));
            p.getWallet().setUpWallet(Integer.parseInt(pinfo.get(3)));
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



        gameStatus.addP( PB_FromId(objectPacket[TC_cursor+4]));
        gameStatus.addP( PB_FromId(objectPacket[TC_cursor+5]));
        gameStatus.addP( PB_FromId(objectPacket[TC_cursor+6]));


        return gameStatus;
    }





    /**
     * @param id is the id of the ToolCard to rebuilt
     * @return returns a ToolCard whith the given id
     */
    private static ToolCard TC_FromId(String id){

        String[] element = id.split("\\"+CONSTANT.ElenemtsDelimenter);
        ToolCard t =(ToolCard) TCDeck.get(element[0]);
        t.setCost(Integer.parseInt(element[1]));
        return t;
    }

    /**
     * @param id is the id of the PublicObjectiveCard to rebuilt
     * @return returns a ToolCard whith the given id
     */
    private static PublicObjectiveCard PB_FromId(String id){
        return (PublicObjectiveCard) PODeck.get(id);
    }


    /**
     * @param id is the id of the PrivateObjectiveCard to rebuilt
     * @return returns a ToolCard whith the given id
     */
    private static PrivateObjectiveCard PR_FromId(String id){
        return (PrivateObjectiveCard) PRDeck.get(id);
    }


    /**
     * @param packet follow this syntax rules <username.b.b> where b.b are two boolean indicating the active state of the player actions
     * @return an Arraylist with the info of about the player
     */
    private static ArrayList<String> player_InfoFromPacket(String packet){


        String[] elements =packet.split("\\"+CONSTANT.ElenemtsDelimenter);

        return new ArrayList<>(Arrays.asList(elements));

    }


    /**
     * @param id id is the id of the WindowPatternCard to rebuilt
     * @param packet rappresents the dice an their position on the WindowPatternCard it follows this syntax null.null.Y3 ... where null indicates that the dice is not present and Y3 is a yellow dice with value 3
     * @return the WindowPatternCard with all the dices placed
     */
   public static WindowPatternCard WP_fromPacket(String id , String packet){

       WindowPatternCard wp ;


       if (id.startsWith("Dynamic_")){
           id = id.replace("Dynamic_","");
           String[] elements =id.split("\\"+CONSTANT.ElenemtsDelimenter);

           wp = new WindowPatternCard(new ArrayList<>(Arrays.asList(elements)));

       }else {



           wp = (WindowPatternCard) WPDeck.get(id);

           //This is to clean the windowpatternCard because WPDeck has a riferimento to the same WP
           for (int i  = 0 ; i < 20 ; i++)
               wp.removeDice(i);


       }

       String[] elements =packet.split("\\"+CONSTANT.ElenemtsDelimenter);


       for (int i =0 ; i < elements.length;i++){
           if (!elements[i].equals("null")){

               wp.placeDice( new Dice(elements[i]),i,true,true,true);
           }
       }

       return wp;
   }


    /**
     * @param packet rappresent the action and ther attributes it follows this syntax ClassName <_> params.params ..  where ClassName is the Name of the Action and params are all the necessary parameters
     * @return returns an Actions initialized with all the given params
     */
   public static Actions ACT_fromPacket(String packet,String delimeter){


       System.out.println("Receive Action from Packet " );
       Actions action = null;

       String[] elements =packet.split(delimeter);

       Player player = gs.getPlayerByName(elements[1]);
       if (elements[0].equals("BP")){
           action = player.getPlaceDiceOfTheTurn();
       }else {
            action = player.getUseToolCardOfTheTurn();
       }
       System.out.println("Receive Action from Packet " + elements[0] );
       //Create an istance of the class from the className


       action.setUpAction(elements[3]);
       action.setUserName(elements[1]);

       action.setACTIVE(Boolean.parseBoolean(elements[2]));

       if (elements.length>4){
           (   (UseToolCardBasic) action ).setToolcardID(elements[4]);
       }

       return action;


   }

    /**
     * @param packet rappresent the DraftPool
     * @return a DraftPool with all the dices
     */
   private static DraftPool DP_fromPacket(String packet){
        DraftPool draftPool = new DraftPool(null);

       String[] elements =packet.split("\\"+CONSTANT.ElenemtsDelimenter);

       for (int i =0 ; i < elements.length;i++){
           draftPool.putDice( new Dice(elements[i]));
       }


        return draftPool;
   }

    /**
     * @param packet rappresent an ArrayList of dices
     * @return returns an ArrayList of dices
     */
   private static ArrayList<Dice> arrayList_FromPacket(String packet){

        ArrayList<Dice> ar = new ArrayList<>();

       String[] elements =packet.split("\\"+CONSTANT.ElenemtsDelimenter);

       for (int i =0 ; i < elements.length;i++){
           ar.add( new Dice(elements[i]));
       }

       return ar;
   }

}
