package sem_praca;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class pavuk {

    Scanner vstup = new Scanner(System.in);
    private char smer = ' ';
                                                                                /* !!! NASTAVENIE ARENY [zivoty - znak zivotov; prekazka - znak prekazky;
                                                                                okraje - znak okrajov; pavuk - znak pavuka]*/
    
    private char[][] arena = new char[10][10];                                  //NASTAVENIE VELKOSTI ARENY
    private char zivoty = 'O';
    private char prekazka = '-';
    private char okraje = '#';
    private char pavuk = 'X';
    
                                                                                // !!! KONIEC NASTAVENI
    private int pavukY;
    private int pavukX;
    private int zivotyNUM = 3;                                                  //POCET ZIVOTOV
    private boolean volna = false;
    private boolean vonkuZPola = false;
    private boolean mrtvy = false;
    private String smerSTR = "";
    
    pavuk() {
        
        try {
                FileWriter fwriter = new FileWriter("bludisko.txt");
                BufferedWriter bwriter = new BufferedWriter(fwriter);

                for(int i = 0; i < arena.length; i++) {                         //GENERACIA ARENY
                    for(int x = 0; x < arena[i].length; x++) {
                        if(i == 0 || i == arena.length - 1)    arena[i][x] = okraje;
                        if(x != 0 && x != arena[i].length - 1 && i != 0 && i != 
                                arena.length - 1)    arena[i][x] = ' ';
                        arena[i][0] = okraje;
                        arena[i][arena[i].length - 1] = okraje;
                    }
                }
                for(int i = 1; i < arena.length - 1; i++) {                     //GENERACIA PREKAZOK
                    int prekazky = 1 + (int) (Math.random() * 
                            ((arena[0].length / 3) + (arena[0].length % 3)));
                    int[] prekPos = new int[prekazky];
                    for(int l = 0; l < prekPos.length; l++) {
                        prekPos[l] = (int) (Math.random() * 
                                (arena[0].length - 2)) + 1;
                    }
                    for(int b = 0; b < prekPos.length; b++) { 
                        arena[i][prekPos[b]] = prekazka;
                    }
                }
                for(int i = 0; i < arena.length; i++) {                         //ZAPIS ARENY DO "bludisko.txt"
                    for(int x = 0; x < arena[i].length; x++) {
                        bwriter.write(arena[i][x]);
                    }
                    bwriter.newLine();
                    bwriter.flush();
                }
                while(!volna) {                                                 //JE POLICKO PRE PAVUKA VOLNE?
                    pavukPOS();
                    if(arena[pavukY][pavukX] != prekazka)    volna = true;    
                }
                if(volna)   arena[pavukY][pavukX] = pavuk;                      //AK JE POLICKO VOLNE TAK GENERACIA PAVUKA
                fwriter = new FileWriter("bludisko_s_priserou.txt");            //NOVY SUBOR "bludisko_s_priserou.txt"
                bwriter = new BufferedWriter(fwriter);
                for(int i = 0; i < arena.length; i++) {                         //ZAPIS ARENY S PAVUKOM DO "bludisko_s_priserou.txt"
                    for(int x = 0; x < arena[i].length; x++) {
                        bwriter.write(arena[i][x]);
                    }
                    bwriter.newLine();
                    bwriter.flush();
                }
                vypis_bludisko_na_konzolu();
                while(true) {                                                   //OVLADANIE WASD
                    smerSTR = vstup.nextLine();
                    smer = smerSTR.charAt(0);
                    if(smer == 'w') hore();
                    if(smer == 'a') dolava();
                    if(smer == 's') dole();
                    if(smer == 'd') doprava();
                    if(smer != 'w' && smer != 'a' && smer != 's' && smer != 'd')    
                        System.out.println("NESPRAVNY SMER");
                }
            }
            catch(IOException e) {
                System.out.println(e.getMessage());
        }
    }
    void vypis_bludisko_na_konzolu() {                                          //VYPIS STAVU ARENY DO KONZOLY
        if(!mrtvy) {
            for(int i = 0; i < arena.length; i++) {
                for(int x = 0; x < arena[i].length; x++)    
                    System.out.print(arena[i][x]);
                System.out.println();
            }
            System.out.print("ZIVOTY: ");
            for(int i = zivotyNUM; i > 0; i--) System.out.print(zivoty);        //VYPIS POCTU ZIVOTOV
            System.out.println("\n");
            if(vonkuZPola)  System.out.println("PAVUK NEMOZE IST VON Z ARENY!!!");
            vonkuZPola = false;
        }
        else {    
            System.out.println("PAVUK ZOMREL!!! (0 ZIVOTOV)");
            System.exit(0);
        }
                
    }
    private void pavukPOS() {                                                   //GENERACIA RANDOM POZICII PRE PAVUKA
        pavukY = 1 + (int) (Math.random() * (arena.length - 2));
        pavukX = 1 + (int) (Math.random() * (arena[0].length - 2));
    }
    void hore() {                                                               //POHYB HORE
        pavukY--;
        if(pavukY < 1) {
            pavukY++;
            vonkuZPola = true;
        }
        if(arena[pavukY][pavukX] == prekazka) {                                 //AK PAVUK NARAZI DO PREKAZKY
            pavukY++;
            System.out.println("PAVUK NARAZIL DO PREKAZDY NAD NIM!!! (-1 ZIVOT)");
            zivotyNUM--;
            if(zivotyNUM == 0)  mrtvy = true;
        }
        else
        arena[pavukY + 1][pavukX] = ' ';
        arena[pavukY][pavukX] = pavuk;
        vypis_bludisko_na_konzolu();
    }
    void dole() {                                                               //POHYB DOLE
        pavukY++;
        if(pavukY > (arena[0].length - 2)) {
            pavukY--;
            vonkuZPola = true;
        }
        if(arena[pavukY][pavukX] == prekazka) {                                 //AK PAVUK NARAZI DO PREKAZKY
            pavukY--;
            System.out.println("PAVUK NARAZIL DO PREKAZDY POD NIM!!! (-1 ZIVOT)");
            zivotyNUM--;
            if(zivotyNUM == 0)  mrtvy = true;
        }
        else
        arena[pavukY - 1][pavukX] = ' ';
        arena[pavukY][pavukX] = pavuk;
        vypis_bludisko_na_konzolu();
    }
    void dolava() {                                                             //POHYB DOLAVA
        pavukX--;
        if(pavukX < 1) {
            pavukX++;
            vonkuZPola = true;
        }
        if(arena[pavukY][pavukX] == prekazka) {                                 //AK PAVUK NARAZI DO PREKAZKY
            pavukX++;
            System.out.println("PAVUK NARAZIL DO PREKAZDY NALAVO OD NEHO!!! (-1 ZIVOT)");
            zivotyNUM--;
            if(zivotyNUM == 0)  mrtvy = true;
        }
        else
        arena[pavukY][pavukX + 1] = ' ';
        arena[pavukY][pavukX] = pavuk;
        vypis_bludisko_na_konzolu();
    }
    void doprava() {                                                            //POHYB DOPRAVA
        pavukX++;
        if(pavukX > (arena[0].length - 2)) {
            pavukX--;
            vonkuZPola = true;
        }
        if(arena[pavukY][pavukX] == prekazka) {                                 //AK PAVUK NARAZI DO PREKAZKY
            pavukX--;
            System.out.println("PAVUK NARAZIL DO PREKAZDY NAPRAVO OD NEHO!!! (-1 ZIVOT)");
            zivotyNUM--;
            if(zivotyNUM == 0)  mrtvy = true;
        }
        else
        arena[pavukY][pavukX - 1] = ' ';
        arena[pavukY][pavukX] = pavuk;
        vypis_bludisko_na_konzolu();
    }
    
}