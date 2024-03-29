package it.polimi.ingsw.Client.GUI;

import it.polimi.ingsw.ClientServerCommonInterface.ClientServerReciver;
import it.polimi.ingsw.Server.Game.Utility.CONSTANT;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.util.ResourceBundle;

import static it.polimi.ingsw.Server.Game.Utility.CONSTANT.Login;
import static it.polimi.ingsw.Client.GUI.GUI.ip;
import static it.polimi.ingsw.Client.GUI.GUI.port;

public class ControllerConnection extends AbstractGUI implements Initializable {

    public static boolean rmi = false;
    static ClientServerReciver clientServerReciver;

    public Button rmibutton, socketbutton;
    public AnchorPane anchorconnection;
    public ImageView bg2;
    public TextField serverIP, serverPort;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setBackground(bg2, anchorconnection);
        bg2.autosize();
        serverIP.setText(ip);
        serverPort.setText(port);
    }

    /**
     * @param event user event (eg. user clicks a button)
     */
    @FXML
    private void handleButtonRMI(ActionEvent event){
        try {

        if (serverIP.getText().matches("^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$")
                &&serverPort.getText().matches("[0-9]+")&&(Integer.parseInt(serverPort.getText())<65536)
                &&(Integer.parseInt(serverPort.getText())>0))
        {
            rmi = true;
            ip = serverIP.getText();
            port = serverPort.getText();
            switchScene(Login);
        }
        else
            switchScene(CONSTANT.RMI_Socket);
        }catch (NumberFormatException e) {
            switchScene(Login);

        }
    }

    /**
     * @param event user event (eg. user clicks a button)
     */
    @FXML
    private void handleButtonSocket(ActionEvent event){
        try {
            if (serverIP.getText().matches("^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$")
                    &&serverPort.getText().matches("[0-9]+")&&(Integer.parseInt(serverPort.getText())<65536)
                    &&(Integer.parseInt(serverPort.getText())>0))
            {
                ip = serverIP.getText();
                port = serverPort.getText();
                switchScene(Login);
            }
            else
                switchScene(CONSTANT.RMI_Socket);
        }catch (NumberFormatException e){
            switchScene(Login);
        }


    }
}