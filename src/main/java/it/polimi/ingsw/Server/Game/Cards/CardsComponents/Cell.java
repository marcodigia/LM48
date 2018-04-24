package it.polimi.ingsw.Server.Game.Cards.CardsComponents;

import it.polimi.ingsw.Server.Game.Components.Dice;
import it.polimi.ingsw.Server.Game.Components.Restriction;

import java.util.ArrayList;

public class Cell {



    private ArrayList<Cell> adjacency;
    private Dice dice = null ;
    private Restriction restrictions ;


     Cell() {
        adjacency = new ArrayList<Cell>();
        restrictions = new Restriction();

    }

     void setCell(Cell cell ){
        adjacency.add(cell);
    }

    public Dice getDice() {
        return dice;
    }

     void addRestricion(String s ){
        restrictions.addRestriction(s);
    }

     void removeDice(Dice dice ){

        this.dice = null ;
        restrictions.removeRestricion(dice.getDiceColor().getColor());
        restrictions.removeRestricion(dice.getValue());

    }


    //TODO: prima piazzo il dado poi verifico che sia piazzablie??

     void setDice(Dice dice ) {
        this.dice = dice;
        restrictions.addRestriction(dice.getDiceColor().getColor());
        restrictions.addRestriction(dice.getValue());


    }



    public boolean isPlaceble(Dice dice , boolean ignore_color , boolean ignore_value ){


        for( Cell s :  adjacency ){
            if (!s.verify(dice , ignore_color , ignore_value))
                return false;
        }
        return true ;
    }



    private boolean verify(Dice dice , boolean IGNORE_COLOR , boolean IGNORE_VALUE ){

        Boolean colorRes = restrictions.verifyRestrictions( dice.getDiceColor().getColor() ) || IGNORE_COLOR;
        Boolean valueRes = restrictions.verifyRestrictions( dice.getValue()) || IGNORE_VALUE;
        return colorRes && valueRes;
    }

    public boolean isEmpty(){
        return dice == null;
    }

    public ArrayList<String> getRestrictions(){
        return restrictions.getRestrictions();
    }

}
