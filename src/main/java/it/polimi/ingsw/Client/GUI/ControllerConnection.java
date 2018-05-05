package it.polimi.ingsw.Client.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import static it.polimi.ingsw.Client.GUI.Main.stage;
import static it.polimi.ingsw.Client.GUI.Main.root;

public class ControllerConnection implements Initializable {

    public Button rmibutton, socketbutton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Connection Window Loaded");
    }

    @FXML
    private void handleButtonRMI(ActionEvent event) throws IOException {
        System.out.println("RMI button");
        URL url = new File("src/main/java/it/polimi/ingsw/Client/GUI/lobby.fxml").toURL();
        switchScene(rmibutton, url);
    }

    @FXML
    private void handleButtonSocket(ActionEvent event) throws IOException {
        System.out.println("Socket button");
        URL url = new File("src/main/java/it/polimi/ingsw/Client/GUI/lobby.fxml").toURL();
        switchScene(socketbutton, url);
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