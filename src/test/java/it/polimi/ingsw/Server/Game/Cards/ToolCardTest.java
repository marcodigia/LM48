package it.polimi.ingsw.Server.Game.Cards;

import it.polimi.ingsw.Client.View.UI_SIMULATION;
import it.polimi.ingsw.Exceptions.NoPossibleValidMovesException;
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

class ToolCardTest {
    DiceBag diceBag = new DiceBag();
    DraftPool draftPool = new DraftPool(diceBag);
    Actions moveOneDiceAction;
    GameContext gameContext;
    WindowPatternCard windowPatternCard = null;
    WindowPatternCardFactory factory;

    @BeforeEach
    void setUp() {

        factory = new WindowPatternCardFactory("windowPatternCards.csv");

        try {

            Hashtable<String, Drawable> deck = factory.getNewCardDeck();

            windowPatternCard = (WindowPatternCard) deck.get("25");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        gameContext = new GameContext(draftPool, diceBag, null, windowPatternCard);

        draftPool.extractNdice(2);


    }


    @Test
    void toolCard1() {
        ToolCardFactory Toolfactory = new ToolCardFactory("toolCards.csv");
        ToolCard toolCard;
        try {

            Hashtable<String, Drawable> deck = Toolfactory.getNewCardDeck();

            toolCard = (ToolCard) deck.get("1");
            gameContext.getDraftPool().getDice(0).setValue(2);
            Actions actions = toolCard.getActions(new UI_SIMULATION(), gameContext);
            assertEquals(gameContext.getDraftPool().getDice(0).getValue(), "2");
            actions.doAction();
            assertEquals(gameContext.getDraftPool().getDice(0).getValue(), "3");

        } catch (FileNotFoundException | NoPossibleValidMovesException e) {
            e.printStackTrace();
        }
    }

    @Test
    void toolCard2() {
        ToolCardFactory Toolfactory = new ToolCardFactory("toolCards.csv");
        ToolCard toolCard;
        try {

            Hashtable<String, Drawable> deck = Toolfactory.getNewCardDeck();

            toolCard = (ToolCard) deck.get("2");
            gameContext.getWindowPatternCard().placeDice(new Dice(DiceColor.RED, "2"), 0, true, true, true);
            gameContext.getWindowPatternCard().placeDice(new Dice(DiceColor.RED, "2"), 13, true, true, true);

            assertNotNull(toolCard);
            Actions actions = toolCard.getActions(new UI_SIMULATION(), gameContext);
            assertNull(gameContext.getWindowPatternCard().getDice(19));
            actions.doAction();
            assertNotNull(gameContext.getWindowPatternCard().getDice(19));

        } catch (FileNotFoundException | NoPossibleValidMovesException e) {
            e.printStackTrace();
        }
    }

    @Test
    void toolCard3() {

        try {

            Hashtable<String, Drawable> deck = factory.getNewCardDeck();

            windowPatternCard = (WindowPatternCard) deck.get("26");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        gameContext = new GameContext(draftPool, diceBag, null, windowPatternCard);


        ToolCardFactory Toolfactory = new ToolCardFactory("toolCards.csv");
        ToolCard toolCard;
        try {

            Hashtable<String, Drawable> deck = Toolfactory.getNewCardDeck();

            toolCard = (ToolCard) deck.get("3");
            gameContext.getWindowPatternCard().placeDice(new Dice(DiceColor.RED, "2"), 0, true, true, true);
            gameContext.getWindowPatternCard().placeDice(new Dice(DiceColor.RED, "2"), 13, true, true, true);

            assertNotNull(toolCard);
            Actions actions = toolCard.getActions(new UI_SIMULATION(), gameContext);
            assertNull(gameContext.getWindowPatternCard().getDice(19));
            actions.doAction();
            assertNotNull(gameContext.getWindowPatternCard().getDice(19));

        } catch (FileNotFoundException | NoPossibleValidMovesException e) {
            e.printStackTrace();
        }
    }

    @Test
    void toolCard4_1() {
        ToolCardFactory Toolfactory = new ToolCardFactory("toolCards.csv");
        ToolCard toolCard;
        try {

            Hashtable<String, Drawable> deck = Toolfactory.getNewCardDeck();

            toolCard = (ToolCard) deck.get("4");
            gameContext.getWindowPatternCard().placeDice(new Dice(DiceColor.RED, "2"), 0, true, true, true);
            gameContext.getWindowPatternCard().placeDice(new Dice(DiceColor.RED, "2"), 2, true, true, true);
            gameContext.getWindowPatternCard().placeDice(new Dice(DiceColor.RED, "2"), 6, true, true, true);
            gameContext.getWindowPatternCard().placeDice(new Dice(DiceColor.RED, "2"), 13, true, true, true);

            assertNotNull(toolCard);
            Actions actions = toolCard.getActions(new UI_SIMULATION(), gameContext);
            assertNull(gameContext.getWindowPatternCard().getDice(19));
            assertNotNull(gameContext.getWindowPatternCard().getDice(0));
            assertNotNull(gameContext.getWindowPatternCard().getDice(2));
            actions.doAction();
            assertNotNull(gameContext.getWindowPatternCard().getDice(19));


        } catch (FileNotFoundException | NoPossibleValidMovesException e) {
            e.printStackTrace();
        }
    }

    /*
    void toolCard4_2() {
        ToolCardFactory Toolfactory = new ToolCardFactory("toolCards.csv");
        ToolCard toolCard;
        try {

            Hashtable<String, Drawable> deck = Toolfactory.getNewCardDeck();

            toolCard = (ToolCard) deck.get("4");


            for(int i = 0 ; i < 19 ; i ++) {

                gameContext.getWindowPatternCard().placeDice(new Dice(DiceColor.YELLOW,"4"),i,true,true,true);
            }

            Actions actions = toolCard.getActions(new UI_SIMULATION(), gameContext);
            actions.doAction();

        } catch (FileNotFoundException | NoPossibleValidMovesException e) {
            e.printStackTrace();
            assertNotNull(e);
        }



    }*/
}