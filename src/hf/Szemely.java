package hf;

//Szemely.java (absztrakt osztály a tulajdonoshoz)
public abstract class Szemely {
 protected String nev;
 protected int szuletesiEv;

 public Szemely(String nev, int szuletesiEv) {
     this.nev = formatalNev(nev);
     this.szuletesiEv = szuletesiEv;
 }

 public String getNev() {
     return nev;
 }

 public int getSzuletesiEv() {
     return szuletesiEv;
 }

 protected String formatalNev(String nev) {
     if (nev == null || nev.isEmpty()) return nev;
     String[] szavak = nev.split(" ");
     StringBuilder sb = new StringBuilder();
     for (String szo : szavak) {
         if (!szo.isEmpty()) {
             sb.append(Character.toUpperCase(szo.charAt(0)))
               .append(szo.substring(1).toLowerCase())
               .append(" ");
         }
     }
     return sb.toString().trim();
 }
} 

