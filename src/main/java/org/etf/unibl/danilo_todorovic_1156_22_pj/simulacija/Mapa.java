package org.etf.unibl.danilo_todorovic_1156_22_pj.simulacija;

import java.util.HashMap;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.Pair;
import javafx.scene.control.Label;
import javafx.geometry.Pos;

/**
 * Singleton class that represents a map for vehicle placement and management.
 */
public class Mapa {
    private static final int VELICINA = 20; // The size of the map grid (20x20)
    private final HashMap<Pair<Integer, Integer>, Integer> tileVehicleCount; // Map to keep track of vehicle counts per grid tile

    private static Mapa instanca = null; // Singleton instance
    private GridPane gridPane; // GridPane for rendering the map

    /**
     * Private constructor to prevent instantiation from outside the class.
     */
    private Mapa() {
        this.tileVehicleCount = new HashMap<>();
        inicijalizujMapu();
    }

    /**
     * Initializes the map with zero vehicle counts on all tiles.
     */
    private void inicijalizujMapu() {
        for (int i = 0; i < VELICINA; i++) {
            for (int j = 0; j < VELICINA; j++) {
                tileVehicleCount.put(new Pair<>(i, j), 0);
            }
        }
    }

    /**
     * Returns the singleton instance of the Mapa class.
     *
     * @return The singleton instance of Mapa.
     */
    public static synchronized Mapa getInstance() {
        if (instanca == null) {
            instanca = new Mapa();
        }
        return instanca;
    }

    /**
     * Sets the GridPane for rendering the map.
     *
     * @param gridPane The GridPane to set.
     */
    public void setGridPane(GridPane gridPane) {
        this.gridPane = gridPane;
    }

    /**
     * Returns the GridPane used for rendering the map.
     *
     * @return The GridPane.
     */
    public GridPane getGridPane() {
        return gridPane;
    }

    /**
     * Updates the position on the map with a new vehicle label.
     *
     * @param x The x-coordinate of the position.
     * @param y The y-coordinate of the position.
     * @param vrsta The type of vehicle.
     * @param id The ID of the vehicle.
     * @param baterija The battery percentage of the vehicle.
     * @return The Label representing the vehicle, or {@code null} if the position is invalid.
     */
    public synchronized Label azurirajPoziciju(int x, int y, String vrsta, String id, int baterija) {
        if (x >= 0 && x < VELICINA && y >= 0 && y < VELICINA) {
            if (gridPane != null) {
                HBox pane = (HBox) getNodeByRowColumnIndex(x, y, gridPane);
                pane.setAlignment(Pos.CENTER);
                pane.setSpacing(1);
                pane.setPadding(new Insets(0));

                Pair<Integer, Integer> tile = new Pair<>(x, y);
                tileVehicleCount.put(tile, tileVehicleCount.get(tile) + 1);

                Label label = new Label(id + "\n" + baterija + "%");
                label.setStyle("-fx-font-size: 8px; -fx-text-fill: white; -fx-font-weight: 600;" + "-fx-background-color: " + getColorForChar(vrsta) + ";" +  "-fx-background-radius: 5;");
                label.setPadding(new Insets(3));
                Platform.runLater(() -> {
                    pane.getChildren().add(label);
                });
                return label;
            }
        }
        return null;
    }

    /**
     * Resets the position on the map by removing the specified label.
     *
     * @param x The x-coordinate of the position.
     * @param y The y-coordinate of the position.
     * @param label The label to remove.
     */
    public synchronized void resetujPoziciju(int x, int y, Label label) {
        if (x >= 0 && x < VELICINA && y >= 0 && y < VELICINA) {
            if (gridPane != null) {
                HBox pane = (HBox) getNodeByRowColumnIndex(x, y, gridPane);
                if (pane != null) {
                    Pair<Integer, Integer> tile = new Pair<>(x, y);
                    int count = tileVehicleCount.get(tile) - 1;
                    tileVehicleCount.put(tile, count);

                    if (count == 0) {
                        Platform.runLater(() -> {
                            pane.getChildren().clear();
                        });
                    } else {
                        Platform.runLater(() -> {
                            pane.getChildren().remove(label);
                        });
                    }
                }
            }
        }
    }

    /**
     * Resets the position on the map to its default state by clearing all labels.
     *
     * @param x The x-coordinate of the position.
     * @param y The y-coordinate of the position.
     */
    public synchronized void resetujPozicijuNaDefault(int x, int y) {
        if (x >= 0 && x < VELICINA && y >= 0 && y < VELICINA) {
            if (gridPane != null) {
                HBox pane = (HBox) getNodeByRowColumnIndex(x, y, gridPane);
                if (pane != null) {
                    Pair<Integer, Integer> tile = new Pair<>(x, y);
                    int count = tileVehicleCount.get(tile) - 1;
                    tileVehicleCount.put(tile, count);
                    Platform.runLater(() -> {
                        pane.getChildren().clear();
                    });
                }
            }
        }
    }

    /**
     * Retrieves the node at the specified row and column index in the GridPane.
     *
     * @param row The row index.
     * @param column The column index.
     * @param gridPane The GridPane to search in.
     * @return The HBox node at the specified location, or {@code null} if not found.
     */
    public HBox getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
        for (javafx.scene.Node node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) == row &&
                    GridPane.getColumnIndex(node) != null && GridPane.getColumnIndex(node) == column) {
                return (HBox) node;
            }
        }
        return null;
    }

    /**
     * Returns the size of the map grid.
     *
     * @return The size of the map grid.
     */
    public static int getVelicina() {
        return VELICINA;
    }

    /**
     * Returns the color associated with a vehicle type.
     *
     * @param vrsta The type of vehicle.
     * @return The color associated with the vehicle type.
     */
    private String getColorForChar(String vrsta) {
        switch (vrsta) {
            case "automobil":
                return "red";
            case "bicikl":
                return "green";
            case "trotinet":
                return "blue";
            default:
                return "white"; // Default color
        }
    }
}
