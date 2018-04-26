package it.polimi.ingsw.Server.Game.Cards;

import it.polimi.ingsw.Server.Game.Utility.CSVReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

public abstract class  AbstractCardFactory {

    //This method will provide the name of the file which stores the patterns of the cards
    abstract String getFilename() ;

    //This method create actually creates the istance of the specific card

    //TODO throw excteption file not found
    abstract  Drawable getCard(ArrayList<String> pattern );


    // read the csv file and create card deck
    public Hashtable<String , Drawable > getNewCardDeck() throws FileNotFoundException  {

        Hashtable<String , Drawable > decks = new Hashtable<String, Drawable>() ;

        //open the stream from resources folder , filename is initialize whith the constructor
        String filename = getFilename();
        InputStream is =  getClass().getClassLoader().getResourceAsStream(filename);
        Scanner scanner =  new Scanner(is);

        while (scanner.hasNextLine()){

            // get card is different for every AbstractFactory
            Drawable card = getCard(CSVReader.parseLine(scanner.nextLine()));
            decks.put(card.getID(), card);

        }
        //Close streams
        scanner.close();
        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return decks ;

    }







}
