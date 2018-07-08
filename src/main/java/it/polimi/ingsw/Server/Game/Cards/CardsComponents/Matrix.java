package it.polimi.ingsw.Server.Game.Cards.CardsComponents;

import it.polimi.ingsw.Exceptions.WrongPatternSizeException;
import it.polimi.ingsw.Packetable;
import it.polimi.ingsw.Server.Game.Components.Dice;
import it.polimi.ingsw.Server.Game.GameRules.Restriction;
import it.polimi.ingsw.Server.Game.Utility.CONSTANT;

import java.util.ArrayList;
import java.util.Iterator;

public class Matrix implements Packetable {
   private int lenght;
    private int height;
    private ArrayList<Cell> matrix;

    @Override
    public String toString() {
        StringBuilder matrixString = new StringBuilder(CONSTANT.matrixDelimenter);
        for (Cell cell : matrix)
            matrixString.append(cell.toString());
        matrixString.append(CONSTANT.matrixDelimenter);
        return matrixString.toString();
    }

    public Matrix(int lenght, int height) {
        this.lenght = lenght;
        this.height = height;
        matrix = new ArrayList<>();

        for (int i = 0; i < lenght * height; i++) {

            matrix.add(new Cell());
        }
    }


    // initialize the matrix by linking all the cells whith their neighbour


    /**
     * initialize the matrix by linking all the cells whith their neighbour
     */
    public void initialize() {

        for (int i = 0; i < matrix.size(); i++) {

            if (hasNord(i)) {
                Cell nord_cell = matrix.get(i - lenght);
                matrix.get(i).setCell(nord_cell, false);
                if (hasEst(i)) {
                    Cell diagonal_nord_cell_est = matrix.get(i - lenght + 1);
                    matrix.get(i).setCell(diagonal_nord_cell_est, true);
                }
                if (hasOvest(i)) {
                    Cell diagonal_nord_cell_ovest = matrix.get(i - lenght - 1);
                    matrix.get(i).setCell(diagonal_nord_cell_ovest, true);
                }
            }
            if (hasSud(i, matrix.size())) {

                Cell sud_cell = matrix.get(i + lenght);
                matrix.get(i).setCell(sud_cell, false);
                if (hasEst(i)) {
                    Cell diagonal_sud_cell_est = matrix.get(i + lenght + 1);
                    matrix.get(i).setCell(diagonal_sud_cell_est, true);
                }
                if (hasOvest(i)) {
                    Cell diagonal_sud_cell_ovest = matrix.get(i + lenght - 1);
                    matrix.get(i).setCell(diagonal_sud_cell_ovest, true);
                }
            }

            if (hasEst(i)) {
                Cell current_cell = matrix.get(i);
                Cell est_cell = matrix.get(i + 1);

                current_cell.setCell(est_cell, false);
            }
            if (hasOvest(i)) {
                Cell current_cell = matrix.get(i);
                Cell ovest_cell = matrix.get(i - 1);
                current_cell.setCell(ovest_cell, false);
            }

        }
    }

    /**
     * @param i the cell to be test
     * @return true if has a cell in a position at north respect to this one
     */
    private boolean hasNord(int i) {
        return i >= lenght;
    }

    /**
     * @param i the cell to be test
     * @return true if has a cell in a position at south respect to this one
     */
    private boolean hasSud(int i, int size) {
        return i < size - lenght;
    }

    /**
     * @param i the cell to be test
     * @return true if has a cell in a position at ovest respect to this one
     */
    private boolean hasOvest(int i) {
        return i % lenght >= 1;
    }

    /**
     * @param i the cell to be test
     * @return true if has a cell in a position at est respect to this one
     */
    private boolean hasEst(int i) {
        return i % lenght < lenght - 1;
    }
    //this method add all the restrictions


    /**
     * @param matrix_pattern an ArrayList of String wich reppresent the restriction to be add in each cell
     * @throws WrongPatternSizeException if the size of the pattern is incorect throws and exceptions
     */
    public void initialize_restricions( ArrayList<String> matrix_pattern ) throws WrongPatternSizeException {


        if (matrix_pattern.size() != matrix.size())
            throw new WrongPatternSizeException();

        for (int i = 0 ; i < matrix.size() ; i ++ )
        {

            matrix.get(i).setRestriction(Restriction.parseRestricion(matrix_pattern.get(i)));
        }

    }


    /**
     *
     * checks if the input is legal and if the the cell is empty than  if the restrictions are satisfied place the dice
     * @param dice the dice wich need to be tested
     * @param ignore_color boolean wich indicates if the color restriction can be ignored
     * @param ignore_value boolean wich indicates if the value restriction can be ignored
     * @param ignore_adjacency boolean wich indicates if the adjacency restriction can be ignored
     * @return true if the dice is placebale and all the restriction are satified
     */
    // check if the input is legal and if the the cell is empty than  if the restrictions are satisfied place the dice
    public boolean setDice(Dice dice, int coordinate, boolean ignore_color, boolean ignore_value, boolean ignore_adjacency) {
        return matrix.get(coordinate).putDice(dice, ignore_color, ignore_value, ignore_adjacency);



    }

    /**
     * Removes a dice at a given coordinate
     * @param coordinate the cell wich dice's has to be removed
     * @return the Dice removed
     */
    public Dice removeDice(int coordinate) {


        if (coordinate >= matrix.size() || coordinate < 0) {
            System.out.println("index out of bound");
            return null;
        }
        if (matrix.get(coordinate).getDice() == null) {
            return null;
        }


        Cell cell = matrix.get(coordinate);
        Dice dice = cell.getDice();
        cell.removeDice();


        return dice;

    }

    /**
     * check if a the dice is present and if the destination cell is empty than move the dice from a cell to the another
     * remove dice from cell_source before move it so can check if adjacent is still true
     * @param from the cell index of the source
     * @param to the cell index of the destination
     * @param ignore_color boolean wich indicates if the color restriction can be ignored
     * @param ignore_value boolean wich indicates if the value restriction can be ignored
     * @param ignore_adjacency boolean wich indicates if the adjacency restriction can be ignored
     * @return true if managed to correctly move the dice
     */
    //check if a the dice is present and if the destination cell is empty than move the dice from a cell to the another
    //remove dice from cell_source before move it so can check if adjacent is still true
    public boolean moveDice(int from, int to, boolean ignore_color, boolean ignore_value, boolean ignore_adjacency) {


        Cell cell_source = matrix.get(from);

        Cell cell_destination = matrix.get(to);


        if (!cell_source.isEmpty()){

            if (cell_destination.isEmpty()) {
                Dice dice_source = cell_source.getDice();
                cell_source.removeDice();
                if (cell_destination.putDice(dice_source, ignore_color, ignore_value, ignore_adjacency))
                    return true;
                else {
                    //Set everything true if dice was there it doesn't matter how but replace it there
                    cell_source.putDice(dice_source, true, true, true);

                }
            }

        }
        return false;
    }

    /**
     * @param dice the dice wich need to be tested
     * @param ignore_color boolean wich indicates if the color restriction can be ignored
     * @param ignore_value boolean wich indicates if the value restriction can be ignored
     * @param ignore_adjacency boolean wich indicates if the adjacency restriction can be ignored
     * @return true if the dice is placeable and all the restriction are satified
     */
    public boolean isPlaceable(Dice dice, int coordinate, boolean ignore_color, boolean ignore_value, boolean ignore_adjacency) {

       if (checkRestriction(dice, coordinate, ignore_color, ignore_value, ignore_adjacency))
        {
            Cell cell = matrix.get(coordinate);
            return cell.isEmpty();
        }

        return false;

    }

    /**
     * @param dice the dice wich need to be tested
     * @param ignore_color boolean wich indicates if the color restriction can be ignored
     * @param ignore_value boolean wich indicates if the value restriction can be ignored
     * @param ignore_adjacency boolean wich indicates if the adjacency restriction can be ignored
     * @return true if the dice respects all the restrictions
     */
    private boolean checkRestriction(Dice dice, int coordinate, boolean ignore_color, boolean ignore_value, boolean ignore_adjacency) {

        if (coordinate >= matrix.size()) {
            System.out.println("index out of bound");
            return false;
        }
        Cell cell = matrix.get(coordinate);

        return cell.isPlaceble(dice, ignore_color, ignore_value, ignore_adjacency);

    }

    /**
     * @return an ArrayList with all the dices in the matrix
     */
    public ArrayList<Dice> getAllDices() {
        ArrayList<Dice> alldices = new ArrayList<>();
        for (Cell cell : matrix) {
            if (!cell.isEmpty()) {
                alldices.add(cell.getDice());
            }
        }
        return alldices;
    }

    /**
     * @param coordinate the index of the cell wich dice wants to be returned
     * @return the Dice of the cell at the given index
     */
    public Dice getDice(int coordinate){

        return matrix.get(coordinate).getDice();
    }


    /**
     * @param i index of the cell wich restriction wants to be returned
     * @return the Restriction of the cell at the given index
     */
    public Restriction getRestrictionAt(int i){
        return getCellatIndex(i).getRestriction();
    }

    /**
     * @param i index of the cell
     * @return the cell at the given index
     */
    private Cell getCellatIndex(int i){

        return matrix.get(i);
    }

    @Override
    public String toPacket() {
        StringBuilder packet = new StringBuilder();

        //Use iterator instead of for each because i need to check when i am at the last element , so i do not append the last delimeter
        Iterator<Cell> iterator = matrix.iterator();

        while(iterator.hasNext()){

            Cell cell =iterator.next();
            if (!cell.isEmpty())
                packet.append(cell.getDice());
            else
                packet.append("null");

            if (iterator.hasNext())
                packet.append(CONSTANT.ElenemtsDelimenter);
        }

        return packet.toString();
    }
}
