package it.polimi.ingsw.Server.Game.GameRules.CountEffects;

import it.polimi.ingsw.Server.Game.Cards.WindowPatternCard;
import it.polimi.ingsw.Server.Game.Components.Dice;
import it.polimi.ingsw.Server.Game.GameRules.Restriction;

import java.io.Serializable;
import java.util.ArrayList;

public class DiagonalEffect implements CountEffect {

    private int value ;

    public DiagonalEffect(int value) {
        this.value = value;
    }

    @Override
    public int getPoints(WindowPatternCard wp) {

        int score = 0;
        for (int i = 0 ; i < 20 ; i ++) {
            ArrayList<Dice> dices = diagonalAdjacency(i, wp);
            boolean flag = false;
            for (Dice d : dices){
                if (wp.getDice(i) == null || d == null)
                    continue;
                if (wp.getDice(i).getDiceColor().equals(d.getDiceColor()))
                    flag = true;
            }
            if (flag)
                score+=value;
        }
        return score;
    }

    @Override
    public void setRestriction(ArrayList<Restriction> restrictions) {

    }


    /**
     * @param i the index of the cell which diagonal adjacency needs to be tested
     * @param wp the WindowPatternCard to be evalueted
     * @return an ArrayList containing all the adjacent cells to the one wich the index were providden
     */
    private ArrayList<Dice> diagonalAdjacency(int i , WindowPatternCard wp){

        ArrayList<Dice> dices = new ArrayList<>();
        if (( (i-6)%5 < i%5 ) && i>5)
            dices.add(wp.getDice(i-6));
        if (( (i-4)%5 > i%5) && i > 5)
            dices.add(wp.getDice(i-4));
        if (((i+6)%5> i%5) && i < 15)
            dices.add(wp.getDice(i+6));
        if(((i+4)%5 < i%5) && i < 15)
            dices.add(wp.getDice(i+4));
        return dices;
    }
}


