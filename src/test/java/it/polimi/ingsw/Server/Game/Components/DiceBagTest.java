package it.polimi.ingsw.Server.Game.Components;

import it.polimi.ingsw.Server.Game.Utility.DiceColor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DiceBagTest {

    private DiceBag diceBag;
    private DiceBag diceBagSecond;

    @BeforeEach
    void setUp() {
        diceBag = new DiceBag();
        diceBagSecond = new DiceBag();
    }

    @Test
    void allDicesBetweenOneSix(){
        ArrayList<Dice> result = diceBag.getNdices(90);
        for(Dice d : result){
            assertTrue(6>=Integer.parseInt(d.getValue()),"Error, random is too high");
            assertTrue(1<=Integer.parseInt(d.getValue()),"Error, random is too low");
        }
    }

    /*Giusto che fallisca voglio verificare se è necessario cambiare
    il seed per avvere set di dadi diversi*/

    @Test
    void twoDiceBagTwoSet(){
        ArrayList<Dice> result = diceBag.getNdices(90);
        ArrayList<Dice> resultSecond = diceBagSecond.getNdices(90);

        assertIterableEquals(result, resultSecond);
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