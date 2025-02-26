@0xabcdef1234568991;

using Java = import "java.capnp";
$Java.package("at.fhv.kabi.samples.formatwriter.capnproto.generated");
$Java.outerClassname("ImageProto");

struct ImageProtoStruct {
    name @0: Text;
    imageBytes @1: Data;
}
