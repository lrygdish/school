package com.lrygdish;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	    Scanner sc = new Scanner(System.in);
	    boolean exit = false;
	    while(!exit) {
            System.out.println("Kolko goldov chces z itemu?");
            /*if(sc.nextLine().equals("q") || sc.nextLine().equals("Q")) {
                exit = !exit;
                break;
            }*/
            double gold = (double) Math.round(((sc.nextDouble() * 100) / 95) * 100) / 100;
            double silver = Double.parseDouble(String.valueOf(gold).substring(String.valueOf(gold).length() - 2));
            if (gold <= 0)
                System.out.println("Zisk nemoze byt mensi ako 0g\n");
            else
                System.out.println("Item predaj za -> " + (int) gold + "g " + (int) silver + ("s\n"));
        }
    }
}
