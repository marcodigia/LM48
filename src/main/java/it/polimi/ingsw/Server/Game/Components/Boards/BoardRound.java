package it.polimi.ingsw.Server.Game.Components.Boards;

import it.polimi.ingsw.Packetable;
import it.polimi.ingsw.Server.Game.Components.Dice;
import it.polimi.ingsw.Server.Game.GameRules.Player;
import it.polimi.ingsw.Server.Game.Utility.CONSTANT;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

public class BoardRound implements Packetable {

    private Hashtable<Player, Integer> scoreboard;

    private ArrayList<ArrayList<Dice>> roundTrack;


    public BoardRound(ArrayList<Player> players) {
        scoreboard = new Hashtable<>();
        for (Player p : players)
            scoreboard.put(p, 0);

        roundTrack = new ArrayList<>();
    }

    public boolean updateScore(Player player, int score) {
        if (scoreboard.containsKey(player)) {
            scoreboard.put(player, score);
            return true;
        }
        return false;
    }

    public ArrayList<Player> getWinners() {
        ArrayList<Player> winners = new ArrayList<>();
        for (Player p1 : scoreboard.keySet()) {

            boolean flag = true;
            for (Player p2 : scoreboard.keySet()) {

                if (scoreboard.get(p1) < scoreboard.get(p2))
                    flag = false;

            }
            if (flag)
                winners.add(p1);


        }
        return winners;
    }

    public Hashtable<Player, Integer> getScoreboard() {
        return new Hashtable<>(scoreboard);
    }

    public Integer getScore(Player player) {
        return scoreboard.get(player);
    }

    public ArrayList<ArrayList<Dice>> getDices(){ return new ArrayList<ArrayList<Dice>>(roundTrack);}

    public void addDices(ArrayList<Dice> dices){
        roundTrack.add(dices);
    }

    @Override
    public String toPacket() {
        StringBuilder packet = new StringBuilder();


        packet.append(CONSTANT.ObjectDelimeter);

        packet.append(roundTrack.size());

        if(roundTrack.size() > 0)
            packet.append(CONSTANT.ObjectDelimeter);

        Iterator<ArrayList<Dice>> arrayListIterator = roundTrack.iterator();
        while (arrayListIterator.hasNext()){

            ArrayList<Dice> ar = arrayListIterator.next();

            Iterator<Dice> iterator = ar.iterator();

            while (iterator.hasNext()){

                packet.append(iterator.next());
                if (iterator.hasNext())
                    packet.append(CONSTANT.ElenemtsDelimenter);
            }

            if (arrayListIterator.hasNext())
                packet.append(CONSTANT.ObjectDelimeter);

        }

        return packet.toString();
    }
}
