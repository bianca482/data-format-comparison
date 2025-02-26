package at.fhv.kabi.samples.formatwriter;

import at.fhv.kabi.samples.models.BufferedImage.ImageData;
import at.fhv.kabi.samples.models.HeartData.HeartData;
import at.fhv.kabi.samples.models.HttpResponse;
import at.fhv.kabi.samples.models.ImageDescriptor.ImageDescriptor;
import at.fhv.kabi.samples.models.LocationData.LocationData;
import at.fhv.kabi.samples.models.Person;
import at.fhv.kabi.samples.models.SensorValue;
import at.fhv.kabi.samples.models.SmartLightController;
import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import com.fasterxml.jackson.core.FormatSchema;

import java.io.*;

public class HessianWriter extends FormatWriter {

    public HessianWriter() {
        super("Hessian", ".hessian");
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
        try (InputStream fileInputStream = new FileInputStream(file)) {
            HessianInput input = new HessianInput(fileInputStream);
            return (Person) input.readObject();
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
        try (InputStream fileInputStream = new FileInputStream(file)) {
            HessianInput input = new HessianInput(fileInputStream);
            return (SensorValue) input.readObject();
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
        try (InputStream fileInputStream = new FileInputStream(file)) {
            HessianInput input = new HessianInput(fileInputStream);
            return (ImageDescriptor) input.readObject();
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
        try (InputStream fileInputStream = new FileInputStream(file)) {
            HessianInput input = new HessianInput(fileInputStream);
            return (ImageData) input.readObject();
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
        try (InputStream fileInputStream = new FileInputStream(file)) {
            HessianInput input = new HessianInput(fileInputStream);
            return (SmartLightController) input.readObject();
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
        try (InputStream fileInputStream = new FileInputStream(file)) {
            HessianInput input = new HessianInput(fileInputStream);
            return (LocationData) input.readObject();
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
        try (InputStream fileInputStream = new FileInputStream(file)) {
            HessianInput input = new HessianInput(fileInputStream);
            return (HeartData) input.readObject();
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
        try (InputStream fileInputStream = new FileInputStream(file)) {
            HessianInput input = new HessianInput(fileInputStream);
            return (HttpResponse) input.readObject();
        }
    }

    @Override
    public void measureTime(Object object, File file) throws Exception {
        writeTimeElapsed(getSerializationSpeed(object), file.getParentFile().getPath());
    }

    @Override
    public long getSerializationSpeed(Object object) throws Exception {
        try(ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            long start = System.nanoTime();
            HessianOutput hessianOutput = new HessianOutput(os);
            hessianOutput.writeObject(object);
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
        try (OutputStream fileOutputStream = new FileOutputStream(f)) {
            HessianOutput hessianOutput = new HessianOutput(fileOutputStream);
            hessianOutput.writeObject(object);
        }
        return f;
    }
}
