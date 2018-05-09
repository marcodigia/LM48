package it.polimi.ingsw.Server.Game.GameRules;

import it.polimi.ingsw.Client.View.UI;
import it.polimi.ingsw.Client.View.UI_SIMULATION;
import it.polimi.ingsw.Server.Game.Cards.CardsComponents.Cell;
import it.polimi.ingsw.Server.Game.Cards.PrivateObjectiveCard;
import it.polimi.ingsw.Server.Game.Cards.ToolCard;
import it.polimi.ingsw.Server.Game.Cards.WindowPatternCard;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Actions;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Basic.PlaceDiceAction;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Basic.UseToolCardBasic;
import it.polimi.ingsw.Server.Game.GameRules.PlayerUtility.PlayerColor;

import java.util.ArrayList;

//moved player in game rules so doAction is package friendly
public class Player {



    private PrivateObjectiveCard privateObjectiveCard;
    private PlaceDiceAction placeDiceOfTheTurn;
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

    //Game Controller shuould invoke this method at the beginin of every turn
    public void setAction_placeDiceOfTheTurn(int i, int j) {
        placeDiceOfTheTurn = new PlaceDiceAction();
    }

    public Actions getAction_placeDiceOfTheTurn() {
        return placeDiceOfTheTurn;
    }
    public void setAction_UseToolCardOfTheTurn(ToolCard toolCard) {

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
