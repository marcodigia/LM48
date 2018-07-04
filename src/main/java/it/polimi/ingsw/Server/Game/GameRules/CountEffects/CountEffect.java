package it.polimi.ingsw.Server.Game.GameRules.CountEffects;

import it.polimi.ingsw.Server.Game.Cards.WindowPatternCard;
import it.polimi.ingsw.Server.Game.GameRules.Restriction;

import java.util.ArrayList;

public interface CountEffect {


    /**
     * @param wp is the windowPattern wich needs to be evalueted
     * @return the score calculated on the windowPattern
     */
    int getPoints(WindowPatternCard wp);


    /**
     * @param restrictions is an ArrayList of Restriction used to initialize the CountEffect implementation
     */
    void setRestriction(ArrayList<Restriction> restrictions);


}
