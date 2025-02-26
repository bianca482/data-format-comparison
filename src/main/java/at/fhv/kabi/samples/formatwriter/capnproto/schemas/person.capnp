@0xabcdef1234567990;

using Java = import "java.capnp";
$Java.package("at.fhv.kabi.samples.formatwriter.capnproto.generated");
$Java.outerClassname("PersonProto");

struct PersonProtoStruct {
    fn @0: Text;
    bday @1: Text;
    gender @2: Text;
    adr @3: Text;
    email @4: Text;
    tel @5: Text;
    lang @6: Text;
    version @7: Text;
}
