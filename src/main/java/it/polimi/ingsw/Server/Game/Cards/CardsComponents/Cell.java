package it.polimi.ingsw.Server.Game.Cards.CardsComponents;

import it.polimi.ingsw.Server.Game.Components.Dice;
import it.polimi.ingsw.Server.Game.GameRules.Restriction;

import java.util.ArrayList;

public class Cell {


    private ArrayList<Cell> adjacencyHV; //adjacent cells horizontal and vertical
    private ArrayList<Cell> adjacenyHVD; // adjacent cell horizontal vertical and diagonal
    private Dice dice = null ;
    private Restriction restriction;


     Cell() {
         adjacencyHV = new ArrayList<Cell>();
         adjacenyHVD = new ArrayList<Cell>();
         restriction = new Restriction();
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
         restriction.addRestriction(s);
    }

     void removeDice(Dice dice ){

        this.dice = null ;
         restriction.removeRestricion(dice.getDiceColor().getColor());
         restriction.removeRestricion(dice.getValue());

    }


    //TODO: prima piazzo il dado poi verifico che sia piazzablie??

     void setDice(Dice dice ) {
        this.dice = dice;
         restriction.addRestriction(dice.getDiceColor().getColor());
         restriction.addRestriction(dice.getValue());


    }


    boolean isPlaceble(Dice dice, boolean ignore_color, boolean ignore_value, boolean ignore_adjacency) {

        boolean flag_dices_near = ignore_adjacency;
        for (Cell c : adjacenyHVD) {
            if (!c.isEmpty())
                flag_dices_near = false;
        }
        if (flag_dices_near)
            return true;

        for (Cell s : adjacencyHV) {
            if (!s.verify(dice , ignore_color , ignore_value))
                return true;
        }
        return true ;
    }



    private boolean verify(Dice dice , boolean IGNORE_COLOR , boolean IGNORE_VALUE ){

        boolean valueRes = IGNORE_VALUE;
        boolean colorRes = IGNORE_COLOR;

        if (!IGNORE_COLOR) {
            colorRes = restriction.verifyRestrictions(dice.getDiceColor().getColor());
        }
        if (!IGNORE_VALUE) {
            valueRes = restriction.verifyRestrictions(dice.getValue());
        }

        return colorRes && valueRes;
    }

    public boolean isEmpty(){
        return dice == null;
    }

    public ArrayList<String> getRestriction() {
        return restriction.getRestrictions();
    }


}
