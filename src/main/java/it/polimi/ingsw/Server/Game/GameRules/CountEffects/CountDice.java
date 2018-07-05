package it.polimi.ingsw.Server.Game.GameRules.CountEffects;

import it.polimi.ingsw.Server.Game.Cards.CardsComponents.Countable;
import it.polimi.ingsw.Server.Game.Cards.WindowPatternCard;
import it.polimi.ingsw.Server.Game.Components.Dice;
import it.polimi.ingsw.Server.Game.GameRules.Restriction;
import it.polimi.ingsw.Server.Game.Utility.DiceColor;

import java.io.Serializable;
import java.util.ArrayList;

public class CountDice implements CountEffect {


    DiceColor diceColor;

    @Override
    public int getPoints(WindowPatternCard wp) {


        if (diceColor!=null){

            int score = 0;
            for (Dice d : wp.getAllDices()){
                if (d.getDiceColor().equals(diceColor))
                    score++;
            }
            return score;
        }

        return 0;
    }

    @Override
    public void setRestriction(ArrayList<Restriction> restrictions) {


        diceColor = DiceColor.resolveColor(restrictions.get(0).name());

    }
}
