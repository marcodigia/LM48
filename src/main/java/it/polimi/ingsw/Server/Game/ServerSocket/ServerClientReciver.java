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
                        waitingRoom.addClient(username, serverClientSenderImp);
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
                                }
                                else{
                                    //If player is in game & alive ping him
                                    if(game.scanForUsername(username)!=null) {
                                        if (game.scanForUsername(username).getStillAlive()) {
                                            game.scanForUsername(username).setStillAlive(false);
                                            game.scanForUsername(username).getvirtualView().ping();
                                        }
                                    }
                                    else{ //Not in waiting room or in game
                                        if(waitingRoom.scanForSameUsername(username)!=null){
                                            if(!waitingRoom.scanForSameUsername(username).getStillAlive()) {
                                                waitingRoom.removeClient(username);
                                            }
                                        }
                                        if(game.scanForUsername(username)!=null) {
                                            if (!game.scanForUsername(username).getStillAlive()) {
                                                game.scanForUsername(username).setIsNotConnected();
                                            }
                                        }
                                    }
                                }
                            }
                        }, 0, timerUtility.readTimerFromFile(5, "timerDelayPing.txt"));

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

                        System.out.println("Server Client Receiver " + name[name.length-1]);
                        if (name[name.length-1].equals("PlaceDiceAction"))
                            game.getGameStatus().getPlayerByName(username).setPlaceDiceOfTheTurn( (PlaceDiceAction) a) ;
                        else
                            game.getGameStatus().getPlayerByName(username).setUseToolCardOfTheTurn( (UseToolCardBasic) a); ;

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
                waitingRoom.removeClient(username);

                break;
            }
        }
    }
}
