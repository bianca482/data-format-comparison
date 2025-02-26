package at.fhv.kabi.samples.helpers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class Helpers {

    public static Object parseValue(String value) {

        if (value == null) {
            return null;
        }

        if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
            return Boolean.parseBoolean(value);
        }

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException ignored) {
        }

        try {
            return Long.parseLong(value);
        } catch (NumberFormatException ignored) {
        }

        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException ignored) {
        }

        return value;
    }

    public static Object convertValue(String value, Class<?> type) {
        if (type == String.class) {
            return value;
        } else if (type == int.class || type == Integer.class) {
            return Integer.parseInt(value);
        } else if (type == double.class || type == Double.class) {
            return Double.parseDouble(value);
        } else if (type == boolean.class || type == Boolean.class) {
            return Boolean.parseBoolean(value);
        } else if (type == long.class || type == Long.class) {
            return Long.parseLong(value);
        } else if (type == float.class || type == Float.class) {
            return Float.parseFloat(value);
        } else {
            throw new RuntimeException("Unsupported type: " + type);
        }
    }

    public static final Map<Class<?>, Class<?>> primitiveToWrapper = Map.of(
            int.class, Integer.class,
            boolean.class, Boolean.class,
            double.class, Double.class,
            float.class, Float.class,
            long.class, Long.class
    );

    public static void writeFileSize(File outputFile, String name, String inputFileName) {
        Path path = Paths.get(inputFileName);

        try {
            long bytes = Files.size(path);
            FileWriter fw = new FileWriter(outputFile, true);
            fw.write(name + ": " + bytes + "\n");
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException("An error occurred while writing the file");
        }
    }

    public static void writeTimeElapsed(long timeElapsed, File outputFile, String name) {
        try {
            FileWriter fw = new FileWriter(outputFile, true);
            fw.write(name + ": " + timeElapsed + "\n");
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException("An error occurred while writing the file");
        }
    }
}
