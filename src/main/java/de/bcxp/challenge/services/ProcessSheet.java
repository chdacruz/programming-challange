package de.bcxp.challenge.services;

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;


public interface ProcessSheet {
    default Integer processSheet(
            List<List<String>> sheet,
            BiFunction<String, String, Double> calculator,
            int idColumnIndex,
            int firstValueColumnIndex,
            int secondValueColumnIndex
    ) {
        return sheet.stream()
                .skip(1)
                .map(row -> new AbstractMap.SimpleEntry<>(
                        Integer.parseInt(row.get(idColumnIndex)),
                        calculator.apply(
                                row.get(firstValueColumnIndex),
                                row.get(secondValueColumnIndex)
                        )
                ))
                .min(Comparator.comparingDouble(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElse(0);
    }
}
