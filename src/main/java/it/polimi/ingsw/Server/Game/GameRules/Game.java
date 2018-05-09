package it.polimi.ingsw.Server.Game.GameRules;

import java.util.ArrayList;

public class Game {

    private ArrayList<Player> players;
    private GameContext gameContext;

    public Game(){
        players = new ArrayList<>();
        //gameContext = new GameContext();
    }

    public void addPlayer(ArrayList<Player> playersToAdd){
        players.addAll(playersToAdd);
    }
}
