package de.bcxp.challenge.utils;

import de.bcxp.challenge.exceptions.ChallengeException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class Utils {

    public static List<List<String>> readCSV(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            return br.lines()
                    .map(line -> line.split(","))
                    .map(values -> Arrays.stream(values)
                            .map(String::trim)
                            .collect(Collectors.toList()))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new ChallengeException.CSVReadException("Error reading CSV file", e);
        }
    }
}

