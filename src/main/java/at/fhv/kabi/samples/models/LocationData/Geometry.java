package at.fhv.kabi.samples.models.LocationData;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

public class Geometry implements Serializable {
    private String type;
    private double[] coordinates;

    public Geometry() {}

    public Geometry(String type, double[] coordinates) {
        this.type = type;
        setCoordinates(coordinates);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String toFormattedString() {
        return "Geometry{" +
                "type='" + type + '\'' +
                ", coordinates=" + Arrays.toString(coordinates) +
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
