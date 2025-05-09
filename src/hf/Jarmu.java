package hf;

public abstract class Jarmu {
 protected String tipus;
 protected int gyartasiEv;
 protected String uzemanyag;
 protected String szin;
 protected String rendszam; // ID
 protected int teljesitmeny;
 protected Tulajdonos tulajdonos;
 protected int evesDij;
 protected int felevesDij;
 protected int negyedevesDij;

 public Jarmu(String tipus, int gyartasiEv, String uzemanyag, String szin,
              String rendszam, int teljesitmeny, Tulajdonos tulajdonos) {
     this.tipus = tipus;
     this.gyartasiEv = gyartasiEv;
     this.uzemanyag = uzemanyag;
     this.szin = szin;
     this.rendszam = rendszam;
     this.teljesitmeny = teljesitmeny;
     this.tulajdonos = tulajdonos;
 }

 public String getTipus() {
     return tipus;
 }

 public int getGyartasiEv() {
     return gyartasiEv;
 }

 public String getUzemanyag() {
     return uzemanyag;
 }

 public String getSzin() {
     return szin;
 }

 public String getRendszam() {
     return rendszam;
 }

 public int getTeljesitmeny() {
     return teljesitmeny;
 }

 public Tulajdonos getTulajdonos() {
     return tulajdonos;
 }

 public int getEvesDij() {
     return evesDij;
 }

 public int getFelevesDij() {
     return felevesDij;
 }

 public int getNegyedevesDij() {
     return negyedevesDij;
 }

 @Override
 public String toString() {
     return tipus + ", " + gyartasiEv + ", " + uzemanyag + ", " + szin + ", " +
            rendszam + ", " + teljesitmeny + "kW, Tulaj: " + tulajdonos.getNev();
 }
} 

