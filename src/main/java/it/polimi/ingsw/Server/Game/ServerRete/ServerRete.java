package it.polimi.ingsw.Server.Game.ServerRete;

import it.polimi.ingsw.Server.Game.GameRules.Game;
import it.polimi.ingsw.Server.Game.GameRules.Player;

import java.util.ArrayList;

public class ServerRete {

    private Game game;

    public void createNewGame(ArrayList<Player> playerToAdd){
        game = new Game(playerToAdd);
        serverAskClientForWindow();
    }

    public void serverAskClientForWindow(){
        game.sendWindowPatternToChoose();
    }
}
