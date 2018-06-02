package it.polimi.ingsw.Client.GUI;

import it.polimi.ingsw.Client.AbstractClient.GeneriClient;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.annotation.Native;

import static it.polimi.ingsw.Server.Game.Utility.CONSTANT.RMI_Socket;
import static it.polimi.ingsw.Server.Game.Utility.CONSTANT.title;

public class GUI extends AbstractGUI implements Runnable{

    static Stage stage;
    static Parent root;
    public static String username;
    static GeneriClient generiClient = null;
    static String ip;
    static String port;

    public GUI(){

    }

    /**
     * @param ip String which represents server ip
     */
    public static void setIp(String ip) {
        GUI.ip = ip;
    }

    /**
     * @param port String which represents server port
     */
    public static void setPort(String port) {
        GUI.port = port;
    }

    /**
     * @param generiClient GeneriClient which represents an RMI or Socket client
     */
    public void setGeneriClient(GeneriClient generiClient){
        GUI.generiClient = generiClient;
    }

    /**
     * @return GeneriClient getter
     */
    public GeneriClient getGeneriClient(){
        return generiClient;
    }

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

    @Override
    public void run() {
        launch((String) null);
    }

}