package it.polimi.ingsw.Server.Game.GameRules;

import it.polimi.ingsw.Packetable;
import it.polimi.ingsw.Server.Game.Cards.*;
import it.polimi.ingsw.Server.Game.Components.Boards.BoardRound;
import it.polimi.ingsw.Server.Game.Components.Boards.DraftPool;
import it.polimi.ingsw.Server.Game.Components.Dice;
import it.polimi.ingsw.Server.Game.Utility.CONSTANT;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class GameStatus implements Packetable {

    private HashMap<Player,WindowPatternCard> playerWP;

    private HashMap<Player, List<Drawable> > playerCards = new HashMap<>();

    private DraftPool draftPool = null;
    private ArrayList<ToolCard> toolCards = new ArrayList<>();
    private ArrayList<PublicObjectiveCard> publicObjectiveCards = null;
    private BoardRound boardRound = null;

    public GameStatus() {
        playerWP = new HashMap<>();
    }

    public GameStatus(HashMap<Player, List<Drawable>> playerCards , BoardRound boardRound) {
        this.playerCards = playerCards;
        this.boardRound = boardRound;
    }

    public GameStatus (ArrayList<ToolCard> toolCards, ArrayList<PublicObjectiveCard> publicObjectiveCards ){
        this.toolCards = toolCards;
        this.publicObjectiveCards = publicObjectiveCards;
    }

    public void addWindowPatternCard(Player p, WindowPatternCard wp){

        ArrayList<Drawable> list = new ArrayList<>();
        list.add(0,wp);
        playerCards.put(p, list);
    }

    public void addTC(ToolCard toolCard){

        toolCards.add(toolCard);
    }


    //Assume that size of privateObjectiveCards is the same of players
    public void addPrivateObjectiveCard(ArrayList<PrivateObjectiveCard> privateObjectiveCards){


        int i =0;
        for (Player p : playerCards.keySet()){
            playerCards.get(p).add(1, privateObjectiveCards.get(i));
            i++;
        }

    }

    public void addBoardRound(BoardRound boardRound){
        this.boardRound = boardRound;
    }

    public ArrayList<Player> getPlayer(){
        return new ArrayList<>(playerCards.keySet());
    }

    public void setDraftPool(DraftPool draftPool) {
        this.draftPool = draftPool;
    }

    @Override
    public String toPacket() {

        StringBuilder packet = new StringBuilder();
        //Setting the number of player , used from the unpacker to know how many element to istantiate
        packet.append(playerCards.size()).append(CONSTANT.ObjectDelimeter);
        for (Player p : playerCards.keySet()){
            packet.append(p.getName()).append(CONSTANT.ObjectDelimeter)
                    .append( ((WindowPatternCard) playerCards.get(p).get(0) ).toPacket()).append(CONSTANT.ObjectDelimeter);

            //If the toolcard is already been chosen append also that to the packet
            if (playerCards.get(p).get(1)!=null)
                    packet.append(playerCards.get(p).get(1).getID()).append(CONSTANT.ObjectDelimeter);
            else
                packet.append("null").append(CONSTANT.ObjectDelimeter);
        }

        // Setting the Draftpool , used from the unpacker to know how many dice to istantiate

        packet.append(draftPool.getDraft().size()).append(CONSTANT.ObjectDelimeter);
        Iterator<Dice> iterator = draftPool.getDraft().iterator();
        while (iterator.hasNext()){
            packet.append(iterator.next());
            if (iterator.hasNext())
                packet.append(CONSTANT.ElenemtsDelimenter);
        }


        //Setting the BoardRound

        packet.append(boardRound.toPacket());


        for (ToolCard t: toolCards )
        {
            packet.append(CONSTANT.ObjectDelimeter).append(t.getID());

        }

        return packet.toString();
    }
}
