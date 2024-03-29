package it.polimi.ingsw.Server.Game.Components.Boards;

import it.polimi.ingsw.Server.Game.Components.Dice;
import it.polimi.ingsw.Server.Game.Components.DiceBag;

import java.io.Serializable;
import java.util.ArrayList;

public class DraftPool implements Serializable{
    private ArrayList<Dice> dices = new ArrayList<>() ;
    private DiceBag diceBag;


    /**
     * @param diceBag the diceBag that will be used by the draftpool to extract dices
     */
    public DraftPool(DiceBag diceBag) {
        this.diceBag = diceBag;
    }

    public void extractNdice(int n){
        dices = diceBag.getNdices(n);
    }

    public void setDiceAtIndex(int index , Dice dice){
        dices.remove(index);
        dices.add(index,dice);
    }


    public void rerollAllDices(){

        for (Dice d : dices)
            d.reroll();

    }

    public void putDice( Dice diceToPlace){

        dices.add(diceToPlace);
    }

    public Dice getDice(int i ){

        return dices.get(i);

    }

    /**
     * @param dice dice to be removed
     * @return true if the dice is correctly removed
     */
    public boolean removeDice(Dice dice){
        if (dices.contains(dice)){
            dices.remove(dice);
            return true;
        }
        return false;
    }

    public ArrayList<Dice> getDraft(){
        return new ArrayList<>(dices);
    }

    @Override
    public String toString() {

        StringBuilder dp = new StringBuilder("draftpool: ");
        for (Dice dice : dices){
            dp.append(dice);
        }
        return dp.toString();
    }
}
