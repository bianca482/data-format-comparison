package at.fhv.kabi.samples.formatwriter;

import at.fhv.kabi.samples.models.BufferedImage.ImageData;
import at.fhv.kabi.samples.models.HeartData.*;
import at.fhv.kabi.samples.models.HttpResponse;
import at.fhv.kabi.samples.models.ImageDescriptor.ImageDescriptor;
import at.fhv.kabi.samples.models.LocationData.FlattenedLocationData;
import at.fhv.kabi.samples.models.LocationData.Geometry;
import at.fhv.kabi.samples.models.LocationData.Location;
import at.fhv.kabi.samples.models.LocationData.LocationData;
import at.fhv.kabi.samples.models.Person;
import at.fhv.kabi.samples.models.SensorValue;
import at.fhv.kabi.samples.models.SmartLightController;
import com.emc.ecs.nfsclient.rpc.Xdr;
import com.fasterxml.jackson.core.FormatSchema;

import java.io.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static org.apache.commons.math3.util.Precision.round;

public class XDRWriter extends FormatWriter {
    public XDRWriter() {
        super("XDR", ".xdr");
    }

    @Override
    public void writePersonToFile(Person object, File file) throws Exception {
        File f = write(file, object);
        Person result = readPersonFromFile(f, null);
        checkAndPrintOutput(result.toFormattedString(), object.toFormattedString());
        writeFileSizes(file.getParentFile().getPath(), f);
    }

    @Override
    public Person readPersonFromFile(File file, FormatSchema schema) throws Exception {
        Xdr xdr = new Xdr(read(file));

        String fn = xdr.getString();
        String bday = xdr.getString();
        String gender = xdr.getString();
        String adr = xdr.getString();
        String email = xdr.getString();
        String tel = xdr.getString();
        String lang = xdr.getString();
        String version = xdr.getString();

        return new Person(fn, bday, gender, adr, email, tel, lang, version);
    }

    @Override
    public void writeSensorValueToFile(SensorValue object, File file) throws Exception {
        File f = write(file, object);
        SensorValue result = readSensorValueFromFile(f, null);
        checkAndPrintOutput(result.toFormattedString(), object.toFormattedString());
        writeFileSizes(file.getParentFile().getPath(), f);
    }

    @Override
    public SensorValue readSensorValueFromFile(File file, FormatSchema schema) throws Exception {
        Xdr xdr = new Xdr(read(file));

        String deviceId = xdr.getString();
        long timestamp = xdr.getLong();
        double temperature = round((double) xdr.getFloat(), 1);
        double humidity = round((double) xdr.getFloat(), 1);

        return new SensorValue(deviceId, timestamp, temperature, humidity);
    }

    @Override
    public void writeImageDescriptorToFile(ImageDescriptor object, File file) throws Exception {
        File f = write(file, object);
        ImageDescriptor result = readImageDescriptorFromFile(f, null);
        checkAndPrintOutput(result.toFormattedString(), object.toFormattedString());
        writeFileSizes(file.getParentFile().getPath(), f);
    }

    @Override
    public ImageDescriptor readImageDescriptorFromFile(File file, FormatSchema schema) throws Exception {
        Xdr xdr = new Xdr(read(file));

        String name = xdr.getString();
        String description = xdr.getString();
        ImageDescriptor.ImageType type = ImageDescriptor.ImageType.valueOf(xdr.getString());
        String url = xdr.getString();
        int width = xdr.getInt();
        int height = xdr.getInt();

        List<String> tags = new LinkedList<>();

        for (int i = 0; i <= 2; i ++) {
            String s = xdr.getString();
            if (!Objects.equals(s, "")) {
                tags.add(s);
            }
        }

        return new ImageDescriptor(name, description, type, url, width, height, tags);
    }

    @Override
    public void writeSmartLightControllerToFile(SmartLightController object, File file) throws Exception {
        File f = write(file, object);
        SmartLightController result = readSmartLightControllerFromFile(f, null);
        checkAndPrintOutput(result.toFormattedString(), object.toFormattedString());
        writeFileSizes(file.getParentFile().getPath(), f);
    }

    @Override
    public SmartLightController readSmartLightControllerFromFile(File file, FormatSchema schema) throws Exception {
        Xdr xdr = new Xdr(read(file));

        boolean on = xdr.getBoolean();
        int sat = xdr.getInt();
        int bri = xdr.getInt();
        int hue = xdr.getInt();

        return new SmartLightController(on, sat, bri, hue);
    }

    @Override
    public void writeImageToFile(ImageData object, File file) throws Exception {
        File f = write(file, object);
        ImageData result = readImageFromFile(f, null);
        checkAndPrintOutput(result.toFormattedString(), object.toFormattedString());
        writeFileSizes(file.getParentFile().getPath(), f);
    }

    @Override
    public ImageData readImageFromFile(File file, FormatSchema schema) throws Exception {
        Xdr xdr = new Xdr(read(file));

        String name = xdr.getString();
        byte[] imageBytes = xdr.getByteArray();

        return new ImageData(name, imageBytes);
    }

    @Override
    public void writeLocationDataToFile(LocationData object, File file) throws Exception {
        File f = write(file, object);
        LocationData result = readLocationDataFromFile(f, null);
        checkAndPrintOutput(result.toFormattedString(), object.toFormattedString());
        writeFileSizes(file.getParentFile().getPath(), f);
    }

    @Override
    public LocationData readLocationDataFromFile(File file, FormatSchema schema) throws Exception {
        Xdr xdr = new Xdr(read(file));

        String name = xdr.getString();
        String description = xdr.getString();
        String encodingType = xdr.getString();
        String locationType = xdr.getString();
        String geometryType = xdr.getString();
        double longitude = round((double) xdr.getFloat(), 3);
        double latitude = round((double) xdr.getFloat(), 3);

        return new LocationData(name, description, encodingType, new Location(locationType, new Geometry(geometryType, new double[]{longitude, latitude})));
    }

    @Override
    public void writeHeartDataToFile(HeartData object, File file) throws Exception {
        File f = write(file, object);
        HeartData result = readHeartDataFromFile(f, null);
        checkAndPrintOutput(result.toFormattedString(), object.toFormattedString());
        writeFileSizes(file.getParentFile().getPath(), f);
    }

    @Override
    public HeartData readHeartDataFromFile(File file, FormatSchema schema) throws Exception {
        Xdr xdr = new Xdr(read(file));

        int status = xdr.getInt();
        Series[] series = new Series[1];

        Series s = new Series(
                    xdr.getString(), xdr.getInt(), new Ecg(xdr.getInt(), xdr.getInt()),
                    new BloodPressure(xdr.getInt(), xdr.getInt()), xdr.getInt(), xdr.getLong(), xdr.getString()
        );
        series[0] = s;

        boolean more = xdr.getBoolean();
        int offset = xdr.getInt();

        return new HeartData(status, new Body(series, more, offset));
    }

    @Override
    public void writeHttpResponseToFile(HttpResponse object, File file) throws Exception {
        File f = write(file, object);
        HttpResponse result = readHttpResponseFromFile(f, null);
        checkAndPrintOutput(result.toFormattedString(), object.toFormattedString());
        writeFileSizes(file.getParentFile().getPath(), f);
    }

    @Override
    public HttpResponse readHttpResponseFromFile(File file, FormatSchema schema) throws Exception {
        Xdr xdr = new Xdr(read(file));

        String statusLine = xdr.getString();
        String[] headerLines = xdr.getString().split(", ");
        String responseBody = xdr.getString();

        return new HttpResponse(statusLine, headerLines, responseBody);
    }

    @Override
    public void measureTime(Object object, File file) throws Exception {
        writeTimeElapsed(getSerializationSpeed(object), file.getParentFile().getPath());
    }

    @Override
    public long getSerializationSpeed(Object object) throws Exception {
        try(ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            long start = System.nanoTime();
            int size = calculateBufferSizeForObject(object);
            os.write(getXdrForObject(object, size).getBuffer());
            long finish = System.nanoTime();

            return finish - start;
        }
    }

    // Skip since the methods used in getSerializationSpeed and write are exactly the same
    @Override
    public void compareFileSizesOfWritingMethods(Object object, File file) {}

    private Xdr getXdrForObject(Object object, int size) throws Exception {
        return switch (object.getClass().getSimpleName()) {
            case "Person" -> getPersonXdr((Person) object, size);
            case "SensorValue" -> getSensorValueXdr((SensorValue) object, size);
            case "ImageData" -> getImageXdr((ImageData) object, size);
            case "HttpResponse" -> getHttpResponseXdr((HttpResponse) object, size);
            case "SmartLightController" -> getSmartLightControllerXdr((SmartLightController) object, size);
            case "HeartData" -> getHeartDataXdr(((HeartData) object).flatten(0), size);
            case "ImageDescriptor" -> getImageDescriptorXdr((ImageDescriptor) object, size);
            case "LocationData" -> getLocationDataXdr(((LocationData) object).flatten(), size);
            default -> null;
        };
    }

    private File write(File file, Object object) {
        File f = new File(file + FILE_EXTENSION);
        int size = calculateBufferSizeForObject(object);

        try (FileOutputStream os = new FileOutputStream(f)) {
            byte[] bytes = getXdrForObject(object, size).getBuffer();
            os.write(bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return f;
    }

    // As defined in the XDR format specification https://datatracker.ietf.org/doc/html/rfc1014
    // "The representation fo all items require a multiple of four bytes ... If the n bytes needed to contain the data are not
    // a multiple of four, then the n bytes are followed by enough residual zero bytes, ..., to make the total byte count a multiple of 4." (https://datatracker.ietf.org/doc/html/rfc1014)
    private int calculateBufferSizeForValue(Object value) {
        int size = 0;

        if (value == null) return 0;

        if (value instanceof Integer || value instanceof Boolean || value instanceof Float) {
            size += 4;
        } else if (value instanceof Long || value instanceof Double) {
            size += 8;
        } else if (value instanceof String str) {
            size += 4 + str.length(); // "The standard defines a string of n ... bytes to be the number n encoded as an unsigned integer ..., and followed by the n bytes of the string." (https://datatracker.ietf.org/doc/html/rfc1014)
            size += (4 - (size % 4)) % 4; // Padding
        } else if (value instanceof byte[] byteArray) {
            size += 4 + byteArray.length; // Integer (4 bytes) + length of actual data
            size += (4 - (size % 4)) % 4; // Padding
        } else if (value instanceof List<?> list) {
            size += 4;
            for (Object o : list) {
                size += calculateBufferSizeForValue(o);
            }
            size += (4 - (size % 4)) % 4; // Padding
        } else if (value.getClass().isArray()) {
            for (int i = 0; i < Array.getLength(value); i++) {
                size += calculateBufferSizeForValue(Array.get(value, i));
            }
        } else if (value.getClass().isEnum()) {
            size += calculateBufferSizeForValue(((Enum<?>) value).name());
        }
        else {
            size += calculateBufferSizeForObject(value);
        }
        return size;
    }

    private int calculateBufferSizeForObject(Object object) {
        int size = 0;

        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);

            try {
                Object value = field.get(object);
                size += calculateBufferSizeForValue(value);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Failed to access field: " + field.getName(), e);
            }
        }
        return size;
    }

    private byte[] read(File file) {
        try (FileInputStream is = new FileInputStream(file)) {
            return is.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Xdr getImageXdr(ImageData object, int size) {
        Xdr xdr = new Xdr(size);

        xdr.putString(object.getName());
        xdr.putByteArray(object.getImageBytes());
        return xdr;
    }
    private Xdr getSmartLightControllerXdr(SmartLightController object, int size) {
        Xdr xdr = new Xdr(size);

        xdr.putBoolean(object.isOn());
        xdr.putInt(object.getSat());
        xdr.putInt(object.getBri());
        xdr.putInt(object.getHue());
        return xdr;
    }
    private Xdr getImageDescriptorXdr(ImageDescriptor object, int size) {
        Xdr xdr = new Xdr(size);

        xdr.putString(object.getTitle());
        xdr.putString(object.getDescription());
        xdr.putString(object.getType().name());
        xdr.putString(object.getUrl());
        xdr.putInt(object.getWidth());
        xdr.putInt(object.getHeight());

        for (String t : object.getTags()) {
            xdr.putString(t);
        }
        return xdr;
    }
    private Xdr getSensorValueXdr(SensorValue object, int size) {
        Xdr xdr = new Xdr(size);

        xdr.putString(object.getDeviceId());
        xdr.putLong(object.getTimestamp());
        xdr.putFloat((float) object.getTemperature());
        xdr.putFloat((float) object.getHumidity());
        return xdr;
    }
    private Xdr getPersonXdr(Person object, int size) {
        Xdr xdr = new Xdr(size);

        xdr.putString(object.getFn());
        xdr.putString(object.getBday());
        xdr.putString(object.getGender());
        xdr.putString(object.getAdr());
        xdr.putString(object.getEmail());
        xdr.putString(object.getTel());
        xdr.putString(object.getLang());
        xdr.putString(object.getVersion());
        return xdr;
    }
    private Xdr getHttpResponseXdr(HttpResponse object, int size) {
        Xdr xdr = new Xdr(size);

        xdr.putString(object.getStatusLine());
        String joined = Arrays.toString(object.getHeaderLines());
        xdr.putString(joined.substring(1, joined.length()-1));
        xdr.putString(object.getResponseBody());

        return xdr;
    }
    private Xdr getHeartDataXdr(FlattenedHeartData object, int size) {
        Xdr xdr = new Xdr(size);

        xdr.putInt(object.getStatus());
        xdr.putString(object.getDeviceid()[0]);
        xdr.putInt(object.getModel()[0]);
        xdr.putInt(object.getSignalid()[0]);
        xdr.putInt(object.getAfib()[0]);
        xdr.putInt(object.getDiastole()[0]);
        xdr.putInt(object.getSystole()[0]);
        xdr.putInt(object.getHeartrate()[0]);
        xdr.putLong(object.getTimestamp()[0]);
        xdr.putString(object.getTimezone()[0]);

        xdr.putBoolean(object.isMore());
        xdr.putInt(object.getOffset());
        return xdr;
    }
    private Xdr getLocationDataXdr(FlattenedLocationData object, int size) {
        Xdr xdr = new Xdr(size);

        xdr.putString(object.getName());
        xdr.putString(object.getDescription());
        xdr.putString(object.getEncodingType());
        xdr.putString(object.getLocType());
        xdr.putString(object.getGeoType());
        xdr.putFloat((float) object.getCoordinates()[0]);
        xdr.putFloat((float) object.getCoordinates()[1]);
        return xdr;
    }
}
