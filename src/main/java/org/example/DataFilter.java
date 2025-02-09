package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class DataFilter {

    private int integerCount = 0;
    private int floatCount = 0;
    private int stringCount = 0;

    public void filterFiles(List<String> inputFiles) {
        for (String inputFile : inputFiles) {
            try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    processLine(line);
                }
            } catch (IOException e) {
                System.err.println("Ошибка при чтении файла: " + inputFile);
            }
        }
    }

    private void processLine(String line) {
        try {
            int integerValue = Integer.parseInt(line);
            integerCount++;
        } catch (NumberFormatException e1) {
            try {
                double floatValue = Double.parseDouble(line);
                floatCount++;
            } catch (NumberFormatException e2) {
                stringCount++;
            }
        }
    }

    public void printStatistics() {
        System.out.println("Целые числа: " + integerCount);
        System.out.println("Вещественные числа: " + floatCount);
        System.out.println("Строки: " + stringCount);
    }
}