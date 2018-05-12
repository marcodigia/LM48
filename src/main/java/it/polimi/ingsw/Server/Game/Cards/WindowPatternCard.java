package it.polimi.ingsw.Server.Game.Cards;

import it.polimi.ingsw.Server.Game.Cards.CardsComponents.Cell;
import it.polimi.ingsw.Server.Game.Cards.CardsComponents.Matrix;
import it.polimi.ingsw.Server.Game.Components.Dice;
import it.polimi.ingsw.Server.Game.Utility.CONSTANT;

import java.util.ArrayList;

public class WindowPatternCard implements Drawable {
    private String name;
    private String id;
    private int difficulty;
    private Matrix matrix ;

    public WindowPatternCard(ArrayList<String> pattern) {

        matrix = new Matrix(5,4);
        matrix.initialize();



        id = pattern.remove(0);
        name = pattern.remove(0);
        difficulty = Integer.parseInt(pattern.remove(0));

        matrix.initialize_restricions(pattern);
    }

    @Override
    public String toString() {
        String windowpatterncard = "WP " +CONSTANT.delimenter + id;
        windowpatterncard += matrix + CONSTANT.delimenter;
        return windowpatterncard;
    }

    public boolean placeDice(Dice dice, int coordinate, boolean ignore_color_restriction, boolean ignore_value_restriction, boolean ignore_adjacency) {

        return matrix.setDice(dice, coordinate, ignore_color_restriction, ignore_value_restriction, ignore_adjacency);
    }

    public boolean moveDice(int from, int to, boolean color_restricion, boolean value_restriction, boolean ignore_adjacency) {
        return matrix.moveDice(from, to, color_restricion, value_restriction, ignore_adjacency);
    }

    public boolean isPlaceable(Dice dice, int coordinate, boolean ignoreColorRestriction, boolean ignoreValueRestriction, boolean ignore_adjacency) {
        return matrix.isPlaceable(dice, coordinate, ignoreColorRestriction, ignoreValueRestriction, ignore_adjacency);
    }

    public Dice removeDice(int n) {
        return matrix.removeDice(n);
    }

    public ArrayList<Dice> getAllDices() {
        return matrix.getAllDices();
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

    public String getName() {
        return name;
    }

    public int getDifficulty(){
        return difficulty;
    }
}
