package it.polimi.ingsw.Server.Game.Components.Boards;

import it.polimi.ingsw.Server.Game.PlayerUtility.Player;
import it.polimi.ingsw.Server.Game.PlayerUtility.PlayerColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BoardRoundTest {
    private ArrayList<Player> players;
    private BoardRound boardRound;

    @BeforeEach
    void setUp() {

        players = new ArrayList<Player>();
        players.add(new Player(PlayerColor.PLAYER_BLUE, "mario"));
        players.add(new Player(PlayerColor.PLAYER_BLUE, "mauro"));
        players.add(new Player(PlayerColor.PLAYER_BLUE, "nanni"));

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
        assertEquals(boardRound.getWinners(), aspectedWinners);
    }
}