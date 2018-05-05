package it.polimi.ingsw.Client.GUI;

import it.polimi.ingsw.Server.Game.Cards.WindowPatternCard;
import it.polimi.ingsw.Server.Game.Components.Boards.DraftPool;
import it.polimi.ingsw.Server.Game.Components.Dice;
import it.polimi.ingsw.Server.Game.Components.DiceBag;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import static it.polimi.ingsw.Client.GUI.ControllerLogin.user;

public class ControllerGame implements Initializable {

    private int draftpoolindex = -1;
    private static Label draftToDisable;
    public WindowPatternCard windowPatternCard;
    public DraftPool draftPool;
    ArrayList<Label> draftPoolLabel = new ArrayList<>();
    ArrayList<String> pattern = new ArrayList<String>();
    public Label p4,
                 cellp400, cellp401, cellp402, cellp403, cellp404,
                 cellp410, cellp411, cellp412, cellp413, cellp414,
                 cellp420, cellp421, cellp422, cellp423, cellp424,
                 cellp430, cellp431, cellp432, cellp433, cellp434,
                 draft1, draft2, draft3, draft4, draft5, draft6, draft7, draft8, draft9;
    public MenuItem showpublic, showprivate, showtool, showcopyright;
    private ArrayList<Label> cells = new ArrayList<>();
    private String buffer;
    private boolean put = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Game Window Loaded");

        p4.setText(user + " (You)");

        cells.add(cellp400);
        cells.add(cellp401);
        cells.add(cellp402);
        cells.add(cellp403);
        cells.add(cellp404);
        cells.add(cellp410);
        cells.add(cellp411);
        cells.add(cellp412);
        cells.add(cellp413);
        cells.add(cellp414);
        cells.add(cellp420);
        cells.add(cellp421);
        cells.add(cellp422);
        cells.add(cellp423);
        cells.add(cellp424);
        cells.add(cellp430);
        cells.add(cellp431);
        cells.add(cellp432);
        cells.add(cellp433);
        cells.add(cellp434);

        draftPoolLabel.add(draft1);
        draftPoolLabel.add(draft2);
        draftPoolLabel.add(draft3);
        draftPoolLabel.add(draft4);
        draftPoolLabel.add(draft5);
        draftPoolLabel.add(draft6);
        draftPoolLabel.add(draft7);
        draftPoolLabel.add(draft8);
        draftPoolLabel.add(draft9);

        setUp();
        setUpDP();
        //updateDP();

        for (int i = 0; i < 9; i++) {
            draftPoolLabel.get(i).setGraphic(toImage(draftPool.getDraft().get(i)));
        }
    }

    @FXML
    public void handleClickDraft(MouseEvent mouseEvent) {
        Label eventDraft = (Label) mouseEvent.getSource();
        confirmDice(eventDraft);
        draftpoolindex = draftPoolLabel.indexOf(eventDraft);
        draftToDisable = eventDraft;
        put = false;
    }

    @FXML
    public void handleClickWP(MouseEvent mouseEvent) {
        Label event = (Label) mouseEvent.getSource();
        int indice_dado = cells.indexOf(event);
        if (windowPatternCard.getDice(indice_dado)== null && !put) {
            windowPatternCard.placeDice(draftPool.getDice(draftpoolindex), indice_dado, true,true,true);
            event.setText("");
            updateWP();
            draftToDisable.setDisable(true);
            put = true;
            //updateDP();
        } else {
            createAlertBox();
        }
    }

    private void confirmDice(Label l) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Dice");
        String header = l.getText();
        alert.setHeaderText(header);
        String content = "Do you want to place this dice?";
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void createAlertBox(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error!");
        String header = "Wrong action";
        alert.setHeaderText(header);
        String content = "You already placed this dice or in this cell has already been placed a dice! Please perform a correct move.";
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void createInfoBox(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Copyright ©");
        String header = "Software Engineering Project\nAll rights reserved";
        alert.setHeaderText(header);
        String content = "Sagrada\nby Marco Di Giacomantonio, Matthias Carretta and Fabio Dalle Rive\n:D";
        alert.setContentText(content);
        alert.showAndWait();
    }

    void setUp(){
        pattern.add("2");
        pattern.add("Via Lux");
        pattern.add("4");
        pattern.add("Y");   //0
        pattern.add("0");   //1
        pattern.add("6");   //2...
        pattern.add("0");
        pattern.add("0");
        pattern.add("0");
        pattern.add("1");
        pattern.add("5");
        pattern.add("0");
        pattern.add("2");
        pattern.add("3");
        pattern.add("Y");
        pattern.add("R");
        pattern.add("P");
        pattern.add("0");
        pattern.add("0");
        pattern.add("0");
        pattern.add("4");
        pattern.add("3");
        pattern.add("R");   //19
        windowPatternCard = new WindowPatternCard(pattern);
    }

    void setUpDP(){
        draftPool = new DraftPool(new DiceBag());
        draftPool.extractNdice(9);
    }

    private void updateWP(){
        for (int i=0; i<cells.size(); i++) {
            if (windowPatternCard.getDice(i)!=null)
                cells.get(i).setGraphic(toImage(windowPatternCard.getDice(i)));
                //cells.get(i).setText(windowPatternCard.getDice(i).getValue() + windowPatternCard.getDice(i).getDiceColor());
        }
    }

    /*private void updateDP(){
        for (int i=0; i<draftPoolLabel.size(); i++) {
            draftPoolLabel.get(i).setText(draftPool.getDice(i).getValue() + draftPool.getDice(i).getDiceColor());
        }
    }*/

    private ImageView toImage(Dice dice){
        Image image = new Image(dice.getDiceImage());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);
        return imageView;
    }

    public void handleShow(javafx.event.ActionEvent event) {
        if (event.getSource().equals(showtool))
            openWindowFromMenu("Tool Cards");
        if (event.getSource().equals(showpublic))
            openWindowFromMenu("Public Objective Cards");
        if (event.getSource().equals(showprivate))
            openWindowFromMenu("Private Objective Cards");
    }

    private void openWindowFromMenu(String string){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(string);
        window.setMinWidth(250);
        window.setMinHeight(100);
        Label label = new Label(string);
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label);
        layout.setAlignment(Pos.TOP_CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    public void handleCR(javafx.event.ActionEvent event){
        createInfoBox();
    }
}
