package it.polimi.ingsw.Client.GUI.ControllerJavaFX;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerJavaFXLogin extends ControllerJavaFX implements Initializable {

    public Button playbutton;
    public TextField usernametext;
    public ImageView bg1;
    public AnchorPane anchorlogin;
    static String user;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setBackground(bg1, anchorlogin);
        usernametext.addEventFilter(KeyEvent.KEY_TYPED, username_Validation(20));
    }

    @FXML
    private void handleButtonPlay(ActionEvent event) throws IOException {
        if (usernametext.getLength() > 0) {
            saveName();
            String fxml = "/RMI_Socket.fxml";
            switchScene(fxml);
        } else {
            createAlertBox("Error", "Your username should be at least 1 character long.", "Please enter a valid username.");
        }
    }

    @FXML
    private void handleEnter(ActionEvent enter) throws IOException {
        handleButtonPlay(enter);
    }

    /* Username limited to max_Lenght AND to only Digits and Letters */
    public EventHandler<KeyEvent> username_Validation(final Integer max_Length) {
        return new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                TextField textField = (TextField) e.getSource();
                if (textField.getText().length() >= max_Length) {
                    e.consume();
                }
                if(e.getCharacter().matches("[0-9.]")){
                    if(textField.getText().contains(".") && e.getCharacter().matches("[.]")){
                        e.consume();
                    }else if(textField.getText().length() == 0 && e.getCharacter().matches("[.]")){
                        e.consume();
                    }
                }
                else if (e.getCharacter().matches("[A-Za-z]")){
                }
                else{
                    e.consume();
                }
            }
        };
    }

    private void saveName(){
        user = new String(usernametext.getText());
        System.out.println(user);
    }
}