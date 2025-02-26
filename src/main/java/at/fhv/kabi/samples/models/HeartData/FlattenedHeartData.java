package at.fhv.kabi.samples.models.HeartData;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.io.Serializable;
import java.util.Arrays;

// Needed by CSV, XDR and Protobuf since they cannot handle nested objects
public class FlattenedHeartData implements Serializable {
    private int status;
    private boolean more;
    private int offset;
    private String[] deviceid;
    private int[] model;
    private int[] heartrate;
    private long[] timestamp;
    private String[] timezone;
    private int[] afib;
    private int[] signalid;
    private int[] diastole;
    private int[] systole;

    public FlattenedHeartData() {}
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isMore() {
        return more;
    }

    public void setMore(boolean more) {
        this.more = more;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public String[] getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String[] deviceid) {
        this.deviceid = deviceid;
    }

    public int[] getModel() {
        return model;
    }

    public void setModel(int[] model) {
        this.model = model;
    }

    public int[] getHeartrate() {
        return heartrate;
    }

    public void setHeartrate(int[] heartrate) {
        this.heartrate = heartrate;
    }

    public long[] getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long[] timestamp) {
        this.timestamp = timestamp;
    }

    public String[] getTimezone() {
        return timezone;
    }

    public void setTimezone(String[] timezone) {
        this.timezone = timezone;
    }

    public int[] getAfib() {
        return afib;
    }

    public void setAfib(int[] afib) {
        this.afib = afib;
    }

    public int[] getSignalid() {
        return signalid;
    }

    public void setSignalid(int[] signalid) {
        this.signalid = signalid;
    }

    public int[] getDiastole() {
        return diastole;
    }

    public void setDiastole(int[] diastole) {
        this.diastole = diastole;
    }

    public int[] getSystole() {
        return systole;
    }

    public void setSystole(int[] systole) {
        this.systole = systole;
    }

    public HeartData toHeartData(int idx) {
        HeartData data = new HeartData();
        data.setStatus(status);

        Ecg ecg = new Ecg(signalid[idx], afib[idx]);
        BloodPressure bloodPressure = new BloodPressure(diastole[idx], systole[idx]);
        Series[] series = new Series[]{
                new Series(deviceid[idx], model[idx], ecg, bloodPressure, heartrate[idx], timestamp[idx], timezone[idx])
        };
        Body body = new Body(series, more, offset);
        data.setBody(body);

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
        return "FlattenedHeartData{" +
                "status=" + status +
                ", more=" + more +
                ", offset=" + offset +
                ", deviceid=" + Arrays.toString(deviceid) +
                ", model=" + Arrays.toString(model) +
                ", heartrate=" + Arrays.toString(heartrate) +
                ", timestamp=" + Arrays.toString(timestamp) +
                ", timezone=" + Arrays.toString(timezone) +
                ", afib=" + Arrays.toString(afib) +
                ", signalid=" + Arrays.toString(signalid) +
                ", diastole=" + Arrays.toString(diastole) +
                ", systole=" + Arrays.toString(systole) +
                '}';
    }
}
