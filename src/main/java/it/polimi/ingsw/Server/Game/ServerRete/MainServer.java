package it.polimi.ingsw.Server.Game.ServerRete;

import it.polimi.ingsw.Server.Game.ServerRMI.ServerRMI;
import it.polimi.ingsw.Server.Game.ServerSocket.ServerSocketAccept;
import it.polimi.ingsw.Server.Game.WaitingRoom.WaitingRoom;

public class MainServer {
    public static void main(String[] args){
        ServerRete serverRete = new ServerRete();
        WaitingRoom waitingRoom = new WaitingRoom();
        waitingRoom.setServerRete(serverRete);
        ServerRMI serverRMI = new ServerRMI();
        ServerSocketAccept serverSocketAccept = new ServerSocketAccept(2000);
        Thread t = new Thread(serverSocketAccept);

        serverRMI.start();
        t.start();

    }
}
