package it.polimi.ingsw.Client.ClientSocket;

import it.polimi.ingsw.ClientServerCommonInterface.ClientServerReciver;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientServerReciverImp implements Runnable, ClientServerReciver {
    private Socket socket;
    private Scanner scanner;
    private String message;

    public ClientServerReciverImp(Socket socket){
        this.socket = socket;
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
                case "MESSAGE":
                     message = scanner.next();
                     System.out.println(message);
                    break;

                default:
                    break;
            }
        }
    }
}
