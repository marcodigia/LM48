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

public class ControllerLogin extends Controller implements Initializable {

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
            saveName();
            URL url = new File("src/main/java/it/polimi/ingsw/Client/GUI/FXMLs/rmi_socket.fxml").toURL();
            switchScene(playbutton, url);
        } else {
            createAlertBox("Error", "Your username should be at least 1 character long.", "Please enter a valid username.");
        }
    }

    @FXML
    private void handleEnter(ActionEvent enter) throws IOException {
        handleButtonPlay(enter);
    }

    private void saveName(){
        user = new String(usernametext.getText());
        System.out.println(user);
    }
}