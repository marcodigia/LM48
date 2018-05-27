package it.polimi.ingsw.Server.View;


import it.polimi.ingsw.ClientServerCommonInterface.ServerClientSender;
import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import it.polimi.ingsw.Server.Game.GameRules.Player;
import it.polimi.ingsw.Server.Game.GameRules.Score;

import java.rmi.RemoteException;
import java.util.ArrayList;


//Virtual view is an Observer of the Model , and through ServerClientSender notify the changes to the ClientView
public class VirtualViewImp implements VirtualView {

    ServerClientSender serverClientSender;

    public VirtualViewImp(ServerClientSender serverClientSender) {
        this.serverClientSender = serverClientSender;
    }

    public ServerClientSender getServerClientSender() {
        return serverClientSender;
    }

    @Override
    public void sendCurrentPlayers(ArrayList<String> player) {
        try {
            serverClientSender.sendCurrentPlayers(player);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessage(String message) {
        try {
            serverClientSender.sendMessage(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void chooseWindowPattern(String id1, String id2, String id3, String id4) {
        try {
            serverClientSender.chooseWindowPattern(id1,id2,id3,id4);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void timerEnd() {
        try {
            serverClientSender.timerEnd();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void timerStart() {
        try {
            System.out.println("VirtualView");
            serverClientSender.timerStart();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendGameStatus(GameStatus gameStatus) {
        try {
            serverClientSender.sendGameStatus(gameStatus);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendScore(Score score) {
        try {
            serverClientSender.sendScore(score);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
