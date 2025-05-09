package hf;

public class Auto extends Jarmu {

 public Auto(String tipus, int gyartasiEv, String uzemanyag, String szin,
             String rendszam, int teljesitmeny, Tulajdonos tulajdonos) {
     super(tipus, gyartasiEv, uzemanyag, szin, rendszam, teljesitmeny, tulajdonos);
     szamolBiztositas();
 }
 
 protected void szamolBiztositas() {
     int korDij;
     
     if (this.getGyartasiEv() <= 1980) {
    	 korDij = 15000;
     }else {
    	 korDij = (this.getGyartasiEv() - 1980) * 500 + 15000;
     }
     
     int teljesitmenyDij;
     
     if (this.getTeljesitmeny() <= 55) {
    	 teljesitmenyDij = 20000;
     } else {
    	 teljesitmenyDij = (this.getTeljesitmeny() - 55) * 650 + 20000;
     }
     
     int osszeg = korDij + teljesitmenyDij;
     
     if (tulajdonos.isOkozottBalesetet()) {
         osszeg = (int)(osszeg * 1.4);
     }

     this.evesDij = osszeg;
     this.felevesDij = (int)(osszeg * 0.6);
     this.negyedevesDij = (int)(osszeg * 0.4);
 }

 @Override
 public String toString() {
     return super.toString() + ", Éves díj: " + evesDij + " Ft" + ", Féléves díj: " + felevesDij + " Ft" + ", Negyedéves díj: " + negyedevesDij + " Ft";
 	 }
} 
