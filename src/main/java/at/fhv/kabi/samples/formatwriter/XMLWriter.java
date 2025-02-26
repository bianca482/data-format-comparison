package at.fhv.kabi.samples.formatwriter;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class XMLWriter extends FormatWriter {

    public XMLWriter() {
        super("XML", ".xml");
        setMapper(new XmlMapper());
    }

}
