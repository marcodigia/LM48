package it.polimi.ingsw.Server.Game.GameRules.Actions.Complex;

import it.polimi.ingsw.Server.Game.Cards.Drawable;
import it.polimi.ingsw.Server.Game.Cards.WindowPatternCard;
import it.polimi.ingsw.Server.Game.Cards.WindowPatternCardFactory;
import it.polimi.ingsw.Server.Game.Components.Boards.DraftPool;
import it.polimi.ingsw.Server.Game.Components.Dice;
import it.polimi.ingsw.Server.Game.Components.DiceBag;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Actions;
import it.polimi.ingsw.Server.Game.GameRules.GameSetUp;
import it.polimi.ingsw.Server.Game.Utility.DiceColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.Hashtable;

import static org.junit.jupiter.api.Assertions.*;

class MoveOneDieIgnoringColorTest {

    DiceBag diceBag = new DiceBag();
    DraftPool draftPool = new DraftPool(diceBag);
    Actions moveOneDiceAction;
    GameSetUp gameSetUp;

    @BeforeEach
    void setUp() {
        WindowPatternCardFactory factory = new WindowPatternCardFactory("windowPatternCards.csv");
        WindowPatternCard windowPatternCard = null;

        try {

            Hashtable<String, Drawable> deck = factory.getNewCardDeck();

            windowPatternCard = (WindowPatternCard) deck.get("25");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        draftPool.extractNdice(2);
        gameSetUp = new GameSetUp(draftPool, diceBag, null, windowPatternCard);
    }

    @Test
    void doAction() {
        gameSetUp.getWindowPatternCard().placeDice(new Dice(DiceColor.RED, "1"), 0, true, true, true);
        gameSetUp.getWindowPatternCard().placeDice(new Dice(DiceColor.RED, "2"), 6, true, true, true);

        assertNotNull(gameSetUp.getWindowPatternCard().getDice(0));
        moveOneDiceAction = new MoveOneDieIgnoringColor(gameSetUp, 0, 1);
        assertNull(gameSetUp.getWindowPatternCard().getDice(1));
        //assertTrue(gameSetUp.getWindowPatternCard().moveDice(0,1,true,false,false));
        moveOneDiceAction.doAction();
        assertNull(gameSetUp.getWindowPatternCard().getDice(0));
        assertNotNull(gameSetUp.getWindowPatternCard().getDice(1));
    }

    @Test
    void doAction2() {
        gameSetUp.getWindowPatternCard().placeDice(new Dice(DiceColor.RED, "4"), 0, true, true, true);
        gameSetUp.getWindowPatternCard().placeDice(gameSetUp.getDraftPool().getDice(1), 13, true, true, true);

        assertNotNull(gameSetUp.getWindowPatternCard().getDice(0));
        moveOneDiceAction = new MoveOneDieIgnoringColor(gameSetUp, 0, 19);
        assertNull(gameSetUp.getWindowPatternCard().getDice(19));
        assertTrue(gameSetUp.getWindowPatternCard().moveDice(0, 19, true, false, false));

        assertNull(gameSetUp.getWindowPatternCard().getDice(0));
        assertNotNull(gameSetUp.getWindowPatternCard().getDice(19));
    }
}