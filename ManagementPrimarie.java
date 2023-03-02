package org.example;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

class Cerere {
    String text;
    Date date;
    int prioritate;
    Utilizator user;
    TipCereri tip;

    public Cerere(Date date, int prioritate, Utilizator user, TipCereri tip, String text) {
        this.date = date;
        this.prioritate = prioritate;
        this.user = user;
        this.tip = tip;
        this.text = text;
    }
}
public class ManagementPrimarie {

    public static void main(String[] args) throws IOException, ParseException {
        Scanner myReaderUsers;
        myReaderUsers = new Scanner(new File("src/main/resources/input/" + args[0]));
        FileWriter out = new FileWriter("src/main/resources/output/" + args[0], true);
        List<Utilizator> utilizatori = new ArrayList<>();
        Birou<Persoana> birouPersoane = new Birou<>();
        Birou<EntitateJuridica> birouEntitati = new Birou<>();
        Birou<Angajat> birouAngajati = new Birou<>();
        Birou<Pensionar> birouPensionari = new Birou<>();
        Birou<Elev> birouElevi = new Birou<>();
        while (myReaderUsers.hasNextLine()) {
            String commandline = myReaderUsers.nextLine();
            String[] command = commandline.split("; ");
            if (command[0].equals("adauga_utilizator")) {

                if (command[1].equals("elev")) {
                    Elev user = new Elev(command[2], command[3]);
                    utilizatori.add(user);
                } else if (command[1].equals("pensionar")) {
                    Pensionar user = new Pensionar(command[2]);
                    utilizatori.add(user);
                } else if (command[1].equals("angajat")) {
                    Angajat user = new Angajat(command[2], command[3]);
                    utilizatori.add(user);
                } else if (command[1].equals("entitate juridica")) {
                    EntitateJuridica user = new EntitateJuridica(command[2], command[3]);
                    utilizatori.add(user);
                } else if (command[1].equals("persoana")) {
                    Persoana user = new Persoana(command[2]);
                    utilizatori.add(user);
                }
            } else if (command[0].equals("cerere_noua")) {
                Date data = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH).parse(command[3]);
                TipCereri tip;
                if (command[2].equals("inlocuire buletin"))
                    tip = TipCereri.BULETIN;
                else if (command[2].equals("inlocuire carnet de sofer"))
                    tip = TipCereri.SOFER;
                else if (command[2].equals("creare act constitutiv"))
                    tip = TipCereri.CONSTITUTIV;
                else if (command[2].equals("inregistrare cupoane de pensie"))
                    tip = TipCereri.PENSIE;
                else if (command[2].equals("inregistrare venit salarial"))
                    tip = TipCereri.SALARIU;
                else if (command[2].equals("inlocuire carnet de elev"))
                    tip = TipCereri.ELEV;
                else
                    tip = TipCereri.AUTORIZATIE;
                for (Utilizator user : utilizatori) {
                    if (user.getNume().equals(command[1])) {
                        try {
                            String text = user.scriereCerere(tip);
                            Cerere cerere = new Cerere(data, Integer.parseInt(command[4]), user, tip, text);
                            user.cereriAsteptare.add(cerere);
                            if (user instanceof Persoana) {
                                birouPersoane.adaugaCerere(cerere, user);
                            } else if (user instanceof EntitateJuridica) {
                                birouEntitati.adaugaCerere(cerere, user);
                            } else if (user instanceof Angajat) {
                                birouAngajati.adaugaCerere(cerere, user);
                            } else if (user instanceof Pensionar) {
                                birouPensionari.adaugaCerere(cerere, user);
                            } else if (user instanceof Elev) {
                                birouElevi.adaugaCerere(cerere, user);
                            }
                        } catch (IllegalArgumentException e) {
                            out.write(e.getMessage());
                            out.write('\n');
                        }
                        break;

                    }
                }
            } else if (command[0].equals("afiseaza_cereri_in_asteptare")) {
                out.write(command[1] + " - " + "cereri in asteptare:" + '\n');
                for (Utilizator user : utilizatori) {
                    if (user.getNume().equals(command[1])) {
                        out.write(user.afisareCereriAsteptare());
                        break;
                    }
                }

            } else if (command[0].equals("retrage_cerere")) {
                Date d = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH).parse(command[2]);
                for (Utilizator user : utilizatori) {
                    if (user.getNume().equals(command[1])) {
                        user.retragereCerere(d);
                        if (user instanceof Elev)
                            birouElevi.retragereCerere(d);
                        else if (user instanceof Pensionar)
                            birouPensionari.retragereCerere(d);
                        else if (user instanceof Angajat)
                            birouAngajati.retragereCerere(d);
                        else if (user instanceof EntitateJuridica)
                            birouEntitati.retragereCerere(d);
                        else if (user instanceof Persoana)
                            birouPersoane.retragereCerere(d);
                    }
                }
            } else if (command[0].equals("afiseaza_cereri")) {
                if (command[1].equals("angajat")) {
                    out.write(command[1] + " - " + "cereri in birou:" + '\n');
                    out.write(birouAngajati.afiseazaCereri());
                } else if (command[1].equals("elev")) {
                    out.write(command[1] + " - " + "cereri in birou:" + '\n');
                    out.write(birouElevi.afiseazaCereri());
                } else if (command[1].equals("persoana")) {
                    out.write(command[1] + " - " + "cereri in birou:" + '\n');
                    out.write(birouPersoane.afiseazaCereri());
                } else if (command[1].equals("pensionar")) {
                    out.write(command[1] + " - " + "cereri in birou:" + '\n');
                    out.write(birouPensionari.afiseazaCereri());
                } else if (command[1].equals("entitate juridica")) {
                    out.write(command[1] + " - " + "cereri in birou:" + '\n');
                    out.write(birouEntitati.afiseazaCereri());
                }
            } else if (command[0].equals("adauga_functionar")) {
                if (command[1].equals("angajat")) {
                    FunctionarPublic f = new FunctionarPublic(command[2]);
                    birouAngajati.adaugaFunctionar(f);
                }
                else if(command[1].equals("elev")) {
                    FunctionarPublic f = new FunctionarPublic(command[2]);
                    birouElevi.adaugaFunctionar(f);
                }
                else if(command[1].equals("persoana")) {
                    FunctionarPublic f = new FunctionarPublic(command[2]);
                    birouPersoane.adaugaFunctionar(f);
                }
                else if(command[1].equals("pensionar")) {
                    FunctionarPublic f = new FunctionarPublic(command[2]);
                    birouPensionari.adaugaFunctionar(f);
                }
                else if(command[1].equals("entitate juridica")) {
                    FunctionarPublic f = new FunctionarPublic(command[2]);
                    birouEntitati.adaugaFunctionar(f);
                }
            }
            else if(command[0].equals("rezolva_cerere")) {
                if(command[1].equals("angajat")) {
                    for(FunctionarPublic f : birouAngajati.functionari) {
                        if(f.nume.equals(command[2])) {
                           birouAngajati.rezolvaCererea(f);
                            break;
                        }
                    }
                }
                else if(command[1].equals("elev")) {
                    for(FunctionarPublic f : birouElevi.functionari) {
                        if(f.nume.equals(command[2])) {
                            birouElevi.rezolvaCererea(f);
                            break;
                        }
                    }
                }
                else if(command[1].equals("pensionar")) {
                    for(FunctionarPublic f : birouPensionari.functionari) {
                        if(f.nume.equals(command[2])) {
                            birouPensionari.rezolvaCererea(f);
                            break;
                        }
                    }
                }
                else if(command[1].equals("persoana")) {
                    for(FunctionarPublic f : birouPersoane.functionari) {
                        if(f.nume.equals(command[2])) {
                            birouPersoane.rezolvaCererea(f);
                            break;
                        }
                    }
                }
                else if(command[1].equals("entitate juridica")) {
                    for(FunctionarPublic f : birouEntitati.functionari) {
                        if(f.nume.equals(command[2])) {
                           birouEntitati.rezolvaCererea(f);

                            break;
                        }
                    }
                }

            }
            else if(command[0].equals("afiseaza_cereri_finalizate")) {
            out.write(command[1] + " - " + "cereri in finalizate:" + '\n');
            for(Utilizator user : utilizatori) {
                if(user.getNume().equals(command[1])) {
                    out.write(user.afisareCereriSolutionate(command[1]));
                    break;
                }
            }
            }
        }
        out.close();
    }
}

