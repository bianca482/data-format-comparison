package at.fhv.kabi.samples.formatwriter;

import at.fhv.kabi.samples.helpers.Helpers;
import at.fhv.kabi.samples.models.BufferedImage.ImageData;
import at.fhv.kabi.samples.models.HeartData.HeartData;
import at.fhv.kabi.samples.models.*;
import at.fhv.kabi.samples.models.ImageDescriptor.ImageDescriptor;
import at.fhv.kabi.samples.models.LocationData.LocationData;
import com.fasterxml.jackson.core.FormatSchema;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;

public abstract class FormatWriter {

    private static final String RED = "\033[0;31m";
    private static final String RESET = "\033[0m";
    ObjectMapper MAPPER;
    FormatSchema SCHEMA;
    public String NAME;
    public String FILE_EXTENSION;

    public FormatWriter(String name, String fileExtension) {
        NAME = name;
        FILE_EXTENSION = fileExtension;
        SCHEMA = null;
    }

    public void setMapper(ObjectMapper mapper) {
        MAPPER = mapper;
    }
    public String getName() {
        return NAME;
    }

    public void writePersonToFile(Person object, File file) throws Exception {
        try {
            File f = write(object, file);
            Person result = readPersonFromFile(f, SCHEMA);

            checkAndPrintOutput(result.toFormattedString(), object.toFormattedString());
            writeFileSizes(file.getParentFile().getPath(), f);
        } catch (Exception e) {
            System.out.println(RED + NAME + ": " + "Requires custom write function." + RESET);
        }
    }

    public Person readPersonFromFile(File file, FormatSchema schema) throws Exception {
        try {
            return MAPPER.readerFor(Person.class).with(schema).readValue(file);
        } catch (Exception e) {
            System.out.println(RED + NAME + ": " + "Requires custom read function." + RESET);
            return null;
        }
    }

    public void writeSensorValueToFile(SensorValue object, File file) throws Exception {
        try {
            File f = write(object, file);
            SensorValue result = readSensorValueFromFile(f, SCHEMA);

            checkAndPrintOutput(result.toFormattedString(), object.toFormattedString());
            writeFileSizes(file.getParentFile().getPath(), f);
        } catch (Exception e) {
            System.out.println(RED + NAME + ": " + "Requires custom write function." + RESET);
        }
    }

    public SensorValue readSensorValueFromFile(File file, FormatSchema schema) throws Exception {
        try {
            return MAPPER.readerFor(SensorValue.class).with(schema).readValue(file);
        } catch (Exception e) {
            System.out.println(RED + NAME + ": " + "Requires custom read function." + RESET);
            return null;
        }
    }

    public void writeImageDescriptorToFile(ImageDescriptor object, File file) throws Exception {
        try {
            File f = write(object, file);
            ImageDescriptor result = readImageDescriptorFromFile(f, SCHEMA);

            checkAndPrintOutput(result.toFormattedString(), object.toFormattedString());
            writeFileSizes(file.getParentFile().getPath(), f);
        } catch (Exception e) {
            System.out.println(RED + NAME + ": " + "Requires custom write function." + RESET);
        }

    }
    public ImageDescriptor readImageDescriptorFromFile(File file, FormatSchema schema) throws Exception {
        try {
            return MAPPER.readerFor(ImageDescriptor.class).with(schema).readValue(file);
        } catch (Exception e) {
            System.out.println(RED + NAME + ": " + "Requires custom read function." + RESET);
            return null;
        }
    }

    public void writeSmartLightControllerToFile(SmartLightController object, File file) throws Exception {
        try {
            File f = write(object, file);
            SmartLightController result = readSmartLightControllerFromFile(f, SCHEMA);

            checkAndPrintOutput(result.toFormattedString(), object.toFormattedString());
            writeFileSizes(file.getParentFile().getPath(), f);
        } catch (Exception e) {
            System.out.println(RED + NAME + ": " + "Requires custom write function." + RESET);
        }
    }

    public SmartLightController readSmartLightControllerFromFile(File file, FormatSchema schema) throws Exception {
        try {
            return MAPPER.readerFor(SmartLightController.class).with(schema).readValue(file);
        } catch (Exception e) {
            System.out.println(RED + NAME + ": " + "Requires custom read function." + RESET);
            return null;
        }
    }

    public void writeImageToFile(ImageData object, File file) throws Exception {
        try {
            File f = write(object, file);
            ImageData result = readImageFromFile(f, SCHEMA);

            checkAndPrintOutput(result.toFormattedString(), object.toFormattedString());
            writeFileSizes(file.getParentFile().getPath(), f);
        } catch (Exception e) {
            System.out.println(RED + NAME + ": " + "Requires custom write function." + RESET);
        }

    }
    public ImageData readImageFromFile(File file, FormatSchema schema) throws Exception {
        try {
            return MAPPER.readerFor(ImageData.class).with(schema).readValue(file);
        } catch (Exception e) {
            System.out.println(RED + NAME + ": " + "Requires custom read function." + RESET);
            return null;
        }
    }

    public void writeLocationDataToFile(LocationData object, File file) throws Exception {
        try {
            File f = write(object, file);
            LocationData result = readLocationDataFromFile(f, SCHEMA);

            checkAndPrintOutput(result.toFormattedString(), object.toFormattedString());
            writeFileSizes(file.getParentFile().getPath(), f);
        } catch (Exception e) {
            System.out.println(RED + NAME + ": " + "Requires custom write function." + RESET);
        }
    }

    public LocationData readLocationDataFromFile(File file, FormatSchema schema) throws Exception {
        try {
            return MAPPER.readerFor(LocationData.class).with(schema).readValue(file);
        } catch (Exception e) {
            System.out.println(RED + NAME + ": " + "Requires custom read function." + RESET);
            return null;
        }
    }

    public void writeHeartDataToFile(HeartData object, File file) throws Exception {
        try {
            File f = write(object, file);
            HeartData result = readHeartDataFromFile(f, SCHEMA);

            checkAndPrintOutput(result.toFormattedString(), object.toFormattedString());
            writeFileSizes(file.getParentFile().getPath(), f);
        } catch (Exception e) {
            System.out.println(RED + NAME + ": " + "Requires custom write function." + RESET);
        }
    }

    public HeartData readHeartDataFromFile(File file, FormatSchema schema) throws Exception {
        try {
            return MAPPER.readerFor(HeartData.class).with(schema).readValue(file);
        } catch (Exception e) {
            System.out.println(RED + NAME + ": " + "Requires custom read function." + RESET);
            return null;
        }
    }

    public void writeHttpResponseToFile(HttpResponse object, File file) throws Exception {
        try {
            File f = write(object, file);
            HttpResponse result = readHttpResponseFromFile(f, SCHEMA);

            checkAndPrintOutput(result.toFormattedString(), object.toFormattedString());
            writeFileSizes(file.getParentFile().getPath(), f);
        } catch (Exception e) {
            System.out.println(RED + NAME + ": " + "Requires custom write function." + RESET);
        }
    }

    public HttpResponse readHttpResponseFromFile(File file, FormatSchema schema) throws Exception {
        try {
            return MAPPER.readerFor(HttpResponse.class).with(schema).readValue(file);
        } catch (Exception e) {
            System.out.println(RED + NAME + ": " + "Requires custom read function." + RESET);
            return null;
        }
    }

    private File write(Object object, File file) throws IOException {
        File f = new File(file + FILE_EXTENSION);
        try {
            MAPPER.writeValue(f, object);
        } catch (IOException e) {
            throw new IOException();
        }
        return f;
    }

    public void measureTime(Object object, File file) throws Exception {
        writeTimeElapsed(getSerializationSpeed(object), file.getParentFile().getPath());
    }

    public long getSerializationSpeed(Object object) throws Exception {
        try(ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            long start = System.nanoTime();
            os.write(MAPPER.writeValueAsBytes(object));
            long finish = System.nanoTime();

            return finish - start;
        } catch (IOException e) {
            throw new IOException();
        }
    }

    // Check if writeByteArray produces exactly the same as writeValue
    public void compareFileSizesOfWritingMethods(Object object, File file) throws IOException {
        File f2 = new File(file.getParentFile().getPath() + "\\comp\\" + file.getName() + FILE_EXTENSION);
        writeByteArray(f2, MAPPER.writeValueAsBytes(object));
    }

    public void writeByteArray(File file, byte[] bytes) {
        try(FileOutputStream os = new FileOutputStream(file)) {
            os.write(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeTimeElapsed(long timeElapsed, String filePath) {
        File outputFile = new File(filePath + "Times.txt");
        Helpers.writeTimeElapsed(timeElapsed, outputFile, NAME);
    }

    public void writeFileSizes(String filePath, File inputFile) {
        File outputFile = new File(filePath + "Sizes.txt");
        Helpers.writeFileSize(outputFile, NAME, inputFile.getPath());
    }

    public void checkAndPrintOutput(String actual, String expected) {
        if (actual.equals(expected)) {
            System.out.println(NAME + ": " + actual);
        } else {
            System.out.println(RED + NAME + ": " + "Strings do not match." + RESET);
        }
    }
}
