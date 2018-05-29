package it.polimi.ingsw.Server.Game.GameRules.Actions.Complex;

import it.polimi.ingsw.Server.Game.Cards.WindowPatternCard;
import it.polimi.ingsw.Server.Game.Components.Dice;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Actions;
import it.polimi.ingsw.Server.Game.GameRules.GameContext;
import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import it.polimi.ingsw.Server.Game.GameRules.Player;
import it.polimi.ingsw.UI;

public class MoveTwoDice implements Actions {


    private int from1, from2, to1, to2;
    String userName;

    @Override
    public void doAction(GameStatus gameStatus) {
        Player activePlayer = gameStatus.getPlayerByName(userName) ;
        WindowPatternCard activePlayerWP = (WindowPatternCard)gameStatus.getPlayerCards().get(activePlayer).get(0);

        if (activePlayerWP.moveDice(from1, to1, false, false, false))
            if (!activePlayerWP.moveDice(from2, to2, false, false, false))
                activePlayerWP.moveDice(to1, from1, true, true, true);

    }

    @Override
    public void useAction(UI ui, GameStatus gameStatus, String userName) {

        this.userName = userName;
        Player activePlayer = gameStatus.getPlayerByName(userName) ;
        Thread getUserInputThread = new Thread(new Runnable() {
            @Override
            public void run() {
                ui.printMessage("Primo Dado");
                from1 = ui.getMatrixIndexFrom();
                to1 = ui.getMatrixIndexTo();

                ui.printMessage("Secondo Dado");
                from2 = ui.getMatrixIndexFrom();
                to2 = ui.getMatrixIndexTo();
            }
        });

    }

    @Override
    public void setACTIVE(boolean b) {

    }

    @Override
    public void setUpAction(String packet) {

    }

    @Override
    public void setUserName(String userName) {

    }


    private boolean exists2DiceValidMove(GameContext gameContext) {

        System.out.println(gameContext.getWindowPatternCard().getAllDices().size());
        for (int i = 0; i < 20; i++) {
            if (gameContext.getWindowPatternCard().getDice(i) != null)
                for (int j = 0; j < 20; j++) {
                    if (j != i)
                        if (gameContext.getWindowPatternCard().getDice(j) != null) {
                            Dice dice1 = gameContext.getWindowPatternCard().removeDice(i);
                            Dice dice2 = gameContext.getWindowPatternCard().removeDice(j);
                            if (scanMatrix(dice1, gameContext.getWindowPatternCard(), false, false, false, i))
                                if (scanMatrix(dice2, gameContext.getWindowPatternCard(), false, false, false, j)) {
                                    gameContext.getWindowPatternCard().placeDice(dice1, i, true, true, true);
                                    gameContext.getWindowPatternCard().placeDice(dice2, j, true, true, true);
                                    return true;
                                }
                            gameContext.getWindowPatternCard().placeDice(dice1, i, true, true, true);
                            gameContext.getWindowPatternCard().placeDice(dice2, j, true, true, true);
                        }


                }

        }
        return false;
    }

    private boolean scanMatrix(Dice dice, WindowPatternCard windowPatternCard, boolean ignoreColor, boolean ignoreValue, boolean ignoreAdjacency, int currentPlace) {
        for (int i = 0; i < 20; i++) {
            if (i != currentPlace)
                if (windowPatternCard.isPlaceable(dice, i, ignoreColor, ignoreValue, ignoreAdjacency)) {
                    return true;
                }
        }
        return false;
    }

    @Override
    public String toPacket() {
        return null;
    }
}
