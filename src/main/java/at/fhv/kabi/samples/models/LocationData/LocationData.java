package at.fhv.kabi.samples.models.LocationData;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.io.Serializable;

// Based on the Location Entity defined in https://docs.ogc.org/is/18-088/18-088.html#location
public class LocationData implements Serializable {
    private String name;
    private String description;
    private String encodingType;
    private Location location;

    public LocationData() {}

    public LocationData(String name, String description, String encodingType, Location location) {
        this.name = name;
        this.description = description;
        this.encodingType = encodingType;
        this.location = location;
    }

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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    // Because CSV and Protobuf can't handle nested objects
    public FlattenedLocationData flatten() throws Exception {
        FlattenedLocationData flattened = new FlattenedLocationData();

        flattened.setDescription(description);
        flattened.setEncodingType(encodingType);
        flattened.setName(name);
        flattened.setLocType(location.getType());
        flattened.setGeoType(location.getGeometry().getType());
        flattened.setCoordinates(location.getGeometry().getCoordinates());

        return flattened;
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
        return "LocationData{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", encodingType='" + encodingType + '\'' +
                ", location=" + location +
                '}';
    }
}
