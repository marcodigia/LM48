package it.polimi.ingsw.Server.Game.Cards;

import it.polimi.ingsw.Client.View.UI;
import it.polimi.ingsw.Exceptions.NoPossibleValidMovesException;
import it.polimi.ingsw.Server.Game.Components.Dice;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Actions;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Complex.*;
import it.polimi.ingsw.Server.Game.GameRules.GameContext;
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


    public Actions getActions(UI ui, GameContext gameContext) throws NoPossibleValidMovesException {

        Actions action = null;
        switch (id) {

            case "13":
                System.out.println("Did nothing");
                action = new ChangeDiceValueByOne(1, ui.getDraftPoolIndex(), gameContext);
                ui.getMatrixIndexTo();
                ui.getDraftPoolIndex();
                break;
            case "1":

                action = new ChangeDiceValueByOne(ui.getAmmountToChange(), ui.getDraftPoolIndex(), gameContext);
                break;


            case "2": // The dice cannot be choose if it is not possible to place it, if it is the first round it is impossible to move the dice due to adjacency restriction
                if (!existsValidMove(gameContext.getDraftPool().getDraft(), gameContext.getWindowPatternCard(), true, false, false)) {
                    ui.printMessage("No possible valid moves");
                    throw new NoPossibleValidMovesException();
                }
                boolean flag = true;
                do {
                    int matrixTo = ui.getMatrixIndexTo();
                    int matrixIndexFromFrom = ui.getMatrixIndexFrom();
                    if (gameContext.getWindowPatternCard().isPlaceable(gameContext.getWindowPatternCard().getDice(matrixIndexFromFrom), matrixTo, true, false, false)) {
                        flag = false;
                        action = new MoveOneDieIgnoringColor(gameContext, matrixIndexFromFrom, matrixTo);
                    }
                } while (flag);

                break;


            case "3": // The dice cannot be choose if it is not possible to place it, if it is the first round it is impossible to move the dice due to adjacency restriction
                if (!existsValidMove(gameContext.getDraftPool().getDraft(), gameContext.getWindowPatternCard(), false, true, false)) {
                    ui.printMessage("No possible valid moves");
                    throw new NoPossibleValidMovesException();
                }
                boolean flag2 = true;
                do {
                    int matrixTo = ui.getMatrixIndexTo();
                    int matrixIndexFrom = ui.getMatrixIndexFrom();
                    if (gameContext.getWindowPatternCard().isPlaceable(gameContext.getWindowPatternCard().getDice(matrixIndexFrom), matrixTo, false, true, false)) {
                        flag2 = false;
                        action = new MoveOneDiceIgnoringValue(gameContext, matrixIndexFrom, matrixTo);
                    }
                } while (flag2);

                break;


            case "4":
                if (gameContext.getWindowPatternCard().getAllDices().size() < 2 || gameContext.getWindowPatternCard().getAllDices().size() > 19)
                    throw new NoPossibleValidMovesException();
                flag = false;
                if (!exists2DiceValidMove(gameContext))
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

                    if (gameContext.getWindowPatternCard().isPlaceable(
                            gameContext.getWindowPatternCard().getDice(dice1From), dice1To,
                            false, false, false)) {

                        gameContext.getWindowPatternCard().placeDice(gameContext.getWindowPatternCard().getDice(dice1From), dice1To,
                                false, false, false);

                        if (gameContext.getWindowPatternCard().isPlaceable(
                                gameContext.getWindowPatternCard().getDice(dice2From), dice2To,
                                false, false, false)) {

                            gameContext.getWindowPatternCard().placeDice(gameContext.getWindowPatternCard().getDice(dice2From), dice2To,
                                    false, false, false);

                            gameContext.getWindowPatternCard().removeDice(dice1To);
                            gameContext.getWindowPatternCard().removeDice(dice2To);
                            flag = true;
                        } else {
                            Dice dice1 = gameContext.getWindowPatternCard().removeDice(dice1To);
                            gameContext.getWindowPatternCard().placeDice(dice1, dice1From, true, true, true);
                        }
                    }
                    if (!flag) {
                        ui.printMessage("Dices chosen not valid");
                    }
                } while (!flag);

                return new MoveTwoDice(dice1From, dice1To, dice2From, dice2To, gameContext);
            case "6":
                int draftFrom = ui.getDraftPoolIndex();
                return new RerollDraftedDice(gameContext, draftFrom, ui);
            default:
                break;

        }
        return action;
    }


    private boolean exists2DiceValidMove(GameContext gameContext) {

        System.out.println(gameContext.getWindowPatternCard().getAllDices().size());
        for (int i = 0; i < 20; i++) {
            if (gameContext.getWindowPatternCard().getDice(i) != null)
                for (int j = 0; j < 20; j++) {
                    if (j != i)
                        if (gameContext.getWindowPatternCard().getDice(j) != null) {
                            System.out.println(" i : " + i + " j :" + j);
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
