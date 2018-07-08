package it.polimi.ingsw.Server.Game.Cards;

import it.polimi.ingsw.Server.Game.Cards.CardsComponents.Countable;
import it.polimi.ingsw.Server.Game.Components.Dice;
import it.polimi.ingsw.Server.Game.GameRules.CountEffects.CountDice;
import it.polimi.ingsw.Server.Game.GameRules.CountEffects.CountEffect;
import it.polimi.ingsw.Server.Game.GameRules.Restriction;
import it.polimi.ingsw.Server.Game.Utility.DiceColor;

import java.io.InputStream;
import java.util.ArrayList;

public class PrivateObjectiveCard implements Countable {

   private String id;
   private DiceColor diceColor;

   CountDice effect;

    /**
     * @param pattern and Arraylist containing a valid pattern for the PrivateObjectiveCard : id , diceColor , effect
     */
    public PrivateObjectiveCard(ArrayList<String> pattern ) {
        id = pattern.get(0);
        diceColor = DiceColor.resolveColor( pattern.get(1) ) ;
        effect = new CountDice();
        ArrayList<Restriction> res = new ArrayList<>();
        res.add(Restriction.parseRestricion(pattern.get(1).substring(0,1)));
        effect.setRestriction(res);
    }

    /**
     * @return an InputStream of the immage of this Card
     */
    public InputStream getPrivateObjectiveCardImage(){
        String privateImageName = "private" + getID() + ".png";
        return getClass().getClassLoader().getResourceAsStream(privateImageName);
    }


    public String getID() {
        return id;
    }

    @Override
    public int getPoints(WindowPatternCard wp) {

        return effect.getPoints(wp);
    }

    /**
     * @return the diceColor of the PrivateObjectiveCard
     */
    public DiceColor getDiceColor() {
        return diceColor;
    }
}
