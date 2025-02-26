namespace java at.fhv.kabi.samples.formatwriter.thrift

struct SensorValueThrift {
    1: string deviceId;
    2: i64 timestamp; 
    3: double temperature;
    4: double humidity;
}