package it.polimi.ingsw.Server.Game.ServerRete;

import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import it.polimi.ingsw.Server.Game.GameRules.Player;
import it.polimi.ingsw.Server.Game.Utility.CONSTANT;

import java.rmi.RemoteException;
import java.util.*;

public class Turn extends TimerTask {

    private LinkedHashMap<Player,Boolean> players;
    private GameStatus gameStatus;

    private static int numberOfTurn;
    private static Player currentPlayer = null;
    private static boolean back = false;
    private static boolean notImmediately = false;
    private static int turn=0;
    private static int round=0;

    public Turn(LinkedHashMap<Player, Boolean> players, GameStatus gameStatus){
        this.players=players;
        numberOfTurn=players.keySet().size()*2;
        this.gameStatus=gameStatus;
    }

    @Override
    public void run() {
        if(turn==numberOfTurn){
            //Move dices from draftpool to boardRound
            gameStatus.getBoardRound().addDices(gameStatus.getDraftPool().getDraft());
            //Extract new dices from DraftPool
            gameStatus.getDraftPool().extractNdice(players.keySet().size()*2+1);
            turn=0;
            round++;
            ArrayList<Player> p = new ArrayList<Player>(players.keySet());
            Player q = p.get(0);
            p.remove(0);
            players.remove(q);
            players.put(q,false);
        }
        if(round< CONSTANT.numberOfRound){
            if(currentPlayer!=null){
                currentPlayer.getvirtualView().timerEnd();
                //Send to each player who result connected new GameStatus
                for(Map.Entry<Player,Boolean> entry : players.entrySet()) {
                    if (entry.getKey().getConnected()) {
                        entry.getKey().getvirtualView().sendGameStatus(gameStatus);
                    }
                }
            }
            currentPlayer=manageTurn();
            gameStatus.getPlayerByName(currentPlayer.getName()).startRound();
            turn++;
        }
        else{
            //TODO segnala fine gioco
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
                        entry.getKey().getvirtualView().timerStart();   //Say player that his/her turn is started
                        player = entry.getKey();
                        break;
                    }
                    //If player is not connected timer will not be started
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
                        key.getvirtualView().timerStart();   //Say player that his/her turn is started
                        player = key;
                        break;
                    }
                    //If player is not connected timer will not be started
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

        return player;
    }
}
