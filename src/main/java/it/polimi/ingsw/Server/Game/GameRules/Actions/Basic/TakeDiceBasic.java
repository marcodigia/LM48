package it.polimi.ingsw.Server.Game.GameRules.Actions.Basic;

import it.polimi.ingsw.UI;
import it.polimi.ingsw.Exceptions.EndOfTurnException;
import it.polimi.ingsw.Server.Game.Cards.WindowPatternCard;
import it.polimi.ingsw.Server.Game.Components.Boards.DraftPool;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Actions;
import it.polimi.ingsw.Server.Game.GameRules.GameContext;

public class TakeDiceBasic implements Actions {

    private WindowPatternCard windowPatternCard;
    private DraftPool draftPool;

    private int from, to;
    private boolean active = false;

    public TakeDiceBasic(WindowPatternCard windowPatternCard, DraftPool draftPool) {
        this.windowPatternCard = windowPatternCard;
        this.draftPool = draftPool;
    }

    public boolean takeDice(int from, int to) {
        if (to > 5 && to < 9 || to > 10 && to < 14)
            return false;
        if (windowPatternCard.isPlaceable(draftPool.getDice(from), to, false, false, true)) {
            active = true;
            return true;
        }
        return false;
    }

    @Override
    public void doAction(GameContext gameContext) {
        if (active) {
            windowPatternCard.placeDice(draftPool.getDice(from), to, false, false, true);
        }
    }


    @Override
    public void useAction(UI ui, GameContext gameContext) {

        final boolean[] result = new boolean[1]; // necessary to pass information between thread
        //UI must allow only valid values
        do {

            //Start a new Thread to get information from User Input and wait untill get it
            result[0] = true;
            Thread getUIinputThread = new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        from = ui.getDraftPoolIndex();
                        to = ui.getMatrixIndexTo();

                    } catch (EndOfTurnException e) {
                        e.printStackTrace();
                        result[0] = false;
                    }
                }
            });


            getUIinputThread.start();

            try {
                getUIinputThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
            if (result[0])
                return;

        } while (from == -1 || to == -1);

        //TODO send action to the server!!


    }

    @Override
    public String toPacket() {
        return null;
    }
}
