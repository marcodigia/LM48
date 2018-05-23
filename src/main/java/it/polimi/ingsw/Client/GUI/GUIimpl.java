package it.polimi.ingsw.Client.GUI;

import it.polimi.ingsw.ClientServerCommonInterface.ClientServerSender;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class GUIimpl extends Application implements Runnable{

    public static Stage stage;
    public static Parent root;
    private ClientServerSender clientServerSender;

    public GUIimpl(){

    }

    public void start(Stage primaryStage) {
        stage = primaryStage;
        stage.setTitle("Sagrada");
        String fxml = "/RMI_Socket.fxml";
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(fxml));
            root = loader.load();
            // Show the scene containing the root layout.
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void run() {
        launch(null);
    }
}