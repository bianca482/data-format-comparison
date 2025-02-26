package at.fhv.kabi.samples.formatwriter;

import at.fhv.kabi.samples.helpers.Helpers;
import at.fhv.kabi.samples.models.BufferedImage.ImageData;
import at.fhv.kabi.samples.models.HeartData.*;
import at.fhv.kabi.samples.models.*;
import at.fhv.kabi.samples.models.ImageDescriptor.ImageDescriptor;
import at.fhv.kabi.samples.models.LocationData.Geometry;
import at.fhv.kabi.samples.models.LocationData.Location;
import at.fhv.kabi.samples.models.LocationData.LocationData;
import com.devsmart.ubjson.*;
import com.fasterxml.jackson.core.FormatSchema;
import org.apache.logging.log4j.util.TriConsumer;

import java.io.*;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class UBJSONWriter extends FormatWriter {

    private static final Map<Class<?>, TriConsumer<UBObject, String, Object>> TYPE_HANDLERS = new HashMap<>();

    static {
        TYPE_HANDLERS.put(String.class, (obj, name, value) -> obj.put(name, UBValueFactory.createString((String) value)));
        TYPE_HANDLERS.put(Integer.class, (obj, name, value) -> obj.put(name, UBValueFactory.createInt((Integer) value)));
        TYPE_HANDLERS.put(Boolean.class, (obj, name, value) -> obj.put(name, UBValueFactory.createBool((Boolean) value)));
        TYPE_HANDLERS.put(Double.class, (obj, name, value)-> obj.put(name, UBValueFactory.createFloat64((Double) value)));
        TYPE_HANDLERS.put(Float.class, (obj, name, value) -> obj.put(name, UBValueFactory.createFloat32((Float) value)));
        TYPE_HANDLERS.put(Long.class, (obj, name, value) -> obj.put(name, UBValueFactory.createInt((Long) value)));
        TYPE_HANDLERS.put(Enum.class, (obj, name, value) -> obj.put(name, UBValueFactory.createString(((Enum<?>) value).name())));
        TYPE_HANDLERS.put(byte[].class, (obj, name, value) -> obj.put(name, UBValueFactory.createArray((byte[]) value)));
        TYPE_HANDLERS.put(List.class, (obj, name, value) -> obj.put(name, UBValueFactory.createArray((List<?>) value)));
        TYPE_HANDLERS.put(double[].class, (obj, name, value) -> obj.put(name, UBValueFactory.createArray((double[]) value)));
        TYPE_HANDLERS.put(String[].class, (obj, name, value) -> obj.put(name, UBValueFactory.createArray((String[]) value)));
    }

    public UBJSONWriter() {
        super("UBJSON", ".ubjson");
    }

    @Override
    public void writePersonToFile(Person object, File file) throws IOException {
        File f = write(file, object);
        Person result = readPersonFromFile(f, null);
        checkAndPrintOutput(result.toFormattedString(), object.toFormattedString());
        writeFileSizes(file.getParentFile().getPath(), f);
    }

    @Override
    public Person readPersonFromFile(File file, FormatSchema schema) throws IOException {
        UBObject obj = readUBObject(file);

        return new Person(
                obj.get("fn").asString(), obj.get("bday").asString(),
                obj.get("gender").asString(), obj.get("adr").asString(),
                obj.get("email").asString(),  obj.get("tel").asString(),
                obj.get("lang").asString(),  obj.get("version").asString());
    }

    @Override
    public void writeSensorValueToFile(SensorValue object, File file) throws IOException {
        File f = write(file, object);
        SensorValue result = readSensorValueFromFile(f, null);
        checkAndPrintOutput(result.toFormattedString(), object.toFormattedString());
        writeFileSizes(file.getParentFile().getPath(), f);
    }

    @Override
    public SensorValue readSensorValueFromFile(File file, FormatSchema schema) throws IOException {
        UBObject obj = readUBObject(file);

        return new SensorValue(
                obj.get("deviceId").asString(), obj.get("timestamp").asLong(),
                obj.get("temperature").asFloat64(), obj.get("humidity").asFloat64());
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
        UBObject obj = readUBObject(file);

        UBArray tagsArray = obj.get("tags").asArray();
        List<String> tags = new LinkedList<>();

        for (int i = 0; i < tagsArray.size(); i++) {
            tags.add(tagsArray.get(i).asString());
        }

        return new ImageDescriptor(
                obj.get("title").asString(), obj.get("description").asString(),
                ImageDescriptor.ImageType.valueOf(obj.get("type").asString()), obj.get("url").asString(),
                obj.get("width").asInt(), obj.get("height").asInt(), tags);
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
        UBObject obj = readUBObject(file);

        return new SmartLightController(
                obj.get("on").asBool(), obj.get("sat").asInt(),
                obj.get("bri").asInt(), obj.get("hue").asInt());
    }

    @Override
    public void writeImageToFile(ImageData object, File file) {
        File f = write(file, object);
        ImageData result = readImageFromFile(f, null);
        checkAndPrintOutput(result.toFormattedString(), object.toFormattedString());
        writeFileSizes(file.getParentFile().getPath(), f);
    }

    @Override
    public ImageData readImageFromFile(File file, FormatSchema schema) {
        UBObject obj = readUBObject(file);
        return new ImageData(obj.get("name").asString(), obj.get("imageBytes").asByteArray());
    }

    @Override
    public void writeLocationDataToFile(LocationData object, File file) throws IOException {
        File f = new File(file + FILE_EXTENSION);
        writeUBObject(createLocationDataObject(object), f);

        LocationData result = readLocationDataFromFile(f, null);
        checkAndPrintOutput(result.toFormattedString(), object.toFormattedString());
        writeFileSizes(file.getParentFile().getPath(), f);
    }

    @Override
    public LocationData readLocationDataFromFile(File file, FormatSchema schema) throws IOException {
        UBObject obj = readUBObject(file);
        UBObject locationObj = obj.get("location").asObject();
        UBObject geometryObj = locationObj.get("geometry").asObject();

        UBArray coord = geometryObj.get("coordinates").asArray();
        Geometry geometry = new Geometry(geometryObj.get("type").asString(), new double[]{coord.get(0).asFloat64(), coord.get(1).asFloat64()});

        return new LocationData(
                obj.get("name").asString(), obj.get("description").asString(),
                obj.get("encodingType").asString(), new Location(locationObj.get("type").asString(), geometry));
    }

    @Override
    public void writeHeartDataToFile(HeartData object, File file) throws IOException {
        File f = new File(file + FILE_EXTENSION);
        writeUBObject(createHeartDataObject(object), f);

        HeartData result = readHeartDataFromFile(f, null);
        checkAndPrintOutput(result.toFormattedString(), object.toFormattedString());
        writeFileSizes(file.getParentFile().getPath(), f);
    }

    @Override
    public HeartData readHeartDataFromFile(File file, FormatSchema schema) throws IOException {
        UBObject obj = readUBObject(file);
        UBObject bodyObj = obj.get("body").asObject();
        UBObject seriesObj = bodyObj.get("series").asObject();
        UBObject ecgObj = seriesObj.get("ecg").asObject();
        UBObject bpObj = seriesObj.get("bloodpressure").asObject();

        Ecg ecg = new Ecg(ecgObj.get("signalid").asInt(), ecgObj.get("afib").asInt());
        BloodPressure bloodpressure = new BloodPressure(bpObj.get("diastole").asInt(), bpObj.get("systole").asInt());
        Series series = new Series(seriesObj.get("deviceid").asString(), seriesObj.get("model").asInt(), ecg, bloodpressure, seriesObj.get("heartrate").asInt(), seriesObj.get("timestamp").asLong(), seriesObj.get("timezone").asString());
        Body body = new Body(new Series[]{series}, bodyObj.get("more").asBool(), bodyObj.get("offset").asInt());
        return new HeartData(obj.get("status").asInt(), body);
    }

    @Override
    public void writeHttpResponseToFile(HttpResponse object, File file) {
        File f = write(file, object);
        HttpResponse result = readHttpResponseFromFile(f, null);
        checkAndPrintOutput(result.toFormattedString(), object.toFormattedString());
        writeFileSizes(file.getParentFile().getPath(), f);
    }

    @Override
    public HttpResponse readHttpResponseFromFile(File file, FormatSchema schema) {
        UBObject obj = readUBObject(file);

        return new HttpResponse(obj.get("statusLine").asString(), obj.get("headerLines").asStringArray(), obj.get("responseBody").asString());
    }

    @Override
    public void measureTime(Object object, File file) throws Exception {
        writeTimeElapsed(getSerializationSpeed(object), file.getParentFile().getPath());
    }

    @Override
    public long getSerializationSpeed(Object object) throws Exception {
        try(ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            long start = System.nanoTime();
            UBWriter writer = new UBWriter(os);
            writer.write(getUBObjectForClass(object));
            long finish = System.nanoTime();

            return finish - start;
        } catch (IOException e) {
            throw new IOException();
        }
    }

    // Skip since the methods used in getSerializationSpeed and write are exactly the same
    @Override
    public void compareFileSizesOfWritingMethods(Object object, File file) {}

    private UBObject getUBObjectForClass(Object object) {
        return switch (object.getClass().getSimpleName()) {
            case "HeartData" -> createHeartDataObject((HeartData) object);
            case "LocationData" -> createLocationDataObject((LocationData) object);
            default -> createUBObject(object);
        };
    }

    private File write(File file, Object object) {
        File f = new File(file + FILE_EXTENSION);
        UBObject obj = getUBObjectForClass(object);
        writeUBObject(obj, f);
        return f;
    }

    private UBObject createUBObject(Object object) {
        UBObject obj = UBValueFactory.createObject();
        Field[] fields = object.getClass().getDeclaredFields();

        for (Field field : fields) {
            try {
                String name = field.getName();
                field.setAccessible(true);
                Object value = field.get(object);

                if (value == null) continue;

                Class<?> type = field.getType();
                type = type.isPrimitive() ? Helpers.primitiveToWrapper.get(type) : type;

                TriConsumer<UBObject, String, Object> handler = TYPE_HANDLERS.getOrDefault(
                        type.isEnum() ? Enum.class : type,
                        (o, n, v) -> {} // no operation for unsupported types
                );

                handler.accept(obj, name, value);

            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return obj;
    }

    private void writeUBObject(UBObject obj, File f) {
        try (UBWriter writer = new UBWriter(new FileOutputStream(f))) {
            writer.write(obj);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private UBObject readUBObject(File file) {
        try (UBReader reader = new UBReader(new FileInputStream(file))) {
            return reader.read().asObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, Object> UBObjectToMap(UBObject dataObj) {
        Map<String, Object> map = new HashMap<>();
        for (Map.Entry<String, UBValue> e : dataObj.entrySet()) {
            map.put(e.getKey(), convertFromUBValue(e.getValue()));
        }
        return map;
    }

    private Object convertFromUBValue(UBValue value) {
        if (value.isString()) {
            return value.asString();
        } else if (value.isInteger()) {
            return value.asInt();
        } else if (value.isBool()) {
            return value.asBool();
        } else if (value.isNumber()) {
            return value.asFloat64();
        } else if (value.isObject()) {
            return UBObjectToMap(value.asObject());
        } else {
            return null;
        }
    }

    private UBObject createHeartDataObject(HeartData object) {
        UBObject obj = createUBObject(object);

        Body body = object.getBody();
        UBObject bodyObj = createUBObject(body);
        UBObject seriesObj = createUBObject(body.getSeries()[0]);
        UBObject ecgObj = createUBObject(body.getSeries()[0].getEcg());
        UBObject bpObj = createUBObject(body.getSeries()[0].getBloodpressure());

        seriesObj.put("bloodpressure", bpObj);
        seriesObj.put("ecg", ecgObj);
        bodyObj.put("series", seriesObj);
        obj.put("body", bodyObj);

        return obj;
    }
    private UBObject createLocationDataObject(LocationData object) {
        UBObject obj = createUBObject(object);
        UBObject locationObj = createUBObject(object.getLocation());
        UBObject geometryObj = createUBObject(object.getLocation().getGeometry());
        locationObj.put("geometry", geometryObj);
        obj.put("location", locationObj);
        return obj;
    }
}
