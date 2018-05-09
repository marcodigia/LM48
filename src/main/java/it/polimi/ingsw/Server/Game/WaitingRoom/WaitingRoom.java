package it.polimi.ingsw.Server.Game.WaitingRoom;

import it.polimi.ingsw.ClientServerCommonInterface.ServerClientSender;
import it.polimi.ingsw.Server.Game.GameRules.Player;
import it.polimi.ingsw.Server.Game.ServerRete.ServerRete;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class WaitingRoom {

    public static ArrayList<Player> clientList = new ArrayList<>();
    private static Timer timer = null;

    //TODO check if username already exists
    public synchronized static boolean addClient(String username, ServerClientSender clientRef){
        Player newPlayer = new Player(username,clientRef);
        if(!clientList.contains(newPlayer)){
            clientList.add(newPlayer);

            return true;
        }
        return false;
    }

    private void waitForGame(){
        if(clientList.size()>=2){
            if(clientList.size() == 4){
                //TODO classe partita già implementata???
            }
            else{
                //TODO take timer from file
                if(timer==null)
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            ServerRete.createNewGame();
                        }
                    }, 1*10*1000);
            }
        }
    }

}
