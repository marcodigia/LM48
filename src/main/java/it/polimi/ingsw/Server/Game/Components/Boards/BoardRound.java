package it.polimi.ingsw.Server.Game.Components.Boards;

import it.polimi.ingsw.Server.Game.PlayerUtility.Player;

import java.util.ArrayList;
import java.util.Hashtable;

public class BoardRound {

    private Hashtable<Player, Integer> scoreboard;

    public BoardRound(ArrayList<Player> players) {
        scoreboard = new Hashtable<Player, Integer>();
        for (Player p : players)
            scoreboard.put(p, 0);
    }

    public boolean updateScore(Player player, int score) {
        if (scoreboard.containsKey(player)) {
            scoreboard.put(player, score);
            return true;
        }
        return false;
    }

    public ArrayList<Player> getWinners() {
        ArrayList<Player> winners = new ArrayList<Player>();
        for (Player p1 : scoreboard.keySet()) {

            boolean flag = true;
            for (Player p2 : scoreboard.keySet()) {

                if (scoreboard.get(p1) < scoreboard.get(p2))
                    flag = false;

            }
            if (flag)
                winners.add(p1);


        }
        return winners;
    }

    public Hashtable<Player, Integer> getScoreboard() {
        return new Hashtable<Player, Integer>(scoreboard);
    }

    public Integer getScore(Player player) {
        return scoreboard.get(player);
    }


}
