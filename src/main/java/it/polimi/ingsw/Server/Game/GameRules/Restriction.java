package it.polimi.ingsw.Server.Game.GameRules;

import it.polimi.ingsw.Server.Game.GameRules.Restrictions.RestrictionType;

import java.io.InputStream;
import java.io.Serializable;

public enum Restriction implements Serializable{
    ONE("1",RestrictionType.Value), TWO("2",RestrictionType.Value), THREE("3",RestrictionType.Value), FOUR("4",RestrictionType.Value),
    FIVE("5",RestrictionType.Value), SIX("6",RestrictionType.Value), GREEN("G",RestrictionType.Color), YELLOW("Y",RestrictionType.Color),
    BLUE("B",RestrictionType.Color), RED("R",RestrictionType.Color), PURPLE("P",RestrictionType.Color), NONE("0",RestrictionType.None);

    String res;
    RestrictionType resType;

    Restriction(String res, RestrictionType resType) {
        this.res = res;
        this.resType = resType;
    }

    public static Restriction parseRestricion(String s) {
        Restriction res;
        switch (s) {
            case "Y":
                res = YELLOW;
                break;
            case "B":
                res = BLUE;
                break;
            case "G":
                res = GREEN;
                break;
            case "R":
                res = RED;
                break;
            case "P":
                res = PURPLE;
                break;
            case "1":
                res = ONE;
                break;
            case "2":
                res = TWO;
                break;
            case "3":
                res = THREE;
                break;
            case "4":
                res = FOUR;
                break;
            case "5":
                res = FIVE;
                break;
            case "6":
                res = SIX;
                break;
            default:
                res = NONE;
                break;
        }
        return res;
    }

    public InputStream getRestrictionImage(){
        String resImageName = "res" + (res + ".png");
        return getClass().getClassLoader().getResourceAsStream(resImageName);
    }

    public String getRestrictionType() {
        return res;
    }

    public boolean isColor(){

        return resType.name().equals("Color");
    }

}
