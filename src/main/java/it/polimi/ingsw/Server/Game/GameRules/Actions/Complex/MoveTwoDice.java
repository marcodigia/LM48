package it.polimi.ingsw.Server.Game.GameRules.Actions.Complex;

import it.polimi.ingsw.Server.Game.GameRules.Actions.Actions;
import it.polimi.ingsw.Server.Game.GameRules.GameContext;

public class MoveTwoDice implements Actions {


    private int dice1From, dice1To;
    private int dice2From, dice2To;

    private GameContext gameContext;

    public MoveTwoDice(int dice1From, int dice1To, int dice2From, int dice2To, GameContext gameContext) {
        this.dice1From = dice1From;
        this.dice1To = dice1To;
        this.dice2From = dice2From;
        this.dice2To = dice2To;
        this.gameContext = gameContext;

    }


    @Override
    public void doAction() {
        if (gameContext.getWindowPatternCard().moveDice(dice1From, dice1To, false, false, false))
            if (!gameContext.getWindowPatternCard().moveDice(dice2From, dice2To, false, false, false))
                gameContext.getWindowPatternCard().moveDice(dice1To, dice1From, true, true, true);

    }
}
