package at.fhv.kabi.samples.formatwriter.avro;

import at.fhv.kabi.samples.formatwriter.FormatWriter;
import at.fhv.kabi.samples.models.BufferedImage.ImageData;
import at.fhv.kabi.samples.models.HeartData.Body;
import at.fhv.kabi.samples.models.HeartData.HeartData;
import at.fhv.kabi.samples.models.HttpResponse;
import at.fhv.kabi.samples.models.ImageDescriptor.ImageDescriptor;
import at.fhv.kabi.samples.models.LocationData.Location;
import at.fhv.kabi.samples.models.LocationData.LocationData;
import at.fhv.kabi.samples.models.Person;
import at.fhv.kabi.samples.models.SensorValue;
import at.fhv.kabi.samples.models.SmartLightController;
import com.fasterxml.jackson.core.FormatSchema;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.*;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;

public class AvroWriter extends FormatWriter {

    static Schema PERSON_SCHEMA;
    static Schema SENSOR_VALUE_SCHEMA;
    static Schema IMAGE_DESCRIPTOR_SCHEMA;
    static Schema SMART_LIGHT_CONTROLLER_SCHEMA;
    static Schema IMAGE_SCHEMA;
    static Schema LOCATION_SCHEMA;
    static Schema HEART_DATA_SCHEMA;
    static Schema HTTP_RESPONSE_SCHEMA;

    public AvroWriter() {
        super("Avro", ".avro");
        init();
    }

    private void init() {
        try {
            PERSON_SCHEMA = new Schema.Parser().parse(new File("src/main/java/at/fhv/kabi/samples/formatwriter/avro/schemas/person.avsc"));
            SENSOR_VALUE_SCHEMA = new Schema.Parser().parse(new File("src/main/java/at/fhv/kabi/samples/formatwriter/avro/schemas/sensorValue.avsc"));
            IMAGE_DESCRIPTOR_SCHEMA = new Schema.Parser().parse(new File("src/main/java/at/fhv/kabi/samples/formatwriter/avro/schemas/imageDescriptor.avsc"));
            SMART_LIGHT_CONTROLLER_SCHEMA = new Schema.Parser().parse(new File("src/main/java/at/fhv/kabi/samples/formatwriter/avro/schemas/smartLightController.avsc"));
            IMAGE_SCHEMA = new Schema.Parser().parse(new File("src/main/java/at/fhv/kabi/samples/formatwriter/avro/schemas/image.avsc"));
            LOCATION_SCHEMA = new Schema.Parser().parse(new File("src/main/java/at/fhv/kabi/samples/formatwriter/avro/schemas/locationData.avsc"));
            HEART_DATA_SCHEMA = new Schema.Parser().parse(new File("src/main/java/at/fhv/kabi/samples/formatwriter/avro/schemas/heartData.avsc"));
            HTTP_RESPONSE_SCHEMA = new Schema.Parser().parse(new File("src/main/java/at/fhv/kabi/samples/formatwriter/avro/schemas/httpResponse.avsc"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        GenericRecord record = read(file, PERSON_SCHEMA);

        Person result = new Person();
        result.setFn(record.get("fn").toString());
        result.setBday(record.get("bday").toString());
        result.setGender(record.get("gender").toString());
        result.setAdr(record.get("adr").toString());
        result.setEmail(record.get("email").toString());
        result.setTel(record.get("tel").toString());
        result.setLang(record.get("lang").toString());
        result.setVersion(record.get("version").toString());

        return result;
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
        GenericRecord record = read(file, SENSOR_VALUE_SCHEMA);

        return new SensorValue(
                record.get("deviceId").toString(),
                Long.parseLong(record.get("timestamp").toString()),
                Double.parseDouble(record.get("temperature").toString()),
                Double.parseDouble(record.get("humidity").toString())
        );
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
        GenericRecord record = read(file, IMAGE_DESCRIPTOR_SCHEMA);

        ImageDescriptor result = new ImageDescriptor();

        result.setTitle(record.get("title").toString());
        result.setDescription(record.get("description").toString());
        result.setUrl(record.get("url").toString());
        result.setType(ImageDescriptor.ImageType.valueOf(record.get("type").toString()));
        result.setWidth(Integer.parseInt(record.get("width").toString()));
        result.setHeight(Integer.parseInt(record.get("height").toString()));
        //noinspection unchecked
        result.setTags((List<String>) record.get("tags"));

        return result;
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
        GenericRecord record = read(file, SMART_LIGHT_CONTROLLER_SCHEMA);

        return new SmartLightController(
                Boolean.parseBoolean(record.get("on").toString()),
                Integer.parseInt(record.get("sat").toString()),
                Integer.parseInt(record.get("bri").toString()),
                Integer.parseInt(record.get("hue").toString())
        );
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
        GenericRecord record = read(file, IMAGE_SCHEMA);

        ImageData result = new ImageData();
        result.setName(record.get("name").toString());
        result.setImageBytes(((ByteBuffer) record.get("imageBytes")).array());

        return result;
    }

    @Override
    public void writeLocationDataToFile(at.fhv.kabi.samples.models.LocationData.LocationData object, File file) throws Exception {
        File f = write(file, object);
        LocationData result = readLocationDataFromFile(f, null);
        checkAndPrintOutput(result.toFormattedString(), object.toFormattedString());
        writeFileSizes(file.getParentFile().getPath(), f);
    }

    @Override
    public LocationData readLocationDataFromFile(File file, FormatSchema schema) throws Exception {
        GenericRecord record = read(file, LOCATION_SCHEMA);
        at.fhv.kabi.samples.models.LocationData.Location location = new JsonMapper().readValue(record.get("location").toString(), Location.class);

        return new LocationData(
                record.get("name").toString(),
                record.get("description").toString(),
                record.get("encodingType").toString(),
                location
        );
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
        GenericRecord record = read(file, HEART_DATA_SCHEMA);
        Body body = new JsonMapper().readValue(record.get("body").toString(), Body.class);

        return new HeartData(Integer.parseInt(record.get("status").toString()), body);
    }

    @Override
    public void writeHttpResponseToFile(HttpResponse object, File file) throws IOException {
        File f = write(file, object);
        HttpResponse result = readHttpResponseFromFile(f, null);
        checkAndPrintOutput(result.toFormattedString(), object.toFormattedString());
        writeFileSizes(file.getParentFile().getPath(), f);
    }

    @Override
    public HttpResponse readHttpResponseFromFile(File file, FormatSchema schema) throws IOException {
        GenericRecord record = read(file, HTTP_RESPONSE_SCHEMA);

        String headerLines = record.get("headerLines").toString();
        String[] header = headerLines.substring(1, headerLines.length()-1).split(", ");

        return new HttpResponse(record.get("statusLine").toString(), header, record.get("responseBody").toString());
    }

    @Override
    public void measureTime(Object object, File file) throws Exception {
        writeTimeElapsed(getSerializationSpeed(object), file.getParentFile().getPath());
    }

    @Override
    public long getSerializationSpeed(Object object) throws Exception {
        try(ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            long start = System.nanoTime();
            Object[] schemaAndRecord = getSchemaAndRecord(object);
            DatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<>((Schema) schemaAndRecord[0]);
            Encoder encoder = EncoderFactory.get().binaryEncoder(os, null);
            datumWriter.write((GenericRecord) schemaAndRecord[1], encoder);
            long finish = System.nanoTime();

            return finish - start;
        } catch (IOException e) {
            throw new IOException();
        }
    }

    // Skip since the methods used in getSerializationSpeed and write are exactly the same
    @Override
    public void compareFileSizesOfWritingMethods(Object object, File file) {}

    private GenericRecord read(File file, Schema schema) throws IOException {
        GenericRecord record;

        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            DatumReader<GenericRecord> datumReader = new GenericDatumReader<>(schema);
            Decoder decoder = DecoderFactory.get().binaryDecoder(fileInputStream, null);
            record = datumReader.read(null, decoder);
        }
        return record;
    }

    private File write(File file, Object object) throws IOException {
        Object[] schemaAndRecord = getSchemaAndRecord(object);
        Schema schema = (Schema) schemaAndRecord[0];
        GenericRecord record = (GenericRecord) schemaAndRecord[1];

        File f = new File(file + FILE_EXTENSION);

        try (FileOutputStream fileOutputStream = new FileOutputStream(f)) {
            DatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<>(schema);
            Encoder encoder = EncoderFactory.get().binaryEncoder(fileOutputStream, null);
            datumWriter.write(record, encoder);
            encoder.flush();
        }
        return f;
    }

    private Object[] getSchemaAndRecord(Object object) {
        Schema schema = null;
        GenericRecord record = switch (object.getClass().getSimpleName()) {
            case "Person" -> {
                schema = PERSON_SCHEMA;
                yield createGenericRecord(object, schema);
            }
            case "SensorValue" -> {
                schema = SENSOR_VALUE_SCHEMA;
                yield createGenericRecord(object, schema);
            }
            case "ImageData" -> {
                schema = IMAGE_SCHEMA;
                yield createGenericRecord(object, schema);
            }
            case "HeartData" -> {
                schema = HEART_DATA_SCHEMA;
                yield createHeartDataRecord((HeartData) object);
            }
            case "ImageDescriptor" -> {
                schema = IMAGE_DESCRIPTOR_SCHEMA;
                yield createImageDescriptorRecord((ImageDescriptor) object);
            }
            case "LocationData" -> {
                schema = LOCATION_SCHEMA;
                yield createLocationDataRecord((LocationData) object);
            }
            case "HttpResponse" -> {
                schema = HTTP_RESPONSE_SCHEMA;
                yield createGenericRecord(object, schema);
            }
            case "SmartLightController" -> {
                schema = SMART_LIGHT_CONTROLLER_SCHEMA;
                yield createGenericRecord(object, schema);
            }
            default -> null;
        };

        return new Object[] {schema, record};
    }

    private GenericRecord createGenericRecord(Object object, Schema schema) {
        GenericRecord r = new GenericData.Record(schema);

        Field[] fields = object.getClass().getDeclaredFields();

        for (Field field : fields) {
            try {
                String name = field.getName();
                field.setAccessible(true);
                Object value = field.get(object);

                if (value instanceof byte[]) {
                    r.put(name, ByteBuffer.wrap((byte[]) value));
                } else if (value instanceof double[]) {
                    r.put(name, Arrays.stream((double[]) value).boxed().toList());
                } else if (value instanceof String[] strings) {
                    r.put(name, Arrays.stream(strings).toList());
                } else if (value instanceof Object[]) {
                    for (Object o : (Object[]) value) {
                        r.put(name, createGenericRecord(o, schema.getField(name).schema().getElementType()));
                    }
                } else {
                    r.put(name, value);
                }
            } catch (Exception ignored) {
            }
        }
        return r;
    }

    private GenericRecord createImageDescriptorRecord(ImageDescriptor object) {
        GenericData.EnumSymbol type = new GenericData.EnumSymbol(IMAGE_DESCRIPTOR_SCHEMA.getField("type").schema(), object.getType().toString());

        GenericRecord record = createGenericRecord(object, IMAGE_DESCRIPTOR_SCHEMA);
        record.put("type", type);

        return record;
    }

    private GenericRecord createHeartDataRecord(HeartData object) {
        Schema bodySchema = HEART_DATA_SCHEMA.getField("body").schema();
        GenericRecord bodyRecord = createGenericRecord(object.getBody(), bodySchema);

        Schema seriesSchema = bodySchema.getField("series").schema().getElementType();
        GenericRecord seriesRecord = createGenericRecord(object.getBody().getSeries()[0], seriesSchema);

        Schema bpSchema = seriesSchema.getField("bloodpressure").schema();
        GenericRecord bpRecord = createGenericRecord(object.getBody().getSeries()[0].getBloodpressure(), bpSchema);

        Schema ecgSchema = seriesSchema.getField("ecg").schema();
        GenericRecord ecgRecord = createGenericRecord(object.getBody().getSeries()[0].getEcg(), ecgSchema);

        seriesRecord.put("bloodpressure", bpRecord);
        seriesRecord.put("ecg", ecgRecord);
        bodyRecord.put("series", List.of(seriesRecord));

        GenericRecord record = createGenericRecord(object, HEART_DATA_SCHEMA);
        record.put("body", bodyRecord);

        return record;
    }
    private GenericRecord createLocationDataRecord(LocationData object) {
        Schema locationSchema = LOCATION_SCHEMA.getField("location").schema();
        GenericRecord locationRecord = createGenericRecord(object.getLocation(), locationSchema);

        Schema geometrySchema = locationSchema.getField("geometry").schema();
        GenericRecord geometryRecord = createGenericRecord(object.getLocation().getGeometry(), geometrySchema);

        locationRecord.put("geometry", geometryRecord);

        GenericRecord record = createGenericRecord(object, LOCATION_SCHEMA);
        record.put("location", locationRecord);

        return record;
    }
}
