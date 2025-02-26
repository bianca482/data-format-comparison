package at.fhv.kabi.samples.formatwriter;

import at.fhv.kabi.samples.models.BufferedImage.ImageData;
import at.fhv.kabi.samples.models.HeartData.HeartData;
import at.fhv.kabi.samples.models.ImageDescriptor.ImageDescriptor;
import at.fhv.kabi.samples.models.Person;
import at.fhv.kabi.samples.models.SensorValue;
import at.fhv.kabi.samples.models.SmartLightController;
import com.fasterxml.jackson.core.FormatSchema;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.siemens.ct.exi.core.EXIFactory;
import com.siemens.ct.exi.core.exceptions.EXIException;
import com.siemens.ct.exi.core.helpers.DefaultEXIFactory;
import com.siemens.ct.exi.main.api.sax.EXIResult;
import com.siemens.ct.exi.main.api.sax.EXISource;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

public class EXIWriter extends FormatWriter {
    private final EXIFactory exiFactory;
    private final XmlMapper mapper;

    public EXIWriter() {
        super("EXI", ".exi");
        exiFactory = DefaultEXIFactory.newInstance();
        mapper = new XmlMapper();
        setMapper(mapper);
    }

    private String convertToXml(Object object) throws JsonProcessingException {
        return mapper.writeValueAsString(object);
    }

    @Override
    public void writePersonToFile(Person object, File file) throws IOException {
        File f = write(object, file);

        Person result = readPersonFromFile(f, null);
        checkAndPrintOutput(result.toFormattedString(), object.toFormattedString());

        writeFileSizes(file.getParentFile().getPath(), f);
    }

    @Override
    public Person readPersonFromFile(File file, FormatSchema schema) throws IOException {
       SAXSource exiSource = getSaxSource(file);
       String transformedString = transformToString(exiSource);

       return mapper.readValue(transformedString, Person.class);
    }

    @Override
    public void writeSensorValueToFile(SensorValue object, File file) throws IOException {
        File f = write(object, file);

        SensorValue result = readSensorValueFromFile(f, null);
        checkAndPrintOutput(result.toFormattedString(), object.toFormattedString());

        writeFileSizes(file.getParentFile().getPath(), f);
    }

    @Override
    public SensorValue readSensorValueFromFile(File file, FormatSchema schema) throws IOException {
        SAXSource exiSource = getSaxSource(file);
        String transformedString = transformToString(exiSource);

        return mapper.readValue(transformedString, SensorValue.class);
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
        SAXSource exiSource = getSaxSource(file);
        String transformedString = transformToString(exiSource);

        return mapper.readValue(transformedString, ImageDescriptor.class);
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
        SAXSource exiSource = getSaxSource(file);
        String transformedString = transformToString(exiSource);

        return mapper.readValue(transformedString, SmartLightController.class);
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
        SAXSource exiSource = getSaxSource(file);
        String transformedString = transformToString(exiSource);

        return mapper.readValue(transformedString, ImageData.class);
    }

    @Override
    public HeartData readHeartDataFromFile(File file, FormatSchema schema) throws Exception {
        SAXSource exiSource = getSaxSource(file);
        String transformedString = transformToString(exiSource);

        return mapper.readValue(transformedString, HeartData.class);
    }

    @Override
    public void writeHeartDataToFile(HeartData object, File file) throws Exception {
        File f = write(object, file);

        HeartData result = readHeartDataFromFile(f, null);
        checkAndPrintOutput(result.toFormattedString(), object.toFormattedString());

        writeFileSizes(file.getParentFile().getPath(), f);
    }

    @Override
    public void measureTime(Object object, File file) throws Exception {
        writeTimeElapsed(getSerializationSpeed(object), file.getParentFile().getPath());
    }

    @Override
    public long getSerializationSpeed(Object object) throws Exception {
        try(ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            long start = System.nanoTime();
            XMLReader xmlReader = setupXMLReader(os);
            InputSource inputSource = new InputSource(new StringReader(convertToXml(object)));
            xmlReader.parse(inputSource);
            long finish = System.nanoTime();

            return finish - start;
        } catch (IOException e) {
            throw new IOException();
        }
    }

    // Skip since the methods used in getSerializationSpeed and write are exactly the same
    @Override
    public void compareFileSizesOfWritingMethods(Object object, File file) {}

    private String transformToString(SAXSource exiSource) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StringWriter stringWriter = new StringWriter();
            StreamResult result = new StreamResult(stringWriter);

            transformer.transform(exiSource, result);
            return stringWriter.toString();
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
    }

    private SAXSource getSaxSource(File file) {
        try {
            EXIFactory exiFactory = DefaultEXIFactory.newInstance();
            InputSource inputSource = new InputSource(new FileInputStream(file));
            SAXSource exiSource = new EXISource(exiFactory);
            exiSource.setInputSource(inputSource);
            return exiSource;
        } catch (FileNotFoundException | EXIException e) {
            throw new RuntimeException(e);
        }
    }

    private XMLReader setupXMLReader(OutputStream osEXI) {
        try {
            EXIResult exiResult = new EXIResult(exiFactory);
            exiResult.setOutputStream(osEXI);

            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            saxParserFactory.setNamespaceAware(true);
            SAXParser saxParser = saxParserFactory.newSAXParser();
            XMLReader xmlReader = saxParser.getXMLReader();
            xmlReader.setContentHandler(exiResult.getHandler());

            return xmlReader;
        } catch (EXIException | IOException | ParserConfigurationException | SAXException e) {
            throw new RuntimeException(e);
        }
    }

    private File write(Object o, File file) {
        File f = new File(file + FILE_EXTENSION);

        try(OutputStream os = new FileOutputStream(f)) {
            XMLReader xmlReader = setupXMLReader(os);
            InputSource inputSource = new InputSource(new StringReader(convertToXml(o)));
            xmlReader.parse(inputSource);

            return f;
        } catch (SAXException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
