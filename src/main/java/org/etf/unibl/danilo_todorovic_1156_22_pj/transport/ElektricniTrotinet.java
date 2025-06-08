package org.etf.unibl.danilo_todorovic_1156_22_pj.transport;

/**
 * Represents an electric scooter, which is a type of transport vehicle.
 * Extends the PrevoznoSredstvo class.
 */
public class ElektricniTrotinet extends PrevoznoSredstvo {

    private final double maksimalnaBrzina; // The maximum speed of the electric scooter

    /**
     * Constructs an ElektricniTrotinet object.
     *
     * @param id Unique identifier for the electric scooter.
     * @param proizvodjac Manufacturer of the electric scooter.
     * @param model Model of the electric scooter.
     * @param cijenaNabavke Purchase price of the electric scooter.
     * @param trenutniNivoBaterije Current battery level of the electric scooter.
     * @param maksimalnaBrzina The maximum speed the electric scooter can achieve.
     */
    public ElektricniTrotinet(String id, String proizvodjac, String model, double cijenaNabavke, int trenutniNivoBaterije, double maksimalnaBrzina) {
        super(id, proizvodjac, model, cijenaNabavke, trenutniNivoBaterije);
        this.maksimalnaBrzina = maksimalnaBrzina;
    }

    /**
     * Gets the maximum speed of the electric scooter.
     *
     * @return The maximum speed (in kilometers per hour) of the electric scooter.
     */
    public double getMaksimalnaBrzina() {
        return maksimalnaBrzina;
    }

    /**
     * Gets the type of vehicle.
     *
     * @return A string representing the type of vehicle, which is "trotinet".
     */
    @Override
    public String getVrsta() {
        return "trotinet";
    }
}
