package it.polimi.ingsw.Server.Game.GameRules.Actions.Complex;

import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import it.polimi.ingsw.Server.Game.GameRules.Player;
import it.polimi.ingsw.UI;
import it.polimi.ingsw.Server.Game.Cards.WindowPatternCard;
import it.polimi.ingsw.Server.Game.Components.Dice;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Actions;

public class MoveOneDiceIgnoringValue implements Actions {


  private  int from , to;
  private boolean ACTIVE = true;
  String userName;
    @Override
    public void doAction(GameStatus gameStatus) {


        Player activePlayer = gameStatus.getPlayerByName(userName) ;
        WindowPatternCard activePlayerWP = (WindowPatternCard)gameStatus.getPlayerCards().get(activePlayer).get(0);

        if (!ACTIVE)
            return;

           if( activePlayerWP.moveDice(from, to, false, true, false))
            ACTIVE = false;

    }


    @Override
    public void useAction(UI ui, GameStatus gameStatus, String userName) {

        this.userName = userName;
        Player activePlayer = gameStatus.getPlayerByName(userName) ;
        WindowPatternCard activePlayerWP = (WindowPatternCard)gameStatus.getPlayerCards().get(activePlayer).get(0);

        if (!ACTIVE)
            return;

        if (!existsValidMove(activePlayerWP,true)){
            ui.printMessage("No possible moves");
            return;
        } else {

            final boolean[] result = new boolean[1];

            result[0] = true;
            //If the user interupt the action because he decide to do something else the action remains active
                Thread getUserInputThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        from = ui.getMatrixIndexFrom();
                        to = ui.getMatrixIndexTo();

                    }
                });
                getUserInputThread.start();
                try {
                    getUserInputThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    result[0]=false;

                }

            if (!result[0]){
                return;
            }

            //TODO send action to the server

        }


    }

    @Override
    public void setUpPlaceDiceAction(String packet) {

    }

    @Override
    public void setUserName(String userName) {

    }


    private boolean existsValidMove(WindowPatternCard windowPatternCard , boolean ignoreValue){
        for (int i =0 ;  i < 20 ; i++){
            Dice dice = windowPatternCard.removeDice(i);
            for (int j = 0 ; j< 20 ; j++){
                if (j!=i){
                    // if found a suitable place for the dice according to the restricitons
                    if (windowPatternCard.isPlaceable(dice,j,false,ignoreValue,false)){
                        //Put dice back
                        windowPatternCard.placeDice(dice,i,true,true,true);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String toPacket() {
        return null;
    }
}
