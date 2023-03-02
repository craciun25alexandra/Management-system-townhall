package org.example;

public class Elev extends Utilizator {

    private String scoala;

    public Elev(String nume, String scoala) {
        super(nume);
        this.scoala = scoala;
    }

    public String getScoala() {
        return scoala;
    }
}
