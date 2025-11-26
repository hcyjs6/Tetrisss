package com.comp2042.gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;
import com.comp2042.app.GameController;
import com.comp2042.audio.SoundEffect;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

public class MenuController {

   @FXML
   private VBox menuPanel;

    @FXML
    private Pane controlPanel;

    @FXML
    private Label levelLabel;

    @FXML
    private Button decreaseLevelButton;

    @FXML
    private Button increaseLevelButton;

    private int selectedLevel = 1;

    private Stage stage;
    
    private final SoundEffect buttonClickSFX = new SoundEffect("Audio/clickButtonSFX.wav");
    private final SoundEffect crossButtonSFX = new SoundEffect("Audio/crossButtonSFX.wav");



    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void startGame() throws Exception {
        buttonClickSFX.playSFX();
        // Load the game layout
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("gameLayout.fxml"));
        Parent root = fxmlLoader.load(); // Load the FXML file
        
        // Get the game controller
        GuiController c = fxmlLoader.getController();
        
        // Set the stage reference for scene switching
        c.setStage(stage);
        
        
        // Switch to game scene
        Scene gameScene = new Scene(root, 800, 580);
        stage.setScene(gameScene);
        
        // Initialize the game with the selected level from the menu
        new GameController(c, selectedLevel);
    }

    @FXML
    private void closeControlPanel() {
        crossButtonSFX.playSFX();
        controlPanel.setVisible(false);
    }

    @FXML
    private void showControlKeys() {
        buttonClickSFX.playSFX();
        controlPanel.setVisible(true);
    }

    @FXML
    private void exitGame() {
        buttonClickSFX.playSFX();
        Platform.exit();
    }

    @FXML
    private void decreaseLevel() {
        buttonClickSFX.playSFX();
        if (selectedLevel > 1) {
            selectedLevel--;
            updateLevelLabel();
        }
    }

    @FXML
    private void increaseLevel() {
        buttonClickSFX.playSFX();
        if (selectedLevel < 100) {
            selectedLevel++;
            updateLevelLabel();
        }
    }

    private void updateLevelLabel() {
      
        levelLabel.setText(String.valueOf(selectedLevel));
        
    }

    public int getSelectedLevel() {
        return selectedLevel;
    }

    
}

