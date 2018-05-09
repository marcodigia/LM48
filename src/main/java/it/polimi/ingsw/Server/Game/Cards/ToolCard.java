package it.polimi.ingsw.Server.Game.Cards;

import it.polimi.ingsw.Client.View.UI;
import it.polimi.ingsw.Exceptions.NoPossibleValidMovesException;
import it.polimi.ingsw.Server.Game.Cards.CardsComponents.Id;
import it.polimi.ingsw.Server.Game.Components.Dice;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Actions;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Complex.*;
import it.polimi.ingsw.Server.Game.GameRules.GameContext;
import it.polimi.ingsw.Server.Game.Utility.DiceColor;

import java.util.ArrayList;

public class ToolCard implements Drawable {
    private DiceColor color;
    private String name;
    private Id id;
    private String effect;
    private String restriction;

    //The Id of the toolcard must be in Id
    public ToolCard(ArrayList<String> pattern) {

        color = DiceColor.resolveColor(pattern.get(0));
        name = pattern.get(1);
        id = Id.valueOf("_"+pattern.get(2));

        effect = pattern.get(3);
        restriction = pattern.get(4);

    }


    public String getID() {
        return id.getId();
    }


    public Actions getActions(UI ui, GameContext gameContext) throws NoPossibleValidMovesException {

        Actions action = null;
        switch (id) {

            case _13:
                System.out.println("Did nothing");
                action = new ChangeDiceValueByOne();
                break;
            case _1:

                action = new ChangeDiceValueByOne();
                break;


            case _2: // The dice cannot be choose if it is not possible to place it, if it is the first round it is impossible to move the dice due to adjacency restriction

                action = new MoveOneDieIgnoringColor();
                break;


            case _3: // The dice cannot be choose if it is not possible to place it, if it is the first round it is impossible to move the dice due to adjacency restriction

                action = new MoveOneDiceIgnoringValue();

                break;


            case _4:
                if (gameContext.getWindowPatternCard().getAllDices().size() < 2 || gameContext.getWindowPatternCard().getAllDices().size() > 19)
                    throw new NoPossibleValidMovesException();
                boolean flag = false;
                if (!exists2DiceValidMove(gameContext))
                    throw new NoPossibleValidMovesException();
                int dice1From = -1;
                int dice1To = -1;
                int dice2From = -1;
                int dice2To = -1;
                do {
                    // if i due dadi non vanno in constrasto tra di loro quando vengono piazzati piazza

                    try {
                        dice1From = ui.getMatrixIndexFrom();
                    } catch (it.polimi.ingsw.Exceptions.EndOfTurnException e) {
                        e.printStackTrace();
                    }

                    try {
                        dice1To = ui.getMatrixIndexTo();
                    } catch (it.polimi.ingsw.Exceptions.EndOfTurnException e) {
                        e.printStackTrace();
                    }

                    try {
                        dice2From = ui.getMatrixIndexFrom();
                    } catch (it.polimi.ingsw.Exceptions.EndOfTurnException e) {
                        e.printStackTrace();
                    }

                    try {
                        dice2To = ui.getMatrixIndexTo();
                    } catch (it.polimi.ingsw.Exceptions.EndOfTurnException e) {
                        e.printStackTrace();
                    }

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
                        try {
                            ui.printMessage("Dices chosen not valid");
                        } catch (it.polimi.ingsw.Exceptions.EndOfTurnException e) {
                            e.printStackTrace();
                        }
                    }
                } while (!flag);

                return new MoveTwoDice(dice1From, dice1To, dice2From, dice2To, gameContext);
            case _6:
                return new RerollDraftedDice();
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
