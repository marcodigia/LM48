package it.polimi.ingsw.Server.Game.GameRules;

import it.polimi.ingsw.Server.Game.Cards.PrivateObjectiveCard;
import it.polimi.ingsw.Server.Game.Cards.ToolCard;
import it.polimi.ingsw.Server.Game.Cards.WindowPatternCard;
import it.polimi.ingsw.Server.Game.Components.Boards.BoardRound;
import it.polimi.ingsw.Server.Game.Components.Boards.DraftPool;
import it.polimi.ingsw.Server.Game.Components.DiceBag;


public class GameContext {

    private PrivateObjectiveCard privateObjectiveCard;
    private DraftPool draftPool;
    private DiceBag diceBag;
    private BoardRound boardRound;
    private WindowPatternCard windowPatternCard;
    private ToolCard choosenToolCard;
    private boolean isFirstRound = false;



    public GameContext(DraftPool draftPool, DiceBag diceBag, BoardRound boardRound, WindowPatternCard windowPatternCard, PrivateObjectiveCard privateObjectiveCard) {
        this.draftPool = draftPool;
        this.diceBag = diceBag;
        this.boardRound = boardRound;
        this.windowPatternCard = windowPatternCard;
        this.privateObjectiveCard = privateObjectiveCard;
    }

    public PrivateObjectiveCard getPrivateObjectiveCard() {
        return privateObjectiveCard;
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

    public void setWindowPatternCard(WindowPatternCard wp){windowPatternCard = wp;}

    public boolean isFirstRound() {
        return isFirstRound;
    }

    public void setFirstRound(boolean firstRound) {
        isFirstRound = firstRound;
    }

    public void setChoosenToolCard(ToolCard toolCard){
        this.choosenToolCard = toolCard;
    }

    public ToolCard getChoosenToolCard() {
        return choosenToolCard;
    }
}
