package de.bcxp.challenge.services;

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public interface ProcessSheet {

    interface ValueConverter<T> {
        T convert(String value);
        T defaultValue();
    }

    default <T> T processSheet(
            List<List<String>> sheet,
            BiFunction<String, String, Double> calculator,
            int idColumnIndex,
            int firstValueColumnIndex,
            int secondValueColumnIndex,
            ValueConverter<T> converter
    ) {
        return sheet.stream()
                .skip(1)
                .map(row -> new AbstractMap.SimpleEntry<>(
                        converter.convert(row.get(idColumnIndex)),
                        calculator.apply(
                                row.get(firstValueColumnIndex),
                                row.get(secondValueColumnIndex)
                        )
                ))
                .min(Comparator.comparingDouble(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElse(converter.defaultValue());
    }

    ValueConverter<String> STRING_CONVERTER = new ValueConverter<>() {
        @Override
        public String convert(String value) { return value; }
        @Override
        public String defaultValue() { return ""; }
    };

    ValueConverter<Integer> INTEGER_CONVERTER = new ValueConverter<>() {
        @Override
        public Integer convert(String value) { return Integer.parseInt(value); }
        @Override
        public Integer defaultValue() { return 0; }
    };
}