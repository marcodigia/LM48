package it.polimi.ingsw.Server.Game.Utility;

import it.polimi.ingsw.Packetable;
import it.polimi.ingsw.Server.Game.Cards.*;
import it.polimi.ingsw.Server.Game.Components.Dice;
import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import it.polimi.ingsw.Server.Game.GameRules.Restriction;

import java.io.FileNotFoundException;
import java.util.*;

public class Unpacker {


    private static Hashtable<String,Drawable>   WPDeck;
    private static Hashtable<String,Drawable>   TCDeck;
    private static Hashtable<String,Drawable>   PODeck;
    private static Hashtable<String,Drawable>   PRDeck;

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


    //This class returns the game status
    public static GameStatus getGameStatus(String packet){
        GameStatus gameStatus = null;
        ArrayList<String> objectPacket = new ArrayList<>(Arrays.asList(packet.split(CONSTANT.ObjectDelimeter)));

        return gameStatus;
    }

    public static WindowPatternCard WP_FromId(String id) {

        return (WindowPatternCard) WPDeck.get(id);
    }

    public static ToolCard TC_FromId(String id){
        return (ToolCard) TCDeck.get(id);
    }

    public static PublicObjectiveCard PB_FromId(String id){
        return (PublicObjectiveCard) PODeck.get(id);
    }

    public static PrivateObjectiveCard PR_FromId(String id){
        return (PrivateObjectiveCard) PRDeck.get(id);
    }

    public static WindowPatternCard WP_FromPacket(ArrayList<String> wpPacket){
        ArrayList<String> packet = new ArrayList<>(wpPacket);
        WindowPatternCard wp = WP_FromId(packet.remove(0));
        System.out.println(wpPacket.get(0));

        return null;
    }



    public static HashMap<Restriction,Dice> parseMatrix(String matrix){

        HashMap<Restriction,Dice> result = new HashMap<>();
        List<String> cells = Arrays.asList((matrix.split("[\\(\\)]")));

        for (String k : cells){
            String[] el = k.split("\\,");
            if(el[0].equals(""))
                continue;
            Restriction restriction = Restriction.valueOf(el[0]);
            Dice dice=null;

            if (!el[1].equals("null ")){
                dice = new Dice(DiceColor.valueOf(String.valueOf(el[1].charAt(0))),String.valueOf(el[1].charAt(1)));
            }


            result.put(restriction,dice);

        }
        return result;
    }

}
