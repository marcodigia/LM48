package it.polimi.ingsw.Server.Game.Cards.CardsComponents;

import it.polimi.ingsw.Server.Game.Components.Dice;
import it.polimi.ingsw.Server.Game.GameRules.Restriction;
import it.polimi.ingsw.Server.Game.Utility.ANSI_COLOR;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Cell implements Serializable{


    //private Restriction restriction;
    private Restriction cellRestriction;
    private ArrayList<Cell> adjacencyOrthogonal; //adjacent cells horizontal and vertical
    private Dice dice = null ;
    private ArrayList<Cell> adjaceny; // adjacent cell horizontal vertical and diagonal

    Cell() {
         adjacencyOrthogonal = new ArrayList<>();
         adjaceny = new ArrayList<>();
         // restriction = new Restriction();


    }

    @Override
    public String toString() {

        return "("  + cellRestriction + "," + dice + " )" ;
    }


    /**
     * Removes the dice from the cell
     */
    void removeDice() {

        this.dice = null ;

    }

    /**
     * @param dice the dice wich need to be tested
     * @param ignore_color boolean wich indicates if the color restriction can be ignored
     * @param ignore_value boolean wich indicates if the value restriction can be ignored
     * @param ignore_adjacency boolean wich indicates if the adjacency restriction can be ignored
     * @return true if the dice is placebale and all the restriction are satified
     */
    boolean isPlaceble(Dice dice, boolean ignore_color, boolean ignore_value, boolean ignore_adjacency) {

        return verifyColorAndValue(dice, ignore_color, ignore_value) && verifyAdjacency(ignore_adjacency);
    }

    /**
     * @return the Restriction of this cell
     */
    public Restriction getRestriction() {
        return cellRestriction;
    }

    /**
     * @param restriction the restriction to be set to this cell
     */
    void setRestriction(Restriction restriction) {
        this.cellRestriction = restriction;
    }

    /**
     * @param diceToPlace is the dice to be placed
     * @param ignoreColor boolean wich indicates if the color restriction can be ignored
     * @param ignoreValue boolean wich indicates if the value restriction can be ignored
     * @return true only if all the dices in the orthogonal cell are not in contrast with the dice
     */
    // return true only if all the dices in the orthogonal cell are not in contrast with the dice
    boolean verifyColorAndValue(Dice diceToPlace, boolean ignoreColor, boolean ignoreValue) {

        // convert the DiceInformation to a Restriction
        Restriction diceToPlaceColor = Restriction
                .parseRestricion(diceToPlace
                        .getDiceColor()
                        .getColor());
        Restriction diceToPlaceValue = Restriction
                .parseRestricion(diceToPlace
                .getValue());
        // NB. == is ok because restriction is an enum
        //Consider value restriction

        if (cellRestriction != Restriction.NONE && !ignoreValue &&
                ((cellRestriction==Restriction.ONE || cellRestriction==Restriction.TWO ||
                cellRestriction==Restriction.THREE || cellRestriction==Restriction.FOUR ||
                cellRestriction==Restriction.FIVE || cellRestriction==Restriction.SIX) &&
                cellRestriction!=diceToPlaceValue))
            return false;
        //Consider color restriction
        if(cellRestriction!=Restriction.NONE && !ignoreColor &&
                ((cellRestriction==Restriction.BLUE || cellRestriction==Restriction.GREEN ||
                cellRestriction==Restriction.PURPLE || cellRestriction == Restriction.RED ||
                cellRestriction==Restriction.YELLOW) && cellRestriction!=diceToPlaceColor))
            return false;
        /*if (!((cellRestriction == (diceToPlaceColor) || ignoreColor) ||
                (cellRestriction == (diceToPlaceValue) || ignoreValue) ||
                cellRestriction == Restriction.NONE))
            return false;*/


        for (Cell adjacentOrthogonalCell : adjacencyOrthogonal) {
            if (!adjacentOrthogonalCell.isEmpty()) {

                boolean colorIsEqual = diceToPlace.getDiceColor().equals(adjacentOrthogonalCell.getDice().getDiceColor());
                boolean valueIsEqual = diceToPlace.getValue().equals(adjacentOrthogonalCell.getDice().getValue());
                if(colorIsEqual && (!ignoreColor))
                    return false;
                if(valueIsEqual && (!ignoreValue))
                    return false;
            }
        }
        return true;
    }

    /**
     * @param ignoreAdjacency boolean wich indicates if the adjacency restriction can be ignored
     * @return true only if exists a cell adjacent to this one that is not empty
     */
    //return true only if exists a cell adjacent to this one that is not empty
    boolean verifyAdjacency(boolean ignoreAdjacency) {
        if (ignoreAdjacency)
            return true;
        for (Cell adjacentCell : adjaceny) {
            if (!adjacentCell.isEmpty())
                return true;
        }
        return false;
    }

    /**
     * @return true if the cell is empty
     */
    //return true if the cell is empty
    boolean isEmpty(){
        return dice == null;
    }

    /**
     * @param diceToPlace the dice wich need to be tested
     * @param ignoreColor boolean wich indicates if the color restriction can be ignored
     * @param ignoreValue boolean wich indicates if the value restriction can be ignored
     * @param ignoreAdjacency boolean wich indicates if the adjacency restriction can be ignored
     * @return according to the current restrictions return true if the method managed to correctly place the dice
     */
    //if is according to the current restrictions return true if the method managed to correctly set the dice
    boolean putDice(Dice diceToPlace, boolean ignoreColor, boolean ignoreValue, boolean ignoreAdjacency) {
        if (!isEmpty())
            return false;
        if (verifyColorAndValue(diceToPlace, ignoreColor, ignoreValue) && verifyAdjacency(ignoreAdjacency)) {
            this.dice = diceToPlace;
            return true;
        }
        return false;

    }

    /**
     * @return the dice in this cell
     */
    //return the dice in this cell
    public Dice getDice() {
        return dice;
    }

    /**
     * used during the initialization of cell in matrix
     * @param cell the cell to be added at the list of adjcency of this cell
     * @param isDiagonal true if the cell to be added is in a diagonal position respect to this one
     */
    //used during the initialization of cell in matrix
    void setCell(Cell cell, boolean isDiagonal) {

        if (!isDiagonal)
            adjacencyOrthogonal.add(cell);

        adjaceny.add((cell));

    }
}
