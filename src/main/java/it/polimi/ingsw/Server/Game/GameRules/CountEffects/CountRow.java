package it.polimi.ingsw.Server.Game.GameRules.CountEffects;

import it.polimi.ingsw.Server.Game.Cards.WindowPatternCard;
import it.polimi.ingsw.Server.Game.Components.Dice;
import it.polimi.ingsw.Server.Game.GameRules.Restriction;

import java.util.ArrayList;

public class CountRow implements CountEffect {



    private ArrayList<Restriction> restrictions = new ArrayList<>();

    private int value ;

    public CountRow(int value) {
        this.value = value;
    }

    @Override
    public int getPoints(WindowPatternCard wp) {

        boolean restrictionType = restrictions.get(0).isColor();


        if (restrictionType)
            return getScoreColor(wp);
        else
            return getScoreValue(wp);
    }

    @Override
    public void setRestriction(ArrayList<Restriction> restrictions) {
        this.restrictions = restrictions;
    }


    private ArrayList<Dice> getRow(int i , WindowPatternCard wp ){

        ArrayList<Dice> row = new ArrayList<>();

        for (int j =0 ; j < 5 ; j++){

            Dice d = wp.getDice(i*5+ j);
            if (d != null)
                row.add( d  );

        }

        return row;
    }



    /**
     * This method is invoked when a windowPatternCard need to be evalueted base on a Color Restriciton
     * @param wp is the windowPattern wich is to be evalueted
     * @return the score of the row based on the restriction provided in the constructor of the class
     */

    private int getScoreColor(WindowPatternCard wp){

        //Verifico colori diversi
        int score = 0;
        for (int i = 0 ; i < 4 ; i ++ ){ // for each col
            ArrayList<Dice> row = getRow(i,wp);
            boolean point = true ;
            if (row.size()<5)
                continue;
            for (int j = 0 ; j < row.size(); j++){ // then test for each dice
                for (int k = 0 ; k < row.size() ; k++){ // compare with every other dice
                    if (j==k)
                    {k++; }
                    else {
                        if (row.get(k).getDiceColor().equals(row.get(j).getDiceColor()))
                            point = false;
                    }
                }

            }
            if (point)
                score+=value;

        }
        return score;
    }

    /**
     * This method is invoked when a windowPatternCard need to be evalueted base on a Value Restriciton
     * @param wp is the windowPattern wich is to be evalueted
     * @return the score of the row based on the restriction provided in the constructor of the class
     */
    private int getScoreValue(WindowPatternCard wp){

        int score = 0;
        for (int i = 0 ; i < 4 ; i ++ ){ // for each col
            ArrayList<Dice> row = getRow(i,wp);
            boolean point = true ;
            if (row.size()<5)
                continue;
            for (int j = 0 ; j < row.size(); j++){ // then test for each dice
                for (int k = 0 ; k < row.size() ; k++){ // compare with every other dice
                    if (j==k)
                    {k++; }
                    else {
                        if (row.get(k).getValue().equals(row.get(j).getValue()))
                            point = false;
                    }
                }

            }
            if (point)
                score+=value;

        }
        return score;
    }




}
