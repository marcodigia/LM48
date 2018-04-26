package it.polimi.ingsw.Server.Game.Cards;

import java.util.ArrayList;

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
}
