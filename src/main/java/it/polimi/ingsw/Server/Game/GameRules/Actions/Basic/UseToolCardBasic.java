package it.polimi.ingsw.Server.Game.GameRules.Actions.Basic;

import it.polimi.ingsw.UI;
import it.polimi.ingsw.Exceptions.NoPossibleValidMovesException;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Actions;
import it.polimi.ingsw.Server.Game.GameRules.GameContext;

public class UseToolCardBasic implements Actions {


    private boolean ACTIVE = true;

    Actions toolCardAction;
    public UseToolCardBasic() {

    }

    @Override
    public void doAction(GameContext gameContext) {

        if (!ACTIVE)
            return;

        toolCardAction.doAction(gameContext);
        ACTIVE = false;
    }

    @Override
    public void useAction(UI ui, GameContext gameContext) {

        if (!ACTIVE)
            return;
        try {
            toolCardAction = gameContext.getChoosenToolCard().getActions(ui , gameContext);

            toolCardAction.useAction(ui,gameContext);

        } catch (NoPossibleValidMovesException e) {
            e.printStackTrace();
            return;
        }

        //TODO send Action to the Server

    }
}
