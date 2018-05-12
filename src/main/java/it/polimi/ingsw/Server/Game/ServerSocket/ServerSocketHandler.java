package it.polimi.ingsw.Server.Game.ServerSocket;

import it.polimi.ingsw.Server.Game.WaitingRoom.WaitingRoom;

import java.net.Socket;

public class ServerSocketHandler implements Runnable {
    private Socket socket;
    private WaitingRoom waitingRoom;

    public ServerSocketHandler(Socket socket, WaitingRoom waitingRoom){
        this.socket = socket;
        this.waitingRoom = waitingRoom;
    }

    @Override
    public void run() {
        ServerClientSenderImp serverClientSender = new ServerClientSenderImp(socket);
        ServerClientReciver serverClientReciver = new ServerClientReciver(socket, serverClientSender, waitingRoom);
        Thread t = new Thread(serverClientSender);
        Thread t1 = new Thread(serverClientReciver);
        t.start();
        t1.start();
    }
}
