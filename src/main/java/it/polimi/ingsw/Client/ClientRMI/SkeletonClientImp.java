package it.polimi.ingsw.Client.ClientRMI;

import it.polimi.ingsw.ClientServerCommonInterface.RMICommonInterface.SkeletonClient;
import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import it.polimi.ingsw.Server.Game.GameRules.Player;
import it.polimi.ingsw.UI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class SkeletonClientImp extends UnicastRemoteObject implements SkeletonClient{

    private UI ui;
    private String username;

     public SkeletonClientImp() throws RemoteException {
    }

    public void setUsername(String username){
         this.username = username;
    }

    @Override
    public void ping() throws RemoteException {

    }

    @Override
    public void sendMessage(String message) throws RemoteException {
        ui.printMessage(message);
    }

    @Override
    public void chooseWindowPattern(String id1, String id2, String id3, String id4) throws RemoteException {
        ui.chooseWP(id1,id2,id3,id4);
    }

    @Override
    public void timerEnd() throws RemoteException {
        ui.disable();
    }

    @Override
    public void timerStart() throws RemoteException {
        ui.activate();
    }

    @Override
    public void sendGameStatus(GameStatus gameStatus) throws RemoteException {
         System.out.println("GameStatusRMISkeleton");
        ui.pingBack();
        ui.updateGameStatus(gameStatus);
    }

    @Override
    public void sendScore(Player player) throws RemoteException {
        ui.endGame(player.getName());
    }

    @Override
    public void sendCurrentPlayers(ArrayList<String> player) throws RemoteException {
        String message = new String("");
        for(String s : player){
            message = message + s + ", ";
        }
        ui.allCurrentPlayers(message);
    }

    @Override
    public void setUI(UI ui) throws RemoteException {
        this.ui = ui;
    }

    @Override
    public void close() throws RemoteException {
    }

}
