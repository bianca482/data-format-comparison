package at.fhv.kabi.samples.models.LocationData;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.io.Serializable;

public class Location implements Serializable {
    private String type;
    private Geometry geometry;

    public Location() {}

    public Location(String type, Geometry geometry) {
        this.type = type;
        this.geometry = geometry;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public String toFormattedString() {
        return "Location{" +
                "type='" + type + '\'' +
                ", geometry=" + geometry +
                '}';
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
}
