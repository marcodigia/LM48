package it.polimi.ingsw.Server.Game.GameRules.Actions.Basic;

import it.polimi.ingsw.Server.Game.Cards.WindowPatternCard;
import it.polimi.ingsw.Server.Game.Components.Boards.DraftPool;

public class TakeDiceBasic extends BasicAction {

    private WindowPatternCard windowPatternCard;
    private DraftPool draftPool;

    private int from, to;
    private boolean active = false;

    public TakeDiceBasic(WindowPatternCard windowPatternCard, DraftPool draftPool) {
        this.windowPatternCard = windowPatternCard;
        this.draftPool = draftPool;
    }

    public boolean takeDice(int from, int to) {
        if (to > 5 && to < 9 || to > 10 && to < 14)
            return false;
        if (windowPatternCard.isPlaceable(draftPool.getDice(from), to, false, false, true)) {
            active = true;
            return true;
        }
        return false;
    }

    @Override
    public void doAction() {
        if (active) {
            windowPatternCard.placeDice(draftPool.getDice(from), to, false, false, true);
            System.out.println("Took dice and put");
        }
    }
}
