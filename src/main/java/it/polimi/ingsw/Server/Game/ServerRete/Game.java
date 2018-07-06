package it.polimi.ingsw.Server.Game.ServerRete;


import it.polimi.ingsw.Server.Game.Cards.*;
import it.polimi.ingsw.Server.Game.GameRules.GameSetup;
import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import it.polimi.ingsw.Server.Game.GameRules.Player;
import it.polimi.ingsw.Server.Game.TimerUtility.TimerUtility;
import it.polimi.ingsw.Server.Game.Utility.CONSTANT;
import it.polimi.ingsw.Server.Game.Utility.Unpacker;

import java.rmi.RemoteException;
import java.util.*;

public class Game {
    //LinkedHashMap let us iterate over ordered Map
    private LinkedHashMap<Player,Boolean> players = new LinkedHashMap<Player,Boolean>(); // True -> associated player turn False -> otherwise
    private GameStatus gameStatus;
    private GameSetup gameSetup;
    private final Object lock = new Object();

    Timer timerwp = new Timer();
    TimerUtility timerUtilitywp= new TimerUtility();


    public Game(){
    }

    public LinkedHashMap<Player,Boolean> getPlayers(){
        return players;
    }

    public GameStatus getGameStatus(){
        return gameStatus;
    }

    public Game(ArrayList<Player> playerToAdd){
        synchronized (lock){
            players = new LinkedHashMap<Player,Boolean>();
            for(Player p : playerToAdd){
                players.put(p, false);
            }
        }
    }

    public Player scanForUsername(String username){
        Player pr = null;
        if(players != null){
            for(Player p : players.keySet())
                if(p.getName().equals(username))
                    pr = p;
        }
        return pr;
    }

    /**
     * @param playerToAdd arrays of players to add to the game which is going to start
     * 
     */
    public void createNewGame(ArrayList<Player> playerToAdd){
        synchronized (lock){
            addPlayer(playerToAdd);
            gameSetup = new GameSetup(players); //TC - PBC - WP
            gameStatus = new GameStatus(gameSetup.getToolCards(),gameSetup.getPublicObjectiveCards());  //TC and PBC will not change also if

            //TODO Very important to setup here the gameStatus. The Unpacker need a reference to it in order to do Act_from Packet.
            Unpacker.setGameStatus(gameStatus);

            //client will disconnect after WP sending

            gameSetup.getPublicObjectiveCards().clear();
            gameSetup.getToolCards().clear();
            gameAskClientForWindow();
        }
    }

    /**
     * @param username username of player that should be set as disconnected
     *
     */
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

        System.out.println("setWP to plater GAME" + idWp);
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
        else
            return;

        if (windowToRemove==null){
            windowToRemove = Unpacker.WP_fromPacket(idWp,CONSTANT.emptyWp);
        }
        gameStatus.addWindowPatternCard(playerRecived, windowToRemove);  //Add tuples of players and WP to GameStatus
        playerRecived.getWallet().setUpWallet(windowToRemove.getDifficulty());

    }

    //TODO change timervalue
    private synchronized void gameAskClientForWindow(){

        sendWindowPatternToChoose();
        timerwp.schedule(new TimerTask() {
            @Override
            public void run() {
                boolean q = deleteWhoLeftGame();
                if(!q)
                    endGameSetUp();    //End setup with players who are still playing
                else
                    endGame();          //All players left game
            }
        }, timerUtilitywp.readTimerFromFile(10,"timerDelayPlayer.txt"));

    }

    private synchronized void sendWindowPatternToChoose(){

        ArrayList<WindowPatternCard> wp = new ArrayList<WindowPatternCard>(gameSetup.getWindowPatternCards());
        Set<Player> playerToWP;
        playerToWP = players.keySet();
        int i=0;
        if(playerToWP.size()>0){
            for(Player p : playerToWP){
                System.out.println("sendWindowPattern: " + p.getName());
                String id1, id2 , id3, id4;
                id1 = wp.remove(0).getID();
                id2 = wp.remove(0).getID();
                id3 = wp.remove(0).getID();
                id4 = wp.remove(0).getID();
                System.out.println(p.getName());
                p.getvirtualView().chooseWindowPattern(id1,id2,id3,id4);

            }
        }

    }

    private synchronized void endGameSetUp(){

        gameSetup.concludeSetUp(players);  //Extract PB card
        gameStatus.addPrivateObjectiveCard(gameSetup.getPrivateObjectiveCards());
        gameStatus.setDraftPool(gameSetup.getDraftPool());
        gameStatus.setBoardRound(gameSetup.getBoardRound());
        gameStatus.setDiceBag(gameSetup.getDiceBag());
        for(Player p : players.keySet()){
            p.getvirtualView().sendGameStatus(gameStatus);
        }
        manageRound();

    }

    //TODO Change turn timer value
    private void manageRound(){
        Thread t = new Thread(new Turn(players, gameStatus));
        t.start();
    }

    //Add player to game
    private synchronized void addPlayer(ArrayList<Player> playersToAdd){

        String s = "Welcome to Sagrada";
        if(playersToAdd!=null)
            for(Player p : playersToAdd){
                players.put(p, false);
                p.getvirtualView().sendMessage(s);
            }

    }

    //This method is called by Game after WP-player pairing. If some players have false
    //value in HashMap they will be removed as consequence of their no decision of WP

    /**
     * @return true if all player are removed
     * @return false if there is at least one player
     */
    private synchronized boolean deleteWhoLeftGame(){

        ArrayList<Player> playersToRemove = new ArrayList<Player>();
        for(Player p : players.keySet()){
            if(!p.getConnected()){
                playersToRemove.add(p);
            }
        }

        for(Player p : playersToRemove){
            players.remove(p);
            gameStatus.deleteWindowPatternCard(p);
        }
        if(players.keySet().size()==0) {
            return true;
        }
        return false;

    }

    private void endGame(){
        synchronized (lock){

        }
        System.out.println("End game");
        //TODO
    }

}
