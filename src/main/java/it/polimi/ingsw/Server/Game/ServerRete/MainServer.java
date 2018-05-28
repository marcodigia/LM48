package it.polimi.ingsw.Server.Game.ServerRete;

import it.polimi.ingsw.Server.Game.ServerRMI.ServerRMI;
import it.polimi.ingsw.Server.Game.ServerSocket.ServerSocketAccept;
import it.polimi.ingsw.Server.Game.Utility.Unpacker;
import it.polimi.ingsw.Server.Game.WaitingRoom.WaitingRoom;

import java.util.Scanner;

public class MainServer {

    private static final int SERVERSOCKETPORT = 2001;
    private static final int RMIPORT = 1099;

    public static void main(String[] args){

        Unpacker.setUpUnpacker();

        Game game = new Game();
        WaitingRoom waitingRoom = new WaitingRoom();
        waitingRoom.setGame(game);
        System.out.println("Insert ip where RMI should be published: ");
        Scanner keyboard = new Scanner(System.in);
        ServerRMI serverRMI = new ServerRMI(RMIPORT, waitingRoom,game, keyboard.nextLine());
        ServerSocketAccept serverSocketAccept = new ServerSocketAccept(SERVERSOCKETPORT, waitingRoom, game);
        Thread t = new Thread(serverSocketAccept);

        serverRMI.start();
        t.start();

    }
}
