package it.polimi.ingsw.Server.Game.ServerSocket;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketAccept implements Runnable {
    private int port;
    private ServerSocket serverSocket;
    private boolean bound = false;
    public ServerSocketAccept(int port){
        this.port = port;
    }
    @Override
    public void run() {
        do{
            try{
                serverSocket = new ServerSocket(port);
                bound = true;
            } catch(IOException e){
                if(e instanceof BindException)
                    port++;
            }
        }while(!bound && port < 3000);

        try {
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
