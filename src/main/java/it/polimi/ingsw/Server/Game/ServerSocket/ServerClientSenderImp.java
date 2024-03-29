package it.polimi.ingsw.Server.Game.ServerSocket;

import it.polimi.ingsw.ClientServerCommonInterface.ServerClientSender;
import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import it.polimi.ingsw.Server.Game.GameRules.Player;
import it.polimi.ingsw.Server.Game.Utility.CONSTANT;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ServerClientSenderImp implements ServerClientSender {

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
    public void ping() throws RemoteException {
        printWriter.println("PING" + CONSTANT.delimenter);
        printWriter.flush();
    }

    @Override
    public void sendMessage(String message) throws RemoteException {
        printWriter.println("S" + CONSTANT.delimenter + message + CONSTANT.delimenter);
        printWriter.flush();
    }

    @Override
    public void chooseWindowPattern(String id1, String id2, String id3, String id4) throws RemoteException {
        printWriter.println("CW" + CONSTANT.delimenter + id1 + CONSTANT.delimenter + id2 +
                CONSTANT.delimenter + id3 + CONSTANT.delimenter + id4 + CONSTANT.delimenter);
        printWriter.flush();
    }

    @Override
    public void timerEnd() throws RemoteException {
        printWriter.println("TE" + CONSTANT.delimenter);
        printWriter.flush();
    }

    @Override
    public void timerStart() throws RemoteException {
        printWriter.println("TS" + CONSTANT.delimenter);
        printWriter.flush();
    }

    @Override
    public void sendGameStatus(GameStatus gameStatus) throws RemoteException {
        printWriter.println("SGS" + CONSTANT.delimenter + gameStatus.toPacket() + CONSTANT.delimenter);
        printWriter.flush();

    }

    @Override
    public void sendScore(Player player) throws RemoteException {
        printWriter.println("SC" + CONSTANT.delimenter + player.getName() + CONSTANT.delimenter);
        printWriter.flush();
    }

    @Override
    public void sendCurrentPlayers(ArrayList<String> player) throws RemoteException {
        String message = new String("");
        for(String s : player){
            message = message + s + ", ";
        }
        printWriter.println("ALL" + CONSTANT.delimenter + message + CONSTANT.delimenter);
        printWriter.flush();
    }

}
