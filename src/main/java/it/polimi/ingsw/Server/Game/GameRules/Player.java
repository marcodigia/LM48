package it.polimi.ingsw.Server.Game.GameRules;

import it.polimi.ingsw.Client.View.UI;
import it.polimi.ingsw.Client.View.UI_SIMULATION;
import it.polimi.ingsw.Server.Game.Cards.CardsComponents.Cell;
import it.polimi.ingsw.Server.Game.Cards.PrivateObjectiveCard;
import it.polimi.ingsw.Server.Game.Cards.ToolCard;
import it.polimi.ingsw.Server.Game.Cards.WindowPatternCard;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Basic.TakeDiceBasic;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Basic.UseToolCardBasic;
import it.polimi.ingsw.Server.Game.GameRules.PlayerUtility.PlayerColor;

import java.util.ArrayList;

//moved player in game rules so doAction is package friendly
public class Player {



    private PrivateObjectiveCard privateObjectiveCard;
    private TakeDiceBasic placeDiceOfTheTurn;
    private UseToolCardBasic useToolCardOfTheTurn;
    private PlayerColor color;
    private String name;
    private UI ui;
    private GameContext gameContext;

    public Player(PlayerColor color, String name) {
        this.color = color;
        this.name = name;

        ui = new UI_SIMULATION(0, 0, 0, 0, 0);
    }

    public String getName() {
        return name;
    }

    public PlayerColor getPlayerColor() {
        return color;
    }


    public void setAction_placeDiceOfTheTurn(int from, int to) {
        placeDiceOfTheTurn = new TakeDiceBasic(gameContext.getWindowPatternCard(), gameContext.getDraftPool());
        placeDiceOfTheTurn.takeDice(from, to);
        placeDiceOfTheTurn.doAction(gameContext);
    }

    public void setAction_UseToolCardOfTheTurn(ToolCard toolCard) {
        useToolCardOfTheTurn = new UseToolCardBasic(toolCard, ui, gameContext);
        useToolCardOfTheTurn.doAction(gameContext);
    }


    public ArrayList<Cell> getRow(int n) {
        return gameContext.getWindowPatternCard().getRow(n);
    }

    public WindowPatternCard getWindowPatternCard() {
        return gameContext.getWindowPatternCard();
    }

    public void setGameContext(GameContext gameContext) {
        this.gameContext = gameContext;
    }


}
