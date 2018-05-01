package it.polimi.ingsw.Server.Game.GameRules;

import it.polimi.ingsw.Client.View.UI;
import it.polimi.ingsw.Client.View.UI_SIMULATION;
import it.polimi.ingsw.Server.Game.Cards.CardsComponents.Cell;
import it.polimi.ingsw.Server.Game.Cards.PrivateObjectiveCard;
import it.polimi.ingsw.Server.Game.Cards.ToolCard;
import it.polimi.ingsw.Server.Game.Cards.WindowPatternCard;
import it.polimi.ingsw.Server.Game.Components.Boards.DraftPool;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Basic.TakeDiceBasic;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Basic.UseToolCardBasic;
import it.polimi.ingsw.Server.Game.GameRules.PlayerUtility.PlayerColor;

import java.util.ArrayList;

//moved player in game rules so doAction is package friendly
public class Player {


    private WindowPatternCard windowPatternCard;
    private PrivateObjectiveCard privateObjectiveCard;
    private TakeDiceBasic placeDiceOfTheTurn;
    private UseToolCardBasic useToolCardOfTheTurn;
    private PlayerColor color;
    private String name;
    private DraftPool draftPool;
    private UI ui;

    public Player(PlayerColor color, String name) {
        this.color = color;
        this.name = name;

        ui = new UI_SIMULATION();
    }

    public String getName() {
        return name;
    }

    public PlayerColor getPlayerColor() {
        return color;
    }


    public void setAction_placeDiceOfTheTurn(int from, int to) {
        placeDiceOfTheTurn = new TakeDiceBasic(windowPatternCard, draftPool);
        placeDiceOfTheTurn.takeDice(from, to);
        placeDiceOfTheTurn.doAction();
    }

    public void setAction_UseToolCardOfTheTurn(ToolCard toolCard) {
        useToolCardOfTheTurn = new UseToolCardBasic(toolCard, ui, draftPool, windowPatternCard);
        useToolCardOfTheTurn.doAction();
    }


    public ArrayList<Cell> getRow(int n) {
        return windowPatternCard.getRow(n);
    }

    public WindowPatternCard getWindowPatternCard() {
        return windowPatternCard;
    }

    public void setWindowPatternCard(WindowPatternCard windowPatternCard) {
        this.windowPatternCard = windowPatternCard;
    }

    public DraftPool getDraftPool() {
        return draftPool;
    }

    public void setDraftPool(DraftPool draftPool) {
        this.draftPool = draftPool;
    }
}
