package it.polimi.ingsw.Server.Game.ServerSocket;

import it.polimi.ingsw.Server.Game.ServerRete.Game;
import it.polimi.ingsw.Server.Game.WaitingRoom.WaitingRoom;

import java.net.Socket;

public class ServerSocketHandler implements Runnable {

    private Game game;
    private Socket socket;
    private WaitingRoom waitingRoom;

    public ServerSocketHandler(Socket socket, WaitingRoom waitingRoom, Game game){
        this.game = game;
        this.socket = socket;
        this.waitingRoom = waitingRoom;
    }

    @Override
    public void run() {
        ServerClientSenderImp serverClientSender = new ServerClientSenderImp(socket);
        ServerClientReciver serverClientReciver = new ServerClientReciver(socket, serverClientSender, waitingRoom, game);
        Thread t1 = new Thread(serverClientReciver);
        t1.start();
    }
}
