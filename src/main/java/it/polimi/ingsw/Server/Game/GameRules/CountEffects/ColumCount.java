package it.polimi.ingsw.Server.Game.GameRules.CountEffects;

import it.polimi.ingsw.Server.Game.Cards.WindowPatternCard;
import it.polimi.ingsw.Server.Game.Components.Dice;
import it.polimi.ingsw.Server.Game.GameRules.Restriction;

import java.util.ArrayList;

public class ColumCount implements CountEffect {


    private ArrayList<Restriction> restrictions = new ArrayList<>();

    private int value ;

    public ColumCount(int value) {
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


    private ArrayList<Dice> getCol(int i , WindowPatternCard wp ){

        ArrayList<Dice> col = new ArrayList<>();

        for (int j =0 ; j < 4 ; j++){

            Dice d = wp.getDice(i+ j*5);
            if (d != null)
                col.add( d  );

        }

        return col;
    }

    /**
     * This method is invoked when a windowPatternCard need to be evalueted base on a Color Restriciton
     * @param wp is the windowPattern wich is to be evalueted
     * @return the score of the colums based on the restriction provided in the constructor of the class
     */
    private int getScoreColor(WindowPatternCard wp){

        //Verifico colori diversi
        int score = 0;
        for (int i = 0 ; i < 5 ; i ++ ){ // for each col
            ArrayList<Dice> col = getCol(i,wp);
            boolean point = true ;
            if (col.size()<4)
                continue;
            for (int j = 0 ; j < col.size(); j++){ // then test for each dice
                for (int k = 0 ; k < col.size() ; k++){ // compare with every other dice
                    if (j==k)
                    {k++; }
                    else {
                        if (col.get(k).getDiceColor().equals(col.get(j).getDiceColor()))
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
     * @return the score of the colums based on the restriction provided in the constructor of the class
     */
    private int getScoreValue(WindowPatternCard wp){

        int score = 0;
        for (int i = 0 ; i < 5 ; i ++ ){ // for each col
            ArrayList<Dice> col = getCol(i,wp);
            boolean point = true ;
            if (col.size()<4)
                continue;
            for (int j = 0 ; j < col.size(); j++){ // then test for each dice
                for (int k = 0 ; k < col.size() ; k++){ // compare with every other dice
                    if (j==k)
                    {k++; }
                    else {
                        if (col.get(k).getValue().equals(col.get(j).getValue()))
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
