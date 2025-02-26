package at.fhv.kabi.samples.formatwriter.tlv;

import at.fhv.kabi.samples.formatwriter.FormatWriter;
import at.fhv.kabi.samples.models.BufferedImage.ImageData;
import at.fhv.kabi.samples.models.HeartData.*;
import at.fhv.kabi.samples.models.*;
import at.fhv.kabi.samples.models.ImageDescriptor.ImageDescriptor;
import at.fhv.kabi.samples.models.LocationData.Geometry;
import at.fhv.kabi.samples.models.LocationData.Location;
import at.fhv.kabi.samples.models.LocationData.LocationData;
import com.fasterxml.jackson.core.FormatSchema;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class TLVWriter extends FormatWriter {
    
    private static List<Integer> types = new ArrayList<>();

    public TLVWriter() {
        super("TLV",".tlv");
        types = Arrays.asList(0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18, 0x19, 0x20);
    }

    @Override
    public void writePersonToFile(Person object, File file) throws IOException {
        File f = writeBox(file, object);
        Person result = readPersonFromFile(f, null);
        checkAndPrintOutput(result.toFormattedString(), object.toFormattedString());
        writeFileSizes(file.getParentFile().getPath(), f);
    }

    @Override
    public Person readPersonFromFile(File file, FormatSchema schema) throws IOException {
        TlvBox parsedBox = readBox(file);
        TlvBox parsedPerson = parsedBox.getObjectValue(types.get(8));

        Person p = new Person();
        p.setFn(parsedPerson.getStringValue(types.get(0)));
        p.setBday(parsedPerson.getStringValue(types.get(1)));
        p.setGender(parsedPerson.getStringValue(types.get(2)));
        p.setAdr(parsedPerson.getStringValue(types.get(3)));
        p.setEmail(parsedPerson.getStringValue(types.get(4)));
        p.setTel(parsedPerson.getStringValue(types.get(5)));
        p.setLang(parsedPerson.getStringValue(types.get(6)));
        p.setVersion(parsedPerson.getStringValue(types.get(7)));

        return p;
    }

    @Override
    public void writeSensorValueToFile(SensorValue object, File file) throws IOException {
        File f = writeBox(file, object);
        SensorValue result = readSensorValueFromFile(f, null);
        checkAndPrintOutput(result.toFormattedString(), object.toFormattedString());
        writeFileSizes(file.getParentFile().getPath(), f);
    }

    @Override
    public SensorValue readSensorValueFromFile(File file, FormatSchema schema) throws IOException {
        TlvBox parsedBox = readBox(file);
        TlvBox parsedPerson = parsedBox.getObjectValue(types.get(5));

        SensorValue s = new SensorValue();
        s.setDeviceId(parsedPerson.getStringValue(types.get(0)));
        s.setTimestamp(parsedPerson.getLongValue(types.get(1)));
        s.setTemperature(parsedPerson.getDoubleValue(types.get(2)));
        s.setHumidity(parsedPerson.getDoubleValue(types.get(3)));

        return s;
    }

    @Override
    public void writeImageDescriptorToFile(ImageDescriptor object, File file) throws Exception {
        File f = writeBox(file, object);
        ImageDescriptor result = readImageDescriptorFromFile(f, null);
        checkAndPrintOutput(result.toFormattedString(), object.toFormattedString());
        writeFileSizes(file.getParentFile().getPath(), f);
    }

    @Override
    public ImageDescriptor readImageDescriptorFromFile(File file, FormatSchema schema) throws Exception {
        TlvBox parsedBox = readBox(file);
        TlvBox parsedImage = parsedBox.getObjectValue(types.get(7));

        ImageDescriptor i = new ImageDescriptor();
        i.setTitle(parsedImage.getStringValue(types.get(0)));
        i.setDescription(parsedImage.getStringValue(types.get(1)));
        i.setType(ImageDescriptor.ImageType.valueOf(parsedImage.getStringValue(types.get(2))));
        i.setUrl(parsedImage.getStringValue(types.get(3)));
        i.setHeight(parsedImage.getIntValue(types.get(4)));
        i.setWidth(parsedImage.getIntValue(types.get(5)));

        TlvBox parsedTags = parsedImage.getObjectValue(types.get(6));
        List<String> tags = new LinkedList<>();
        for (int idx = 0; idx <= 10; idx++) {
            String value = parsedTags.getStringValue(idx);

            if (value != null) { tags.add(value); } else { break; }
        }

        i.setTags(tags);

        return i;
    }

    @Override
    public void writeSmartLightControllerToFile(SmartLightController object, File file) throws Exception {
        File f = writeBox(file, object);
        SmartLightController result = readSmartLightControllerFromFile(f, null);
        checkAndPrintOutput(result.toFormattedString(), object.toFormattedString());
        writeFileSizes(file.getParentFile().getPath(), f);
    }

    @Override
    public SmartLightController readSmartLightControllerFromFile(File file, FormatSchema schema) throws Exception {
        TlvBox parsedBox = readBox(file);
        TlvBox parsed = parsedBox.getObjectValue(types.get(4));

        SmartLightController obj = new SmartLightController();
        obj.setOn(Boolean.parseBoolean(parsed.getStringValue(types.get(0))));
        obj.setSat(parsed.getIntValue(types.get(1)));
        obj.setBri(parsed.getIntValue(types.get(2)));
        obj.setHue(parsed.getIntValue(types.get(3)));

        return obj;
    }

    @Override
    public void writeImageToFile(ImageData object, File file) throws Exception {
        File f = writeBox(file, object);
        ImageData result = readImageFromFile(f, null);
        checkAndPrintOutput(result.toFormattedString(), object.toFormattedString());
        writeFileSizes(file.getParentFile().getPath(), f);
    }

    @Override
    public ImageData readImageFromFile(File file, FormatSchema schema) throws Exception {
        TlvBox parsedBox = readBox(file);
        TlvBox parsed = parsedBox.getObjectValue(types.get(2));

        ImageData obj = new ImageData();
        obj.setName(parsed.getStringValue(types.get(0)));
        obj.setImageBytes(parsed.getBytesValue(types.get(1)));

        return obj;
    }

    @Override
    public void writeLocationDataToFile(LocationData object, File file) throws Exception {
        File f = writeBox(file, object);
        LocationData result = readLocationDataFromFile(f, null);
        checkAndPrintOutput(result.toFormattedString(), object.toFormattedString());
        writeFileSizes(file.getParentFile().getPath(), f);
    }

    @Override
    public LocationData readLocationDataFromFile(File file, FormatSchema schema) throws Exception {
        TlvBox parsedBox = readBox(file);
        TlvBox parsed = parsedBox.getObjectValue(types.get(9));

        LocationData obj = new LocationData();
        obj.setName(parsed.getStringValue(types.get(0)));
        obj.setDescription(parsed.getStringValue(types.get(1)));
        obj.setEncodingType(parsed.getStringValue(types.get(2)));

        TlvBox locationBox = parsed.getObjectValue(types.get(8));
        TlvBox geometryBox = locationBox.getObjectValue(types.get(7));
        Geometry geometry = new Geometry(geometryBox.getStringValue(types.get(3)), new double[]{
                geometryBox.getDoubleValue(types.get(4)),
                geometryBox.getDoubleValue(types.get(5))
        });

        obj.setLocation(new Location(locationBox.getStringValue(types.get(6)), geometry));
        return obj;
    }

    @Override
    public void writeHeartDataToFile(HeartData object, File file) throws Exception {
        File f = writeBox(file, object);
        HeartData result = readHeartDataFromFile(f, null);
        checkAndPrintOutput(result.toFormattedString(), object.toFormattedString());
        writeFileSizes(file.getParentFile().getPath(), f);
    }

    @Override
    public HeartData readHeartDataFromFile(File file, FormatSchema schema) throws Exception {
        TlvBox parsed = readBox(file).getObjectValue(types.get(17));
        TlvBox bodyBox = parsed.getObjectValue(types.get(16));
        TlvBox seriesBox = bodyBox.getObjectValue(types.get(12));
        TlvBox ecgBox = seriesBox.getObjectValue(types.get(7));
        TlvBox bpBox = seriesBox.getObjectValue(types.get(8));

        Ecg ecg = new Ecg(ecgBox.getIntValue(types.get(1)), ecgBox.getIntValue(types.get(2)));
        BloodPressure bloodPressure = new BloodPressure(bpBox.getIntValue(types.get(3)), bpBox.getIntValue(types.get(4)));
        Series[] series = new Series[1];
        series[0] = new Series(
                seriesBox.getStringValue(types.get(5)),
                seriesBox.getIntValue(types.get(6)),
                ecg,
                bloodPressure,
                seriesBox.getIntValue(types.get(9)),
                seriesBox.getLongValue(types.get(10)),
                seriesBox.getStringValue(types.get(11)));

        Body body = new Body(series, Boolean.parseBoolean(bodyBox.getStringValue(types.get(13))), bodyBox.getIntValue(types.get(14)));
        return new HeartData(parsed.getIntValue(types.get(15)), body);
    }

    @Override
    public void writeHttpResponseToFile(HttpResponse object, File file) throws IOException {
        File f = writeBox(file, object);
        HttpResponse result = readHttpResponseFromFile(f, null);
        checkAndPrintOutput(result.toFormattedString(), object.toFormattedString());
        writeFileSizes(file.getParentFile().getPath(), f);
    }

    @Override
    public HttpResponse readHttpResponseFromFile(File file, FormatSchema schema) throws IOException {
        TlvBox parsedBox = readBox(file);
        TlvBox parsedResponse = parsedBox.getObjectValue(types.get(3));

        HttpResponse response = new HttpResponse();
        response.setStatusLine(parsedResponse.getStringValue(types.get(0)));
        response.setHeaderLines(parsedResponse.getStringValue(types.get(1)).split(", "));
        response.setResponseBody(parsedResponse.getStringValue(types.get(2)));

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
            os.write(getBoxForClass(object).serialize());
            long finish = System.nanoTime();

            return finish - start;
        }
    }

    // Skip since the methods used in getSerializationSpeed and write are exactly the same
    @Override
    public void compareFileSizesOfWritingMethods(Object object, File file) {}

    private TlvBox readBox(File file) throws IOException {
        try(FileInputStream is = new FileInputStream(file)) {
            byte[] serialized = is.readAllBytes();
            return TlvBox.parse(serialized, 0, serialized.length);
        }
    }

    private File writeBox(File file, Object object) throws IOException {
        File f = new File(file + FILE_EXTENSION);
        TlvBox box = getBoxForClass(object);

        try(FileOutputStream os = new FileOutputStream(f)) {
            os.write(box.serialize());
        }
        return f;
    }

    private static TlvBox getImageDescriptorBox(ImageDescriptor object) {
        TlvBox tagsBox = new TlvBox();
        int i = 0;
        for (String s : object.getTags()) {
            tagsBox.putStringValue(i, s);
            i++;
        }

        TlvBox box = new TlvBox();
        box.putStringValue(types.get(0), object.getTitle());
        box.putStringValue(types.get(1), object.getDescription());
        box.putStringValue(types.get(2), object.getType().toString());
        box.putStringValue(types.get(3), object.getUrl());
        box.putIntValue(types.get(4), object.getHeight());
        box.putIntValue(types.get(5), object.getWidth());
        box.putObjectValue(types.get(6), tagsBox);

        TlvBox boxes = new TlvBox();
        boxes.putObjectValue(types.get(7), box);
        return boxes;
    }
    private static TlvBox getHttpResponseBox(HttpResponse object) {
        String joined = Arrays.toString(object.getHeaderLines());

        TlvBox box = new TlvBox();
        box.putStringValue(types.get(0), object.getStatusLine());
        box.putStringValue(types.get(1), joined.substring(1, joined.length()-1));
        box.putStringValue(types.get(2), object.getResponseBody());

        TlvBox boxes = new TlvBox();
        boxes.putObjectValue(types.get(3), box);
        return boxes;
    }
    private static TlvBox getHeartDataBox(HeartData object) {
        TlvBox bodyBox = getBodyBox(object);
        bodyBox.putStringValue(types.get(13), String.valueOf(object.getBody().isMore()));
        bodyBox.putIntValue(types.get(14), object.getBody().getOffset());

        TlvBox box = new TlvBox();
        box.putIntValue(types.get(15), object.getStatus());
        box.putObjectValue(types.get(16), bodyBox);

        TlvBox boxes = new TlvBox();
        boxes.putObjectValue(types.get(17), box);
        return boxes;
    }
    private static TlvBox getBodyBox(HeartData object) {
        Series series = object.getBody().getSeries()[0];

        TlvBox ecgBox = new TlvBox();
        ecgBox.putIntValue(types.get(1), series.getEcg().getSignalid());
        ecgBox.putIntValue(types.get(2), series.getEcg().getAfib());

        TlvBox bpBox = new TlvBox();
        bpBox.putIntValue(types.get(3), series.getBloodpressure().getDiastole());
        bpBox.putIntValue(types.get(4), series.getBloodpressure().getSystole());

        TlvBox seriesBox = new TlvBox();
        seriesBox.putStringValue(types.get(5), series.getDeviceid());
        seriesBox.putIntValue(types.get(6), series.getModel());
        seriesBox.putObjectValue(types.get(7), ecgBox);
        seriesBox.putObjectValue(types.get(8), bpBox);
        seriesBox.putIntValue(types.get(9), series.getHeartrate());
        seriesBox.putLongValue(types.get(10), series.getTimestamp());
        seriesBox.putStringValue(types.get(11), series.getTimezone());

        TlvBox bodyBox = new TlvBox();
        bodyBox.putObjectValue(types.get(12), seriesBox);
        return bodyBox;
    }
    private static TlvBox getImageBox(ImageData object) {
        TlvBox box = new TlvBox();
        box.putStringValue(types.get(0), object.getName());
        box.putBytesValue(types.get(1), object.getImageBytes());

        TlvBox boxes = new TlvBox();
        boxes.putObjectValue(types.get(2), box);
        return boxes;
    }
    private static TlvBox getLocationDataBox(LocationData object) {
        TlvBox box = new TlvBox();
        box.putStringValue(types.get(0), object.getName());
        box.putStringValue(types.get(1), object.getDescription());
        box.putStringValue(types.get(2), object.getEncodingType());

        Geometry geometry = object.getLocation().getGeometry();
        TlvBox geometryBox = new TlvBox();
        geometryBox.putStringValue(types.get(3), geometry.getType());
        geometryBox.putDoubleValue(types.get(4), geometry.getCoordinates()[0]);
        geometryBox.putDoubleValue(types.get(5), geometry.getCoordinates()[1]);

        Location location = object.getLocation();
        TlvBox locationBox = new TlvBox();
        locationBox.putStringValue(types.get(6), location.getType());
        locationBox.putObjectValue(types.get(7), geometryBox);
        box.putObjectValue(types.get(8), locationBox);

        TlvBox boxes = new TlvBox();
        boxes.putObjectValue(types.get(9), box);
        return boxes;
    }
    private static TlvBox getSmartLightControllerBox(SmartLightController object) {
        TlvBox box = new TlvBox();
        box.putStringValue(types.get(0), String.valueOf(object.isOn()));
        box.putIntValue(types.get(1), object.getSat());
        box.putIntValue(types.get(2), object.getBri());
        box.putIntValue(types.get(3), object.getHue());

        TlvBox boxes = new TlvBox();
        boxes.putObjectValue(types.get(4), box);
        return boxes;
    }
    private static TlvBox getPersonBox(Person object) {
        TlvBox box = new TlvBox();
        box.putStringValue(types.get(0), object.getFn());
        box.putStringValue(types.get(1), object.getBday());
        box.putStringValue(types.get(2), object.getGender());
        box.putStringValue(types.get(3), object.getAdr());
        box.putStringValue(types.get(4), object.getEmail());
        box.putStringValue(types.get(5), object.getTel());
        box.putStringValue(types.get(6), object.getLang());
        box.putStringValue(types.get(7), object.getVersion());

        TlvBox boxes = new TlvBox();
        boxes.putObjectValue(types.get(8), box);
        return boxes;
    }
    private static TlvBox getSensorValueBox(SensorValue object) {
        TlvBox box = new TlvBox();
        box.putStringValue(types.get(0), object.getDeviceId());
        box.putLongValue(types.get(1), object.getTimestamp());
        box.putDoubleValue(types.get(2), object.getTemperature());
        box.putDoubleValue(types.get(3), object.getHumidity());

        TlvBox boxes = new TlvBox();
        boxes.putObjectValue(types.get(5), box);
        return boxes;
    }

    private TlvBox getBoxForClass(Object object) {
        return switch (object.getClass().getSimpleName()) {
            case "Person" -> getPersonBox((Person) object);
            case "SensorValue" -> getSensorValueBox((SensorValue) object);
            case "ImageData" -> getImageBox((ImageData) object);
            case "HttpResponse" -> getHttpResponseBox((HttpResponse) object);
            case "SmartLightController" -> getSmartLightControllerBox((SmartLightController) object);
            case "HeartData" -> getHeartDataBox((HeartData) object);
            case "ImageDescriptor" -> getImageDescriptorBox((ImageDescriptor) object);
            case "LocationData" -> getLocationDataBox((LocationData) object);
            default -> null;
        };
    }
}
