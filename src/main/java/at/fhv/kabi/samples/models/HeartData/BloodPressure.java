package at.fhv.kabi.samples.models.HeartData;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.io.Serializable;

public class BloodPressure implements Serializable {
    private int diastole;
    private int systole;

    public BloodPressure() {}

    public BloodPressure(int diastole, int systole) {
        this.diastole = diastole;
        this.systole = systole;
    }

    public int getDiastole() { return diastole; }
    public void setDiastole(int diastole) { this.diastole = diastole; }

    public int getSystole() { return systole; }
    public void setSystole(int systole) { this.systole = systole; }

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
        return "BloodPressure{" +
                "diastole=" + diastole +
                ", systole=" + systole +
                '}';
    }
}