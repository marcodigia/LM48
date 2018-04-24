package it.polimi.ingsw.Server.Game.GameRules;

import it.polimi.ingsw.Server.Game.Cards.WindowPatternCard;
import it.polimi.ingsw.Server.Game.Components.Boards.DraftPool;
import it.polimi.ingsw.Server.Game.Components.Dice;
import it.polimi.ingsw.Server.Game.Utility.ANSI_COLOR;

public class ActionPlaceDice extends Action {
    private boolean ignoreColorRestricion;
    private boolean ignoreValueRestriction;

    private static final String actionName  = "Place Dice";
    private int coordinate ;
    private WindowPatternCard windowPatternCard;
    private int diceIndex;

    private Dice dice ;
    private DraftPool draftPool ;

    public ActionPlaceDice(boolean ignoreColorRestricion, boolean ignoreValueRestriction , DraftPool draftPool) {
        this.ignoreColorRestricion = ignoreColorRestricion;
        this.ignoreValueRestriction = ignoreValueRestriction;
        this.draftPool = draftPool;
    }

    public boolean placeDice(int coordinate , WindowPatternCard windowPatternCard , int diceIndex ){

        this.coordinate = coordinate;
        this.windowPatternCard = windowPatternCard;
        this.diceIndex = diceIndex;

        dice = draftPool.getDice(diceIndex);
        return windowPatternCard.isPlaceable(dice,coordinate,ignoreColorRestricion,ignoreValueRestriction);

    }


    public String getActionName() {
        return actionName;
    }

    public void doAction() {
        boolean res = windowPatternCard.placeDice(dice,coordinate,ignoreColorRestricion,ignoreValueRestriction);
        draftPool.removeDice(dice);

        //TODO throw an exception instead
        if (!res)
            System.out.println(ANSI_COLOR.ANSI_RED + "[!!] Action failed illegal placement" + ANSI_COLOR.ANSI_RESET);
    }
}

