package it.polimi.ingsw.Server.Game.Cards.CardsComponents;

import it.polimi.ingsw.Exceptions.WrongPatternSizeException;
import it.polimi.ingsw.Server.Game.Components.Dice;
import it.polimi.ingsw.Server.Game.GameRules.Restriction;

import java.util.ArrayList;

public class Matrix {
   private int lenght;
    private int height;
    private ArrayList<Cell> matrix;

    public Matrix(int lenght, int height) {
        this.lenght = lenght;
        this.height = height;
        matrix = new ArrayList<Cell>();

        for (int i = 0; i < lenght * height; i++) {

            matrix.add(new Cell());
        }
    }


    // initialize the matric by linking all the cells whith their neighbour


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

    private boolean hasNord(int i) {
        return i >= lenght;
    }

    private boolean hasSud(int i, int size) {
        return i < size - lenght;
    }

    private boolean hasOvest(int i) {
        return i % lenght >= 1;
    }

    private boolean hasEst(int i) {
        return i % lenght < lenght - 1;
    }
    //this method add all the restrictions
    public void initialize_restricions( ArrayList<String> matrix_pattern ) throws WrongPatternSizeException {


        if (matrix_pattern.size() != matrix.size())
            throw new WrongPatternSizeException();

        for (int i = 0 ; i < matrix.size() ; i ++ )
        {

            matrix.get(i).setRestriction(Restriction.parseRestricion(matrix_pattern.get(i)));
        }

    }


    // check if the input is legal and if the the cell is empty than  if the restrictions are satisfied place the dice
    public boolean setDice(Dice dice, int coordinate, boolean ignore_color, boolean ignore_value, boolean ignore_adjacency) {
        return matrix.get(coordinate).putDice(dice, ignore_color, ignore_value, ignore_adjacency);



    }

    // check if the input is lega than remove the dice if present
    public boolean removeDice(int coordinate) {


        if (coordinate >= matrix.size() || coordinate < 0) {
            System.out.println("index out of bound ");
            return false;
        }
        if (matrix.get(coordinate).getDice() == null) {
            System.out.println("nessun dado piazzato ");
            return false;
        }


        Cell cell = matrix.get(coordinate);
        cell.removeDice();


        return true;

    }

    //check if a the dice is present and if the destination cell is empty than move the dice from a cell to the another
    //remove dice from cell_source before move it so can check if adjacent is still true
    public boolean moveDice(int from, int to, boolean ignore_color, boolean ignore_value, boolean ignore_adjacency) {

        Cell cell_source = matrix.get(from);
        Dice dice = cell_source.getDice();

        Cell cell_destination = matrix.get(to);

        if (!cell_source.isEmpty())

            if (cell_destination.isEmpty()) {
                Dice dice_source = cell_source.getDice();
                cell_source.removeDice();
                if (cell_destination.putDice(dice, ignore_color, ignore_value, ignore_adjacency))
                    return true;
                 else{
                     //Set everything true if dice was there it doesn't matter how but replace it there
                     cell_source.putDice(dice_source,true,true,true);
                }
            }


        return false;
    }

    public boolean isPlaceable(Dice dice, int coordinate, boolean ignore_color, boolean ignore_value, boolean ignore_adjacency) {

        if (checkRestriction(dice, coordinate, ignore_color, ignore_value, ignore_adjacency))
        {
            Cell cell = matrix.get(coordinate);
            return cell.isEmpty();
        }

        return false;

    }

    public boolean checkRestriction(Dice dice, int coordinate, boolean ignore_color, boolean ignore_value, boolean ignore_adjacency) {

        //TODO throw exception
        if (coordinate >= matrix.size()) {
            System.out.println("index out of bound ");
            return false;
        }
        Cell cell = matrix.get(coordinate);

        return cell.isPlaceble(dice, ignore_color, ignore_value, ignore_adjacency);

    }

    public ArrayList<Dice> getAllDices() {
        ArrayList<Dice> alldices = new ArrayList<>();
        for (Cell cell : matrix) {
            if (!cell.isEmpty()) {
                alldices.add(cell.getDice());
            }
        }
        return alldices;
    }

    public Dice getDice(int coordinate){

        return matrix.get(coordinate).getDice();
    }



    public ArrayList<Cell> getRow(int n ){

        ArrayList<Cell> row = new ArrayList<Cell>();
        //TODO throw exception , forse basta solo verificare che l'input non sia mai sbagliato
        if (n > height)
            return null;

        int m = n*lenght;
        for (int i = m; i < m +lenght; i++ ) {
            //System.out.println(ANSI_COLOR.ANSI_BLUE+i+ANSI_COLOR.ANSI_RESET);
            row.add(matrix.get(i));

        }


        return row;
    }

}
