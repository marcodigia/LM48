package it.polimi.ingsw.Client.GUI;

import it.polimi.ingsw.Client.AbstractClient.GeneriClient;
import it.polimi.ingsw.ClientServerCommonInterface.ClientServerSender;
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
import java.net.URL;
import java.rmi.ConnectException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

import static it.polimi.ingsw.Client.GUI.ControllerGame.gameStatus;
import static it.polimi.ingsw.Client.GUI.GUI.ip;
import static it.polimi.ingsw.Client.GUI.GUI.port;
import static it.polimi.ingsw.Client.GUI.GUI.username;
import static it.polimi.ingsw.Client.GUI.GUI.generiClient;
import static it.polimi.ingsw.Server.Game.Utility.CONSTANT.*;

public class ControllerLogin extends AbstractGUI implements Initializable{

    public Button loginButton;
    public TextField usernametext;
    public ImageView bg1;
    public AnchorPane anchorlogin;
    static ArrayList<String> playersName = new ArrayList<>();
    static ClientServerSender clientServerSender = null;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setBackground(bg1, anchorlogin);
        usernametext.addEventFilter(KeyEvent.KEY_TYPED, username_Validation(20));
        usernametext.setText("user"+(new Random()).nextInt(99999999));
    }

    /**
     * @param event user event (eg. user clicks a button)
     */
    @FXML
    private void handleLoginButton(ActionEvent event){

        if (!validateUsername())
            switchScene(Login);
        else {
            saveName();

            if (ControllerConnection.rmi) {
                generiClient = new GeneriClient();
                generiClient.setLinkClientServerRMI();
                generiClient.setClientServerReciverRMI();
                ControllerConnection.clientServerReciver = generiClient.getClientServerReciver();
                try {
                    ControllerConnection.clientServerReciver.setUI(this);
                } catch (RemoteException e) {
                    switchScene(RMI_Socket);
                    return;
                }
                try {
                    generiClient.register(username, ip, Integer.parseInt(port));
                } catch (ConnectException e){
                    switchScene(RMI_Socket);
                    return;
                }catch (RemoteException e) {
                    switchScene(RMI_Socket);
                }
            } else {
                generiClient = new GeneriClient();
                generiClient.setLinkClientServer(ip, Integer.parseInt(port));
                generiClient.setClientServerReciver();
                generiClient.setClientServerSender();
                ControllerConnection.clientServerReciver = generiClient.getClientServerReciver();
                try {
                    ControllerConnection.clientServerReciver.setUI(this);
                }catch (ConnectException e){
                    switchScene(RMI_Socket);
                    return;
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                catch (NullPointerException nullPointerException){
                    switchScene(RMI_Socket);
                    return;
                }
                generiClient.register(username);
            }
        }
    }

    private boolean validateUsername() {
        if (usernametext.getText().contains(".")){
            printMessage("'.' can't be included in your username!");
            return false;
        }
        if (!usernametext.getText().matches("[A-Za-z0-9]+")) {
            printMessage("non alphanumeric username!");
            return false;
        }
        return true;
    }

    /**
     * @param enter user event (eg. user clicks a button)
     */
    @FXML
    private void handleEnter(ActionEvent enter){
        handleLoginButton(enter);
    }

    /**
     * @param max_Length Integer that represents the maximum length of a string
     * @return EventHandler used to validate a string to max_Length and to only digits and letters
     */
    private EventHandler<KeyEvent> username_Validation(final Integer max_Length) {
        return e -> {
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
        };
    }

    private void saveName(){
        username = usernametext.getText();
    }

    /**
     * @param players String containing all players
     */
    @Override
    public void allCurrentPlayers(String players) {
        Platform.runLater(() -> {
            String[] names = players.split("\\s*,\\s*");
            playersName = new ArrayList<>();

            for (String name : names) {
                playersName.add(name);

            }
            switchScene(Lobby);
        });
    }

    /**
     * @param s String that represent a message sent from server to client
     */
    @Override
    public void printMessage(String s) {
        Platform.runLater(() -> {
            createInfoBox(s);
            switch (s) {
                case CONSTANT.usernameAlreadyUsed:
                    switchScene(Login);
                    break;
                case CONSTANT.correctUsername:
                    clientServerSender = generiClient.getClientServerSender();
                    break;
            }
        });
    }

    public void pingBack(){
        if(clientServerSender!=null){
            try {
                clientServerSender.pingBack(username);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void updateGameStatus(GameStatus gameStat){
        Platform.runLater(() -> {
            gameStatus = gameStat;
            switchScene(Board);
        });
    }

}