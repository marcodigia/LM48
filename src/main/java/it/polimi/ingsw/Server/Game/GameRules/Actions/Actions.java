package it.polimi.ingsw.Server.Game.GameRules.Actions;

import it.polimi.ingsw.Packetable;
import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import it.polimi.ingsw.UI;

//UI use useAction to setup the action and then sends Action to the Server
public interface Actions extends Packetable {

    //GameContext as paramenter because this method is executed on the server so need to use the correct Data
    //If the user Did any modification to the state before use the toolcard it must be send to the server prior to
    // use the toolcard
    void doAction(GameStatus gameStatus);

    //To be used by UI
    void useAction(UI ui, GameStatus gameStatus, String userName);


    /*
    In case RMI is in use just send the whole object through a method, so doAction can be executed directly on the server.
    In case Socekt is in use View on the Client will just update the virtual view on the Server and then virutalview will use useAction
    */

    void setACTIVE(boolean b);



    //Used to rebuild the action from packet
    void setUpAction(String packet);

    void setUserName(String userName);
}
