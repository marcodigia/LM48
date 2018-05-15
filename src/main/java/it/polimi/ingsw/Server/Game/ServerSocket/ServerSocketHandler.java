package it.polimi.ingsw.Server.Game.ServerSocket;

import it.polimi.ingsw.Server.Game.ServerRete.ServerRete;
import it.polimi.ingsw.Server.Game.WaitingRoom.WaitingRoom;

import java.net.Socket;

public class ServerSocketHandler implements Runnable {

    private ServerRete serverRete;
    private Socket socket;
    private WaitingRoom waitingRoom;

    public ServerSocketHandler(Socket socket, WaitingRoom waitingRoom, ServerRete serverRete){
        this.serverRete = serverRete;
        this.socket = socket;
        this.waitingRoom = waitingRoom;
    }

    @Override
    public void run() {
        ServerClientSenderImp serverClientSender = new ServerClientSenderImp(socket);
        ServerClientReciver serverClientReciver = new ServerClientReciver(socket, serverClientSender, waitingRoom, serverRete);
        Thread t = new Thread(serverClientSender);
        Thread t1 = new Thread(serverClientReciver);
        t.start();
        t1.start();
    }
}
