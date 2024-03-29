package it.polimi.ingsw.Server.Game.ServerRete;

import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import it.polimi.ingsw.Server.Game.GameRules.Player;
import it.polimi.ingsw.Server.Game.TimerUtility.TimerUtility;
import it.polimi.ingsw.Server.Game.Utility.ANSI_COLOR;
import it.polimi.ingsw.Server.Game.Utility.CONSTANT;

import java.rmi.RemoteException;
import java.util.*;

public class Turn extends TimerTask implements Runnable {

    private static LinkedHashMap<Player,Boolean> players;
    private GameStatus gameStatus;

    private static boolean winnerFind = false;
    private static int numberOfTurn;
    private static Player currentPlayer = null;
    private static boolean back = false;
    private static boolean notImmediately = false;
    private static int turn=0;
    private static int round=0;


    TimerUtility timerUtilityround = new TimerUtility();

    public Turn(LinkedHashMap<Player, Boolean> players, GameStatus gameStatus){
        this.players=players;
        numberOfTurn=players.keySet().size()*2;
        this.gameStatus=gameStatus;
    }

    public static Player getCurrentPlayer(){return currentPlayer;}

    public static int getNumberOfTurn(){
        return numberOfTurn;
    }

    public static int getSecondTurn(){

        if(turn < players.keySet().size())
            return 1;

        return 2;
    }

    public static synchronized LinkedHashMap<Player, Boolean> getPlayers() {
        return new LinkedHashMap<>(players);
    }

    @Override
    public void run() {
        synchronized (players){

            if(!winnerFind){

                if(round< CONSTANT.numberOfRound){
                    System.out.println("ROUND: " + round + "\n" + "Turn: " + turn);
                    for(Player p : players.keySet())
                        System.out.println(ANSI_COLOR.ANSI_PURPLE + "Player : " + p.getName() + " " + p.getConnected() +  " " +  p.getStillAlive() + ANSI_COLOR.ANSI_RESET);
                    if(currentPlayer!=null){
                        currentPlayer.getvirtualView().timerEnd();
                        currentPlayer.startRound();
                        //Send to each player who result connected new GameStatus
                        for(Map.Entry<Player,Boolean> entry : players.entrySet()) {
                            entry.getKey().getvirtualView().sendGameStatus(gameStatus);

                        }
                    }
                    if(lookForWinner() == null){
                        currentPlayer=manageTurn();
                        Timer timerRound = new Timer();

                        for(Player p : players.keySet())
                            System.out.println(ANSI_COLOR.ANSI_PURPLE + "Player : " + p.getName() + " " + p.getConnected() +  " " +  p.getStillAlive() + ANSI_COLOR.ANSI_RESET);
                        if(currentPlayer==null || currentPlayer.getSkipNextTurn()) { //currentPlayer is not connected
                            timerRound.schedule(new Turn(players, gameStatus), 0);
                            System.out.println("current true skip next");
                        }
                        else                    //currentPlayer is connected
                            timerRound.schedule(new Turn(players,gameStatus),timerUtilityround.readTimerFromFile(60,"timerDelayPlayers.txt"));

                        turn++;
                    }
                    else{
                        Player winner = gameStatus.getBoardRound().getWinner(gameStatus);
                        System.out.println("There is a winner");
                        for(Player p : players.keySet())
                        {
                            if (p!=null)
                                p.getvirtualView().sendScore(winner);

                        }
                    }
                    if(turn>=numberOfTurn){
                        //Move dices from draftpool to boardRound
                        gameStatus.getBoardRound().addDices(gameStatus.getDraftPool().getDraft());
                        //Extract new dices from DraftPool
                        gameStatus.getDraftPool().extractNdice(players.keySet().size()*2+1);
                        turn=0;
                        round++;
                        for(Player p : players.keySet())
                            p.setSkipNextTurn(false);
                        ArrayList<Player> p = new ArrayList<Player>(players.keySet());
                        Player q = p.get(0);
                        p.remove(0);
                        players.remove(q);
                        players.put(q,false);
                    }
                }
                else{
                    Player winner = gameStatus.getBoardRound().getWinner(gameStatus);
                    System.out.println("Game end");
                    for(Player p : players.keySet())
                    {
                        if (p!=null)
                            p.getvirtualView().sendScore(winner);

                    }
                }
            }
        }
    }

    private Player manageTurn(){

        Player player = null;

        //Forth
        if(!back){
            for(Map.Entry<Player,Boolean> entry : players.entrySet()){

                if(!entry.getValue()){
                    back = true;
                    notImmediately = true;
                    entry.setValue(true);
                    if(entry.getKey().getConnected()){
                        System.out.println("CURRENT: " + entry.getKey().getName());
                        entry.getKey().getvirtualView().timerStart();   //Say player that his/her turn is started
                        player = entry.getKey();
                    }
                    break;
                    //If player is not connected timer will be started with 0 value
                }
            }
            for(Map.Entry<Player,Boolean> entry : players.entrySet()){
                if(!entry.getValue())
                    back = false;
            }
        }

        //Back
        if(!notImmediately && back){
            ArrayList<Player> keyList = new ArrayList<Player>(players.keySet());
            for (int i = keyList.size() - 1; i >= 0; i--) {
                Player key = (Player) keyList.get(i);
                if(players.get(key)){
                    back = false;
                    notImmediately = true;
                    players.put(key, false);
                    if(key.getConnected()){
                        System.out.println("CURRENT: " + key.getName());
                        key.getvirtualView().timerStart();   //Say player that his/her turn is started
                        player = key;
                    }
                    break;
                    //If player is not connected timer will be started with 0 value
                }
            }
            for(Map.Entry<Player,Boolean> entry : players.entrySet()){
                if(entry.getValue()) {
                    back = true;
                    notImmediately = false;
                }
            }
        }
        else{
            notImmediately = false;
        }

        if(player!=null)
            return gameStatus.getPlayerByName(player.getName());
        return null;
    }

    /**
     * @return the last player online , null if nobody is left
     */
    private Player lookForWinner(){
        int i = 0;
        Player winner = null;
        for(Player p : players.keySet()){
            if(p.getConnected()) {
                winner = p;
                i++;
            }
        }
        if(i==1){
            winnerFind = true;
            return winner;
        }
        return null;
    }

}
