package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class DataFilter {

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
        System.out.println("Processing line: " + line);
    }
}