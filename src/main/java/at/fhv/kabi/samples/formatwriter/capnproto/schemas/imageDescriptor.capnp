@0xabcdef1234567870;

using Java = import "java.capnp";
$Java.package("at.fhv.kabi.samples.formatwriter.capnproto.generated");
$Java.outerClassname("ImageDescriptorProto");

enum ImageTypeProtoStruct {
    png @0;
    jpeg @1;
    svg @2;
    gif @3;
    tiff @4;
    pdf @5;
}

struct ImageDescriptorProtoStruct {
    title @0 :Text;
    description @1 :Text;
    type @2 :ImageTypeProtoStruct;
    url @3 :Text;
    width @4 :Int32;
    height @5 :Int32;
    tags @6 :List(Text);
}
