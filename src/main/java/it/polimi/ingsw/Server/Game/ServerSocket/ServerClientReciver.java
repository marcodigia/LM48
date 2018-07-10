package it.polimi.ingsw.Server.Game.ServerSocket;

import it.polimi.ingsw.Server.Game.GameRules.Actions.Actions;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Basic.PlaceDiceAction;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Basic.UseToolCardBasic;
import it.polimi.ingsw.Server.Game.GameRules.Player;
import it.polimi.ingsw.Server.Game.ServerRete.Game;
import it.polimi.ingsw.Server.Game.ServerRete.Turn;
import it.polimi.ingsw.Server.Game.TimerUtility.TimerUtility;
import it.polimi.ingsw.Server.Game.Utility.ANSI_COLOR;
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


    @Override
    public void run() {
        while(true){
            try{
                String command = scanner.next();
                switch(command){
                    case "PINGBACK":
                        System.out.println("pingback server" + username);
                        if(game.scanForUsername(username)!=null) {
                            game.scanForUsername(username).setStillAlive(true); //Still alive in game
                            game.scanForUsername(username).setIsConnected();
                        }
                        break;
                    case "R":
                        username = scanner.next();
                        if(game.scanForUsername(username)!=null) {
                            if (!game.scanForUsername(username).getConnected()) {
                                game.scanForUsername(username).setStillAlive(true);
                                game.scanForUsername(username).setIsConnected();

                                if(Turn.getPlayers().entrySet().iterator().next().getKey().getName().equals(username))
                                    Turn.getPlayers().entrySet().iterator().next().getKey().getvirtualView().timerStart();
                                else
                                    Turn.getPlayers().entrySet().iterator().next().getKey().getvirtualView().timerEnd();

                                
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
                        break;
                    case "U":
                        username = scanner.next();
                        waitingRoom.removeClient(username);
                        game.setPlayerAsDisconnected(username);
                        break;
                    case "CWP":
                        message = scanner.next();
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
                        if(!Turn.getCurrentPlayer().getName().equals(username))
                            game.scanForUsername(username).getvirtualView().timerEnd();
                        else{
                            a.doAction(game.getGameStatus());
                        }

                        for(Player p : game.getPlayers().keySet()) {
                            p.getvirtualView().sendGameStatus(game.getGameStatus());
                            System.out.println(ANSI_COLOR.BOLD+"Player name "+ username + " placeDiceStaete " + game.getGameStatus().getPlayerByName(username).getPlaceDiceOfTheTurn() +
                            " useToolcardState " + game.getGameStatus().getPlayerByName(username).getUseToolCardState()+ ANSI_COLOR.ANSI_RESET);
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
