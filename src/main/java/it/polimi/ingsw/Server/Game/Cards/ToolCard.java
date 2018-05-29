package it.polimi.ingsw.Server.Game.Cards;

import it.polimi.ingsw.Server.Game.Cards.CardsComponents.Id;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Actions;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Complex.*;
import it.polimi.ingsw.Server.Game.Utility.DiceColor;

import java.util.ArrayList;

public class ToolCard implements Drawable {
    private DiceColor color;
    private String name;
    private Id id;
    private String effect;
    private String restriction;
    private int cost;

    //The Id of the toolcard must be in Id
    public ToolCard(ArrayList<String> pattern) {

        color = DiceColor.resolveColor(pattern.get(0));
        name = pattern.get(1);
        id = Id.valueOf("_"+pattern.get(2));

        effect = pattern.get(3);
        restriction = pattern.get(4);
        cost = 0;
    }


    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }


    public String getID() {
        return id.getId();
    }


    public Actions getActions() {

        if (cost == 0 )
            cost++;

        Actions action = null;
        switch (id) {

            case _13:
                System.out.println("Did nothing");
                action = new ChangeDiceValue(0);
                break;
            case _1:
                action = new ChangeDiceValue(0);
                break;
            case _2:
                action = new MoveOneDieIgnoringColor();
                break;
            case _3:
                action = new MoveOneDiceIgnoringValue();
                break;
            case _4:
                action = new MoveTwoDice();
                break;
            case _6:
                action = new RerollDraftedDice(0);
                break;
            case _7:
                action = new RerollDraftedDice(-1);
                break;
            case _10:
                action = new ChangeDiceValue(7);
                break;
            default:
                break;

        }
        return action;
    }

}
