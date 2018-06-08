package it.polimi.ingsw.Server.Game.Cards.PublicCardEffects;

import it.polimi.ingsw.Server.Game.Cards.WindowPatternCard;
import it.polimi.ingsw.Server.Game.Components.Dice;
import it.polimi.ingsw.Server.Game.GameRules.Restriction;

import java.util.ArrayList;

public class CountSets implements CountEffect {


    ArrayList<Restriction> restrictions;
    @Override
    public int getPoints(WindowPatternCard wp) {

        ArrayList<Dice> dices = wp.getAllDices();

        ArrayList<Restriction> resToTest = new ArrayList<>();

        for (Dice d : dices)
            resToTest.add(Restriction.parseRestricion(d.getValue()));



        int minimum = resToTest.size();
        for (Restriction res : restrictions){

            int count = 0 ;

            for (Restriction resTes : resToTest)
                if (res.equals(resTes))
                    count ++;
            if (minimum>count)
                minimum = count;
        }


        return minimum;
    }

    @Override
    public void setRestriction(ArrayList<Restriction> restrictions) {
        this.restrictions = restrictions;
    }
}
