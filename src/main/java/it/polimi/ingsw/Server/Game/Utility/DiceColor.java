package it.polimi.ingsw.Server.Game.Utility;

public enum DiceColor {
    RED("R" , ANSI_COLOR.ANSI_RED), BLUE("B",ANSI_COLOR.ANSI_BLUE ) ,
    YELLOW("Y", ANSI_COLOR.ANSI_YELLOW) , PURPLE("P" , ANSI_COLOR.ANSI_PURPLE) ,
    GREEN("G" ,ANSI_COLOR.ANSI_GREEN);

    String color ;
    String ansiColor;
    DiceColor(String color , String ansi_color) {
        this.color = color;
        this.ansiColor = ansi_color;
    }



    public static DiceColor resolveColor( String s ){

        DiceColor diceColor = null;
        if (s.toLowerCase().equals("purple") || s.toLowerCase().equals("p"))
            diceColor = DiceColor.PURPLE;
        else if (s.toLowerCase().equals("yellow") || s.toLowerCase().equals("y"))
            diceColor = DiceColor.YELLOW ;
        else if (s.toLowerCase().equals("blue") || s.toLowerCase().equals("b"))
            diceColor = DiceColor.BLUE;
        else if (s.toLowerCase().equals("red") || s.toLowerCase().equals("r"))
            diceColor = DiceColor.RED ;
        else if (s.toLowerCase().equals("green") || s.toLowerCase().equals("g"))
            diceColor = DiceColor.GREEN;
        return diceColor;
    }

    public String getColor() {
        return color;
    }

    public String getAnsiColor(){return ansiColor;}
}

