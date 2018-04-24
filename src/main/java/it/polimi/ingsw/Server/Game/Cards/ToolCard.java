package it.polimi.ingsw.Server.Game.Cards;

import it.polimi.ingsw.Server.Game.GameRules.Action;
import it.polimi.ingsw.Server.Game.Utility.DiceColor;

import java.util.ArrayList;

public class ToolCard implements Drawable {
   private DiceColor color;
   private String name ;
   private String id;
    private String effect;
   private String restriction;
    private ArrayList<Action> actions;

    public ToolCard(ArrayList<String> pattern ) {

        color = DiceColor.resolveColor( pattern.get(0) );
        name = pattern.get(1);
        id = pattern.get(2);
        effect = pattern.get(3);
        restriction = pattern.get(4);

    }


    public String getID() {
        return id;
    }
}
