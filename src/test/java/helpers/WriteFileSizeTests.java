package helpers;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import static at.fhv.kabi.samples.helpers.Helpers.writeFileSize;
import static org.junit.jupiter.api.Assertions.*;

public class WriteFileSizeTests {

    private static File outputFile;

    @BeforeAll
    public static void setUp() throws IOException {
        outputFile = new File("output.txt");
        if (outputFile.exists()) {
            outputFile.delete();
        }
        outputFile.createNewFile();
    }

    @AfterAll
    public static void tearDown() throws IOException {
        Files.deleteIfExists(outputFile.toPath());
    }

    @Test
    public void testWriteFileSize() throws IOException {
        Path inputFile = Files.createTempFile("testInputFile", ".txt");
        Files.write(inputFile, "Hello, World!".getBytes());
        long expectedSize = Files.size(inputFile);

        writeFileSize(outputFile, "testFile", inputFile.toString());

        String outputContent = new String(Files.readAllBytes(outputFile.toPath())).trim();
        assertTrue(outputContent.contains("testFile: " + expectedSize));
    }

    @Test
    public void testWriteFileSizeWithEmptyFile() throws IOException {
        Path emptyInputFile = Files.createTempFile("emptyTestFile", ".txt");
        long expectedSize = Files.size(emptyInputFile);

        writeFileSize(outputFile, "emptyFile", emptyInputFile.toString());

        String outputContent = new String(Files.readAllBytes(outputFile.toPath())).trim();
        assertTrue(outputContent.contains("emptyFile: " + expectedSize));

        Files.deleteIfExists(emptyInputFile);
    }

    @Test
    public void testWriteFileSizeWithNonExistentFile() {
        String nonExistentFile = "nonExistentFile.txt";

        Throwable exception = assertThrows(RuntimeException.class, () -> writeFileSize(outputFile, "nonExistentFile", nonExistentFile));
        assertEquals("An error occurred while writing the file", exception.getMessage());
    }
}
