package com.practise;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.*;

public class Main {

    public static void main(String[] args) {
	    int[][] table = new int[9][9];
        int[][] steps = new int[4][3];

        Path workDir = FileSystems.getDefault().getPath("Data");

        System.out.print("1. feladat\nAdja meg a bemeneti fájl nevét! ");
        Scanner in = new Scanner(System.in);
        String fileName = in.nextLine().trim();
        System.out.println("Adja meg egy sor számát! ");
        int row = in.nextInt() - 1; in.nextLine();
        System.out.println("Adja meg egy oszlop számát! ");
        int column = in.nextInt() - 1; in.nextLine();

        in.close();

        Path filePath = workDir.resolve(fileName);

        List<String> inputData = new ArrayList<>();

        try(Scanner input = new Scanner(filePath, StandardCharsets.UTF_8)) {
            for(int h=0; h<table.length; h++) {
                inputData.add(input.nextLine().replaceAll(" ", ""));
            }
            String line;
            for(int h=0; h<4; h++) {
                line = input.nextLine().replaceAll(" ", "");
                for(int w=0; w<3; w++) {
                    steps[h][w] = Integer.parseInt(String.valueOf(line.charAt(w)));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(int h=0; h<table.length; h++) {
            for(int w=0; w<table[0].length; w++) {
                table[h][w] = Integer.parseInt(String.valueOf(inputData.get(h).charAt(w)));
            }
        }


        // 3. feladat
        // a)
        System.out.println("3. feladat");
        if(table[column][row] == 0) {
            System.out.println("Az adott helyet még nem töltötték ki");
        } else {
            System.out.printf("Az adott helyen szereplő szám: %d\n", table[column][row]);
        }
        // b)
        System.out.printf("A hely a(z) %d résztáblázathoz tartozik.\n\n", getSmallTableIndex(column, row));

        // 4. feladat
        int count = 0;
        for(int h=0; h < table.length; h++) {
            for(int w=0; w<table[0].length; w++) {
                if(table[h][w] == 0) {
                    count++;
                }
            }
        }

        System.out.printf("4.feladat\nAz üres helyek aránya: %.1f\n\n", (float)count / (table.length * table[0].length) * 100);

        System.out.println("5. feladat");


        for(int[] i : steps) {
            System.out.printf("A kiválasztott sor: %d oszlop: %d a szám: %d\n", i[1], i[2], i[0]);

            int x = i[1] - 1;
            int y = i[2] - 1;
            int num = i[0];


            if(!isValid(table, y, x)) {
                System.out.println("Érvénytelen koordináta.");
                continue;
            }
            if(!isSpaceEmpty(table, y, x)) {
                System.out.println("A helyet már kitöltötték.");
                continue;
            }
            if(alreadyInSmallTable(table, y, x, num)) {
                System.out.println("Az adott résztáblázatban már szerepel a szám");
                continue;
            }
            if(isPresentInRow(table, y, num)) {
                System.out.println("Az adott sorban már szerepel a szám");
                continue;
            }
            if(isPresentInColumn(table, x, num)) {
                System.out.println("Az adott oszlopban már szerepel a szám");
                continue;
            }


            System.out.println("A lépés megtehető.");
        }
    }

    private static void printTable(int[][] table) {
        for(int h=0; h<table.length; h++) {
            for(int w=0;w<table[0].length; w++) {
                System.out.print(table[h][w]);
            }
            System.out.println();
        }
    }

    private static int getSmallTableIndex(int h, int w) {
        return (h / 3) * 3 + w / 3 + 1;
    }

    private static int[] getSmallTable(int h, int w) {
        int tableID = getSmallTableIndex(h, w) - 1;

        int c = (tableID / 3) * 3;
        int r = (tableID % 3) * 3;

        return new int[] {r, c};
    }

    private static boolean isSpaceEmpty(int[][] table, int h, int w) {
        return table[h][w] == 0;
    }

    private static boolean isPresentInRow(int[][] table, int h, int num) {
        for(int i = 0; i<table[0].length; i++) {
            if(table[h][i] == num) return true;
        }

        return false;
    }

    private static boolean isPresentInColumn(int[][] table, int w, int num) {
        for(int i = 0; i<table.length; i++) {
            if(table[i][w] == num) return true;
        }

        return false;
    }

    private static boolean isValid(int[][] table, int h, int w) {
        return w >= 0 && w < table.length && h >= 0 && h < table[0].length;
    }

    private static boolean alreadyInSmallTable(int[][] table, int h, int w, int num) {
        int[] smallTable = getSmallTable(h, w);

        Set<Integer> set = new HashSet<>();

        for(int i=smallTable[1]; i<smallTable[1]+3; i++) {
            for(int j=smallTable[0]; j<smallTable[0]+3; j++) {
                if(set.contains(num)) return true;
                else if(isSpaceEmpty(table, i, j)) set.add(table[i][j]);
            }
        }

        return false;
    }


}
