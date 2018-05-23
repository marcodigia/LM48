package it.polimi.ingsw.Server.Game.GameRules.Actions.Complex;

import it.polimi.ingsw.UI;
import it.polimi.ingsw.Exceptions.EndOfTurnException;
import it.polimi.ingsw.Server.Game.Cards.WindowPatternCard;
import it.polimi.ingsw.Server.Game.Components.Dice;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Actions;
import it.polimi.ingsw.Server.Game.GameRules.GameContext;

public class MoveOneDiceIgnoringValue implements Actions {


  private  int from , to;
  private boolean ACTIVE = true;
    @Override
    public void doAction(GameContext gameContext) {
        if (!ACTIVE)
            return;

           if( gameContext.getWindowPatternCard().moveDice(from, to, false, true, false))
            ACTIVE = false;

    }


    @Override
    public void useAction(UI ui, GameContext gameContext) {

        if (!ACTIVE)
            return;

        if (!existsValidMove(gameContext.getWindowPatternCard(),true)){
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
