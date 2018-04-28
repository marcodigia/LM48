package it.polimi.ingsw.Server.Game.Cards.CardsComponents;

import it.polimi.ingsw.Server.Game.Components.Dice;
import it.polimi.ingsw.Server.Game.GameRules.Restriction;
import it.polimi.ingsw.Server.Game.Utility.DiceColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CellTest2 {

    Cell cell1 = new Cell();
    Cell cell2 = new Cell();
    Cell cell3 = new Cell();
    Cell cell4 = new Cell();
    Cell cell = new Cell();
    Cell cell6 = new Cell();
    Cell cell7 = new Cell();
    Cell cell8 = new Cell();
    Cell cell9 = new Cell();
    Dice dice1 = new Dice(DiceColor.RED, "2");
    Dice dice2 = new Dice(DiceColor.RED, "3");
    Dice dice3 = new Dice(DiceColor.BLUE, "2");

    @BeforeEach
    void setUP() {

        cell.setCell(cell1, true);
        cell.setCell(cell3, true);
        cell.setCell(cell7, true);
        cell.setCell(cell9, true);
        cell.setCell(cell2, false);
        cell.setCell(cell4, false);
        cell.setCell(cell6, false);
        cell.setCell(cell8, false);
    }

    @Test
    void verifyColorAndValue() {

        cell.setRestriction(Restriction.BLUE);
        assertTrue(cell.verifyColorAndValue(dice1, true, true));
        assertFalse(cell.verifyColorAndValue(dice1, false, false));
        assertTrue((cell.verifyColorAndValue(dice3, false, false)));
        assertTrue(cell.putDice(dice3, false, false, true));
        cell.removeDice();
        assertFalse(cell.putDice(dice1, false, false, true));
        assertTrue(cell.putDice(dice3, false, false, true));
        assertFalse(cell.putDice(dice1, true, true, true));
    }

    @Test
    void verifyAdjacency() {

        cell.setRestriction(Restriction.BLUE);
        cell9.setRestriction(Restriction.NONE);
        assertFalse(cell.putDice(dice3, false, false, false));
        assertFalse(cell.verifyAdjacency(false));
        assertTrue(cell.verifyColorAndValue(dice3, false, false));

        cell1.putDice(dice1, true, true, true);
        assertTrue(cell.putDice(dice3, false, false, false));

        cell1.removeDice();
        cell.removeDice();

        assertTrue(cell1.putDice(dice1, true, true, true));
        assertFalse((cell9.putDice(dice3, true, true, false)));
    }

    @Test
    void isEmpty() {
        cell1.setRestriction(Restriction.NONE);
        assertTrue(cell1.isEmpty());
        cell1.putDice(dice3, true, true, true);
        assertFalse(cell1.isEmpty());
        cell1.removeDice();
        assertTrue(cell1.isEmpty());
    }

}