package it.polimi.ingsw.Client.GUI.ControllerJavaFX;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerJavaFXConnection extends ControllerJavaFX implements Initializable {

    public Button rmibutton, socketbutton;
    public AnchorPane anchorconnection;
    public ImageView bg2;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setBackground(bg2, anchorconnection);
        bg2.autosize();
    }

    @FXML
    private void handleButtonRMI(ActionEvent event) throws IOException {
        String fxml = "/Lobby.fxml";
        switchScene(fxml);
    }

    @FXML
    private void handleButtonSocket(ActionEvent event) throws IOException {
        String fxml = "/Lobby.fxml";
        switchScene(fxml);
    }

}