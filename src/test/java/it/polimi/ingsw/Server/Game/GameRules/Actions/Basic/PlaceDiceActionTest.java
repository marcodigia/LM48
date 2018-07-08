package it.polimi.ingsw.Server.Game.GameRules.Actions.Basic;

import it.polimi.ingsw.Server.Game.Cards.Drawable;
import it.polimi.ingsw.Server.Game.Components.Boards.BoardRound;
import it.polimi.ingsw.Server.Game.Components.Boards.DraftPool;
import it.polimi.ingsw.Server.Game.Components.DiceBag;
import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import it.polimi.ingsw.Server.Game.GameRules.Player;
import it.polimi.ingsw.Server.Game.Utility.Unpacker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sun.awt.UNIXToolkit;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlaceDiceActionTest {

    GameStatus gameStatus;
    DraftPool draftPool;
    DiceBag diceBag;
    BoardRound boardRound;
    ArrayList<Player> players;
    private LinkedHashMap<Player, List<Drawable> > playerCards = new LinkedHashMap<Player, List<Drawable>>();
    @BeforeEach
    void createGameStatus(){
        Unpacker.setUpUnpacker();
        players = new ArrayList<>();
        players.add(new Player("A",null));
        players.add(new Player("B",null));
        players.add(new Player("C",null));
        players.add(new Player("D",null));

        for (int i = 0 ; i < players.size() ; i ++){
            ArrayList<Drawable> pl = new ArrayList<>();
            pl.add(Unpacker.WPDeck.get(String.valueOf(i)));
            pl.add(Unpacker.PRDeck.get(String.valueOf(i)));
            playerCards.put(players.get(i),pl);
        }

        diceBag = new DiceBag();
        draftPool = new DraftPool(diceBag);
        boardRound = new BoardRound(players);
        gameStatus = new GameStatus(playerCards,boardRound);
        gameStatus.setDraftPool(draftPool);
        gameStatus.setDiceBag(diceBag);
        draftPool.extractNdice(2*players.size()+1);
    }

    @Test
    void doAction() {
        PlaceDiceAction placeDiceAction = new PlaceDiceAction();


    }

    @Test
    void useAction() {
    }

    @Test
    void actionState() {
    }

    @Test
    void setUpAction() {
    }

    @Test
    void changeRestricion() {
    }
}