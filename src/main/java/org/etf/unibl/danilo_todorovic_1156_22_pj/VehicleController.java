package org.etf.unibl.danilo_todorovic_1156_22_pj;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import org.etf.unibl.danilo_todorovic_1156_22_pj.transport.*;

import java.time.LocalDate;
import java.util.HashMap;

/**
 * Controller class for managing the display and periodic refresh of vehicle data in JavaFX TableViews.
 * It handles the initialization and updating of TableViews for various types of vehicles.
 */
public class VehicleController {

    @FXML
    private TableColumn<Automobil, Double> automobilCijenaNabavkeColumn;

    @FXML
    private TableColumn<Automobil, LocalDate> automobilDatumNabavkeColumn;

    @FXML
    private TableColumn<Automobil, String> automobilIdColumn;

    @FXML
    private TableColumn<Automobil, Integer> automobilKapacitetOsobaColumn;

    @FXML
    private TableColumn<Automobil, String> automobilMmodelColumn;

    @FXML
    private TableColumn<Automobil, String> automobilOpisColumn;

    @FXML
    private TableColumn<Automobil, String> automobilProizvodjacColumn;

    @FXML
    private TableColumn<Automobil, Integer> automobilTrenutniNivoBaterijeColumn;

    @FXML
    private TableColumn<ElektricniBicikl, Double> biciklAutonomijaColumn;

    @FXML
    private TableColumn<ElektricniBicikl, Double> biciklCijenaNabavkeColumn;

    @FXML
    private TableColumn<ElektricniBicikl, String> biciklIidColumn;

    @FXML
    private TableColumn<ElektricniBicikl, String> biciklModelColumn;

    @FXML
    private TableColumn<ElektricniBicikl, String> biciklProizvodjacColumn;

    @FXML
    private TableColumn<ElektricniBicikl, Integer> biciklTrenutniNivoBaterijeColumn;

    @FXML
    private TableColumn<ElektricniTrotinet, Double> trotinetCijenaNabavkeColumn;

    @FXML
    private TableColumn<ElektricniTrotinet, String> trotinetIdColumn;

    @FXML
    private TableColumn<ElektricniTrotinet, Double> trotinetMaksimalnaBrzinaColumn;

    @FXML
    private TableColumn<ElektricniTrotinet, String> trotinetModelColumn;

    @FXML
    private TableColumn<ElektricniTrotinet, String> trotinetProizvodjacColumn;

    @FXML
    private TableColumn<ElektricniTrotinet, Double> trotinetTrenutniNivoBaterijeColumn;

    @FXML
    private TableView<Automobil> automobilTableView;
    @FXML
    private TableView<ElektricniBicikl> elektricniBiciklTableView;
    @FXML
    private TableView<ElektricniTrotinet> elektricniTrotinetTableView;

    private HashMap<String, PrevoznoSredstvo> vehicleMap;

    /**
     * Initializes the TableViews and their columns, sets up the periodic refresh.
     * This method is automatically called after the FXML file is loaded.
     */
    public void initialize() {
        // Initialize columns for Automobil TableView
        automobilIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        automobilProizvodjacColumn.setCellValueFactory(new PropertyValueFactory<>("proizvodjac"));
        automobilMmodelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        automobilCijenaNabavkeColumn.setCellValueFactory(new PropertyValueFactory<>("cijenaNabavke"));
        automobilTrenutniNivoBaterijeColumn.setCellValueFactory(new PropertyValueFactory<>("trenutniNivoBaterije"));
        automobilDatumNabavkeColumn.setCellValueFactory(new PropertyValueFactory<>("datumNabavke"));
        automobilOpisColumn.setCellValueFactory(new PropertyValueFactory<>("opis"));
        automobilKapacitetOsobaColumn.setCellValueFactory(new PropertyValueFactory<>("kapacitetOsoba"));

        // Initialize columns for ElektricniBicikl TableView
        biciklIidColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        biciklProizvodjacColumn.setCellValueFactory(new PropertyValueFactory<>("proizvodjac"));
        biciklModelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        biciklCijenaNabavkeColumn.setCellValueFactory(new PropertyValueFactory<>("cijenaNabavke"));
        biciklTrenutniNivoBaterijeColumn.setCellValueFactory(new PropertyValueFactory<>("trenutniNivoBaterije"));
        biciklAutonomijaColumn.setCellValueFactory(new PropertyValueFactory<>("autonomija"));

        // Initialize columns for ElektricniTrotinet TableView
        trotinetIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        trotinetProizvodjacColumn.setCellValueFactory(new PropertyValueFactory<>("proizvodjac"));
        trotinetModelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        trotinetCijenaNabavkeColumn.setCellValueFactory(new PropertyValueFactory<>("cijenaNabavke"));
        trotinetTrenutniNivoBaterijeColumn.setCellValueFactory(new PropertyValueFactory<>("trenutniNivoBaterije"));
        trotinetMaksimalnaBrzinaColumn.setCellValueFactory(new PropertyValueFactory<>("maksimalnaBrzina"));

        // Populate the TableViews
        setupPeriodicRefresh();
    }

    /**
     * Sets the vehicle map that will be used to populate the TableViews.
     *
     * @param vehicleMap A HashMap containing vehicles with their identifiers as keys.
     */
    public void setVehicleMap(HashMap<String, PrevoznoSredstvo> vehicleMap) {
        this.vehicleMap = vehicleMap;
    }

    /**
     * Refreshes the data in the TableViews by clearing them and re-adding items
     * based on the current state of the vehicle map.
     */
    private void refreshTables() {
        // Clear current items in each TableView
        automobilTableView.getItems().clear();
        elektricniBiciklTableView.getItems().clear();
        elektricniTrotinetTableView.getItems().clear();

        // Iterate over the HashMap and update each TableView
        for (PrevoznoSredstvo vehicle : vehicleMap.values()) {
            if (vehicle instanceof Automobil) {
                automobilTableView.getItems().add((Automobil) vehicle);
            } else if (vehicle instanceof ElektricniBicikl) {
                elektricniBiciklTableView.getItems().add((ElektricniBicikl) vehicle);
            } else if (vehicle instanceof ElektricniTrotinet) {
                elektricniTrotinetTableView.getItems().add((ElektricniTrotinet) vehicle);
            }
        }
    }

    /**
     * Sets up a periodic refresh using a JavaFX Timeline to refresh the TableViews
     * every second.
     */
    private void setupPeriodicRefresh() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            refreshTables();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE); // Repeat indefinitely
        timeline.play(); // Start the timeline
    }
}
