package at.fhv.kabi.samples.formatwriter;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.undercouch.bson4jackson.BsonFactory;

public class BSONWriter extends FormatWriter {

    public BSONWriter() {
        super("BSON", ".bson");
        setMapper(new ObjectMapper(new BsonFactory()));
    }

}
