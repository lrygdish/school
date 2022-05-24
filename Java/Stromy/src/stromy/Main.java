package stromy;

public class Main {

    public static void main(String[] args) {
     Strom mojStrom = new Strom();
     mojStrom.Vloz(10);
     mojStrom.Vloz(5);
     mojStrom.Vloz(2);
     mojStrom.Vloz(7);
     mojStrom.Vloz(15);
     mojStrom.HladajPreorder(mojStrom.koren,5);
    }

}
