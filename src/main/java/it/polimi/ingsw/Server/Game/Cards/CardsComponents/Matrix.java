package it.polimi.ingsw.Server.Game.Cards.CardsComponents;

import it.polimi.ingsw.Exceptions.WrongPatternSizeException;
import it.polimi.ingsw.Server.Game.Components.Dice;

import java.io.PrintStream;
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

        // inizializzo con celle a nord
        for (int i = lenght; i < matrix.size(); i++) {

            Cell current_cell = matrix.get(i);
            Cell nord_cell = matrix.get(i - lenght);
            current_cell.setCell(nord_cell);
        }

        // inizializzo con celle a sud

        for (int i = 0; i < matrix.size() - lenght; i++) {

            Cell current_cell = matrix.get(i);
            Cell sud_cell = matrix.get(i + lenght);
            current_cell.setCell(sud_cell);
        }


        // inizializzo con celle ad ovest

        for (int i = 0; i < matrix.size(); i++) {

            if (i % lenght >= 1) {
                Cell current_cell = matrix.get(i);
                Cell ovest_cell = matrix.get(i - 1);
                current_cell.setCell(ovest_cell);
            }

        }

        //inizializzo con celle ad est
        for (int i = 0; i < matrix.size(); i++) {

            if (i % lenght < lenght - 1) {
                Cell current_cell = matrix.get(i);
                Cell est_cell = matrix.get(i + 1);

                current_cell.setCell(est_cell);
            }

        }


    }


    //this method add all the restrictions
    public void initialize_restricions( ArrayList<String> matrix_pattern ) throws WrongPatternSizeException {

        if (matrix_pattern.size() != matrix.size())
            throw new WrongPatternSizeException();

        for (int i = 0 ; i < matrix.size() ; i ++ )
        {
            matrix.get(i).addRestricion( matrix_pattern.get(i));
        }

    }


    // check if the input is legal and if the the cell is empty than  if the restrictions are satisfied place the dice
    public boolean setDice(Dice dice, int coordinate, boolean ignore_color, boolean ignore_value) {


        boolean res = isPlaceable(dice,coordinate,ignore_color,ignore_value);
        if (res){
            Cell cell = matrix.get(coordinate);
            cell.setDice(dice);
        }
        return res;



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
        Dice dice = cell.getDice();
        cell.removeDice(dice);


        return true;

    }

    //check if a the dice is present and if the destination cell is empty than move the dice from a cell to the another
    public boolean moveDice(int from, int to, boolean ignore_color, boolean ignore_value) {

        Cell cell_source = matrix.get(from);
        Dice dice = cell_source.getDice();

        Cell cell_destination = matrix.get(to);

        if (!cell_source.isEmpty())

            if (cell_destination.isEmpty()) {

                if (cell_destination.isPlaceble(dice, ignore_color, ignore_value)) {
                    cell_source.removeDice(dice);
                    cell_destination.setDice(dice);
                } else
                    return false;
                return true;

            }


        return false;
    }

    public boolean isPlaceable(Dice dice, int coordinate, boolean ignore_color, boolean ignore_value){

        if (checkRestriction(dice,coordinate,ignore_color,ignore_value))
        {
            Cell cell = matrix.get(coordinate);
            if (!cell.isEmpty()) {
                System.out.println("dado gia piazzato ");
                return false;
            }
            return true;
        }

        return false;

    }

    public boolean checkRestriction(Dice dice, int coordinate, boolean ignore_color, boolean ignore_value){

        //TODO throw exception
        if (coordinate >= matrix.size()) {
            System.out.println("index out of bound ");
            return false;
        }
        Cell cell = matrix.get(coordinate);

        return cell.isPlaceble(dice, ignore_color, ignore_value);

    }
    public ArrayList<String> getAllDices(){
        return null;
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
