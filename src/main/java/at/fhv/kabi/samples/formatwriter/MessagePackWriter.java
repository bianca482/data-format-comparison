package at.fhv.kabi.samples.formatwriter;

import org.msgpack.jackson.dataformat.MessagePackMapper;


public class MessagePackWriter extends FormatWriter {

    public MessagePackWriter() {
        super("MessagePack", ".msgpack");
        setMapper(new MessagePackMapper());
    }
}
