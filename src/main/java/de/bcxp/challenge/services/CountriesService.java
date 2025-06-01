package de.bcxp.challenge.services;

import java.util.List;

public class CountriesService implements ProcessSheet {

    public String getCountryWithHighestPopDensity(List<List<String>> countriesSheet) {
        return processSheet(
                countriesSheet,
                (val1, val2) -> Double.parseDouble(val1) / Double.parseDouble(val2),
                0, 4, 5,
                String.class
        );
    }


}
