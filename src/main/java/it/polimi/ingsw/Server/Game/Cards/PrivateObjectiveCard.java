package it.polimi.ingsw.Server.Game.Cards;

import it.polimi.ingsw.Server.Game.Utility.DiceColor;

import java.util.ArrayList;

public class PrivateObjectiveCard implements Drawable {

   private String id;
   private DiceColor diceColor;


    public PrivateObjectiveCard(ArrayList<String> pattern ) {
        id = pattern.get(0);
        diceColor = DiceColor.resolveColor( pattern.get(1) ) ;
    }

    public String getID() {
        return id;
    }
}
