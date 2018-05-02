package it.polimi.ingsw.Server.Game.Cards;

import it.polimi.ingsw.Client.View.UI;
import it.polimi.ingsw.Exceptions.NoPossibleValidMovesException;
import it.polimi.ingsw.Server.Game.Components.Dice;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Actions;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Complex.ChangeDiceValueByOne;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Complex.MoveOneDieIgnoringColor;
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
            case "2":
                if (!existsValidMove(gameSetUp.getDraftPool().getDraft(), gameSetUp.getWindowPatternCard(), true, false, false)) {
                    ui.printMessage("No possible valid moves");
                    throw new NoPossibleValidMovesException();
                }
                boolean flag = true;
                do {
                    int matrixTo = ui.getMatrixIndexTo();
                    int draftPooolFrom = ui.getDraftPoolIndex();
                    if (gameSetUp.getWindowPatternCard().isPlaceable(gameSetUp.getDraftPool().getDice(draftPooolFrom), matrixTo, true, false, false)) {
                        flag = false;
                        action = new MoveOneDieIgnoringColor(gameSetUp, draftPooolFrom, matrixTo);
                    }
                } while (flag);

                break;

            default:
                break;

        }
        return action;
    }

    private boolean existsValidMove(ArrayList<Dice> dicesToTest, WindowPatternCard windowPatternCard, boolean ignoreColor, boolean ignoreValue, boolean ignoreAdjacency) {
        for (Dice dice : dicesToTest) {
            for (int i = 0; i < 20; i++) {
                if (windowPatternCard.isPlaceable(dice, i, ignoreColor, ignoreValue, ignoreAdjacency)) {
                    return true;
                }
            }
        }
        return false;
    }
}
