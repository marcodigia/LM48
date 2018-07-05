package it.polimi.ingsw.Server.Game.Cards;

import it.polimi.ingsw.Server.Game.Cards.CardsComponents.Countable;
import it.polimi.ingsw.Server.Game.GameRules.CountEffects.*;
import it.polimi.ingsw.Server.Game.GameRules.Restriction;
import it.polimi.ingsw.Server.Game.GameRules.Restrictions.RestrictionType;

import java.io.InputStream;
import java.util.ArrayList;

public class PublicObjectiveCard implements Countable {
   private String id;
   private String name ;
    private String description ;
   private int point;

   CountEffect effect ;

    public PublicObjectiveCard(ArrayList<String> pattern ) {

        id = pattern.get(0);
        name = pattern.get(1);
        description = pattern.get(2);
        point = Integer.parseInt( pattern.get(3));


    }

    public InputStream getPublicObjectiveCardImage(){
        String publicImageName = "public" + getID() + ".png";
        return getClass().getClassLoader().getResourceAsStream(publicImageName);
    }

    public String getID() {
        return id;
    }

    @Override
    public int getPoints(WindowPatternCard wp) {
        return getEffect(id).getPoints(wp);
    }

    

    private CountEffect getEffect(String id){
        CountEffect effect = null;
        ArrayList<Restriction> res = new ArrayList<>() ;
        switch (id){
            case "1":
                res.add(Restriction.BLUE);
                effect = new CountRow(point);
                effect.setRestriction(res);
                break;
            case "2":
                effect = new CountRow(point);
                res.add(Restriction.ONE);
                effect.setRestriction(res);
                break;
            case "3":
                effect = new ColumCount(point);
                res.add(Restriction.BLUE);
                effect.setRestriction(res);
                break;
            case "4":
                res.add(Restriction.ONE);
                res.add(Restriction.TWO);
                res.add(Restriction.THREE);
                res.add(Restriction.FOUR);
                res.add(Restriction.FIVE);
                res.add(Restriction.SIX);
                effect = new CountSets(point);
                effect.setRestriction(res);
                break;
            case "5":
                effect = new ColumCount(point);
                res.add(Restriction.ONE);
                effect.setRestriction(res);
                break;

            case "6":

                res.add(Restriction.BLUE);
                res.add(Restriction.RED);
                res.add(Restriction.GREEN);
                res.add(Restriction.YELLOW);
                res.add(Restriction.PURPLE);
                effect = new CountSets(point);
                effect.setRestriction(res);
                break;

            case "7":
                res.add(Restriction.ONE);
                res.add(Restriction.TWO);
                effect = new CountSets(point);
                effect.setRestriction(res);
                break;

            case "8":
                res.add(Restriction.THREE);
                res.add(Restriction.FOUR);
                effect = new CountSets(point);
                effect.setRestriction(res);
                break;

            case "9":
                res.add(Restriction.FIVE);
                res.add(Restriction.SIX);
                effect = new CountSets(point);
                effect.setRestriction(res);
                break;

            case "10":
                effect = new DiagonalEffect(point);
                break;

        }
        return effect;
    }


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
