package it.polimi.ingsw.Server.Game.GameRules.Actions.Complex;

import it.polimi.ingsw.UI;
import it.polimi.ingsw.Exceptions.EndOfTurnException;
import it.polimi.ingsw.Server.Game.Components.Boards.DraftPool;
import it.polimi.ingsw.Server.Game.Components.Dice;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Actions;
import it.polimi.ingsw.Server.Game.GameRules.GameContext;

public class ChangeDiceValueByOne implements Actions {

    private DraftPool draftPool;

    private int ammount;
    private int draftPoolIndex;
    @Override
    public void doAction(GameContext gameContext) {
        draftPool = gameContext.getDraftPool();
        Dice diceToChange = draftPool.getDice(draftPoolIndex);


        if (ammount == 1) {
            if (Integer.parseInt(diceToChange.getValue()) < 6) {

                diceToChange.setValue(Integer.parseInt(diceToChange.getValue()) + ammount);
            }
        } else if (ammount == -1) {
            if (Integer.parseInt(diceToChange.getValue()) > 1) {
                diceToChange.setValue(Integer.parseInt(diceToChange.getValue()) + ammount);
            }

        }
    }

    @Override
    public void useAction(UI ui, GameContext gameContext) {

        final boolean[] result = new boolean[1];
        do {

            result[0] = true;
            Thread getUserInputThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        draftPoolIndex = ui.getDraftPoolIndex();
                        ammount = ui.getAmmountToChange();

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
            }

            if (!result[0])
                return;
        } while (ammount != -1 && ammount != 1);

        //TODO send the action to the server !!

    }


    @Override
    public String toPacket() {
        return null;
    }
}
