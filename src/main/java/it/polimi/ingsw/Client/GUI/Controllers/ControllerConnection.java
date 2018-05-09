package it.polimi.ingsw.Client.GUI.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static it.polimi.ingsw.Client.GUI.Main.stage;
import static it.polimi.ingsw.Client.GUI.Main.root;

public class ControllerConnection implements Initializable {

    public Button rmibutton, socketbutton;
    public AnchorPane anchorconnection;
    public ImageView bg2;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setBackground(bg2, anchorconnection);
    }

    @FXML
    private void handleButtonRMI(ActionEvent event) throws IOException {
        URL url = new File("src/main/java/it/polimi/ingsw/Client/GUI/FXMLs/lobby.fxml").toURL();
        switchScene(rmibutton, url);
    }

    @FXML
    private void handleButtonSocket(ActionEvent event) throws IOException {
        URL url = new File("src/main/java/it/polimi/ingsw/Client/GUI/FXMLs/lobby.fxml").toURL();
        switchScene(socketbutton, url);
    }

    void switchScene(Button button, URL url) throws IOException {
        //get reference to the button's stage
        stage = (Stage) button.getScene().getWindow();
        //load up OTHER FXML document
        root = FXMLLoader.load(url);
        //create a new scene with root and set the stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void setBackground(ImageView background, AnchorPane anchorPane){
        Image image = new Image(getClass().getClassLoader().getResourceAsStream("sfondo.png"));
        background.setImage(image);
        background.setOpacity(0.25);
        background.setPreserveRatio(false);
        background.setCache(true);
        background.setSmooth(true);
        background.fitWidthProperty().bind(anchorPane.widthProperty());
        background.fitHeightProperty().bind(anchorPane.heightProperty());
        background.toBack();
    }
}