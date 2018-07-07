package it.polimi.ingsw.Server.View;

import it.polimi.ingsw.Server.Game.GameRules.Player;

import java.util.Hashtable;

public class VirtualViewManager  {

    static Hashtable<Player,VirtualView> virtualviews= new Hashtable<>();

    public static synchronized void addVirtualView(Player player,VirtualView vv){
        virtualviews.put(player,vv);
    }

    public static synchronized void removeVirtualView(Player p){virtualviews.remove(p);}

    public static synchronized VirtualView getVirtualView(Player player){
        return virtualviews.get(player);
    }
}
