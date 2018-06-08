package it.polimi.ingsw.Server.Game.Cards.PublicCardEffects;

import it.polimi.ingsw.Server.Game.Cards.WindowPatternCard;
import it.polimi.ingsw.Server.Game.Components.Dice;
import it.polimi.ingsw.Server.Game.GameRules.Restriction;

import java.util.ArrayList;

public class ColumEffect implements CountEffect {


    ArrayList<Restriction> restrictions = new ArrayList<>();
    @Override
    public int getPoints(WindowPatternCard wp) {


        ArrayList<Dice> dice =  wp.getAllDices();

        ArrayList<Restriction> resToTest = new ArrayList<>();



        return 0;
    }

    @Override
    public void setRestriction(ArrayList<Restriction> restrictions) {
        this.restrictions = restrictions;
    }
}
