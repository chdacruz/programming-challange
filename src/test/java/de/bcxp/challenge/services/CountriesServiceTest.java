package de.bcxp.challenge.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static de.bcxp.challenge.utils.Utils.readCSV;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Arrays;

class CountriesServiceTest {

    private static final String COUNTRIES_SEPARATOR = ";";

    private CountriesService countriesService;

    @Nested
    class GetCountryWithHighestPopDensityTests {
        @BeforeEach
        void setUp() {
            countriesService = new CountriesService();
        }

        @Test
        void withValidData_ReturnsHighestDensityCountry() {
            // Act
            var countriesSheet = readCSV("src/main/resources/de/bcxp/challenge/countries.csv", COUNTRIES_SEPARATOR);
            String result = countriesService.getCountryWithHighestPopDensity(countriesSheet);

            // Assert
            assertNotNull(result);
            assertEquals("Malta", result);
        }

        @Test
        void validateCSVStructure() {
            // Arrange
            var countriesSheet = readCSV("src/main/resources/de/bcxp/challenge/countries.csv", COUNTRIES_SEPARATOR);

            // Act & Assert
            // Headers
            List<String> header = countriesSheet.get(0);
            assertTrue(header.size() >= 5);
            assertEquals("Name", header.get(0));
            assertEquals("Population", header.get(3));
            assertEquals("Area (km²)", header.get(4));

            // Data row
            List<String> dataRow = countriesSheet.get(1);
            assertTrue(dataRow.size() >= 5);
            assertFalse(dataRow.get(0).isEmpty()); // Country name
            assertTrue(isNumericWithSeparators(dataRow.get(3))); // Population
            assertTrue(isNumericWithSeparators(dataRow.get(4))); // Area
        }

       /* @Test
        void withValidData_ReturnsHighestDensityCountry() {
            // Arrange
            List<List<String>> testData = Arrays.asList(
                    Arrays.asList("Name", "Capital", "Accession", "Population", "Area (km²)"), // header
                    Arrays.asList("CountryA", "CapitalA", "2000", "1000000", "1000"),     // density: 1000
                    Arrays.asList("CountryB", "CapitalB", "2001", "2000000", "500"),      // density: 4000 (highest)
                    Arrays.asList("CountryC", "CapitalC", "2002", "1500000", "2000")      // density: 750
            );

            // Act
            String result = countriesService.getCountryWithHighestPopDensity(testData);

            // Assert
            assertEquals("CountryB", result);
        }*/

        @Test
        void withSingleCountry_ReturnsThatCountry() {
            // Arrange
            List<List<String>> testData = Arrays.asList(
                    Arrays.asList("Name", "Capital", "Accession", "Population", "Area (km²)"), // header
                    Arrays.asList("CountryA", "CapitalA", "2000", "1000000", "1000")
            );

            // Act
            String result = countriesService.getCountryWithHighestPopDensity(testData);

            // Assert
            assertEquals("CountryA", result);
        }

        @Test
        void withEqualDensities_ReturnsFirstCountry() {
            // Arrange
            List<List<String>> testData = Arrays.asList(
                    Arrays.asList("Name", "Capital", "Accession", "Population", "Area (km²)"), // header
                    Arrays.asList("CountryA", "CapitalA", "2000", "1000000", "1000"), // density: 1000
                    Arrays.asList("CountryB", "CapitalB", "2001", "2000000", "2000"), // density: 1000
                    Arrays.asList("CountryC", "CapitalC", "2002", "3000000", "3000")  // density: 1000
            );

            // Act
            String result = countriesService.getCountryWithHighestPopDensity(testData);

            // Assert
            assertEquals("CountryA", result);
        }

        @Test
        void withInvalidNumberFormat_ThrowsNumberFormatException() {
            // Arrange
            List<List<String>> testData = Arrays.asList(
                    Arrays.asList("Name", "Capital", "Accession", "Population", "Area (km²)"), // header
                    Arrays.asList("CountryA", "CapitalA", "2000", "ABC", "1000")  // invalid number format
            );

            // Act & Assert
            assertThrows(NumberFormatException.class, () ->
                    countriesService.getCountryWithHighestPopDensity(testData)
            );
        }

        @Test
        void withFormattedNumbers_HandlesCorrectly() {
            // Arrange
            List<List<String>> testData = Arrays.asList(
                    Arrays.asList("Name", "Capital", "Accession", "Population", "Area (km²)"), // header
                    Arrays.asList("CountryA", "CapitalA", "2000", "1000000", "1000"),     // density: 1000
                    Arrays.asList("CountryB", "CapitalB", "2001", "2000000", "500"),      // density: 4000 (highest)
                    Arrays.asList("CountryC", "CapitalC", "2002", "1500000", "2000")      // density: 750
            );

            // Act
            String result = countriesService.getCountryWithHighestPopDensity(testData);

            // Assert
            assertEquals("CountryB", result);
        }

        @Test
        void withEmptyData_ReturnsEmptyString() {
            // Arrange
            List<List<String>> testData = List.of(
                    Arrays.asList("Name", "Capital", "Accession", "Population", "Area (km²)")  // only header
            );

            // Act
            String result = countriesService.getCountryWithHighestPopDensity(testData);

            // Assert
            assertEquals("", result);
        }
    }

    // Helper method for CSV structure validation
    private boolean isNumericWithSeparators(String str) {
        return str.replaceAll("[,.]", "").matches("\\d+");
    }
}