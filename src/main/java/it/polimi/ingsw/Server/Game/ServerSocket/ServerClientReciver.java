package it.polimi.ingsw.Server.Game.ServerSocket;

import it.polimi.ingsw.Server.Game.WaitingRoom.WaitingRoom;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ServerClientReciver implements Runnable {
    private Socket socket;
    private Scanner scanner;
    private ServerClientSenderImp serverClientSenderImp;
    private String username;

    public ServerClientReciver(Socket socket, ServerClientSenderImp serverClientSenderImp){
        this.socket = socket;
        this.serverClientSenderImp = serverClientSenderImp;
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
                case "REGISTER":
                    username = scanner.next();
                    WaitingRoom.addClient(username, serverClientSenderImp);
                    break;
                /*case "UNREGISTER":
                    username = scanner.next();
                    WaitingRoom.removeClient(username);
                    break;*/
                default:
                    break;
            }
        }
    }
}
