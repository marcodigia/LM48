package it.polimi.ingsw.Server.Game.Cards.CardsComponents;

import it.polimi.ingsw.Server.Game.Components.Dice;
import it.polimi.ingsw.Server.Game.GameRules.Restriction;
import it.polimi.ingsw.Server.Game.Utility.ANSI_COLOR;

import java.util.ArrayList;

public class Cell {


    //private Restriction restriction;
    Restriction cellRestriction;
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

        return ANSI_COLOR.ANSI_YELLOW + "{" + "dice :" + dice + " " + cellRestriction + " }" + ANSI_COLOR.ANSI_RESET;
    }


    void removeDice() {

        this.dice = null ;

    }

    boolean isPlaceble(Dice dice, boolean ignore_color, boolean ignore_value, boolean ignore_adjacency) {

        return verifyColorAndValue(dice, ignore_color, ignore_value) && verifyAdjacency(ignore_adjacency);
    }

    public Restriction getRestriction() {
        return cellRestriction;
    }

    void setRestriction(Restriction restriction) {
        this.cellRestriction = restriction;
    }

    // return true only if all the dices in the orthogonal cell are not in contrast with the dice
    boolean verifyColorAndValue(Dice diceToPlace, boolean ignoreColor, boolean ignoreValue) {
        // convert the DiceInformation to a Restriction
        Restriction diceToPlaceColor = Restriction.parseRestricion(diceToPlace.getDiceColor().getColor());
        Restriction diceToPlaceValue = Restriction.parseRestricion(diceToPlace.getValue());
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

        System.out.println(adjacencyOrthogonal);
        for (Cell adjacentOrthogonalCell : adjacencyOrthogonal) {
            if (!adjacentOrthogonalCell.isEmpty()) {
                boolean colorIsEqual = diceToPlace.getDiceColor().equals(adjacentOrthogonalCell.getDice().getDiceColor());
                boolean valueIsEqual = diceToPlace.getValue().equals(adjacentOrthogonalCell.getDice().getValue());

                System.out.println(ANSI_COLOR.ANSI_CYAN + " cell restriction :" + cellRestriction + " color Is equal " + colorIsEqual + " value is Equal" + valueIsEqual + ANSI_COLOR.ANSI_RESET);

                if(colorIsEqual && (!ignoreColor))
                    return false;
                if(valueIsEqual && (!ignoreValue))
                    return false;
            }
        }
        return true;
    }

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

    //return true if the cell is empty
    public boolean isEmpty(){
        return dice == null;
    }

    //if is according to the current restrictions return true if the method managed to correctly set the dice
    public boolean putDice(Dice diceToPlace, boolean ignoreColor, boolean ignoreValue, boolean ignoreAdjacency) {
        if (!isEmpty())
            return false;
        System.out.println("verify color value " + diceToPlace + " ignore " + ignoreColor + ignoreValue + ignoreAdjacency + " " + verifyColorAndValue(diceToPlace, ignoreColor, ignoreValue));
        if (verifyColorAndValue(diceToPlace, ignoreColor, ignoreValue) && verifyAdjacency(ignoreAdjacency)) {
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
