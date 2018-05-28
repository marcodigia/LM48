package it.polimi.ingsw.Server.Game.GameRules.Actions.Basic;

import it.polimi.ingsw.Server.Game.GameRules.Actions.Actions;
import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import it.polimi.ingsw.Server.Game.Utility.CONSTANT;
import it.polimi.ingsw.UI;

public class UseToolCardBasic implements Actions {


    private boolean ACTIVE = true;

    private Actions toolCardAction;
    private String userName;
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

        this.userName = userName;
      if(!ACTIVE)
          return;

     toolCardAction= ui.getChoosenToolCard().getActions();

     toolCardAction.useAction(ui,gameStatus,userName);

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

        StringBuilder packet = new StringBuilder();
        packet.append(PlaceDiceAction.class.getName()).append(CONSTANT.ObjectDelimeter);


        packet.append(CONSTANT.ObjectDelimeter).append(userName);
        packet.append(CONSTANT.ObjectDelimeter).append(ACTIVE);

        packet.append(toolCardAction.toPacket()).append(CONSTANT.ObjectDelimeter);

        return null;
    }
}
