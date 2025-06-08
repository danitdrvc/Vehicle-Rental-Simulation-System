package org.etf.unibl.danilo_todorovic_1156_22_pj;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.etf.unibl.danilo_todorovic_1156_22_pj.util.DnevniRezultatPoslovanja;
import org.etf.unibl.danilo_todorovic_1156_22_pj.util.SumarniRezultatPoslovanja;

import java.time.LocalDate;
import java.util.List;

/**
 * Controller class for managing the display of daily and summary business results.
 * <p>
 * This class is responsible for initializing and populating the TableView with daily
 * business results and for updating labels that show summary information about
 * the company's total costs, taxes, and other financial metrics.
 * </p>
 * <p>
 * It provides methods to set the data to be displayed (`setResults`) and to
 * update the summary labels (`updateSummaryLabels`), ensuring that the
 * user interface reflects the most recent business performance data.
 * </p>
 */
public class RezultatiController {
    @FXML
    private TableView<DnevniRezultatPoslovanja> dnevniRezultatiTableView;

    @FXML
    private TableColumn<DnevniRezultatPoslovanja, LocalDate> datumColumn;
    @FXML
    private TableColumn<DnevniRezultatPoslovanja, Double> ukupnaCijenaColumn;
    @FXML
    private TableColumn<DnevniRezultatPoslovanja, Double> cijenaPopustaColumn;
    @FXML
    private TableColumn<DnevniRezultatPoslovanja, Double> cijenaPromocijeColumn;
    @FXML
    private TableColumn<DnevniRezultatPoslovanja, Double> cijenaUdaljenostiColumn;
    @FXML
    private TableColumn<DnevniRezultatPoslovanja, Double> cijenaOdrzavanjaColumn;
    @FXML
    private TableColumn<DnevniRezultatPoslovanja, Double> cijenaPopravkiColumn;

    @FXML
    private Label totalPriceLabel;
    @FXML
    private Label discountPriceLabel;
    @FXML
    private Label promotionPriceLabel;
    @FXML
    private Label distancePriceLabel;
    @FXML
    private Label maintenancePriceLabel;
    @FXML
    private Label repairPriceLabel;
    @FXML
    private Label totalCompanyCostLabel;
    @FXML
    private Label totalTaxLabel;

    private SumarniRezultatPoslovanja sumarniRezultat;

    /**
     * Initializes the columns in the TableView.
     */
    public void initialize() {
        datumColumn.setCellValueFactory(new PropertyValueFactory<>("datum"));
        ukupnaCijenaColumn.setCellValueFactory(new PropertyValueFactory<>("ukupnaCijena"));
        cijenaPopustaColumn.setCellValueFactory(new PropertyValueFactory<>("cijenaPopusta"));
        cijenaPromocijeColumn.setCellValueFactory(new PropertyValueFactory<>("cijenaPromocije"));
        cijenaUdaljenostiColumn.setCellValueFactory(new PropertyValueFactory<>("cijenaUdaljenosti"));
        cijenaOdrzavanjaColumn.setCellValueFactory(new PropertyValueFactory<>("cijenaOdrzavanja"));
        cijenaPopravkiColumn.setCellValueFactory(new PropertyValueFactory<>("cijenaPopravki"));
    }

    /**
     * Sets the results for the TableView and summary labels.
     *
     * @param dnevniRezultati List of daily business results.
     * @param sumarniRezultat Summary business result.
     */
    public void setResults(List<DnevniRezultatPoslovanja> dnevniRezultati, SumarniRezultatPoslovanja sumarniRezultat) {
        // Populate TableView with daily results
        dnevniRezultatiTableView.getItems().clear();
        dnevniRezultatiTableView.getItems().addAll(dnevniRezultati);

        // Set labels for summary results
        this.sumarniRezultat = sumarniRezultat;
        if (sumarniRezultat != null){
            updateSummaryLabels();
        }
    }

    /**
     * Updates the summary labels with data from the summary result.
     */
    private void updateSummaryLabels() {
        totalPriceLabel.setText(String.format("%.2f", sumarniRezultat.getUkupnaCijena()));
        discountPriceLabel.setText(String.format("%.2f", sumarniRezultat.getCijenaPopusta()));
        promotionPriceLabel.setText(String.format("%.2f", sumarniRezultat.getCijenaPromocije()));
        distancePriceLabel.setText(String.format("%.2f", sumarniRezultat.getCijenaUdaljenosti()));
        maintenancePriceLabel.setText(String.format("%.2f", sumarniRezultat.getCijenaOdrzavanja()));
        repairPriceLabel.setText(String.format("%.2f", sumarniRezultat.getCijenaPopravki()));
        totalCompanyCostLabel.setText(String.format("%.2f", sumarniRezultat.getUkupniTrosakKompanije()));
        totalTaxLabel.setText(String.format("%.2f", sumarniRezultat.getUkupniPorez()));
    }
}
