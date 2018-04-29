package it.polimi.ingsw.Server.Game.Cards;

import java.util.ArrayList;

public class ToolCardFactory extends AbstractCardFactory {

    private String filname;

    public ToolCardFactory(String filname) {

        this.filname = filname;

    }

    public String getFilename() {
        return filname;
    }


    public Drawable getCard(ArrayList<String> pattern) {

        return new ToolCard(pattern);
    }
}

