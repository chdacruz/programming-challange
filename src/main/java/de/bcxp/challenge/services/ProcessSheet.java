package de.bcxp.challenge.services;

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;


public interface ProcessSheet {

    // Using BiFunction
    default Integer processSheet(List<List<String>> weatherSheet, BiFunction<String, String, Double> calculator) {
        return weatherSheet.stream()
                .skip(1)
                .map(row -> new AbstractMap.SimpleEntry<>(
                        Integer.parseInt(row.get(0)),
                        calculator.apply(row.get(1), row.get(2))
                ))
                .min(Comparator.comparingDouble(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElse(0);
    }
}
