package it.polimi.ingsw.Server.Game.GameRules;

import java.io.InputStream;

public enum Restriction {
    ONE("1"), TWO("2"), THREE("3"), FOUR("4"),
    FIVE("5"), SIX("6"), GREEN("G"), YELLOW("Y"),
    BLUE("B"), RED("R"), PURPLE("P"), NONE("0");

    String restrictionType;

    Restriction(String restrictionType) {
        this.restrictionType = restrictionType;
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

}
