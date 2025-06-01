
package de.bcxp.challenge.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static de.bcxp.challenge.utils.Utils.readCSV;
import static de.bcxp.challenge.utils.Utils.isInteger;
import static de.bcxp.challenge.utils.Utils.isNumeric;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Arrays;

class WeatherServiceTest {

    private WeatherService weatherService;

    @Nested
    class GetSmallestSpreadTests {
        @BeforeEach
        void setUp() {
            weatherService = new WeatherService();
        }

        @Test
        void returnsCorrectDay() {
            // Act
            var weatherSheet = readCSV("src/main/resources/de/bcxp/challenge/weather.csv");
            Integer result = weatherService.getSmallestSpread(weatherSheet);

            // Assert
            assertNotNull(result);
            // The actual expected value should be verified against the data in weather.csv
            assertEquals(14, (int) result);
        }

        /**
         * Validates that the first three columns of the file are Day,MxT and MnT
         */
        @Test
        void validateCSVStructure() {
            // Arrange
            var weatherSheet = readCSV("src/main/resources/de/bcxp/challenge/weather.csv");

            // Act & Assert
            //Headers
            List<String> header = weatherSheet.get(0);
            assertTrue(header.size() >= 3);
            assertEquals("Day", header.get(0));
            assertEquals("MxT", header.get(1));
            assertEquals("MnT", header.get(2));

            //Data
            List<String> dataRow = weatherSheet.get(1);
            assertTrue(dataRow.size() >= 3);
            assertTrue(isInteger(dataRow.get(0)));
            assertTrue(isNumeric(dataRow.get(1)));
            assertTrue(isNumeric(dataRow.get(2)));
        }

        @Test
        void withValidData_ReturnsSmallestSpreadDay() {
            // Arrange
            List<List<String>> testData = Arrays.asList(
                    Arrays.asList("Day", "MxT", "MnT"), // header
                    Arrays.asList("1", "88", "59"),     // spread: 29
                    Arrays.asList("2", "79", "63"),     // spread: 16 (smallest)
                    Arrays.asList("3", "90", "66")      // spread: 24
            );

            // Act
            Integer result = weatherService.getSmallestSpread(testData);

            // Assert
            assertEquals(2, result);
        }

        @Test
        void withSingleRow_ReturnsThatDay() {
            // Arrange
            List<List<String>> testData = Arrays.asList(
                    Arrays.asList("Day", "MxT", "MnT"), // header
                    Arrays.asList("1", "80", "70")      // spread: 10
            );

            // Act
            Integer result = weatherService.getSmallestSpread(testData);

            // Assert
            assertEquals(1, result);
        }

        @Test
        void withEqualSpreads_ReturnsFirstDay() {
            // Arrange
            List<List<String>> testData = Arrays.asList(
                    Arrays.asList("Day", "MxT", "MnT"), // header
                    Arrays.asList("1", "80", "70"),     // spread: 10
                    Arrays.asList("2", "85", "75"),     // spread: 10
                    Arrays.asList("3", "90", "80")      // spread: 10
            );

            // Act
            Integer result = weatherService.getSmallestSpread(testData);

            // Assert
            assertEquals(1, result);
        }

        @Test
        void withNegativeTemperatures_ReturnsCorrectDay() {
            // Arrange
            List<List<String>> testData = Arrays.asList(
                    Arrays.asList("Day", "MxT", "MnT"), // header
                    Arrays.asList("1", "5", "-5"),      // spread: 10
                    Arrays.asList("2", "0", "-2"),      // spread: 2 (smallest)
                    Arrays.asList("3", "-10", "-15")    // spread: 5
            );

            // Act
            Integer result = weatherService.getSmallestSpread(testData);

            // Assert
            assertEquals(2, result);
        }

        @Test
        void withEmptyData_ReturnsZero() {
            // Arrange
            List<List<String>> testData = List.of(
                    Arrays.asList("Day", "MxT", "MnT")  // only header
            );

            // Act
            Integer result = weatherService.getSmallestSpread(testData);

            // Assert
            assertEquals(0, result);
        }
    }


}