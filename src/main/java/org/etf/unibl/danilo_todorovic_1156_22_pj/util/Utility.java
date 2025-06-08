package org.etf.unibl.danilo_todorovic_1156_22_pj.util;

import org.etf.unibl.danilo_todorovic_1156_22_pj.rental.Iznajmljivanje;
import org.etf.unibl.danilo_todorovic_1156_22_pj.simulacija.Simulacija;

import java.io.*;
import java.util.Properties;

/**
 * Utility class for various helper methods related to rental and pricing calculations.
 */
public class Utility {
    public static final String DATA_BASE_PATH = "src" + File.separator + "main" + File.separator + "resources" + File.separator + "data";
    private static final double KOEFICIJENT_POPRAVKE_AUTOMOBILA = 0.07; // Repair coefficient for cars
    private static final double KOEFICIJENT_POPRAVKE_BICIKLA = 0.04; // Repair coefficient for bikes
    private static final double KOEFICIJENT_POPRAVKE_TROTINETA = 0.02; // Repair coefficient for scooters
    private static final String PROPERTIES_PATH = "app.properties";

    private final double distance_narrow;
    private final double distance_wide;
    private final double discount;
    private final double discount_prom;
    private final double car_unit_price;
    private final double bike_unit_price;
    private final double scooter_unit_price;

    /**
     * Constructor initializes all pricing and discount parameters from the properties file.
     */
    public Utility() {
        Properties appProps = getProperties();
        this.distance_narrow = Double.parseDouble(appProps.getProperty("DISTANCE_NARROW"));
        this.distance_wide = Double.parseDouble(appProps.getProperty("DISTANCE_WIDE"));
        this.discount = Double.parseDouble(appProps.getProperty("DISCOUNT"));
        this.discount_prom = Double.parseDouble(appProps.getProperty("DISCOUNT_PROM"));
        this.car_unit_price = Double.parseDouble(appProps.getProperty("CAR_UNIT_PRICE"));
        this.bike_unit_price = Double.parseDouble(appProps.getProperty("BIKE_UNIT_PRICE"));
        this.scooter_unit_price = Double.parseDouble(appProps.getProperty("SCOOTER_UNIT_PRICE"));
    }

    /**
     * Calculates the total rental price after applying discounts and promotions.
     *
     * @param iznajmljivanje The rental object.
     * @param osnovnaCijena The base price of the rental.
     * @param udaljenost The distance factor.
     * @return The total price after discounts, promotions, and if applicable, zero if the vehicle is damaged.
     */
    public double getUkupnaCijena(Iznajmljivanje iznajmljivanje, double osnovnaCijena, double udaljenost) {
        double iznos = osnovnaCijena * udaljenost;

        double popust = getPopust(iznajmljivanje, iznos);
        double promocija = getPromocija(iznajmljivanje, iznos);
        double ukupno = iznos - popust - promocija;

        return iznajmljivanje.isKvar() ? 0 : ukupno;
    }

    /**
     * Gets the base price for a rental based on the vehicle type and rental duration.
     *
     * @param iznajmljivanje The rental object.
     * @return The calculated base price.
     */
    public double getOsnovnaCijena(Iznajmljivanje iznajmljivanje) {
        double osnovnaCijena;
        String vrsta = Simulacija.getVozila().get(iznajmljivanje.getIDPrevoznogSredstva()).getVrsta();

        switch (vrsta) {
            case "automobil":
                osnovnaCijena = car_unit_price * iznajmljivanje.getTrajanjeSekunde();
                break;
            case "bicikl":
                osnovnaCijena = bike_unit_price * iznajmljivanje.getTrajanjeSekunde();
                break;
            case "trotinet":
                osnovnaCijena = scooter_unit_price * iznajmljivanje.getTrajanjeSekunde();
                break;
            default:
                System.out.println("Greska u odredjivanju vrste vozila tokom racunanja cijene");
                osnovnaCijena = 0;
                break;
        }
        return osnovnaCijena;
    }

    /**
     * Determines the distance coefficient for the rental based on the starting and ending locations.
     *
     * @param iznajmljivanje The rental object.
     * @return The distance coefficient.
     */
    public double getUdaljenost(Iznajmljivanje iznajmljivanje) {
        if (iznajmljivanje.getXPreuzimanja() >= 5 && iznajmljivanje.getXPreuzimanja() <= 14 &&
                iznajmljivanje.getYPreuzimanja() >= 5 && iznajmljivanje.getYPreuzimanja() <= 14 &&
                iznajmljivanje.getXVracanja() >= 5 && iznajmljivanje.getXVracanja() <= 14 &&
                iznajmljivanje.getYVracanja() >= 5 && iznajmljivanje.getYVracanja() <= 14) {
            return distance_narrow;
        } else {
            return distance_wide;
        }
    }

    /**
     * Calculates the discount for the rental if applicable.
     *
     * @param iznajmljivanje The rental object.
     * @param iznos The base amount to apply the discount to.
     * @return The discount amount.
     */
    public double getPopust(Iznajmljivanje iznajmljivanje, double iznos) {
        return iznajmljivanje.isPopust() ? discount * iznos : 0;
    }

    /**
     * Calculates the promotional discount for the rental if applicable.
     *
     * @param iznajmljivanje The rental object.
     * @param iznos The base amount to apply the promotional discount to.
     * @return The promotional discount amount.
     */
    public double getPromocija(Iznajmljivanje iznajmljivanje, double iznos) {
        return iznajmljivanje.isPromocija() ? discount_prom * iznos : 0;
    }

    /**
     * Retrieves the properties from the properties file.
     *
     * @return A Properties object containing the application settings.
     */
    public static Properties getProperties() {
        Properties appProps = new Properties();
        try (InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(PROPERTIES_PATH)) {
            if (input == null) {
                System.err.println("Unable to find app.properties");
                return appProps;
            }
            appProps.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return appProps;
    }

    /**
     * Calculates the repair cost for a vehicle based on its type and acquisition cost.
     *
     * @param id The ID of the vehicle.
     * @return The calculated repair cost.
     */
    public double getCijenaPopravke(String id) {
        double cijenaNabavke = Simulacija.getVozila().get(id).getCijenaNabavke();
        String vrsta = Simulacija.getVozila().get(id).getVrsta();
        switch (vrsta) {
            case "automobil":
                return cijenaNabavke * KOEFICIJENT_POPRAVKE_AUTOMOBILA;
            case "bicikl":
                return cijenaNabavke * KOEFICIJENT_POPRAVKE_BICIKLA;
            case "trotinet":
                return cijenaNabavke * KOEFICIJENT_POPRAVKE_TROTINETA;
            default:
                System.out.println("Greska pri citanju vrste vozila");
                return 0;
        }
    }
}
