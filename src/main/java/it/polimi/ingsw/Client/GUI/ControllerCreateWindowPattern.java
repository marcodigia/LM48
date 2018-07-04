package it.polimi.ingsw.Client.GUI;

import it.polimi.ingsw.Server.Game.Cards.CardsUtility.DinamicCardCreator;
import it.polimi.ingsw.Server.Game.Utility.CONSTANT;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerCreateWindowPattern extends AbstractGUI implements Initializable {

    public Button createButton;
    public TextField cardName, cardDifficulty;
    public static DinamicCardCreator dinamicCardCreator;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void handleCreateButton(ActionEvent actionEvent) {
        dinamicCardCreator = new DinamicCardCreator(cardName.getText(), Integer.parseInt(cardDifficulty.getText()));
        switchScene(CONSTANT.Set);
    }
}
