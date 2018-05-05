package it.polimi.ingsw.Server.Game.Components;

import it.polimi.ingsw.Server.Game.Utility.DiceColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DiceTest {
    private Dice dice;
    private Dice dice2;

    @BeforeEach
    void setup(){
        dice = new Dice(DiceColor.YELLOW,"2");
        dice2 = new Dice(DiceColor.YELLOW, "3");
    }

    @Test
    void copyDice(){
        dice2 = dice.cloneIt();
        assertEquals(dice.toString(),dice2.toString());
        assertNotSame(dice,dice2);
    }
    //Sometimes this test can fail due to random nature of reroll() method
    @RepeatedTest(2)
    void reRollDice(){
        dice2.reroll();
        while (dice.toString().equals(dice2.toString()))
            dice2.reroll();
        assertNotEquals(dice.toString(),dice2.toString());
    }
    @Test
    void equalDices(){
        dice2 = dice.cloneIt();
        assertTrue(dice2.equals(dice));
    }
}
