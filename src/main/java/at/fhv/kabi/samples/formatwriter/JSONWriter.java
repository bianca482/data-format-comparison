package at.fhv.kabi.samples.formatwriter;

import com.fasterxml.jackson.databind.json.JsonMapper;


public class JSONWriter extends FormatWriter {

    public JSONWriter() {
        super("JSON", ".json");
        setMapper(new JsonMapper());
    }
}
