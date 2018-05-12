package it.polimi.ingsw.Server.Game.Components.Boards;

import it.polimi.ingsw.Server.Game.GameRules.Player;
import it.polimi.ingsw.Server.Game.GameRules.PlayerUtility.PlayerColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Hashtable;

import static org.junit.jupiter.api.Assertions.*;

class BoardRoundTest {
    private ArrayList<Player> players;
    private BoardRound boardRound;

    @BeforeEach
    void setUp() {

        players = new ArrayList<Player>();
        players.add(new Player( "mario",null));
        players.add(new Player("mauro",null));
        players.add(new Player("nanni" , null));

        boardRound = new BoardRound(players);
    }

    @Test
    void updateScore() {

        assertTrue(boardRound.updateScore(players.get(0), 2));
        assertTrue(boardRound.updateScore(players.get(1), 4));
        assertEquals(boardRound.getScore(players.get(0)), new Integer(2));
        assertEquals(boardRound.getScore(players.get(1)), new Integer(4));
    }

    @Test
    void getWinnerSingleWinner() {
        boardRound.updateScore(players.get(1), 4);
        boardRound.updateScore(players.get(0), 1);
        ArrayList<Player> aspectedWinners = new ArrayList<Player>();
        aspectedWinners.add(players.get(1));
        assertEquals(boardRound.getWinners(), aspectedWinners);
    }

    @Test
    void getWinnerManyWinner() {
        boardRound.updateScore(players.get(1), 4);
        boardRound.updateScore(players.get(2), 4);
        boardRound.updateScore(players.get(0), 1);
        ArrayList<Player> aspectedWinners = new ArrayList<Player>();
        aspectedWinners.add(players.get(1));
        aspectedWinners.add(players.get(2));
        assertEquals(boardRound.getWinners().size(), aspectedWinners.size());
        for (Player player : boardRound.getWinners()) {
            boolean flag = false;
            for (Player player2 : aspectedWinners) {
                if (player.equals(player2))
                    flag = true;
            }
            assertTrue(flag);
        }

    }


    @Test
    void getScoreBoardEmpty() {
        Hashtable<Player, Integer> scoreborard = boardRound.getScoreboard();
        for (Player p : scoreborard.keySet()) {
            assertTrue(players.contains(p));
            assertEquals(boardRound.getScore(p), new Integer(0));
        }
        assertEquals(scoreborard.keySet().size(), players.size());

    }

    @Test
    void getScoreBoardFull() {
        Hashtable<Player, Integer> scoreborard = boardRound.getScoreboard();
        for (Player p : scoreborard.keySet()) {
            boardRound.updateScore(p, 2);
        }
        for (Player p : scoreborard.keySet()) {
            assertTrue(players.contains(p));
            assertEquals(boardRound.getScore(p), new Integer(2));
        }
        assertEquals(scoreborard.keySet().size(), players.size());

    }


}