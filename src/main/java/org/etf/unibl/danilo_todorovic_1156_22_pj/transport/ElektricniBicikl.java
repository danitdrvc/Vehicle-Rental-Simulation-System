package org.etf.unibl.danilo_todorovic_1156_22_pj.transport;

/**
 * Represents an electric bicycle, which is a type of transport vehicle.
 * Extends the PrevoznoSredstvo class.
 */
public class ElektricniBicikl extends PrevoznoSredstvo {

    private final double autonomija; // The range or autonomy of the electric bicycle

    /**
     * Constructs an ElektricniBicikl object.
     *
     * @param id Unique identifier for the electric bicycle.
     * @param proizvodjac Manufacturer of the electric bicycle.
     * @param model Model of the electric bicycle.
     * @param cijenaNabavke Purchase price of the electric bicycle.
     * @param trenutniNivoBaterije Current battery level of the electric bicycle.
     * @param autonomija The maximum distance the electric bicycle can travel on a full charge.
     */
    public ElektricniBicikl(String id, String proizvodjac, String model, double cijenaNabavke, int trenutniNivoBaterije, double autonomija) {
        super(id, proizvodjac, model, cijenaNabavke, trenutniNivoBaterije);
        this.autonomija = autonomija;
    }

    /**
     * Gets the range or autonomy of the electric bicycle.
     *
     * @return The range (in kilometers) of the electric bicycle.
     */
    public double getAutonomija() {
        return autonomija;
    }

    /**
     * Gets the type of vehicle.
     *
     * @return A string representing the type of vehicle, which is "bicikl".
     */
    @Override
    public String getVrsta() {
        return "bicikl";
    }
}
