package it.polimi.ingsw.Server.Game.ServerSocket;

import it.polimi.ingsw.ClientServerCommonInterface.ServerClientSender;
import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import it.polimi.ingsw.Server.Game.GameRules.Score;

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
    public void sendMessage(String message) throws RemoteException {
        printWriter.println("MESSAGE £00£"+message+"£00£");
        printWriter.flush();
    }

    @Override
    public void chooseWindowPattern(String id1, String id2, String id3, String id4) throws RemoteException {

    }

    @Override
    public void timerEnd() throws RemoteException {

    }

    @Override
    public void timerStart() throws RemoteException {

    }

    @Override
    public void sendGameStatus(GameStatus gameStatus) throws RemoteException {

    }

    @Override
    public void sendScore(Score score) throws RemoteException {

    }
}
