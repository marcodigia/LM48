package it.polimi.ingsw.Server.Game.ServerRete;

import it.polimi.ingsw.Server.Game.ServerRMI.ServerRMI;
import it.polimi.ingsw.Server.Game.ServerSocket.ServerSocketAccept;
import it.polimi.ingsw.Server.Game.WaitingRoom.WaitingRoom;

public class MainServer {

    private static final int SERVERSOCKETPORT = 2000;
    private static final int RMIPORT = 1099;

    public static void main(String[] args){
        Game game = new Game();
        WaitingRoom waitingRoom = new WaitingRoom();
        waitingRoom.setGame(game);
        ServerRMI serverRMI = new ServerRMI(RMIPORT, waitingRoom,game);
        ServerSocketAccept serverSocketAccept = new ServerSocketAccept(SERVERSOCKETPORT, waitingRoom, game);
        Thread t = new Thread(serverSocketAccept);

        serverRMI.start();
        t.start();

    }
}
