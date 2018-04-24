package it.polimi.ingsw.Server.Game.PlayerUtility;

import it.polimi.ingsw.Server.Game.Cards.CardsComponents.Cell;
import it.polimi.ingsw.Server.Game.Cards.PrivateObjectiveCard;
import it.polimi.ingsw.Server.Game.Cards.WindowPatternCard;
import it.polimi.ingsw.Server.Game.GameRules.Action;

import java.util.ArrayList;

public class Player {


    private WindowPatternCard windowPatternCard;
    private PrivateObjectiveCard privateObjectiveCard;
    private ArrayList<Action> actions;
    private PlayerColor color;
    private String name;


    public Player(PlayerColor color, String name) {
        this.color = color;
        this.name = name;
        actions = new ArrayList<Action>();
    }

    public void addAction(Action action) {

        actions.add(action);
    }

    public String getName() {
        return name;
    }

    public PlayerColor getPlayerColor() {
        return color;
    }

    public boolean actionAvaiable(Action action) {

        return actions.contains(action);
    }

    //TODO da rimuovere??
    public void executeActions() {
        for (Action action : actions) {
            action.doAction();

        }
        actions.clear();

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

}
