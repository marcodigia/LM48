package it.polimi.ingsw.Server.Game.Components;

import it.polimi.ingsw.Server.Game.Cards.CardsComponents.Matrix;
import it.polimi.ingsw.Server.Game.Utility.DiceColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MatrixTest {

    private Matrix matrix;
    private ArrayList<String> pattern;
    private Dice dice;

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

        dice = new Dice(DiceColor.YELLOW,"2");
    }

    //@Test
    void placeWrongDice(){
        assertFalse(matrix.setDice(dice,0,false,false,false));
    }

    @Test
    void placeCorrectDice(){
        assertTrue(matrix.setDice(dice, 3,false,false,false));
    }

    @Test
    void removeDice(){
        assertTrue(matrix.setDice(dice, 3,false,false,false));
        assertTrue(matrix.removeDice(3));
    }
}
