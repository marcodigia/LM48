package it.polimi.ingsw.Server.Game.ServerRete;

import it.polimi.ingsw.Server.Game.GameRules.Game;
import it.polimi.ingsw.Server.Game.GameRules.Player;
import it.polimi.ingsw.Server.Game.TimerUtility.TimerUtility;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ServerRete {

    private Game game;
    private Timer timer;
    private TimerUtility timerUtility;

    public void createNewGame(ArrayList<Player> playerToAdd){
        timerUtility = new TimerUtility();
        timer = new Timer();
        game = new Game(playerToAdd);
        serverAskClientForWindow();
    }

    public synchronized void setWindowPattern(String idWp, String username){
        game.setWindowToPlayer(idWp, username);
    }

    private void serverAskClientForWindow(){
        game.sendWindowPatternToChoose();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                game.deleteWhoLeftGame();
                game.lookForWinner();
                game.endGameSetUp();    //End setup with players who are still playing

            }
        }, timerUtility.readTimerFromFile(40,"timerDelayPlayer.txt"));
    }

    

    public Game getGame(){return game;}
}
