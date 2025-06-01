package de.bcxp.challenge.services;

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;


public interface ProcessSheet {

    default <T> T processSheet(
            List<List<String>> sheet,
            BiFunction<String, String, Double> calculator,
            int idColumnIndex,
            int firstValueColumnIndex,
            int secondValueColumnIndex,
            Class<T> keyType
    ) {
        var result = sheet.stream()
                .skip(1)
                .map(row -> new AbstractMap.SimpleEntry<>(
                        convertKey(row.get(idColumnIndex), keyType),
                        calculator.apply(
                                row.get(firstValueColumnIndex),
                                row.get(secondValueColumnIndex)
                        )
                ))
                .min(Comparator.comparingDouble(Map.Entry::getValue))
                .map(Map.Entry::getKey);

        return result.orElse(getDefaultValue(keyType));
    }

    @SuppressWarnings("unchecked")
    private <T> T convertKey(String value, Class<T> keyType) {
        if (keyType == Integer.class) {
            return (T) Integer.valueOf(value);
        } else if (keyType == String.class) {
            return (T) value;
        }
        throw new IllegalArgumentException("Unsupported key type: " + keyType);
    }

    @SuppressWarnings("unchecked")
    private <T> T getDefaultValue(Class<T> keyType) {
        if (keyType == Integer.class) {
            return (T) Integer.valueOf(0);
        } else if (keyType == String.class) {
            return (T) "";
        }
        throw new IllegalArgumentException("Unsupported key type: " + keyType);
    }

}
