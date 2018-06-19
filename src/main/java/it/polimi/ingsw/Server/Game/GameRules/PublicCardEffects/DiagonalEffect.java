package it.polimi.ingsw.Server.Game.GameRules.PublicCardEffects;

import it.polimi.ingsw.Server.Game.Cards.WindowPatternCard;
import it.polimi.ingsw.Server.Game.GameRules.Restriction;

import java.util.ArrayList;

public class DiagonalEffect implements CountEffect {
    @Override
    public int getPoints(WindowPatternCard wp) {
        return 0;
    }

    @Override
    public void setRestriction(ArrayList<Restriction> restrictions) {

    }
}
