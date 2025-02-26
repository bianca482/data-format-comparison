package at.fhv.kabi.samples.formatwriter;

import at.fhv.kabi.samples.helpers.Helpers;
import at.fhv.kabi.samples.models.BufferedImage.ImageData;
import at.fhv.kabi.samples.models.HeartData.*;
import at.fhv.kabi.samples.models.HttpResponse;
import at.fhv.kabi.samples.models.ImageDescriptor.ImageDescriptor;
import at.fhv.kabi.samples.models.LocationData.Geometry;
import at.fhv.kabi.samples.models.LocationData.Location;
import at.fhv.kabi.samples.models.LocationData.LocationData;
import at.fhv.kabi.samples.models.Person;
import at.fhv.kabi.samples.models.SensorValue;
import at.fhv.kabi.samples.models.SmartLightController;
import com.fasterxml.jackson.core.FormatSchema;
import com.google.flatbuffers.FlexBuffers;
import com.google.flatbuffers.FlexBuffersBuilder;
import org.apache.logging.log4j.util.TriConsumer;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlexBuffersWriter extends FormatWriter {

    private static final Map<Class<?>, TriConsumer<FlexBuffersBuilder, String, Object>> TYPE_HANDLERS = new HashMap<>();

    static {
        TYPE_HANDLERS.put(String.class, (builder, key, value) -> builder.putString(key, (String) value));
        TYPE_HANDLERS.put(Integer.class, (builder, key, value) -> builder.putInt(key, ((Integer) value)));
        TYPE_HANDLERS.put(Long.class, (builder, key, value) -> builder.putInt(key, ((Long) value)));
        TYPE_HANDLERS.put(Boolean.class, (builder, key, value) -> builder.putBoolean(key, (Boolean) value));
        TYPE_HANDLERS.put(Double.class, (builder, key, value)-> builder.putFloat(key, (Double) value));
        TYPE_HANDLERS.put(Float.class, (builder, key, value) -> builder.putFloat(key, (Float) value));
        TYPE_HANDLERS.put(Enum.class, (builder, key, value) -> builder.putString(key, ((Enum<?>) value).name()));
        TYPE_HANDLERS.put(byte[].class, (builder, key, value) -> builder.putBlob(key, ((byte[]) value)));
    }

    public FlexBuffersWriter() {
        super("FlexBuffers", "_flex.bin");
    }

    @Override
    public void writePersonToFile(Person object, File file) throws IOException, IllegalAccessException {
        File f = write(file, object);
        Person result = readPersonFromFile(f, null);
        checkAndPrintOutput(result.toFormattedString(), object.toFormattedString());
        writeFileSizes(file.getParentFile().getPath(), f);
    }

    @Override
    public Person readPersonFromFile(File file, FormatSchema schema) throws IOException {
        FlexBuffers.Map map = read(file);
        return new Person(map.get("fn").asString(), map.get("bday").asString(), map.get("gender").asString(), map.get("adr").asString(), map.get("email").asString(), map.get("tel").asString(), map.get("lang").asString(), map.get("version").asString());
    }

    @Override
    public void writeSensorValueToFile(SensorValue object, File file) throws IOException, IllegalAccessException {
        File f = write(file, object);
        SensorValue result = readSensorValueFromFile(f, null);
        checkAndPrintOutput(result.toFormattedString(), object.toFormattedString());
        writeFileSizes(file.getParentFile().getPath(), f);
    }

    @Override
    public SensorValue readSensorValueFromFile(File file, FormatSchema schema) throws IOException {
        FlexBuffers.Map map = read(file);

        return new SensorValue(
                map.get("deviceId").asString(), map.get("timestamp").asLong(),
                map.get("temperature").asFloat(), map.get("humidity").asFloat());
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
        FlexBuffers.Map map = read(file);

        FlexBuffers.Vector tagsVector = map.get("tags").asVector();
        List<String> tags = new ArrayList<>();
        for (int i = 0; i < tagsVector.size(); i++) {
            tags.add(tagsVector.get(i).asString());
        }

        return new ImageDescriptor(
                map.get("title").asString(), map.get("description").asString(),
                ImageDescriptor.ImageType.valueOf(map.get("type").asString()), map.get("url").asString(),
                map.get("width").asInt(), map.get("height").asInt(), tags);
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
        FlexBuffers.Map map = read(file);

        return new SmartLightController(
                map.get("on").asBoolean(), map.get("sat").asInt(),
                map.get("bri").asInt(), map.get("hue").asInt());
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
        FlexBuffers.Map map = read(file);
        return new ImageData(map.get("name").asString(), map.get("imageBytes").asBlob().getBytes());
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
        FlexBuffers.Map map = read(file);

        Location location = new Location();
        FlexBuffers.Map locationMap = map.get("location").asMap();
        location.setType(locationMap.get("type").asString());

        Geometry geometry = new Geometry();
        FlexBuffers.Map geometryMap = locationMap.get("geometry").asMap();
        geometry.setType(geometryMap.get("type").asString());

        FlexBuffers.Vector coordinatesVector = geometryMap.get("coordinates").asVector();
        double[] coordinates = new double[]{
                coordinatesVector.get(0).asFloat(),
                coordinatesVector.get(1).asFloat()
        };
        geometry.setCoordinates(coordinates);
        location.setGeometry(geometry);

        return new LocationData(
                map.get("name").asString(), map.get("description").asString(),
                map.get("encodingType").asString(), location);
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
        FlexBuffers.Map map = read(file);
        FlexBuffers.Map bodyMap = map.get("body").asMap();

        FlexBuffers.Vector seriesVector = bodyMap.get("series").asVector();
        Series[] seriesList = new Series[seriesVector.size()];

        for (int i = 0; i < seriesVector.size(); i++) {
            FlexBuffers.Map seriesMap = seriesVector.get(i).asMap();

            String deviceid = seriesMap.get("deviceid").asString();
            int model = seriesMap.get("model").asInt();
            int heartRate = seriesMap.get("heartRate").asInt();
            long timestamp = seriesMap.get("timestamp").asLong();
            String timezone = seriesMap.get("timezone").asString();

            FlexBuffers.Map ecgMap = seriesMap.get("ecg").asMap();
            Ecg ecg = new Ecg(ecgMap.get("signalid").asInt(), ecgMap.get("afib").asInt());

            FlexBuffers.Map bloodPressureMap = seriesMap.get("bloodpressure").asMap();
            BloodPressure bloodPressure = new BloodPressure(
                    bloodPressureMap.get("diastole").asInt(),
                    bloodPressureMap.get("systole").asInt()
            );

            seriesList[i] = new Series(deviceid, model, ecg, bloodPressure, heartRate, timestamp, timezone);
        }

        Body body = new Body(seriesList, bodyMap.get("more").asBoolean(), bodyMap.get("offset").asInt());
        return new HeartData(map.get("status").asInt(), body);
    }

    @Override
    public void writeHttpResponseToFile(HttpResponse object, File file) throws IOException, IllegalAccessException {
        File f = write(file, object);
        HttpResponse result = readHttpResponseFromFile(f, null);
        checkAndPrintOutput(result.toFormattedString(), object.toFormattedString());
        writeFileSizes(file.getParentFile().getPath(), f);
    }

    @Override
    public HttpResponse readHttpResponseFromFile(File file, FormatSchema schema) throws IOException {
        FlexBuffers.Map map = read(file);

        FlexBuffers.Vector headerVector = map.get("headerLines").asVector();
        String[] headerLines = new String[headerVector.size()];
        for (int i = 0; i < headerVector.size(); i++) {
            headerLines[i] = headerVector.get(i).asString();
        }

        return new HttpResponse(map.get("statusLine").asString(), headerLines, map.get("responseBody").asString());
    }

    @Override
    public void measureTime(Object object, File file) throws Exception {
        writeTimeElapsed(getSerializationSpeed(object), file.getParentFile().getPath());
    }

    @Override
    public long getSerializationSpeed(Object object) throws Exception {
        try(ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            long start = System.nanoTime();
            os.write(getBuilderForClass(new FlexBuffersBuilder(), object).array());
            long finish = System.nanoTime();

            return finish - start;
        }
    }

    // Skip since the methods used in getSerializationSpeed and write are exactly the same
    @Override
    public void compareFileSizesOfWritingMethods(Object object, File file) {}

    private static void createMap(Object obj, FlexBuffersBuilder builder) throws IllegalAccessException {
        Class<?> clazz = obj.getClass();

        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String key = field.getName();
            Object value = field.get(obj);

            if (value == null) continue;

            Class<?> type = field.getType();
            type = type.isPrimitive() ? Helpers.primitiveToWrapper.get(type) : type;

            TriConsumer<FlexBuffersBuilder, String, Object> handler = TYPE_HANDLERS.getOrDefault(
                    type.isEnum() ? Enum.class : type,
                    (o, n, v) -> {} // no operation for unsupported types
            );

            handler.accept(builder, key, value);
        }
    }

    private File write(File file, Object object) throws IllegalAccessException {
        File f = new File(file + FILE_EXTENSION);
        ByteBuffer buf = getBuilderForClass(new FlexBuffersBuilder(), object);

        try(FileOutputStream os = new FileOutputStream(f)) {
            os.write(buf.array(), buf.position(), buf.remaining());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return f;
    }

    private FlexBuffers.Map read(File file) throws IOException {
        try(FileInputStream is = new FileInputStream(file)) {
            byte[] buffer = is.readAllBytes();
            return FlexBuffers.getRoot(ByteBuffer.wrap(buffer)).asMap();
        }
    }

    private ByteBuffer getFlexBuffersBuilderForHttpResponse(HttpResponse object, FlexBuffersBuilder builder) throws IllegalAccessException {
        int mapStart = builder.startMap();
        createMap(object, builder);

        String[] headerLines = object.getHeaderLines();
        int headerVectorStart = builder.startVector();
        for (String s : headerLines) {
            builder.putString(s);
        }
        builder.endVector("headerLines", headerVectorStart, false, false);

        builder.endMap(null, mapStart);

        return builder.finish();
    }
    private static ByteBuffer getFlexBuffersBuilderForImageDescriptor(ImageDescriptor object, FlexBuffersBuilder builder) throws IllegalAccessException {
        int mapStart = builder.startMap();
        createMap(object, builder);

        List<String> tags = object.getTags();
        int tagsVectorStart = builder.startVector();
        for (String tag : tags) {
            builder.putString(tag);
        }
        builder.endVector("tags", tagsVectorStart, false, false);

        builder.endMap(null, mapStart);
        return builder.finish();
    }
    private static ByteBuffer getFlexBuffersBuilderForHeartData(HeartData object, FlexBuffersBuilder builder) throws IllegalAccessException {
        int mapStart = builder.startMap();
        builder.putInt("status", object.getStatus());

        int bodyMapStart = builder.startMap();
        createMap(object.getBody(), builder);

        int seriesVectorStart = builder.startVector();
        int i = 0;
        for (Series s : object.getBody().getSeries()) {
            int sStart = builder.startMap();
            builder.startMap();

            builder.putString("deviceid", s.getDeviceid());
            builder.putInt("model", s.getModel());
            builder.putInt("heartRate", s.getHeartrate());
            builder.putInt("timestamp", s.getTimestamp());
            builder.putString("timezone", s.getTimezone());

            int eStart = builder.startMap();
            builder.startMap();
            builder.putInt("signalid", s.getEcg().getSignalid());
            builder.putInt("afib", s.getEcg().getAfib());
            builder.endMap("ecg", eStart);

            int bStart = builder.startMap();
            builder.startMap();
            builder.putInt("diastole", s.getBloodpressure().getDiastole());
            builder.putInt("systole", s.getBloodpressure().getSystole());
            builder.endMap("bloodpressure", bStart);

            builder.endMap(String.valueOf(i++), sStart);
        }

        builder.endVector("series", seriesVectorStart, false, false);
        builder.endMap("body", bodyMapStart);
        builder.endMap(null, mapStart);
        return builder.finish();
    }
    private static ByteBuffer getFlexBuffersBuilderForLocationData(LocationData object, FlexBuffersBuilder builder) throws IllegalAccessException {
        int startMap = builder.startMap();
        createMap(object, builder);

        Location location = object.getLocation();
        int locationStart = builder.startMap();
        builder.putString("type", location.getType());

        Geometry geometry = object.getLocation().getGeometry();
        int geometryStart = builder.startMap();
        builder.putString("type", geometry.getType());

        int coordinatesVector = builder.startVector();
        for (double c : geometry.getCoordinates()) {
            builder.putFloat(c);
        }
        builder.endVector("coordinates", coordinatesVector, false, false);
        builder.endMap("geometry", geometryStart);
        builder.endMap("location", locationStart);

        builder.endMap(null, startMap);
        return builder.finish();
    }
    private static ByteBuffer getFlexBuffersBuilder(Object object, FlexBuffersBuilder builder) throws IllegalAccessException {
        int startMap = builder.startMap();
        createMap(object, builder);
        builder.endMap(null, startMap);
        return builder.finish();
    }

    private ByteBuffer getBuilderForClass(FlexBuffersBuilder builder, Object object) throws IllegalAccessException {
        return switch (object.getClass().getSimpleName()) {
            case "HeartData" -> getFlexBuffersBuilderForHeartData((HeartData) object, builder);
            case "ImageDescriptor" -> getFlexBuffersBuilderForImageDescriptor((ImageDescriptor) object, builder);
            case "LocationData" -> getFlexBuffersBuilderForLocationData((LocationData) object, builder);
            case "HttpResponse" -> getFlexBuffersBuilderForHttpResponse((HttpResponse) object, builder);
            default -> getFlexBuffersBuilder(object, builder);
        };
    }
}
