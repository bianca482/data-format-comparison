package at.fhv.kabi.samples;

import at.fhv.kabi.samples.models.*;
import at.fhv.kabi.samples.models.BufferedImage.ImageData;
import at.fhv.kabi.samples.models.HeartData.*;
import at.fhv.kabi.samples.models.ImageDescriptor.ImageDescriptor;
import at.fhv.kabi.samples.models.LocationData.Geometry;
import at.fhv.kabi.samples.models.LocationData.Location;
import at.fhv.kabi.samples.models.LocationData.LocationData;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import static at.fhv.kabi.samples.Main.BASE_PATH;

public class TestData {

    static final Person person = new Person(
            "John Doe",
            LocalDate.of(1985, 5, 15).toString(),
            "M",
            "1234 Elm Street, Springfield, USA",
            "johndoe@example.com",
            "+1-555-1234",
            "en",
            "4.0"
    );
    static final SensorValue sensorValue = new SensorValue("device1",  Timestamp.valueOf("2024-10-10 20:20:20.000").getTime(), 23.1, 80.2);
    static final SmartLightController controller  = new SmartLightController(false, 254, 254, 10000);
    static ImageDescriptor imageDescriptor;
    static ImageData imageData;
    static LocationData locationData;
    static HeartData heartDataResponse;
    static HttpResponse httpResponse;

    static void initialize() {
        buildImageDescriptor();
        buildImage();
        buildLocation();
        buildHeartData();
        buildHttpResponse();
    }

    static List<Object> getUseCases() {
        List<Object> objects = new LinkedList<>();

        objects.add(person);
        objects.add(sensorValue);
        objects.add(imageDescriptor);
        objects.add(controller);
        objects.add(imageData);
        objects.add(locationData);
        objects.add(heartDataResponse);
        objects.add(httpResponse);

        return objects;
    }

    private static void buildImageDescriptor() {
        List<String> tags = new LinkedList<>();
        tags.add("outdoor");
        tags.add("nature");

        imageDescriptor = new ImageDescriptor("example", "", ImageDescriptor.ImageType.JPEG, "https://www.sample.at", 1920, 1080, tags);
    }

    private static void buildImage() {
        try(ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            BufferedImage image = ImageIO.read(new File(BASE_PATH + "models/BufferedImage/SampleImage.jpg"));
            ImageIO.write(image, "jpg", os);
            imageData = new ImageData("SampleImage", os.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static void buildLocation() {
        Location location = new Location("Feature", new Geometry("Point", new double[]{13.405, 52.522}));
        locationData = new LocationData("CCIT", "Calgary Center for Innvative Technologies", "application/geo+json", location);
    }

    private static void buildHeartData() {
        Series[] series = new Series[1];
        series[0] = new Series(
                "892359876fd8805ac45bab078c4828692f0276b1",
                44,
                new Ecg(48, 1),
                new BloodPressure(100, 101),
                82,
                1594159644L,
                "Europe/Paris"
        );

        heartDataResponse = new HeartData(0, new Body(series, true, 0));
    }

    private static void buildHttpResponse() {
        String[] headers = {
                "Accept-Ranges: bytes",
                "Age: 3600",
                "Allow: GET",
                "Cache-Control: no-cache",
                "Connection: keep-alive",
                "Content-Encoding: gzip",
                "Content-Language: en-US",
                "Content-Length: 1234",
                "Content-Location: /index.html",
                "Content-Type: text/html",
                "Date: " + Timestamp.valueOf("2024-10-10 20:20:20.000"),
                "Location: https://example.com/new-page",
                "Pragma: no-cache",
                "Retry-After: 120",
                "Server: Apache/2.4.41 (Ubuntu)",
        };

        httpResponse = new HttpResponse("HTTP/1.1 200 OK", headers, sensorValue.toString());
    }
}
