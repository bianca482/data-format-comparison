@0xabcdef1234567870;

using Java = import "java.capnp";
$Java.package("at.fhv.kabi.samples.formatwriter.capnproto.generated");
$Java.outerClassname("SmartLightControllerProto");

struct SmartLightControllerProtoStruct {
    on @0: Bool;
    sat @1: Int32;
    bri @2: Int32;
    hue @3: Int32;
}
