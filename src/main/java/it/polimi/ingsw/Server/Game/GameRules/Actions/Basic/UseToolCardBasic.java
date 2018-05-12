package it.polimi.ingsw.Server.Game.GameRules.Actions.Basic;

import it.polimi.ingsw.Server.Game.GameRules.Actions.Actions;
import it.polimi.ingsw.Server.Game.GameRules.GameContext;
import it.polimi.ingsw.UI;

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

        toolCardAction = gameContext.getChoosenToolCard().getActions();

            toolCardAction.useAction(ui,gameContext);


        //TODO send Action to the Server

    }

    @Override
    public String toPacket() {
        return null;
    }
}
