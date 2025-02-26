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
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.protobuf.ProtobufMapper;
import com.fasterxml.jackson.dataformat.protobuf.schema.ProtobufSchema;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class ProtobufWriter extends FormatWriter {
    private final ProtobufMapper protobufMapper;

    public ProtobufWriter() {
        super("Protobuf", ".proto");
        protobufMapper = new ProtobufMapper();
        setMapper(protobufMapper);
    }

    @Override
    public void writePersonToFile(Person object, File file) throws Exception {
        ProtobufSchema schemaWrapper = protobufMapper.generateSchemaFor(Person.class);
        File f = write(file, object, schemaWrapper);

        Person result = readPersonFromFile(f, schemaWrapper);
        checkAndPrintOutput(result.toFormattedString(), object.toFormattedString());
        writeFileSizes(file.getParentFile().getPath(), f);
    }

    @Override
    public void writeSensorValueToFile(SensorValue object, File file) throws Exception {
        ProtobufSchema schemaWrapper = protobufMapper.generateSchemaFor(SensorValue.class);
        File f = write(file, object, schemaWrapper);

        SensorValue result = readSensorValueFromFile(f, schemaWrapper);
        checkAndPrintOutput(result.toFormattedString(), object.toFormattedString());
        writeFileSizes(file.getParentFile().getPath(), f);
    }

    @Override
    public void writeImageDescriptorToFile(ImageDescriptor object, File file) throws Exception {
        ProtobufSchema schemaWrapper = protobufMapper.generateSchemaFor(ImageDescriptor.class);
        File f = write(file, object, schemaWrapper);

        ImageDescriptor result = readImageDescriptorFromFile(f, schemaWrapper);
        checkAndPrintOutput(result.toFormattedString(), object.toFormattedString());
        writeFileSizes(file.getParentFile().getPath(), f);
    }

    @Override
    public void writeSmartLightControllerToFile(SmartLightController object, File file) throws Exception {
        ProtobufSchema schemaWrapper = protobufMapper.generateSchemaFor(SmartLightController.class);
        File f = write(file, object, schemaWrapper);

        SmartLightController result = readSmartLightControllerFromFile(f, schemaWrapper);
        checkAndPrintOutput(result.toFormattedString(), object.toFormattedString());
        writeFileSizes(file.getParentFile().getPath(), f);
    }

    @Override
    public void writeImageToFile(ImageData object, File file) throws Exception {
        ProtobufSchema schemaWrapper = protobufMapper.generateSchemaFor(ImageData.class);
        File f = write(file, object, schemaWrapper);

        ImageData result = readImageFromFile(f, schemaWrapper);
        checkAndPrintOutput(result.toFormattedString(), object.toFormattedString());
        writeFileSizes(file.getParentFile().getPath(), f);
    }

    @Override
    public void writeLocationDataToFile(LocationData object, File file) throws Exception {
        ProtobufSchema schemaWrapper = protobufMapper.generateSchemaFor(FlattenedLocationData.class);
        File f = write(file, object, schemaWrapper);

        LocationData result = readLocationDataFromFile(f, schemaWrapper);
        checkAndPrintOutput(result.toFormattedString(), object.toFormattedString());
        writeFileSizes(file.getParentFile().getPath(), f);
    }

    @Override
    public LocationData readLocationDataFromFile(File file, FormatSchema schema) throws IOException {
        FlattenedLocationData flattenedLocationData = protobufMapper.readerFor(FlattenedLocationData.class).with(schema).readValue(file);
        return flattenedLocationData.toLocationData();
    }

    @Override
    public void writeHeartDataToFile(HeartData object, File file) throws Exception {
        ProtobufSchema schemaWrapper = protobufMapper.generateSchemaFor(FlattenedHeartData.class);
        File f = write(file, object, schemaWrapper);

        HeartData result = readHeartDataFromFile(f, schemaWrapper);
        checkAndPrintOutput(result.toFormattedString(), object.toFormattedString());
        writeFileSizes(file.getParentFile().getPath(), f);
    }

    @Override
    public HeartData readHeartDataFromFile(File file, FormatSchema schema) throws IOException {
        FlattenedHeartData flattenedHeartData = protobufMapper.readerFor(FlattenedHeartData.class).with(schema).readValue(file);
        return flattenedHeartData.toHeartData(0);
    }

    @Override
    public void writeHttpResponseToFile(HttpResponse object, File file) throws Exception {
        ProtobufSchema schemaWrapper = protobufMapper.generateSchemaFor(HttpResponse.class);
        File f = write(file, object, schemaWrapper);

        HttpResponse result = readHttpResponseFromFile(f, schemaWrapper);
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
        ProtobufSchema schema = protobufMapper.generateSchemaFor(o.getClass());

        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            long start = System.nanoTime();
            os.write(protobufMapper.writer(schema).writeValueAsBytes(o));
            long finish = System.nanoTime();

            return finish - start;
        }
    }

    @Override
    public void compareFileSizesOfWritingMethods(Object object, File file) throws IOException {
        File f2 = new File(file.getParentFile().getPath() + "\\comp\\" + file.getName() + FILE_EXTENSION);

        Object o = getFlattenedObject(object);
        ProtobufSchema schema = protobufMapper.generateSchemaFor(o.getClass());
        writeByteArray(f2, protobufMapper.writer(schema).writeValueAsBytes(o));
    }


    private File write(File file, Object o, ProtobufSchema schemaWrapper) {
        Object object = getFlattenedObject(o);
        File f = new File(file + FILE_EXTENSION);
        try {
            protobufMapper.writer(schemaWrapper).writeValue(f, object);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return f;
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
}
