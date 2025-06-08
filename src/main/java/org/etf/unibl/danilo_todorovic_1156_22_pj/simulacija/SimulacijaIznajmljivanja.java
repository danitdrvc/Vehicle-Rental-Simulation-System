package org.etf.unibl.danilo_todorovic_1156_22_pj.simulacija;

import javafx.scene.control.Label;
import org.etf.unibl.danilo_todorovic_1156_22_pj.rental.Iznajmljivanje;
import org.etf.unibl.danilo_todorovic_1156_22_pj.transport.PrevoznoSredstvo;
import org.etf.unibl.danilo_todorovic_1156_22_pj.util.*;

import java.io.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Properties;
import java.time.format.DateTimeFormatter;

/**
 * Class responsible for simulating the rental of a vehicle.
 * Extends the Thread class to handle the simulation in a separate thread.
 */
public class SimulacijaIznajmljivanja extends Thread {
    private final Iznajmljivanje iznajmljivanje; // Rental information
    private final Mapa mapa; // Map for updating vehicle position
    private final PrevoznoSredstvo prevoznoSredstvo; // Vehicle being rented

    /**
     * Constructs a SimulacijaIznajmljivanja object.
     *
     * @param iznajmljivanje Rental information.
     * @param mapa Map for updating vehicle position.
     */
    public SimulacijaIznajmljivanja(Iznajmljivanje iznajmljivanje, Mapa mapa) {
        this.iznajmljivanje = iznajmljivanje;
        this.mapa = mapa;
        HashMap<String, PrevoznoSredstvo> vozila = Simulacija.getVozila();
        this.prevoznoSredstvo = vozila.get(iznajmljivanje.getIDPrevoznogSredstva());
        setDaemon(true); // Set as a daemon thread
    }

    @Override
    public void run() {
        int xStart = iznajmljivanje.getXPreuzimanja();
        int yStart = iznajmljivanje.getYPreuzimanja();
        int xEnd = iznajmljivanje.getXVracanja();
        int yEnd = iznajmljivanje.getYVracanja();

        prevoznoSredstvo.setX(xStart);
        prevoznoSredstvo.setY(yStart);

        int numOfTiles = Math.abs(xStart - xEnd) + 1 + Math.abs(yStart - yEnd);
        double timeOnOneTile = (double) iznajmljivanje.getTrajanjeSekunde() / numOfTiles;

        // Check battery level
        if (prevoznoSredstvo.getTrenutniNivoBaterije() < numOfTiles * PrevoznoSredstvo.BATTERY_DRAIN) {
            System.out.println("Baterija napunjena");
            prevoznoSredstvo.napuniBateriju();
        }

        if (numOfTiles * PrevoznoSredstvo.BATTERY_DRAIN > 100) {
            System.out.println("Vozilo " + prevoznoSredstvo.getId() + " nema dovoljno veliku bateriju za taj put");
            return;
        }

        String vrsta = prevoznoSredstvo.getVrsta();
        String id = prevoznoSredstvo.getId();

        // Update the position on the map
        Label label = mapa.azurirajPoziciju(prevoznoSredstvo.getX(), prevoznoSredstvo.getY(), vrsta, id, prevoznoSredstvo.getTrenutniNivoBaterije());

        // Move the vehicle to the end position
        while (prevoznoSredstvo.getX() != xEnd) {
            resetujPozicijuSaPauzom(timeOnOneTile, label);
            if (xStart > xEnd) {
                prevoznoSredstvo.setX(prevoznoSredstvo.getX() - 1);
                prevoznoSredstvo.setTrenutniNivoBaterije(prevoznoSredstvo.getTrenutniNivoBaterije() - PrevoznoSredstvo.BATTERY_DRAIN);
            } else if (xStart < xEnd) {
                prevoznoSredstvo.setX(prevoznoSredstvo.getX() + 1);
                prevoznoSredstvo.setTrenutniNivoBaterije(prevoznoSredstvo.getTrenutniNivoBaterije() - PrevoznoSredstvo.BATTERY_DRAIN);
            }
            label = mapa.azurirajPoziciju(prevoznoSredstvo.getX(), prevoznoSredstvo.getY(), vrsta, id, prevoznoSredstvo.getTrenutniNivoBaterije());
        }

        while (prevoznoSredstvo.getY() != yEnd) {
            resetujPozicijuSaPauzom(timeOnOneTile, label);
            if (yStart > yEnd) {
                prevoznoSredstvo.setY(prevoznoSredstvo.getY() - 1);
                prevoznoSredstvo.setTrenutniNivoBaterije(prevoznoSredstvo.getTrenutniNivoBaterije() - PrevoznoSredstvo.BATTERY_DRAIN);
            } else if (yStart < yEnd) {
                prevoznoSredstvo.setY(prevoznoSredstvo.getY() + 1);
                prevoznoSredstvo.setTrenutniNivoBaterije(prevoznoSredstvo.getTrenutniNivoBaterije() - PrevoznoSredstvo.BATTERY_DRAIN);
            }
            label = mapa.azurirajPoziciju(prevoznoSredstvo.getX(), prevoznoSredstvo.getY(), vrsta, id, prevoznoSredstvo.getTrenutniNivoBaterije());
        }

        // Generate receipt file
        generisiFajlRacuna();
    }

    /**
     * Updates the position of the vehicle on the map with a pause.
     *
     * @param timeOnOneTile Time taken to move between tiles.
     * @param label The label to update on the map.
     */
    private synchronized void resetujPozicijuSaPauzom(double timeOnOneTile, Label label) {
        try {
            if (timeOnOneTile > 0) {
                Thread.sleep((long) (timeOnOneTile * 1000)); // Sleep for the time on one tile
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mapa.resetujPoziciju(prevoznoSredstvo.getX(), prevoznoSredstvo.getY(), label);
    }

    /**
     * Checks if the rental matches the given date and vehicle ID.
     *
     * @param dateTime The date and time to check.
     * @param id The vehicle ID to check.
     * @return {@code true} if the rental matches; {@code false} otherwise.
     */
    public boolean equalsByDateTimeId(LocalDateTime dateTime, String id) {
        return iznajmljivanje.getDatumVrijemeIznajmljivanja().equals(dateTime) &&
                iznajmljivanje.getIDPrevoznogSredstva().equals(id);
    }

    /**
     * Generates a receipt file for the rental.
     */
    public synchronized void generisiFajlRacuna() {
        Properties appProps = Utility.getProperties();
        String billFolderPath = appProps.getProperty("RECEIPT_PATH").replace("/", File.separator);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(billFolderPath + File.separator +
                iznajmljivanje.getDatumVrijemeIznajmljivanja().format(formatter) + "-" +
                iznajmljivanje.getIDPrevoznogSredstva() + ".txt"))) {
            bw.write(new Racun(iznajmljivanje).toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Getters and Setters

    /**
     * Retrieves the rental information associated with this object.
     *
     * @return The {@link Iznajmljivanje} instance representing the rental.
     */
    public Iznajmljivanje getIznajmljivanje() {
        return iznajmljivanje;
    }

    /**
     * Retrieves the vehicle associated with this object.
     *
     * @return The {@link PrevoznoSredstvo} instance representing the vehicle.
     */
    public PrevoznoSredstvo getPrevoznoSredstvo() {
        return prevoznoSredstvo;
    }

    /**
     * Returns a string representation of this object,
     * which is the string representation of the rental information.
     *
     * @return A string representing the rental information.
     */
    @Override
    public String toString() {
        return iznajmljivanje.toString();
    }

}
