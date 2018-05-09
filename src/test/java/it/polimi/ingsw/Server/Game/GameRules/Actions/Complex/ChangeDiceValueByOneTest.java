package it.polimi.ingsw.Server.Game.GameRules.Actions.Complex;

import it.polimi.ingsw.Client.View.UI_SIMULATION;
import it.polimi.ingsw.Server.Game.Components.Boards.DraftPool;
import it.polimi.ingsw.Server.Game.Components.DiceBag;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Actions;
import it.polimi.ingsw.Server.Game.GameRules.GameContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChangeDiceValueByOneTest {

    DiceBag diceBag = new DiceBag();
    DraftPool draftPool = new DraftPool(diceBag);
    Actions increaseDiceAction;
    GameContext gameContext;

    @BeforeEach
    void setUp() {

        draftPool.extractNdice(7);
        gameContext = new GameContext(draftPool, diceBag, null, null);

    }


    //Test increase Value
    @ParameterizedTest(name = " dice at index: {index} of draftpool")
    @ValueSource(ints = {1, 2, 3, 4, 5, 6})
    void doAction(int n) {
        increaseDiceAction = new ChangeDiceValueByOne();
        draftPool.getDice(0).setValue(n);

        if (n == 6) {

            assertEquals(Integer.toString(n), draftPool.getDice(0).getValue());
            //Done Client Side
            increaseDiceAction.useAction(new UI_SIMULATION(1, 0, 0, 0, 0), null);

            //Done Server Side
            increaseDiceAction.doAction(gameContext);
            assertEquals(Integer.toString(n), draftPool.getDice(0).getValue());
            return;
        }
        assertEquals(Integer.toString(n), draftPool.getDice(0).getValue());
        //Done Client Side
        increaseDiceAction.useAction(new UI_SIMULATION(1, 0, 0, 0, 0), null);

        //Done Server Side
        increaseDiceAction.doAction(gameContext);
        assertEquals(Integer.toString(n + 1), draftPool.getDice(0).getValue());

    }


    //Test decrease Value
    @ParameterizedTest(name = " dice at index: {index} of draftpool")
    @ValueSource(ints = {1, 2, 3, 4, 5, 6})
    void doAction2(int n) {
        increaseDiceAction = new ChangeDiceValueByOne();
        draftPool.getDice(0).setValue(n);


        if (n == 1) {
            assertEquals(Integer.toString(n), draftPool.getDice(0).getValue());
            //Done Client Side
            increaseDiceAction.useAction(new UI_SIMULATION(-1, 0, 0, 0, 0), null);

            //Done Server Side
            increaseDiceAction.doAction(gameContext);
            assertEquals(Integer.toString(n), draftPool.getDice(0).getValue());
            return;
        }
        assertEquals(Integer.toString(n), draftPool.getDice(0).getValue());
        //Done Client Side
        increaseDiceAction.useAction(new UI_SIMULATION(-1, 0, 0, 0, 0), null);

        //Done Server Side
        increaseDiceAction.doAction(gameContext);
        assertEquals(Integer.toString(n - 1), draftPool.getDice(0).getValue());


    }
}