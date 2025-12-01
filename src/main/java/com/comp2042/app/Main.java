package com.comp2042.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import com.comp2042.gui.MenuController;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Main class is the entry point for the Tetris application.
 * It initializes the JavaFX application window and loads the main menu.
 * 
 * @author Sek Joe Rin
 */
public class Main extends Application {

    /**
     * Starts the JavaFX application by loading the menu layout and initializing the primary stage.
     * This method sets up the application window with appropriate title, icon, and dimensions, then displays the main menu to the user.
     * 
     * @param primaryStage the primary stage for the JavaFX application
     * @throws Exception if there is an error loading the FXML file or initializing the stage
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        // Load the menu layout
        URL location = getClass().getClassLoader().getResource("menuLayout.fxml");
        ResourceBundle resources = null;

        FXMLLoader fxmlLoader = new FXMLLoader(location, resources);
        Parent root = fxmlLoader.load(); // Load the FXML file

        Image icon = new Image(getClass().getResourceAsStream("/tetrisIcon.png"));
        primaryStage.getIcons().add(icon); // Add the icon to the primary stage

        MenuController menuController = fxmlLoader.getController();
        menuController.setStage(primaryStage);

        primaryStage.setTitle("TetrisJFX");
        Scene scene = new Scene(root, 800, 580); // Create a new scene with the root node and the width and height
        primaryStage.setResizable(false);
        
        primaryStage.setScene(scene); // Set the scene to the primary stage
        primaryStage.sizeToScene(); // Automatically size the stage to fit the scene
        primaryStage.centerOnScreen(); // Center the window on the screen
        primaryStage.show(); // Display the primary stage to user
    }

    /**
     * The main entry point for the Tetris application.
     * This method launches the JavaFX application.
     * 
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        launch(args); // Start the JavaFX application
    }
}