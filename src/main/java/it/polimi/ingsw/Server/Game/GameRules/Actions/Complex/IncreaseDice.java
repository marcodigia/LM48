package it.polimi.ingsw.Server.Game.GameRules.Actions.Complex;

import it.polimi.ingsw.Server.Game.Cards.WindowPatternCard;
import it.polimi.ingsw.Server.Game.Components.Boards.BoardRound;
import it.polimi.ingsw.Server.Game.Components.Boards.DraftPool;
import it.polimi.ingsw.Server.Game.Components.Dice;

public class IncreaseDice extends ComplexAction {

    private DraftPool draftPool;
    private WindowPatternCard windowPatternCard;

    private int ammount;
    private int draftPoolIndex;

    public IncreaseDice(int ammount, int draftPoolIndex) {
        this.ammount = ammount;
        this.draftPoolIndex = draftPoolIndex;
    }

    @Override
    public void doAction() {

        Dice diceToChange = draftPool.getDice(draftPoolIndex);
        if (ammount == 1 || ammount == -1)
            if (Integer.parseInt(diceToChange.getValue()) < 6 && Integer.parseInt(diceToChange.getValue()) > 1)
                diceToChange.setValue(Integer.parseInt(diceToChange.getValue()) + ammount);
    }

    @Override
    public void setDraftPool(DraftPool draftPool) {
        this.draftPool = draftPool;
    }

    @Override
    public void setWindowPatter(WindowPatternCard windowPatter) {

        this.windowPatternCard = windowPatter;
    }

    @Override
    public void setRoundTrack(BoardRound roundTrack) {

    }


}
