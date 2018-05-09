package it.polimi.ingsw.Server.Game.ServerSocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketAccept implements Runnable {
    private int port;
    private ServerSocket serverSocket;

    public ServerSocketAccept(int port){
        this.port = port;
    }
    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            while(true){
                Socket socket = serverSocket.accept();
                ServerSocketHandler serverSocketHandler = new ServerSocketHandler(socket);
                Thread t = new Thread(serverSocketHandler);
                t.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
