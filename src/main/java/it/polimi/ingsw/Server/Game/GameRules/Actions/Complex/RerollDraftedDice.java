package it.polimi.ingsw.Server.Game.GameRules.Actions.Complex;

import it.polimi.ingsw.Client.View.UI;
import it.polimi.ingsw.Server.Game.Cards.WindowPatternCard;
import it.polimi.ingsw.Server.Game.Components.Dice;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Actions;
import it.polimi.ingsw.Server.Game.GameRules.GameContext;

public class RerollDraftedDice implements Actions {

    private int MatrixSixe = 20;
    private GameContext gameContext;
    private int diceIndex;
    private UI ui;

    public RerollDraftedDice(GameContext gameContext, int diceIndex, UI ui) {
        this.gameContext = gameContext;
        this.diceIndex = diceIndex;

        this.ui = ui;
    }

    @Override
    public void doAction() {

        Dice diceToReroll = gameContext.getDraftPool().getDice(diceIndex);
        diceToReroll.reroll();
        if (existsValidMove(diceToReroll, gameContext.getWindowPatternCard())) {
            boolean flag = true;
            do {
                int diceIndexTo = ui.getMatrixIndexTo();
                if (gameContext.getWindowPatternCard().isPlaceable(diceToReroll, diceIndexTo, false, false, false)) {

                    gameContext.getWindowPatternCard().placeDice(diceToReroll, diceIndexTo, false, false, false);
                    gameContext.getDraftPool().removeDice(gameContext.getWindowPatternCard().getDice(diceIndex));
                    flag = false;
                }

            } while (flag);
        }
    }

    private boolean existsValidMove(Dice dice, WindowPatternCard windowPatternCard) {

        for (int i = 0; i < MatrixSixe; i++) {
            if (windowPatternCard.isPlaceable(dice, i, false, false, false))
                return true;
        }
        return false;
    }
}
