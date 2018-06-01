package it.polimi.ingsw.Server.Game.Cards;

import it.polimi.ingsw.Server.Game.Utility.CSVReader;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

public class WindowPatternCardFactory extends AbstractCardFactory {
   private String filname;

    public WindowPatternCardFactory(String filname) {
        this.filname = filname;
    }

    public String getFilename() {
        return filname;
    }


    public Drawable getCard(ArrayList<String> pattern ) {

        return new WindowPatternCard( pattern ) ;
    }



    @Override
    public Hashtable<String, Drawable> getNewCardDeck() throws FileNotFoundException {

        Hashtable<String , Drawable > decks = new Hashtable<String, Drawable>() ;

        Iterator<String> iterator = CardManager.getCardsIterator();

        while (iterator.hasNext()){
            Drawable card = getCard(CSVReader.parseLine(iterator.next()));
            decks.put(card.getID(), card);
        }

        return decks;
    }
}
