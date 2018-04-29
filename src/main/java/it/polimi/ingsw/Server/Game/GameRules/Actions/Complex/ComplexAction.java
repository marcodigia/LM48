package it.polimi.ingsw.Server.Game.GameRules.Actions.Complex;

import it.polimi.ingsw.Server.Game.Cards.WindowPatternCard;
import it.polimi.ingsw.Server.Game.Components.Boards.BoardRound;
import it.polimi.ingsw.Server.Game.Components.Boards.DraftPool;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Actions;

public abstract class ComplexAction implements Actions {

    public abstract void setDraftPool(DraftPool draftPool);

    public abstract void setWindowPatter(WindowPatternCard windowPatter);

    public abstract void setRoundTrack(BoardRound roundTrack);

}
