package org.example;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        DataFilter dataFilter = new DataFilter();
        List<String> inputFiles = new ArrayList<>();
        boolean appendMode = false;
        boolean fullStatistics = false;
        String outputPath = "";
        String prefix = "";

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-o":
                    outputPath = args[++i];
                    break;
                case "-p":
                    prefix = args[++i];
                    break;
                case "-a":
                    appendMode = true;
                    break;
                case "-s":
                    fullStatistics = false;
                    break;
                case "-f":
                    fullStatistics = true;
                    break;
                default:
                    inputFiles.add(args[i]);
                    break;
            }
        }

        dataFilter.setOutputPath(outputPath);
        dataFilter.setPrefix(prefix);
        dataFilter.setAppendMode(appendMode);
        dataFilter.setFullStatistics(fullStatistics);
        dataFilter.filterFiles(inputFiles);
    }
}