package it.polimi.ingsw.Server.Game.GameRules.CountEffects;

import it.polimi.ingsw.Server.Game.Cards.WindowPatternCard;
import it.polimi.ingsw.Server.Game.Components.Dice;
import it.polimi.ingsw.Server.Game.GameRules.Restriction;
import it.polimi.ingsw.Server.Game.Utility.DiceColor;

import java.util.ArrayList;

public class CountSets implements CountEffect {


    private ArrayList<Restriction> restrictions;

    private int value;

    public CountSets(int value) {
        this.value = value;
    }

    @Override
    public int getPoints(WindowPatternCard wp) {

        boolean resType = restrictions.get(0).isColor();
        if (resType)
            return countSetsOfRestricionsColor(restrictions,wp);
        else
            return countSetsOfRestricionsValue(restrictions,wp);
    }



    @Override
    public void setRestriction(ArrayList<Restriction> restrictions) {
        this.restrictions = restrictions;
    }


    /**
     * This method is invoked when a windowPatternCard need to be evalueted base on a Value Restriciton
     * @param resToTest is an ArrayList of Restriction that will be used to evaluate the windowPatternCard
     * @param wp is the WindowPatternCard to be evalueted
     * @return the score of the sets based on the restrictions provided
     */
    private int countSetsOfRestricionsValue(ArrayList<Restriction> resToTest , WindowPatternCard wp ){
        ArrayList<Dice> dices = wp.getAllDices();
        int min = 20;
        for (Restriction res :  resToTest){
            int rescount = 0 ;
            for (Dice d :dices){
                if (d.getValue().equals(res.getRestrictionType())){
                    rescount++;
                }
            }
            if (rescount<min)
                min=rescount;

        }

        return min*value;
    }
    /**
     * This method is invoked when a windowPatternCard need to be evalueted base on a Color Restriciton
     * @param resToTest is an ArrayList of Restriction that will be used to evaluate the windowPatternCard
     * @param wp is the WindowPatternCard to be evalueted
     * @return the score of the sets based on the restrictions provided
     */
    private int countSetsOfRestricionsColor(ArrayList<Restriction> resToTest , WindowPatternCard wp ){
        ArrayList<Dice> dices = wp.getAllDices();
        int min = 20;
        for (Restriction res :  resToTest){
            int rescount = 0 ;
            for (Dice d :dices){
                if (d.getDiceColor().equals(DiceColor.resolveColor(res.getRestrictionType()))){
                    rescount++;
                }
            }
            if (rescount<min)
                min=rescount;

        }

        return min*value;
    }
}
