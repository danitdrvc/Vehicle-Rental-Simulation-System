package org.etf.unibl.danilo_todorovic_1156_22_pj.simulacija;

import org.etf.unibl.danilo_todorovic_1156_22_pj.transport.*;
import org.etf.unibl.danilo_todorovic_1156_22_pj.rental.Iznajmljivanje;
import org.etf.unibl.danilo_todorovic_1156_22_pj.util.*;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.*;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

import static java.lang.Thread.sleep;

/**
 * Singleton class that handles the simulation of vehicle rentals and operations.
 */
public class Simulacija {
    private static final HashMap<String, PrevoznoSredstvo> vozila = new HashMap<>(); // Map of vehicles identified by their ID
    private static Simulacija instance; // Singleton instance

    private final ArrayList<SimulacijaIznajmljivanja> iznajmljivanja = new ArrayList<>(); // List of rentals
    private final ArrayList<Kvar> malfunctions = new ArrayList<>(); // List of malfunctions
    private final String pathIznajmljivanja; // Path to rental data file
    private final String pathPrevoznaSredstva; // Path to vehicle data file
    private final HashMap<LocalDate, ArrayList<Racun>> racuni = new HashMap<>(); // Map of invoices per date
    private final ArrayList<DnevniRezultatPoslovanja> dnevniRezultati = new ArrayList<>(); // List of daily business results
    private SumarniRezultatPoslovanja sumarniRezultat; // Summary of business results

    /**
     * Returns the singleton instance of the Simulacija class.
     *
     * @return The singleton instance of Simulacija.
     */
    public static synchronized Simulacija getInstance() {
        if (instance == null) {
            instance = new Simulacija();
        }
        return instance;
    }

    /**
     * Private constructor to initialize paths and load data.
     */
    private Simulacija() {
        this.pathIznajmljivanja = Utility.DATA_BASE_PATH + File.separator + "PJ2 - projektni zadatak 2024 - Iznajmljivanja.csv";
        this.pathPrevoznaSredstva = Utility.DATA_BASE_PATH + File.separator + "PJ2 - projektni zadatak 2024 - Prevozna sredstva.csv";

        ucitavanjeVozila();
        ucitavanjeIznajmljivanja();
        iznajmljivanja.sort(Comparator.comparing(s -> s.getIznajmljivanje().getDatumVrijemeIznajmljivanja()));
    }

    /**
     * Loads vehicles from the data file into the vozila map.
     */
    private void ucitavanjeVozila() {
        try (BufferedReader br = new BufferedReader(new FileReader(pathPrevoznaSredstva))) {

            String line = "";
            br.readLine(); // Skip header line
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                if (!PrevoznoSredstvo.validacijaVozila(data)) {
                    System.out.println("Prevozno sredstvo nije validno: " + data[0]);
                    continue;
                }

                if (vozila.containsKey(data[0])) {
                    System.out.println("Prevozno sredstvo vec postoji: " + data[0]);
                    continue;
                }

                if ("automobil".equals(data[8])) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy.");
                    LocalDate date = LocalDate.parse(data[3], formatter);
                    vozila.put(data[0], new Automobil(data[0], data[1], data[2], Double.parseDouble(data[4]), 100, date, data[7], 4));

                } else if ("bicikl".equals(data[8])) {
                    vozila.put(data[0], new ElektricniBicikl(data[0], data[1], data[2], Double.parseDouble(data[4]), 100, Double.parseDouble(data[5])));

                } else if ("trotinet".equals(data[8])) {
                    vozila.put(data[0], new ElektricniTrotinet(data[0], data[1], data[2], Double.parseDouble(data[4]), 100, Double.parseDouble(data[6])));

                } else {
                    System.out.println("Ne postojeca vrsta: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads rentals from the data file and processes them.
     */
    private void ucitavanjeIznajmljivanja() {
        try (BufferedReader br = new BufferedReader(new FileReader(pathIznajmljivanja))) {
            Mapa mapa = Mapa.getInstance();
            HashMap<String, Integer> brIznajmljivanja = new HashMap<>();

            String line = br.readLine(); // Skip header line
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                if (!Iznajmljivanje.validnoIznajmljivanje(data)) {
                    System.out.println("Iznajmljivanje nije validno");
                    continue;
                }

                if (!vozila.containsKey(data[2])) {
                    System.out.println("Vozilo ne postoji: " + data[2]);
                    continue;
                }

                if (!brIznajmljivanja.containsKey(data[1])) {
                    brIznajmljivanja.put(data[1], 1);
                } else {
                    brIznajmljivanja.put(data[1], brIznajmljivanja.get(data[1]) + 1);
                }

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy HH:mm");
                LocalDateTime dateTime = LocalDateTime.parse(data[0], formatter);

                if (voziloSeKoristi(dateTime, data)) continue;

                Iznajmljivanje novoIznajmljivanje = new Iznajmljivanje(dateTime, data[1], "123456", "654321", data[2],
                        Integer.parseInt(data[3].substring(1)), Integer.parseInt(data[4].substring(0, data[4].length() - 1)),
                        Integer.parseInt(data[5].substring(1)), Integer.parseInt(data[6].substring(0, data[6].length() - 1)),
                        Integer.parseInt(data[7]), ("da".equals(data[8])), ("da".equals(data[9])), brIznajmljivanja.get(data[1]) == 10);

                if (!racuni.containsKey(dateTime.toLocalDate())) {
                    racuni.put(dateTime.toLocalDate(), new ArrayList<Racun>());
                }
                racuni.get(dateTime.toLocalDate()).add(new Racun(novoIznajmljivanje));

                iznajmljivanja.add(new SimulacijaIznajmljivanja(novoIznajmljivanje, mapa));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if a vehicle is already in use at a given date and time.
     *
     * @param dateTime The date and time to check.
     * @param data The rental data.
     * @return {@code true} if the vehicle is in use; {@code false} otherwise.
     */
    private boolean voziloSeKoristi(LocalDateTime dateTime, String[] data) {
        for (SimulacijaIznajmljivanja si : iznajmljivanja) {
            if (si.equalsByDateTimeId(dateTime, data[2])) {
                System.out.println("Vozilo se trenutno koristi");
                return true;
            }
        }
        return false;
    }

    /**
     * Starts threads for processing rentals and handles their results.
     */
    public void pokretanjeNiti() {
        int i = 0;
        while (i < iznajmljivanja.size()) {
            ArrayList<Thread> threads = new ArrayList<>();

            LocalDateTime currentDateTime = iznajmljivanja.get(i).getIznajmljivanje().getDatumVrijemeIznajmljivanja();

            while (i < iznajmljivanja.size() && iznajmljivanja.get(i).getIznajmljivanje().getDatumVrijemeIznajmljivanja().equals(currentDateTime)) {
                if (iznajmljivanja.get(i).getIznajmljivanje().isKvar()) {
                    evidentiranjeKvara(i);
                    i++;
                } else {
                    Thread thread = iznajmljivanja.get(i);
                    threads.add(thread);
                    thread.start();
                    i++;
                }
            }

            for (Thread thread : threads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            dodavanjeDnevnogRezultata(currentDateTime);

            try {
                sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Mapa mapa = Mapa.getInstance();
            for (Thread thread : threads) {
                int x = ((SimulacijaIznajmljivanja)thread).getPrevoznoSredstvo().getX();
                int y = ((SimulacijaIznajmljivanja)thread).getPrevoznoSredstvo().getY();
                mapa.resetujPozicijuNaDefault(x, y);
            }
        }
        sumarniRezultat = new SumarniRezultatPoslovanja(dnevniRezultati);
        System.out.println(sumarniRezultat.getUkupniPorez());
    }

    /**
     * Adds daily business results based on invoices.
     *
     * @param currentDateTime The current date and time.
     */
    private void dodavanjeDnevnogRezultata(LocalDateTime currentDateTime) {
        LocalDate date = currentDateTime.toLocalDate();

        if (dnevniRezultati.isEmpty()) {
            dnevniRezultati.add(new DnevniRezultatPoslovanja(racuni.get(date), date));

        } else if (!date.equals(dnevniRezultati.get(dnevniRezultati.size()-1).getDatum())){
            dnevniRezultati.add(new DnevniRezultatPoslovanja(racuni.get(date), date));
        }
    }

    /**
     * Records a malfunction and saves the vehicle state to a file.
     *
     * @param i The index of the rental with the malfunction.
     */
    private void evidentiranjeKvara(int i) {
        LocalDateTime dateTime = iznajmljivanja.get(i).getIznajmljivanje().getDatumVrijemeIznajmljivanja();
        String id = iznajmljivanja.get(i).getIznajmljivanje().getIDPrevoznogSredstva();

        malfunctions.add(new Kvar(vozila.get(id).getVrsta(), id, dateTime, "Opis kvara"));

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(Utility.DATA_BASE_PATH + File.separator + "kvarovi" + File.separator + id + ".ser"))) {
            out.writeObject(vozila.get(id));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the map of vehicles.
     *
     * @return The map of vehicles.
     */
    public static HashMap<String, PrevoznoSredstvo> getVozila() {
        return vozila;
    }

    /**
     * Returns the list of malfunctions.
     *
     * @return The list of malfunctions.
     */
    public ArrayList<Kvar> getMalfunctions() {
        return malfunctions;
    }

    /**
     * Returns the summary of business results.
     *
     * @return The summary of business results.
     */
    public SumarniRezultatPoslovanja getSumarniRezultat() {
        return sumarniRezultat;
    }

    /**
     * Returns the list of daily business results.
     *
     * @return The list of daily business results.
     */
    public ArrayList<DnevniRezultatPoslovanja> getDnevniRezultati() {
        return dnevniRezultati;
    }
}
