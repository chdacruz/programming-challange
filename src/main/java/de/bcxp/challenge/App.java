package de.bcxp.challenge;

import de.bcxp.challenge.services.WeatherService;

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

        //String dayWithSmallestTempSpread = "Someday";     // Your day analysis function call …
        Integer dayWithSmallestTempSpread = weatherService.getSmallestSpread();
        System.out.printf("Day with smallest temperature spread: %s%n", dayWithSmallestTempSpread);

    }
}
