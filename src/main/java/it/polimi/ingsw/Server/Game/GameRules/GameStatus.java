package it.polimi.ingsw.Server.Game.GameRules;

import it.polimi.ingsw.Packetable;
import it.polimi.ingsw.Server.Game.Cards.PrivateObjectiveCard;
import it.polimi.ingsw.Server.Game.Cards.PublicObjectiveCard;
import it.polimi.ingsw.Server.Game.Cards.ToolCard;
import it.polimi.ingsw.Server.Game.Cards.WindowPatternCard;
import it.polimi.ingsw.Server.Game.Components.Boards.DraftPool;
import it.polimi.ingsw.Server.Game.Utility.CONSTANT;

import java.util.ArrayList;
import java.util.HashMap;

public class GameStatus implements Packetable {

    HashMap<Player,WindowPatternCard> playerWP;
    DraftPool draftPool = null;
    ArrayList<ToolCard> toolCards = null;
    ArrayList<PublicObjectiveCard> publicObjectiveCards = null;
    PrivateObjectiveCard privateObjectiveCard = null;

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
        return null;
    }
}
