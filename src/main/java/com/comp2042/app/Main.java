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

public class Main extends Application {

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
        primaryStage.show(); // Display the primary stage to user
    }


    public static void main(String[] args) {
        launch(args); // Start the JavaFX application
    }
}