package it.polimi.ingsw.Server.Game.ServerRete;

import it.polimi.ingsw.Server.Game.Cards.CardManager;
import it.polimi.ingsw.Server.Game.ServerRMI.ServerRMI;
import it.polimi.ingsw.Server.Game.ServerSocket.ServerSocketAccept;
import it.polimi.ingsw.Server.Game.Utility.CONSTANT;
import it.polimi.ingsw.Server.Game.Utility.Unpacker;
import it.polimi.ingsw.Server.Game.WaitingRoom.WaitingRoom;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class MainServer {

    public static void main(String[] args){

        Scanner keyboard = new Scanner(System.in);
        Unpacker.setUpUnpacker();
        
        try {
            CardManager.setWPCards(CONSTANT.windowPatternfile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Game game = new Game();
        WaitingRoom waitingRoom = new WaitingRoom();
        waitingRoom.setGame(game);


        System.out.println("Insert ip where RMI should be published: ");
        String ipServer = args[0];//keyboard.nextLine();

        System.out.println("Insert port where RMI should be published: ");
        int portRMI = 1099;//keyboard.nextInt();

        System.out.println("Insert port where socket should be listen: ");
        int portSocket = Integer.parseInt(args[2]);//keyboard.nextInt();

        ServerRMI serverRMI = new ServerRMI(portRMI, waitingRoom,game, ipServer);
        ServerSocketAccept serverSocketAccept = new ServerSocketAccept(portSocket, waitingRoom, game);
        Thread t = new Thread(serverSocketAccept);

        serverRMI.start();
        t.start();

    }
}
