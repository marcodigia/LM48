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
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import java.io.IOException;
import java.util.Optional;

import static it.polimi.ingsw.Client.GUI.GUIimpl.generiClient;
import static it.polimi.ingsw.Client.GUI.GUIimpl.root;
import static it.polimi.ingsw.Client.GUI.GUIimpl.stage;

public abstract class GUI implements UI{

    /**
     * @param title string representing the title of the box
     * @param header string representing the header of the box
     * @param content string representing the content of the box
     */
    protected void createAlertBox(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * @param title string representing the title of the box
     * @param header string representing the header of the box
     * @param content string representing the content of the box
     * @return button bar containing OK / CANCEL type buttons
     */
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

    /**
     * @param title string representing the title of the box
     * @param header string representing the header of the box
     * @param content string representing the content of the box
     */
    protected void createInfoBox(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.initModality(Modality.APPLICATION_MODAL);


        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * @param title string representing the title of the box
     * @param header string representing the header of the box
     * @param content string representing the content of the box
     * @return button bar containing OK / CANCEL type buttons
     */
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

    /**
     * @param fxml String representing the fxml name that has to be charged
     */
    protected void switchScene(String fxml){
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(fxml));
            root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setOnCloseRequest(event -> { event.consume();
            ButtonBar.ButtonData closing = createWarningBox("Warning", "Closing window", "Are you sure?");
            if (closing.equals(ButtonBar.ButtonData.OK_DONE))
                if (generiClient != null)
                    generiClient.close();
                stage.close(); } );
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param background imageview of the background
     * @param pane layout pane whose background has to be set
     */
    protected void setBackground(ImageView background, Pane pane){
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

    }

    /**
     * @return
     */
    @Override
    public int getAmmountToChange() {
        return 0;
    }

    /**
     * @return
     */
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

    /**
     * @return
     */
    @Override
    public int getMatrixIndexFrom() {
        return 0;
    }

    /**
     * @return
     */
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
     * @return
     */
    @Override
    public int getRoundTrackIndex() {
        return 0;
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
    public void allCurrentPlayers(String players) {

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

    public void resetWPindex(){

    }

    /**
     * @return
     */
    public int getDiceClickedindexDraftpool(){
        return 0;
    }

    public void resetDraftPoolindex(){

    }
}
