package it.polimi.ingsw.Client.GUI;

import it.polimi.ingsw.ClientServerCommonInterface.ClientServerReciver;
import it.polimi.ingsw.ClientServerCommonInterface.ClientServerSender;
import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import it.polimi.ingsw.UI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class GUIimpl extends Application implements Runnable, UI {

    public static Stage stage;
    public static Parent root;
    private static String username;
    public static ClientServerSender clientServerSender;
    public static ClientServerReciver clientServerReciver;

    public GUIimpl(){
        
    }

    public void setClientServerSender(ClientServerSender cs){
        clientServerSender = cs;
    }

    public void setClientServerReciver(ClientServerReciver cr){
        clientServerReciver = cr;
    }

    public void setUsername(String u){
        username = u;
    }

    public void start(Stage primaryStage) {
        stage = primaryStage;
        stage.setTitle("Sagrada");
        String fxml = "/Lobby.fxml";
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


    @Override
    public void printMessage(String s) {

    }

    @Override
    public int getAmmountToChange() {
        return 0;
    }

    @Override
    public int getDraftPoolIndex() {
        return 0;
    }

    @Override
    public int getMatrixIndexFrom() {
        return 0;
    }

    @Override
    public int getMatrixIndexTo() {
        return 0;
    }

    @Override
    public String chooseWP(String wp1fronte, String wp2retro, String wp3fronte, String wp4retro) {
        System.out.println("pd");
        return null;
    }

    @Override
    public int getRoundTrackIndex() {
        return 0;
    }

    @Override
    public void updateGameStatus(GameStatus gameStatus) {

    }

    @Override
    public void activate() {

    }
}