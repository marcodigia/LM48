package it.polimi.ingsw.Server.Game.ServerRete;

import it.polimi.ingsw.Server.Game.GameRules.Game;
import java.util.ArrayList;

public class ServerRete {

    public static ArrayList<Game> currentGames = new ArrayList<>();

    public static void createNewGame(){
        Game game = new Game();
        currentGames.add(game);
    }

}
