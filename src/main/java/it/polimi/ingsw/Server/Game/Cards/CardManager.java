package it.polimi.ingsw.Server.Game.Cards;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class CardManager {


    //In order to add the functionality of the dinamic card we had to add this class.
    //Because we can no longer simply load the wp card from file so we load only at the beginning and than we access to
    //the card from the CardManager

    public static ArrayList<String> WPCards = new ArrayList<>();


    public static void setWPCards(String filename) throws FileNotFoundException {


        InputStream is =  CardManager.class.getClassLoader().getResourceAsStream(filename);


        if (is == null)
            throw new FileNotFoundException();

        Scanner scanner =  new Scanner(is);

        while (scanner.hasNextLine()){
            String s = scanner.nextLine();
            WPCards.add(s);
        }





    }


    public static void addCard(String name,String wpPacket){

        StringBuilder wpCard = new StringBuilder();
        wpCard.append(WPCards.size()+1).append(",").append(name);
        wpCard.append(',').append(wpPacket);
        WPCards.add(wpCard.toString());
    }



    //TODO
    //NB this method expose the internal state. not good

    public static Iterator<String> getCardsIterator(){
        return WPCards.iterator();
    }
}
