package it.polimi.ingsw.Server.Game.ServerRete;


import it.polimi.ingsw.Server.Game.Cards.*;
import it.polimi.ingsw.Server.Game.GameRules.GameSetup;
import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import it.polimi.ingsw.Server.Game.GameRules.Player;
import it.polimi.ingsw.Server.Game.TimerUtility.TimerUtility;
import it.polimi.ingsw.Server.Game.Utility.CONSTANT;

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

    public static void main(String[] args){
        ArrayList player = new ArrayList<>();
        Player p = new Player("lucia",null);
        p.setIsConnected();
        player.add(p);
        p = new Player("maria",null);
        p.setIsConnected();
        player.add(p);
        p = new Player("stefano",null);
        p.setIsConnected();
        player.add(p);
        p = new Player("matteo",null);
        p.setIsConnected();
        player.add(p);
        Game game = new Game(player);
        game.manageRound();
    }

    public Game(ArrayList<Player> playerToAdd){
        players = new LinkedHashMap<>();
        for(Player p : playerToAdd){
            players.put(p, false);
        }
    }

    public void createNewGame(ArrayList<Player> playerToAdd){
        players = new LinkedHashMap<>();
        addPlayer(playerToAdd);
        gameSetup = new GameSetup(players); //TC - PBC - WP
        gameStatus = new GameStatus(gameSetup.getToolCards(),gameSetup.getPublicObjectiveCards());  //TC and PBC will not change also if
                                                                                                    //client will disconnect after WP sending
        gameSetup.getPublicObjectiveCards().clear();
        gameSetup.getToolCards().clear();
        gameAskClientForWindow();
    }

    //TODO control if idWP belongs to WPs send to client
    public synchronized void setWindowToPlayer(String idWp, String username){
        HashMap<Player,WindowPatternCard> playerWP = new HashMap<>();
        WindowPatternCard windowToRemove = null;
        Player playerRecived = null;    //Used to modify HashMap. Set true mapped value to denote
        //that the player is still playing
        for(Player p : players.keySet()) {
            if (p.getName().equals(username)) {
                playerRecived = p;
                for (WindowPatternCard w : gameSetup.getWindowPatternCards()) {
                    if (w.getID().equals(idWp)) {
                        p.getGameContext().setWindowPatternCard(w);     //Assign WP to player
                        windowToRemove = w;
                        playerWP.put(p, w);                              //Add tuple Player WP
                    }
                }
            }
        }
        if(playerRecived!=null)  //Now after scan data structure, it can be modified
            playerRecived.setIsConnected();
        if(windowToRemove!=null)
            gameSetup.getWindowPatternCards().remove(windowToRemove);
        gameStatus.addWindowPatternCard(playerWP);  //Add tuples of players and WP to GameStatus
    }

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
        }, timerUtility.readTimerFromFile(40,"timerDelayPlayer.txt"));
    }

    private void manageRound(){
        for(int i=0;i< CONSTANT.numberOfRound;i++){
            System.out.println("\n"+i+"\n");
            for(int j=0;j<players.size()*2;j++){
                manageTurn(j);
            }
            ArrayList<Player> p = new ArrayList(players.keySet());
            Player q = p.get(0);
            p.remove(0);
            players.remove(q);
            players.put(q,false);
        }
    }

    private void manageTurn(int j){
        //Forth
        if(!back){
            for(Map.Entry<Player,Boolean> entry : players.entrySet()){
                if(!entry.getValue()){
                    back = true;
                    notImmediately = true;
                    entry.setValue(true);
                    if(entry.getKey().getConnected()){
                        //TODO give control to this palyer and disable other players
                        System.out.println(j+" : "+entry.getKey().getName());
                        break;
                    }
                    else{
                        //TODO this player cannot play this turn
                    }
                }
            }
            for(Map.Entry<Player,Boolean> entry : players.entrySet()){
                if(!entry.getValue())
                    back = false;
            }
        }

        //Back
        if(!notImmediately && back){
            ArrayList keyList = new ArrayList(players.keySet());
            for (int i = keyList.size() - 1; i >= 0; i--) {
                Player key = (Player) keyList.get(i);
                if(players.get(key)){
                    back = false;
                    notImmediately = true;
                    players.put(key, false);
                    if(key.getConnected()){
                        //TODO give control to this palyer and disable other players
                        System.out.println(j+" : "+key.getName());
                        break;
                    }
                    else{

                        //TODO this player cannot play this turn
                    }
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
    }

    private void endGameSetUp(){
        gameSetup.concludeSetUp(players);  //Extract PB card
        gameStatus.addPrivateObjectiveCard(gameSetup.getPrivateObjectiveCards());
        gameSetup.getPrivateObjectiveCards().clear();
    }

    //Add player to game
    private void addPlayer(ArrayList<Player> playersToAdd){
        String s = "Hello";
        if(playersToAdd!=null)
            for(Player p : playersToAdd){
                players.put(p, false);
                p.getvirtualView().sendMessage(s);
            }
    }

    //This method is called by Game after WP-player pairing. If some players have false
    //value in HashMap they will be removed as consequence of their no decision of WP
    private void deleteWhoLeftGame(){
        ArrayList<Player> playersToRemove = new ArrayList<>();
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

    private void sendWindowPatternToChoose(){
        ArrayList<WindowPatternCard> wp = new ArrayList<>(gameSetup.getWindowPatternCards());
        Set<Player> playerToWP;
        playerToWP = players.keySet();
        int i=0;
        for(Player p : playerToWP){
            String id1, id2 , id3, id4;
            id1 = wp.remove(0).getID();
            id2 = wp.remove(0).getID();
            id3 = wp.remove(0).getID();
            id4 = wp.remove(0).getID();
            System.out.println(id1+id2+id3+id4);
            System.out.println(wp.size());
            p.getvirtualView().chooseWindowPattern(id1,id2,id3,id4);
        }
    }


}
