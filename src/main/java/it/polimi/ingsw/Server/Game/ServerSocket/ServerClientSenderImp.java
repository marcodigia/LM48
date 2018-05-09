package it.polimi.ingsw.Server.Game.ServerSocket;

import it.polimi.ingsw.ClientServerCommonInterface.ServerClientSender;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.RemoteException;

public class ServerClientSenderImp implements Runnable, ServerClientSender {
    private Socket socket;
    private PrintWriter printWriter;

    public ServerClientSenderImp(Socket socket){
        this.socket = socket;
        try {
            printWriter = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {

    }

    @Override
    public void sendHello() throws RemoteException {
        printWriter.println("MESSAGE £00£ Hello £00£");
        printWriter.flush();
    }
}
