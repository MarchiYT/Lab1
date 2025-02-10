package org.example;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class DataFilter {

    private Path outputPath = Paths.get("");
    private String prefix = "";
    private boolean appendMode = false;
    private boolean fullStatistics = false;

    private int integerCount = 0;
    private int floatCount = 0;
    private int stringCount = 0;
    private int minInteger = Integer.MAX_VALUE;
    private int maxInteger = Integer.MIN_VALUE;
    private long integerSum = 0;
    private double minFloat = Double.MAX_VALUE;
    private double maxFloat = Double.MIN_VALUE;
    private double floatSum = 0;
    private int minStringLength = Integer.MAX_VALUE;
    private int maxStringLength = 0;

    public void setOutputPath(String outputPath) {
        this.outputPath = Paths.get(outputPath);
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setAppendMode(boolean appendMode) {
        this.appendMode = appendMode;
    }

    public void setFullStatistics(boolean fullStatistics) {
        this.fullStatistics = fullStatistics;
    }

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
        try {
            writeResults();
        } catch (IOException e) {
            System.err.println("Ошибка при записи результатов: " + e.getMessage());
        }
        printStatistics();
    }

    private void processLine(String line) {
        try {
            int integerValue = Integer.parseInt(line);
            integerCount++;
            integerSum += integerValue;
            minInteger = Math.min(minInteger, integerValue);
            maxInteger = Math.max(maxInteger, integerValue);
        } catch (NumberFormatException e1) {
            try {
                double floatValue = Double.parseDouble(line);
                floatCount++;
                floatSum += floatValue;
                minFloat = Math.min(minFloat, floatValue);
                maxFloat = Math.max(maxFloat, floatValue);
            } catch (NumberFormatException e2) {
                stringCount++;
                minStringLength = Math.min(minStringLength, line.length());
                maxStringLength = Math.max(maxStringLength, line.length());
            }
        }
    }

    private void writeResults() throws IOException {
        if (integerCount > 0) {
            writeToFile(prefix + "integers.txt", integerCount);
        }
        if (floatCount > 0) {
            writeToFile(prefix + "floats.txt", floatCount);
        }
        if (stringCount > 0) {
            writeToFile(prefix + "strings.txt", stringCount);
        }
    }

    private void writeToFile(String fileName, int count) throws IOException {
        Path filePath = outputPath.resolve(fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile(), appendMode))) {
            // Здесь можно добавить запись данных, если они хранятся в коллекциях
        }
    }

    public void printStatistics() {
        System.out.println("Краткая статистика:");
        System.out.println("Целые числа: " + integerCount);
        System.out.println("Вещественные числа: " + floatCount);
        System.out.println("Строки: " + stringCount);

        if (fullStatistics) {
            System.out.println("\nПолная статистика:");
            if (integerCount > 0) {
                System.out.println("Целые числа: min=" + minInteger + ", max=" + maxInteger +
                        ", sum=" + integerSum + ", avg=" + (integerSum / (double) integerCount));
            }
            if (floatCount > 0) {
                System.out.println("Вещественные числа: min=" + minFloat + ", max=" + maxFloat +
                        ", sum=" + floatSum + ", avg=" + (floatSum / floatCount));
            }
            if (stringCount > 0) {
                System.out.println("Строки: min length=" + minStringLength + ", max length=" + maxStringLength);
            }
        }
    }
}