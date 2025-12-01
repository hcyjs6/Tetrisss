package com.comp2042.gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.event.ActionEvent;

import javafx.stage.Stage;
import com.comp2042.app.GameController;
import com.comp2042.audio.SoundEffect;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;

/**
 * Controls the main menu UI of the Tetris game.
 * This class handles menu navigation, level selection, and starting the game.
 * 
 * @author Sek Joe Rin
 */
public class MenuController {

    @FXML
    private Pane controlPanel;

    @FXML
    private Label levelLabel;

    private int selectedLevel = 1;

    private Stage stage;
    
    private final SoundEffect buttonClickSFX = new SoundEffect("Audio/clickButtonSFX.wav");
    private final SoundEffect crossButtonSFX = new SoundEffect("Audio/crossButtonSFX.wav");



    /**
     * Sets the primary stage for scene switching.
     * 
     * @param stage the primary stage of the application
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Starts a new game by loading the game layout and initializing the GameController.
     * 
     * @throws Exception if there is an error loading the FXML file or initializing the game
     */
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


    /**
     * Closes the control panel.
     * 
     * @param buttonEvent the action event from the close button
     */
    public void closeControlPanel(ActionEvent buttonEvent) {
        crossButtonSFX.playSFX();
        controlPanel.setVisible(false);
    }

    /**
     * Shows the control panel that displays keyboard controls and score values.
     * 
     * @param buttonEvent the action event from the show controls button
     */
    public void showControlKeys(ActionEvent buttonEvent) {
        buttonClickSFX.playSFX();
        controlPanel.setVisible(true);
    }

    /**
     * Exits the application.
     * 
     * @param buttonEvent the action event from the exit button
     */
    public void exitGame(ActionEvent buttonEvent) {
        buttonClickSFX.playSFX();
        Platform.exit();
    }

    /**
     * Decreases the selected starting level by one if it is greater than 1.
     * 
     * @param buttonEvent the action event from the decrease level button
     */
    public void decreaseLevel(ActionEvent buttonEvent) {
        buttonClickSFX.playSFX();
        if (selectedLevel > 1) {
            selectedLevel--;
            updateLevelLabel();
        }
    }

    /**
     * Increases the selected starting level by one if it is less than 100.
     * 
     * @param buttonEvent the action event from the increase level button
     */
    public void increaseLevel(ActionEvent buttonEvent) {
        buttonClickSFX.playSFX();
        if (selectedLevel < 100) {
            selectedLevel++;
            updateLevelLabel();
        }
    }

    /**
     * Updates the level label to display the currently selected level.
     */
    private void updateLevelLabel() {
        levelLabel.setText(String.valueOf(selectedLevel));
    }

    /**
     * This method returns the currently selected starting level.
     * 
     * @return the selected level value (1-100)
     */
    public int getSelectedLevel() {
        return selectedLevel;
    }

    
}

