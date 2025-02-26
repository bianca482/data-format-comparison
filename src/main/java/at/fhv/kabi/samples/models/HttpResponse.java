package at.fhv.kabi.samples.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.io.Serializable;
import java.util.Arrays;

public class HttpResponse implements Serializable {
    private String statusLine;
    private String[] headerLines;
    private String responseBody;

    public HttpResponse() {}

    public HttpResponse(String statusLine, String[] headerLines, String responseBody) {
        this.statusLine = statusLine;
        this.headerLines = headerLines;
        this.responseBody = responseBody;
    }

    public String getStatusLine() {
        return statusLine;
    }

    public void setStatusLine(String statusLine) {
        this.statusLine = statusLine;
    }

    public String[] getHeaderLines() {
        return headerLines;
    }

    public void setHeaderLines(String[] headerLines) {
        this.headerLines = headerLines;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
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
        return "HttpResponse{" +
                "statusLine='" + statusLine + '\'' +
                ", headerLines=" + Arrays.toString(headerLines) +
                ", responseBody='" + responseBody + '\'' +
                '}';
    }
}
