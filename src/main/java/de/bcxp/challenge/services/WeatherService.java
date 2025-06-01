package de.bcxp.challenge.services;

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static de.bcxp.challenge.utils.Utils.readCSV;

public class WeatherService {

    public Integer getSmallestSpread() {
        var weather = readCSV("src/main/resources/de/bcxp/challenge/weather.csv");
        return processSheet(weather);
    }

    private Integer processSheet(List<List<String>> weatherSheet) {
        return weatherSheet.stream()
                .skip(1)  // Pula a linha de cabeçalho
                .map(row -> new AbstractMap.SimpleEntry<>(
                        Integer.parseInt(row.get(0)),
                        Double.parseDouble(row.get(1)) - Double.parseDouble(row.get(2))
                ))
                .min(Comparator.comparingDouble(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElse(0);
    }
}
