package it.polimi.ingsw.Server.Game.GameRules;


import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Hashtable;

public class Game {

    private Hashtable<Player ,Boolean> players;
    private GameContext gameContext;

    public Game(){
        players = new Hashtable<>();
        //gameContext = new GameContext();
    }
    //Aggiungi players alla partita
    public void addPlayer(ArrayList<Player> playersToAdd){
        String s = "Hello";
        if(playersToAdd!=null)
            for(Player p : playersToAdd){
                players.put(p, true);
                try {
                    p.getServerClientSender().sendMessage(s);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

    }
    public boolean scanForPlayer(Player playerToFind){
        return players.containsKey(playerToFind);
    }
    //Metti a false la partecipazione alla partita del player che si è disconesso
    public boolean setPlayerToDisconnect(Player playerToDisconnect){
        if(scanForPlayer(playerToDisconnect)) {
            players.replace(playerToDisconnect, false);
            return true;
        }
        return false;
    }
}
