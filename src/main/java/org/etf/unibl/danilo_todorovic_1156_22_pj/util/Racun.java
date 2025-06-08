package org.etf.unibl.danilo_todorovic_1156_22_pj.util;

import org.etf.unibl.danilo_todorovic_1156_22_pj.rental.Iznajmljivanje;

/**
 * Represents an invoice (Racun) for a rental transaction.
 */
public class Racun {
    private final Iznajmljivanje iznajmljivanje; // The rental transaction associated with this invoice.
    private final double ukupnaCijena;           // The total price of the rental.
    private final double cijenaPopusta;          // The discount applied to the total price.
    private final double cijenaPromocije;        // The promotion discount applied to the total price.
    private final double cijenaUdaljenosti;      // The cost based on the distance traveled.
    private final double cijenaOdrzavanja;       // The maintenance cost for the rental vehicle.
    private final double cijenaPopravke;         // The repair cost in case of a malfunction during rental.

    /**
     * Constructs a Racun (invoice) for the given rental transaction.
     *
     * @param iznajmljivanje The rental transaction for which the invoice is being created.
     */
    public Racun(Iznajmljivanje iznajmljivanje) {
        this.iznajmljivanje = iznajmljivanje;

        Utility utility = new Utility();
        double osnovnaCijena = utility.getOsnovnaCijena(iznajmljivanje);
        double udaljenost = utility.getUdaljenost(iznajmljivanje);
        this.ukupnaCijena = utility.getUkupnaCijena(iznajmljivanje, osnovnaCijena, udaljenost);
        this.cijenaPopusta = utility.getPopust(iznajmljivanje, osnovnaCijena * udaljenost);
        this.cijenaPromocije = utility.getPromocija(iznajmljivanje, osnovnaCijena * udaljenost);
        this.cijenaUdaljenosti = udaljenost * osnovnaCijena;
        this.cijenaOdrzavanja = ukupnaCijena * 0.2;
        if (iznajmljivanje.isKvar()) {
            this.cijenaPopravke = utility.getCijenaPopravke(iznajmljivanje.getIDPrevoznogSredstva());
        } else {
            this.cijenaPopravke = 0.0;
        }
    }

    // Getter methods for retrieving different cost components of the rental.
    /**
     * Returns the total price of the vehicle.
     *
     * @return The total price as a double value.
     */
    public double getUkupnaCijena() {
        return ukupnaCijena;
    }

    /**
     * Returns the discounted price of the vehicle.
     *
     * @return The price after discount as a double value.
     */
    public double getCijenaPopusta() {
        return cijenaPopusta;
    }

    /**
     * Returns the promotional price of the vehicle.
     *
     * @return The promotional price as a double value.
     */
    public double getCijenaPromocije() {
        return cijenaPromocije;
    }

    /**
     * Returns the price based on the distance traveled.
     *
     * @return The distance-based price as a double value.
     */
    public double getCijenaUdaljenosti() {
        return cijenaUdaljenosti;
    }

    /**
     * Returns the maintenance cost of the vehicle.
     *
     * @return The maintenance cost as a double value.
     */
    public double getCijenaOdrzavanja() {
        return cijenaOdrzavanja;
    }

    /**
     * Returns the repair cost of the vehicle.
     *
     * @return The repair cost as a double value.
     */
    public double getCijenaPopravke() {
        return cijenaPopravke;
    }


    @Override
    public String toString() {
        Utility utility = new Utility();
        double osnovnaCijena = utility.getOsnovnaCijena(iznajmljivanje);
        double udaljenost = utility.getUdaljenost(iznajmljivanje);
        double ukupnaCijena = utility.getUkupnaCijena(iznajmljivanje, osnovnaCijena, udaljenost);
        String dokumentacija = "";
        if ("automobil".equals(iznajmljivanje.getVrstaVozila())) {
            dokumentacija = "Identifikacioni dokument: " + iznajmljivanje.getIdentifikacioniDokument() + "\n" +
                    "Broj vozacke dozvole: " + iznajmljivanje.getBrojVozackeDozvole() + "\n";
        }
        return "Račun za iznajmljivanje:\n" +
                "Korisnik: " + iznajmljivanje.getImeKorisnika() + "\n" + dokumentacija +
                "Prevozno sredstvo: " + iznajmljivanje.getIDPrevoznogSredstva() + "\n" +
                "Datum i vrijeme iznajmljivanja: " + iznajmljivanje.getDatumVrijemeIznajmljivanja() + "\n" +
                "Trajanje (sekunde): " + iznajmljivanje.getTrajanjeSekunde() + "\n" +
                "Pocetna lokacija: (" + iznajmljivanje.getXPreuzimanja() + "," + iznajmljivanje.getYPreuzimanja() + ")\n" +
                "Krajnja lokacija: (" + iznajmljivanje.getXVracanja() + "," + iznajmljivanje.getYVracanja() + ")\n" +
                "Kvar: " + (iznajmljivanje.isKvar() ? "Vozilo ima kvar" : "Nema kvara") + "\n" +
                "Promocija: " + (iznajmljivanje.isPromocija() ? "Korisnik ima promociju" : "Korisnik nema promociju") + "\n" +
                "Popust: " + (iznajmljivanje.isPopust() ? "Korisnik ima popust" : "Korisnik nema popust") + "\n" +
                "Osnovna cijena: " + osnovnaCijena + "\n" +
                "Skalar za dio grada: " + udaljenost + "\n" +
                "Ukupna cijena: " + ukupnaCijena + "\n";
    }
}
