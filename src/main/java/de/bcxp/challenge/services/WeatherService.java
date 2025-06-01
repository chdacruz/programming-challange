package de.bcxp.challenge.services;

import java.util.List;

public class WeatherService implements ProcessSheet {

    public Integer getSmallestSpread(List<List<String>> weatherSheet) {
        return processSheet(
                weatherSheet,
                (val1, val2) -> Double.parseDouble(val1) - Double.parseDouble(val2),
                0, 1, 2,
                Integer.class
        );
    }
}
