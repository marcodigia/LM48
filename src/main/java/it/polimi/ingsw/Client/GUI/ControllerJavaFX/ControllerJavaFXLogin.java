package it.polimi.ingsw.Client.GUI.ControllerJavaFX;

import it.polimi.ingsw.Client.AbstractClient.GeneriClient;
import it.polimi.ingsw.ClientServerCommonInterface.ClientServerSender;
import it.polimi.ingsw.Server.Game.Cards.ToolCard;
import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import it.polimi.ingsw.Server.Game.Utility.CONSTANT;
import javafx.application.Platform;
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
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static it.polimi.ingsw.Client.GUI.GUIimpl.username;
import static it.polimi.ingsw.Client.GUI.GUIimpl.generiClient;
import static it.polimi.ingsw.Client.GUI.GUIimpl.ip;
import static it.polimi.ingsw.Client.GUI.GUIimpl.port;
import static it.polimi.ingsw.Client.GUI.ControllerJavaFX.ControllerJavaFXConnection.rmi;
import static it.polimi.ingsw.Client.GUI.ControllerJavaFX.ControllerJavaFXConnection.clientServerReciver;
import static it.polimi.ingsw.Server.Game.Utility.CONSTANT.Lobby;
import static it.polimi.ingsw.Server.Game.Utility.CONSTANT.Login;

public class ControllerJavaFXLogin extends GUI implements Initializable {

    public Button loginButton;
    public TextField usernametext;
    public ImageView bg1;
    public AnchorPane anchorlogin;
    public static ArrayList<String> playersName = new ArrayList<>();

    public static ClientServerSender clientServerSender ;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setBackground(bg1, anchorlogin);
        usernametext.addEventFilter(KeyEvent.KEY_TYPED, username_Validation(20));
    }

    @FXML
    private void handleLoginButton(ActionEvent event) throws IOException {
        saveName();
        if (rmi) {
            generiClient = new GeneriClient();
            generiClient.setLinkClientServerRMI();
            generiClient.setClientServerReciverRMI();
            clientServerReciver = generiClient.getClientServerReciver();
            clientServerReciver.setUI(this);
            generiClient.register(username, ip, Integer.parseInt(port));
        }
        else {
            generiClient = new GeneriClient();
            generiClient.setLinkClientServer(ip, Integer.parseInt(port));
            generiClient.setClientServerReciver();
            generiClient.setClientServerSender();
            clientServerReciver = generiClient.getClientServerReciver();
            clientServerReciver.setUI(this);
            generiClient.register(username);
        }
    }

    @FXML
    private void handleEnter(ActionEvent enter) throws IOException {
        handleLoginButton(enter);
    }

    // Username limited to max_Lenght AND to only Digits and Letters
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
                    if(textField.getText().contains(".") && e.getCharacter().matches("[.]")){
                        e.consume();
                    }else if(textField.getText().length() == 0 && e.getCharacter().matches("[.]")){
                        e.consume();
                    }
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

    @Override
    public void activate() {

    }

    @Override
    public void disable() {

    }

    @Override
    public void allCurrentPlayers(String players) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                System.out.println(players);
                String[] names = players.split("\\s*,\\s*");
                playersName = new ArrayList<String>();

                for (int i = 0 ; i < names.length ; i++){
                    System.out.println(names[i]);
                    playersName.add(new String(names[i]));
                }
                try {
                    switchScene(Lobby);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public ToolCard getChoosenToolCard() {
        return null;
    }

    @Override
    public void printMessage(String s) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                createInfoBox("", s, "");
                switch (s) {
                    case CONSTANT.usernameAlreadyUsed:
                        try {
                            switchScene(Login);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case CONSTANT.correctUsername:
                        clientServerSender = generiClient.getClientServerSender();
                        break;
                }
            }
        });
    }
}