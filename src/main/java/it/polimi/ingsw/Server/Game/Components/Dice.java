package it.polimi.ingsw.Server.Game.Components;

import it.polimi.ingsw.Server.Game.Utility.DiceColor;

import java.util.Random;

public class Dice {

    private DiceColor color;
    private String value;

    public Dice(DiceColor color , String value ) {

        this.color = color;
        this.value = value;

    }



    public DiceColor getDiceColor() { return color;}

    public String getValue() {
        return value;
    }



    public void reroll() {
        Random random = new Random();
        int i = random.nextInt(6) + 1;
        value = Integer.toString(i);
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Dice)

            return  ((Dice) obj).getDiceColor().getColor().equals( color.getColor() )
                    && ((Dice) obj).getValue().equals( value );

        return super.equals(obj);
    }
}

