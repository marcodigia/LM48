package it.polimi.ingsw.Server.Game.GameRules.Actions.Basic;

import it.polimi.ingsw.Server.Game.Cards.Drawable;
import it.polimi.ingsw.Server.Game.Cards.WindowPatternCard;
import it.polimi.ingsw.Server.Game.Cards.WindowPatternCardFactory;
import it.polimi.ingsw.Server.Game.Components.Boards.DraftPool;
import it.polimi.ingsw.Server.Game.Components.DiceBag;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Actions;
import it.polimi.ingsw.Server.Game.GameRules.GameContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.Hashtable;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class PlaceDiceActionTest {

  /*  DiceBag diceBag = new DiceBag();
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
        gameContext = new GameContext(draftPool, diceBag, null, windowPatternCard, null);
    }

    //Verify if the the Action is correctly done in the first round
    @Test
    void doActionFirstRound() {
        gameContext.setFirstRound(true);
        PlaceDiceAction placeDiceAction = new PlaceDiceAction();
        placeDiceAction.useAction(new UI_SIMULATION(0, 0, 0, 0, 0), gamestatus);
        assertNull(gameContext.getWindowPatternCard().getDice(0));
        placeDiceAction.doAction(gamestatus);
        assertNotNull(gameContext.getWindowPatternCard().getDice(0));
    }

    //Verify if the Action is correctly done in a round different from the first
    @Test
    void doActionNonFirstRound() {
        gameContext.setFirstRound(true);
        PlaceDiceAction placeDiceAction = new PlaceDiceAction();
        placeDiceAction.useAction(new UI_SIMULATION(0, 0, 0, 0, 0), gamestatus);
        assertNull(gameContext.getWindowPatternCard().getDice(0));
        placeDiceAction.doAction(gamestatus);
        assertNotNull(gameContext.getWindowPatternCard().getDice(0));
        draftPool.extractNdice(1);
        gameContext.setFirstRound(false);
        placeDiceAction.useAction(new UI_SIMULATION(0, 0, 0, 7, 0), gamestatus);
        assertNull(gameContext.getWindowPatternCard().getDice(7));
        placeDiceAction.doAction(gamestatus);
        assertNull(gameContext.getWindowPatternCard().getDice(7));
    }*/
}