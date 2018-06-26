package it.polimi.ingsw.Server.Game.GameRules.Actions.Complex;

import it.polimi.ingsw.Server.Game.Cards.WindowPatternCard;
import it.polimi.ingsw.Server.Game.Components.Dice;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Actions;
import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import it.polimi.ingsw.Server.Game.GameRules.Player;
import it.polimi.ingsw.Server.Game.Utility.ANSI_COLOR;
import it.polimi.ingsw.Server.Game.Utility.CONSTANT;
import it.polimi.ingsw.UI;

public class PlaceDice implements Actions {


    private final int MatrixSixe = 20;
    private int matrixIndexTo;
    private int draftpoolFrom;
    private boolean ignoreValue;
    private boolean ignoreColor;
    private boolean ignoreAdjacency;
    private boolean isFirstRound;

    private String userName;

    @Override
    public void doAction(GameStatus gameStatus) {


        Player activePlayer = gameStatus.getPlayerByName(userName);
        WindowPatternCard activePlayerWP = (WindowPatternCard) gameStatus
                .getPlayerCards()
                .get(activePlayer)
                .get(0);

        Dice dice = gameStatus.getDraftPool().getDice(draftpoolFrom);
        if (matrixIndexTo == -1)
            return;

        if ((activePlayerWP).getAllDices().size() == 0) {
            //Check if matrixIndexTo is not on border
            if ((matrixIndexTo > 5 && matrixIndexTo < 9) || (matrixIndexTo > 10 && matrixIndexTo < 14)) {

                return;
            }

            //Try to place the Dice without adjacency restriction

            if (activePlayerWP.isPlaceable(dice, matrixIndexTo, false, false, true)) {
                activePlayerWP.placeDice(dice, matrixIndexTo, false, false, true);
                gameStatus.getDraftPool().removeDice(dice);
                gameStatus.getPlayerByName(userName).getPlaceDiceOfTheTurn().setACTIVE(false);
                return;
            }


        }

        //Try to place the Dice

        if (activePlayerWP.isPlaceable(dice, matrixIndexTo, ignoreColor, ignoreValue, ignoreAdjacency)) {
            activePlayerWP.placeDice(dice, matrixIndexTo, ignoreColor, ignoreValue, ignoreAdjacency);
            gameStatus.getDraftPool().removeDice(dice);
            gameStatus.getPlayerByName(userName).getPlaceDiceOfTheTurn().setACTIVE(false);
           }


    }

    @Override
    public void useAction(UI ui, GameStatus gameStatus, String userName) {

        this.userName = userName;
        Player activePlayer = gameStatus.getPlayerByName(userName);
        WindowPatternCard activePlayerWP = (WindowPatternCard) gameStatus.getPlayerCards().get(activePlayer).get(0);


        draftpoolFrom = ui.getDraftPoolIndex();
        matrixIndexTo = ui.getMatrixIndexTo();


        if (gameStatus.getPlayerByName(userName).getWindowPatternCard().getAllDices().size() > 0) {
            if (!existsValidMove(gameStatus.getDraftPool().getDice(draftpoolFrom), gameStatus.getPlayerByName(userName).getWindowPatternCard())) {
                matrixIndexTo = -1;
                ui.printMessage("Sorry no possible move.\n Try with a different dice.");
            }
        }
    }

    @Override
    public void setACTIVE(boolean b) {
    }

    @Override
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public void setUpAction(String packet) {

        String[] elements = packet.split("\\" + CONSTANT.ElenemtsDelimenter);

        matrixIndexTo = Integer.parseInt(elements[0]);
        draftpoolFrom = Integer.parseInt(elements[1]);
    }


    @Override
    public String toPacket() {
        StringBuilder packet = new StringBuilder();
        packet.append(PlaceDice.class.getName());
        packet.append(CONSTANT.ObjectDelimeterComplex);
        packet.append(matrixIndexTo).append(CONSTANT.ElenemtsDelimenter)
                .append(draftpoolFrom);

        return packet.toString();
    }


    private boolean existsValidMove(Dice dice, WindowPatternCard windowPatternCard) {

        for (int i = 0; i < MatrixSixe; i++) {
            if (windowPatternCard.isPlaceable(dice, i, false, false, false))
                return true;
        }
        return false;
    }
}
