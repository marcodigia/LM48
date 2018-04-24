package it.polimi.ingsw.Server.Game.Cards;

import java.util.ArrayList;

public class PublicObjectiveCardFactory extends AbstractCardFactory{
    private String filename;

    public PublicObjectiveCardFactory(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    public Drawable getCard(ArrayList<String> pattern) {
        return new PublicObjectiveCard(pattern);
    }
}
