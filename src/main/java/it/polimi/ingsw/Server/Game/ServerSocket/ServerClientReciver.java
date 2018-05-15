package it.polimi.ingsw.Server.Game.ServerSocket;

import it.polimi.ingsw.Server.Game.ServerRete.ServerRete;
import it.polimi.ingsw.Server.Game.WaitingRoom.WaitingRoom;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ServerClientReciver implements Runnable {

    private ServerRete serverRete;
    private Socket socket;
    private Scanner scanner;
    private ServerClientSenderImp serverClientSenderImp;
    private String username;
    private String id;
    private WaitingRoom waitingRoom;

    public ServerClientReciver(Socket socket, ServerClientSenderImp serverClientSenderImp, WaitingRoom waitingRoom, ServerRete serverRete){
        this.serverRete = serverRete;
        this.socket = socket;
        this.serverClientSenderImp = serverClientSenderImp;
        this.waitingRoom = waitingRoom;
        try {
            scanner = new Scanner(socket.getInputStream()).useDelimiter("\\s*£00£\\s*");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(true){

            String command = scanner.next();
            switch(command){
                case "R":
                    username = scanner.next();
                    waitingRoom.addClient(username, serverClientSenderImp);
                    break;
                case "U":
                    username = scanner.next();
                    waitingRoom.removeClient(username);
                    break;
                case "CWP":
                    id = scanner.next();
                    serverRete.setWindowPattern(id);
                default:
                    break;
            }
        }
    }
}
