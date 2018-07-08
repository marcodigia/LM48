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

    /**
     * @param pattern an ArrayList cotaining a valid pattern for the windowPAtternCard : id , name , difficulty , pattern of Restrictions
     */
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

    /**
     *
     * @param dice the dice wich need to be tested
     * @param ignore_color_restriction boolean wich indicates if the color restriction can be ignored
     * @param ignore_value_restriction boolean wich indicates if the value restriction can be ignored
     * @param ignore_adjacency boolean wich indicates if the adjacency restriction can be ignored
     * @return true if the dice is placebale and all the restriction are satified
     */
    public boolean placeDice(Dice dice, int coordinate, boolean ignore_color_restriction, boolean ignore_value_restriction, boolean ignore_adjacency) {
        return matrix.setDice(dice, coordinate, ignore_color_restriction, ignore_value_restriction, ignore_adjacency);
    }

    /**
     * check if a the dice is present and if the destination cell is empty than move the dice from a cell to the another
     * remove dice from cell_source before move it so can check if adjacent is still true
     * @param from the cell index of the source
     * @param to the cell index of the destination
     * @param  color_restricion boolean wich indicates if the color restriction can be ignored
     * @param value_restriction boolean wich indicates if the value restriction can be ignored
     * @param ignore_adjacency boolean wich indicates if the adjacency restriction can be ignored
     * @return true if managed to correctly move the dice
     */
    public boolean moveDice(int from, int to, boolean color_restricion, boolean value_restriction, boolean ignore_adjacency) {
        return matrix.moveDice(from, to, color_restricion, value_restriction, ignore_adjacency);
    }

    /**
     * @param dice the dice wich need to be tested
     * @param ignoreColorRestriction boolean wich indicates if the color restriction can be ignored
     * @param ignoreValueRestriction boolean wich indicates if the value restriction can be ignored
     * @param ignore_adjacency boolean wich indicates if the adjacency restriction can be ignored
     * @return true if the dice is placeable and all the restriction are satified
     */
    public boolean isPlaceable(Dice dice, int coordinate, boolean ignoreColorRestriction, boolean ignoreValueRestriction, boolean ignore_adjacency) {
        return matrix.isPlaceable(dice, coordinate, ignoreColorRestriction, ignoreValueRestriction, ignore_adjacency);
    }

    /**
     * Removes a dice at a given coordinate
     * @param n the cell wich dice's has to be removed
     * @return the Dice removed
     */
    public Dice removeDice(int n) {
        return matrix.removeDice(n);
    }

    /**
     * @return an ArrayList with all the dices in the matrix
     */
    public ArrayList<Dice> getAllDices() {
        return matrix.getAllDices();
    }

    /**
     * @param coordinate the index of the cell wich dice wants to be returned
     * @return the Dice of the cell at the given index
     */
    public Dice getDice(int coordinate){
        return matrix.getDice(coordinate);
    }

    /**
     * @param i index of the cell wich restriction wants to be returned
     * @return the Restriction of the cell at the given index
     */
    public Restriction getRestrictionAtIndex(int i){
        return matrix.getRestrictionAt(i);
    }

    /**
     * @return the id of the winodwPAttern
     */
    public String getID() {
        return id;
    }

    /**
     * @return the name of the windowPattern
     */
    public String getName() {
        return name;
    }

    /**
     * @return the difficult of the windowPAttern
     */
    public int getDifficulty(){
        return difficulty;
    }

    /**
     * @param id the id of the windowPattern
     */
    public void setId(String id) {
        this.id = id;
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
