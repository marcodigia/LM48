package it.polimi.ingsw.Server.Game.ServerRete;

import it.polimi.ingsw.Server.Game.ServerRMI.ServerRMI;
import it.polimi.ingsw.Server.Game.ServerSocket.ServerSocketAccept;
import it.polimi.ingsw.Server.Game.WaitingRoom.WaitingRoom;

public class MainServer {

    private static final int SERVERSOCKETPORT = 2000;
    private static final int RMIPORT = 1099;

    public static void main(String[] args){
        ServerRete serverRete = new ServerRete();
        WaitingRoom waitingRoom = new WaitingRoom();
        waitingRoom.setServerRete(serverRete);
        ServerRMI serverRMI = new ServerRMI(RMIPORT, waitingRoom);
        ServerSocketAccept serverSocketAccept = new ServerSocketAccept(SERVERSOCKETPORT, waitingRoom, serverRete);
        Thread t = new Thread(serverSocketAccept);

        serverRMI.start();
        t.start();

    }
}
