package it.polimi.ingsw.Server.Game.Cards;

import it.polimi.ingsw.Server.Game.Cards.CardsComponents.Cell;
import it.polimi.ingsw.Server.Game.Cards.CardsComponents.Matrix;
import it.polimi.ingsw.Server.Game.Components.Dice;

import java.util.ArrayList;

public class WindowPatternCard {
    private String name ;
    private String id;
    private int difficulty;
    private ArrayList<String> pattern;
    private Matrix matrix ;

    public WindowPatternCard(ArrayList<String> pattern) {

        this.pattern = pattern ;
        matrix = new Matrix(5,4);
        matrix.initialize();



        id = pattern.remove(0);
        name = pattern.remove(0);
        difficulty = Integer.parseInt(pattern.remove(0));

        matrix.initialize_restricions(pattern);
    }

    public boolean placeDice(Dice dice, int coordinate , boolean ignore_color_restriction, boolean ignore_value_restriction){

        return matrix.setDice(dice,coordinate,ignore_color_restriction,ignore_value_restriction);
    }

    public boolean moveDice(int from , int to , boolean color_restricion , boolean value_restriction){
        return matrix.moveDice(from , to ,color_restricion , value_restriction);
    }

    public boolean isPlaceable(Dice dice , int coordinate , boolean ignoreColorRestriction , boolean ignoreValueRestriction){
        return matrix.isPlaceable(dice,coordinate,ignoreColorRestriction,ignoreValueRestriction);
    }

    public boolean checkRestriction(Dice dice, int coordinate , boolean ignoreColorRestriction ,boolean ignoreValueRestriction){
        return matrix.checkRestriction(dice,coordinate,ignoreColorRestriction,ignoreValueRestriction);
    }

    public Dice getDice(int coordinate){
        return matrix.getDice(coordinate);
    }


    public ArrayList<Cell> getRow(int n){
        return matrix.getRow(n);
    }

    public String getID() {
        return id;
    }
}