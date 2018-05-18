package it.polimi.ingsw.Client.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.File;
import java.net.URL;

public class GUI extends Application implements Runnable{

    public static Stage stage;
    public static Parent root;

    public void start(Stage primaryStage) throws Exception {
        Parent root;
        stage = primaryStage;
        URL url = new File("src/main/java/it/polimi/ingsw/Client/GUI/FXMLs/Login.fxml").toURL();
        root = FXMLLoader.load(url);
        stage.setTitle("Sagrada");
        /* stage.setOnCloseRequest(event -> {
            event.consume();
            Alert alert = new Alert(Alert.AlertType.WARNING);

            alert.setTitle("Warning");
            alert.setHeaderText("Closing Window");
            alert.setContentText("Are You Sure?");

            ButtonType buttonTypeOk = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(buttonTypeOk, buttonTypeCancel);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeOk) {
                alert.close();
                stage.close();
            } else {
                alert.close();
            }
        }); */
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void run() {
        launch(null);
    }
}