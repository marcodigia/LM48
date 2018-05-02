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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class MoveTwoDiceTest {

    DiceBag diceBag = new DiceBag();
    DraftPool draftPool = new DraftPool(diceBag);
    Actions moveTwoDiceAction;
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

    //Move two Dices in two legal position
    @Test
    void doAction() {
        gameSetUp.getWindowPatternCard().placeDice(new Dice(DiceColor.BLUE, "1"), 0, true, true, true);
        gameSetUp.getWindowPatternCard().placeDice(new Dice(DiceColor.BLUE, "2"), 2, true, true, true);

        //gameSetUp.getWindowPatternCard().placeDice(new Dice(DiceColor.BLUE, "1"), 5, true, true, true);
        gameSetUp.getWindowPatternCard().placeDice(new Dice(DiceColor.BLUE, "2"), 6, true, true, true);

        assertNotNull(gameSetUp.getWindowPatternCard().getDice(0));
        moveTwoDiceAction = new MoveTwoDice(0, 12, 2, 10, gameSetUp);
        assertNotNull(gameSetUp.getWindowPatternCard().getDice(0));
        assertNotNull(gameSetUp.getWindowPatternCard().getDice(2));
        moveTwoDiceAction.doAction();
        assertNull(gameSetUp.getWindowPatternCard().getDice(0));
        assertNull(gameSetUp.getWindowPatternCard().getDice(2));
    }

    //Move same Dice in Two different Positions
    @Test
    void doAction2() {
        gameSetUp.getWindowPatternCard().placeDice(new Dice(DiceColor.BLUE, "1"), 0, true, true, true);
        gameSetUp.getWindowPatternCard().placeDice(new Dice(DiceColor.BLUE, "2"), 2, true, true, true);

        //gameSetUp.getWindowPatternCard().placeDice(new Dice(DiceColor.BLUE, "1"), 5, true, true, true);
        gameSetUp.getWindowPatternCard().placeDice(new Dice(DiceColor.BLUE, "2"), 6, true, true, true);

        assertNotNull(gameSetUp.getWindowPatternCard().getDice(0));
        moveTwoDiceAction = new MoveTwoDice(0, 12, 0, 10, gameSetUp);
        moveTwoDiceAction.doAction();
        assertNotNull(gameSetUp.getWindowPatternCard().getDice(0));
        assertNull(gameSetUp.getWindowPatternCard().getDice(12));
        assertNull(gameSetUp.getWindowPatternCard().getDice(10));
    }

    //Move two dice in the same position
    @Test
    void doAction3() {
        gameSetUp.getWindowPatternCard().placeDice(new Dice(DiceColor.BLUE, "1"), 0, true, true, true);
        gameSetUp.getWindowPatternCard().placeDice(new Dice(DiceColor.BLUE, "2"), 2, true, true, true);

        //gameSetUp.getWindowPatternCard().placeDice(new Dice(DiceColor.BLUE, "1"), 5, true, true, true);
        gameSetUp.getWindowPatternCard().placeDice(new Dice(DiceColor.BLUE, "2"), 6, true, true, true);

        assertNotNull(gameSetUp.getWindowPatternCard().getDice(0));
        moveTwoDiceAction = new MoveTwoDice(0, 10, 2, 10, gameSetUp);
        assertNotNull(gameSetUp.getWindowPatternCard().getDice(0));
        assertNotNull(gameSetUp.getWindowPatternCard().getDice(2));
        moveTwoDiceAction.doAction();
        assertNotNull(gameSetUp.getWindowPatternCard().getDice(0));
        assertNotNull(gameSetUp.getWindowPatternCard().getDice(2));
        assertNull(gameSetUp.getWindowPatternCard().getDice(10));
    }

    //Move two Dices one in legal position the other one in an illegal position
    @Test
    void doAction4() {
        gameSetUp.getWindowPatternCard().placeDice(new Dice(DiceColor.BLUE, "1"), 0, true, true, true);
        gameSetUp.getWindowPatternCard().placeDice(new Dice(DiceColor.RED, "2"), 2, true, true, true);

        //gameSetUp.getWindowPatternCard().placeDice(new Dice(DiceColor.BLUE, "1"), 5, true, true, true);
        gameSetUp.getWindowPatternCard().placeDice(new Dice(DiceColor.BLUE, "2"), 6, true, true, true);

        assertNotNull(gameSetUp.getWindowPatternCard().getDice(0));
        moveTwoDiceAction = new MoveTwoDice(0, 12, 2, 19, gameSetUp);
        assertNotNull(gameSetUp.getWindowPatternCard().getDice(0));
        assertNotNull(gameSetUp.getWindowPatternCard().getDice(2));
        moveTwoDiceAction.doAction();
        assertNotNull(gameSetUp.getWindowPatternCard().getDice(0));
        assertNotNull(gameSetUp.getWindowPatternCard().getDice(2));
        assertNull(gameSetUp.getWindowPatternCard().getDice(12));
        assertNull(gameSetUp.getWindowPatternCard().getDice(19));

    }


}