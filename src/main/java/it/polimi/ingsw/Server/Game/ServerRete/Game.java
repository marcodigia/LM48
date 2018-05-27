package it.polimi.ingsw.Server.Game.ServerRete;


import it.polimi.ingsw.Server.Game.Cards.*;
import it.polimi.ingsw.Server.Game.GameRules.GameSetup;
import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import it.polimi.ingsw.Server.Game.GameRules.Player;
import it.polimi.ingsw.Server.Game.TimerUtility.TimerUtility;
import it.polimi.ingsw.Server.Game.Utility.CONSTANT;

import java.rmi.RemoteException;
import java.util.*;

public class Game {
    //LinkedHashMap let us iterate over ordered Map
    private LinkedHashMap<Player,Boolean> players; // True -> associated player turn False -> otherwise
    private GameStatus gameStatus;
    private GameSetup gameSetup;

    private boolean back = false;
    private boolean notImmediately = false;



    public Game(){
    }

    public LinkedHashMap<Player,Boolean> getPlayers(){
        return players;
    }

    public GameStatus getGameStatus(){
        return gameStatus;
    }

    public Game(ArrayList<Player> playerToAdd){
        players = new LinkedHashMap<Player,Boolean>();
        for(Player p : playerToAdd){
            players.put(p, false);
        }
    }

    public void createNewGame(ArrayList<Player> playerToAdd){
        players = new LinkedHashMap<Player,Boolean>();
        addPlayer(playerToAdd);
        gameSetup = new GameSetup(players); //TC - PBC - WP
        gameStatus = new GameStatus(gameSetup.getToolCards(),gameSetup.getPublicObjectiveCards());  //TC and PBC will not change also if
                                                                                                    //client will disconnect after WP sending

        gameSetup.getPublicObjectiveCards().clear();
        gameSetup.getToolCards().clear();
        gameAskClientForWindow();
    }

    public synchronized  void setPlayerAsDisconnected(String username){
        if(players!=null){
            for(Player p : players.keySet()){
                if(p.getName().equals(username)){
                    System.out.println("Diconnect : "+username);
                    p.setIsNotConnected();
                    p.getvirtualView().sendMessage("You disconnect from game.\n" +
                            "You will be able to reconnect with your username.");
                }
            }
        }
    }

    //TODO control if idWP belongs to WPs send to client
    public synchronized void setWindowToPlayer(String idWp, String username){
        WindowPatternCard windowToRemove = null;
        Player playerRecived = null;    //Used to modify HashMap. Set true mapped value to denote
        //that the player is still playing
        for(Player p : players.keySet()) {
            if (p.getName().equals(username)) {
                playerRecived = p;
                for (WindowPatternCard w : gameSetup.getWindowPatternCards())
                    if (w.getID().equals(idWp))
                        windowToRemove = w;

            }
        }
        if(playerRecived!=null)  //Now after scan data structure, it can be modified
            playerRecived.setIsConnected();
        /*if(windowToRemove!=null)
            gameSetup.getWindowPatternCards().remove(windowToRemove);*/
        gameStatus.addWindowPatternCard(playerRecived, windowToRemove);  //Add tuples of players and WP to GameStatus
    }

    //TODO change timervalue
    private void gameAskClientForWindow(){
        sendWindowPatternToChoose();
        Timer timer = new Timer();
        TimerUtility timerUtility= new TimerUtility();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                deleteWhoLeftGame();
                lookForWinner();
                endGameSetUp();    //End setup with players who are still playing
            }
        }, timerUtility.readTimerFromFile(10,"timerDelayPlayer.txt"));
    }

    private void sendWindowPatternToChoose(){
        ArrayList<WindowPatternCard> wp = new ArrayList<WindowPatternCard>(gameSetup.getWindowPatternCards());
        Set<Player> playerToWP;
        playerToWP = players.keySet();
        int i=0;
        for(Player p : playerToWP){
            String id1, id2 , id3, id4;
            id1 = wp.remove(0).getID();
            id2 = wp.remove(0).getID();
            id3 = wp.remove(0).getID();
            id4 = wp.remove(0).getID();
            System.out.println(p.getName());
            p.getvirtualView().chooseWindowPattern(id1,id2,id3,id4);

        }
    }

    private void endGameSetUp(){
        gameSetup.concludeSetUp(players);  //Extract PB card
        gameStatus.addPrivateObjectiveCard(gameSetup.getPrivateObjectiveCards());
        gameSetup.getPrivateObjectiveCards().clear();
        gameStatus.setDraftPool(gameSetup.getDraftPool());
        gameStatus.setBoardRound(gameSetup.getBoardRound());
        for(Player p : players.keySet()){
            p.getvirtualView().sendGameStatus(gameStatus);
        }
        manageRound();
    }

    //TODO Change turn timer value
    private void manageRound(){

        TimerUtility timerUtility = new TimerUtility();
        Timer timer = new Timer();
        timer.schedule(new Turn(players, gameStatus),0,timerUtility.readTimerFromFile(30,"timerTurnPlayer.txt"));
    }

    //Add player to game
    private void addPlayer(ArrayList<Player> playersToAdd){
        String s = "Welcome to Sagrada";
        if(playersToAdd!=null)
            for(Player p : playersToAdd){
                players.put(p, false);
                p.getvirtualView().sendMessage(s);
            }
    }

    //This method is called by Game after WP-player pairing. If some players have false
    //value in HashMap they will be removed as consequence of their no decision of WP
    private void deleteWhoLeftGame(){
        ArrayList<Player> playersToRemove = new ArrayList<Player>();
        for(Player p : players.keySet()){
            if(!p.getConnected()){
                playersToRemove.add(p);
            }
        }

        for(Player p : playersToRemove){
            players.remove(p);
        }
    }

    //In order to understand who is the winner check size of players and for each player who is
    //still in the game check if he/she is connected or not
    private void lookForWinner(){
        //TODO implement
    }

}
