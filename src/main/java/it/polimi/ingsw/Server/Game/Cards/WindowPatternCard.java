package it.polimi.ingsw.Server.Game.Cards;

import it.polimi.ingsw.Packetable;
import it.polimi.ingsw.Server.Game.Cards.CardsComponents.Cell;
import it.polimi.ingsw.Server.Game.Cards.CardsComponents.Matrix;
import it.polimi.ingsw.Server.Game.Components.Dice;
import it.polimi.ingsw.Server.Game.GameRules.Restriction;
import it.polimi.ingsw.Server.Game.Utility.CONSTANT;

import java.util.ArrayList;

public class WindowPatternCard implements Drawable , Packetable {
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

    public ArrayList<Restriction> getRow(int index){
        ArrayList<Restriction> restriction = new ArrayList<Restriction>();
        for(int i=0;i<5;i++){
            restriction.add(matrix.getRestrictionAt(5*index+i));
        }
        return restriction;
    }

    public boolean placeDice(Dice dice, int coordinate, boolean ignore_color_restriction, boolean ignore_value_restriction, boolean ignore_adjacency) {
        System.out.println("Window Pattern Card : "+dice.toString());
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

    public Restriction getRestrictionAtIndex(int i){
        return matrix.getRestrictionAt(i);
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

    @Override
    public String toPacket() {

        StringBuilder packet = new StringBuilder();
        packet.append(id).append(CONSTANT.ObjectDelimeter).append(matrix.toPacket());
        return packet.toString();
    }

    @Override
    public String toString() {
        String windowpatterncard = "WP" + CONSTANT.delimenter +id;
        windowpatterncard += CONSTANT.delimenter + matrix + CONSTANT.delimenter;
        return windowpatterncard;
    }
}
