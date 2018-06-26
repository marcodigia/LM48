package it.polimi.ingsw.Server.Game.GameRules.Actions.Complex;

import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import it.polimi.ingsw.Server.Game.GameRules.Player;
import it.polimi.ingsw.Server.Game.Utility.CONSTANT;
import it.polimi.ingsw.UI;
import it.polimi.ingsw.Server.Game.Cards.WindowPatternCard;
import it.polimi.ingsw.Server.Game.Components.Dice;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Actions;

public class MoveOneDice implements Actions {

    private int from, to;

    private boolean color_restriction;
    private boolean value_restriction;
    private boolean adjacency_restriction;

    private String userName;


    public MoveOneDice() {
        color_restriction = false;
        value_restriction =false;
        adjacency_restriction =false;
    }

    public MoveOneDice(boolean color_restriction, boolean value_restriction, boolean adjacency_restriction) {
        this.color_restriction = color_restriction;
        this.value_restriction = value_restriction;
        this.adjacency_restriction = adjacency_restriction;
    }

    @Override
    public void doAction(GameStatus gameStatus) {



        if (from == -1 || to == -1)
            return;

        Player activePlayer = gameStatus.getPlayerByName(userName);

        WindowPatternCard activePlayerWP = (WindowPatternCard)gameStatus
                .getPlayerCards()
                .get(activePlayer)
                .get(0);


        activePlayerWP.moveDice(from, to, color_restriction, value_restriction, adjacency_restriction);


    }


    @Override
    public void useAction(UI ui, GameStatus gameStatus, String userName) {

        this.userName = userName;
        Player activePlayer = gameStatus.getPlayerByName(userName) ;
        WindowPatternCard activePlayerWP = (WindowPatternCard)gameStatus.getPlayerCards().get(activePlayer).get(0);

        if (!existsValidMove(activePlayerWP)) {
            ui.printMessage("No possible moves");
            System.out.println("No possible moves");
            from = -1;
            to = -1 ;
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
        color_restriction = Boolean.parseBoolean(elements[2]);
        value_restriction = Boolean.parseBoolean(elements[3]);
        adjacency_restriction = Boolean.parseBoolean(elements[4]);
    }

    @Override
    public void setUserName(String userName) {

        this.userName = userName;
    }


    private boolean existsValidMove(WindowPatternCard windowPatternCard) {
        for (int i = 0; i < 20; i++) {
            if (windowPatternCard.getDice(i)!=null) {
                Dice dice = windowPatternCard.removeDice(i);
                for (int j = 0; j < 20; j++) {
                    if (j != i) {
                        // if found a suitable place for the dice according to the restricitons
                        if (windowPatternCard.isPlaceable(dice, j, color_restriction, value_restriction, adjacency_restriction)) {
                            //Put dice back
                            windowPatternCard.placeDice(dice, i, color_restriction, value_restriction, adjacency_restriction);
                            return true;
                        }
                    }
                }
                windowPatternCard.placeDice(dice,i,true,true,true);

            }
        }
        return false;
    }

    @Override
    public String toPacket() {

        StringBuilder packet = new StringBuilder();
        packet.append(MoveOneDice.class.getName()).append(CONSTANT.ObjectDelimeterComplex);
        packet.append(from).append(CONSTANT.ElenemtsDelimenter).append(to);
        packet.append(CONSTANT.ElenemtsDelimenter).append(color_restriction)
                .append(CONSTANT.ElenemtsDelimenter).append(value_restriction)
                .append(CONSTANT.ElenemtsDelimenter).append(adjacency_restriction);
        return packet.toString();
    }
}