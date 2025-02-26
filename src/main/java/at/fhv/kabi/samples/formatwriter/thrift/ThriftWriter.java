package at.fhv.kabi.samples.formatwriter.thrift;

import at.fhv.kabi.samples.formatwriter.FormatWriter;
import at.fhv.kabi.samples.formatwriter.thrift.at.fhv.kabi.samples.formatwriter.thrift.*;
import at.fhv.kabi.samples.models.BufferedImage.ImageData;
import at.fhv.kabi.samples.models.HeartData.*;
import at.fhv.kabi.samples.models.*;
import at.fhv.kabi.samples.models.ImageDescriptor.ImageDescriptor;
import at.fhv.kabi.samples.models.LocationData.Geometry;
import at.fhv.kabi.samples.models.LocationData.Location;
import at.fhv.kabi.samples.models.LocationData.LocationData;
import com.fasterxml.jackson.core.FormatSchema;
import org.apache.thrift.TBase;
import org.apache.thrift.TDeserializer;
import org.apache.thrift.TException;
import org.apache.thrift.TSerializer;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThriftWriter extends FormatWriter {


    public ThriftWriter() {
        super("Thrift", ".thrift");
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
		PersonThrift thriftObj = (PersonThrift) read(new PersonThrift(), file);
        assert thriftObj != null;

        Person person = new Person();
        person.setFn(thriftObj.getFn());
        person.setBday(thriftObj.getBday());
        person.setAdr(thriftObj.getAdr());
        person.setGender(thriftObj.getGender());
        person.setEmail(thriftObj.getEmail());
        person.setTel(thriftObj.getTel());
        person.setLang(thriftObj.getLang());
        person.setVersion(thriftObj.getVersion());

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
		SensorValueThrift thriftObj = (SensorValueThrift) read(new SensorValueThrift(), file);
        assert thriftObj != null;

        SensorValue sensorValue = new SensorValue();
        sensorValue.setDeviceId(thriftObj.getDeviceId());
        sensorValue.setTimestamp(thriftObj.getTimestamp());
        sensorValue.setTemperature(thriftObj.getTemperature());
        sensorValue.setHumidity(thriftObj.getHumidity());

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
        ImageDescriptorThrift thriftObj = (ImageDescriptorThrift) read(new ImageDescriptorThrift(), file);
        assert thriftObj != null;

        ImageDescriptor imageDescriptor = new ImageDescriptor();
        imageDescriptor.setTitle(thriftObj.getTitle());
        imageDescriptor.setDescription(thriftObj.getDescription());
        imageDescriptor.setUrl(thriftObj.getUrl());
        imageDescriptor.setTags(thriftObj.getTags());
        imageDescriptor.setType(ImageDescriptor.ImageType.valueOf(thriftObj.getType().name()));
        imageDescriptor.setWidth(thriftObj.getWidth());
        imageDescriptor.setHeight(thriftObj.getHeight());

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
    	SmartLightControllerThrift thriftObj = (SmartLightControllerThrift) read(new SmartLightControllerThrift(), file);
        assert thriftObj != null;

        return new SmartLightController(thriftObj.isOn(), thriftObj.getSat(), thriftObj.getBri(), thriftObj.getHue());
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
        ImageThrift thriftObj = (ImageThrift) read(new ImageThrift(), file);
        assert thriftObj != null;

        return new ImageData(thriftObj.getName(), thriftObj.getImageBytes());
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
        LocationDataThrift thriftObj = (LocationDataThrift) read(new LocationDataThrift(), file);
        assert thriftObj != null;

        LocationThrift locationThrift = thriftObj.getLocation();

        Geometry geometry = new Geometry();
        double[] coordinates = new double[]{
                locationThrift.getGeometry().getCoordinates().get(0),
                locationThrift.getGeometry().getCoordinates().get(1)
        };
        geometry.setCoordinates(coordinates);
        geometry.setType(locationThrift.getGeometry().getType());

        return new LocationData(
                thriftObj.getName(), thriftObj.getDescription(),
                thriftObj.getEncodingType(), new Location(locationThrift.getType(), geometry));
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
        HeartDataThrift thriftObj = (HeartDataThrift) read(new HeartDataThrift(), file);
        assert thriftObj != null;

        BodyThrift bodyThrift = thriftObj.getBody();
        Body body = new Body();
        body.setMore(bodyThrift.isMore());
        body.setOffset(bodyThrift.getOffset());

        List<Series> seriesList = new ArrayList<>();
        for (SeriesThrift seriesThrift : bodyThrift.getSeries()) {
            seriesList.add(getSeries(seriesThrift));
        }

        body.setSeries(seriesList.toArray(new Series[0]));

        return new HeartData(thriftObj.getStatus(), body);
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
        HttpResponseThrift thriftObj = (HttpResponseThrift) read(new HttpResponseThrift(), file);
        assert thriftObj != null;

        HttpResponse response = new HttpResponse();
        response.setStatusLine(thriftObj.getStatusLine());
        response.setHeaderLines(thriftObj.getHeaderLines().toArray(new String[0]));
        response.setResponseBody(thriftObj.getResponseBody());

        return response;
    }

    @Override
    public void measureTime(Object object, File file) throws Exception {
        writeTimeElapsed(getSerializationSpeed(object), file.getParentFile().getPath());
    }

    @Override
    public long getSerializationSpeed(Object object) throws Exception {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            long start = System.nanoTime();
            TSerializer serializer = new TSerializer();
            os.write(serializer.serialize(getThriftObject(object)));
            long finish = System.nanoTime();

            return finish - start;
        }
    }

    // Skip since the methods used in getSerializationSpeed and write are exactly the same
    @Override
    public void compareFileSizesOfWritingMethods(Object object, File file) {}

    private File write(File file, Object object) {
        File f = new File(file + FILE_EXTENSION);
        TBase<?, ?>  thriftObj = getThriftObject(object);

        try(FileOutputStream os = new FileOutputStream(f)) {
            TSerializer serializer = new TSerializer();
            byte[] b = serializer.serialize(thriftObj);
            os.write(b);
        } catch (TException | IOException e) {
            throw new RuntimeException(e);
        }
        return f;
    }

    private TBase<?, ?> read(TBase<?, ?>  thriftObj, File f) {
        try(FileInputStream is = new FileInputStream(f)) {
            byte[] buffer = is.readAllBytes();

            TDeserializer deserializer = new TDeserializer();
            deserializer.deserialize(thriftObj, buffer);

            return thriftObj;
        } catch (Exception e) {
            return null;
        }
    }

    private static PersonThrift getPersonThrift(Person object) {
        PersonThrift thriftObj = new PersonThrift();
        thriftObj.setFn(object.getFn());
        thriftObj.setBday(object.getBday());
        thriftObj.setGender(object.getGender());
        thriftObj.setAdr(object.getAdr());
        thriftObj.setEmail(object.getEmail());
        thriftObj.setTel(object.getTel());
        thriftObj.setLang(object.getLang());
        thriftObj.setVersion(object.getVersion());
        return thriftObj;
    }
    private static SensorValueThrift getSensorValueThrift(SensorValue object) {
        SensorValueThrift thriftObj = new SensorValueThrift();
        thriftObj.setDeviceId(object.getDeviceId());
        thriftObj.setHumidity(object.getHumidity());
        thriftObj.setTemperature(object.getTemperature());
        thriftObj.setTimestamp(object.getTimestamp());
        return thriftObj;
    }
    private static ImageDescriptorThrift getImageDescriptorThrift(ImageDescriptor object) {
        ImageDescriptorThrift thriftObj = new ImageDescriptorThrift();
        thriftObj.setDescription(object.getDescription());
        thriftObj.setTitle(object.getTitle());
        thriftObj.setWidth(object.getWidth());
        thriftObj.setHeight(object.getHeight());
        thriftObj.setTags(object.getTags());
        thriftObj.setType(ImageTypeThrift.valueOf(object.getType().name()));
        thriftObj.setUrl(object.getUrl());
        return thriftObj;
    }
    private static SmartLightControllerThrift getSmartLightControllerThrift(SmartLightController object) {
        SmartLightControllerThrift thriftObj = new SmartLightControllerThrift();
        thriftObj.setOn(object.isOn());
        thriftObj.setBri(object.getBri());
        thriftObj.setHue(object.getHue());
        thriftObj.setSat(object.getSat());
        return thriftObj;
    }
    private static ImageThrift getImageThrift(ImageData object) {
        ImageThrift thriftObj = new ImageThrift();
        thriftObj.setName(object.getName());
        thriftObj.setImageBytes(object.getImageBytes());
        return thriftObj;
    }
    private static LocationDataThrift getLocationDataThrift(LocationData object) {
        LocationDataThrift thriftObj = new LocationDataThrift();
        thriftObj.setName(object.getName());
        thriftObj.setDescription(object.getDescription());
        thriftObj.setEncodingType(object.getEncodingType());

        Location location = object.getLocation();
        Geometry geometry = location.getGeometry();

        GeometryThrift geometryThrift = new GeometryThrift();
        geometryThrift.setType(geometry.getType());
        geometryThrift.setCoordinates(Arrays.stream(geometry.getCoordinates()).boxed().toList());

        LocationThrift locationThrift = new LocationThrift();
        locationThrift.setType(location.getType());
        locationThrift.setGeometry(geometryThrift);

        thriftObj.setLocation(locationThrift);
        return thriftObj;
    }
    private static SeriesThrift getSeriesThrift(Series series) {
        SeriesThrift seriesThrift = new SeriesThrift();
        seriesThrift.setDeviceid(series.getDeviceid());
        seriesThrift.setModel(series.getModel());
        seriesThrift.setHeartrate(series.getHeartrate());
        seriesThrift.setTimestamp(series.getTimestamp());
        seriesThrift.setTimezone(series.getTimezone());

        EcgThrift ecgThrift = new EcgThrift();
        ecgThrift.setSignalid(series.getEcg().getSignalid());
        ecgThrift.setAfib(series.getEcg().getAfib());
        seriesThrift.setEcg(ecgThrift);

        BloodPressureThrift bpThrift = new BloodPressureThrift();
        bpThrift.setDiastole(series.getBloodpressure().getDiastole());
        bpThrift.setSystole(series.getBloodpressure().getSystole());
        seriesThrift.setBloodpressure(bpThrift);
        return seriesThrift;
    }
    private static HttpResponseThrift getHttpResponseThrift(HttpResponse object) {
        HttpResponseThrift thriftObj = new HttpResponseThrift();
        thriftObj.setStatusLine(object.getStatusLine());
        thriftObj.setHeaderLines(Arrays.stream(object.getHeaderLines()).toList());
        thriftObj.setResponseBody(object.getResponseBody());
        return thriftObj;
    }
    private static Series getSeries(SeriesThrift seriesThrift) {
        Series series = new Series();
        series.setDeviceid(seriesThrift.getDeviceid());
        series.setModel(seriesThrift.getModel());
        series.setHeartrate(seriesThrift.getHeartrate());
        series.setTimestamp(seriesThrift.getTimestamp());
        series.setTimezone(seriesThrift.getTimezone());

        Ecg ecg = new Ecg();
        ecg.setSignalid(seriesThrift.getEcg().getSignalid());
        ecg.setAfib(seriesThrift.getEcg().getAfib());
        series.setEcg(ecg);

        BloodPressure bp = new BloodPressure();
        bp.setDiastole(seriesThrift.getBloodpressure().getDiastole());
        bp.setSystole(seriesThrift.getBloodpressure().getSystole());
        series.setBloodpressure(bp);
        return series;
    }
    private static HeartDataThrift getHeartDataThrift(HeartData object) {
        HeartDataThrift thriftObj = new HeartDataThrift();
        thriftObj.setStatus(object.getStatus());

        Body body = object.getBody();
        BodyThrift bodyThrift = new BodyThrift();
        bodyThrift.setMore(body.isMore());
        bodyThrift.setOffset(body.getOffset());

        List<SeriesThrift> seriesThriftList = new ArrayList<>();
        for (Series series : body.getSeries()) {
            seriesThriftList.add(getSeriesThrift(series));
        }

        bodyThrift.setSeries(seriesThriftList);
        thriftObj.setBody(bodyThrift);
        return thriftObj;
    }


    private TBase<?, ?> getThriftObject(Object object) {
        return switch (object.getClass().getSimpleName()) {
            case "Person" -> getPersonThrift((Person) object);
            case "SensorValue" -> getSensorValueThrift((SensorValue) object);
            case "ImageData" -> getImageThrift((ImageData) object);
            case "HttpResponse" -> getHttpResponseThrift((HttpResponse) object);
            case "SmartLightController" -> getSmartLightControllerThrift((SmartLightController) object);
            case "HeartData" -> getHeartDataThrift((HeartData) object);
            case "ImageDescriptor" -> getImageDescriptorThrift((ImageDescriptor) object);
            case "LocationData" -> getLocationDataThrift((LocationData) object);
            default -> null;
        };
    }
}
