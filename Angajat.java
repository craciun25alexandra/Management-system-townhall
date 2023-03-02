package org.example;

public class Angajat extends Utilizator {

    private String companie;

    public Angajat(String nume, String companie) {
        super(nume);
        this.companie = companie;
    }

    public String getCompanie() {
        return companie;
    }
}
