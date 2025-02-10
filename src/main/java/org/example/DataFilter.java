package org.example;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class DataFilter {

    private Path outputPath = Paths.get("");
    private String prefix = "";
    private boolean appendMode = false;

    private int integerCount = 0;
    private int floatCount = 0;
    private int stringCount = 0;

    public void setOutputPath(String outputPath) {
        this.outputPath = Paths.get(outputPath);
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setAppendMode(boolean appendMode) {
        this.appendMode = appendMode;
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
        System.out.println("Целые числа: " + integerCount);
        System.out.println("Вещественные числа: " + floatCount);
        System.out.println("Строки: " + stringCount);
    }
}