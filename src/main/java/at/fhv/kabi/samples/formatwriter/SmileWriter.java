package at.fhv.kabi.samples.formatwriter;

import com.fasterxml.jackson.dataformat.smile.databind.SmileMapper;

public class SmileWriter extends FormatWriter {

    public SmileWriter() {
        super("Smile", ".smile");
        setMapper(new SmileMapper());
    }
}
