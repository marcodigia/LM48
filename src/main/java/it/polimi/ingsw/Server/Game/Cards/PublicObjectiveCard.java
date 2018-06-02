package it.polimi.ingsw.Server.Game.Cards;

import java.io.InputStream;
import java.util.ArrayList;

public class PublicObjectiveCard implements Drawable {
   private String id;
   private String name ;
    private String description ;
   private int point;


    public PublicObjectiveCard(ArrayList<String> pattern ) {

        id = pattern.get(0);
        name = pattern.get(1);
        description = pattern.get(2);
        point = Integer.parseInt( pattern.get(3));
    }

    public InputStream getPublicObjectiveCardImage(){
        String publicImageName = "public" + getID() + ".png";
        return getClass().getClassLoader().getResourceAsStream(publicImageName);
    }

    public String getID() {
        return id;
    }
}
