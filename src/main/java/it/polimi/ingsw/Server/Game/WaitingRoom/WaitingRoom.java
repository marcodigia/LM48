package it.polimi.ingsw.Server.Game.WaitingRoom;

import it.polimi.ingsw.ClientServerCommonInterface.ServerClientSender;
import it.polimi.ingsw.Server.Game.ServerRete.Game;
import it.polimi.ingsw.Server.Game.GameRules.Player;
import it.polimi.ingsw.Server.Game.ServerSocket.ServerClientSenderImp;
import it.polimi.ingsw.Server.Game.TimerUtility.TimerUtility;
import it.polimi.ingsw.Server.Game.Utility.CONSTANT;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

//Al momento fatta per la singola partita
public class WaitingRoom {

    private ArrayList<Player> clientList = new ArrayList<>();
    private Game game;
    private Timer timer = null;
    private TimerUtility timerUtility;
    private boolean noMorePlayerAccepted = false;

    //This method should be called at the end of a game in order to let
    //other player play a new game.
    public void setNoMorePlayerAccepted(){this.noMorePlayerAccepted = false;}

    public void setGame(Game game){ this.game = game;}

    public synchronized  boolean addClient(String username, ServerClientSender clientRef){
        if(!noMorePlayerAccepted){
            if(scanForSameUsername(username) != null){
                try {
                    System.out.println("Username is already used");
                    clientRef.sendMessage(CONSTANT.usernameAlreadyUsed);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                return false;
            }
            System.out.println("Waiting room: " + username);
            //Create new player
            Player newPlayer = new Player(username,clientRef);
            clientList.add(newPlayer);
            if(clientRef instanceof ServerClientSenderImp)
                System.out.println("Connect Socket: " + username);
            else {
                System.out.println("Connect RMI: " + username);
            }
            try {
                clientRef.sendMessage(CONSTANT.correctUsername);
                ArrayList<String> names = new ArrayList<String>();
                for(Player p : clientList)
                    names.add(p.getName());
                for(Player p : clientList) {
                    p.getvirtualView().sendCurrentPlayers(names);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            waitForGame();
            return true;
        }
        try {
            clientRef.sendMessage("Your connection have been refused");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        System.out.println("Game has already been started");
        return false;
    }
    //True if there is another player with same name
    //False if there is not  a player with same name
    public Player scanForSameUsername(String username){
        Player playerSameUsername = null;
        if(clientList.size()>0){
            for(Player p : clientList)
                if(p.getName().equals(username))
                    playerSameUsername = p;
        }
        return playerSameUsername;
    }
    //Remove client form ClientList if game is not start. This method will be called
    //also if game is started but will not have effect because clientList will be empty
    public synchronized  boolean removeClient(String username){
        Player playerToRemove = null;
        if(clientList.size()>0){
            //Look for player in clientList
            for(Player p : clientList)
                if(p.getName().equals(username))
                    playerToRemove = p;
            //If you find the player query to restart or not timer
            if(playerToRemove!=null) {
                clientList.remove(playerToRemove);
                waitForGame();
                System.out.println("Disconnect : "+playerToRemove.getName());
                playerToRemove.getvirtualView().sendMessage("You have been removed by this game.\n" +
                        "You will not be able to play until a new game starts.");
                return true;
            }

        }
        return false;
    }

    private void waitForGame(){
        if(clientList.size()>=2){
            if(clientList.size() == 4){
                timer.cancel();
                System.out.println("Start new game");
                game.createNewGame(clientList);
                clientList.clear();
                noMorePlayerAccepted = true;
            }
            else{
                if(timer==null) {
                    timer = new Timer();
                    timerUtility = new TimerUtility();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            System.out.println("Start new game");
                            game.createNewGame(clientList);
                            clientList.clear();
                            noMorePlayerAccepted = true;
                        }
                    }, timerUtility.readTimerFromFile(10,"timer.txt"));
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
