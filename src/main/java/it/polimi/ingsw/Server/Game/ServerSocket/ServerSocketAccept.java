package it.polimi.ingsw.Server.Game.ServerSocket;

import it.polimi.ingsw.Server.Game.ServerRete.ServerRete;
import it.polimi.ingsw.Server.Game.WaitingRoom.WaitingRoom;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketAccept implements Runnable {

    private ServerRete serverRete;
    private int port;
    private ServerSocket serverSocket;
    private boolean bound = false;
    private WaitingRoom waitingRoom;

    public ServerSocketAccept(int port, WaitingRoom waitingRoom, ServerRete serverRete){
        this.serverRete = serverRete;
        this.port = port;
        this.waitingRoom = waitingRoom;
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
                ServerSocketHandler serverSocketHandler = new ServerSocketHandler(socket, waitingRoom, serverRete);
                Thread t = new Thread(serverSocketHandler);
                t.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
