package it.polimi.ingsw.Server.Game.GameRules.Actions.Basic;

import it.polimi.ingsw.Server.Game.GameRules.Actions.Actions;

public interface  BasicAction extends Actions {


    void setACTIVE(boolean b);
    void setUserName(String userName);

}
