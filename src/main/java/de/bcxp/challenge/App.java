package de.bcxp.challenge;

import de.bcxp.challenge.services.WeatherService;

import static de.bcxp.challenge.utils.Utils.readCSV;

/**
 * The entry class for your solution. This class is only aimed as starting point and not intended as baseline for your software
 * design. Read: create your own classes and packages as appropriate.
 */
public final class App {

    /**
     * This is the main entry method of your program.
     * @param args The CLI arguments passed
     */
    public static void main(String... args) {

        // Your preparation code …
        WeatherService weatherService = new WeatherService();

        var weatherSheet = readCSV("src/main/resources/de/bcxp/challenge/weather.csv");
        Integer dayWithSmallestTempSpread = weatherService.getSmallestSpread(weatherSheet);
        System.out.printf("Day with smallest temperature spread: %s%n", dayWithSmallestTempSpread);

    }
}
