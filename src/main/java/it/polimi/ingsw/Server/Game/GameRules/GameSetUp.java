package it.polimi.ingsw.Server.Game.GameRules;

import it.polimi.ingsw.Server.Game.Cards.ToolCard;
import it.polimi.ingsw.Server.Game.Cards.WindowPatternCard;
import it.polimi.ingsw.Server.Game.Components.Boards.BoardRound;
import it.polimi.ingsw.Server.Game.Components.Boards.DraftPool;
import it.polimi.ingsw.Server.Game.Components.DiceBag;

import java.util.Hashtable;


public class GameSetUp {

    private DraftPool draftPool;
    private DiceBag diceBag;
    private BoardRound boardRound;
    private WindowPatternCard windowPatternCard;


    public GameSetUp(DraftPool draftPool, DiceBag diceBag, BoardRound boardRound, WindowPatternCard windowPatternCard) {
        this.draftPool = draftPool;
        this.diceBag = diceBag;
        this.boardRound = boardRound;
        this.windowPatternCard = windowPatternCard;
    }


    public DraftPool getDraftPool() {
        return draftPool;
    }

    public DiceBag getDiceBag() {
        return diceBag;
    }

    public BoardRound getBoardRound() {
        return boardRound;
    }

    public WindowPatternCard getWindowPatternCard() {
        return windowPatternCard;
    }
}
