package at.fhv.kabi.samples.formatwriter;

import com.fasterxml.jackson.dataformat.toml.TomlMapper;

public class TOMLWriter extends FormatWriter {

    public TOMLWriter() {
        super("TOML", ".toml");
        setMapper(new TomlMapper());
    }

}
