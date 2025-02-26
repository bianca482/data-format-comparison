package at.fhv.kabi.samples.models.HeartData;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.io.Serializable;

public class Ecg implements Serializable {
    private int signalid;
    private int afib;

    public Ecg() {}

    public Ecg(int signalid, int afib) {
        this.signalid = signalid;
        this.afib = afib;
    }

    public int getSignalid() { return signalid; }
    public void setSignalid(int signalid) { this.signalid = signalid; }

    public int getAfib() { return afib; }
    public void setAfib(int afib) { this.afib = afib; }

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
        return "Ecg{" +
                "signalid=" + signalid +
                ", afib=" + afib +
                '}';
    }
}