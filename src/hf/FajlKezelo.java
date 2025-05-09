package hf;

import java.io.*;
import java.util.ArrayList;

public class FajlKezelo {
 public static ArrayList<Tulajdonos> beolvasTulajdonosok(String fajlNev) {
     ArrayList<Tulajdonos> tulajdonosok = new ArrayList<>();
     try (BufferedReader br = new BufferedReader(new FileReader(fajlNev))) {
         String sor;
         while ((sor = br.readLine()) != null) {
             String[] adatok = sor.split(";");
             String nev = adatok[0];
             int szuletesiEv = Integer.parseInt(adatok[1]);
             String jogsi = adatok[2];
             boolean baleset = adatok[3].equalsIgnoreCase("igen");
             tulajdonosok.add(new Tulajdonos(nev, szuletesiEv, jogsi, baleset));
         }
     } catch (IOException e) {
         System.out.println("Hiba a tulajdonosok betöltésekor: " + e.getMessage());
     }
     return tulajdonosok;
 }

 public static ArrayList<Auto> beolvasAutok(String fajlNev, ArrayList<Tulajdonos> tulajdonosok) {
     ArrayList<Auto> autok = new ArrayList<>();
     try (BufferedReader br = new BufferedReader(new FileReader(fajlNev))) {
         String sor;
         while ((sor = br.readLine()) != null) {
             String[] adatok = sor.split(";");
             String tipus = adatok[0];
             int ev = Integer.parseInt(adatok[1]);
             String uzemanyag = adatok[2];
             String szin = adatok[3];
             String rendszam = adatok[4];
             int teljesitmeny = Integer.parseInt(adatok[5]);
             String tulajJogsi = adatok[6];

             Tulajdonos tulaj = keresTulajdonostJogsiAlapjan(tulajdonosok, tulajJogsi);
             if (tulaj != null) {
                 Auto auto = new Auto(tipus, ev, uzemanyag, szin, rendszam, teljesitmeny, tulaj);
                 autok.add(auto);
                 tulaj.hozzaadJarmu(auto);
             } else {
                 System.out.println("Figyelmeztetés: Nincs tulajdonos ezzel a jogsival: " + tulajJogsi);
             }
         }
     } catch (IOException e) {
         System.out.println("Hiba az autók betöltésekor: " + e.getMessage());
     }
     return autok;
 }

 public static void mentesTulajdonosok(String fajlNev, ArrayList<Tulajdonos> tulajdonosok) {
     try (BufferedWriter bw = new BufferedWriter(new FileWriter(fajlNev))) {
         for (Tulajdonos t : tulajdonosok) {
             bw.write(t.getNev() + ";" + t.getSzuletesiEv() + ";" +
                      t.getJogositvanyAzonosito() + ";" + (t.isOkozottBalesetet() ? "igen" : "nem"));
             bw.newLine();
         }
     } catch (IOException e) {
         System.out.println("Hiba a tulajdonosok mentésekor: " + e.getMessage());
     }
 }

 public static void mentesAutok(String fajlNev, ArrayList<Auto> autok) {
     try (BufferedWriter bw = new BufferedWriter(new FileWriter(fajlNev))) {
         for (Auto a : autok) {
             bw.write(a.getTipus() + ";" + a.getGyartasiEv() + ";" + a.getUzemanyag() + ";" +
                      a.getSzin() + ";" + a.getRendszam() + ";" + a.getTeljesitmeny() + ";" +
                      a.getTulajdonos().getJogositvanyAzonosito());
             bw.newLine();
         }
     } catch (IOException e) {
         System.out.println("Hiba az autók mentésekor: " + e.getMessage());
     }
 }

 private static Tulajdonos keresTulajdonostJogsiAlapjan(ArrayList<Tulajdonos> lista, String jogsi) {
     for (Tulajdonos t : lista) {
         if (t.getJogositvanyAzonosito().equals(jogsi)) {
             return t;
         }
     }
     return null;
 }
}

