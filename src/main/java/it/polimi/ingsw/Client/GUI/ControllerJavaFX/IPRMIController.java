package it.polimi.ingsw.Client.GUI.ControllerJavaFX;

import javafx.event.ActionEvent;

import java.awt.*;

public class IPRMIController {

    public static String rmiIP = new String("");

    public TextField RMIIP;
    
    public void hanldeClickButton(ActionEvent event) {
        rmiIP = RMIIP.getText();
    }

    public void handleEnter(ActionEvent enter) {
        hanldeClickButton(enter);
    }
}
