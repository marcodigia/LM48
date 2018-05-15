package it.polimi.ingsw.Server.Game.ServerRete;

import it.polimi.ingsw.Server.Game.GameRules.Game;
import it.polimi.ingsw.Server.Game.GameRules.Player;

import java.util.ArrayList;
import java.util.Timer;

public class ServerRete {

    private Game game;
    private Timer timer;
    private int numbersReady = 0;

    public void createNewGame(ArrayList<Player> playerToAdd){
        timer = new Timer();
        numbersReady = playerToAdd.size();
        game = new Game(playerToAdd);
        serverAskClientForWindow();
    }

    public synchronized void setWindowPattern(String idWp){
        game.setWindowToPlayer(idWp);
        numbersReady--;
    }

    public void serverAskClientForWindow(){
        game.sendWindowPatternToChoose();
    }

    public Game getGame(){return game;}
}
