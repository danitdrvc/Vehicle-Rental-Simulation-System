package org.etf.unibl.danilo_todorovic_1156_22_pj;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.etf.unibl.danilo_todorovic_1156_22_pj.simulacija.Simulacija;

import java.io.IOException;

/**
 * Main class for launching the JavaFX application.
 */
public class Main extends Application {

    /**
     * The entry point for the JavaFX application.
     *
     * @param stage The primary stage for this application.
     * @throws IOException If the FXML file cannot be loaded.
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Load the FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/etf/unibl/danilo_todorovic_1156_22_pj/Main-view.fxml"));
        Parent root = loader.load();

        // Set the scene and title for the stage
        stage.setTitle("ePJ2");
        stage.setScene(new Scene(root));
        stage.show();

        // Start the simulation in a new thread
        new Thread(() -> {
            Simulacija sim = Simulacija.getInstance();
            sim.pokretanjeNiti();
        }).start();
    }

    /**
     * The main method to launch the application.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        launch();
    }
}
