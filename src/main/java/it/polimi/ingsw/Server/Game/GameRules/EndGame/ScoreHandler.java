package it.polimi.ingsw.Server.Game.GameRules.EndGame;

import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import it.polimi.ingsw.Server.Game.GameRules.Player;

import java.util.ArrayList;
import java.util.Hashtable;

public class ScoreHandler {

    GameStatus gameStatus;
    public ScoreHandler(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public Hashtable<Player,Integer> getFinalScore(){

        return null;
    }
}
