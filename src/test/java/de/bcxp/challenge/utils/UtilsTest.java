
package de.bcxp.challenge.utils;

import de.bcxp.challenge.exceptions.ChallengeException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Arrays;

class UtilsTest {

    private static final String COMMA_SEPARATOR = ",";

    @Nested
    class ReadCSVTests {
        @Test
        void validFile_ReturnsCorrectData() throws IOException {
            // Arrange
            String csvContent = "header1,header2\nvalue1,value2\nvalue3,value4";
            File tempFile = createTempCSVFile(csvContent);

            // Act
            List<List<String>> result = Utils.readCSV(tempFile.getPath(), COMMA_SEPARATOR);

            // Assert
            assertNotNull(result);
            assertEquals(3, result.size());
            assertEquals(Arrays.asList("header1", "header2"), result.get(0));
            assertEquals(Arrays.asList("value1", "value2"), result.get(1));
            assertEquals(Arrays.asList("value3", "value4"), result.get(2));

            assertTrue(tempFile.delete());
        }

        @Test
        void emptyFile_ReturnsEmptyList() throws IOException {
            // Arrange
            String csvContent = "";
            File tempFile = createTempCSVFile(csvContent);

            // Act
            List<List<String>> result = Utils.readCSV(tempFile.getPath(), COMMA_SEPARATOR);

            // Assert
            assertNotNull(result);
            assertTrue(result.isEmpty());

            assertTrue(tempFile.delete());
        }

        @Test
        void fileWithOnlyHeader_ReturnsOnlyHeader() throws IOException {
            // Arrange
            String csvContent = "header1,header2,header3";
            File tempFile = createTempCSVFile(csvContent);

            // Act
            List<List<String>> result = Utils.readCSV(tempFile.getPath(), COMMA_SEPARATOR);

            // Assert
            assertNotNull(result);
            assertEquals(1, result.size());
            assertEquals(Arrays.asList("header1", "header2", "header3"), result.get(0));

            assertTrue(tempFile.delete());
        }

        @Test
        void nonexistentFile_ThrowsException() {
            // Arrange
            String nonExistentFilePath = "nonexistent.csv";

            // Act & Assert
            assertThrows(ChallengeException.CSVReadException.class, () -> Utils.readCSV(nonExistentFilePath, COMMA_SEPARATOR));
        }

        @Test
        void fileWithDifferentColumnCounts_ReturnsAllRows() throws IOException {
            // Arrange
            String csvContent = "header1,header2\nvalue1\nvalue3,value4,value5";
            File tempFile = createTempCSVFile(csvContent);

            // Act
            List<List<String>> result = Utils.readCSV(tempFile.getPath(), COMMA_SEPARATOR);

            // Assert
            assertNotNull(result);
            assertEquals(3, result.size());
            assertEquals(Arrays.asList("header1", "header2"), result.get(0));
            assertEquals(List.of("value1"), result.get(1));
            assertEquals(Arrays.asList("value3", "value4", "value5"), result.get(2));

            assertTrue(tempFile.delete());
        }

        private File createTempCSVFile(String content) throws IOException {
            File tempFile = File.createTempFile("test", ".csv");
            try (FileWriter writer = new FileWriter(tempFile)) {
                writer.write(content);
            }
            return tempFile;
        }
    }


}