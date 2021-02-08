package com.company;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Main {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Enter file name");
            return;
        }
        String filename = args[0];

        try {
            FileInputStream input = new FileInputStream(filename);
            GetterWordStatistic fileStat = new GetterWordStatistic(input);
            fileStat.calcStatistic();
            FileOutputStream fileOutputStream = new FileOutputStream("output.csv");
            fileStat.toCsv(fileOutputStream);
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }

    }
}
