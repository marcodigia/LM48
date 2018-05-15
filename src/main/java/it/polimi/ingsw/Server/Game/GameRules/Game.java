package it.polimi.ingsw.Server.Game.GameRules;


import it.polimi.ingsw.Server.Game.Cards.*;
import it.polimi.ingsw.Server.Game.Components.Boards.BoardRound;
import it.polimi.ingsw.Server.Game.Components.Boards.DraftPool;
import it.polimi.ingsw.Server.Game.Components.DiceBag;
import it.polimi.ingsw.Server.Game.Utility.ANSI_COLOR;
import it.polimi.ingsw.Server.Game.Utility.CONSTANT;

import java.awt.*;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;
import java.util.Set;

public class Game {

    private Hashtable<Player ,Boolean> players; // True -> associated player turn False -> otherwise
    private GameStatus gameStatus;
    private GameSetup gameSetup;



    public Game(ArrayList<Player> playerToAdd){
        players = new Hashtable<>();
        addPlayer(playerToAdd);
        gameSetup = new GameSetup(players);
    }
    //Aggiungi players alla partita
    private void addPlayer(ArrayList<Player> playersToAdd){
        String s = "Hello";
        if(playersToAdd!=null)
            for(Player p : playersToAdd){
                players.put(p, false);
                p.getvirtualView().sendMessage(s);
            }
    }

    public void setWindowToPlayer(String idWp){
        for(Player p : players.keySet())
            if(players.get(p)){
            
            }

    }

    public void sendWindowPatternToChoose(){
        ArrayList<WindowPatternCard> wp = new ArrayList<>(gameSetup.getWindowPatternCards());
        Set<Player> playerToWP;
        playerToWP = players.keySet();
        int i=0;
        for(Player p : playerToWP){
            String id1, id2 , id3, id4;
            id1 = wp.remove(0).getID();
            id2 = wp.remove(0).getID();
            id3 = wp.remove(0).getID();
            id4 = wp.remove(0).getID();
            System.out.println(id1+id2+id3+id4);
            System.out.println(wp.size());
            p.getvirtualView().chooseWindowPattern(id1,id2,id3,id4);
        }
    }


}
