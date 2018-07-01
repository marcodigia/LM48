package it.polimi.ingsw.Server.Game.Cards;

import it.polimi.ingsw.Server.Game.Cards.CardsComponents.Id;
import it.polimi.ingsw.Server.Game.Components.Wallet;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Actions;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Complex.*;
import it.polimi.ingsw.Server.Game.GameRules.Restrictions.RestrictionType;
import it.polimi.ingsw.Server.Game.Utility.DiceColor;

import java.io.InputStream;
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
        cost = 1;
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

    public Boolean isUsable(Wallet wallet){
        return wallet.enoughToken(cost);
    }

    public void use(Wallet wallet){
        wallet.useToken(cost);
        if (cost==1)
            cost =2 ;
    }

    public Actions getActions() {


        Actions action = null;
        switch (id) {

            case _13:
                System.out.println("Did nothing");
                action = new ChangeDiceValue(0);
                break;
            case _1:
                action = new ChangeDiceValue(1);
                break;
            case _2:
                action = new MoveOneDice(true,false,false);
                break;
            case _3:
                action = new MoveOneDice(false,true,false);
                break;
            case _4:
                action = new MoveTwoDice(RestrictionType.None);
                break;
            case _5:
                action = new SwapDiceFromRoundTrack();
                break;
            case _6:
                action = new RerollDraftedDice(0);
                break;
            case _7:
                action = new RerollDraftedDice(-1);
                break;
            case _8:
                action = new PlaceDice(true);
                break;
            case _9:
                action = new ChangePlaceDiceAction(false,false,true);
                break;
            case _10:
                action = new ChangeDiceValue(2);
                break;
            case _11:
                action = new ChangeDiceValue(3);
                break;
            case _12:
                action = new MoveTwoDice(RestrictionType.Color);
                break;
            default:
                break;

        }
        return action;
    }

    public InputStream getToolCardImage(){
        String tcImageName = "tc"+id.name() + ".png";
        return getClass().getClassLoader().getResourceAsStream(tcImageName);
    }

}
