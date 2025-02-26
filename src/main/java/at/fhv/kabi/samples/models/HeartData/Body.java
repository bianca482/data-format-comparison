package at.fhv.kabi.samples.models.HeartData;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.io.Serializable;
import java.util.Arrays;

public class Body implements Serializable {
    private Series[] series;
    private boolean more;
    private int offset;

    public Body() {}

    public Body(Series[] series, boolean more, int offset) {
        this.series = series;
        this.more = more;
        this.offset = offset;
    }

    public Series[] getSeries() { return series; }
    public void setSeries(Series[] series) { this.series = series; }

    public boolean isMore() { return more; }
    public void setMore(boolean more) { this.more = more; }

    public int getOffset() { return offset; }
    public void setOffset(int offset) { this.offset = offset; }

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
        return "Body{" +
                "series=" + Arrays.toString(series) +
                ", more=" + more +
                ", offset=" + offset +
                '}';
    }
}