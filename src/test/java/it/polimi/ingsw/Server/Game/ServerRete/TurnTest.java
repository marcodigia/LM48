package it.polimi.ingsw.Server.Game.ServerRete;

import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import it.polimi.ingsw.Server.Game.GameRules.Player;
import org.junit.jupiter.api.BeforeEach;

import java.util.LinkedHashMap;

public class TurnTest {Player p0 = new Player("fabio", null);


    GameStatus gameStatus;
    LinkedHashMap<Player,Boolean> players;
    Turn turn;

    @BeforeEach
    void setUp(){

        Player p0 = new Player("fabio", null);
        Player p1 = new Player("lucia", null);
        Player p2 = new Player("stefano", null);
        Player p3 = new Player("simona", null);

        players.put(p0, false);
        players.put(p1, false);
        players.put(p2, false);
        players.put(p3, false);
    }
}
