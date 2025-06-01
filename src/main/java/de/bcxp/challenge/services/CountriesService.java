package de.bcxp.challenge.services;

import java.util.List;

import static de.bcxp.challenge.utils.Utils.cleanNumberFormat;

public class CountriesService implements ProcessSheet {

    public String getCountryWithHighestPopDensity(List<List<String>> countriesSheet) {
        return processSheet(
                countriesSheet,
                (val1, val2) -> Double.parseDouble(cleanNumberFormat(val1)) / Double.parseDouble(cleanNumberFormat(val2)),
                0,3, 4,
                STRING_CONVERTER,
                true
        );
    }


}
