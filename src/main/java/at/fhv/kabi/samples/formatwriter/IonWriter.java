package at.fhv.kabi.samples.formatwriter;

import com.fasterxml.jackson.dataformat.ion.IonObjectMapper;

import static com.fasterxml.jackson.dataformat.ion.IonObjectMapper.builderForBinaryWriters;

public class IonWriter extends FormatWriter {

    public IonWriter(String name, String fileExtension, boolean binary) {
        super(name, fileExtension);
        IonObjectMapper mapper = binary ? builderForBinaryWriters().build() : new IonObjectMapper();
        setMapper(mapper);
    }
}
