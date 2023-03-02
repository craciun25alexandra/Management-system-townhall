package org.example;

public class EntitateJuridica extends Utilizator {

    private String reprezentant;

    public EntitateJuridica(String companie, String reprezentant) {
        super(companie);
        this.reprezentant = reprezentant;
    }

    public String getReprezentant() {
        return reprezentant;
    }

}
