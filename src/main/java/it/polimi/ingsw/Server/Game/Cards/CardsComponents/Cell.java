package it.polimi.ingsw.Server.Game.Cards.CardsComponents;

import it.polimi.ingsw.Server.Game.Components.Dice;
import it.polimi.ingsw.Server.Game.Components.Restriction;

import java.util.ArrayList;

public class Cell {


    private ArrayList<Cell> adjacencyHV; //adjacent cells horizontal and vertical
    private ArrayList<Cell> adjacenyHVD; // adjacent cell horizontal vertical and diagonal
    private Dice dice = null ;
    private Restriction restrictions ;


     Cell() {
         adjacencyHV = new ArrayList<Cell>();
         adjacenyHVD = new ArrayList<Cell>();
        restrictions = new Restriction();
         adjacencyHV.add(this);

    }

    void setCell(Cell cell, boolean isDiagonal) {

        if (!isDiagonal)
            adjacencyHV.add(cell);

        adjacenyHVD.add((cell));

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


        for (Cell s : adjacencyHV) {
            if (!s.verify(dice , ignore_color , ignore_value))
                return false;
        }
        return true ;
    }



    private boolean verify(Dice dice , boolean IGNORE_COLOR , boolean IGNORE_VALUE ){

        boolean valueRes = IGNORE_VALUE;
        boolean colorRes = IGNORE_COLOR;

        if (!IGNORE_COLOR)
            colorRes = restrictions.verifyRestrictions(dice.getDiceColor().getColor());
        if (!IGNORE_VALUE)
            valueRes = restrictions.verifyRestrictions(dice.getValue());

        return colorRes && valueRes;
    }

    public boolean isEmpty(){
        return dice == null;
    }

    public ArrayList<String> getRestrictions(){
        return restrictions.getRestrictions();
    }


}
