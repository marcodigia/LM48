package it.polimi.ingsw.Client.GUI;

import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import it.polimi.ingsw.Server.Game.Utility.CONSTANT;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import static it.polimi.ingsw.Client.GUI.ControllerCreateWindowPattern.dinamicCardCreator;
import static it.polimi.ingsw.Client.GUI.ControllerConnection.clientServerReciver;
import static it.polimi.ingsw.Client.GUI.ControllerGame.attivo;
import static it.polimi.ingsw.Client.GUI.ControllerGame.gameStatus;
import static it.polimi.ingsw.Client.GUI.ControllerLogin.clientServerSender;
import static it.polimi.ingsw.Client.GUI.GUI.username;
import static it.polimi.ingsw.Server.Game.Utility.CONSTANT.Board;


public class ControllerSetWindowPattern extends AbstractGUI implements Initializable {

    public TextField restriction, index;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            clientServerReciver.setUI(this);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    public void handleAdd(ActionEvent actionEvent) {
        if (!isCorrect(restriction.getText()))
            createAlertBox("Wrong restriction (1, 2, 3, 4, 5, 6, R, B, G, Y, P)");
        try {
            if (!isCorrect(Integer.parseInt(index.getText())))
                createAlertBox("Wrong index, must be between 0 and 19");
            else
                //restrizione e indice giusti
                dinamicCardCreator.addRestriction(restriction.getText(), Integer.parseInt(index.getText()));

        }catch (NumberFormatException e){
            createAlertBox("Please enter a number as index");
        }
       }

    private boolean isCorrect(String s){
        return s.equals("1") || s.equals("2") || s.equals("3") || s.equals("4") || s.equals("5") || s.equals("6") ||
                s.equals("R") || s.equals("B") || s.equals("P") || s.equals("Y") || s.equals("G");
    }

    private boolean isCorrect(int index){
        return 0 <= index && index <= 19;
    }

    public void handleFinish(ActionEvent actionEvent) {
        try {
            clientServerSender.choosenWindowPattern(dinamicCardCreator.toPacket(), username);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateGameStatus(GameStatus gameStat) {
        Platform.runLater(() -> {
            gameStatus = gameStat;
            switchScene(Board);
        });
    }

    @Override
    public void activate() {
        Platform.runLater(() -> {
            attivo = true;
            switchScene(Board);
        });
    }

    public void pingBack(){
        try {
            ControllerLogin.clientServerSender.pingBack(username);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
