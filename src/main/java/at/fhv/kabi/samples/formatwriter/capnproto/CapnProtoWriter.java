package at.fhv.kabi.samples.formatwriter.capnproto;

import at.fhv.kabi.samples.formatwriter.FormatWriter;
import at.fhv.kabi.samples.formatwriter.capnproto.generated.*;
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
import org.capnproto.*;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import java.util.LinkedList;
import java.util.List;

public class CapnProtoWriter extends FormatWriter {

    private final boolean packed;

    public CapnProtoWriter(String name, String fileExtension, boolean packed) {
        super(name, fileExtension);
        this.packed = packed;
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
        MessageReader messageReader = read(file);
        PersonProto.PersonProtoStruct.Reader fileContent = messageReader.getRoot(PersonProto.PersonProtoStruct.factory);

        return new Person(
                fileContent.getFn().toString(),
                fileContent.getBday().toString(),
                fileContent.getGender().toString(),
                fileContent.getAdr().toString(),
                fileContent.getEmail().toString(),
                fileContent.getTel().toString(),
                fileContent.getLang().toString(),
                fileContent.getVersion().toString()
        );
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
        MessageReader messageReader = read(file);
        SensorValueProto.SensorValueProtoStruct.Reader fileContent = messageReader.getRoot(SensorValueProto.SensorValueProtoStruct.factory);

        return new SensorValue(
                fileContent.getDeviceId().toString(),
                fileContent.getTimestamp(),
                fileContent.getTemperature(),
                fileContent.getHumidity()
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
        MessageReader messageReader = read(file);
        ImageDescriptorProto.ImageDescriptorProtoStruct.Reader fileContent = messageReader.getRoot(ImageDescriptorProto.ImageDescriptorProtoStruct.factory);

        List<String> tags = new LinkedList<>();

        TextList.Reader reader = fileContent.getTags();

        for (int i = 0; i < reader.size(); i++) {
            tags.add(reader.get(i).toString());
        }

        ImageDescriptor imageDescriptor = new ImageDescriptor();
        imageDescriptor.setWidth(fileContent.getWidth());
        imageDescriptor.setHeight(fileContent.getHeight());
        imageDescriptor.setTags(tags);
        imageDescriptor.setUrl(fileContent.getUrl().toString());
        imageDescriptor.setTitle(fileContent.getTitle().toString());
        imageDescriptor.setDescription(fileContent.getDescription().toString());
        imageDescriptor.setType(ImageDescriptor.ImageType.valueOf(fileContent.getType().toString()));

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
        MessageReader messageReader = read(file);
        SmartLightControllerProto.SmartLightControllerProtoStruct.Reader fileContent = messageReader.getRoot(SmartLightControllerProto.SmartLightControllerProtoStruct.factory);

        return new SmartLightController(fileContent.getOn(), fileContent.getSat(), fileContent.getBri(), fileContent.getHue());
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
        MessageReader messageReader = read(file);
        ImageProto.ImageProtoStruct.Reader fileContent = messageReader.getRoot(ImageProto.ImageProtoStruct.factory);

        return new ImageData(fileContent.getName().toString(), fileContent.getImageBytes().toArray());
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
        MessageReader messageReader = read(file);
        LocationDataProto.LocationDataProtoStruct.Reader fileContent = messageReader.getRoot(LocationDataProto.LocationDataProtoStruct.factory);
        LocationDataProto.LocationProtoStruct.Reader locationContent = fileContent.getLocation();

        Geometry geometry = new Geometry();
        double[] coordinates = new double[2];
        PrimitiveList.Double.Reader coordinatesReader = locationContent.getGeometry().getCoordinates();
        coordinates[0] = coordinatesReader.get(0);
        coordinates[1] = coordinatesReader.get(1);
        geometry.setType(locationContent.getGeometry().getType().toString());
        geometry.setCoordinates(coordinates);

        return new LocationData(
                fileContent.getName().toString(),
                fileContent.getDescription().toString(),
                fileContent.getEncodingType().toString(),
                new Location(locationContent.getType().toString(), geometry)
        );
    }

    @Override
    public void writeHeartDataToFile(HeartData object, File file) throws IOException {
        File f = write(file, object);
        HeartData result = readHeartDataFromFile(f, null);
        checkAndPrintOutput(result.toFormattedString(), object.toFormattedString());
        writeFileSizes(file.getParentFile().getPath(), f);
    }


    @Override
    public HeartData readHeartDataFromFile(File file, FormatSchema schema) throws IOException {
        MessageReader messageReader = read(file);
        HeartDataProto.HeartDataProtoStruct.Reader fileContent = messageReader.getRoot(HeartDataProto.HeartDataProtoStruct.factory);
        HeartDataProto.BodyProto.Reader bodyReader = fileContent.getBody();

        StructList.Reader<HeartDataProto.SeriesProto.Reader> seriesReader = bodyReader.getSeries();
        Series[] seriesArray = new Series[seriesReader.size()];

        for (int i = 0; i < seriesReader.size(); i++) {
            HeartDataProto.SeriesProto.Reader s = seriesReader.get(i);
            seriesArray[i] = getSeries(s);
        }
        
        Body body = new Body(seriesArray, bodyReader.getMore(), bodyReader.getOffset());

        return new HeartData(fileContent.getStatus(), body);
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
        MessageReader messageReader = read(file);
        HttpResponseProto.HttpResponseProtoStruct.Reader fileContent = messageReader.getRoot(HttpResponseProto.HttpResponseProtoStruct.factory);

        TextList.Reader headerReader = fileContent.getHeaderLines();
        String[] headers = new String[headerReader.size()];

        for (int i = 0; i < headerReader.size(); i++) {
            Text.Reader reader = headerReader.get(i);
            headers[i] = reader.toString();
        }
        return new HttpResponse(fileContent.getStatusLine().toString(), headers, fileContent.getResponseBody().toString()
        );
    }

    @Override
    public void measureTime(Object object, File file) throws Exception {
        writeTimeElapsed(getSerializationSpeed(object), file.getParentFile().getPath());
    }

    @Override
    public long getSerializationSpeed(Object object) throws Exception {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            long start;
            long finish;

            if (packed) {
                start = System.nanoTime();
                WritableByteChannel c = Channels.newChannel(bos);
                MessageBuilder messageBuilder = getMessageBuilderForClass(object);
                SerializePacked.writeToUnbuffered(c, messageBuilder);
                finish = System.nanoTime();
            } else {
                start = System.nanoTime();
                WritableByteChannel c = Channels.newChannel(bos);
                MessageBuilder messageBuilder = getMessageBuilderForClass(object);
                Serialize.write(c, messageBuilder);
                finish = System.nanoTime();
            }

            return finish - start;
        } catch (IOException e) {
            throw new IOException();
        }
    }

    // Skip since the methods used in getSerializationSpeed and write are exactly the same
    @Override
    public void compareFileSizesOfWritingMethods(Object object, File file) {}

    private File write(File file, Object object) throws IOException {
        MessageBuilder messageBuilder = getMessageBuilderForClass(object);
        File f = new File(file + FILE_EXTENSION);
        try (FileOutputStream os = new FileOutputStream(f)) {
            if (packed) {
                SerializePacked.writeToUnbuffered(os.getChannel(), messageBuilder);
            } else {
                Serialize.write(os.getChannel(), messageBuilder);
            }
        }
        return f;
    }

    private MessageReader read(File file) {
        try {
            MessageReader messageReader;
            try (FileInputStream is = new FileInputStream(file)) {
                if (packed) {
                    messageReader = SerializePacked.readFromUnbuffered(is.getChannel());
                } else {
                    messageReader = Serialize.read(ByteBuffer.wrap(is.readAllBytes()));
                }
            }
            return messageReader;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static MessageBuilder getPersonMessageBuilder(Person object) {
        MessageBuilder messageBuilder = new MessageBuilder();
        PersonProto.PersonProtoStruct.Builder objectBuilder = messageBuilder.initRoot(PersonProto.PersonProtoStruct.factory);

        objectBuilder.setFn(object.getFn());
        objectBuilder.setBday(object.getBday());
        objectBuilder.setGender(object.getGender());
        objectBuilder.setAdr(object.getAdr());
        objectBuilder.setEmail(object.getEmail());
        objectBuilder.setTel(object.getTel());
        objectBuilder.setLang(object.getLang());
        objectBuilder.setVersion(object.getVersion());
        return messageBuilder;
    }
    private static MessageBuilder getSensorValueMessageBuilder(SensorValue object) {
        MessageBuilder messageBuilder = new MessageBuilder();
        SensorValueProto.SensorValueProtoStruct.Builder objectBuilder = messageBuilder.initRoot(SensorValueProto.SensorValueProtoStruct.factory);

        objectBuilder.setDeviceId(object.getDeviceId());
        objectBuilder.setTimestamp(object.getTimestamp());
        objectBuilder.setHumidity(object.getHumidity());
        objectBuilder.setTemperature(object.getTemperature());
        return messageBuilder;
    }
    private static MessageBuilder getImageDescriptorMessageBuilder(ImageDescriptor object) {
        MessageBuilder messageBuilder = new MessageBuilder();
        ImageDescriptorProto.ImageDescriptorProtoStruct.Builder objectBuilder = messageBuilder.initRoot(ImageDescriptorProto.ImageDescriptorProtoStruct.factory);

        objectBuilder.setDescription(object.getDescription());
        objectBuilder.setTitle(object.getTitle());
        objectBuilder.setUrl(object.getUrl());
        objectBuilder.setType(ImageDescriptorProto.ImageTypeProtoStruct.valueOf(object.getType().name()));
        objectBuilder.setWidth(object.getWidth());
        objectBuilder.setHeight(object.getHeight());

        List<String> tagList = object.getTags();
        TextList.Builder tags = objectBuilder.initTags(tagList.size());

        for (int i = 0; i < tagList.size(); i++) {
            tags.set(i, new Text.Reader(tagList.get(i)));
        }
        return messageBuilder;
    }
    private static MessageBuilder getHeartDataMessageBuilder(HeartData object) {
        MessageBuilder messageBuilder = new MessageBuilder();

        HeartDataProto.HeartDataProtoStruct.Builder heartDataBuilder = messageBuilder.initRoot(HeartDataProto.HeartDataProtoStruct.factory);
        heartDataBuilder.setStatus(object.getStatus());

        HeartDataProto.BodyProto.Builder bodyBuilder = heartDataBuilder.initBody();
        bodyBuilder.setMore(object.getBody().isMore());
        bodyBuilder.setOffset(object.getBody().getOffset());

        StructList.Builder<HeartDataProto.SeriesProto.Builder> seriesListBuilder = bodyBuilder.initSeries(object.getBody().getSeries().length);
        for (int i = 0; i < object.getBody().getSeries().length; i++) {
            HeartDataProto.SeriesProto.Builder seriesBuilder = seriesListBuilder.get(i);

            Series series = object.getBody().getSeries()[i];
            seriesBuilder.setDeviceid(series.getDeviceid());
            seriesBuilder.setModel(series.getModel());
            seriesBuilder.setHeartrate(series.getHeartrate());
            seriesBuilder.setTimestamp(series.getTimestamp());
            seriesBuilder.setTimezone(series.getTimezone());

            Ecg ecg = series.getEcg();
            HeartDataProto.EcgProto.Builder ecgBuilder = seriesBuilder.initEcg();
            ecgBuilder.setSignalid(ecg.getSignalid());
            ecgBuilder.setAfib(ecg.getAfib());

            BloodPressure bloodPressure = series.getBloodpressure();
            HeartDataProto.BloodPressureProto.Builder bpBuilder = seriesBuilder.initBloodpressure();
            bpBuilder.setDiastole(bloodPressure.getDiastole());
            bpBuilder.setSystole(bloodPressure.getSystole());
        }
        return messageBuilder;
    }
    private static Series getSeries(HeartDataProto.SeriesProto.Reader s) {
        Series series = new Series();
        series.setDeviceid(s.getDeviceid().toString());
        series.setModel(s.getModel());
        series.setHeartrate(s.getHeartrate());
        series.setTimestamp(s.getTimestamp());
        series.setTimezone(s.getTimezone().toString());

        HeartDataProto.EcgProto.Reader ecgReader = s.getEcg();
        Ecg ecg = new Ecg();
        ecg.setSignalid(ecgReader.getSignalid());
        ecg.setAfib(ecgReader.getAfib());
        series.setEcg(ecg);

        HeartDataProto.BloodPressureProto.Reader bpReader = s.getBloodpressure();
        BloodPressure bloodPressure = new BloodPressure();
        bloodPressure.setDiastole(bpReader.getDiastole());
        bloodPressure.setSystole(bpReader.getSystole());
        series.setBloodpressure(bloodPressure);
        return series;
    }
    private static MessageBuilder getLocationDataMessageBuilder(LocationData object) {
        MessageBuilder messageBuilder = new MessageBuilder();
        LocationDataProto.LocationDataProtoStruct.Builder objectBuilder = messageBuilder.initRoot(LocationDataProto.LocationDataProtoStruct.factory);
        objectBuilder.setName(object.getName());
        objectBuilder.setDescription(object.getDescription());
        objectBuilder.setEncodingType(object.getEncodingType());

        Location location = object.getLocation();
        LocationDataProto.LocationProtoStruct.Builder locationBuilder = objectBuilder.initLocation();
        locationBuilder.setType(location.getType());

        Geometry geometry = location.getGeometry();
        LocationDataProto.GeometryProtoStruct.Builder geometryBuilder = locationBuilder.initGeometry();
        geometryBuilder.setType(geometry.getType());

        PrimitiveList.Double.Builder coordinatesBuilder = geometryBuilder.initCoordinates(geometry.getCoordinates().length);
        coordinatesBuilder.set(0, geometry.getCoordinates()[0]);
        coordinatesBuilder.set(1, geometry.getCoordinates()[1]);
        return messageBuilder;
    }
    private static MessageBuilder getImageMessageBuilder(ImageData object) {
        MessageBuilder messageBuilder = new MessageBuilder();
        ImageProto.ImageProtoStruct.Builder objectBuilder = messageBuilder.initRoot(ImageProto.ImageProtoStruct.factory);
        objectBuilder.setName(object.getName());
        objectBuilder.setImageBytes(object.getImageBytes());
        return messageBuilder;
    }
    private static MessageBuilder getSmartLightControllerMessageBuilder(SmartLightController object) {
        MessageBuilder messageBuilder = new MessageBuilder();
        SmartLightControllerProto.SmartLightControllerProtoStruct.Builder objectBuilder = messageBuilder.initRoot(SmartLightControllerProto.SmartLightControllerProtoStruct.factory);

        objectBuilder.setOn(object.isOn());
        objectBuilder.setSat(object.getSat());
        objectBuilder.setBri(object.getBri());
        objectBuilder.setHue(object.getHue());
        return messageBuilder;
    }
    private static MessageBuilder getHttpResponseMessageBuilder(HttpResponse object) {
        MessageBuilder messageBuilder = new MessageBuilder();
        HttpResponseProto.HttpResponseProtoStruct.Builder objectBuilder = messageBuilder.initRoot(HttpResponseProto.HttpResponseProtoStruct.factory);

        String[] headers = object.getHeaderLines();
        TextList.Builder headerLines = objectBuilder.initHeaderLines(headers.length);

        for (int i = 0; i < headers.length; i++) {
            headerLines.set(i, new Text.Reader(headers[i]));
        }

        objectBuilder.setStatusLine(object.getStatusLine());
        objectBuilder.setResponseBody(object.getResponseBody());
        return messageBuilder;
    }


    private MessageBuilder getMessageBuilderForClass(Object object) {
        return switch (object.getClass().getSimpleName()) {
            case "Person" -> getPersonMessageBuilder((Person) object);
            case "SensorValue" -> getSensorValueMessageBuilder((SensorValue) object);
            case "ImageData" -> getImageMessageBuilder((ImageData) object);
            case "HttpResponse" -> getHttpResponseMessageBuilder((HttpResponse) object);
            case "SmartLightController" -> getSmartLightControllerMessageBuilder((SmartLightController) object);
            case "HeartData" -> getHeartDataMessageBuilder((HeartData) object);
            case "ImageDescriptor" -> getImageDescriptorMessageBuilder((ImageDescriptor) object);
            case "LocationData" -> getLocationDataMessageBuilder((LocationData) object);
            default -> null;
        };
    }
}
