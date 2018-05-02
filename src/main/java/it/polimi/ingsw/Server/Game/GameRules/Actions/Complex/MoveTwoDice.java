package it.polimi.ingsw.Server.Game.GameRules.Actions.Complex;

import it.polimi.ingsw.Server.Game.GameRules.Actions.Actions;
import it.polimi.ingsw.Server.Game.GameRules.GameSetUp;

public class MoveTwoDice implements Actions {


    private int dice1From, dice1To;
    private int dice2From, dice2To;

    private GameSetUp gameSetUp;

    public MoveTwoDice(int dice1From, int dice1To, int dice2From, int dice2To, GameSetUp gameSetUp) {
        this.dice1From = dice1From;
        this.dice1To = dice1To;
        this.dice2From = dice2From;
        this.dice2To = dice2To;
        this.gameSetUp = gameSetUp;

    }


    @Override
    public void doAction() {
        if (gameSetUp.getWindowPatternCard().moveDice(dice1From, dice1To, false, false, false))
            if (!gameSetUp.getWindowPatternCard().moveDice(dice2From, dice2To, false, false, false))
                gameSetUp.getWindowPatternCard().moveDice(dice1To, dice1From, true, true, true);

    }
}
