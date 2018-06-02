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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class RerollDraftedDiceTest {}/*

    DiceBag diceBag = new DiceBag();
    DraftPool draftPool = new DraftPool(diceBag);
    Actions rerollDraftedDice;
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
        draftPool.extractNdice(1);
        gameContext = new GameContext(draftPool, diceBag, null, windowPatternCard, null);
    }

    //Reroll a dice and place in a legal position
    @Test
    void doAction() {
        while (gameContext.getDraftPool().getDice(0).getDiceColor() == DiceColor.RED) {
            gameContext.getDraftPool().extractNdice(1);
        }

        gameContext.getWindowPatternCard().placeDice(new Dice(DiceColor.BLUE, "1"), 0, true, true, true);
        gameContext.getWindowPatternCard().placeDice(new Dice(DiceColor.BLUE, "2"), 19, true, true, true);


        Actions rerollDraftedDice = new RerollDraftedDice();
        assertNull(gameContext.getWindowPatternCard().getDice(13));
        rerollDraftedDice.useAction(new UI_SIMULATION(0, 0, 0, 13, 0), gamestatus);
        rerollDraftedDice.doAction(gamestatus);
        assertNotNull(gameContext.getWindowPatternCard().getDice(13));


    }

    //Reroll a dice and place in an illegal position due to a Color Restriction
    @Test
    void doAction2() {
        while (gameContext.getDraftPool().getDice(0).getDiceColor() == DiceColor.RED) {
            gameContext.getDraftPool().extractNdice(1);
        }

        gameContext.getWindowPatternCard().placeDice(new Dice(DiceColor.BLUE, "2"), 13, true, true, true);

        Actions rerollDraftedDice = new RerollDraftedDice();
        assertNull(gameContext.getWindowPatternCard().getDice(19));
        rerollDraftedDice.useAction(new UI_SIMULATION(0, 0, 0, 19, 0), gamestatus);
        rerollDraftedDice.doAction(gamestatus);
        assertNull(gameContext.getWindowPatternCard().getDice(19));


    }

    //Reroll drafted and then trying to put it in an illegal position due to a Value Restriction
    @Test
    void doAction3() {
        WindowPatternCardFactory factory = new WindowPatternCardFactory("windowPatternCards.csv");
        WindowPatternCard windowPatternCard = null;

        try {

            Hashtable<String, Drawable> deck = factory.getNewCardDeck();

            windowPatternCard = (WindowPatternCard) deck.get("26");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        draftPool.extractNdice(1);
        gameContext = new GameContext(draftPool, diceBag, null, windowPatternCard, null);

        while (gameContext.getDraftPool().getDice(0).getDiceColor() == DiceColor.RED) {
            gameContext.getDraftPool().extractNdice(1);
        }

        gameContext.getDraftPool().getDice(0).setValue(4);

        gameContext.getWindowPatternCard().placeDice(new Dice(DiceColor.BLUE, "2"), 13, true, true, true);

        Actions rerollDraftedDice = new RerollDraftedDice();
        assertNull(gameContext.getWindowPatternCard().getDice(19));
        rerollDraftedDice.useAction(new UI_SIMULATION(0, 0, 0, 17, 0), gamestatus);
        rerollDraftedDice.doAction(gamestatus);
        assertNull(gameContext.getWindowPatternCard().getDice(19));

    }


}*/