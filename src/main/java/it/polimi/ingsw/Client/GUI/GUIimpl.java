package it.polimi.ingsw.Client.GUI;

import it.polimi.ingsw.Client.AbstractClient.GeneriClient;
import it.polimi.ingsw.Server.Game.Cards.CardManager;
import it.polimi.ingsw.Server.Game.Cards.ToolCard;
import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import it.polimi.ingsw.Server.Game.Utility.CONSTANT;
import it.polimi.ingsw.UI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;

import static it.polimi.ingsw.Server.Game.Utility.CONSTANT.RMI_Socket;
import static it.polimi.ingsw.Server.Game.Utility.CONSTANT.title;

public class GUIimpl extends Application implements Runnable, UI {

    public static Stage stage;
    public static Parent root;
    public static String username;
    public static GeneriClient generiClient = null;
    public static String ip;
    public static String port;

    public GUIimpl(){
        try {
            CardManager.setWPCards(CONSTANT.windowPatternfile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * @param ip String which represents server ip
     */
    public static void setIp(String ip) {
        GUIimpl.ip = ip;
    }

    /**
     * @param port String which represents server port
     */
    public static void setPort(String port) {
        GUIimpl.port = port;
    }

    /**
     * @param generiClient GeneriClient which represents an RMI or Socket client
     */
    public void setGeneriClient(GeneriClient generiClient){
        this.generiClient = generiClient;
    }

    /**
     * @return GeneriClient getter
     */
    public GeneriClient getGeneriClient(){return generiClient;}

    /**
     * @param u String which represents username's name
     */
    public void setUsername(String u){
        username = u;
    }

    /**
     * @param primaryStage Stage used to start the GUI
     */
    public void start(Stage primaryStage) {
        stage = primaryStage;
        stage.setTitle(title);
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(RMI_Socket));
            root = loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(root, 300, 180);
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
        main(null);
    }

    /**
     * @param s String that represent a message sent from server to client
     */
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
    public void chooseWP(String wp1fronte, String wp2retro, String wp3fronte, String wp4retro) {

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

    @Override
    public void disable() {
        
    }

    @Override
    public void pingBack() {

    }

    @Override
    public void allCurrentPlayers(String players) {

    }

    @Override
    public ToolCard getChoosenToolCard() {
        return null;
    }
}