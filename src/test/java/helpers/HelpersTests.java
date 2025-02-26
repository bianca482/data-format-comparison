package helpers;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import static at.fhv.kabi.samples.helpers.Helpers.*;
import static org.junit.jupiter.api.Assertions.*;

public class HelpersTests {
    @Test
    public void testParseValueWithString() {
        String input = "Hello, World!";
        Object result = parseValue(input);
        assertEquals(input, result);
    }

    @Test
    public void testParseValueWithInteger() {
        String input = "123";
        Object result = parseValue(input);
        assertEquals(Integer.parseInt(input), result);
    }

    @Test
    public void testParseValueWithDouble() {
        String input = "123.45";
        Object result = parseValue(input);
        assertEquals(Double.parseDouble(input), result);
    }

    @Test
    public void testParseValueWithLong() {
        String input = "9223372036854775807";
        Object result = parseValue(input);
        assertEquals(Long.parseLong(input), result);
    }

    @Test
    public void testParseValueWithBooleanTrue() {
        String input = "true";
        Object result = parseValue(input);
        assertEquals(true, result);
    }

    @Test
    public void testParseValueWithBooleanFalse() {
        String input = "false";
        Object result = parseValue(input);
        assertEquals(false, result);
    }

    @Test
    public void testParseValueWithInvalidNumber() {
        String input = "twenty";
        Object result = parseValue(input);
        assertEquals(input, result);
    }

    @Test
    public void testParseValueWithEmptyString() {
        // Test that an empty string is handled correctly (as a String)
        String input = "";
        Object result = parseValue(input);
        assertEquals(input, result);
    }

    @Test
    public void testParseValueWithNull() {
        String input = null;
        Object result = parseValue(input);
        assertNull(result);
    }

    @Test
    void convertValueTest_String() {
        String value = "StringValue";
        Object result = convertValue(value, String.class);
        assertEquals(result, value);
    }

    @Test
    void convertValueTest_Boolean() {
        String value = "true";
        Object result = convertValue(value, Boolean.class);
        assertEquals(result, Boolean.valueOf(value));
    }

    @Test
    void convertValueTest_Int() {
        String value = "12";
        Object result = convertValue(value, Integer.class);
        assertEquals(result, Integer.parseInt(value));
    }

    @Test
    void convertValueTest_Double() {
        String value = "12.03";
        Object result = convertValue(value, Double.class);
        assertEquals(result, Double.parseDouble(value));
    }

    @Test
    void convertValueTest_Long() {
        String value = "12308243580882934";
        Object result = convertValue(value, Long.class);
        assertEquals(result, Long.parseLong(value));
    }

    @Test
    void convertValueTest_Float() {
        String value = "-2.34";
        Object result = convertValue(value, Float.class);
        assertEquals(result, Float.valueOf(value));
    }

    @Test
    void convertValueTest_Timestamp() {
        String value = "1732785985";
        Object result = convertValue(value, Timestamp.class);
        assertEquals(result, new Timestamp(Long.parseLong(value)));
    }

    @Test
    void convertValueTest_Exception() {
        String value = "ljahgkaekrg";
        Throwable exception = assertThrows(RuntimeException.class, () -> convertValue(value, Exception.class));
        assertEquals("Unsupported type: " + Exception.class, exception.getMessage());
    }
}
