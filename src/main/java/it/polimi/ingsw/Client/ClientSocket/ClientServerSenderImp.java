package it.polimi.ingsw.Client.ClientSocket;

import it.polimi.ingsw.ClientServerCommonInterface.ClientServerSender;
import it.polimi.ingsw.ClientServerCommonInterface.ServerClientSender;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Actions;
import it.polimi.ingsw.Server.Game.Utility.CONSTANT;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.RemoteException;

public class ClientServerSenderImp implements ClientServerSender {

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
    public void pingBack(String username) throws RemoteException {
        printWriter.println("PINGBACK"+ CONSTANT.delimenter + username + CONSTANT.delimenter);
        printWriter.flush();
    }

    @Override
    public void register(String username, ServerClientSender clientRef) throws RemoteException {
        printWriter.println("R"+ CONSTANT.delimenter + username + CONSTANT.delimenter);
        printWriter.flush();
    }

    @Override
    public void choosenWindowPattern(String id, String username) throws RemoteException {
        printWriter.println("CWP" + CONSTANT.delimenter + id + CONSTANT.delimenter);
        printWriter.flush();
    }

    @Override
    public void sendAction(Actions action, String username) throws RemoteException {
        printWriter.println("A" + CONSTANT.delimenter + action.toPacket() + CONSTANT.delimenter);
        printWriter.flush();
    }

    @Override
    public void close() throws RemoteException {
        printWriter.close();
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
