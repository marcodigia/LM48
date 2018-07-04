package it.polimi.ingsw.Client.GUI;

import it.polimi.ingsw.Server.Game.Components.Boards.BoardRound;
import it.polimi.ingsw.Server.Game.Utility.CONSTANT;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerSetWindowPattern extends AbstractGUI implements Initializable {

    public TextField restriction, index;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void handleAdd(ActionEvent actionEvent) {
        if (isCorrect(restriction.getText())){

        }
            else {
            createAlertBox("Wrong restriction (1, 2, 3, 4, 5, 6, R, B, G, Y, P)");
        }
        if (isCorrect(Integer.parseInt(index.getText()))){

        }
        else {
            createAlertBox("Wrong index, must be between 0 and 19");
        }
    }

    private boolean isCorrect(String s){
        return s.equals("1") || s.equals("2") || s.equals("3") || s.equals("4") || s.equals("5") || s.equals("6") ||
                s.equals("R") || s.equals("B") || s.equals("P") || s.equals("Y") || s.equals("G");
    }

    private boolean isCorrect(int index){
        return 0 <= index && index <= 19;
    }

    public void handleFinish(ActionEvent actionEvent) {
        switchScene(CONSTANT.Board);
    }
}
