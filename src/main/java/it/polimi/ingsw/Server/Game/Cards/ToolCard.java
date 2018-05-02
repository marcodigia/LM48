package it.polimi.ingsw.Server.Game.Cards;

import it.polimi.ingsw.Client.View.UI;
import it.polimi.ingsw.Exceptions.NoPossibleValidMovesException;
import it.polimi.ingsw.Server.Game.Components.Dice;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Actions;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Complex.ChangeDiceValueByOne;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Complex.MoveOneDiceIgnoringValue;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Complex.MoveOneDieIgnoringColor;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Complex.MoveTwoDice;
import it.polimi.ingsw.Server.Game.GameRules.GameSetUp;
import it.polimi.ingsw.Server.Game.Utility.DiceColor;

import java.util.ArrayList;

public class ToolCard implements Drawable {
    private DiceColor color;
    private String name;
    private String id;
    private String effect;
    private String restriction;

    public ToolCard(ArrayList<String> pattern) {

        color = DiceColor.resolveColor(pattern.get(0));
        name = pattern.get(1);
        id = pattern.get(2);
        effect = pattern.get(3);
        restriction = pattern.get(4);

    }


    public String getID() {
        return id;
    }


    public Actions getActions(UI ui, GameSetUp gameSetUp) throws NoPossibleValidMovesException {

        Actions action = null;
        switch (id) {

            case "13":
                System.out.println("Did nothing");
                action = new ChangeDiceValueByOne(1, ui.getDraftPoolIndex(), gameSetUp);
                ui.getMatrixIndexTo();
                ui.getDraftPoolIndex();
                break;
            case "1":

                action = new ChangeDiceValueByOne(ui.getAmmountToChange(), ui.getDraftPoolIndex(), gameSetUp);
                break;


            case "2": // The dice cannot be choose if it is not possible to place it, if it is the first round it is impossible to move the dice due to adjacency restriction
                if (!existsValidMove(gameSetUp.getDraftPool().getDraft(), gameSetUp.getWindowPatternCard(), true, false, false)) {
                    ui.printMessage("No possible valid moves");
                    throw new NoPossibleValidMovesException();
                }
                boolean flag = true;
                do {
                    int matrixTo = ui.getMatrixIndexTo();
                    int matrixIndexFromFrom = ui.getMatrixIndexFrom();
                    if (gameSetUp.getWindowPatternCard().isPlaceable(gameSetUp.getWindowPatternCard().getDice(matrixIndexFromFrom), matrixTo, true, false, false)) {
                        flag = false;
                        action = new MoveOneDieIgnoringColor(gameSetUp, matrixIndexFromFrom, matrixTo);
                    }
                } while (flag);

                break;


            case "3": // The dice cannot be choose if it is not possible to place it, if it is the first round it is impossible to move the dice due to adjacency restriction
                if (!existsValidMove(gameSetUp.getDraftPool().getDraft(), gameSetUp.getWindowPatternCard(), false, true, false)) {
                    ui.printMessage("No possible valid moves");
                    throw new NoPossibleValidMovesException();
                }
                boolean flag2 = true;
                do {
                    int matrixTo = ui.getMatrixIndexTo();
                    int matrixIndexFrom = ui.getMatrixIndexFrom();
                    if (gameSetUp.getWindowPatternCard().isPlaceable(gameSetUp.getWindowPatternCard().getDice(matrixIndexFrom), matrixTo, false, true, false)) {
                        flag2 = false;
                        action = new MoveOneDiceIgnoringValue(gameSetUp, matrixIndexFrom, matrixTo);
                    }
                } while (flag2);

                break;


            case "4":
                if (gameSetUp.getWindowPatternCard().getAllDices().size() < 2 || gameSetUp.getWindowPatternCard().getAllDices().size() > 19)
                    throw new NoPossibleValidMovesException();
                flag = false;
                if (!exists2DiceValidMove(gameSetUp))
                    throw new NoPossibleValidMovesException();
                int dice1From;
                int dice1To;
                int dice2From;
                int dice2To;
                do {
                    // if i due dadi non vanno in constrasto tra di loro quando vengono piazzati piazza

                    dice1From = ui.getMatrixIndexFrom();

                    dice1To = ui.getMatrixIndexTo();

                    dice2From = ui.getMatrixIndexFrom();

                    dice2To = ui.getMatrixIndexTo();

                    if (gameSetUp.getWindowPatternCard().isPlaceable(
                            gameSetUp.getWindowPatternCard().getDice(dice1From), dice1To,
                            false, false, false)) {

                        gameSetUp.getWindowPatternCard().placeDice(gameSetUp.getWindowPatternCard().getDice(dice1From), dice1To,
                                false, false, false);

                        if (gameSetUp.getWindowPatternCard().isPlaceable(
                                gameSetUp.getWindowPatternCard().getDice(dice2From), dice2To,
                                false, false, false)) {

                            gameSetUp.getWindowPatternCard().placeDice(gameSetUp.getWindowPatternCard().getDice(dice2From), dice2To,
                                    false, false, false);

                            gameSetUp.getWindowPatternCard().removeDice(dice1To);
                            gameSetUp.getWindowPatternCard().removeDice(dice2To);
                            flag = true;
                        } else {
                            Dice dice1 = gameSetUp.getWindowPatternCard().removeDice(dice1To);
                            gameSetUp.getWindowPatternCard().placeDice(dice1, dice1From, true, true, true);
                        }
                    }
                    if (!flag) {
                        ui.printMessage("Dices chosen not valid");
                    }
                } while (!flag);

                return new MoveTwoDice(dice1From, dice1To, dice2From, dice2To, gameSetUp);
            default:
                break;

        }
        return action;
    }


    private boolean exists2DiceValidMove(GameSetUp gameSetUp) {

        System.out.println(gameSetUp.getWindowPatternCard().getAllDices().size());
        for (int i = 0; i < 20; i++) {
            if (gameSetUp.getWindowPatternCard().getDice(i) != null)
                for (int j = 0; j < 20; j++) {
                    if (j != i)
                        if (gameSetUp.getWindowPatternCard().getDice(j) != null) {
                            System.out.println(" i : " + i + " j :" + j);
                            Dice dice1 = gameSetUp.getWindowPatternCard().removeDice(i);
                            Dice dice2 = gameSetUp.getWindowPatternCard().removeDice(j);
                            if (scanMatrix(dice1, gameSetUp.getWindowPatternCard(), false, false, false, i))
                                if (scanMatrix(dice2, gameSetUp.getWindowPatternCard(), false, false, false, j)) {
                                    gameSetUp.getWindowPatternCard().placeDice(dice1, i, true, true, true);
                                    gameSetUp.getWindowPatternCard().placeDice(dice2, j, true, true, true);
                                    return true;
                                }
                            gameSetUp.getWindowPatternCard().placeDice(dice1, i, true, true, true);
                            gameSetUp.getWindowPatternCard().placeDice(dice2, j, true, true, true);
                        }


                }

        }
        return false;
    }

    private boolean existsValidMove(ArrayList<Dice> dicesToTest, WindowPatternCard windowPatternCard, boolean ignoreColor, boolean ignoreValue, boolean ignoreAdjacency) {
        for (Dice dice : dicesToTest) {
            if (scanMatrix(dice, windowPatternCard, ignoreColor, ignoreValue, ignoreAdjacency, -1))
                return true;
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
}
