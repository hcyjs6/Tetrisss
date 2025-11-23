package com.comp2042.gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;
import com.comp2042.app.GameController;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;

public class MenuController {

   @FXML
   private VBox menuPanel;

    @FXML
    private Pane controlPanel;


    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void startGame() throws Exception {
        // Load the game layout
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("gameLayout.fxml"));
        Parent root = fxmlLoader.load(); // Load the FXML file
        
        // Get the game controller
        GuiController c = fxmlLoader.getController();
        
        // Switch to game scene
        Scene gameScene = new Scene(root, 800, 580);
        stage.setScene(gameScene);
        
        // Initialize the game (GameController will handle game state)
        new GameController(c);
    }

    @FXML
    private void showControlKeys() {
        controlPanel.setVisible(true);
    }

    @FXML
    private void exitGame() {
        Platform.exit();
    }
}

