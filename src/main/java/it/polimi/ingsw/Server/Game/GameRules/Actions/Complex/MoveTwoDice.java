package it.polimi.ingsw.Server.Game.GameRules.Actions.Complex;

import it.polimi.ingsw.Server.Game.Cards.WindowPatternCard;
import it.polimi.ingsw.Server.Game.Components.Dice;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Actions;
import it.polimi.ingsw.Server.Game.GameRules.GameContext;
import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import it.polimi.ingsw.Server.Game.GameRules.Player;
import it.polimi.ingsw.Server.Game.GameRules.Restrictions.RestrictionType;
import it.polimi.ingsw.Server.Game.Utility.ANSI_COLOR;
import it.polimi.ingsw.Server.Game.Utility.CONSTANT;
import it.polimi.ingsw.Server.Game.Utility.Logger;
import it.polimi.ingsw.UI;

public class MoveTwoDice implements Actions {


    private int from1, from2, to1, to2;

    private int roundIndex1, roundDiceIndex1 , roundIndex2, roundDiceIndex2;
    String userName;

    RestrictionType resType;

    public MoveTwoDice() {
    }

    public MoveTwoDice(RestrictionType resType) {
        this.resType = resType;
    }

    @Override
    public void doAction(GameStatus gameStatus) {
        Player activePlayer = gameStatus.getPlayerByName(userName) ;
        WindowPatternCard activePlayerWP = (WindowPatternCard)gameStatus.getPlayerCards().get(activePlayer).get(0);


        //TODO review this method. because first one dice is moved and than the other

        System.out.println(ANSI_COLOR.ANSI_YELLOW + from1 +to1+from2+to2 + ANSI_COLOR.ANSI_RESET);
        if (from1 == -1 || to1 == -1 )
            return;
        if (activePlayerWP.moveDice(from1, to1, false, false, false))
            if (from2 != -1 )
                if (!activePlayerWP.moveDice(from2, to2, false, false, false))
                    activePlayerWP.moveDice(to1, from1, true, true, true);



    }

    @Override
    public void useAction(UI ui, GameStatus gameStatus, String userName) {

        this.userName = userName;

        if (resType== RestrictionType.None){

            Logger.log("Primo Dado\n");
            Logger.log("Seleziona cella di partenza\n");
            from1 = ui.getMatrixIndexFrom();
            Logger.log("Seleziona cella di destinazione\n");
            to1 = ui.getMatrixIndexTo();

            Logger.log("Secondo Dado");
            Logger.log("Seleziona cella di partenza\n");
            from2 = ui.getMatrixIndexFrom();
            Logger.log("Seleziona cella di destinazione\n");
            to2 = ui.getMatrixIndexTo();

        }
        else if (resType == RestrictionType.Color){

            Logger.log("Seleziona round dal RoundTrack\n");
            roundIndex1= ui.getRoundIndex();
            System.out.println(" ROUNDINDEX " + roundIndex1);
            Logger.log("Seleziona cella dado del Round\n");

            roundDiceIndex1 = ui.getDiceIndexFromRound();
            System.out.println(" CELLAROUND " + roundDiceIndex1);

            Dice diceTrack = gameStatus.getBoardRound().getDices().get(roundIndex1).get(roundDiceIndex1);
            Logger.log("Seleziona cella di partenza\n");
            from1 = ui.getMatrixIndexFrom();
            System.out.println(" CELLAWP " + from1);
            Logger.log("Seleziona cella di destinazione\n");
            to1 = ui.getMatrixIndexTo();
            System.out.println(" CELLATO " + to1);

            Dice choosenDice = gameStatus.getPlayerWP(gameStatus.getPlayerByName(userName)).getDice(from1);
            if (choosenDice== null || diceTrack== null || !choosenDice.getDiceColor().equals(diceTrack.getDiceColor()))
            {
                Logger.log("Scelta non valida ");
                from1 = -1 ;
                to1 = -1 ;
                return;
            } else {


                System.out.println(" askforanotherdice ");
                boolean answer = ui.askForAnotherDice();
                if (!answer){
                    System.out.println(" ASKANOTHER ANSWER" + answer);
                    from2 = -1;
                    to2 = -1;
                    return;
                }



                Logger.log("Seleziona cella di partenza\n");
                from2 = ui.getMatrixIndexFrom();

                Logger.log("Seleziona cella di destinazione\n");
                to2 = ui.getMatrixIndexTo();


                choosenDice = gameStatus.getPlayerWP(gameStatus.getPlayerByName(userName)).getDice(from2);
                if (choosenDice== null || !choosenDice.getDiceColor().equals(diceTrack.getDiceColor())){
                    Logger.log("Scelta secondo dado non valida ");
                    from2 = -1 ;
                    to2 = -1 ;
                    return;
                }

            }







        }



    }

    @Override
    public void setACTIVE(boolean b) {

    }

    @Override
    public void setUpAction(String packet) {

        String[] elements = packet.split("\\"+CONSTANT.ElenemtsDelimenter);
        from1 = Integer.parseInt(elements[0]);
        to1 = Integer.parseInt(elements[1]);
        from2 = Integer.parseInt(elements[2]);
        to2 = Integer.parseInt(elements[3]);
    }

    @Override
    public void setUserName(String userName) {
        this.userName = userName;
    }


    private boolean exists2DiceValidMove(GameContext gameContext) {

        for (int i = 0; i < 20; i++) {
            if (gameContext.getWindowPatternCard().getDice(i) != null)
                for (int j = 0; j < 20; j++) {
                    if (j != i)
                        if (gameContext.getWindowPatternCard().getDice(j) != null) {
                            Dice dice1 = gameContext.getWindowPatternCard().removeDice(i);
                            Dice dice2 = gameContext.getWindowPatternCard().removeDice(j);
                            if (scanMatrix(dice1, gameContext.getWindowPatternCard(), false, false, false, i))
                                if (scanMatrix(dice2, gameContext.getWindowPatternCard(), false, false, false, j)) {
                                    gameContext.getWindowPatternCard().placeDice(dice1, i, true, true, true);
                                    gameContext.getWindowPatternCard().placeDice(dice2, j, true, true, true);
                                    return true;
                                }
                            gameContext.getWindowPatternCard().placeDice(dice1, i, true, true, true);
                            gameContext.getWindowPatternCard().placeDice(dice2, j, true, true, true);
                        }


                }

        }
        return false;
    }

    private boolean scanMatrix(Dice dice, WindowPatternCard windowPatternCard, boolean ignoreColor, boolean ignoreValue, boolean ignoreAdjacency, int currentPlace) {
        for (int i = 0; i < 20; i++) {
            if (i != currentPlace)
                if (windowPatternCard.isPlaceable(dice, i, ignoreColor, ignoreValue, ignoreAdjacency)) {
                    return true;
                }
        }
        return false;
    }

    @Override
    public String toPacket() {


        StringBuilder packet = new StringBuilder();
        packet.append(MoveTwoDice.class.getName()).append(CONSTANT.ObjectDelimeterComplex);
        packet.append(from1).append(CONSTANT.ElenemtsDelimenter).append(to1).append(CONSTANT.ElenemtsDelimenter);
        packet.append(from2).append(CONSTANT.ElenemtsDelimenter).append(to2);
        return packet.toString();
    }
}
