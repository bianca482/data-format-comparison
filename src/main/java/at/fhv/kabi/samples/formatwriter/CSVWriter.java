package at.fhv.kabi.samples.formatwriter;

import at.fhv.kabi.samples.models.BufferedImage.ImageData;
import at.fhv.kabi.samples.models.HeartData.FlattenedHeartData;
import at.fhv.kabi.samples.models.HeartData.HeartData;
import at.fhv.kabi.samples.models.HttpResponse;
import at.fhv.kabi.samples.models.ImageDescriptor.ImageDescriptor;
import at.fhv.kabi.samples.models.LocationData.FlattenedLocationData;
import at.fhv.kabi.samples.models.LocationData.LocationData;
import at.fhv.kabi.samples.models.Person;
import at.fhv.kabi.samples.models.SensorValue;
import at.fhv.kabi.samples.models.SmartLightController;
import com.fasterxml.jackson.core.FormatSchema;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class CSVWriter extends FormatWriter {

    private final CsvMapper csvMapper;
    private final boolean withHeader;

    public CSVWriter(String name, String fileExtension, boolean withHeader) {
        super(name, fileExtension);
        csvMapper = new CsvMapper();
        setMapper(csvMapper);
        this.withHeader = withHeader;
    }

    @Override
    public void writePersonToFile(Person object, File file) throws Exception {
        CsvSchema s = csvMapper.schemaFor(Person.class);
        CsvSchema schema = withHeader ? s.withHeader() : s;
        File f = write(object, file, schema);

        Person result = readPersonFromFile(f, schema);
        checkAndPrintOutput(result.toFormattedString(), object.toFormattedString());
        writeFileSizes(file.getParentFile().getPath(), f);
    }

    @Override
    public void writeSensorValueToFile(SensorValue object, File file) throws Exception {
        CsvSchema s = csvMapper.schemaFor(SensorValue.class);
        CsvSchema schema = withHeader ? s.withHeader() : s;
        File f = write(object, file, schema);

        SensorValue result = readSensorValueFromFile(f, schema);
        checkAndPrintOutput(result.toFormattedString(), object.toFormattedString());
        writeFileSizes(file.getParentFile().getPath(), f);
    }

    @Override
    public void writeImageDescriptorToFile(ImageDescriptor object, File file) throws Exception {
        CsvSchema s = csvMapper.schemaFor(ImageDescriptor.class);
        CsvSchema schema = withHeader ? s.withHeader() : s;
        File f = write(object, file, schema);

        ImageDescriptor result = readImageDescriptorFromFile(f, schema);
        checkAndPrintOutput(result.toFormattedString(), object.toFormattedString());
        writeFileSizes(file.getParentFile().getPath(), f);
    }

    @Override
    public void writeSmartLightControllerToFile(SmartLightController object, File file) throws Exception {
        CsvSchema s = csvMapper.schemaFor(SmartLightController.class);
        CsvSchema schema = withHeader ? s.withHeader() : s;
        File f = write(object, file, schema);

        SmartLightController result = readSmartLightControllerFromFile(f, schema);
        checkAndPrintOutput(result.toFormattedString(), object.toFormattedString());
        writeFileSizes(file.getParentFile().getPath(), f);
    }

    @Override
    public void writeImageToFile(ImageData object, File file) throws Exception {
        CsvSchema s = csvMapper.schemaFor(ImageData.class);
        CsvSchema schema = withHeader ? s.withHeader() : s;
        File f = write(object, file, schema);

        ImageData result = readImageFromFile(f, schema);
        checkAndPrintOutput(result.toFormattedString(), object.toFormattedString());
        writeFileSizes(file.getParentFile().getPath(), f);
    }

    @Override
    public void writeLocationDataToFile(LocationData object, File file) throws Exception {
        FlattenedLocationData flattenedLocationData = object.flatten();
        CsvSchema s = csvMapper.schemaFor(FlattenedLocationData.class);
        CsvSchema schema = withHeader ? s.withHeader() : s;
        File f = write(flattenedLocationData, file, schema);

        LocationData result = readLocationDataFromFile(f, schema);
        checkAndPrintOutput(result.toFormattedString(), object.toFormattedString());
        writeFileSizes(file.getParentFile().getPath(), f);
    }

    @Override
    public LocationData readLocationDataFromFile(File file, FormatSchema schema) throws Exception {
        FlattenedLocationData flattenedLocationData = csvMapper.readerFor(FlattenedLocationData.class).with(schema).readValue(file);
        return flattenedLocationData.toLocationData();
    }

    @Override
    public void writeHeartDataToFile(HeartData object, File file) throws Exception {
        FlattenedHeartData flattenedHeartData = object.flatten(0);
        CsvSchema s = csvMapper.schemaFor(FlattenedHeartData.class);
        CsvSchema schema = withHeader ? s.withHeader() : s;
        File f = write(flattenedHeartData, file, schema);

        HeartData result = readHeartDataFromFile(f, schema);
        checkAndPrintOutput(result.toFormattedString(), object.toFormattedString());
        writeFileSizes(file.getParentFile().getPath(), f);
    }

    @Override
    public HeartData readHeartDataFromFile(File file, FormatSchema schema) throws Exception {
        FlattenedHeartData flattenedHeartData = csvMapper.readerFor(FlattenedHeartData.class).with(schema).readValue(file);
        return flattenedHeartData.toHeartData(0);
    }

    @Override
    public void writeHttpResponseToFile(HttpResponse object, File file) throws Exception {
        CsvSchema s = csvMapper.schemaFor(HttpResponse.class);
        CsvSchema schema = withHeader ? s.withHeader() : s;
        File f = write(object, file, schema);

        HttpResponse result = readHttpResponseFromFile(f, schema);
        checkAndPrintOutput(result.toFormattedString(), object.toFormattedString());
        writeFileSizes(file.getParentFile().getPath(), f);
    }
    
    @Override
    public void measureTime(Object object, File file) throws Exception {
       writeTimeElapsed(getSerializationSpeed(object), file.getParentFile().getPath());
    }

    @Override
    public long getSerializationSpeed(Object object) throws Exception {
        Object o = getFlattenedObject(object);
        CsvSchema s = csvMapper.schemaFor(o.getClass());
        CsvSchema schema = withHeader ? s.withHeader() : s;

        try(ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            long start = System.nanoTime();
            os.write(csvMapper.writer(schema).writeValueAsBytes(o));
            long finish = System.nanoTime();

            return finish - start;
        } catch (IOException e) {
            throw new IOException();
        }
    }

    @Override
    public void compareFileSizesOfWritingMethods(Object object, File file) throws IOException {
        File f2 = new File(file.getParentFile().getPath() + "\\comp\\" + file.getName() + FILE_EXTENSION);

        Object o = getFlattenedObject(object);
        CsvSchema s = csvMapper.schemaFor(o.getClass());
        CsvSchema schema = withHeader ? s.withHeader() : s;
        writeByteArray(f2, csvMapper.writer(schema).writeValueAsBytes(o));
    }

    private Object getFlattenedObject(Object object) {
        try {
            return switch (object.getClass().getSimpleName()) {
                case "LocationData" -> ((LocationData) object).flatten();
                case "HeartData" -> ((HeartData) object).flatten(0);
                default -> object;
            };
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    private File write(Object object, File file, CsvSchema schema) {
        File f = new File(file + FILE_EXTENSION);
        try {
            csvMapper.writer(schema).writeValue(f, object);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return f;
    }
}
