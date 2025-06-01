package de.bcxp.challenge.utils;

import de.bcxp.challenge.exceptions.ChallengeException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class Utils {

    public static List<List<String>> readCSV(String fileName, String separator) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            return br.lines()
                    .map(line -> line.split(separator))
                    .map(values -> Arrays.stream(values)
                            .map(String::trim)
                            .collect(Collectors.toList()))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new ChallengeException.CSVReadException("Error reading CSV file", e);
        }
    }

    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String cleanNumberFormat(String number) {
        // Remove all dots first
        String withoutDots = number.replaceAll("\\.", "");
        // If there's a comma, remove everything after it
        int commaIndex = withoutDots.indexOf(",");
        if (commaIndex != -1) {
            return withoutDots.substring(0, commaIndex);
        }
        return withoutDots;
    }
}

