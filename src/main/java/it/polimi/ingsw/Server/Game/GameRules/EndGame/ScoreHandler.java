package it.polimi.ingsw.Server.Game.GameRules.EndGame;

import it.polimi.ingsw.Server.Game.Cards.PrivateObjectiveCard;
import it.polimi.ingsw.Server.Game.Cards.PublicObjectiveCard;
import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import it.polimi.ingsw.Server.Game.GameRules.Player;

import java.util.ArrayList;
import java.util.Hashtable;

public class ScoreHandler {

    GameStatus gameStatus;
    public ScoreHandler(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public Hashtable<Player,Integer> getFinalScore(){

        Hashtable<Player,Integer> finalScore = new Hashtable<>();
        for ( Player player : gameStatus.getPlayer()){
            Integer score = 0;
            for (PublicObjectiveCard pB : gameStatus.getPublicObjectiveCards()){
                score += pB.getPoints( player.getWindowPatternCard());
            }
            score +=  gameStatus.getPlayerPrivateObjectiveCards(
                    player.getName())
                    .getPoints(player.getWindowPatternCard());

            finalScore.put(player,score);
        }
        return finalScore;
    }
}
