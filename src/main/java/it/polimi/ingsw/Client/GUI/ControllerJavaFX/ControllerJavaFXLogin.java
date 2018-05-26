package it.polimi.ingsw.Client.GUI.ControllerJavaFX;

import it.polimi.ingsw.Client.AbstractClient.GeneriClient;
import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static it.polimi.ingsw.Client.GUI.GUIimpl.username;


public class ControllerJavaFXLogin extends GUI implements Initializable {

    public Button playbutton;
    public TextField usernametext;
    public ImageView bg1;
    public AnchorPane anchorlogin;
    private GeneriClient generiClient;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setBackground(bg1, anchorlogin);
        usernametext.addEventFilter(KeyEvent.KEY_TYPED, username_Validation(20));
    }

    @FXML
    private void handleButtonPlay(ActionEvent event) throws IOException {
        if (usernametext.getLength() > 0) {
            saveName();
            String fxml = "/Lobby.fxml";
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
        username = new String(usernametext.getText());
    }

    @Override
    public int getWPindexDice() {
        return 0;
    }

    @Override
    public void resetWPindex() {

    }

    @Override
    public int getDiceClickedindexDraftpool() {
        return 0;
    }

    @Override
    public void resetDraftPoolindex() {

    }

    @Override
    public void updateGameStatus(GameStatus gameStatus) {

    }
}