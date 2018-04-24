package it.polimi.ingsw.Server.Game.Components;

import it.polimi.ingsw.Server.Game.Utility.DiceColor;

import java.util.ArrayList;
import java.util.Random;

public class DiceBag {
   private ArrayList<Dice> dices;

    public DiceBag() {
        dices = new ArrayList<Dice>();
        for (DiceColor dye : DiceColor.values() )
            for (int i = 0 ; i < 18 ; i++){

                Random random = new Random();
                dices.add( new Dice(dye, Integer.toString( random.nextInt() ) ) );
            }
    }


    //TODO throw exception for not enough dices , meglio fare controllo su null
    //TODO verificare comportamento con get 0 dices

    public ArrayList<Dice> getNdices(int n ){

        if (dices.size() < n){
            return null;
        }


        ArrayList<Dice> draft = new ArrayList<Dice>();

        for (int i = 0 ; i < n ; i++ ) {

            Random random = new Random();

            int dice_num = random.nextInt(dices.size() );

            draft.add(dices.get(dice_num));
            dices.remove(dice_num);

        }
        return draft ;

    }

    public void putDice( Dice dice){
        dices.add(dice);
    }
}
