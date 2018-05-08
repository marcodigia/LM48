package it.polimi.ingsw.Server.Game.GameRules.Actions.Complex;

import it.polimi.ingsw.Client.View.UI;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Actions;
import it.polimi.ingsw.Server.Game.GameRules.GameContext;

public class MoveOneDieIgnoringColor implements Actions {

    private GameContext gameContext;
    private int from;
    private int to;

    public MoveOneDieIgnoringColor(GameContext gameContext, int from, int to) {
        this.gameContext = gameContext;
        this.from = from;
        this.to = to;
    }

    @Override
    public void doAction(GameContext gameContext) {
        this.gameContext.getWindowPatternCard().moveDice(from, to, true, false, false);

    }

    @Override
    public void useAction(UI ui, GameContext gameContext) {

    }
}
