package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

class CustomComparator2 implements Comparator<Cerere> {

    public int compare(Cerere c1, Cerere c2) {

        if (c1.prioritate > c2.prioritate) return -1;
        else if (c1.prioritate < c2.prioritate) return 1;
        else if (c1.date.compareTo(c2.date) > 0) return 1;
        else return -1;

    }
}
class FunctionarPublic {
    String nume;

    public FunctionarPublic(String nume) {
        this.nume = nume;
    }

}
public class Birou<T extends Utilizator>  {


    List<FunctionarPublic> functionari = new ArrayList<>();
    PriorityQueue<Cerere> cereri = new PriorityQueue<>(new CustomComparator2());
    public void rezolvaCererea(FunctionarPublic f) {

        Cerere c = cereri.poll();
        FileWriter out2;
        Date d2;
        try {
            out2 = new FileWriter("src/main/resources/output/" + "functionar_" + f.nume + ".txt", true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);
            String d = format.format(c.date);
            try {
                d2 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH).parse(d);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            out2.write(d + " - " + c.user.getNume() + '\n');
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            out2.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        retragereCerere(d2);
        c.user.retragereCerere(d2);
        c.user.cereriSolutionate.add(c);
    }
    public void adaugaCerere(Cerere cerere, Utilizator user) {
        cereri.add(cerere);
    }

    public void adaugaFunctionar(FunctionarPublic functionar) {
        functionari.add(functionar);
    }
    public void retragereCerere(Date d) {
        for (Cerere c : cereri) {
            if (c.date.equals(d)) {
                cereri.remove(c);
                return;
            }
        }
    }
    public String afiseazaCereri() {

        String rez = "";
        Cerere c;
        PriorityQueue<Cerere> cereriCopy = new PriorityQueue(100, new CustomComparator2());
        while(!cereri.isEmpty()) {
            c = cereri.poll();
            cereriCopy.add(c);
            SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);
            String d = format.format(c.date);
            rez += c.prioritate + " - " + d + " - " + c.text + "\n";}
        cereri = cereriCopy;
        return rez;
    }


}
