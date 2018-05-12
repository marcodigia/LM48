package it.polimi.ingsw.Server.Game.GameRules.Actions.Complex;

import it.polimi.ingsw.UI;
import it.polimi.ingsw.Exceptions.EndOfTurnException;
import it.polimi.ingsw.Server.Game.Components.Dice;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Actions;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Basic.PlaceDiceAction;
import it.polimi.ingsw.Server.Game.GameRules.GameContext;

public class RerollDraftedDice implements Actions {

    private boolean ACTIVE = true;
    private int MatrixSixe = 20;
    private int diceIndex;
    private UI ui;

    public RerollDraftedDice() {
    }

    @Override
    public void doAction(GameContext gameContext) {

        if (!ACTIVE)
            return;
        Dice diceToReroll = gameContext.getDraftPool().getDice(diceIndex);
        diceToReroll.reroll();

        ACTIVE = false;
    }

    @Override
    public void useAction(UI ui, GameContext gameContext) {
        if (!ACTIVE)
            return;

        final boolean[] result = {true};
        Thread getUserInputThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    diceIndex = ui.getDraftPoolIndex();


                } catch (EndOfTurnException e) {
                    e.printStackTrace();
                    result[0] = false;
                }

            }
        });

        getUserInputThread.start();

        try {
            getUserInputThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return;
        }

        //TODO send action to the Server
        Actions nextAction = null;
        //To be done on Server but at the moment is done here because there is no server implementation yet
        doAction(gameContext);
        //An action shuld be return by the server
        // reupdate the UI
        nextAction = new PlaceDiceAction(gameContext.getDraftPool().getDice(diceIndex), false, false, false);

        if (nextAction != null)
            nextAction.useAction(ui, gameContext);

        nextAction.doAction(gameContext);




    }


    @Override
    public String toPacket() {
        return null;
    }
}
