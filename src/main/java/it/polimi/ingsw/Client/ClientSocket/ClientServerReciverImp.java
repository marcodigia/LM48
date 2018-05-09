package it.polimi.ingsw.Client.ClientSocket;

import it.polimi.ingsw.ClientServerCommonInterface.ClientServerReciver;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientServerReciverImp implements Runnable, ClientServerReciver {
    private Socket socket;
    private Scanner scanner;

    public ClientServerReciverImp(Socket socket){
        this.socket = socket;
        try {
            scanner = new Scanner(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

    }
}
