package it.polimi.ingsw.Server.Game.GameRules.Actions.Basic;

import it.polimi.ingsw.UI;
import it.polimi.ingsw.Exceptions.EndOfTurnException;
import it.polimi.ingsw.Server.Game.Cards.WindowPatternCard;
import it.polimi.ingsw.Server.Game.Components.Dice;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Actions;
import it.polimi.ingsw.Server.Game.GameRules.GameContext;

public class PlaceDiceAction implements Actions {

    private boolean ACTIVE = true;
    private final int MatrixSixe = 20;
    private Dice dice;
    private int matrixIndexTo;
    private boolean ignoreValue;
    private boolean ignoreColor;
    private boolean ignoreAdjacency;
    private boolean isFirstRound;

    //This is called in TakeDiceBasicAction
    public PlaceDiceAction() {
        dice = null;
    }

    //This is called in RerollDraftedDiceAction
    public PlaceDiceAction(Dice dice, boolean ignoreColor, boolean ignoreValue, boolean ignoreAdjacency) {
        this.ignoreAdjacency = ignoreAdjacency;
        this.ignoreColor = ignoreColor;
        this.ignoreValue = ignoreValue;
        this.dice = dice;
    }

    //NB if the get to this method it means that the user has already confirm its action so if if is illegal the dice shuld be removed
    @Override
    public void doAction(GameContext gameContext) {

        if (!ACTIVE)
            return;
        if (gameContext.isFirstRound()) {
            //Check if matrixIndexTo is not on border
            if ((matrixIndexTo > 5 && matrixIndexTo < 9) || (matrixIndexTo > 10 && matrixIndexTo < 14)) {
                return;
            }
            //Try to place the Dice without adjacency restriction

            if (gameContext.getWindowPatternCard().isPlaceable(dice, matrixIndexTo, false, false, true)) {
                gameContext.getWindowPatternCard().placeDice(dice, matrixIndexTo, false, false, true);
                gameContext.getDraftPool().removeDice(dice);
                ACTIVE = false;
            } else {
                ACTIVE = true;
            }



        }

        //Try to place the Dice

        if (gameContext.getWindowPatternCard().isPlaceable(dice, matrixIndexTo, ignoreColor, ignoreValue, ignoreAdjacency)) {
            gameContext.getWindowPatternCard().placeDice(dice, matrixIndexTo, ignoreColor, ignoreValue, ignoreAdjacency);
            gameContext.getDraftPool().removeDice(dice);
            ACTIVE = false;
        } else {
            ACTIVE = true;
        }


    }

   /* @Override
    public void useAction(UI ui, GameContext gameContext) {

        //NB the order in this 'if' is important because it is shortcutted
        if (!ACTIVE)
            return;
        final boolean[] result = new boolean[1];
        if (dice != null && existsValidMove(dice, gameContext.getWindowPatternCard())) {

            Thread getUserInputThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    matrixIndexTo = ui.getMatrixIndexTo();
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
                    matrixIndexTo = ui.getMatrixIndexTo();
                    int diceIndex = ui.getDraftPoolIndex();
                    dice = gameContext.getDraftPool().getDice(diceIndex);
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
            ui.printMessage("No possible moves , Putting dice back to Draft Pool ... ");
            return;
        }

        if (result[0]) {
            //TODO send the Action to the server to do Do Action
            doAction(gameContext);
        }

    }*/

    @Override
    public void useAction(UI ui, GameContext gameContext){
        if (!ACTIVE)
            return;

        if (dice != null && existsValidMove(dice, gameContext.getWindowPatternCard())){

            matrixIndexTo = ui.getMatrixIndexTo();

        } else if (dice == null){

            matrixIndexTo = ui.getMatrixIndexTo();
            int diceIndex = ui.getDraftPoolIndex();
            dice = gameContext.getDraftPool().getDice(diceIndex);

        }else {
            ui.printMessage("No possible moves , Putting dice back to Draft Pool ... ");
            return;
        }

    }


    private boolean existsValidMove(Dice dice, WindowPatternCard windowPatternCard) {

        for (int i = 0; i < MatrixSixe; i++) {
            if (windowPatternCard.isPlaceable(dice, i, false, false, false))
                return true;
        }
        return false;
    }

    public void setACTIVE(boolean b){
        ACTIVE = b;
    }

    public boolean actionState(){
        return ACTIVE ;
    }

    @Override
    public String toPacket() {
        return null;
    }
}
