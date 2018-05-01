package it.polimi.ingsw.Server.Game.Cards.CardsComponents;

import it.polimi.ingsw.Server.Game.Cards.CardsComponents.Matrix;
import it.polimi.ingsw.Server.Game.Components.Dice;
import it.polimi.ingsw.Server.Game.Utility.DiceColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MatrixTest {

    private Matrix matrix;
    private ArrayList<String> pattern;
    private ArrayList<Cell> cells;
    private ArrayList<Dice> dicesone;
    private Dice dice1;
    private Dice dice2;
    private Dice dice3;
    private Dice dice4;
    private Dice dice5;
    private Dice dice6;

    @BeforeEach
    void setup(){

        pattern = new ArrayList<String>();
        pattern.add("Y");   //0
        pattern.add("0");   //1
        pattern.add("6");   //2...
        pattern.add("0");
        pattern.add("0");
        pattern.add("0");
        pattern.add("1");
        pattern.add("5");
        pattern.add("0");
        pattern.add("2");
        pattern.add("3");
        pattern.add("Y");
        pattern.add("R");
        pattern.add("P");
        pattern.add("0");
        pattern.add("0");
        pattern.add("0");
        pattern.add("4");
        pattern.add("3");
        pattern.add("R");   //19

        matrix = new Matrix(5,4);
        matrix.initialize();
        matrix.initialize_restricions(pattern);

        dice1 = new Dice(DiceColor.YELLOW,"2");
        dice2 = new Dice(DiceColor.RED,"6");
        dice3 = new Dice(DiceColor.YELLOW,"4");
        dice4 = new Dice(DiceColor.GREEN,"1");
        dice5 = new Dice(DiceColor.PURPLE,"1");
        dice6 = new Dice(DiceColor.BLUE,"5");

        dicesone = new ArrayList<Dice>();
        cells = new ArrayList<Cell>();

    }

    @Test
    void placeDice(){
        //Place dices where they can be placed
        assertTrue(matrix.setDice(dice2,8,false,false,true));
        assertTrue(matrix.setDice(dice4,3,false,false,false));
        assertTrue(matrix.setDice(dice6,7,false,false,false));
        assertTrue(matrix.setDice(dice1,11,false,false,false));
        //Try to place dice ignoring value restriction
        assertTrue(matrix.setDice(dice3,9,false,true,false));
        assertTrue(matrix.removeDice(9));
        //Try to place dice ignoring color restriction
        assertTrue(matrix.setDice(dice3,16,true,false,false));
        assertTrue(matrix.removeDice(16));
        //Try to place dice ignoring value-color restrction
        assertTrue(matrix.setDice(dice3,6,true,true,false));
        assertTrue(matrix.removeDice(6));
        //Try to place dices where they cannot go for color-value restriction of matrix
        assertFalse(matrix.setDice(dice5,2,false,false,false));
        assertFalse(matrix.setDice(dice3,12,false,false,false));
        //Try to place dices where there are other dices
        assertFalse(matrix.setDice(dice1,8,false,false,false));
        assertFalse(matrix.setDice(dice3,3,false,false,false));
        assertFalse(matrix.setDice(dice5,7,false,false,false));
        //Try to place dices where they cannot go because of color value restrictions of other dices
        assertFalse(matrix.setDice(dice5,4,false,false,false));
    }

    @Test
    void removeDice(){
        //Place dices where they can be placed
        assertTrue(matrix.setDice(dice2,8,false,false,true));
        assertTrue(matrix.setDice(dice4,3,false,false,false));
        assertTrue(matrix.setDice(dice6,7,false,false,false));
        assertTrue(matrix.setDice(dice1,11,false,false,false));
        //Try remove dices that are really in the matrix
        assertTrue(matrix.removeDice(3));
        assertTrue(matrix.removeDice(8));
        assertTrue(matrix.removeDice(7));
        assertTrue(matrix.removeDice(11));
        //After remove dices try to remove them again
        assertFalse(matrix.removeDice(3));
        assertFalse(matrix.removeDice(8));
        assertFalse(matrix.removeDice(7));
        assertFalse(matrix.removeDice(11));
        //Remove no dice
        assertFalse(matrix.removeDice(1));
        //Out of bounds
        assertFalse(matrix.removeDice(20));
        assertFalse(matrix.removeDice(-1));

    }
    @Test
    void getAllDices(){
        //Place dices where they can be placed
        assertTrue(matrix.setDice(dice2,8,false,false,true));
        assertTrue(matrix.setDice(dice4,3,false,false,false));
        assertTrue(matrix.setDice(dice6,7,false,false,false));
        assertTrue(matrix.setDice(dice1,11,false,false,false));
        //Get all dices
        dicesone = matrix.getAllDices();
        assertEquals(4,dicesone.size());
    }
    @Test
    void getDice(){
        //Place dices where they can be placed
        assertTrue(matrix.setDice(dice2,8,false,false,true));
        assertTrue(matrix.setDice(dice4,3,false,false,false));
        assertTrue(matrix.setDice(dice6,7,false,false,false));
        //Get dices that are in the matrix
        assertSame(dice2,matrix.getDice(8));
        assertSame(dice4,matrix.getDice(3));
        assertSame(dice6,matrix.getDice(7));
    }
    @Test
    void moveDice(){
        //Place dices where they can be placed
        assertTrue(matrix.setDice(dice2,8,false,false,true));
        assertTrue(matrix.setDice(dice4,3,false,false,false));
        assertTrue(matrix.setDice(dice6,7,false,false,false));
        assertTrue(matrix.setDice(dice1,11,false,false,false));
        //Move dices
        assertTrue(matrix.moveDice(8,4,false,false,false));
        assertSame(dice2,matrix.getDice(4));
        assertFalse(matrix.moveDice(11,5,false,false,false));
    }
    @Test
    void getRow(){
        //Place dices where they can be placed
        assertTrue(matrix.setDice(dice1,0,false,false,true));
        assertTrue(matrix.setDice(dice4,1,false,false,false));
        assertTrue(matrix.setDice(dice2,2,false,false,false));
        assertTrue(matrix.setDice(dice3,3,false,false,false));
        cells = matrix.getRow(0);
        //Verify cells contains dices
        assertTrue(cells.get(0).getDice().equals(dice1));
        assertTrue(cells.get(1).getDice().equals(dice4));
        assertTrue(cells.get(2).getDice().equals(dice2));
        assertTrue(cells.get(3).getDice().equals(dice3));
    }
    @Test
    void isPlaceable(){
        //Place dice
        assertTrue(matrix.isPlaceable(dice3,0,false,false,true));
        assertTrue(matrix.setDice(dice3,0,false,false,true));
        //Try place dice where it cannot be placed
        assertFalse(matrix.isPlaceable(dice1,15,false,false,false));
        //Place dice considering adjacent restriction
        assertTrue(matrix.isPlaceable(dice2,5,false,false,false));
        assertTrue(matrix.setDice(dice2,5,false,false,false));
        //Place dice next to another dice same color (ignor color)
        assertTrue(matrix.isPlaceable(dice1,1,true,false,false));
        assertTrue(matrix.setDice(dice1,1,true,false,false));
        //Place dice on cell with value restriction (ignor value restriction)
        assertTrue(matrix.isPlaceable(dice6,6,false,true,false));
        assertTrue(matrix.setDice(dice6,6,false,true,false));
        //Place dice next to another dice same value and cell with color restriction
        assertTrue(matrix.isPlaceable(dice4,11,true,true,false));
        assertTrue(matrix.setDice(dice4,11,true,true,false));
    }
}
