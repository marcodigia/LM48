package it.polimi.ingsw.Server.Game.GameRules;


import it.polimi.ingsw.Server.Game.Cards.*;

import java.util.*;

public class Game {

    private HashMap<Player ,Boolean> players; // True -> associated player turn False -> otherwise
    private GameStatus gameStatus;
    private GameSetup gameSetup;



    public Game(ArrayList<Player> playerToAdd){
        players = new HashMap<>();
        addPlayer(playerToAdd);
        gameSetup = new GameSetup(players); //TC - PBC - WP
        gameStatus = new GameStatus(gameSetup.getToolCards(),gameSetup.getPublicObjectiveCards());  //TC and PBC will not change also if
                                                                                                    //client will disconnect after WP sending
        gameSetup.getPublicObjectiveCards().clear();
        gameSetup.getToolCards().clear();
    }

    public int getNumberOfPlayers(){
        return players.size();
    }

    public void endGameSetUp(){
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

    //This method is called by ServerRete after WP-player pairing. If some players have false
    //value in HashMap they will be removed as consequence of their no decision of WP
    public void deleteWhoLeftGame(){
        ArrayList<Player> playersToRemove = new ArrayList<>();
        for(Player p : players.keySet()){
            if(!players.get(p)){
                playersToRemove.add(p);
            }
        }
        for(Player p : playersToRemove){
            players.remove(p);
        }
    }

    public void lookForWinner(){
        //TODO implement
    }

    //TODO control if idWP belongs to WPs send to client
    public void setWindowToPlayer(String idWp, String username){
        HashMap<Player,WindowPatternCard> playerWP = new HashMap<>();
        Player playerRecived = null;    //Used to modify HashMap. Set true mapped value to denote
                                        //that the player is still playing
        for(Player p : players.keySet()) {
            if (p.getName().equals(username)) {
                playerRecived = p;
                for (WindowPatternCard w : gameSetup.getWindowPatternCards()) {
                    if (w.getID().equals(idWp)) {
                        p.getGameContext().setWindowPatternCard(w);     //Assign WP to player
                        gameSetup.getWindowPatternCards().remove(w);    //Remove WP from GameSetUp
                        playerWP.put(p, w);                              //Add tuple Player WP
                    }
                }
            }
        }
        if(playerRecived!=null) //Now after scan data structure, it can be modified
            players.replace(playerRecived,true);
        gameStatus.addWindowPatternCard(playerWP);  //Add tuples of players and WP to GameStatus
    }

    public void sendWindowPatternToChoose(){
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
