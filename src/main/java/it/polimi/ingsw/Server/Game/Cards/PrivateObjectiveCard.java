package it.polimi.ingsw.Server.Game.Cards;

import it.polimi.ingsw.Server.Game.Cards.CardsComponents.Countable;
import it.polimi.ingsw.Server.Game.Components.Dice;
import it.polimi.ingsw.Server.Game.Utility.DiceColor;

import java.io.InputStream;
import java.util.ArrayList;

public class PrivateObjectiveCard implements Countable {

   private String id;
   private DiceColor diceColor;


    public PrivateObjectiveCard(ArrayList<String> pattern ) {
        id = pattern.get(0);
        diceColor = DiceColor.resolveColor( pattern.get(1) ) ;
    }

    public InputStream getPrivateObjectiveCardImage(){
        String privateImageName = "private" + getID() + ".png";
        return getClass().getClassLoader().getResourceAsStream(privateImageName);
    }

    public String getID() {
        return id;
    }

    @Override
    public int getPoints(WindowPatternCard wp) {
        int count = 0;

        ArrayList<Dice> dices = wp.getAllDices();
        for (Dice d : dices){
            if (d.getDiceColor().equals(diceColor))
                count++;
        }
        return count;
    }
}
