package it.polimi.ingsw.Server.Game.GameRules.CountEffects;

import it.polimi.ingsw.Server.Game.Cards.WindowPatternCard;
import it.polimi.ingsw.Server.Game.GameRules.Restriction;

import java.util.ArrayList;

public interface CountEffect {


    int getPoints(WindowPatternCard wp);

    void setRestriction(ArrayList<Restriction> restrictions);


}
