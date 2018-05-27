package it.polimi.ingsw.Server.Game.ServerSocket;

import it.polimi.ingsw.Server.Game.ServerRete.Game;
import it.polimi.ingsw.Server.Game.WaitingRoom.WaitingRoom;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketAccept implements Runnable {

    private Game game;
    private int port;
    private ServerSocket serverSocket;
    private boolean bound = false;
    private WaitingRoom waitingRoom;

    public ServerSocketAccept(int port, WaitingRoom waitingRoom, Game game){
        this.game = game;
        this.port = port;
        this.waitingRoom = waitingRoom;
    }
    @Override
    public void run() {
        do{
            try{
                System.out.println(port);
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
                ServerSocketHandler serverSocketHandler = new ServerSocketHandler(socket, waitingRoom, game);
                Thread t = new Thread(serverSocketHandler);
                t.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
