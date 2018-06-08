package it.polimi.ingsw.Client.ClientSocket;

import it.polimi.ingsw.ClientServerCommonInterface.ClientServerReciver;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Actions;
import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import it.polimi.ingsw.Server.Game.Utility.ANSI_COLOR;
import it.polimi.ingsw.Server.Game.Utility.Unpacker;
import it.polimi.ingsw.UI;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ClientServerReciverImp implements Runnable, ClientServerReciver {

    private Socket socket;
    private Scanner scanner;
    private String message;
    private String[] id = new String[4];
    private UI ui;
    private boolean connect = true;

    public ClientServerReciverImp(Socket socket){
        this.socket = socket;
        try {
            scanner = new Scanner(socket.getInputStream()).useDelimiter("\\s*£00£\\s*");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void run() {

        while(connect && true){
            try{
                String command = scanner.next();
                switch(command){
                    case "PING":
                        ui.pingBack();
                        break;
                    case "S":
                        message = scanner.next();
                        ui.printMessage(message);
                        break;
                    case "CW":
                        for(int i=0;i<4;i++){
                            id[i] = scanner.next();
                        }
                        ui.chooseWP(id[0],id[1],id[2],id[3]);
                        break;
                    case "TS":
                        ui.activate();
                        break;
                    case "TE":
                        ui.disable();
                        break;
                    case "SGS":
                        message = scanner.next();
                        GameStatus gameStatus = Unpacker.getGameStatus(message);
                        ui.updateGameStatus(gameStatus);
                        break;
                    case "ALL":
                        message = scanner.next();
                        ui.allCurrentPlayers(message);
                        break;
                    default:
                        break;
                }
            }catch(NoSuchElementException e){

            }
        }
    }

    @Override
    public void setUI(UI ui) {
        this.ui = ui;
    }

    @Override
    public void close() throws RemoteException {
        connect = false;
        scanner.close();
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
