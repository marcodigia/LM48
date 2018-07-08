package it.polimi.ingsw.Server.Game.Cards;

import it.polimi.ingsw.Server.Game.Utility.ANSI_COLOR;
import it.polimi.ingsw.Server.Game.Utility.CSVReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

public abstract class  AbstractCardFactory {

    //This method will provide the name of the file which stores the patterns of the cards

    /**This method will provide the name of the file which stores the patterns of the cards
     * @return the filename of the csv file to be read
     */
    abstract String getFilename() ;

    //This method create actually creates the istance of the specific card


    /**
     * @param pattern an ArrayList with the pattern of valid Drawable
     * @return a Drawable wich is actually the istance of the specific card
     */
    abstract  Drawable getCard(ArrayList<String> pattern );


    // read the csv file and create card deck

    /**
     * read the csv file and create card deck
     * @return an HashTable where the key is the id of the Drawable , and value the Actual Drawable
     * @throws FileNotFoundException throw the exception if the file to be read does not exixst
     */
    public Hashtable<String , Drawable > getNewCardDeck() throws FileNotFoundException  {

        Hashtable<String , Drawable > decks = new Hashtable<String, Drawable>() ;

        //open the stream from resources folder , filename is initialize whith the constructor
        String filename = getFilename();
        InputStream is =  getClass().getClassLoader().getResourceAsStream(filename);


        if (is == null)
            throw new FileNotFoundException();

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
