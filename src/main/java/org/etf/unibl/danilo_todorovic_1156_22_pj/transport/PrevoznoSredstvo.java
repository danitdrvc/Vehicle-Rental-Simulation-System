package org.etf.unibl.danilo_todorovic_1156_22_pj.transport;

import org.etf.unibl.danilo_todorovic_1156_22_pj.util.Utility;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.io.Serializable;

/**
 * Represents a base class for various types of transport vehicles.
 * Implements Serializable to allow instances to be serialized.
 */
public abstract class PrevoznoSredstvo implements Serializable {
    private static final long serialVersionUID = 1L;
    public static int BATTERY_DRAIN = 4; // Amount of battery drain per unit distance

    protected String id; // Unique identifier for the vehicle
    protected String proizvodjac; // Manufacturer of the vehicle
    protected String model; // Model of the vehicle
    protected double cijenaNabavke; // Purchase price of the vehicle
    protected int trenutniNivoBaterije; // Current battery level of the vehicle
    protected int x; // Current X position of the vehicle
    protected int y; // Current Y position of the vehicle

    /**
     * Constructs a PrevoznoSredstvo object.
     *
     * @param id Unique identifier for the vehicle.
     * @param proizvodjac Manufacturer of the vehicle.
     * @param model Model of the vehicle.
     * @param cijenaNabavke Purchase price of the vehicle.
     * @param trenutniNivoBaterije Current battery level of the vehicle.
     */
    public PrevoznoSredstvo(String id, String proizvodjac, String model, double cijenaNabavke, int trenutniNivoBaterije) {
        this.id = id;
        this.proizvodjac = proizvodjac;
        this.model = model;
        this.cijenaNabavke = cijenaNabavke;
        this.trenutniNivoBaterije = trenutniNivoBaterije;
    }

    /**
     * Fills the battery of the vehicle to full capacity (100%).
     */
    public void napuniBateriju() {
        this.trenutniNivoBaterije = 100;
    }

    /**
     * Abstract method to be implemented by subclasses to return the type of vehicle.
     *
     * @return A string representing the type of vehicle.
     */
    public abstract String getVrsta();

    /**
     * Validates vehicle data based on the vehicle type and attributes.
     *
     * @param data Array of strings representing vehicle data.
     * @return True if the data is valid; false otherwise.
     */
    public static boolean validacijaVozila(String[] data) {
        if (data.length != 9) {
            return false;
        }
        if (data[0].isEmpty() || data[1].isEmpty() || data[2].isEmpty() || data[4].isEmpty() || data[8].isEmpty() || !isInteger(data[4])) {
            return false;
        }
        if ("automobil".equals(data[8])) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy.");
                LocalDate dateTime = LocalDate.parse(data[3], formatter);
            } catch (DateTimeParseException e) {
                return false;
            }
            return !data[7].isEmpty();
        } else if ("bicikl".equals(data[8])) {
            return !data[5].isEmpty() && isInteger(data[5]);
        } else if ("trotinet".equals(data[8])) {
            return !data[6].isEmpty() && isInteger(data[6]);
        } else {
            return false;
        }
    }

    /**
     * Checks if a given string represents a valid integer.
     *
     * @param s The string to check.
     * @return True if the string is a valid integer; false otherwise.
     */
    private static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Getters and Setters
    /**
     * Gets the unique identifier of the vehicle.
     *
     * @return The ID of the vehicle.
     */
    public String getId() { return id; }

    /**
     * Gets the manufacturer of the vehicle.
     *
     * @return The manufacturer of the vehicle.
     */
    public String getProizvodjac() { return proizvodjac; }

    /**
     * Gets the model of the vehicle.
     *
     * @return The model of the vehicle.
     */
    public String getModel() { return model; }

    /**
     * Gets the purchase price of the vehicle.
     *
     * @return The purchase price of the vehicle.
     */
    public double getCijenaNabavke() { return cijenaNabavke; }

    /**
     * Gets the current battery level of the vehicle.
     *
     * @return The current battery level of the vehicle.
     */
    public int getTrenutniNivoBaterije() { return trenutniNivoBaterije; }

    /**
     * Sets the current battery level of the vehicle.
     *
     * @param trenutniNivoBaterije The new battery level of the vehicle.
     */
    public void setTrenutniNivoBaterije(int trenutniNivoBaterije) { this.trenutniNivoBaterije = trenutniNivoBaterije; }

    /**
     * Sets the x-coordinate of the vehicle's position.
     *
     * @param x The new x-coordinate of the vehicle.
     */
    public void setX(int x) { this.x = x; }

    /**
     * Sets the y-coordinate of the vehicle's position.
     *
     * @param y The new y-coordinate of the vehicle.
     */
    public void setY(int y) { this.y = y; }

    /**
     * Gets the x-coordinate of the vehicle's position.
     *
     * @return The x-coordinate of the vehicle's position.
     */
    public int getX() { return x; }

    /**
     * Gets the y-coordinate of the vehicle's position.
     *
     * @return The y-coordinate of the vehicle's position.
     */
    public int getY() { return y; }


    /**
     * Gets the repair cost of the vehicle by fetching it from a utility class.
     *
     * @return The repair cost of the vehicle.
     */
    public double getCijenaPopravke() {
        Utility util = new Utility();
        return util.getCijenaPopravke(id);
    }
}
