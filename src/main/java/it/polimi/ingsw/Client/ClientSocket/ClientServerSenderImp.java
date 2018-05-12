package it.polimi.ingsw.Client.ClientSocket;

import it.polimi.ingsw.ClientServerCommonInterface.ClientServerSender;
import it.polimi.ingsw.ClientServerCommonInterface.ServerClientSender;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.RemoteException;

public class ClientServerSenderImp implements Runnable, ClientServerSender {

    private Socket socket;
    private PrintWriter printWriter;

    public ClientServerSenderImp(Socket socket){
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
    public void register(String username, ServerClientSender clientRef) throws RemoteException {
        printWriter.println("R £00£ "+username+" £00£");
        printWriter.flush();
    }

    @Override
    public void unregister(String username) throws RemoteException {
        printWriter.println("U £00£ "+username+" £00£");
        printWriter.flush();
    }

    @Override
    public void choosenWindowPattern(String id) throws RemoteException {

    }

    @Override
    public void sendAction(Actions action) throws RemoteException {

    }

    @Override
    public void endOfTurn() throws RemoteException {

    }
}
