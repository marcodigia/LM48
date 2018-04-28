package it.polimi.ingsw.Server.Game.Components;

import it.polimi.ingsw.Server.Game.Components.Boards.DraftPool;
import it.polimi.ingsw.Server.Game.Utility.DiceColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class DraftPoolTest {

    private DiceBag diceBag;
    private ArrayList<Dice> dices;
    private ArrayList<Dice> dices2;
    private DraftPool draftPool;

    @BeforeEach
    void setup(){
        diceBag = new DiceBag();
        draftPool = new DraftPool(diceBag);
    }

    @ParameterizedTest(name="{index}+1 => players")
    @ValueSource(ints = {5,7,9})
    void extractNDices(int n){
        draftPool.extractNdice(n);
        assertEquals(n,draftPool.getDraft().size());
    }

    @Test
    void reRollDices(){
        draftPool.extractNdice(9);
        dices = draftPool.getDraft();
        draftPool.rerollAllDices();
        dices2 = draftPool.getDraft();
        assertIterableEquals(dices,dices2);
    }

    @Test
    void removeDice(){
        draftPool.extractNdice(9);
        dices = draftPool.getDraft();
        assertEquals(9,draftPool.getDraft().size());
        draftPool.removeDice(dices.get(0));
        assertEquals(8,draftPool.getDraft().size());
    }
    @Test
    void addDice(){
        Dice dice = new Dice(DiceColor.YELLOW,"2");
        draftPool.extractNdice(9);
        draftPool.putDice(dice);
        assertTrue(draftPool.getDraft().contains(dice));
    }
}
