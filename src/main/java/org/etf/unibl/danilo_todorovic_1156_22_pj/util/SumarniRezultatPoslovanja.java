package org.etf.unibl.danilo_todorovic_1156_22_pj.util;

import java.util.ArrayList;

/**
 * Represents the summary result of the business for multiple days.
 * It aggregates the daily business results to provide an overall summary.
 */
public class SumarniRezultatPoslovanja {
    private final double ukupnaCijena;           // The total income from all days.
    private final double cijenaPopusta;          // The total discount given over all days.
    private final double cijenaPromocije;        // The total promotional discounts applied over all days.
    private final double cijenaUdaljenosti;      // The total distance-based cost over all days.
    private final double cijenaOdrzavanja;       // The total maintenance cost for all rentals.
    private final double cijenaPopravki;         // The total repair costs for all days.
    private final double ukupniTrosakKompanije;  // The total cost for the company including maintenance.
    private final double ukupniPorez;            // The total tax calculated based on business earnings.

    /**
     * Constructs a summary result of the business based on daily results.
     *
     * @param dnevniRezultati The list of daily results (`DnevniRezultatPoslovanja`) for the business.
     */
    public SumarniRezultatPoslovanja(ArrayList<DnevniRezultatPoslovanja> dnevniRezultati) {
        double ukupnaCijena = 0.0;
        double cijenaPopusta = 0.0;
        double cijenaPromocije = 0.0;
        double cijenaUdaljenosti = 0.0;
        double cijenaOdrzavanja = 0.0;
        double cijenaPopravki = 0.0;

        // Sum up all relevant costs and earnings from the list of daily results.
        for (DnevniRezultatPoslovanja rezultat : dnevniRezultati) {
            ukupnaCijena += rezultat.getUkupnaCijena();
            cijenaPopusta += rezultat.getCijenaPopusta();
            cijenaPromocije += rezultat.getCijenaPromocije();
            cijenaUdaljenosti += rezultat.getCijenaUdaljenosti();
            cijenaPopravki += rezultat.getCijenaPopravki();
        }
        cijenaOdrzavanja = ukupnaCijena * 0.2; // Calculating maintenance costs as 20% of total income.

        this.ukupnaCijena = ukupnaCijena;
        this.cijenaPopusta = cijenaPopusta;
        this.cijenaPromocije = cijenaPromocije;
        this.cijenaUdaljenosti = cijenaUdaljenosti;
        this.cijenaOdrzavanja = cijenaOdrzavanja;
        this.cijenaPopravki = cijenaPopravki;
        this.ukupniTrosakKompanije = ukupnaCijena * 0.2;
        this.ukupniPorez = (ukupnaCijena - cijenaOdrzavanja - cijenaPopravki - ukupniTrosakKompanije) * 0.1;
    }

    // Getter methods for retrieving various costs and business results.
    /**
     * Retrieves the total price.
     *
     * @return The total price as a double.
     */
    public double getUkupnaCijena() {
        return ukupnaCijena;
    }

    /**
     * Retrieves the discount price.
     *
     * @return The discount price as a double.
     */
    public double getCijenaPopusta() {
        return cijenaPopusta;
    }

    /**
     * Retrieves the promotional price.
     *
     * @return The promotional price as a double.
     */
    public double getCijenaPromocije() {
        return cijenaPromocije;
    }

    /**
     * Retrieves the price based on distance.
     *
     * @return The distance-based price as a double.
     */
    public double getCijenaUdaljenosti() {
        return cijenaUdaljenosti;
    }

    /**
     * Retrieves the maintenance cost.
     *
     * @return The maintenance cost as a double.
     */
    public double getCijenaOdrzavanja() {
        return cijenaOdrzavanja;
    }

    /**
     * Retrieves the cost of repairs.
     *
     * @return The repair cost as a double.
     */
    public double getCijenaPopravki() {
        return cijenaPopravki;
    }

    /**
     * Retrieves the total company cost.
     *
     * @return The total cost incurred by the company as a double.
     */
    public double getUkupniTrosakKompanije() {
        return ukupniTrosakKompanije;
    }

    /**
     * Retrieves the total tax amount.
     *
     * @return The total tax amount as a double.
     */
    public double getUkupniPorez() {
        return ukupniPorez;
    }

}
