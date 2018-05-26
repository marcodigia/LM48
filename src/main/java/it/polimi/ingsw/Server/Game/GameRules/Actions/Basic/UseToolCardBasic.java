package it.polimi.ingsw.Server.Game.GameRules.Actions.Basic;

import it.polimi.ingsw.Server.Game.GameRules.Actions.Actions;
import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import it.polimi.ingsw.UI;

public class UseToolCardBasic implements Actions {


    private boolean ACTIVE = true;

    private Actions toolCardAction;
    public UseToolCardBasic() {

    }

    @Override
    public void doAction(GameStatus gameStatus) {

        if (!ACTIVE)
            return;

        toolCardAction.doAction(gameStatus);
        ACTIVE = false;
    }

    @Override
    public void useAction(UI ui, GameStatus gameStatus, String userName) {

       /* if (!ACTIVE)
            return;

        toolCardAction = gameContext.getChoosenToolCard().getActions();

            toolCardAction.useAction(ui, gamestatus);*/


        //TODO send Action to the Server

    }

    @Override
    public void setUpPlaceDiceAction(String packet) {

    }

    @Override
    public void setUserName(String userName) {

    }

    public void setACTIVE(boolean b){
        ACTIVE = b;
    }

    public boolean actionState(){
        return ACTIVE ;
    }

    @Override
    public String toPacket() {
        return null;
    }
}
