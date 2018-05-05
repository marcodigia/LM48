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

public class ControllerLogin implements Initializable {

    public Button playbutton;
    public TextField usernametext;
    static String user;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Login Window Loaded");
    }

    @FXML
    private void handleButtonPlay(ActionEvent event) throws IOException {
        System.out.println("Play button");
        if (usernametext.getLength() > 0) {
            goToConnection();
        } else {
            createAlertBox();
        }
    }

    @FXML
    private void onEnterLogin(ActionEvent enter) throws IOException {
        System.out.println("Enter pressed");
        if(usernametext.getLength()>0) {
            goToConnection();
        }
        else {
            createAlertBox();
        }
    }

    private void switchScene(Button button, URL url) throws IOException {
        //get reference to the button's stage
        stage = (Stage) button.getScene().getWindow();
        //load up OTHER FXML document
        root = FXMLLoader.load(url);
        //create a new scene with root and set the stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void saveName(){
        user = new String(usernametext.getText());
        System.out.println(user);
    }

    private void goToConnection() throws IOException {
        saveName();
        URL url = new File("src/main/java/it/polimi/ingsw/Client/GUI/rmi_socket.fxml").toURL();
        switchScene(playbutton, url);
    }

    private void createAlertBox(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error!");
        String s = "Text should be at least 1 characters long. " + "Enter valid text.";
        alert.setContentText(s);
        alert.showAndWait();
    }

}