package org.etf.unibl.danilo_todorovic_1156_22_pj;

import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.etf.unibl.danilo_todorovic_1156_22_pj.transport.PrevoznoSredstvo;
import org.etf.unibl.danilo_todorovic_1156_22_pj.util.Utility;

import java.io.*;
import java.util.ArrayList;

/**
 * Controller class for handling deserialization and populating the TableView with vehicle data.
 */
public class DeserializationController {

    @FXML
    private TableColumn<PrevoznoSredstvo, Double> prevoznoSredstvoCijenaNabakeColumn;
    @FXML
    private TableColumn<PrevoznoSredstvo, Double> prevoznoSredstvoCijenaPopravkeColumn;
    @FXML
    private TableColumn<PrevoznoSredstvo, String> prevoznoSredstvoIdColumn;
    @FXML
    private TableColumn<PrevoznoSredstvo, String> prevoznoSredstvoModelColumn;
    @FXML
    private TableColumn<PrevoznoSredstvo, String> prevoznoSredstvoProizvodjacColumn;
    @FXML
    private TableView<PrevoznoSredstvo> prevoznoSredstvoTable;

    private final ArrayList<PrevoznoSredstvo> prevoznaSredstva = new ArrayList<>();
    private ObservableList<PrevoznoSredstvo> observableVehicleList;

    /**
     * Handles the deserialization of vehicle objects from files.
     */
    public void handleDeserialize() {
        observableVehicleList.clear();
        try {
            File[] files = new File(Utility.DATA_BASE_PATH + File.separator + "kvarovi").listFiles();
            if (files != null) {
                for (File file : files) {
                    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                        observableVehicleList.add((PrevoznoSredstvo) ois.readObject());
                    } catch (IOException | ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes the controller class. This method is automatically called after the FXML file is loaded.
     */
    public void initialize() {
        prevoznoSredstvoCijenaNabakeColumn.setCellValueFactory(new PropertyValueFactory<>("cijenaNabavke"));
        prevoznoSredstvoIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        prevoznoSredstvoModelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        prevoznoSredstvoProizvodjacColumn.setCellValueFactory(new PropertyValueFactory<>("proizvodjac"));
        prevoznoSredstvoCijenaPopravkeColumn.setCellValueFactory(new PropertyValueFactory<>("cijenaPopravke"));

        observableVehicleList = FXCollections.observableArrayList(prevoznaSredstva);
        prevoznoSredstvoTable.setItems(observableVehicleList);
    }
}
