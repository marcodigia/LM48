package it.polimi.ingsw.Client.GUI;

import it.polimi.ingsw.ClientServerCommonInterface.ClientServerSender;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.File;
import java.net.URL;

public class GUI extends Application implements  Runnable{

    public static Stage stage;
    public static Parent root;
    public static ClientServerSender clientServerSender;

    public void start(Stage primaryStage) throws Exception {
        Parent root;
        URL url = new File("src/main/java/it/polimi/ingsw/Client/GUI/FXMLs/login.fxml").toURL();
        root = FXMLLoader.load(url);
        primaryStage.setTitle("Sagrada");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void run() {
        launch(null);
    }
}