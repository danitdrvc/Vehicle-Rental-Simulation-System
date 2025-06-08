package org.etf.unibl.danilo_todorovic_1156_22_pj.transport;

import java.time.LocalDate;

/**
 * Represents a car, which is a type of transport vehicle.
 * Extends the PrevoznoSredstvo class.
 */
public class Automobil extends PrevoznoSredstvo {

    private final LocalDate datumNabavke; // Date of purchase
    private final String opis; // Description of the car
    private final int kapacitetOsoba; // Capacity in terms of the number of people

    /**
     * Constructs an Automobil object.
     *
     * @param id Unique identifier for the car.
     * @param proizvodjac Manufacturer of the car.
     * @param model Model of the car.
     * @param cijenaNabavke Purchase price of the car.
     * @param trenutniNivoBaterije Current battery level of the car.
     * @param datumNabavke Date of purchase of the car.
     * @param opis Description of the car.
     * @param kapacitetOsoba Capacity of the car in terms of the number of people it can accommodate.
     */
    public Automobil(String id, String proizvodjac, String model, double cijenaNabavke, int trenutniNivoBaterije,
                     LocalDate datumNabavke, String opis, int kapacitetOsoba) {
        super(id, proizvodjac, model, cijenaNabavke, trenutniNivoBaterije);
        this.datumNabavke = datumNabavke;
        this.opis = opis;
        this.kapacitetOsoba = kapacitetOsoba;
    }

    /**
     * Gets the date of purchase of the car.
     *
     * @return The date of purchase.
     */
    public LocalDate getDatumNabavke() {
        return datumNabavke;
    }

    /**
     * Gets the description of the car.
     *
     * @return The description of the car.
     */
    public String getOpis() {
        return opis;
    }

    /**
     * Gets the capacity of the car in terms of the number of people it can accommodate.
     *
     * @return The capacity of the car.
     */
    public int getKapacitetOsoba() {
        return kapacitetOsoba;
    }

    /**
     * Gets the type of vehicle.
     *
     * @return A string representing the type of vehicle, which is "automobil".
     */
    @Override
    public String getVrsta() {
        return "automobil";
    }
}
