package it.polimi.ingsw.Server.Game.GameRules.Actions.Complex;

import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import it.polimi.ingsw.Server.Game.GameRules.Player;
import it.polimi.ingsw.Server.Game.Utility.CONSTANT;
import it.polimi.ingsw.UI;
import it.polimi.ingsw.Server.Game.Cards.WindowPatternCard;
import it.polimi.ingsw.Server.Game.Components.Dice;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Actions;

public class MoveOneDieIgnoringColor implements Actions {

    private int from, to;

    private String userName;
    @Override
    public void doAction(GameStatus gameStatus) {

        Player activePlayer = gameStatus.getPlayerByName(userName);
        WindowPatternCard activePlayerWP = (WindowPatternCard)gameStatus.getPlayerCards().get(activePlayer).get(0);


        System.out.println("Move one ignore Cor"+activePlayerWP.toString());
        activePlayerWP.moveDice(from, to, true, false, false);


    }


    @Override
    public void useAction(UI ui, GameStatus gameStatus, String userName) {

        this.userName = userName;
        Player activePlayer = gameStatus.getPlayerByName(userName) ;
        WindowPatternCard activePlayerWP = (WindowPatternCard)gameStatus.getPlayerCards().get(activePlayer).get(0);

        if (!existsValidMove(activePlayerWP, true)) {
            ui.printMessage("No possible moves");
        } else {

            from = ui.getMatrixIndexFrom();
            to = ui.getMatrixIndexTo();

        }


    }

    @Override
    public void setACTIVE(boolean b) {

    }

    @Override
    public void setUpAction(String packet) {
        String[] elements = packet.split("\\"+CONSTANT.ElenemtsDelimenter);
        from = Integer.parseInt(elements[0]);
        to = Integer.parseInt(elements[1]);
    }

    @Override
    public void setUserName(String userName) {

        this.userName = userName;
    }


    private boolean existsValidMove(WindowPatternCard windowPatternCard, boolean ignoreColor) {
        for (int i = 0; i < 20; i++) {
            Dice dice = windowPatternCard.removeDice(i);
            for (int j = 0; j < 20; j++) {
                if (j != i) {
                    // if found a suitable place for the dice according to the restricitons
                    if (windowPatternCard.isPlaceable(dice, j, ignoreColor, false, false)) {
                        //Put dice back
                        windowPatternCard.placeDice(dice, i, true, true, true);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String toPacket() {

        StringBuilder packet = new StringBuilder();
        packet.append(MoveOneDieIgnoringColor.class.getName()).append(CONSTANT.ObjectDelimeterComplex);
        packet.append(from).append(CONSTANT.ElenemtsDelimenter).append(to);
        return packet.toString();
    }
}