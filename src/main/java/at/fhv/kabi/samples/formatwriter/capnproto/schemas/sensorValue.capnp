@0xabcdef1234567890;

using Java = import "java.capnp";
$Java.package("at.fhv.kabi.samples.formatwriter.capnproto.generated");
$Java.outerClassname("SensorValueProto");

struct SensorValueProtoStruct {
    deviceId @0: Text;
    timestamp @1: Int64;
    temperature @2: Float64;
    humidity @3: Float64;
}
