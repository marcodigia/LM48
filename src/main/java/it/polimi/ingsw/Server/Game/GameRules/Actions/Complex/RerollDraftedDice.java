package it.polimi.ingsw.Server.Game.GameRules.Actions.Complex;

import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import it.polimi.ingsw.UI;
import it.polimi.ingsw.Server.Game.Components.Dice;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Actions;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Basic.PlaceDiceAction;

public class RerollDraftedDice implements Actions {

    private boolean ACTIVE = true;
    private int MatrixSixe = 20;
    private int diceIndex;
    private UI ui;

    public RerollDraftedDice() {
    }

    @Override
    public void doAction(GameStatus gameStatus) {

        if (!ACTIVE)
            return;
        Dice diceToReroll = gameStatus.getDraftPool().getDice(diceIndex);
        diceToReroll.reroll();

        ACTIVE = false;
    }

    @Override
    public void useAction(UI ui, GameStatus gameStatus, String userName) {
        if (!ACTIVE)
            return;

        final boolean[] result = {true};
        Thread getUserInputThread = new Thread(new Runnable() {
            @Override
            public void run() {
                diceIndex = ui.getDraftPoolIndex();
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
        doAction(gameStatus);
        //An action shuld be return by the server
        // reupdate the UI
        nextAction = new PlaceDiceAction(gameStatus.getDraftPool().getDice(diceIndex), false, false, false);

        if (nextAction != null)
            nextAction.useAction(ui, gameStatus, userName);

        nextAction.doAction(gameStatus);




    }

    @Override
    public void setACTIVE(boolean b) {

    }

    @Override
    public void setUpPlaceDiceAction(String packet) {

    }

    @Override
    public void setUserName(String userName) {

    }


    @Override
    public String toPacket() {
        return null;
    }
}
