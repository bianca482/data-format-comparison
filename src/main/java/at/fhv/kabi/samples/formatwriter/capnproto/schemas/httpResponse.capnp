@0xf380cdfd8bb0ce84;

using Java = import "java.capnp";
$Java.package("at.fhv.kabi.samples.formatwriter.capnproto.generated");
$Java.outerClassname("HttpResponseProto");

struct HttpResponseProtoStruct {
  statusLine @0: Text;
  headerLines @1: List(Text);
  responseBody @2: Text;
}
