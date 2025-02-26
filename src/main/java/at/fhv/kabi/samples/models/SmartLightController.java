package at.fhv.kabi.samples.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.io.Serializable;

// Based on a request example of Philips Hue REST API: https://developers.meethue.com/develop/get-started-2/
public class SmartLightController implements Serializable {
    private boolean on;
    private int sat;
    private int bri;
    private int hue;

    public SmartLightController() {}


    public SmartLightController(boolean on, int sat, int bri, int hue) {
        this.on = on;
        this.sat = sat;
        this.bri = bri;
        this.hue = hue;
    }

    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }

    public int getSat() {
        return sat;
    }

    public void setSat(int sat) {
        this.sat = sat;
    }

    public int getBri() {
        return bri;
    }

    public void setBri(int bri) {
        this.bri = bri;
    }

    public int getHue() {
        return hue;
    }

    public void setHue(int hue) {
        this.hue = hue;
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
        return "SmartLightController{" +
                "on=" + on +
                ", sat=" + sat +
                ", bri=" + bri +
                ", hue=" + hue +
                '}';
    }
}
