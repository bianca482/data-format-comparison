package at.fhv.kabi.samples.formatwriter.flatbuffers;

import at.fhv.kabi.samples.formatwriter.FormatWriter;
import at.fhv.kabi.samples.formatwriter.flatbuffers.at.fhv.kabi.samples.formatwriter.flatbuffers.*;
import at.fhv.kabi.samples.models.BufferedImage.ImageData;
import at.fhv.kabi.samples.models.HeartData.*;
import at.fhv.kabi.samples.models.*;
import at.fhv.kabi.samples.models.ImageDescriptor.ImageDescriptor;
import at.fhv.kabi.samples.models.LocationData.Geometry;
import at.fhv.kabi.samples.models.LocationData.Location;
import at.fhv.kabi.samples.models.LocationData.LocationData;
import com.fasterxml.jackson.core.FormatSchema;
import com.google.flatbuffers.FlatBufferBuilder;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class FlatBuffersWriter extends FormatWriter {


    public FlatBuffersWriter() {
        super("FlatBuffers", ".bin");
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
        Person_FB person_fb = Person_FB.getRootAsPerson_FB(read(file));

        Person person = new Person();
        person.setFn(person_fb.fn());
        person.setBday(person_fb.bday());
        person.setAdr(person_fb.adr());
        person.setGender(person_fb.gender());
        person.setEmail(person_fb.email());
        person.setTel(person_fb.tel());
        person.setLang(person_fb.lang());
        person.setVersion(person_fb.version());

        return person;
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
        SensorValue_FB sensorValueFB = SensorValue_FB.getRootAsSensorValue_FB(read(file));

        SensorValue sensorValue = new SensorValue();
        sensorValue.setDeviceId(sensorValueFB.deviceId());
        sensorValue.setTimestamp(sensorValueFB.timestamp());
        sensorValue.setTemperature(sensorValueFB.temperature());
        sensorValue.setHumidity(sensorValueFB.humidity());

        return sensorValue;
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
        ImageDescriptor_FB image_fb = ImageDescriptor_FB.getRootAsImageDescriptor_FB(read(file));

        ImageDescriptor imageDescriptor = new ImageDescriptor();
        imageDescriptor.setTitle(image_fb.title());
        imageDescriptor.setDescription(image_fb.description());
        imageDescriptor.setUrl(image_fb.url());
        imageDescriptor.setHeight(image_fb.height());
        imageDescriptor.setWidth(image_fb.width());

        List<String> list = new ArrayList<>();
        for (int i = 0; i < image_fb.tagsLength(); i++) {
            list.add(image_fb.tags(i));
        }
        imageDescriptor.setTags(list);
        imageDescriptor.setType(ImageDescriptor.ImageType.valueOf(ImageType_FB.name(image_fb.type())));

        return imageDescriptor;
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
        SmartLightController_FB object_fb = SmartLightController_FB.getRootAsSmartLightController_FB(read(file));

        SmartLightController obj = new SmartLightController();
        obj.setSat(object_fb.sat());
        obj.setOn(object_fb.on());
        obj.setBri(object_fb.bri());
        obj.setHue(object_fb.hue());

        return obj;
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
        Image_FB object_fb = Image_FB.getRootAsImage_FB(read(file));

        ByteBuffer imageBytesBuffer = object_fb.imageBytesAsByteBuffer();
        byte[] imageBytes = new byte[imageBytesBuffer.remaining()];

        return new ImageData(object_fb.name(), imageBytes);
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
        LocationData_FB fb = LocationData_FB.getRootAsLocationData_FB(read(file));

        LocationData data = new LocationData();
        data.setName(fb.name());
        data.setDescription(fb.description());
        data.setEncodingType(fb.encodingType());

        Location_FB locationFb = fb.location();

        Geometry geometry = new Geometry();
        Geometry_FB geometryFb = locationFb.geometry();
        geometry.setType(geometryFb.type());
        geometry.setCoordinates(new double[]{
                geometryFb.coordinatesVector().get(0),
                geometryFb.coordinatesVector().get(1)
        });

        data.setLocation(new Location(locationFb.type(), geometry));

        return data;
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
        HeartData_FB heartDataFB = HeartData_FB.getRootAsHeartData_FB(read(file));

        Body_FB bodyFB = heartDataFB.body();
        boolean more = bodyFB.more();
        int offset = bodyFB.offset();

        Series_FB.Vector seriesArray = bodyFB.seriesVector();

        Series[] series = new Series[seriesArray.length()];
        for (int i = 0; i < seriesArray.length(); i++) {
            Series_FB seriesFB = seriesArray.get(i);

            Ecg_FB ecgFB = seriesFB.ecg();
            Ecg ecg = new Ecg(ecgFB.signalid(), ecgFB.afib());

            BloodPressure_FB bloodPressureFB = seriesFB.bloodpressure();
            BloodPressure bloodPressure = new BloodPressure(bloodPressureFB.diastole(), bloodPressureFB.systole());

            series[i] = new Series(seriesFB.deviceid(), seriesFB.model(), ecg, bloodPressure, seriesFB.heartRate(), seriesFB.timestamp(), seriesFB.timezone());
        }

        return new HeartData(heartDataFB.status(), new Body(series, more, offset));
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
        HttpResponse_FB httpResponse_FB = HttpResponse_FB.getRootAsHttpResponse_FB(read(file));

        String[] headers = new String[httpResponse_FB.headerLinesLength()];
        for (int i = 0; i < httpResponse_FB.headerLinesLength(); i++) {
            headers[i] = httpResponse_FB.headerLines(i);
        }

        HttpResponse response = new HttpResponse();
        response.setStatusLine(httpResponse_FB.statusLine());
        response.setHeaderLines(headers);
        response.setResponseBody(httpResponse_FB.responseBody());

        return response;
    }

    @Override
    public void measureTime(Object object, File file) throws Exception {
        writeTimeElapsed(getSerializationSpeed(object), file.getParentFile().getPath());
    }

    @Override
    public long getSerializationSpeed(Object object) throws Exception {
        try(ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            long start = System.nanoTime();
            os.write(getBuilderForClass(new FlatBufferBuilder(), object).sizedByteArray());
            long finish = System.nanoTime();

            return finish - start;
        }
    }

    // Skip since the methods used in getSerializationSpeed and write are exactly the same
    @Override
    public void compareFileSizesOfWritingMethods(Object object, File file) {}


    private File write(File file, Object object) {
        File f = new File(file + FILE_EXTENSION);
        FlatBufferBuilder builder = getBuilderForClass(new FlatBufferBuilder(), object);

        try(FileOutputStream os = new FileOutputStream(f)) {
            ByteBuffer buf = builder.dataBuffer();
            os.write(buf.array(), buf.position(), buf.remaining());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return f;
    }

    private ByteBuffer read(File file) throws IOException {
        try(FileInputStream is = new FileInputStream(file)) {
            byte[] buffer = is.readAllBytes();
            return ByteBuffer.wrap(buffer);
        }
    }

    private static FlatBufferBuilder getFlatBufferBuilderForHttpResponse(HttpResponse object, FlatBufferBuilder builder) {
        int statusLineOffset = builder.createString(object.getStatusLine());
        String[] headers = object.getHeaderLines();
        int[] offsets = new int[headers.length];
        for (int i = 0; i < headers.length; i++) {
            offsets[i] = builder.createString(headers[i]);
        }
        int headerOffsets = ImageDescriptor_FB.createTagsVector(builder, offsets);
        int responseBodyOffset = builder.createString(object.getResponseBody());

        HttpResponse_FB.startHttpResponse_FB(builder);
        HttpResponse_FB.addStatusLine(builder, statusLineOffset);
        HttpResponse_FB.addHeaderLines(builder, headerOffsets);
        HttpResponse_FB.addResponseBody(builder, responseBodyOffset);
        builder.finish(HttpResponse_FB.endHttpResponse_FB(builder));

        return builder;
    }

    private static FlatBufferBuilder getFlatBufferBuilderForHeartData(HeartData object, FlatBufferBuilder builder) {
        Series[] series = object.getBody().getSeries();
        int[] seriesOffset = new int[series.length];
        int i = 0;
        for (Series s : series) {
            int ecgOffset = Ecg_FB.createEcg_FB(builder, s.getEcg().getSignalid(), s.getEcg().getAfib());
            int bloodPressureOffset = BloodPressure_FB.createBloodPressure_FB(builder, s.getBloodpressure().getDiastole(), s.getBloodpressure().getSystole());
            int deviceidOffset = builder.createString(s.getDeviceid());
            int timezoneOffset = builder.createString(s.getTimezone());
            seriesOffset[i++] = Series_FB.createSeries_FB(builder, deviceidOffset, s.getModel(), ecgOffset, bloodPressureOffset, s.getHeartrate(), s.getTimestamp(), timezoneOffset);
        }

        int seriesVectorOffset = Body_FB.createSeriesVector(builder, seriesOffset);
        int bodyOffset = Body_FB.createBody_FB(builder, seriesVectorOffset, object.getBody().isMore(), object.getBody().getOffset());

        HeartData_FB.startHeartData_FB(builder);
        HeartData_FB.addStatus(builder, object.getStatus());
        HeartData_FB.addBody(builder, bodyOffset);
        builder.finish(HeartData_FB.endHeartData_FB(builder));

        return builder;
    }
    private static FlatBufferBuilder getFlatBufferBuilderForLocationData(LocationData object, FlatBufferBuilder builder) {
        int nameOffset = builder.createString(object.getName());
        int descriptionOffset = builder.createString(object.getDescription());
        int encodingOffset = builder.createString(object.getEncodingType());

        Geometry geometry = object.getLocation().getGeometry();
        double[] coordinates = new double[]{
                geometry.getCoordinates()[0],
                geometry.getCoordinates()[1]
        };
        int coordinatesOffset = Geometry_FB.createCoordinatesVector(builder, coordinates);
        int typeOffset = builder.createString(geometry.getType());
        int geometryOffset = Geometry_FB.createGeometry_FB(builder, typeOffset, coordinatesOffset);

        int locationTypeOffset = builder.createString(object.getLocation().getType());
        int locationOffset = Location_FB.createLocation_FB(builder, locationTypeOffset, geometryOffset);

        LocationData_FB.startLocationData_FB(builder);
        LocationData_FB.addName(builder, nameOffset);
        LocationData_FB.addDescription(builder, descriptionOffset);
        LocationData_FB.addEncodingType(builder, encodingOffset);
        LocationData_FB.addLocation(builder, locationOffset);
        builder.finish(LocationData_FB.endLocationData_FB(builder));

        return builder;
    }
    private static FlatBufferBuilder getFlatBufferBuilderForImage(ImageData object, FlatBufferBuilder builder) {
        int nameOffset = builder.createString(object.getName());
        int bytesOffset = Image_FB.createImageBytesVector(builder, object.getImageBytes());

        Image_FB.startImage_FB(builder);
        Image_FB.addName(builder, nameOffset);
        Image_FB.addImageBytes(builder, bytesOffset);
        builder.finish(Image_FB.endImage_FB(builder));

        return builder;
    }
    private static FlatBufferBuilder getFlatBufferBuilderForSmartLightController(SmartLightController object, FlatBufferBuilder builder) {
        SmartLightController_FB.startSmartLightController_FB(builder);
        SmartLightController_FB.addSat(builder, object.getSat());
        SmartLightController_FB.addOn(builder, object.isOn());
        SmartLightController_FB.addHue(builder, object.getHue());
        SmartLightController_FB.addBri(builder, object.getBri());
        builder.finish(SmartLightController_FB.endSmartLightController_FB(builder));

        return builder;
    }
    private static FlatBufferBuilder getFlatBufferBuilderForImageDescriptor(ImageDescriptor object, FlatBufferBuilder builder) {
        int nameOffset = builder.createString(object.getTitle());
        int descriptionOffset = builder.createString(object.getDescription());
        int urlOffset = builder.createString(object.getUrl());
        int typeOffset = object.getType().ordinal();

        List<String> tags = object.getTags();
        int[] offsets = new int[tags.size()];
        for (int i = 0; i < tags.size(); i++) {
            offsets[i] = builder.createString(tags.get(i));
        }
        int tagsOffset = ImageDescriptor_FB.createTagsVector(builder, offsets);

        ImageDescriptor_FB.startImageDescriptor_FB(builder);
        ImageDescriptor_FB.addTitle(builder, nameOffset);
        ImageDescriptor_FB.addDescription(builder, descriptionOffset);
        ImageDescriptor_FB.addType(builder, typeOffset);
        ImageDescriptor_FB.addUrl(builder, urlOffset);
        ImageDescriptor_FB.addTags(builder, tagsOffset);
        ImageDescriptor_FB.addWidth(builder, object.getWidth());
        ImageDescriptor_FB.addHeight(builder, object.getHeight());
        builder.finish(ImageDescriptor_FB.endImageDescriptor_FB(builder));

        return builder;
    }
    private static FlatBufferBuilder getFlatBufferBuilderForPerson(Person object, FlatBufferBuilder builder) {
        int fnOffset = builder.createString(object.getFn());
        int bdayOffset = builder.createString(object.getBday());
        int genderOffset = builder.createString(object.getGender());
        int adrOffset = builder.createString(object.getAdr());
        int emailOffset = builder.createString(object.getEmail());
        int telOffset = builder.createString(object.getTel());
        int langOffset = builder.createString(object.getLang());
        int versionOffset = builder.createString(object.getVersion());

        Person_FB.startPerson_FB(builder);
        Person_FB.addFn(builder, fnOffset);
        Person_FB.addBday(builder, bdayOffset);
        Person_FB.addGender(builder, genderOffset);
        Person_FB.addAdr(builder, adrOffset);
        Person_FB.addEmail(builder, emailOffset);
        Person_FB.addTel(builder, telOffset);
        Person_FB.addLang(builder, langOffset);
        Person_FB.addVersion(builder, versionOffset);
        builder.finish(Person_FB.endPerson_FB(builder));

        return builder;
    }
    private static FlatBufferBuilder getFlatBufferBuilderForSensorValue(SensorValue object, FlatBufferBuilder builder) {
        int deviceIdOffset = builder.createString(object.getDeviceId());
        long timestampMillis = object.getTimestamp();

        SensorValue_FB.startSensorValue_FB(builder);
        SensorValue_FB.addDeviceId(builder, deviceIdOffset);
        SensorValue_FB.addTimestamp(builder, timestampMillis);
        SensorValue_FB.addTemperature(builder, object.getTemperature());
        SensorValue_FB.addHumidity(builder, object.getHumidity());
        builder.finish(SensorValue_FB.endSensorValue_FB(builder));

        return builder;
    }

    private FlatBufferBuilder getBuilderForClass(FlatBufferBuilder builder, Object object) {
        return switch (object.getClass().getSimpleName()) {
            case "Person" -> getFlatBufferBuilderForPerson((Person) object, builder);
            case "SensorValue" -> getFlatBufferBuilderForSensorValue((SensorValue) object, builder);
            case "ImageData" -> getFlatBufferBuilderForImage((ImageData) object, builder);
            case "HttpResponse" -> getFlatBufferBuilderForHttpResponse((HttpResponse) object, builder);
            case "SmartLightController" -> getFlatBufferBuilderForSmartLightController((SmartLightController) object, builder);
            case "HeartData" -> getFlatBufferBuilderForHeartData((HeartData) object, builder);
            case "ImageDescriptor" -> getFlatBufferBuilderForImageDescriptor((ImageDescriptor) object, builder);
            case "LocationData" -> getFlatBufferBuilderForLocationData((LocationData) object, builder);
            default -> null;
        };
    }
}
