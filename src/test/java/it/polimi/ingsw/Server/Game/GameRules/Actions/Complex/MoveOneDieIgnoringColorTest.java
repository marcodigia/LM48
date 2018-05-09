package it.polimi.ingsw.Server.Game.GameRules.Actions.Complex;

import it.polimi.ingsw.Client.View.UI_SIMULATION;
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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class MoveOneDieIgnoringColorTest {

    DiceBag diceBag = new DiceBag();
    DraftPool draftPool = new DraftPool(diceBag);
    Actions moveOneDiceAction;
    GameContext gameContext;
    Hashtable<String, Drawable> deck;
    @BeforeEach
    void setUp() {
        WindowPatternCardFactory factory = new WindowPatternCardFactory("windowPatternCards.csv");
        WindowPatternCard windowPatternCard = null;

        try {


            deck = factory.getNewCardDeck();

            windowPatternCard = (WindowPatternCard) deck.get("25");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        draftPool.extractNdice(2);
        gameContext = new GameContext(draftPool, diceBag, null, windowPatternCard);
    }

    //Test if correctly move one dice in a legal position
    @Test
    void doAction() {
        gameContext.getWindowPatternCard().placeDice(new Dice(DiceColor.YELLOW, "2"), 0, true, true, true);
        gameContext.getWindowPatternCard().placeDice(new Dice(DiceColor.YELLOW, "3"), 2, true, true, true);

        assertNotNull(gameContext.getWindowPatternCard().getDice(0));
        assertNull(gameContext.getWindowPatternCard().getDice(1));

        moveOneDiceAction = new MoveOneDieIgnoringColor();
        moveOneDiceAction.useAction(new UI_SIMULATION(0,0,0,1,0),gameContext);

        moveOneDiceAction.doAction(gameContext);

        assertNull(gameContext.getWindowPatternCard().getDice(0));
        assertNotNull(gameContext.getWindowPatternCard().getDice(1));
    }


    //Move one dice in a legal place with restriction
    @Test
    void doAction2() {
        gameContext.getWindowPatternCard().placeDice(new Dice(DiceColor.RED, "4"), 0, true, true, true);
        gameContext.getWindowPatternCard().placeDice(new Dice(DiceColor.RED, "3"), 13, true, true, true);

        assertNotNull(gameContext.getWindowPatternCard().getDice(0));
        assertNull(gameContext.getWindowPatternCard().getDice(19));

        moveOneDiceAction = new MoveOneDieIgnoringColor();
        moveOneDiceAction.useAction(new UI_SIMULATION(0,0,0,19,0),gameContext);

        moveOneDiceAction.doAction(gameContext);

        assertNull(gameContext.getWindowPatternCard().getDice(0));
        assertNotNull(gameContext.getWindowPatternCard().getDice(19));
    }

    //Move one dice in a illegal place with a restriction not Ignored
    @Test
    void doAction3() {
        gameContext = new GameContext(draftPool, diceBag, null, (WindowPatternCard) deck.get("26"));

        gameContext.getWindowPatternCard().placeDice(new Dice(DiceColor.BLUE, "3"), 0, true, true, true);
        gameContext.getWindowPatternCard().placeDice(new Dice(DiceColor.YELLOW, "3"), 13, true, true, true);

        assertNotNull(gameContext.getWindowPatternCard().getDice(0));
        assertNull(gameContext.getWindowPatternCard().getDice(19));

        moveOneDiceAction = new MoveOneDieIgnoringColor();
        moveOneDiceAction.useAction(new UI_SIMULATION(0,0,0,19,0),gameContext);

        moveOneDiceAction.doAction(gameContext);

        assertNotNull(gameContext.getWindowPatternCard().getDice(0));
        assertNull(gameContext.getWindowPatternCard().getDice(19));
    }

}