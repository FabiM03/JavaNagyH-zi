package hf;

import java.util.*;

//Egy új lista létrehozása a paraméterben kapott listából, ha az adott érték, amit talál a program nem egyezik meg a keresettel, akkor kiveszi azt a listából (Auto)
public class AdatKezelo {
    public static ArrayList<Auto> szuresAutok(ArrayList<Auto> autok, Map<String, String> feltetelek) {
        ArrayList<Auto> eredmeny = new ArrayList<>(autok);
        for (Map.Entry<String, String> feltetel : feltetelek.entrySet()) {
            String kulcs = feltetel.getKey();
            String ertek = feltetel.getValue().toLowerCase();
            eredmeny.removeIf(auto -> {
                return switch (kulcs) {
                    case "Üzemanyag" -> !auto.getUzemanyag().equalsIgnoreCase(ertek);
                    case "Szín" -> !auto.getSzin().equalsIgnoreCase(ertek);
                    case "Évjárat" -> auto.getGyartasiEv() != Integer.parseInt(ertek);
                    case "Rendszám" -> !auto.getRendszam().toLowerCase().contains(ertek);
                    default -> true;
                };
            });
        }
        return eredmeny;
    }

    //A praméterként kapott lista a paraméterként kapott kritérium alapján történő rendezése (Auto)
    public static void rendezAutok(ArrayList<Auto> autok, String kriterium) {
        switch (kriterium) {
            case "Típus" -> autok.sort(Comparator.comparing(auto -> auto.getTipus()));
            case "Rendszám" -> autok.sort(Comparator.comparing(auto -> auto.getRendszam()));
            case "Évjárat" -> autok.sort(Comparator.comparingInt(auto -> auto.getGyartasiEv()));
            default -> System.out.println("Nincs ilyen rendezési szempont: " + kriterium);
        }
    }
    
  //Egy új lista létrehozása a paraméterben kapott listából, ha az adott érték, amit talál a program nem egyezik meg a keresettel, akkor kiveszi azt a listából (Tulajdonos)
    public static ArrayList<Tulajdonos> szuresTulajdonosok(ArrayList<Tulajdonos> tulajdonosok, Map<String, String> feltetelek) {
        ArrayList<Tulajdonos> eredmeny = new ArrayList<>(tulajdonosok);
        for (Map.Entry<String, String> feltetel : feltetelek.entrySet()) {
            String kulcs = feltetel.getKey();
            String ertek = feltetel.getValue().toLowerCase();
            eredmeny.removeIf(t -> {
                return switch (kulcs) {
                    case "Név" -> !t.getNev().toLowerCase().contains(ertek);
                    case "Baleset" -> (ertek.equals("igen") != t.isOkozottBalesetet());
                    default -> true;
                };
            });
        }
        return eredmeny;
    }
    
    //A praméterként kapott lista a paraméterként kapott kritérium alapján történő rendezése (Tulajdonos)
    public static void rendezTulajdonosok(ArrayList<Tulajdonos> tulajdonosok, String kriterium) {
        switch (kriterium) {
            case "Név" -> tulajdonosok.sort(Comparator.comparing(Tulajdonos::getNev));
            case "Születés" -> tulajdonosok.sort(Comparator.comparingInt(Tulajdonos::getSzuletesiEv));
            default -> System.out.println("Ismeretlen rendezési szempont: " + kriterium);
        }
    }
}