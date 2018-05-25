package it.polimi.ingsw.Server.Game.ServerSocket;

import it.polimi.ingsw.Server.Game.GameRules.Actions.Actions;
import it.polimi.ingsw.Server.Game.ServerRete.Game;
import it.polimi.ingsw.Server.Game.Utility.Unpacker;
import it.polimi.ingsw.Server.Game.WaitingRoom.WaitingRoom;

import java.io.IOException;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

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
                    case "R":
                        username = scanner.next();
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
                        System.out.println(message + username);
                        break;
                    case "A":
                        message = scanner.next();
                        Actions a = Unpacker.ACT_fromPacket(username);
                        //a.doAction();
                        break;
                    default:
                        break;
                }
            }catch(NoSuchElementException e){
                System.out.println("[!] Network problem "+username+" client unreachable" );
                break;
            }
        }
    }
}
