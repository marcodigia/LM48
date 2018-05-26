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
        printWriter.println("S £00£"+message+"£00£");
        printWriter.flush();
    }

    @Override
    public void chooseWindowPattern(String id1, String id2, String id3, String id4) throws RemoteException {
        printWriter.println("CW £00£"+id1+"£00£"+id2+"£00£"+id3+"£00£"+id4+"£00£");
        printWriter.flush();
    }

    @Override
    public void timerEnd() throws RemoteException {
        printWriter.println("TE £00£");
        printWriter.flush();
    }

    @Override
    public void timerStart() throws RemoteException {
        printWriter.println("TS £00£");
        printWriter.flush();
    }

    @Override
    public void sendGameStatus(GameStatus gameStatus) throws RemoteException {
        printWriter.println("SGS £00£"+gameStatus.toPacket()+"£00£");
        System.out.println("server client server imp gamestatus" + gameStatus.toPacket());
        printWriter.flush();
    }

    @Override
    public void sendScore(Score score) throws RemoteException {
        printWriter.println("SC £00£"+score.toString()+"£00£");
        printWriter.flush();
    }
}
