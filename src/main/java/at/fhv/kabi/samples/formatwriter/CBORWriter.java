package at.fhv.kabi.samples.formatwriter;

import com.fasterxml.jackson.dataformat.cbor.databind.CBORMapper;

public class CBORWriter extends FormatWriter {

    public CBORWriter() {
        super("CBOR", ".cbor");
        setMapper(new CBORMapper());
    }


}
