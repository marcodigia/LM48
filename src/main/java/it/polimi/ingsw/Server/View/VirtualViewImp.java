package it.polimi.ingsw.Server.View;


import it.polimi.ingsw.ClientServerCommonInterface.ServerClientSender;
import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import it.polimi.ingsw.Server.Game.GameRules.Player;
import it.polimi.ingsw.Server.Game.Utility.ANSI_COLOR;
import it.polimi.ingsw.Server.Game.WaitingRoom.WaitingRoom;

import java.rmi.RemoteException;
import java.util.ArrayList;


//Virtual view is an Observer of the Model , and through ServerClientSender notify the changes to the ClientView
public class VirtualViewImp implements VirtualView {

    ServerClientSender serverClientSender;
    private Player myPlayer;

    public VirtualViewImp(ServerClientSender serverClientSender, Player player) {
        this.serverClientSender = serverClientSender;
        myPlayer = player;
    }

    public void setServerClientSender(ServerClientSender serverClientSender){this.serverClientSender = serverClientSender;}

    public ServerClientSender getServerClientSender() {
        return serverClientSender;
    }

    @Override
    public void ping() {
        try {
            serverClientSender.ping();
        } catch (RemoteException e) {
            myPlayer.setIsNotConnected(); //Not able to contact client. Set it as disconnected
            System.out.println("[!] Network problem "+myPlayer.getName()+" client unreachable : RMI" );
            WaitingRoom.removeClient(myPlayer.getName());
        }
    }

    @Override
    public void sendCurrentPlayers(ArrayList<String> player) {
        try {
            serverClientSender.sendCurrentPlayers(player);
        } catch (RemoteException e) {
            myPlayer.setIsNotConnected();
            System.out.println("[!] Network problem "+myPlayer.getName()+" client unreachable : RMI" );
            WaitingRoom.removeClient(myPlayer.getName());
        }
    }

    @Override
    public void sendMessage(String message) {
        try {
            serverClientSender.sendMessage(message);
        } catch (RemoteException e) {
            myPlayer.setIsNotConnected();
            System.out.println("[!] Network problem "+myPlayer.getName()+" client unreachable : RMI" );
            WaitingRoom.removeClient(myPlayer.getName());
        }
    }

    @Override
    public void chooseWindowPattern(String id1, String id2, String id3, String id4) {
        try {
            serverClientSender.chooseWindowPattern(id1,id2,id3,id4);
        } catch (RemoteException e) {
            myPlayer.setIsNotConnected();
            System.out.println("[!] Network problem "+myPlayer.getName()+" client unreachable : RMI" );
            WaitingRoom.removeClient(myPlayer.getName());
        }
    }

    @Override
    public void timerEnd() {
        try {
            serverClientSender.timerEnd();
        } catch (RemoteException e) {
            myPlayer.setIsNotConnected();
            System.out.println("[!] Network problem "+myPlayer.getName()+" client unreachable : RMI" );

            WaitingRoom.removeClient(myPlayer.getName());
        }
    }

    @Override
    public void timerStart() {
        try {
            serverClientSender.timerStart();
        } catch (RemoteException e) {
            myPlayer.setIsNotConnected();
            System.out.println("[!] Network problem "+myPlayer.getName()+" client unreachable : RMI" );
            WaitingRoom.removeClient(myPlayer.getName());

        }
    }

    @Override
    public void sendGameStatus(GameStatus gameStatus) {
        System.out.println("Virtual view sendGameStatus");
        try {
            if(!myPlayer.getStillAlive()) {
                myPlayer.setIsNotConnected();
                serverClientSender.sendGameStatus(gameStatus);
                System.out.println(ANSI_COLOR.ANSI_RED + "1: " + myPlayer.getName() + ANSI_COLOR.ANSI_RESET);
            }
            else {
                myPlayer.setStillAlive(false);
                serverClientSender.sendGameStatus(gameStatus);
                System.out.println("SetNotStillAlive: " + myPlayer.getName() );
            }
        } catch (RemoteException e) {
            System.out.println("Catch : " + e.getMessage());
            myPlayer.setIsNotConnected();
            System.out.println("[!] Network problem "+myPlayer.getName()+" client unreachable : RMI" );
            WaitingRoom.removeClient(myPlayer.getName());
        }
    }

    @Override
    public void sendScore(Player player) {
        try {
            serverClientSender.sendScore(player);
        } catch (RemoteException e) {
            myPlayer.setIsNotConnected();
            System.out.println("[!] Network problem "+myPlayer.getName()+" client unreachable : RMI" );
            WaitingRoom.removeClient(myPlayer.getName());

        }
    }
}
