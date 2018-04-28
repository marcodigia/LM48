package it.polimi.ingsw.Server.Game.GameRules;

import it.polimi.ingsw.Server.Game.Cards.WindowPatternCard;
import it.polimi.ingsw.Server.Game.Components.Boards.DraftPool;
import it.polimi.ingsw.Server.Game.Components.Dice;
import it.polimi.ingsw.Server.Game.Utility.ANSI_COLOR;


public class ActionPlaceDice extends Action {

    private boolean ignoreColorRestricion;
    private boolean ignoreValueRestriction;
    private boolean ignoreAdjacencyRestriction;
    private static final String actionName = "Place Dice";
    private boolean actionActive = false;
    private int coordinate;
    private WindowPatternCard windowPatternCard;
    private int diceIndex;

    private Dice dice;
    private DraftPool draftPool;

    public ActionPlaceDice(boolean ignoreColorRestricion, boolean ignoreValueRestriction, boolean ignoreAdjacencyRestriction, DraftPool draftPool) {
        this.ignoreColorRestricion = ignoreColorRestricion;
        this.ignoreValueRestriction = ignoreValueRestriction;
        this.ignoreAdjacencyRestriction = ignoreAdjacencyRestriction;
        this.draftPool = draftPool;
    }

    public boolean placeDice(int coordinate, WindowPatternCard windowPatternCard, int diceIndex) {


        this.coordinate = coordinate;
        this.windowPatternCard = windowPatternCard;
        this.diceIndex = diceIndex;

        dice = draftPool.getDice(diceIndex);
        if (windowPatternCard.isPlaceable(dice, coordinate, ignoreColorRestricion, ignoreValueRestriction, ignoreAdjacencyRestriction))
            actionActive = true;
        return actionActive;
    }


    public String getActionName() {
        return actionName;
    }

    public void doAction() {
        if (!actionActive) {
            return;
        }

        boolean res = windowPatternCard.placeDice(dice, coordinate, ignoreColorRestricion, ignoreValueRestriction, ignoreAdjacencyRestriction);
        if (res)
            draftPool.removeDice(dice);
    }
}

