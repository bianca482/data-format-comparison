package at.fhv.kabi.samples.models.HeartData;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.io.Serializable;

public class Series implements Serializable {
    private String deviceid;
    private int model;
    private Ecg ecg;
    private BloodPressure bloodpressure;
    private int heartrate;
    private long timestamp;
    private String timezone;

    public Series() {
    }

    public Series(String deviceid, int model, Ecg ecg, BloodPressure bloodpressure, int heartrate, long timestamp, String timezone) {
        this.deviceid = deviceid;
        this.model = model;
        this.ecg = ecg;
        this.bloodpressure = bloodpressure;
        this.heartrate = heartrate;
        this.timestamp = timestamp;
        this.timezone = timezone;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public int getModel() {
        return model;
    }

    public void setModel(int model) {
        this.model = model;
    }

    public Ecg getEcg() {
        return ecg;
    }

    public void setEcg(Ecg ecg) {
        this.ecg = ecg;
    }

    public BloodPressure getBloodpressure() {
        return bloodpressure;
    }

    public void setBloodpressure(BloodPressure bloodpressure) {
        this.bloodpressure = bloodpressure;
    }

    public int getHeartrate() {
        return heartrate;
    }

    public void setHeartrate(int heartrate) {
        this.heartrate = heartrate;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
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
        return "Series{" +
                "deviceid='" + deviceid + '\'' +
                ", model=" + model +
                ", ecg=" + ecg +
                ", bloodpressure=" + bloodpressure +
                ", heart_rate=" + heartrate +
                ", timestamp=" + timestamp +
                ", timezone='" + timezone + '\'' +
                '}';
    }
}