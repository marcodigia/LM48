package it.polimi.ingsw.Server.Game.GameRules;

import java.io.InputStream;
import java.io.Serializable;

public enum Restriction implements Serializable{
    ONE("1",false), TWO("2",false), THREE("3",false), FOUR("4",false),
    FIVE("5",false), SIX("6",false), GREEN("G",true), YELLOW("Y",true),
    BLUE("B",true), RED("R",true), PURPLE("P",true), NONE("0",false);

    String restrictionType;
    boolean iscolor;

    Restriction(String restrictionType, boolean iscolor) {
        this.restrictionType = restrictionType;
        this.iscolor = iscolor;
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
        String resImageName = "res" + (restrictionType + ".png");
        return getClass().getClassLoader().getResourceAsStream(resImageName);
    }

    public String getRestrictionType() {
        return restrictionType;
    }

    public boolean isColor(){

        return iscolor;
    }

}
