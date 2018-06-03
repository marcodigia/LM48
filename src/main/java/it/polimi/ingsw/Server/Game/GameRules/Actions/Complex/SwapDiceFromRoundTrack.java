package it.polimi.ingsw.Server.Game.GameRules.Actions.Complex;

import it.polimi.ingsw.Server.Game.Components.Dice;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Actions;
import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import it.polimi.ingsw.Server.Game.Utility.CONSTANT;
import it.polimi.ingsw.UI;

public class SwapDiceFromRoundTrack implements Actions {


    private int roundIndex;
    private int diceIndex;

    private int draftPoolIndex;

    private  String userName;


    @Override
    public void doAction(GameStatus gameStatus) {

        Dice diceToSwap = gameStatus.getDraftPool().getDice(draftPoolIndex);

        Dice dice2toSwap = gameStatus.getBoardRound().getDices().get(roundIndex).get(diceIndex);

        gameStatus.getBoardRound().setDiceAtIndex(roundIndex,diceIndex,diceToSwap);

        gameStatus.getDraftPool().setDiceAtIndex(draftPoolIndex,dice2toSwap);
        //gameStatus.getDraftPool().

    }

    @Override
    public void useAction(UI ui, GameStatus gameStatus, String userName) {


        draftPoolIndex=ui.getDraftPoolIndex();
        roundIndex = ui.getRoundIndex();
        diceIndex = ui.getDiceIndexFromRound();

    }

    @Override
    public void setACTIVE(boolean b) {

    }

    @Override
    public void setUpAction(String packet) {

        String[] elements = packet.split("\\"+CONSTANT.ElenemtsDelimenter);

        draftPoolIndex = Integer.parseInt(elements[0]);
        roundIndex = Integer.parseInt(elements[1]);
        diceIndex = Integer.parseInt(elements[2]);

    }

    @Override
    public void setUserName(String userName) {

        this.userName = userName;
    }

    @Override
    public String toPacket() {

        StringBuilder packet = new StringBuilder();

        packet.append(SwapDiceFromRoundTrack.class.getName()).append(CONSTANT.ObjectDelimeterComplex);
        packet.append(draftPoolIndex).append(CONSTANT.ElenemtsDelimenter);
        packet.append(roundIndex).append(CONSTANT.ElenemtsDelimenter);
        packet.append(diceIndex);
        return packet.toString();
    }
}
