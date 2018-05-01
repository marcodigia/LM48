package it.polimi.ingsw.Server.Game.Cards;

import it.polimi.ingsw.Client.View.UI;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Actions;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Complex.ChangeDiceValueByOne;
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


    public Actions getActions(UI ui, GameSetUp gameSetUp) {

        Actions action = null;
        switch (id) {

            case "13":
                System.out.println("Did nothing");
                action = new ChangeDiceValueByOne(1, ui.getDraftPoolIndex(), gameSetUp);
                ui.getMatrixIndexTo();
                ui.getDraftPoolIndex();
                break;
            default:
                break;

        }
        return action;
    }
}
