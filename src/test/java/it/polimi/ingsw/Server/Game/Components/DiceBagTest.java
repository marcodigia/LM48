package it.polimi.ingsw.Server.Game.Components;

import it.polimi.ingsw.Server.Game.Utility.DiceColor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DiceBagTest {

    private DiceBag diceBag;

    @BeforeEach
    void setUp() {
        diceBag = new DiceBag();
    }

    @Test
    void get0dices() {
        ArrayList<Dice> result = diceBag.getNdices(0);
        assertTrue(result.isEmpty());

    }

    @Test
    void getAlldices() {
        ArrayList<Dice> result = diceBag.getNdices(90);
        assertEquals(90, result.size());

    }

    @Test
    void putDice() {

        assertFalse(diceBag.putDice(new Dice(DiceColor.YELLOW, "1")));
        diceBag.getNdices(1);
        assertTrue(diceBag.putDice(new Dice(DiceColor.YELLOW, "1")));
    }
}