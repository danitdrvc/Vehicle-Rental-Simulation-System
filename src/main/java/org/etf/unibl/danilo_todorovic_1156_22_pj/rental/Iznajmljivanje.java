package org.etf.unibl.danilo_todorovic_1156_22_pj.rental;

import org.etf.unibl.danilo_todorovic_1156_22_pj.simulacija.Mapa;
import org.etf.unibl.danilo_todorovic_1156_22_pj.util.Utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a rental transaction in the system.
 */
public class Iznajmljivanje {

    private final LocalDateTime datumVrijemeIznajmljivanja;
    private final String imeKorisnika;
    private final String identifikacioniDokument;
    private final String brojVozackeDozvole;
    private final String iDPrevoznogSredstva;
    private final int xPreuzimanja;
    private final int yPreuzimanja;
    private final int xVracanja;
    private final int yVracanja;
    private final int trajanjeSekunde;
    private final boolean kvar;
    private final boolean promocija;
    private final boolean popust;

    /**
     * Constructs an {@code Iznajmljivanje} instance.
     *
     * @param datumVrijemeIznajmljivanja The date and time of the rental.
     * @param imeKorisnika The name of the user renting the vehicle.
     * @param identifikacioniDokument The identification document of the user.
     * @param brojVozackeDozvole The driver's license number of the user.
     * @param iDPrevoznogSredstva The ID of the rented vehicle.
     * @param xPreuzimanja The x-coordinate for pickup location.
     * @param yPreuzimanja The y-coordinate for pickup location.
     * @param xVracanja The x-coordinate for return location.
     * @param yVracanja The y-coordinate for return location.
     * @param trajanjeSekunde The duration of the rental in seconds.
     * @param kvar Whether the vehicle had a malfunction.
     * @param promocija Whether a promotion was applied.
     * @param popust Whether a discount was applied.
     */
    public Iznajmljivanje(LocalDateTime datumVrijemeIznajmljivanja, String imeKorisnika, String identifikacioniDokument, String brojVozackeDozvole, String iDPrevoznogSredstva, int xPreuzimanja, int yPreuzimanja, int xVracanja, int yVracanja, int trajanjeSekunde, boolean kvar, boolean promocija, boolean popust) {
        this.datumVrijemeIznajmljivanja = datumVrijemeIznajmljivanja;
        this.imeKorisnika = imeKorisnika;
        this.identifikacioniDokument = identifikacioniDokument;
        this.brojVozackeDozvole = brojVozackeDozvole;
        this.iDPrevoznogSredstva = iDPrevoznogSredstva;
        this.xPreuzimanja = xPreuzimanja;
        this.yPreuzimanja = yPreuzimanja;
        this.xVracanja = xVracanja;
        this.yVracanja = yVracanja;
        this.trajanjeSekunde = trajanjeSekunde;
        this.kvar = kvar;
        this.promocija = promocija;
        this.popust = popust;
    }

    /**
     * Returns a string representation of the rental transaction.
     *
     * @return A string containing the rental date/time, user name, and identification document.
     */
    @Override
    public String toString() {
        return datumVrijemeIznajmljivanja.toString() + " " + imeKorisnika + " " + identifikacioniDokument + " ";
    }

    /**
     * Retrieves the type of vehicle based on the vehicle ID.
     *
     * @return The type of vehicle, or an empty string if the vehicle ID is not found.
     */
    public String getVrstaVozila() {
        try (BufferedReader br = new BufferedReader(new FileReader(Utility.DATA_BASE_PATH + File.separator + "PJ2 - projektni zadatak 2024 - Prevozna sredstva.csv"))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (iDPrevoznogSredstva.equals(data[0])) {
                    return data[8];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Validates rental data from an array of strings.
     *
     * @param data An array of strings representing rental data.
     * @return {@code true} if the data is valid; {@code false} otherwise.
     */
    public static boolean validnoIznajmljivanje(String[] data) {
        if (data.length != 10) {
            return false;
        }
        for (int i = 1; i < data.length; i++) {
            if (data[i].isEmpty() || data[i] == null) {
                return false;
            }
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(data[0], formatter);
        } catch (DateTimeParseException e) {
            return false;
        }
        if (isInteger(data[3].substring(1))) {
            try {
                int x = Integer.parseInt(data[3].substring(1));
                if (x < 0 || x >= Mapa.getVelicina()) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }
        }
        if (isInteger(data[4].substring(0, data[4].length()-1))) {
            try {
                int x = Integer.parseInt(data[4].substring(0, data[4].length() - 1));
                if (x < 0 || x >= Mapa.getVelicina()) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }
        }
        if (isInteger(data[5].substring(1))) {
            try {
                int x = Integer.parseInt(data[5].substring(1));
                if (x < 0 || x >= Mapa.getVelicina()) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }
        }
        if (isInteger(data[6].substring(0, data[6].length()-1))) {
            try {
                int x = Integer.parseInt(data[6].substring(0, data[6].length() - 1));
                if (x < 0 || x >= Mapa.getVelicina()) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }
        }
        if (!data[3].startsWith("\"") || !data[4].endsWith("\"") || !data[5].startsWith("\"") || !data[6].endsWith("\"") ||
                !isInteger(data[7]) || !isDaNe(data[8]) || !isDaNe(data[9])) {
            return false;
        }
        return true;
    }

    /**
     * Checks if a string represents a valid integer.
     *
     * @param s The string to check.
     * @return {@code true} if the string represents a valid integer; {@code false} otherwise.
     */
    private static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Checks if a string is either "da" or "ne".
     *
     * @param s The string to check.
     * @return {@code true} if the string is "da" or "ne"; {@code false} otherwise.
     */
    private static boolean isDaNe(String s) {
        return "da".equals(s) || "ne".equals(s);
    }

    // Getters i Setters

    /**
     * Gets the date and time of the rental.
     *
     * @return The rental date and time.
     */
    public LocalDateTime getDatumVrijemeIznajmljivanja() {
        return datumVrijemeIznajmljivanja;
    }

    /**
     * Gets the name of the user renting the vehicle.
     *
     * @return The user's name.
     */
    public String getImeKorisnika() {
        return imeKorisnika;
    }

    /**
     * Gets the identification document of the user.
     *
     * @return The user's identification document.
     */
    public String getIdentifikacioniDokument() {
        return identifikacioniDokument;
    }

    /**
     * Gets the driver's license number of the user.
     *
     * @return The driver's license number.
     */
    public String getBrojVozackeDozvole() {
        return brojVozackeDozvole;
    }

    /**
     * Gets the ID of the rented vehicle.
     *
     * @return The vehicle ID.
     */
    public String getIDPrevoznogSredstva() {
        return iDPrevoznogSredstva;
    }

    /**
     * Gets the x-coordinate for the pickup location.
     *
     * @return The x-coordinate of the pickup location.
     */
    public int getXPreuzimanja() {
        return xPreuzimanja;
    }

    /**
     * Gets the y-coordinate for the pickup location.
     *
     * @return The y-coordinate of the pickup location.
     */
    public int getYPreuzimanja() {
        return yPreuzimanja;
    }

    /**
     * Gets the x-coordinate for the return location.
     *
     * @return The x-coordinate of the return location.
     */
    public int getXVracanja() {
        return xVracanja;
    }

    /**
     * Gets the y-coordinate for the return location.
     *
     * @return The y-coordinate of the return location.
     */
    public int getYVracanja() {
        return yVracanja;
    }

    /**
     * Gets the duration of the rental in seconds.
     *
     * @return The rental duration in seconds.
     */
    public int getTrajanjeSekunde() {
        return trajanjeSekunde;
    }

    /**
     * Checks if the vehicle had a malfunction.
     *
     * @return {@code true} if the vehicle had a malfunction; {@code false} otherwise.
     */
    public boolean isKvar() {
        return kvar;
    }

    /**
     * Checks if a promotion was applied to the rental.
     *
     * @return {@code true} if a promotion was applied; {@code false} otherwise.
     */
    public boolean isPromocija() {
        return promocija;
    }

    /**
     * Checks if a discount was applied to the rental.
     *
     * @return {@code true} if a discount was applied; {@code false} otherwise.
     */
    public boolean isPopust() {
        return popust;
    }

}
