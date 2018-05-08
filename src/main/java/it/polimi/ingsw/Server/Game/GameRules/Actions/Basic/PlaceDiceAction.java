package it.polimi.ingsw.Server.Game.GameRules.Actions.Basic;

import it.polimi.ingsw.Client.View.UI;
import it.polimi.ingsw.Exceptions.EndOfTurnException;
import it.polimi.ingsw.Server.Game.Cards.WindowPatternCard;
import it.polimi.ingsw.Server.Game.Components.Dice;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Actions;
import it.polimi.ingsw.Server.Game.GameRules.GameContext;

public class PlaceDiceAction implements Actions {

    private final int MatrixSixe = 20;
    private Dice dice;
    private int matrixIndexTo;

    public PlaceDiceAction() {
        dice = null;
    }

    public PlaceDiceAction(Dice dice) {
        this.dice = dice;
    }

    //NB if the get to this method it means that the user has already confirm its action so if if is illegal the dice shuld be removed
    @Override
    public void doAction(GameContext gameContext) {

        if (gameContext.getWindowPatternCard().isPlaceable(dice, matrixIndexTo, false, false, false)) {
            gameContext.getDraftPool().removeDice(dice);
            gameContext.getWindowPatternCard().placeDice(dice, matrixIndexTo, false, false, false);
        }

    }

    @Override
    public void useAction(UI ui, GameContext gameContext) {

        //NB the order in this if is important because it is shortcutted
        final boolean[] result = new boolean[1];
        if (dice != null && existsValidMove(dice, gameContext.getWindowPatternCard())) {

            Thread getUserInputThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        boolean flag = true;
                        do {
                            matrixIndexTo = ui.getMatrixIndexTo();
                            if (gameContext.getWindowPatternCard().isPlaceable(dice, matrixIndexTo, false, false, false))
                                flag = false;
                        } while (flag);

                    } catch (EndOfTurnException e) {
                        e.printStackTrace();
                        result[0] = false;
                    }
                }
            });

            getUserInputThread.start();
            try {
                getUserInputThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
            if (!result[0]) {
                return;
            }
        } else if (dice == null) {
            Thread getUserInputThread = new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        matrixIndexTo = ui.getMatrixIndexTo();
                        int diceIndex = ui.getDraftPoolIndex();
                        dice = gameContext.getDraftPool().getDice(diceIndex);
                    } catch (EndOfTurnException e) {
                        e.printStackTrace();
                        result[0] = false;
                    }
                }
            });

            getUserInputThread.start();

            try {
                getUserInputThread.join();

            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
        } else {
            try {
                ui.printMessage("No possible moves , Putting dice back to Draft Pool ... ");
            } catch (EndOfTurnException e) {
                e.printStackTrace();
            }
            return;
        }

        if (result[0]) {
            //TODO send the Action to the server to do Do Action
            doAction(gameContext);
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
