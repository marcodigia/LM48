package it.polimi.ingsw.Server.Game.ServerRete;

import it.polimi.ingsw.Server.Game.GameRules.Game;
import it.polimi.ingsw.Server.Game.GameRules.Player;
import it.polimi.ingsw.Server.View.VirtualView;

import java.util.ArrayList;

public class ServerRete {

    //TODO Multipartita???
    //private ArrayList<Game> currentGames = new ArrayList<>();
    private Game game;

    public void createNewGame(ArrayList<Player> playerToAdd){
        game = new Game();
        game.addPlayer(playerToAdd);
        //currentGames.add(game);
    }

    public boolean setPlayerSilent(Player playerToDisconnect){
        return game.setPlayerToDisconnect(playerToDisconnect);
    }

    public boolean scanForPlayer(Player playerToFind){
        return game.scanForPlayer(playerToFind);
    }


}
