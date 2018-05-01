package it.polimi.ingsw.Server.Game.GameRules.Actions.Complex;

import it.polimi.ingsw.Server.Game.Cards.WindowPatternCard;
import it.polimi.ingsw.Server.Game.Components.Boards.BoardRound;
import it.polimi.ingsw.Server.Game.Components.Boards.DraftPool;
import it.polimi.ingsw.Server.Game.Components.Dice;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Actions;
import it.polimi.ingsw.Server.Game.GameRules.GameSetUp;

public class ChangeDiceValueByOne implements Actions {

    private DraftPool draftPool;

    private int ammount;
    private int draftPoolIndex;
    private boolean active;

    public ChangeDiceValueByOne(int ammount, int draftPoolIndex, GameSetUp gameSetUp) {
        active = true;
        this.ammount = ammount;
        this.draftPoolIndex = draftPoolIndex;
        draftPool = gameSetUp.getDraftPool();

    }

    @Override
    public void doAction() {

        if (!active)
            return;

        Dice diceToChange = draftPool.getDice(draftPoolIndex);

        if (ammount == 1) {
            if (Integer.parseInt(diceToChange.getValue()) < 6) {
                diceToChange.setValue(Integer.parseInt(diceToChange.getValue()) + ammount);
                active = false;
            }
        } else if (ammount == -1) {
            if (Integer.parseInt(diceToChange.getValue()) > 1) {
                diceToChange.setValue(Integer.parseInt(diceToChange.getValue()) + ammount);
                active = false;
            }

        }
    }


}
