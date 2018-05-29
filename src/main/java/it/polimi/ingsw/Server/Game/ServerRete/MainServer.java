package it.polimi.ingsw.Server.Game.ServerRete;

import it.polimi.ingsw.Server.Game.ServerRMI.ServerRMI;
import it.polimi.ingsw.Server.Game.ServerSocket.ServerSocketAccept;
import it.polimi.ingsw.Server.Game.Utility.Unpacker;
import it.polimi.ingsw.Server.Game.WaitingRoom.WaitingRoom;

import java.util.Scanner;

public class MainServer {

    public static void main(String[] args){

        Scanner keyboard = new Scanner(System.in);
        Unpacker.setUpUnpacker();

        Game game = new Game();
        WaitingRoom waitingRoom = new WaitingRoom();
        waitingRoom.setGame(game);

        System.out.println("Insert ip where RMI should be published: ");
        String ipServer = keyboard.nextLine();

        System.out.println("Insert port where RMI should be published: ");
        int portRMI = keyboard.nextInt();

        System.out.println("Insert port where socket should be listen: ");
        int portSocket = keyboard.nextInt();

        ServerRMI serverRMI = new ServerRMI(portRMI, waitingRoom,game, ipServer);
        ServerSocketAccept serverSocketAccept = new ServerSocketAccept(portSocket, waitingRoom, game);
        Thread t = new Thread(serverSocketAccept);

        serverRMI.start();
        t.start();

    }
}
