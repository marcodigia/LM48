package it.polimi.ingsw.Server.Game.GameRules.Actions.Basic;

import it.polimi.ingsw.Client.View.UI_SIMULATION;
import it.polimi.ingsw.Server.Game.Cards.Drawable;
import it.polimi.ingsw.Server.Game.Cards.ToolCard;
import it.polimi.ingsw.Server.Game.Cards.ToolCardFactory;
import it.polimi.ingsw.Server.Game.Components.Boards.DraftPool;
import it.polimi.ingsw.Server.Game.Components.DiceBag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.Hashtable;

import static org.junit.jupiter.api.Assertions.*;

class UseToolCardBasicTest {

    ToolCard toolCard;
    DiceBag diceBag = new DiceBag();
    DraftPool draftPool = new DraftPool(diceBag);

    @BeforeEach
    void setuP() {
        ToolCardFactory factory = new ToolCardFactory("toolCards.csv");
        try {
            Hashtable<String, Drawable> deck = factory.getNewCardDeck();
            toolCard = (ToolCard) deck.get("13");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        draftPool.extractNdice(1);
    }

    @Test
    void doAction() {

        BasicAction action = new UseToolCardBasic(toolCard, new UI_SIMULATION(), draftPool, null);
        draftPool.getDice(0).setValue(4);
        action.doAction();
        System.out.println(draftPool.getDice(0).getValue());
        assertTrue(5 == Integer.parseInt(draftPool.getDice(0).getValue()));
    }
}