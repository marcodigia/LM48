package it.polimi.ingsw.Server.Game.GameRules.Actions;

import it.polimi.ingsw.Client.View.UI;
import it.polimi.ingsw.Server.Game.GameRules.GameContext;

import java.io.Serializable;

//UI use useAction to setup the action and then sends Action to the Server
public interface Actions extends Serializable {

    //GameContext as paramenter because this method is executed on the server so need to use the correct Data
    //If the user Did any modification to the state before use the toolcard it must be send to the server prior to
    // use the toolcard
    void doAction(GameContext gameContext);

    //To be used by UI
    void useAction(UI ui, GameContext gameContext);
}
