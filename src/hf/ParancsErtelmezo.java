package hf;

import java.util.*;

public class ParancsErtelmezo{
	
	//Fájlok nevének és tartalmának a tárolása
    private ArrayList<Tulajdonos> tulajdonosok;
    private ArrayList<Auto> autok;
    private final String tulajdonosFajl;
    private final String autoFajl;
    
    //Konstruktor (értékadás)
    public ParancsErtelmezo(String tulajdonosFajl, String autoFajl) {
        this.tulajdonosFajl = tulajdonosFajl;
        this.autoFajl = autoFajl;
        this.tulajdonosok = FajlKezelo.beolvasTulajdonosok(tulajdonosFajl);
        this.autok = FajlKezelo.beolvasAutok(autoFajl, tulajdonosok);
    }
    
    //Parancsok felismerése, exitre megszakad a végtelen ciklus, itt hívódnak meg a parancsokhoz tartozó metódusok
    public void indit() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Parancsok: Filter, Add, Rm, exit");
        while (true) {
            System.out.print("Parancs: ");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("exit")) {
                kilepes();
                return;
            } else if (input.toLowerCase().startsWith("filter")) {
                feldolgozFilter(input);
            } else if (input.toLowerCase().startsWith("add")) {
                feldolgozAdd(scanner);
            } else if (input.toLowerCase().startsWith("rm")) {
                feldolgozRm(input);
            } else {
                System.out.println("Ismeretlen parancs");
            }
        }
    }
    
    //Filter parancs
    private void feldolgozFilter(String parancs) {
        try {
        	//Parancs részeinek a tárolása
            String[] reszek = parancs.split(" ");
            
            //LinkedHashMap használata a feltételekhez (rugalmas és így nem kell minden opciót definiálni)
            Map<String, String> feltetelek = new LinkedHashMap<>();
            
            //Rendezési paraméter
            String sortParam = null;
            
            
            for (int i = 1; i < reszek.length - 1; i += 2) {
                if (reszek[i].equalsIgnoreCase("Sort")) {
                	//ha van Sort, akkor annak a paraméterét külön tároljuk
                    sortParam = reszek[i + 1];
                    break;
                }
                feltetelek.put(reszek[i], reszek[i + 1]);
            }
            
            //Tényleges szűrés, szükség esetén rendezés
            if (feltetelek.containsKey("Üzemanyag") || feltetelek.containsKey("Szín") || feltetelek.containsKey("Évjárat") || feltetelek.containsKey("Rendszám")) {
                ArrayList<Auto> szurt = AdatKezelo.szuresAutok(autok, feltetelek);
                if (sortParam != null) AdatKezelo.rendezAutok(szurt, sortParam);
                szurt.forEach(System.out::println);
            } else if (feltetelek.containsKey("Név") || feltetelek.containsKey("Baleset")) {
                ArrayList<Tulajdonos> szurt = AdatKezelo.szuresTulajdonosok(tulajdonosok, feltetelek);
                if (sortParam != null) AdatKezelo.rendezTulajdonosok(szurt, sortParam);
                
                //Azért Object, mivel az itt hivatkozott ArrayList lehet Auto, vagy Tulajdonos típusú is
                for (Object elem : szurt) {
                    System.out.println(elem);
                }
            } else {
                System.out.println("Ismeretlen szűrési feltétel.");
            }
            
        } catch (Exception e) {
            System.out.println("Hibás Filter parancs formátum.");
        }
    }
    
    //Add parancs
    private void feldolgozAdd(Scanner sc) {
        try {
        	
        	//Adatok bekérése szépen sorban (itt van eltérés a specifikációhoz képest)
            System.out.println("Gépjármű adatok:");
            
            System.out.print("Rendszám: ");
            String rendszam = sc.nextLine();
            
            //Ellenőrizzük, hogy az adott rendszám benne van-e a rendszerben, ha igen, akkor nem is megyünk tovább
            for (Auto a : autok) {
                if (a.getRendszam().equalsIgnoreCase(rendszam)) {
                	
                    System.out.println("Már létezik ilyen rendszámú autó.");
                    return;
                }
            }
            
            System.out.print("Típus: ");
            String tipus = sc.nextLine();
            System.out.print("Gyártási év: ");
            int ev = Integer.parseInt(sc.nextLine());
            System.out.print("Üzemanyag: ");
            String uzemanyag = sc.nextLine();
            System.out.print("Szín: ");
            String szin = sc.nextLine();
            System.out.print("Teljesítmény: ");
            int teljesitmeny = Integer.parseInt(sc.nextLine());

            System.out.println("Tulajdonos adatok:");
            System.out.print("Jogsi: ");
            String jogsi = sc.nextLine();
            Tulajdonos tulaj = keresTulajdonost(jogsi);
            
            //Ha a jogsi száma már benne van a rendszerben, a többi  adatot logikusan már nem kell bekérni
            if (tulaj == null) {
                System.out.print("Név: ");
                String nev = sc.nextLine();
                System.out.print("Születés: ");
                int szulEv = Integer.parseInt(sc.nextLine());
                System.out.print("Baleset (igen/nem): ");
                boolean baleset = sc.nextLine().equalsIgnoreCase("igen");
                tulaj = new Tulajdonos(nev, szulEv, jogsi, baleset);
                tulajdonosok.add(tulaj);
            }
            
            //Tényleges hozzáadás
            Auto uj = new Auto(tipus, ev, uzemanyag, szin, rendszam, teljesitmeny, tulaj);
            autok.add(uj);
            tulaj.hozzaadJarmu(uj);
            mentes();
            System.out.println("Jármű hozzáadva.");
        } catch (Exception e) {
            System.out.println("Hibás adatbevitel. A hozzáadás sikertelen volt.");
        }
    }
    
    //Rm parancs
    private void feldolgozRm(String parancs) {
        String[] reszek = parancs.split(" ");
        
        //A program ellenőrzi, hogy jól lett-e paraméterezve a parancs
        if (reszek.length != 3) {
            System.out.println("Hibás Rm parancs formátum.");
            return;
        }
        String tipus = reszek[1];
        String azonosito = reszek[2];
        
        //Ha járművet akarunk törölni
        if (tipus.equalsIgnoreCase("G")) {
            Auto torlendo = null;
            for (Auto a : autok) {
                if (a.getRendszam().equalsIgnoreCase(azonosito)) {
                    torlendo = a;
                    break;
                }
            }
            
            if (torlendo != null) {
                torlendo.getTulajdonos().torolJarmu(torlendo);
                autok.remove(torlendo);
                if (torlendo.getTulajdonos().getJarmuvek().isEmpty()) {
                    tulajdonosok.remove(torlendo.getTulajdonos());
                }
                mentes();
                System.out.println("Jármű törölve.");
            } else {
                System.out.println("Nem található ilyen rendszámú autó.");
            }
            
            //Ha tulajdonost akarunk törölni
        } else if (tipus.equalsIgnoreCase("T")) {
            Tulajdonos torlendo = keresTulajdonost(azonosito);
            if (torlendo != null) {
                autok.removeIf(a -> a.getTulajdonos().equals(torlendo));
                tulajdonosok.remove(torlendo);
                mentes();
                System.out.println("Tulajdonos és járművei törölve.");
            } else {
                System.out.println("Nem található ilyen jogosítvány azonosítóval tulajdonos.");
            }
        } else {
            System.out.println("Ismeretlen típus. Használat: Rm G <rendszám> vagy Rm T <jogsi azonosító>");
        }
    }
    
    //Tulajdonos kikeresése és visszaadása jogosítvány szám alapján
    private Tulajdonos keresTulajdonost(String jogsi) {
        for (Tulajdonos t : tulajdonosok) {
            if (t.getJogositvanyAzonosito().equalsIgnoreCase(jogsi)) {
                return t;
            }
        }
        return null;
    }
    
    //Adatok mentése
    private void mentes() {
        FajlKezelo.mentesTulajdonosok(tulajdonosFajl, tulajdonosok);
        FajlKezelo.mentesAutok(autoFajl, autok);
    }
    
    //Kilépés
    private void kilepes() {
        mentes();
        System.out.println("Kilépés, adatok mentve.");
    }
}