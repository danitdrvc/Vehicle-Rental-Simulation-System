package org.etf.unibl.danilo_todorovic_1156_22_pj;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.etf.unibl.danilo_todorovic_1156_22_pj.simulacija.Mapa;
import org.etf.unibl.danilo_todorovic_1156_22_pj.simulacija.Simulacija;

/**
 * Controller class for the main application view.
 *
 * This class handles the initialization of the main GridPane and the actions for
 * displaying various views such as the list of vehicles, malfunctions, business results,
 * and deserialization results. It uses JavaFX's FXML framework to load different scenes
 * and manage stage transitions.
 *
 * The class also sets up periodic updates for certain views using JavaFX's
 * {@link Timeline} and {@link KeyFrame}.
 */
public class MainController {

    @FXML
    private GridPane gridPane; // Your GridPane from FXML

    /**
     * Initializes the GridPane with the Mapa instance.
     */
    public void initialize() {
        Mapa mapa = Mapa.getInstance();
        mapa.setGridPane(gridPane);
    }

    /**
     * Handles the action to show the list of vehicles.
     *
     * @param actionEvent The action event triggered by the button click.
     */
    @FXML
    public void handleShowVehicles(ActionEvent actionEvent) {
        try {
            // Load the FXML file for the new stage
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/etf/unibl/danilo_todorovic_1156_22_pj/Vehicle-view.fxml"));
            Parent root = loader.load();

            VehicleController controller = loader.getController();
            controller.setVehicleMap(Simulacija.getVozila());

            // Create a new stage
            Stage stage = new Stage();
            stage.setTitle("Vehicle List");

            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action to show the list of malfunctions.
     *
     * @param actionEvent The action event triggered by the button click.
     */
    @FXML
    public void handleShowMalfunctions(ActionEvent actionEvent) {
        try {
            // Load the FXML file for the new stage
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/etf/unibl/danilo_todorovic_1156_22_pj/Malfunctions-view.fxml"));
            Parent root = loader.load();

            MalfunctionsController controller = loader.getController();
            Simulacija simulacija = Simulacija.getInstance();
            controller.setMalfunctions(simulacija.getMalfunctions());

            // Create a new stage
            Stage stage = new Stage();
            stage.setTitle("Malfunction List");

            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action to show the business results.
     *
     * @param actionEvent The action event triggered by the button click.
     */
    @FXML
    public void handleShowBusinessResults(ActionEvent actionEvent) {
        try {
            // Load the FXML file for the new stage
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/etf/unibl/danilo_todorovic_1156_22_pj/RezultatiPoslovanja-view.fxml"));
            Parent root = loader.load();

            RezultatiController controller = loader.getController();
            Simulacija simulacija = Simulacija.getInstance();

            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
                controller.setResults(simulacija.getDnevniRezultati(), simulacija.getSumarniRezultat());
            }));
            timeline.setCycleCount(Timeline.INDEFINITE); // Repeat indefinitely
            timeline.play();

            // Create a new stage
            Stage stage = new Stage();
            stage.setTitle("Business Results");

            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action to show the deserialization results.
     *
     * @param actionEvent The action event triggered by the button click.
     */
    @FXML
    public void handleShowSerialization(ActionEvent actionEvent) {
        try {
            // Load the FXML file for the new stage
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/etf/unibl/danilo_todorovic_1156_22_pj/Deserialization-view.fxml"));
            Parent root = loader.load();

            DeserializationController controller = loader.getController();

            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
                controller.handleDeserialize();
            }));
            timeline.setCycleCount(Timeline.INDEFINITE); // Repeat indefinitely
            timeline.play(); // Start the timeline

            // Create a new stage
            Stage stage = new Stage();
            stage.setTitle("Deserialization");

            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
