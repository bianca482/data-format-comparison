package at.fhv.kabi.samples.models.HeartData;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.io.Serializable;

// Based on the response of Heart v2 - List API of https://developer.withings.com/api-reference/#tag/heart/operation/heartv2-list
public class HeartData implements Serializable {
    private int status;
    private Body body;
    // alle Attribute einzeln

    public HeartData() {}

    public HeartData(int status, Body body) {
        this.status = status;
        this.body = body;
    }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public Body getBody() { return body; }
    public void setBody(Body body) { this.body = body; }

    // CSV and Protobuf
    public FlattenedHeartData flatten(int idx) throws Exception {
        FlattenedHeartData flattened = new FlattenedHeartData();

        Series series = body.getSeries()[idx];

        flattened.setStatus(status);
        flattened.setMore(body.isMore());
        flattened.setOffset(body.getOffset());
        flattened.setDeviceid(new String[]{series.getDeviceid()});
        flattened.setModel(new int[]{series.getModel()});
        flattened.setAfib(new int[]{series.getEcg().getAfib()});
        flattened.setSignalid(new int[]{series.getEcg().getSignalid()});
        flattened.setDiastole(new int[]{series.getBloodpressure().getDiastole()});
        flattened.setSystole(new int[]{series.getBloodpressure().getSystole()});
        flattened.setHeartrate(new int[]{series.getHeartrate()});
        flattened.setTimestamp(new long[]{series.getTimestamp()});
        flattened.setTimezone(new String[]{series.getTimezone()});

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
        return "HeartDataResponse{" +
                "status=" + status +
                ", body=" + body +
                '}';
    }
}
