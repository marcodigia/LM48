package it.polimi.ingsw.Client.ClientRMI;

import it.polimi.ingsw.ClientServerCommonInterface.RMICommonInterface.SkeletonClient;
import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import it.polimi.ingsw.Server.Game.GameRules.Score;
import it.polimi.ingsw.UI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class SkeletonClientImp extends UnicastRemoteObject implements SkeletonClient{

    private UI ui;

     public SkeletonClientImp() throws RemoteException {
    }

    @Override
    public void sendMessage(String message) throws RemoteException {
        System.out.println(message);
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
