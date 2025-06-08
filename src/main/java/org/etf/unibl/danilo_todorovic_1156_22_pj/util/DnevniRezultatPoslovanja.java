package org.etf.unibl.danilo_todorovic_1156_22_pj.util;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Represents the daily financial results of a business.
 */
public class DnevniRezultatPoslovanja {
    private final double ukupnaCijena;        // Total cost for the day
    private final double cijenaPopusta;       // Total discount cost for the day
    private final double cijenaPromocije;     // Total promotional cost for the day
    private final double cijenaUdaljenosti;   // Total cost related to distance
    private final double cijenaOdrzavanja;    // Total maintenance cost for the day
    private final double cijenaPopravki;      // Total repair cost for the day
    private final LocalDate datum;            // Date of the results

    /**
     * Constructs a DnevniRezultatPoslovanja object by calculating the financial results
     * based on a list of invoices (racuni) and a specific date.
     *
     * @param racuni List of Racun objects representing invoices for the day.
     * @param datum The date for which the financial results are calculated.
     */
    public DnevniRezultatPoslovanja(ArrayList<Racun> racuni, LocalDate datum) {
        double ukupnaCijena = 0.0;
        double cijenaPopusta = 0.0;
        double cijenaPromocije = 0.0;
        double cijenaUdaljenosti = 0.0;
        double cijenaOdrzavanja = 0.0;
        double cijenaPopravki = 0.0;

        // Calculating totals for each category based on the provided invoices
        for (Racun racun : racuni) {
            ukupnaCijena += racun.getUkupnaCijena();
            cijenaPopusta += racun.getCijenaPopusta();
            cijenaPromocije += racun.getCijenaPromocije();
            cijenaUdaljenosti += racun.getCijenaUdaljenosti();
            cijenaPopravki += racun.getCijenaPopravke();
        }

        // Maintenance cost is set as 20% of the total cost
        cijenaOdrzavanja = ukupnaCijena * 0.2;

        // Initializing instance variables with calculated values
        this.ukupnaCijena = ukupnaCijena;
        this.cijenaPopusta = cijenaPopusta;
        this.cijenaPromocije = cijenaPromocije;
        this.cijenaUdaljenosti = cijenaUdaljenosti;
        this.cijenaOdrzavanja = cijenaOdrzavanja;
        this.cijenaPopravki = cijenaPopravki;
        this.datum = datum;
    }

    // Getters for each field to provide access to calculated financial results

    /**
     * Returns the total price.
     *
     * @return the total price as a double.
     */
    public double getUkupnaCijena() {
        return ukupnaCijena;
    }

    /**
     * Returns the discount price.
     *
     * @return the discount price as a double.
     */
    public double getCijenaPopusta() {
        return cijenaPopusta;
    }

    /**
     * Returns the promotion price.
     *
     * @return the promotion price as a double.
     */
    public double getCijenaPromocije() {
        return cijenaPromocije;
    }

    /**
     * Returns the distance-based price.
     *
     * @return the distance-based price as a double.
     */
    public double getCijenaUdaljenosti() {
        return cijenaUdaljenosti;
    }

    /**
     * Returns the maintenance cost.
     *
     * @return the maintenance cost as a double.
     */
    public double getCijenaOdrzavanja() {
        return cijenaOdrzavanja;
    }

    /**
     * Returns the repair cost.
     *
     * @return the repair cost as a double.
     */
    public double getCijenaPopravki() {
        return cijenaPopravki;
    }

    /**
     * Returns the date associated with the result.
     *
     * @return the date as a {@link LocalDate}.
     */
    public LocalDate getDatum() {
        return datum;
    }

}
