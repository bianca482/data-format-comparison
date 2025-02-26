package at.fhv.kabi.samples.formatwriter;

import at.fhv.kabi.samples.models.BufferedImage.ImageData;
import at.fhv.kabi.samples.models.HeartData.HeartData;
import at.fhv.kabi.samples.models.*;
import at.fhv.kabi.samples.models.ImageDescriptor.ImageDescriptor;
import at.fhv.kabi.samples.models.LocationData.LocationData;
import com.fasterxml.jackson.core.FormatSchema;

import java.io.*;

public class JavaWriter extends FormatWriter {

    public JavaWriter() {
        super("Java Serialization", "_Java.bin");
    }

    @Override
    public void writePersonToFile(Person object, File file) throws Exception {
        File f = write(object, file);

        Person result = readPersonFromFile(f, null);
        checkAndPrintOutput(result.toFormattedString(), object.toFormattedString());
        writeFileSizes(file.getParentFile().getPath(), f);
    }

    @Override
    public Person readPersonFromFile(File file, FormatSchema schema) throws Exception {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (Person) ois.readObject();
        }
    }

    @Override
    public void writeSensorValueToFile(SensorValue object, File file) throws Exception {
        File f = write(object, file);

        SensorValue result = readSensorValueFromFile(f, null);
        checkAndPrintOutput(result.toFormattedString(), object.toFormattedString());
        writeFileSizes(file.getParentFile().getPath(), f);
    }

    @Override
    public SensorValue readSensorValueFromFile(File file, FormatSchema schema) throws Exception {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (SensorValue) ois.readObject();
        }
    }

    @Override
    public void writeImageDescriptorToFile(ImageDescriptor object, File file) throws Exception {
        File f = write(object, file);

        ImageDescriptor result = readImageDescriptorFromFile(f, null);
        checkAndPrintOutput(result.toFormattedString(), object.toFormattedString());
        writeFileSizes(file.getParentFile().getPath(), f);
    }

    @Override
    public ImageDescriptor readImageDescriptorFromFile(File file, FormatSchema schema) throws Exception {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (ImageDescriptor) ois.readObject();
        }
    }

    @Override
    public void writeSmartLightControllerToFile(SmartLightController object, File file) throws Exception {
        File f = write(object, file);

        SmartLightController result = readSmartLightControllerFromFile(f, null);
        checkAndPrintOutput(result.toFormattedString(), object.toFormattedString());
        writeFileSizes(file.getParentFile().getPath(), f);
    }

    @Override
    public SmartLightController readSmartLightControllerFromFile(File file, FormatSchema schema) throws Exception {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (SmartLightController) ois.readObject();
        }
    }

    @Override
    public void writeImageToFile(ImageData object, File file) throws Exception {
        File f = write(object, file);

        ImageData result = readImageFromFile(f, null);
        checkAndPrintOutput(result.toFormattedString(), object.toFormattedString());
        writeFileSizes(file.getParentFile().getPath(), f);
    }

    @Override
    public ImageData readImageFromFile(File file, FormatSchema schema) throws Exception {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (ImageData) ois.readObject();
        }
    }

    @Override
    public void writeLocationDataToFile(LocationData object, File file) throws Exception {
        File f = write(object, file);

        LocationData result = readLocationDataFromFile(f, null);
        checkAndPrintOutput(result.toFormattedString(), object.toFormattedString());
        writeFileSizes(file.getParentFile().getPath(), f);
    }

    @Override
    public LocationData readLocationDataFromFile(File file, FormatSchema schema) throws Exception {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (LocationData) ois.readObject();
        }
    }

    @Override
    public void writeHeartDataToFile(HeartData object, File file) throws Exception {
        File f = write(object, file);

        HeartData result = readHeartDataFromFile(f, null);
        checkAndPrintOutput(result.toFormattedString(), object.toFormattedString());
        writeFileSizes(file.getParentFile().getPath(), f);
    }

    @Override
    public HeartData readHeartDataFromFile(File file, FormatSchema schema) throws Exception {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (HeartData) ois.readObject();
        }
    }

    @Override
    public void writeHttpResponseToFile(HttpResponse object, File file) throws Exception {
        File f = write(object, file);

        HttpResponse result = readHttpResponseFromFile(f, null);
        checkAndPrintOutput(result.toFormattedString(), object.toFormattedString());
        writeFileSizes(file.getParentFile().getPath(), f);
    }

    @Override
    public HttpResponse readHttpResponseFromFile(File file, FormatSchema schema) throws Exception {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (HttpResponse) ois.readObject();
        }
    }

    @Override
    public void measureTime(Object object, File file) throws Exception {
        writeTimeElapsed(getSerializationSpeed(object), file.getParentFile().getPath());
    }

    @Override
    public long getSerializationSpeed(Object object) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try (ObjectOutputStream os = new ObjectOutputStream(bos)) {
            long start = System.nanoTime();
            os.writeObject(object);
            long finish = System.nanoTime();

            return finish - start;
        } catch (IOException e) {
            throw new IOException();
        }
    }

    // Skip since the methods used in getSerializationSpeed and write are exactly the same
    @Override
    public void compareFileSizesOfWritingMethods(Object object, File file) {}

    private File write(Object object, File file) throws IOException {
        File f = new File(file + FILE_EXTENSION);
        try(ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(f))) {
            os.writeObject(object);
        }
        return f;
    }
}
