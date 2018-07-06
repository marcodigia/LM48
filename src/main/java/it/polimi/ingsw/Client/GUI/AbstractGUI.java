package it.polimi.ingsw.Client.GUI;

import it.polimi.ingsw.Server.Game.Cards.ToolCard;
import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import it.polimi.ingsw.UI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Optional;

import static java.lang.System.exit;

public abstract class AbstractGUI extends Application implements UI{

    public void start(Stage primaryStage) {

    }

    /**
     * @param header string representing the header of the box
     */
    void createAlertBox(String header){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.showAndWait();
    }

    /**
     * @param header string representing the header of the box
     * @return button bar containing OK / CANCEL type buttons
     */
    ButtonBar.ButtonData createWarningBox(String header){
        Alert alert = new Alert(Alert.AlertType.WARNING);

        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle("Warning");
        alert.setHeaderText(header);

        ButtonType buttonOk = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        ButtonType buttonCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonOk, buttonCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonOk) {
            return ButtonBar.ButtonData.OK_DONE;
        } else {
            return ButtonBar.ButtonData.CANCEL_CLOSE;
        }
    }

    /**
     * @param header string representing the header of the box
     */
    void createInfoBox(String header){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle("Info");
        alert.setHeaderText(header);
        alert.showAndWait();
    }

    /**
     * @param header string representing the header of the box
     * @return button bar containing OK / CANCEL type buttons
     */
    ButtonBar.ButtonData createConfirmationBox(String header){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle("Confirmation");
        alert.setHeaderText(header);

        ButtonType buttonTypeOk = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeOk, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOk) {
            return ButtonBar.ButtonData.OK_DONE;
        } else {
            return ButtonBar.ButtonData.CANCEL_CLOSE;
        }
    }

    /**
     * @param fxml String representing the fxml name that has to be charged
     */
    void switchScene(String fxml){
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(fxml));
            GUI.root = loader.load();
            Scene scene = new Scene(GUI.root);
            GUI.stage.setScene(scene);
            GUI.stage.setOnCloseRequest(event -> {
                event.consume();
                ButtonBar.ButtonData buttonData = createWarningBox("Closing window, are you sure?");
                if (buttonData.equals(ButtonBar.ButtonData.OK_DONE)){
                    if (GUI.generiClient != null)
                        GUI.generiClient.close();
                    
                    exit(0);

                }

            } );
            GUI.stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param background imageview of the background
     * @param pane layout pane whose background has to be set
     */
    void setBackground(ImageView background, Pane pane){
        Image image = new Image(getClass().getClassLoader().getResourceAsStream("sfondo.png"));
        background.setImage(image);
        background.setOpacity(0.25);
        background.setCache(true);
        background.setSmooth(true);
        background.setPreserveRatio(false);
        background.fitWidthProperty().bind(pane.widthProperty());
        background.fitHeightProperty().bind(pane.heightProperty());
        background.toBack();
    }

    /**
     * @param s String that represent a message sent from server to client
     */
    @Override
    public void printMessage(String s) {
        //Platform.runLater(() -> createInfoBox("", s, ""));
    }

    /**
     * @return
     * @param ammountType
     */
    @Override
    public int getAmmountToChange(int ammountType) {
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

    /**
     * @param wp1fronte
     * @param wp2retro
     * @param wp3fronte
     * @param wp4retro
     */
    @Override
    public void chooseWP(String wp1fronte, String wp2retro, String wp3fronte, String wp4retro) {

    }

    /**
     * @param gameStatus
     */
    @Override
    public void updateGameStatus(GameStatus gameStatus) {

    }

    @Override
    public void activate() {
    }

    @Override
    public void disable() {

    }

    /**
     * @param players
     */
    @Override
    public void allCurrentPlayers(String players){

    }

    /**
     * @return
     */
    @Override
    public ToolCard getChoosenToolCard() {
        return null;
    }

    /**
     * @return
     */
    public int getWPindexDice(){
        return 0;
    }

    /**
     * @return
     */
    public int getDiceClickedIndexDraftpool(){
        return 0;
    }

    public void pingBack(){
        System.out.println("PingAbstractGUI");
    }

    public int getRoundIndex(){
        return 0;
    }

    public int getDiceIndexFromRound(){
        return 0;
    }

    @Override
    public boolean askForAnotherDice() {
        return false;
    }

    @Override
    public void endGame(String winner) {

    }
}
