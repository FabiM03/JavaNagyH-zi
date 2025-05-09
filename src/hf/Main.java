package hf;

public class Main {
 public static void main(String[] args) {
	 //Létrejön egy új parancsértelmező, amely paraméternek megkapja a két szükséges fájlt
     ParancsErtelmezo pe = new ParancsErtelmezo("tulajdonosok.txt", "autok.txt");
     
     //Elindul a parancsok fogadása
     pe.indit();
 }
}
