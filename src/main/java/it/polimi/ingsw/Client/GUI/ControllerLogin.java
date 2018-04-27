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
        System.out.println("loaded");
    }

    @FXML
    private void handleButtonPlay(ActionEvent event) throws IOException {
        if (event.getSource() == playbutton) {
            System.out.println("cool, play button");
        }
        if (usernametext.getLength() > 0) {
            saveName();
            URL url = new File("src/main/java/it/polimi/ingsw/Client/GUI/rmi_socket.fxml").toURL();
            switchScene(playbutton, url);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            String s = "Text should be at least 1 characters long. " + "Enter valid text and save.";
            alert.setContentText(s);
            alert.showAndWait();
        }
    }

    @FXML
    private void onEnterLogin(ActionEvent enter) throws IOException {
        System.out.println("cool, enter pressed");
        if(usernametext.getLength()>0) {
            saveName();
            URL url = new File("src/main/java/it/polimi/ingsw/Client/GUI/rmi_socket.fxml").toURL();
            switchScene(playbutton, url);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            String s = "Text should be at least 1 characters long. " + "Enter valid text and save.";
            alert.setContentText(s);
            alert.showAndWait();
        }
    }

    public void switchScene(Button button, URL s) throws IOException {
        //get reference to the button's stage
        stage = (Stage) button.getScene().getWindow();
        //load up OTHER FXML document
        root = FXMLLoader.load(s);
        //create a new scene with root and set the stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void saveName(){
        user = new String(usernametext.getText());
        System.out.println(user);
    }

}