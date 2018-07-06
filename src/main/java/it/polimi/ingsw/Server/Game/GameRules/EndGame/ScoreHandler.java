package it.polimi.ingsw.Server.Game.GameRules.EndGame;

import it.polimi.ingsw.Server.Game.Cards.PrivateObjectiveCard;
import it.polimi.ingsw.Server.Game.Cards.PublicObjectiveCard;
import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import it.polimi.ingsw.Server.Game.GameRules.Player;
import it.polimi.ingsw.Server.Game.Utility.ANSI_COLOR;

import java.util.ArrayList;
import java.util.Hashtable;

public class ScoreHandler {

    private GameStatus gameStatus;

    public ScoreHandler(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }


    /**
     * This method uses the gameStaus provided with the class constructor to build an HashTable with Player as the key
     * and the correspondig score as value
     * @return An HashTable where The Player is the key and its score the value
     */
    public Hashtable<Player,Integer> getFinalScore(){

        Hashtable<Player,Integer> finalScore = new Hashtable<>();
        for ( Player player : gameStatus.getPlayer()){
            Integer score = 0;
            System.out.println(ANSI_COLOR.ANSI_YELLOW+gameStatus.toPacket()+ANSI_COLOR.ANSI_RESET);
            for (PublicObjectiveCard pB : gameStatus.getPublicObjectiveCards()){
                score += pB.getPoints( gameStatus.getPlayerWP(player));
            }

            if (gameStatus.getPlayerPrivateObjectiveCards(player.getName())!=null){

                int privateObjectivescore = gameStatus.getPlayerPrivateObjectiveCards(
                        player.getName())
                        .getPoints(gameStatus.getPlayerWP(player));
                score +=  privateObjectivescore;
                player.setPbScore(privateObjectivescore);
            }


            finalScore.put(player,score);
        }
        return finalScore;
    }
}
