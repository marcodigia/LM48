package it.polimi.ingsw.Server.Game.Components.Boards;

import it.polimi.ingsw.Server.Game.Components.Dice;
import it.polimi.ingsw.Server.Game.Components.DiceBag;

import java.util.ArrayList;

public class DraftPool {
    private ArrayList<Dice> dices ;
    private DiceBag diceBag;


    public DraftPool(DiceBag diceBag) {
        this.diceBag = diceBag;
    }

    public void extractNdice(int n){
        dices = diceBag.getNdices(n);
    }


    public void rerollAllDices(){

        for (Dice d : dices)
            d.reroll();

    }

    public void putDice( Dice diceToPlace){

        dices.add(diceToPlace);
    }

    //TODO throw exception if not present
    public Dice getDice(int i ){

       /* if ( !dices.contains(dice) )
            return null;

        for (Dice dd : dices){
            if (dd.equals(dice))
                dices.remove(dd);
                return dd;
        }
        return null; */

        return dices.get(i);

    }

    public boolean removeDice(Dice dice){
        if (dices.contains(dice)){
            dices.remove(dice);
            return true;
        }
        return false;
    }

    public ArrayList<Dice> getDraft(){

        return new ArrayList<Dice>(dices);
    }
}
