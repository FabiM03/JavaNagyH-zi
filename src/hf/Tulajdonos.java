package hf;

import java.util.ArrayList;

public class Tulajdonos extends Szemely {
    private String jogositvanyAzonosito;
    private boolean okozottBalesetet;
    private ArrayList<Auto> jarmuvek;

    public Tulajdonos(String nev, int szuletesiEv, String jogositvanyAzonosito, boolean okozottBalesetet) {
        super(nev, szuletesiEv);
        this.jogositvanyAzonosito = jogositvanyAzonosito;
        this.okozottBalesetet = okozottBalesetet;
        this.jarmuvek = new ArrayList<>();
    }

    public String getJogositvanyAzonosito() {
        return jogositvanyAzonosito;
    }

    public boolean isOkozottBalesetet() {
        return okozottBalesetet;
    }

    public ArrayList<Auto> getJarmuvek() {
        return jarmuvek;
    }

    public void hozzaadJarmu(Auto auto) {
        jarmuvek.add(auto);
    }

    public void torolJarmu(Auto auto) {
        jarmuvek.remove(auto);
    }

    @Override
    public String toString() {
        return "Név: " + nev + ", Születési év: " + szuletesiEv +
               ", Jogsi: " + jogositvanyAzonosito + ", Baleset: " + (okozottBalesetet ? "igen" : "nem") +
               ", Járművek száma: " + jarmuvek.size();
    }
} 

