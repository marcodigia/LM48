package it.polimi.ingsw.Server.Game.Components;

import it.polimi.ingsw.Server.Game.Cards.WindowPatternCard;
import it.polimi.ingsw.Server.Game.Utility.DiceColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class WindowPatternCardTest {

    private WindowPatternCard windowPatternCard;

    @BeforeEach
    void setUp(){
        ArrayList<String> pattern = new ArrayList<String>();
        pattern.add("2");
        pattern.add("Via Lux");
        pattern.add("4");
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
        windowPatternCard = new WindowPatternCard(pattern);
    }
    /*Verify if it's possible to place a dice in a cell that do not permit that dice*/
    @Test
    void placeDiceOnCellRestriction(){
        Dice dado = new Dice(DiceColor.RED,"6");
        assertFalse(windowPatternCard.placeDice(dado,19,false,false));
    }
}
