package at.fhv.kabi.samples.formatwriter;

import at.fhv.kabi.samples.models.BufferedImage.ImageData;
import at.fhv.kabi.samples.models.HeartData.FlattenedHeartData;
import at.fhv.kabi.samples.models.HeartData.HeartData;
import at.fhv.kabi.samples.models.HttpResponse;
import at.fhv.kabi.samples.models.ImageDescriptor.ImageDescriptor;
import at.fhv.kabi.samples.models.ImageDescriptor.ImageDescriptorFlattened;
import at.fhv.kabi.samples.models.LocationData.FlattenedLocationData;
import at.fhv.kabi.samples.models.LocationData.LocationData;
import at.fhv.kabi.samples.models.Person;
import at.fhv.kabi.samples.models.SensorValue;
import at.fhv.kabi.samples.models.SmartLightController;
import com.fasterxml.jackson.core.FormatSchema;
import org.ini4j.Ini;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public class INIWriter extends FormatWriter {

    public INIWriter() {
        super("INI", ".ini");
    }

    @Override
    public void writePersonToFile(Person object, File file) throws Exception {
        File f = write(file, object, object.getClass().getSimpleName());
        Person result = readPersonFromFile(f, null);
        checkAndPrintOutput(result.toFormattedString(), object.toFormattedString());
        writeFileSizes(file.getParentFile().getPath(), f);
    }

    @Override
    public Person readPersonFromFile(File file, FormatSchema schema) throws Exception {
        return deserialize(Person.class, file, Person.class.getSimpleName());
    }

    @Override
    public void writeSensorValueToFile(SensorValue object, File file) throws Exception {
        File f = write(file, object, object.getClass().getSimpleName());
        SensorValue result = readSensorValueFromFile(f, null);
        checkAndPrintOutput(result.toFormattedString(), object.toFormattedString());
        writeFileSizes(file.getParentFile().getPath(), f);
    }

    @Override
    public SensorValue readSensorValueFromFile(File file, FormatSchema schema) throws Exception {
        return deserialize(SensorValue.class, file, SensorValue.class.getSimpleName());
    }

    @Override
    public void writeImageDescriptorToFile(ImageDescriptor object, File file) throws Exception {
        ImageDescriptorFlattened wrapper = object.toImageDescriptorWrapper();
        File f = write(file, wrapper, "ImageDescriptor");
        ImageDescriptor result = readImageDescriptorFromFile(f, null);
        checkAndPrintOutput(result.toFormattedString(), object.toFormattedString());
        writeFileSizes(file.getParentFile().getPath(), f);
    }

    @Override
    public ImageDescriptor readImageDescriptorFromFile(File file, FormatSchema schema) throws Exception {
        return Objects.requireNonNull(deserialize(ImageDescriptorFlattened.class, file, "ImageDescriptor")).toImageDescriptor();
    }

    @Override
    public void writeSmartLightControllerToFile(SmartLightController object, File file) throws Exception {
        File f = write(file, object, object.getClass().getSimpleName());
        SmartLightController result = readSmartLightControllerFromFile(f, null);
        checkAndPrintOutput(result.toFormattedString(), object.toFormattedString());
        writeFileSizes(file.getParentFile().getPath(), f);
    }

    @Override
    public SmartLightController readSmartLightControllerFromFile(File file, FormatSchema schema) throws Exception {
        return deserialize(SmartLightController.class, file, SmartLightController.class.getSimpleName());
    }

    @Override
    public void writeImageToFile(ImageData object, File file) throws Exception {
        File f = write(file, object, object.getClass().getSimpleName());
        ImageData result = readImageFromFile(f, null);
        checkAndPrintOutput(result.toFormattedString(), object.toFormattedString());
        writeFileSizes(file.getParentFile().getPath(), f);
    }

    @Override
    public ImageData readImageFromFile(File file, FormatSchema schema) throws Exception {
        return deserialize(ImageData.class, file, ImageData.class.getSimpleName());
    }

    @Override
    public void writeLocationDataToFile(LocationData object, File file) throws Exception {
        File f = write(file, object.flatten(), "LocationData");
        LocationData result = readLocationDataFromFile(f, null);
        checkAndPrintOutput(result.toFormattedString(), object.toFormattedString());
        writeFileSizes(file.getParentFile().getPath(), f);
    }

    @Override
    public LocationData readLocationDataFromFile(File file, FormatSchema schema) throws Exception {
        return Objects.requireNonNull(deserialize(FlattenedLocationData.class, file, "LocationData")).toLocationData();
    }

    @Override
    public void writeHeartDataToFile(HeartData object, File file) throws Exception {
        File f = write(file, object.flatten(0), "HeartData");
        HeartData result = readHeartDataFromFile(f, null);
        checkAndPrintOutput(result.toFormattedString(), object.toFormattedString());
        writeFileSizes(file.getParentFile().getPath(), f);
    }

    @Override
    public HeartData readHeartDataFromFile(File file, FormatSchema schema) throws Exception {
        return Objects.requireNonNull(deserialize(FlattenedHeartData.class, file, "HeartData")).toHeartData(0);
    }

    @Override
    public void writeHttpResponseToFile(HttpResponse object, File file) throws Exception {
        File f = write(file, object, object.getClass().getSimpleName());
        HttpResponse result = readHttpResponseFromFile(f, null);
        checkAndPrintOutput(result.toFormattedString(), object.toFormattedString());
        writeFileSizes(file.getParentFile().getPath(), f);
    }

    @Override
    public HttpResponse readHttpResponseFromFile(File file, FormatSchema schema) throws Exception {
        return deserialize(HttpResponse.class, file, HttpResponse.class.getSimpleName());
    }

    @Override
    public void measureTime(Object object, File file) throws Exception {
        writeTimeElapsed(getSerializationSpeed(object), file.getParentFile().getPath());
    }

    @Override
    public long getSerializationSpeed(Object object) throws Exception {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            long start = System.nanoTime();
            Ini ini = new Ini();
            Ini.Section sec = ini.add(object.getClass().getSimpleName().split("Flattened")[0]);

            sec.from(object);
            ini.store(os);
            long finish = System.nanoTime();

            return finish - start;
        }
    }

    // Skip since the methods used in getSerializationSpeed and write are exactly the same
    @Override
    public void compareFileSizesOfWritingMethods(Object object, File file) {}

    private File write(File file, Object object, String name) {
        File f = new File(file + FILE_EXTENSION);

        try {
            Ini ini = serialize(object, name);
            ini.store(f);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return f;
    }

    private static Ini serialize(Object object, String name) {
        Ini ini = new Ini();
        Ini.Section sec = ini.add(name);
        sec.from(object);
        return ini;
    }

    private static <T> T deserialize(Class<T> clazz, File file, String name) throws IOException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        T obj = clazz.getDeclaredConstructor().newInstance();
        try {
            new Ini(file).get(name).to(obj);
        } catch (IllegalArgumentException e) {
            return null;
        }
        return obj;
    }
}
