package it.polimi.ingsw.Server.Game.Cards;

import java.util.ArrayList;

public class PrivateObjectiveCardFactory extends AbstractCardFactory{

    private String filename;


    public PrivateObjectiveCardFactory(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    public Drawable getCard(ArrayList<String> pattern) {
        return new PrivateObjectiveCard(pattern);
    }
}
