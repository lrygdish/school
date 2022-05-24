package stromy;

public class Strom {
 Vrchol koren;
 
 public Strom() {
   koren = null;  
 }
 
 public void Vloz(int ihodnota) {
  if (koren==null)  {
   koren = new Vrchol(ihodnota, null, null);
  } else {
   Zarad(koren,ihodnota);
  }
 }
 
 public void Zarad(Vrchol ivrchol, int ihodnota) {
 Vrchol pom;
  if (ivrchol.hodnota < ihodnota)  {
   if (ivrchol.pravy == null) {
     pom = new Vrchol(ihodnota,null,null);
     ivrchol.pravy = pom;
   } else Zarad(ivrchol.pravy,ihodnota);   
  } else { //ivrchol.hodnota >= ihodnota
   if (ivrchol.lavy == null) {
     pom = new Vrchol(ihodnota,null,null);
     ivrchol.lavy = pom;
   } else Zarad(ivrchol.lavy,ihodnota);     
  }
 }
 
public void Preorder(Vrchol ivrchol) {
  System.out.println(ivrchol.hodnota);  
  if ((ivrchol.lavy) != null) Preorder(ivrchol.lavy);
  if ((ivrchol.pravy) != null) Preorder(ivrchol.pravy);
}
 
public void HladajPreorder(Vrchol ivrchol,int ihladana) {
  if (ivrchol.hodnota == ihladana) System.out.println("nasiel "+ihladana);  
  if ((ivrchol.lavy) != null) HladajPreorder(ivrchol.lavy, ihladana);
  if ((ivrchol.pravy) != null) HladajPreorder(ivrchol.pravy,ihladana);
}

public void HladajInorder(Vrchol ivrchol,int ihladana) {
  if ((ivrchol.lavy) != null) HladajInorder(ivrchol.lavy, ihladana);
  if (ivrchol.hodnota == ihladana) System.out.println("nasiel "+ihladana);  
  if ((ivrchol.pravy) != null) HladajInorder(ivrchol.pravy,ihladana);
}

public void HladajPostorder(Vrchol ivrchol,int ihladana) {
  if ((ivrchol.lavy) != null) HladajPostorder(ivrchol.lavy, ihladana);
  if ((ivrchol.pravy) != null) HladajPostorder(ivrchol.pravy,ihladana);
  if (ivrchol.hodnota == ihladana) System.out.println("nasiel "+ihladana);  
}
}
