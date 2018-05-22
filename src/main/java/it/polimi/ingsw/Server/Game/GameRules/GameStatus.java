package it.polimi.ingsw.Server.Game.GameRules;

import it.polimi.ingsw.Packetable;
import it.polimi.ingsw.Server.Game.Cards.PrivateObjectiveCard;
import it.polimi.ingsw.Server.Game.Cards.PublicObjectiveCard;
import it.polimi.ingsw.Server.Game.Cards.ToolCard;
import it.polimi.ingsw.Server.Game.Cards.WindowPatternCard;
import it.polimi.ingsw.Server.Game.Components.Boards.DraftPool;
import it.polimi.ingsw.Server.Game.Components.Dice;
import it.polimi.ingsw.Server.Game.Utility.CONSTANT;

import java.util.ArrayList;
import java.util.HashMap;

public class GameStatus implements Packetable {

    HashMap<Player,WindowPatternCard> playerWP;
    DraftPool draftPool = null;
    ArrayList<ToolCard> toolCards = null;
    ArrayList<PublicObjectiveCard> publicObjectiveCards = null;
    PrivateObjectiveCard privateObjectiveCard = null;

    public GameStatus(){}
    public GameStatus(HashMap<Player, WindowPatternCard> playerWP) {
        this.playerWP = playerWP;
    }

    public GameStatus(HashMap<Player, WindowPatternCard> playerWP, DraftPool draftPool) {
        this.playerWP = playerWP;
        this.draftPool = draftPool;
    }

    public GameStatus(HashMap<Player, WindowPatternCard> playerWP, DraftPool draftPool, ArrayList<ToolCard> toolCards) {
        this.playerWP = playerWP;
        this.draftPool = draftPool;
        this.toolCards = toolCards;
    }

    public GameStatus(HashMap<Player, WindowPatternCard> playerWP, DraftPool draftPool, ArrayList<ToolCard> toolCards, ArrayList<PublicObjectiveCard> publicObjectiveCards, PrivateObjectiveCard privateObjectiveCard) {
        this.playerWP = playerWP;
        this.draftPool = draftPool;
        this.toolCards = toolCards;
        this.publicObjectiveCards = publicObjectiveCards;
        this.privateObjectiveCard = privateObjectiveCard;
    }

    public GameStatus (ArrayList<ToolCard> toolCards, ArrayList<PublicObjectiveCard> publicObjectiveCards){
        this.toolCards = toolCards;
        this.publicObjectiveCards = publicObjectiveCards;
    }

    public void addWindowPatternCard(HashMap<Player,WindowPatternCard> playerWP){
        this.playerWP = playerWP;
    }

    public void addPrivateObjectiveCard(ArrayList<PrivateObjectiveCard> privateObjectiveCards){
        this.privateObjectiveCard = privateObjectiveCard;
    }

    @Override
    public String toPacket() {

        StringBuilder packet = new StringBuilder();
        //Setting the number of player , used from the unpacker to know how many element to istantiate
        packet.append(playerWP.size()).append(CONSTANT.ObjectDelimeter);
        for (Player p : playerWP.keySet()){
            packet.append(p.getName()).append(CONSTANT.ObjectDelimeter)
                    .append(playerWP.get(p)).append(CONSTANT.ObjectDelimeter);
        }

        // Setting the Draftpool , used from the unpacker to know how many dice to istantiate

        packet.append(draftPool.getDraft().size()).append(CONSTANT.ObjectDelimeter);
        for (Dice d : draftPool.getDraft()){
            packet.append(d).append(CONSTANT.ObjectDelimeter);
        }


        return packet.toString();
    }

    public HashMap<Player, WindowPatternCard> getPlayerWP() {
        return playerWP;
    }

    public void setPlayerWP(HashMap<Player, WindowPatternCard> playerWP) {
        this.playerWP = playerWP;
    }

    public DraftPool getDraftPool() {
        return draftPool;
    }

    public void setDraftPool(DraftPool draftPool) {
        this.draftPool = draftPool;
    }

    public ArrayList<ToolCard> getToolCards() {
        return toolCards;
    }

    public void setToolCards(ArrayList<ToolCard> toolCards) {
        this.toolCards = toolCards;
    }

    public ArrayList<PublicObjectiveCard> getPublicObjectiveCards() {
        return publicObjectiveCards;
    }

    public void setPublicObjectiveCards(ArrayList<PublicObjectiveCard> publicObjectiveCards) {
        this.publicObjectiveCards = publicObjectiveCards;
    }
}
