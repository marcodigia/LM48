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

public class ControllerLogin implements Initializable {

    public Button playbutton;
    public TextField usernametext;
    public ImageView bg1;
    public AnchorPane anchorlogin;
    static String user;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setBackground(bg1, anchorlogin);
    }

    @FXML
    private void handleButtonPlay(ActionEvent event) throws IOException {
        if (usernametext.getLength() > 0) {
            goToConnection();
        } else {
            createAlertBox();
        }
    }

    @FXML
    private void onEnterLogin(ActionEvent enter) throws IOException {
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
        URL url = new File("src/main/java/it/polimi/ingsw/Client/GUI/FXMLs/rmi_socket.fxml").toURL();
        switchScene(playbutton, url);
    }

    private void createAlertBox(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error!");
        String header = "Your username should be at least 1 character long.";
        alert.setHeaderText(header);
        String content = "Please enter a valid username.";
        alert.setContentText(content);
        alert.showAndWait();
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