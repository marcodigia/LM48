package it.polimi.ingsw.Server.Game.GameRules.Actions.Basic;

import com.sun.org.apache.xpath.internal.operations.Bool;
import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import it.polimi.ingsw.Server.Game.GameRules.Player;
import it.polimi.ingsw.Server.Game.Utility.CONSTANT;
import it.polimi.ingsw.UI;
import it.polimi.ingsw.Server.Game.Cards.WindowPatternCard;
import it.polimi.ingsw.Server.Game.Components.Dice;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Actions;

public class PlaceDiceAction implements Actions {

    private boolean ACTIVE = true;
    private final int MatrixSixe = 20;
    public Dice dice;
    private int matrixIndexTo;
    private boolean ignoreValue;
    private boolean ignoreColor;
    private boolean ignoreAdjacency;
    private boolean isFirstRound;

    private String userName;

    //This is called in TakeDiceBasicAction
    public PlaceDiceAction() {
        dice = null;
        ACTIVE = true;
    }

    //This is called in RerollDraftedDiceAction
    public PlaceDiceAction(Dice dice, boolean ignoreColor, boolean ignoreValue, boolean ignoreAdjacency) {
        this.ignoreAdjacency = ignoreAdjacency;
        this.ignoreColor = ignoreColor;
        this.ignoreValue = ignoreValue;
        this.dice = dice;
    }


    public void setUpAction(String packet){
        String[] elements =packet.split(    "\\"+CONSTANT.ElenemtsDelimenter);


        matrixIndexTo = Integer.parseInt(elements[0]);
        dice = new Dice(elements[1]);
        ignoreColor = Boolean.parseBoolean(elements[2]);
        ignoreValue = Boolean.parseBoolean(elements[3]);
        ignoreAdjacency = Boolean.parseBoolean(elements[4]);
    }

    @Override
    public void setUserName(String userName) {
        this.userName = userName;
    }



    //NB if the get to this method it means that the user has already confirm its action so if if is illegal the dice shuld be removed
    @Override
    public void doAction(GameStatus gameStatus) {

        Player activePlayer = gameStatus.getPlayerByName(userName) ;
        WindowPatternCard activePlayerWP = (WindowPatternCard)gameStatus.getPlayerCards().get(activePlayer).get(0);

        if (matrixIndexTo==-1)
            return;

        if (!ACTIVE)
            return;
        if ((activePlayerWP).getAllDices().size() == 0) {
            //Check if matrixIndexTo is not on border
            if ((matrixIndexTo > 5 && matrixIndexTo < 9) || (matrixIndexTo > 10 && matrixIndexTo < 14)) {
                return;
            }

            //Try to place the Dice without adjacency restriction

            if (activePlayerWP.isPlaceable(dice, matrixIndexTo, false, false, true)) {
                activePlayerWP.placeDice(dice, matrixIndexTo, false, false, true);
                gameStatus.getDraftPool().removeDice(dice);
                ACTIVE = false;
                return;
            }



        }

        //Try to place the Dice

        if (activePlayerWP.isPlaceable(dice, matrixIndexTo, ignoreColor, ignoreValue, ignoreAdjacency)) {
            activePlayerWP.placeDice(dice, matrixIndexTo, ignoreColor, ignoreValue, ignoreAdjacency);
            gameStatus.getDraftPool().removeDice(dice);
            ACTIVE = false;
        }


    }


    @Override
    public void useAction(UI ui, GameStatus gameStatus, String userName){

        this.userName = userName;
        Player activePlayer = gameStatus.getPlayerByName(userName) ;
        WindowPatternCard activePlayerWP = (WindowPatternCard)gameStatus.getPlayerCards().get(activePlayer).get(0);

        if (!ACTIVE)
            return;

        if (dice != null && existsValidMove(dice, activePlayerWP)){

            matrixIndexTo = ui.getMatrixIndexTo();

        } else if (dice == null){

            int diceIndex = ui.getDraftPoolIndex();
            dice = gameStatus.getDraftPool().getDice(diceIndex);
            System.out.println("qui pasa");
            matrixIndexTo = ui.getMatrixIndexTo();
            System.out.println("qui no pasa");

        }else {
            ui.printMessage("No possible moves , Putting dice back to Draft Pool ... ");
            matrixIndexTo = -1;
            return;
        }

    }




    private boolean existsValidMove(Dice dice, WindowPatternCard windowPatternCard) {

        for (int i = 0; i < MatrixSixe; i++) {
            if (windowPatternCard.isPlaceable(dice, i, false, false, false))
                return true;
        }
        return false;
    }

    public void setACTIVE(boolean b){
        ACTIVE = b;
    }

    public boolean actionState(){
        return ACTIVE ;
    }

    @Override
    public String toPacket() {

        StringBuilder packet = new StringBuilder();
        packet.append(PlaceDiceAction.class.getName());
        packet.append(CONSTANT.ObjectDelimeter).append(userName);
        packet.append(CONSTANT.ObjectDelimeter).append(ACTIVE);
        packet.append(CONSTANT.ObjectDelimeter).append(matrixIndexTo)
                .append(CONSTANT.ElenemtsDelimenter).append(dice).append(CONSTANT.ElenemtsDelimenter);
        packet.append(ignoreColor).append(CONSTANT.ElenemtsDelimenter);
        packet.append(ignoreValue).append(CONSTANT.ElenemtsDelimenter);
        packet.append(ignoreAdjacency);

        return packet.toString();
    }
}
