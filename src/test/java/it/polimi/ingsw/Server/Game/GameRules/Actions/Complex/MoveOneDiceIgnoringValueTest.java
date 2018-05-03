package it.polimi.ingsw.Server.Game.GameRules.Actions.Complex;

import it.polimi.ingsw.Server.Game.Cards.Drawable;
import it.polimi.ingsw.Server.Game.Cards.WindowPatternCard;
import it.polimi.ingsw.Server.Game.Cards.WindowPatternCardFactory;
import it.polimi.ingsw.Server.Game.Components.Boards.DraftPool;
import it.polimi.ingsw.Server.Game.Components.Dice;
import it.polimi.ingsw.Server.Game.Components.DiceBag;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Actions;
import it.polimi.ingsw.Server.Game.GameRules.GameContext;
import it.polimi.ingsw.Server.Game.Utility.DiceColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.Hashtable;

import static org.junit.jupiter.api.Assertions.*;

class MoveOneDiceIgnoringValueTest {

    DiceBag diceBag = new DiceBag();
    DraftPool draftPool = new DraftPool(diceBag);
    Actions moveOneDiceAction;
    GameContext gameContext;

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
        gameContext = new GameContext(draftPool, diceBag, null, windowPatternCard);
    }

    @Test
    void doAction() {
        gameContext.getWindowPatternCard().placeDice(new Dice(DiceColor.RED, "2"), 0, true, true, true);
        gameContext.getWindowPatternCard().placeDice(new Dice(DiceColor.BLUE, "2"), 6, true, true, true);

        assertNotNull(gameContext.getWindowPatternCard().getDice(0));
        moveOneDiceAction = new MoveOneDiceIgnoringValue(gameContext, 0, 1);
        assertNull(gameContext.getWindowPatternCard().getDice(1));
        //assertTrue(gameContext.getWindowPatternCard().moveDice(0,1,true,false,false));
        moveOneDiceAction.doAction();
        assertNull(gameContext.getWindowPatternCard().getDice(0));
        assertNotNull(gameContext.getWindowPatternCard().getDice(1));
    }

    @Test
    void doAction2() {
        gameContext.getWindowPatternCard().placeDice(new Dice(DiceColor.RED, "4"), 0, true, true, true);
        gameContext.getWindowPatternCard().placeDice(new Dice(DiceColor.YELLOW, "4"), 13, true, true, true);

        assertNotNull(gameContext.getWindowPatternCard().getDice(0));
        moveOneDiceAction = new MoveOneDiceIgnoringValue(gameContext, 0, 19);
        assertNull(gameContext.getWindowPatternCard().getDice(19));
        assertTrue(gameContext.getWindowPatternCard().moveDice(0, 19, true, false, false));

        assertNull(gameContext.getWindowPatternCard().getDice(0));
        assertNotNull(gameContext.getWindowPatternCard().getDice(19));
    }

}