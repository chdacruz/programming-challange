package de.bcxp.challenge.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class Utils {
    public static List<List<String>> readCSV(String fileName) {
        List<List<String>> csvData = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Split on comma but not within quotes
                String[] values = line.split(",");

                // Convert array to list while removing quotes and trimming whitespace
                List<String> row = new ArrayList<>();
                for (String value : values) {
                    value = value.trim();
                    if (value.startsWith("\"") && value.endsWith("\"")) {
                        value = value.substring(1, value.length() - 1);
                    }
                    row.add(value);
                }
                csvData.add(row);
            }
        } catch (IOException e) {
            //TODO Check if it's needed to improve this
            e.printStackTrace();
        }

        return csvData;
    }
}

