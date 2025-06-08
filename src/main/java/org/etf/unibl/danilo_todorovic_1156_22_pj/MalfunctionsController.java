package org.etf.unibl.danilo_todorovic_1156_22_pj;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import org.etf.unibl.danilo_todorovic_1156_22_pj.util.Kvar;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Controller class for managing the malfunctions view.
 *
 * This class is responsible for displaying a list of malfunctions in a table view and periodically refreshing
 * the data to ensure it stays up-to-date. It uses JavaFX's {@link Timeline} to automatically update the
 * table at regular intervals.
 */
public class MalfunctionsController {

    @FXML
    private TableColumn<Kvar, String> kvaroviIdColumn;

    @FXML
    private TableColumn<Kvar, String> kvaroviOpisColumn;

    @FXML
    private TableColumn<Kvar, LocalDateTime> kvaroviVrijemeColumn;

    @FXML
    private TableColumn<Kvar, String> kvaroviVrstaColumn;

    @FXML
    private TableView<Kvar> kvaroviTableView;

    private ArrayList<Kvar> malfunctions;

    /**
     * Initializes the table columns and sets up periodic refresh for the malfunctions table.
     *
     * This method is called automatically after the FXML file has been loaded.
     * It sets cell value factories for each column and starts a timeline to refresh
     * the table view data at regular intervals.
     */
    public void initialize() {
        kvaroviIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        kvaroviOpisColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        kvaroviVrijemeColumn.setCellValueFactory(new PropertyValueFactory<>("dateTime"));

        kvaroviVrstaColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        // Populate the TableViews
        setupPeriodicRefresh();
    }

    /**
     * Sets the list of malfunctions to be displayed in the table.
     *
     * @param malfunctions The list of {@link Kvar} objects representing malfunctions.
     */
    public void setMalfunctions(ArrayList<Kvar> malfunctions) {
        this.malfunctions = malfunctions;
    }

    /**
     * Refreshes the table view to display the current list of malfunctions.
     *
     * This method clears the current items in the table view and adds
     * the malfunctions from the list to ensure the table is up-to-date.
     */
    private void refreshTables() {
        // Clear current items in each TableView
        kvaroviTableView.getItems().clear();

        for (Kvar m : malfunctions) {
            kvaroviTableView.getItems().add(m);
        }
    }

    /**
     * Sets up a periodic refresh of the malfunctions table using a timeline.
     *
     * This method creates a {@link Timeline} that triggers the {@link #refreshTables()} method
     * every second to refresh the table view. The timeline repeats indefinitely.
     */
    private void setupPeriodicRefresh() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            refreshTables();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE); // Repeat indefinitely
        timeline.play(); // Start the timeline
    }

}
