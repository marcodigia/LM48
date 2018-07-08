package it.polimi.ingsw.Server.Game.Components.Boards;

import it.polimi.ingsw.Packetable;
import it.polimi.ingsw.Server.Game.Components.Dice;
import it.polimi.ingsw.Server.Game.GameRules.EndGame.ScoreHandler;
import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import it.polimi.ingsw.Server.Game.GameRules.Player;
import it.polimi.ingsw.Server.Game.ServerRete.Game;
import it.polimi.ingsw.Server.Game.ServerRete.Turn;
import it.polimi.ingsw.Server.Game.Utility.ANSI_COLOR;
import it.polimi.ingsw.Server.Game.Utility.CONSTANT;
import it.polimi.ingsw.Server.Game.Utility.DiceColor;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class BoardRound implements Packetable {

    private Hashtable<Player, Integer> scoreboard;

    private ArrayList<ArrayList<Dice>> roundTrack;



    private ArrayList<DiceColor> colors = new ArrayList<>();
    public BoardRound(ArrayList<Player> players) {
        scoreboard = new Hashtable<>();
        for (Player p : players)
            scoreboard.put(p, 0);

        roundTrack = new ArrayList<>();
    }

    /**
     * @param player the player wich score need to be update
     * @param score the score for the player
     * @return true if the player score is update
     */
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

    public Hashtable<Player,Integer> getScore(GameStatus gameStatus){
        return (new ScoreHandler(gameStatus)).getFinalScore();
    }

    /**
     * This method is used server-side to calculate the winner
     * @param gameStatus the actual gameStatus
     * @return the calculated winner
     */
    public synchronized Player getWinner(GameStatus gameStatus){


        Hashtable<Player, Integer> win =  new Hashtable<>(getScore(gameStatus));
        Hashtable<Player,Integer> winners = new Hashtable<>();
        for (Player p : win.keySet()){
            if(p.getConnected())
                winners.put(p,win.get(p));
        }

        ArrayList<Player> w = new ArrayList<>(winners.keySet());
        int highScore = 0;
        for (Player p : winners.keySet()){
            if (winners.get(p)>highScore){
                highScore = winners.get(p);

            }

        }

        //Do this way because i m not sure if the players in winners and player in w are the same so check by name , look in winners and remove in w
        for(Player p : w){
            Player p1=null;
            for (Player p2 : winners.keySet())
                if (p2.getName().equals(p.getName()))
                    p1= p2;
            if (winners.get(p1)<highScore)
                w.remove(p);
        }

        int highPrivateScore = 0 ;
        for (Player p : w){
            if (p.getPbScore()>highPrivateScore)
                highPrivateScore=p.getPbScore();
        }

        for (Player p :w){
            if (p.getPbScore()<highPrivateScore)
                w.remove(p);

        }

        int higToken = 0;
        for (Player p :w){
            if (p.getWallet().getTokenAmmount()>higToken)
                higToken=p.getWallet().getTokenAmmount();

        }

        for (Player p : w){
            if (p.getWallet().getTokenAmmount()<higToken)
                w.remove(p);
        }

        if (w.size()>0){
            LinkedHashMap<Player,Boolean> turn = Turn.getPlayers();
            ArrayList<Player> pl = new ArrayList<>(turn.keySet());

            int lowturn;
            int posinTurn;

            for (int i = pl.size()-1 ; i >=0 ; i--){

                for (Player p : w){
                    if (p.getName().equals(pl.get(i).getName()))
                        return p;
                }
            }

        }


        return null;
    }

    /**
     * @param round the index of the round
     * @param diceIndex the index of the dice in the round
     * @param dice the new dice to be put
     */
    public void setDiceAtIndex(int round, int diceIndex , Dice dice){
        roundTrack.get(round).remove(diceIndex);
        roundTrack.get(round).add(diceIndex,dice);
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
