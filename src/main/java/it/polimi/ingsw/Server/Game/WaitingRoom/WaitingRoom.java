package it.polimi.ingsw.Server.Game.WaitingRoom;

import it.polimi.ingsw.ClientServerCommonInterface.ServerClientSender;
import it.polimi.ingsw.Server.Game.GameRules.Player;
import it.polimi.ingsw.Server.Game.ServerRete.ServerRete;
import it.polimi.ingsw.Server.Game.TimerUtility.TimerUtility;

import java.io.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

//Al momento fatta per la singola partita
public class WaitingRoom {

    private ArrayList<Player> clientList = new ArrayList<>();
    private ServerRete serverRete;
    private Timer timer = null;
    private TimerUtility timerUtility;

    public void setServerRete(ServerRete serverRete){
        this.serverRete = serverRete;
    }

    public synchronized  boolean addClient(String username, ServerClientSender clientRef){
        if(scanForSameUsername(username)){
            try {
                clientRef.sendMessage("Nome utente già in uso");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            return false;
        }
        //Crea giocatore
        Player newPlayer = new Player(username,clientRef);
        clientList.add(newPlayer);
        System.out.println(username);
        waitForGame();
        return true;
    }
    //True if there is another player with same name
    //False if there is not  a player with same name
    private boolean scanForSameUsername(String username){
        Player playerSameUsername = null;
        if(clientList.size()>0){
            for(Player p : clientList)
                if(p.getName().equals(username))
                    playerSameUsername = p;
            if(playerSameUsername!=null)
                return true;
        }
        return false;
    }
    //Remove client form ClientList if game is not start.
    public synchronized  boolean removeClient(String username){
        Player playerToRemove = null;
        //Cerca tra i client in attesa di giocare
        for(Player p : clientList)
            if(p.getName().equals(username))
                 playerToRemove = p;
        //Se trovi il player resetta il timer
        if(playerToRemove!=null) {
            clientList.remove(playerToRemove);
            waitForGame();
            return true;
        }

        return false;
    }

    private void waitForGame(){
        if(clientList.size()>=2){
            if(clientList.size() == 4){
                //TODO classe partita già implementata???
                //TODO bloccare ulteriri utenti se patrtita in corso ma permettere riconnessioni
            }
            else{
                if(timer==null) {
                    timer = new Timer();
                    timerUtility = new TimerUtility();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            System.out.println("inizia una nuova partita");
                            serverRete.createNewGame(clientList);
                            clientList.clear();
                        }
                    }, timerUtility.readTimerFromFile(5,"timer.txt"));
                }
            }
        }
        else{
            if(timer!=null){
             timer.cancel();
             timer = null;
            }
        }
    }
}
