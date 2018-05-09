package it.polimi.ingsw.Server.Game.ServerSocket;

import java.net.Socket;

public class ServerSocketHandler implements Runnable {
    private Socket socket;

    public ServerSocketHandler(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        ServerClientSenderImp serverClientSender = new ServerClientSenderImp(socket);
        ServerClientReciver serverClientReciver = new ServerClientReciver(socket, serverClientSender);
        Thread t = new Thread(serverClientSender);
        Thread t1 = new Thread(serverClientReciver);
        t.start();
        t1.start();
    }
}
