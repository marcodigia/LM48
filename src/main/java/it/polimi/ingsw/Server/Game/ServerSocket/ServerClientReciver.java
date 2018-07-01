package it.polimi.ingsw.Server.Game.ServerSocket;

import it.polimi.ingsw.Server.Game.GameRules.Actions.Actions;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Basic.PlaceDiceAction;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Basic.UseToolCardBasic;
import it.polimi.ingsw.Server.Game.GameRules.Player;
import it.polimi.ingsw.Server.Game.ServerRete.Game;
import it.polimi.ingsw.Server.Game.TimerUtility.TimerUtility;
import it.polimi.ingsw.Server.Game.Utility.CONSTANT;
import it.polimi.ingsw.Server.Game.Utility.Unpacker;
import it.polimi.ingsw.Server.Game.WaitingRoom.WaitingRoom;
import it.polimi.ingsw.Server.View.VirtualViewImp;

import java.io.IOException;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class ServerClientReciver implements Runnable {

    private Game game;
    private Socket socket;
    private Scanner scanner;
    private ServerClientSenderImp serverClientSenderImp;
    private String username;
    private String message;
    private WaitingRoom waitingRoom;

    public ServerClientReciver(Socket socket, ServerClientSenderImp serverClientSenderImp, WaitingRoom waitingRoom, Game game){
        this.game = game;
        this.socket = socket;
        this.serverClientSenderImp = serverClientSenderImp;
        this.waitingRoom = waitingRoom;
        try {
            scanner = new Scanner(socket.getInputStream()).useDelimiter("\\s*£00£\\s*");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void manageDisconnection(){
        TimerUtility timerUtility = new TimerUtility();
        Timer timeraaa = new Timer();
        timeraaa.schedule(new TimerTask() {
            @Override
            public void run() {
                //If player is in waiting room & alive ping him
                if(waitingRoom.scanForSameUsername(username)!=null) {
                    if (waitingRoom.scanForSameUsername(username).getStillAlive()) {
                        waitingRoom.scanForSameUsername(username).setStillAlive(false);
                        waitingRoom.scanForSameUsername(username).getvirtualView().ping();
                    }
                    else{
                        waitingRoom.removeClient(username);
                    }
                }
                else{
                    //If player is in game & alive ping him
                    if(game.scanForUsername(username)!=null) {
                        if (game.scanForUsername(username).getStillAlive()) {
                            game.scanForUsername(username).setStillAlive(false);
                            game.scanForUsername(username).getvirtualView().ping();
                        }
                        else{
                            game.scanForUsername(username).setIsNotConnected();
                        }
                    }
                }
            }
        }, 0, timerUtility.readTimerFromFile(5, "timerDelayPing.txt"));
    }

    @Override
    public void run() {
        while(true){
            try{
                String command = scanner.next();
                switch(command){
                    case "PINGBACK":
                        if(waitingRoom.scanForSameUsername(username)!=null) //Still alive in waiting room
                            waitingRoom.scanForSameUsername(username).setStillAlive(true);
                        else
                            game.scanForUsername(username).setStillAlive(true); //Still alive in game
                        break;
                    case "R":
                        username = scanner.next();
                        if(game.scanForUsername(username)!=null) {
                            if (!game.scanForUsername(username).getConnected()) {
                                game.scanForUsername(username).setStillAlive(true);
                                game.scanForUsername(username).setIsConnected();
                                try {
                                    ((VirtualViewImp)game.scanForUsername(username).getvirtualView()).setServerClientSender(serverClientSenderImp);
                                    serverClientSenderImp.sendMessage(CONSTANT.correctUsername);
                                    serverClientSenderImp.sendGameStatus(game.getGameStatus());
                                } catch (RemoteException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        else
                            waitingRoom.addClient(username, serverClientSenderImp);

                        manageDisconnection();
                        break;
                    case "U":
                        username = scanner.next();
                        waitingRoom.removeClient(username);
                        game.setPlayerAsDisconnected(username);
                        break;
                    case "CWP":
                        message = scanner.next();
                        System.out.println(message+username);
                        game.setWindowToPlayer(message,username);
                        break;
                    case "A":
                        message = scanner.next();
                        Actions a = Unpacker.ACT_fromPacket(message, CONSTANT.ObjectDelimeter);
                        String[] name = a.getClass().getName().split("\\.");

                        System.out.println(message);
                        if (name[name.length - 1].equals("PlaceDiceAction")) {

                            Boolean status = game.getGameStatus().getPlayerByName(username).getPlaceDiceState();
                            game.getGameStatus().getPlayerByName(username).setPlaceDiceOfTheTurn( (PlaceDiceAction) a) ;
                            a.setACTIVE(status);

                        } else {
                            Boolean status = game.getGameStatus().getPlayerByName(username).getUseToolCardState();
                            game.getGameStatus().getPlayerByName(username).setUseToolCardOfTheTurn((UseToolCardBasic) a);
                            a.setACTIVE(status);
                        }
                        a.setUserName(username);
                        a.doAction(game.getGameStatus());

                        for(Player p : game.getPlayers().keySet()) {
                            p.getvirtualView().sendGameStatus(game.getGameStatus());
                        }
                        break;
                    default:
                        break;
                }
            }catch(NoSuchElementException e){
                System.out.println("[!] Network problem "+username+" client unreachable" );
                if(game.scanForUsername(username)!=null) {
                        game.scanForUsername(username).setIsNotConnected();
                }
                else {
                    waitingRoom.removeClient(username);
                }
                break;
            }
        }
    }
}
