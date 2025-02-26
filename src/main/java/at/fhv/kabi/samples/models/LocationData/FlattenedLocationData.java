package at.fhv.kabi.samples.models.LocationData;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

// Needed by CSV, XDR and Protobuf since they cannot handle nested objects
public class FlattenedLocationData {
    private String name;
    private String description;
    private String encodingType;
    private String locType;
    private String geoType;
    private double[] coordinates;

    public FlattenedLocationData() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEncodingType() {
        return encodingType;
    }

    public void setEncodingType(String encodingType) {
        this.encodingType = encodingType;
    }

    public String getLocType() {
        return locType;
    }

    public void setLocType(String locType) {
        this.locType = locType;
    }

    public String getGeoType() {
        return geoType;
    }

    public void setGeoType(String geoType) {
        this.geoType = geoType;
    }

    public double[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(double[] coordinates) {
        this.coordinates = new double[] {
                BigDecimal.valueOf(coordinates[0]).setScale(3, RoundingMode.HALF_UP).doubleValue(),
                BigDecimal.valueOf(coordinates[1]).setScale(3, RoundingMode.HALF_UP).doubleValue(),
        };
    }

    public LocationData toLocationData() {
        LocationData data = new LocationData();
        data.setDescription(description);
        data.setEncodingType(encodingType);
        data.setName(name);
        data.setLocation(new Location(locType, new Geometry(geoType, coordinates)));

        return data;
    }

    @Override
    public String toString() {
        JsonMapper mapper = new JsonMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public String toFormattedString() {
        return "FlattenedLocationData{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", encodingType='" + encodingType + '\'' +
                ", locType='" + locType + '\'' +
                ", geoType='" + geoType + '\'' +
                ", coordinates=" + Arrays.toString(coordinates) +
                '}';
    }
}
