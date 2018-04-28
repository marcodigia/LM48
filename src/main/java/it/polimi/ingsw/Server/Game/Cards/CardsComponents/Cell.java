package it.polimi.ingsw.Server.Game.Cards.CardsComponents;

import it.polimi.ingsw.Server.Game.Components.Dice;
import it.polimi.ingsw.Server.Game.GameRules.Restriction;

import java.util.ArrayList;

public class Cell {


    //private Restriction restriction;
    Restriction restriction2;
    private ArrayList<Cell> adjacencyOrthogonal; //adjacent cells horizontal and vertical
    private Dice dice = null ;
    private ArrayList<Cell> adjaceny; // adjacent cell horizontal vertical and diagonal


     Cell() {
         adjacencyOrthogonal = new ArrayList<>();
         adjaceny = new ArrayList<>();
         // restriction = new Restriction();


    }


    void removeDice() {

        this.dice = null ;

    }

    boolean isPlaceble(Dice dice, boolean ignore_color, boolean ignore_value, boolean ignore_adjacency) {

        return verifyColorAndValue(dice, ignore_color, ignore_value) && verifyAdjacency(dice, ignore_adjacency);
    }

    public Restriction getRestriction() {
        return restriction2;
    }

    void setRestriction(Restriction cellRestriction) {

    }

    // return true only if all the dices in the orthogonal cell are not in contrast with the dice
    boolean verifyColorAndValue(Dice diceToPlace, boolean ignoreColor, boolean ignoreValue) {
        for (Cell adjacentOrthogonalCell : adjacencyOrthogonal) {
            if (!adjacentOrthogonalCell.isEmpty()) {
                boolean colorIsEqual = diceToPlace.getDiceColor().equals(adjacentOrthogonalCell.getDice().getDiceColor());
                boolean valueIsEqual = diceToPlace.getValue().equals(adjacentOrthogonalCell.getDice().getValue());

                if ((colorIsEqual || ignoreColor) ||
                        (valueIsEqual || ignoreValue)) {
                    return false;

                }
            }
        }
        return true;
    }

    //return true only if exists a cell adjacent to this one that is not empty
    boolean verifyAdjacency(Dice diceToPlace, boolean ignoreAdjacency) {
        if (ignoreAdjacency)
            return true;
        for (Cell adjacentCell : adjaceny) {
            if (!adjacentCell.isEmpty())
                return true;
        }
        return false;
    }

    //return true if the cell is empty
    public boolean isEmpty(){
        return dice == null;
    }

    //if is according to the current restrictions return true if the method managed to correctly set the dice
    public boolean putDice(Dice diceToPlace, boolean ignoreColor, boolean ignoreValue, boolean ignoreAdjacency) {
        if (verifyColorAndValue(diceToPlace, ignoreColor, ignoreValue) && verifyAdjacency(diceToPlace, ignoreAdjacency)) {
            this.dice = diceToPlace;
            return true;
        }
        return false;
    }

    //return the dice in this cell
    public Dice getDice() {
        return dice;
    }

    //used during the initialization of cell in matrix
    void setCell(Cell cell, boolean isDiagonal) {

        if (!isDiagonal)
            adjacencyOrthogonal.add(cell);

        adjaceny.add((cell));

    }
}
