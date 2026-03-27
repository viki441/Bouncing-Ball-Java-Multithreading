package main.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main entry point for the Bouncing Balls application.
 * <p>
 * Loads the FXML layout, creates the primary stage, and shows the main window.
 * </p>
 * <p>
 * Window resizing is disabled to preserve playground layout consistency.
 * </p>
 *
 * @author YourName
 * @version 1.0
 */
public class mainApp extends Application {

    /**
     * Called when the JavaFX application starts.
     * Loads the FXML, sets the scene, and shows the stage.
     *
     * @param stage The primary stage for this application
     * @throws Exception if the FXML cannot be loaded
     */
    @Override
    public void start(Stage stage) throws Exception {
        // Load the FXML layout file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ball.fxml"));

        Scene scene = new Scene(loader.load());

        // Prevent window resizing to maintain fixed playground dimensions
        stage.setResizable(false);

        // Set window title
        stage.setTitle("Bouncing balls");

        // Apply the scene to the stage and show it
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Main method to launch the JavaFX application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}