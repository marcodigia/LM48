package it.polimi.ingsw.Server.Game.GameRules;

import it.polimi.ingsw.Server.Game.Cards.WindowPatternCard;
import it.polimi.ingsw.Server.Game.Components.Dice;

public class ActionMoveDice extends Action {

    private static final String actionName = "Move Dice";
    private boolean color_restriction;
    private boolean value_restriciton;
    private int from;
    private int to;
    private WindowPatternCard windowPatternCard;

    public ActionMoveDice(boolean color_restriction, boolean value_restriciton) {
        this.color_restriction = color_restriction;
        this.value_restriciton = value_restriciton;
    }


    public boolean MoveDice(int from, int to, WindowPatternCard windowPatternCard) {

        this.from = from;
        this.to = to;
        this.windowPatternCard = windowPatternCard;
        Dice dice = windowPatternCard.getDice(from); //TODO not working
        return windowPatternCard.checkRestriction(dice, to, color_restriction, value_restriciton);
    }


    public String getActionName() {
        return actionName;
    }

    public void doAction() {
        windowPatternCard.moveDice(from, to, color_restriction, value_restriciton);
    }
}
