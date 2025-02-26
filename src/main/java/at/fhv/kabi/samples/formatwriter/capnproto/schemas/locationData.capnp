@0xabcdef1212567870;

using Java = import "java.capnp";
$Java.package("at.fhv.kabi.samples.formatwriter.capnproto.generated");
$Java.outerClassname("LocationDataProto");

struct GeometryProtoStruct {
    type @0: Text;
    coordinates @1 :List(Float64);
}
struct LocationProtoStruct {
    type @0 :Text;
    geometry @1 : GeometryProtoStruct;
}

struct LocationDataProtoStruct {
    name @0 :Text;
    description @1 :Text;
    encodingType @2 :Text;
    location @3 :LocationProtoStruct;
}
