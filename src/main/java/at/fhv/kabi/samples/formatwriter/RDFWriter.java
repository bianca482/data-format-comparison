package at.fhv.kabi.samples.formatwriter;

import at.fhv.kabi.samples.models.BufferedImage.ImageData;
import at.fhv.kabi.samples.models.HeartData.*;
import at.fhv.kabi.samples.models.*;
import at.fhv.kabi.samples.models.ImageDescriptor.ImageDescriptor;
import at.fhv.kabi.samples.models.LocationData.Geometry;
import at.fhv.kabi.samples.models.LocationData.Location;
import at.fhv.kabi.samples.models.LocationData.LocationData;
import com.fasterxml.jackson.core.FormatSchema;
import org.apache.jena.rdf.model.*;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;
import org.apache.jena.vocabulary.VCARD;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

public class RDFWriter extends FormatWriter {

    private final String namespace = "http://a/";
    private final RDFFormat format;
    private final String formatName;

    public RDFWriter(String name, String fileExtension, String formatName) {
        super(name, fileExtension);

        this.formatName = formatName;
        this.format = formatName.equals(Lang.TURTLE.getName()) ? RDFFormat.TURTLE : RDFFormat.RDFXML;
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
        Model model = ModelFactory.createDefaultModel();
        Resource node = read(model, file);
        assert node != null;

        return new Person(
                node.getProperty(VCARD.FN).getString(),
                node.getProperty(VCARD.BDAY).getString(),
                getString(model, node, "gender"),
                node.getProperty(VCARD.ADR).getString(),
                node.getProperty(VCARD.EMAIL).getString(),
                node.getProperty(VCARD.TEL).getString(),
                getString(model, node, "lang"),
                getString(model, node, "version"));
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
        Model model = ModelFactory.createDefaultModel();
        Resource node = read(model, file);
        assert node != null;

        return new SensorValue(
                getString(model, node, "deviceId"),
                node.getProperty(model.createProperty(namespace + "timestamp")).getLong(),
                node.getProperty(model.createProperty(namespace + "temperature")).getDouble(),
                node.getProperty(model.createProperty(namespace + "humidity")).getDouble());
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
        Model model = ModelFactory.createDefaultModel();
        Resource node = read(model, file);
        assert node != null;

        Resource tagsResource= node.getProperty(model.createProperty(namespace + "tags")).getResource();
        List<String> tags = readListFromModel(tagsResource);

        return new ImageDescriptor(
                getString(model, node, "name"), getString(model, node, "description"),
                ImageDescriptor.ImageType.valueOf(getString(model, node, "type")), getString(model, node, "url"),
                getInt(model, node, "width"), getInt(model, node, "height"), tags);
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
        Model model = ModelFactory.createDefaultModel();
        Resource node  = read(model, file);
        assert node != null;

        return new SmartLightController(
                Boolean.parseBoolean(node.getProperty(model.createProperty(namespace + "on")).getString()),
                getInt(model, node, "sat"), getInt(model, node, "bri"), getInt(model, node, "hue"));
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
        Model model = ModelFactory.createDefaultModel();
        Resource node = read(model, file);
        assert node != null;

        return new ImageData(getString(model, node, "name"), Base64.getDecoder().decode(getString(model, node, "imageBytes")));
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
        Model model = ModelFactory.createDefaultModel();
        Resource node = read(model, file);
        assert node != null;

        Resource locationNode = node.getProperty(model.createProperty(namespace + "location")).getResource();
        assert locationNode != null;

        Resource geometryNode = locationNode.getProperty(model.createProperty(namespace + "geometry")).getResource();
        assert geometryNode != null;

        Geometry geometry = new Geometry(
                getString(model, geometryNode, "type"),
                new double[]{
                        Double.parseDouble(geometryNode.getProperty(model.createProperty(namespace + "coordinates0")).getString()),
                        Double.parseDouble(geometryNode.getProperty(model.createProperty(namespace + "coordinates1")).getString())
                });

        return new LocationData(
                getString(model, node, "name"),
                getString(model, node, "description"),
                getString(model, node, "encodingType"),
                new Location(getString(model, locationNode, "type"), geometry)
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
        Model model = ModelFactory.createDefaultModel();
        Resource node = read(model, file);
        assert node != null;

        Resource bodyResource = node.getProperty(model.createProperty(namespace + "body")).getResource();
        assert bodyResource != null;

        StmtIterator seriesIterator = bodyResource.listProperties(model.createProperty(namespace + "series"));
        List<Series> seriesList = new ArrayList<>();
        while (seriesIterator.hasNext()) {
            Resource seriesResource = seriesIterator.next().getResource();

            Series series = new Series();
            series.setDeviceid(seriesResource.getProperty(model.createProperty(namespace + "deviceid")).getString());
            series.setModel(Integer.parseInt(seriesResource.getProperty(model.createProperty(namespace + "model")).getString()));
            series.setHeartrate(Integer.parseInt(seriesResource.getProperty(model.createProperty(namespace + "heartRate")).getString()));
            series.setTimestamp(Long.parseLong(seriesResource.getProperty(model.createProperty(namespace + "timestamp")).getString()));
            series.setTimezone(seriesResource.getProperty(model.createProperty(namespace + "timezone")).getString());

            Resource ecgResource = seriesResource.getPropertyResourceValue(model.createProperty(namespace + "ecg"));
            Ecg ecg = new Ecg();
            ecg.setSignalid(Integer.parseInt(ecgResource.getProperty(model.createProperty(namespace + "signalid")).getString()));
            ecg.setAfib(Integer.parseInt(ecgResource.getProperty(model.createProperty(namespace + "afib")).getString()));
            series.setEcg(ecg);

            Resource bpResource = seriesResource.getPropertyResourceValue(model.createProperty(namespace + "bloodpressure"));
            BloodPressure bloodPressure = new BloodPressure();
            bloodPressure.setDiastole(Integer.parseInt(bpResource.getProperty(model.createProperty(namespace + "diastole")).getString()));
            bloodPressure.setSystole(Integer.parseInt(bpResource.getProperty(model.createProperty(namespace + "systole")).getString()));
            series.setBloodpressure(bloodPressure);

            seriesList.add(series);
        }

        Body body = new Body(
                seriesList.toArray(new Series[0]),
                Boolean.parseBoolean(bodyResource.getProperty(model.createProperty(namespace + "more")).getString()),
                Integer.parseInt(bodyResource.getProperty(model.createProperty(namespace + "offset")).getString())
        );
        body.setMore(Boolean.parseBoolean(bodyResource.getProperty(model.createProperty(namespace + "more")).getString()));
        body.setOffset(Integer.parseInt(bodyResource.getProperty(model.createProperty(namespace + "offset")).getString()));

        return new HeartData(
                Integer.parseInt(node.getProperty(model.createProperty(namespace + "status")).getString()),
                body
        );
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
        Model model = ModelFactory.createDefaultModel();
        Resource node = read(model, file);
        assert node != null;

        Resource headersResource= node.getProperty(model.createProperty(namespace + "headerLines")).getResource();
        String[] headers = readListFromModel(headersResource).toArray(new String[0]);

        return new HttpResponse(getString(model, node, "statusLine"), headers, getString(model, node, "responseBody"));
    }

    @Override
    public void measureTime(Object object, File file) throws Exception {
        writeTimeElapsed(getSerializationSpeed(object), file.getParentFile().getPath());
    }

    @Override
    public long getSerializationSpeed(Object object) throws Exception {
        try(ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            long start = System.nanoTime();
            RDFDataMgr.write(os, getModelForClass(object), format);
            long finish = System.nanoTime();

            return finish - start;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Skip since the methods used in getSerializationSpeed and write are exactly the same
    @Override
    public void compareFileSizesOfWritingMethods(Object object, File file) {}

    private Model getModelForClass(Object object) {
        return switch (object.getClass().getSimpleName()) {
            case "Person" -> getPersonModel((Person) object);
            case "SensorValue" -> getSensorValueModel((SensorValue) object);
            case "ImageData" -> getImageModel((ImageData) object);
            case "HttpResponse" -> getHttpResponseModel((HttpResponse) object);
            case "SmartLightController" -> getSmartLightControllerModel((SmartLightController) object);
            case "HeartData" -> getHeartDataModel((HeartData) object);
            case "ImageDescriptor" -> getImageDescriptorModel((ImageDescriptor) object);
            case "LocationData" -> getLocationDataModel((LocationData) object);
            default -> null;
        };
    }

    private Resource read(Model model, File file) {
        try (FileInputStream in = new FileInputStream(file)) {
            model.read(in, null, formatName);
        } catch (IOException e) {
            return null;
        }

        Resource node  = model.getResource(namespace);

        if (node == null) {
            System.out.println("No resource found for this node.");
            return null;
        }
        return node;
    }

    private File write(File file, Object object) throws IOException {
        File f = new File(file + FILE_EXTENSION);
        Model model = getModelForClass(object);

        try (FileOutputStream out = new FileOutputStream(f)) {
            RDFDataMgr.write(out, model, format);
        } catch (IOException e) {
            throw new IOException();
        }
        return f;
    }

    private String getString(Model model, Resource node, String name) {
        return node.getProperty(model.createProperty(namespace + name)).getString();
    }

    private int getInt(Model model, Resource node, String name) {
        return node.getProperty(model.createProperty(namespace + name)).getInt();
    }

    private List<String> readListFromModel(Resource resource) {
        List<String> list = new ArrayList<>();

        if (resource.canAs(RDFList.class)) {
            RDFList rdfList = resource.as(RDFList.class);
            rdfList.iterator().forEachRemaining(node -> list.add(node.asLiteral().getString()));
        }

        return list;
    }

    private Model getPersonModel(Person object) {
        Model model = ModelFactory.createDefaultModel();

        model.createResource(namespace)
                .addProperty(VCARD.FN, object.getFn())
                .addProperty(VCARD.BDAY, object.getBday())
                .addProperty(model.createProperty(namespace + "gender"), object.getGender())
                .addProperty(VCARD.ADR, object.getAdr())
                .addProperty(VCARD.EMAIL, object.getEmail())
                .addProperty(VCARD.TEL, object.getTel())
                .addProperty(model.createProperty(namespace + "lang"), object.getLang())
                .addProperty(model.createProperty(namespace + "version"), object.getVersion());
        return model;
    }
    private Model getSensorValueModel(SensorValue object) {
        Model model = ModelFactory.createDefaultModel();

        model.createResource(namespace)
                .addProperty(model.createProperty(namespace + "deviceId"), object.getDeviceId())
                .addProperty(model.createProperty(namespace + "timestamp"), String.valueOf(object.getTimestamp()))
                .addProperty(model.createProperty(namespace + "temperature"), String.valueOf(object.getTemperature()))
                .addProperty(model.createProperty(namespace + "humidity"), String.valueOf(object.getHumidity()));
        return model;
    }
    private Model getImageDescriptorModel(ImageDescriptor object) {
        Model model = ModelFactory.createDefaultModel();

        RDFNode tagsList = model.createList(object.getTags().stream()
                .map(model::createLiteral)
                .iterator());

        model.createResource(namespace)
                .addProperty(model.createProperty(namespace + "name"), object.getTitle())
                .addProperty(model.createProperty(namespace + "description"), object.getDescription())
                .addProperty(model.createProperty(namespace + "type"), object.getType().toString())
                .addProperty(model.createProperty(namespace + "url"), object.getUrl())
                .addProperty(model.createProperty(namespace + "width"), String.valueOf(object.getWidth()))
                .addProperty(model.createProperty(namespace + "height"), String.valueOf(object.getHeight()))
                .addProperty(model.createProperty(namespace + "tags"), tagsList);
        return model;
    }
    private Model getSmartLightControllerModel(SmartLightController object) {
        Model model = ModelFactory.createDefaultModel();

        model.createResource(namespace)
                .addProperty(model.createProperty(namespace + "sat"), String.valueOf(object.getSat()))
                .addProperty(model.createProperty(namespace + "bri"), String.valueOf(object.getBri()))
                .addProperty(model.createProperty(namespace + "hue"), String.valueOf(object.getHue()))
                .addProperty(model.createProperty(namespace + "on"), String.valueOf(object.isOn()));
        return model;
    }
    private Model getImageModel(ImageData object) {
        Model model = ModelFactory.createDefaultModel();

        model.createResource(namespace)
                .addProperty(model.createProperty(namespace + "name"), object.getName())
                .addProperty(model.createProperty(namespace + "imageBytes"), Base64.getEncoder().encodeToString((object.getImageBytes())));
        return model;
    }
    private Model getLocationDataModel(LocationData object) {
        Model model = ModelFactory.createDefaultModel();

        Resource geometryResource = model.createResource(namespace + "geometry");
        Geometry geometry = object.getLocation().getGeometry();
        geometryResource.addProperty(model.createProperty(namespace + "type"), geometry.getType());
        geometryResource.addProperty(model.createProperty(namespace + "coordinates0"), String.valueOf(geometry.getCoordinates()[0]));
        geometryResource.addProperty(model.createProperty(namespace + "coordinates1"), String.valueOf(geometry.getCoordinates()[1]));

        Resource locationResource = model.createResource(namespace + "location");
        locationResource.addProperty(model.createProperty(namespace + "type"), object.getLocation().getType());
        locationResource.addProperty(model.createProperty(namespace + "geometry"), geometryResource);

        model.createResource(namespace)
                .addProperty(model.createProperty(namespace + "name"), object.getName())
                .addProperty(model.createProperty(namespace + "description"), object.getDescription())
                .addProperty(model.createProperty(namespace + "encodingType"), object.getEncodingType())
                .addProperty(model.createProperty(namespace + "location"), locationResource);
        return model;
    }
    private Model getHeartDataModel(HeartData object) {
        Model model = ModelFactory.createDefaultModel();

        Resource bodyResource = model.createResource(namespace + "Body")
                .addProperty(model.createProperty(namespace + "more"), String.valueOf(object.getBody().isMore()))
                .addProperty(model.createProperty(namespace + "offset"), String.valueOf(object.getBody().getOffset()));

        for (Series series : object.getBody().getSeries()) {
            Resource seriesResource = model.createResource(namespace + "Series")
                    .addProperty(model.createProperty(namespace + "deviceid"), series.getDeviceid())
                    .addProperty(model.createProperty(namespace + "model"), String.valueOf(series.getModel()))
                    .addProperty(model.createProperty(namespace + "heartRate"), String.valueOf(series.getHeartrate()))
                    .addProperty(model.createProperty(namespace + "timestamp"), String.valueOf(series.getTimestamp()))
                    .addProperty(model.createProperty(namespace + "timezone"), series.getTimezone());

            Resource ecgResource = model.createResource(namespace + "Ecg")
                    .addProperty(model.createProperty(namespace + "signalid"), String.valueOf(series.getEcg().getSignalid()))
                    .addProperty(model.createProperty(namespace + "afib"), String.valueOf(series.getEcg().getAfib()));
            seriesResource.addProperty(model.createProperty(namespace + "ecg"), ecgResource);

            Resource bpResource = model.createResource(namespace + "BloodPressure")
                    .addProperty(model.createProperty(namespace + "diastole"), String.valueOf(series.getBloodpressure().getDiastole()))
                    .addProperty(model.createProperty(namespace + "systole"), String.valueOf(series.getBloodpressure().getSystole()));
            seriesResource.addProperty(model.createProperty(namespace + "bloodpressure"), bpResource);

            bodyResource.addProperty(model.createProperty(namespace + "series"), seriesResource);
        }

        model.createResource(namespace)
                .addProperty(model.createProperty(namespace + "status"), String.valueOf(object.getStatus()))
                .addProperty(model.createProperty(namespace + "body"), bodyResource);

        return model;
    }
    private Model getHttpResponseModel(HttpResponse object) {
        Model model = ModelFactory.createDefaultModel();

        RDFNode headers = model.createList(Arrays.stream(object.getHeaderLines()).map(model::createLiteral).iterator());

        model.createResource(namespace)
                .addProperty(model.createProperty(namespace + "statusLine"), object.getStatusLine())
                .addProperty(model.createProperty(namespace + "headerLines"), headers)
                .addProperty(model.createProperty(namespace + "responseBody"), object.getResponseBody());

        return model;
    }
}
