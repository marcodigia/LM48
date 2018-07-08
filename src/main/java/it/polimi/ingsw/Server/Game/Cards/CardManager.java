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

    static int i = 25;
    public static ArrayList<String> WPCards = new ArrayList<>();

    private static ArrayList<String> DynamicCard = new ArrayList<>();

    /**
     *
     * @param filename the csv file containing patterns of the WindowPAtternCards
     * @throws FileNotFoundException throws this excepetion if no valid file with the given filename is given
     */
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


    public static void addDynamicCard(String name,String wpPacket){

        DynamicCard.add( createDynamicCard(name,wpPacket));
    }


    /**
     * @param name the name of the new Dynamic card
     * @param wpPacket the pattern of the new WindowPatternCard
     * @return the formatted DynamicCard
     */
    public static String createDynamicCard(String name, String wpPacket){
        StringBuilder packet = new StringBuilder();

        packet.append("Dynamic_");
        packet.append(i+".");
        packet.append(name+".");
        packet.append(wpPacket);

        i++;
        return packet.toString();
    }



    /**
     * @return and Iterator on the WindowpatternCard deck
     */
    public static Iterator<String> getCardsIterator(){
        return WPCards.iterator();
    }
}
