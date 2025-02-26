package at.fhv.kabi.samples;

import at.fhv.kabi.samples.formatwriter.*;
import at.fhv.kabi.samples.formatwriter.avro.AvroWriter;
import at.fhv.kabi.samples.formatwriter.capnproto.CapnProtoWriter;
import at.fhv.kabi.samples.formatwriter.flatbuffers.FlatBuffersWriter;
import at.fhv.kabi.samples.formatwriter.thrift.ThriftWriter;
import at.fhv.kabi.samples.formatwriter.tlv.TLVWriter;
import at.fhv.kabi.samples.helpers.Comparison;
import at.fhv.kabi.samples.helpers.DataExcelParser;
import org.apache.jena.riot.Lang;

import java.io.File;
import java.util.*;

import static at.fhv.kabi.samples.TestData.*;
import static at.fhv.kabi.samples.helpers.DataExcelParser.writeAllTimesToExcel;

public class Main {

    public static final String BASE_PATH = "src/main/java/at/fhv/kabi/samples/";

    private static void person(File personFile, List<FormatWriter> writers) {
        for (FormatWriter w: writers) {
            try {
                w.writePersonToFile(person, personFile);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void sensorValue(File sensorValueFile, List<FormatWriter> writers) {
        for (FormatWriter w: writers) {
            try {
                w.writeSensorValueToFile(sensorValue, sensorValueFile);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void imageDescriptor(File imageFile, List<FormatWriter> writers) {
        for (FormatWriter w: writers) {
            try {
                w.writeImageDescriptorToFile(imageDescriptor, imageFile);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void smartLightController(File file, List<FormatWriter> writers) {
        for (FormatWriter w: writers) {
            try {
                w.writeSmartLightControllerToFile(controller, file);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void image(File file, List<FormatWriter> writers) {
        for (FormatWriter w : writers) {
            try {
                w.writeImageToFile(imageData, file);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    // OGC Location Entity Standard
    private static void locationData(File locationDataFile, List<FormatWriter> writers) {
        for (FormatWriter w: writers) {
            try {
                w.writeLocationDataToFile(locationData, locationDataFile);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void heartData(File file, List<FormatWriter> writers) {
        for (FormatWriter w: writers) {
            try {
                w.writeHeartDataToFile(heartDataResponse, file);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void httpResponse(File httpResponseFile, List<FormatWriter> writers) {
        for (FormatWriter w: writers) {
            try {
                w.writeHttpResponseToFile(httpResponse, httpResponseFile);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        cleanup();
        TestData.initialize();

        List<FormatWriter> writers = getWriters();

        calculateFileSizes(getFiles(), writers);
        //calculateTimeElapsedForXIterations(1000, writers);
    }

    private static void cleanup() {
        List<File> files = getFiles();

        for (File f : files) {
            File parentDir = f.getParentFile();

            File sizesFile = new File(parentDir + "Sizes.txt");
            File timesFile = new File(parentDir + "Times.txt");

            sizesFile.delete();
            timesFile.delete();
        }
    }

    private static void calculateFileSizes(List<File> files, List<FormatWriter> writers) {
        System.out.println("Person");
        person(files.get(0), writers);
        System.out.println("\n-----------------------------------");

        System.out.println("SensorValue");
        sensorValue(files.get(1), writers);
        System.out.println("\n-----------------------------------");

        System.out.println("Image Descriptor");
        imageDescriptor(files.get(2), writers);
        System.out.println("\n-----------------------------------");

        System.out.println("SmartLightController");
        smartLightController(files.get(3), writers);
        System.out.println("\n-----------------------------------");

        System.out.println("Image");
        image(files.get(4), writers);
        System.out.println("\n-----------------------------------");

        System.out.println("LocationData");
        locationData(files.get(5), writers);
        System.out.println("\n-----------------------------------");

        System.out.println("HeartData");
        heartData(files.get(6), writers);
        System.out.println("\n-----------------------------------");

        System.out.println("HttpResponse");
        httpResponse(files.get(7), writers);
        System.out.println("\n-----------------------------------");

        DataExcelParser.writeSizeToExcel();
    }

    private static void calculateTimeElapsedForXIterations(int iterations, List<FormatWriter> writers) {
        List<Object> useCases = getUseCases();
        Map<String, Map<String, List<Long>>> results = new HashMap<>();

        for (FormatWriter writer : writers) {
            String name = writer.getName();
            results.put(name, new HashMap<>());

            for (Object useCase : useCases) {
                List<Long> times = new ArrayList<>();
                for (int i = 0; i < iterations; i++) {
                    try {
                        long time = writer.getSerializationSpeed(useCase);
                        times.add(time);
                    } catch (Exception e) {
                        throw new RuntimeException("Error in writer: " + writer + " for object: " + useCase, e);
                    }
                }
                results.get(name).put(useCase.getClass().getSimpleName(), times);
            }
        }

        writeAllTimesToExcel(results);
    }

    private static List<File> getFiles() {
        List<File> files = new LinkedList<>();

        files.add(new File(BASE_PATH + "results/Person/person"));
        files.add(new File(BASE_PATH + "results/SensorValue/sensorValue"));
        files.add(new File(BASE_PATH, "results/ImageDescriptor/imageDescriptor"));
        files.add(new File(BASE_PATH, "results/SmartLightController/smartLight"));
        files.add(new File(BASE_PATH, "results/ImageData/image"));
        files.add(new File(BASE_PATH, "results/LocationData/locationData"));
        files.add(new File(BASE_PATH, "results/HeartData/heartData"));
        files.add(new File(BASE_PATH, "results/HttpResponse/httpResponse"));

        return files;
    }

    private static List<FormatWriter> getWriters() {
        List<FormatWriter> writers = new LinkedList<>();

        writers.add(new AvroWriter());
        writers.add(new BSONWriter());
        writers.add(new CapnProtoWriter("Cap'n Proto (packed)", "_packed.capnp", true));
        writers.add(new CapnProtoWriter("Cap'n Proto (unpacked)", "_unpacked.capnp", false));
        writers.add(new CBORWriter());
        writers.add(new CSVWriter("CSV", ".csv", false));
        writers.add(new CSVWriter("CSV (with header)", "_header.csv", true));
        writers.add(new EXIWriter());
        writers.add(new FlatBuffersWriter());
        writers.add(new FlexBuffersWriter());
        writers.add(new HessianWriter());
        writers.add(new INIWriter());
        writers.add(new IonWriter("Ion", ".ion", false));
        writers.add(new IonWriter("Ion Binary", "_binary.ion", true));
        writers.add(new JavaWriter());
        writers.add(new JSONWriter());
        writers.add(new MessagePackWriter());
        writers.add(new ProtobufWriter());
        writers.add(new RDFWriter("RDF", ".rdf", Lang.RDFXML.getName()));
        writers.add(new RDFWriter("RDF Turtle", ".ttl", Lang.TURTLE.getName()));
        writers.add(new SmileWriter());
        writers.add(new TLVWriter());
        writers.add(new TOMLWriter());
        writers.add(new ThriftWriter());
        writers.add(new UBJSONWriter());
        writers.add(new XDRWriter());
        writers.add(new XMLWriter());
        writers.add(new YamlWriter());

        return writers;
    }

    // Sometimes the libraries provide different methods to write directly in a file and to write into a byte array - this method checks if those methods produce exactly the same output file
    private static void compareOutputFiles(List<File> files, List<FormatWriter> writers) {
        List<Object> useCases = getUseCases();

        for (FormatWriter writer : writers) {
            for (int i = 0; i < useCases.size(); i++) {
                Object obj = useCases.get(i);
                File file = files.get(i);

                try {
                    writer.compareFileSizesOfWritingMethods(obj, file);
                } catch (Exception e) {
                    throw new RuntimeException("Error in writer: " + writer + " for object: " + obj, e);
                }
            }
        }

        Comparison.compare();
    }
}