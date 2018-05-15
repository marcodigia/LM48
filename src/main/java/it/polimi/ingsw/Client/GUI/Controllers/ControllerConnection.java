package it.polimi.ingsw.Client.GUI.Controllers;

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

import static it.polimi.ingsw.Client.GUI.GUI.clientServerSender;

public class ControllerConnection extends Controller implements Initializable {

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
        URL url = new File("src/main/java/it/polimi/ingsw/Client/GUI/FXMLs/Lobby.fxml").toURL();
        switchScene(rmibutton, url);
    }

    @FXML
    private void handleButtonSocket(ActionEvent event) throws IOException {
        URL url = new File("src/main/java/it/polimi/ingsw/Client/GUI/FXMLs/Lobby.fxml").toURL();
        switchScene(socketbutton, url);
    }

}