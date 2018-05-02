package it.polimi.ingsw.Server.Game.GameRules.Actions.Complex;

import it.polimi.ingsw.Server.Game.GameRules.Actions.Actions;
import it.polimi.ingsw.Server.Game.GameRules.GameSetUp;

public class MoveOneDiceIgnoringValue implements Actions {

    private GameSetUp gameSetUp;
    private int from;
    private int to;

    public MoveOneDiceIgnoringValue(GameSetUp gameSetUp, int from, int to) {
        this.gameSetUp = gameSetUp;
        this.from = from;
        this.to = to;
    }

    @Override
    public void doAction() {
        gameSetUp.getWindowPatternCard().moveDice(from, to, false, true, false);

    }
}
