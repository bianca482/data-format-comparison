@0xaea6acfc3d20cbc2;

using Java = import "java.capnp";
$Java.package("at.fhv.kabi.samples.formatwriter.capnproto.generated");
$Java.outerClassname("HeartDataProto");

struct HeartDataProtoStruct {
  status @0 :Int32;
  body @1 :BodyProto;
}

struct BodyProto {
  series @0 :List(SeriesProto);
  more @1 :Bool;
  offset @2 :Int32;
}

struct SeriesProto {
  deviceid @0 :Text;
  model @1 :Int32;
  ecg @2 :EcgProto;
  bloodpressure @3 :BloodPressureProto;
  heartrate @4 :Int32;
  timestamp @5 :Int64;
  timezone @6 :Text;
}

struct EcgProto {
  signalid @0 :Int32;
  afib @1 :Int32;
}

struct BloodPressureProto {
  diastole @0 :Int32;
  systole @1 :Int32;
}