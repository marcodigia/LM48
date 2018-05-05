package it.polimi.ingsw.Server.Game.Components;

import it.polimi.ingsw.Server.Game.Utility.DiceColor;

import java.io.InputStream;
import java.util.Random;

public class Dice implements Cloneable{

    private DiceColor color;
    private String value;
    private Random random;

    public Dice(DiceColor color , String value ) {

        this.color = color;
        this.value = value;
        random = new Random();

    }



    public DiceColor getDiceColor() { return color;}

    public String getValue() {
        return value;
    }

    public void reroll() {
        int i = random.nextInt(6) + 1;
        value = Integer.toString(i);
    }

    public void setValue(int i) {
        value = Integer.toString(i);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Dice)

            return  ((Dice) obj).getDiceColor().getColor().equals( color.getColor() )
                    && ((Dice) obj).getValue().equals( value );

        return super.equals(obj);
    }

    @Override
    public String toString(){
        return getDiceColor().getColor() + getValue();
    }

    public Dice cloneIt(){
        try {
            return (Dice) clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }


    public InputStream getDiceImage(){

        String diceImageName = "dice" + (value + color.getColor() + ".png");
        return getClass().getClassLoader().getResourceAsStream(diceImageName);
    }
}

