package at.fhv.kabi.samples.formatwriter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

public class YamlWriter extends FormatWriter {

    public YamlWriter() {
        super("YAML", ".yml");
        ObjectMapper mapper = YAMLMapper.builder()
                .disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER)
                .build();
        setMapper(mapper);
    }
}
