package it.polimi.ingsw.Client.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import static it.polimi.ingsw.Client.GUI.ControllerLogin.user;
import static it.polimi.ingsw.Client.GUI.Main.stage;
import static it.polimi.ingsw.Client.GUI.Main.root;

public class ControllerLobby implements Initializable{

    public Label player1, player2;
    public Button startbutton;
    public static String diceToPut = "Empty";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("loaded");
        player1.setText(user);
    }

    @FXML
    private void handleButtonStart(ActionEvent event) throws IOException {
        System.out.println("cool, start button");
        URL url = new File("src/main/java/it/polimi/ingsw/Client/GUI/board.fxml").toURL();
        switchScene(startbutton, url);
    }

    void switchScene(Button button, URL s) throws IOException {
        //get reference to the button's stage
        stage = (Stage) button.getScene().getWindow();
        //load up OTHER FXML document
        root = FXMLLoader.load(s);
        //create a new scene with root and set the stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}