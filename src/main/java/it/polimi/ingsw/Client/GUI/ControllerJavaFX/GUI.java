package it.polimi.ingsw.Client.GUI.ControllerJavaFX;

import it.polimi.ingsw.Server.Game.Cards.ToolCard;
import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import it.polimi.ingsw.UI;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import java.io.IOException;
import java.util.Optional;

import static it.polimi.ingsw.Client.GUI.GUIimpl.root;
import static it.polimi.ingsw.Client.GUI.GUIimpl.stage;

public abstract class GUI implements UI{

    protected void createAlertBox(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public ButtonBar.ButtonData createWarningBox(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.WARNING);

        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        ButtonType buttonTypeOk = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeOk, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOk) {
            alert.close();
            return ButtonBar.ButtonData.OK_DONE;
        } else {
            alert.close();
            return ButtonBar.ButtonData.CANCEL_CLOSE;
        }
    }

    protected void createInfoBox(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.initModality(Modality.APPLICATION_MODAL);


        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    protected ButtonBar.ButtonData createConfirmationBox(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.initModality(Modality.APPLICATION_MODAL);


        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        ButtonType buttonTypeOk = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeOk, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOk) {
            alert.close();
            return ButtonBar.ButtonData.OK_DONE;
        } else {
            alert.close();
            return ButtonBar.ButtonData.CANCEL_CLOSE;
        }
    }

    protected void switchScene(String fxml) throws IOException {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(fxml));
            root = loader.load();
            //clientServerReciver.setUI(this);
            // Show the scene containing the root layout.
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setOnCloseRequest(event -> { event.consume();
            ButtonBar.ButtonData closing = createWarningBox("Warning", "Closing window", "Are you sure?");
            if (closing.equals(ButtonBar.ButtonData.OK_DONE))
                stage.close(); } );
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void setBackground(ImageView background, AnchorPane anchorPane){
        Image image = new Image(getClass().getClassLoader().getResourceAsStream("sfondo.png"));
        background.setImage(image);
        background.setOpacity(0.25);
        background.setCache(true);
        background.setSmooth(true);
        background.setPreserveRatio(false);
        background.fitWidthProperty().bind(anchorPane.widthProperty());
        background.fitHeightProperty().bind(anchorPane.heightProperty());
        background.toBack();
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
        while (getDiceClickedindexDraftpool() == -1) {
                System.out.println("waiting DP...");
        }
        int toReturn = getDiceClickedindexDraftpool();
        System.out.println(toReturn);
        //resetDraftPoolindex();
        return toReturn;
    }

    @Override
    public int getMatrixIndexFrom() {
        return 0;
    }

    @Override
    public int getMatrixIndexTo() {
        while (getWPindexDice() == -1) {
                System.out.println("waiting WP...");
        }
        int toReturn = getWPindexDice();
        System.out.println(toReturn);
        //resetWPindex();
        return toReturn;
    }

    @Override
    public String chooseWP(String wp1fronte, String wp2retro, String wp3fronte, String wp4retro) {
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

    @Override
    public void disable() {

    }

    @Override
    public void allCurrentPlayers(String players) {

    }

    @Override
    public ToolCard getChoosenToolCard() {
        return null;
    }

    public int getWPindexDice(){
        return 0;
    }

    public void resetWPindex(){

    }

    public int getDiceClickedindexDraftpool(){
        return 0;
    }

    public void resetDraftPoolindex(){

    }
}
