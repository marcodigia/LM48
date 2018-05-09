package it.polimi.ingsw.Server.Game.GameRules;

import it.polimi.ingsw.Server.Game.Cards.WindowPatternCard;
import it.polimi.ingsw.Server.Game.Components.Boards.BoardRound;
import it.polimi.ingsw.Server.Game.Components.Boards.DraftPool;
import it.polimi.ingsw.Server.Game.Components.DiceBag;


public class GameContext {

    private DraftPool draftPool;
    private DiceBag diceBag;
    private BoardRound boardRound;
    private WindowPatternCard windowPatternCard;
    private boolean isFirstRound = false;



    public GameContext(DraftPool draftPool, DiceBag diceBag, BoardRound boardRound, WindowPatternCard windowPatternCard) {
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

    public boolean isFirstRound() {
        return isFirstRound;
    }

    public void setFirstRound(boolean firstRound) {
        isFirstRound = firstRound;
    }
}
