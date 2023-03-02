package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

class CustomComparator implements Comparator<Cerere> {

    public int compare(Cerere c1, Cerere c2) {
        if (c1.date.compareTo(c2.date) > 0) return 1;
        else if (c1.date.compareTo(c2.date) < 0) return -1;
        else return 0;

    }
}
public abstract class Utilizator {

    private String nume;
    TreeSet<Cerere> cereriAsteptare = new TreeSet(new CustomComparator());
    TreeSet<Cerere> cereriSolutionate = new TreeSet(new CustomComparator());
    public Utilizator(String nume) {

        this.nume = nume;
    }

    public String getNume() {
        return nume;
    }

    public String scriereCerere(TipCereri tip) {
        if (this instanceof Persoana) {
            if (tip == TipCereri.BULETIN || tip == TipCereri.SOFER)
                return "Subsemnatul " + this.getNume() + ", va rog sa-mi aprobati urmatoarea solicitare: " + tip.getCerere();
            else
                throw new IllegalArgumentException("Utilizatorul de tip persoana nu poate inainta o cerere de tip " + tip.getCerere());
        }
        else if (this instanceof Angajat) {
            if (tip == TipCereri.BULETIN || tip == TipCereri.SOFER || tip == TipCereri.SALARIU)
                return "Subsemnatul " + this.getNume() + ", angajat la compania " + ((Angajat) this).getCompanie()
                        +", va rog sa-mi aprobati urmatoarea solicitare: " + tip.getCerere();
            else
                throw new IllegalArgumentException("Utilizatorul de tip angajat nu poate inainta o cerere de tip " + tip.getCerere());
        }
        else if (this instanceof Pensionar) {
            if (tip == TipCereri.BULETIN || tip == TipCereri.SOFER || tip == TipCereri.PENSIE)
                return "Subsemnatul " + this.getNume() + ", va rog sa-mi aprobati urmatoarea solicitare: " + tip.getCerere();
            else
                throw new IllegalArgumentException("Utilizatorul de tip pensionar nu poate inainta o cerere de tip " + tip.getCerere());
        }
        else if (this instanceof Elev) {
            if (tip == TipCereri.BULETIN || tip == TipCereri.ELEV)
                return "Subsemnatul " + this.getNume() + ", elev la scoala " + ((Elev) this).getScoala() +
                        ", va rog sa-mi aprobati urmatoarea solicitare: " + tip.getCerere();
            else
                throw new IllegalArgumentException("Utilizatorul de tip elev nu poate inainta o cerere de tip " + tip.getCerere());
        }
        else if (this instanceof EntitateJuridica) {
            if (tip == TipCereri.CONSTITUTIV || tip == TipCereri.AUTORIZATIE)
                return "Subsemnatul " + ((EntitateJuridica) this).getReprezentant() + ", reprezentant legal al companiei " +
                        this.getNume()+ ", va rog sa-mi aprobati urmatoarea solicitare: " + tip.getCerere();
            else
                throw new IllegalArgumentException("Utilizatorul de tip entitate juridica nu poate inainta o cerere de tip " + tip.getCerere());
        }
        else throw new IllegalArgumentException("Utilizatorul nu este niciunul dintre tipurile de utilizatori");
    }
    public void retragereCerere (Date d) {
        for (Cerere c : cereriAsteptare) {
            if (c.date.equals(d)) {
                cereriAsteptare.remove(c);
                return;
            }
        }
    }
    public String afisareCereriAsteptare() {
        String rez = "";
        for( Cerere c : cereriAsteptare) {
            SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);
            String d = format.format(c.date);
            rez += d + " - " + c.text + "\n";
        }
        return rez;
    }
    public String afisareCereriSolutionate(String nume) {
        String rez = "";
        for( Cerere c : cereriSolutionate) {
            SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);
            String d = format.format(c.date);
            rez += d + " - " + c.text + "\n";
        }
        return rez;
    }
}

