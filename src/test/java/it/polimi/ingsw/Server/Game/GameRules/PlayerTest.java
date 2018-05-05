package it.polimi.ingsw.Server.Game.GameRules;

import it.polimi.ingsw.Server.Game.Cards.*;
import it.polimi.ingsw.Server.Game.Components.Boards.DraftPool;
import it.polimi.ingsw.Server.Game.Components.DiceBag;
import it.polimi.ingsw.Server.Game.GameRules.PlayerUtility.PlayerColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.Hashtable;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    Player player;
    DiceBag diceBag = new DiceBag();
    DraftPool draftPool = new DraftPool(diceBag);
    WindowPatternCard windowPatternCard;
    ToolCard toolCard;
    GameContext gameContext;
    @BeforeEach
    void setUp() {
        player = new Player(PlayerColor.PLAYER_BLUE, "maior");
        WindowPatternCardFactory factory = new WindowPatternCardFactory("windowPatternCards.csv");
        try {

            Hashtable<String, Drawable> deck = factory.getNewCardDeck();
            windowPatternCard = (WindowPatternCard) deck.get("25");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        draftPool.extractNdice(2);

        ToolCardFactory toolCardFactory = new ToolCardFactory("toolCards.csv");

        try {
            Hashtable<String, Drawable> deckTools = toolCardFactory.getNewCardDeck();
            //Tool card 13 is fake card which increase the value of a dice by 1
            toolCard = (ToolCard) deckTools.get("13");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        gameContext = new GameContext(draftPool, diceBag, null, windowPatternCard);
        player.setGameContext(gameContext);
    }

    @Test
    void setAction_placeDiceOfTheTurn() {
        assertNull(windowPatternCard.getDice(0));

        player.setAction_placeDiceOfTheTurn(0, 0);
        assertNotNull(windowPatternCard.getDice(0));

    }

    @Test
    void setAction_UseToolCardOfTheTurn() {

        draftPool.getDice(0).setValue(3);
        player.setAction_UseToolCardOfTheTurn(toolCard);
        assertEquals(Integer.parseInt(draftPool.getDice(0).getValue()), 4);

    }

}