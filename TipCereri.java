package org.example;

public enum TipCereri {
    BULETIN("inlocuire buletin"), SALARIU("inregistrare venit salarial"),
    SOFER("inlocuire carnet de sofer"), ELEV("inlocuire carnet de elev"),
    CONSTITUTIV("creare act constitutiv"), AUTORIZATIE("reinnoire autorizatie"),
    PENSIE("inregistrare cupoane de pensie")
    ;
    private String cerere;
    TipCereri(String cerere) {
        this.cerere = cerere;
    }

    public String getCerere() {
        return cerere;
    }
}
