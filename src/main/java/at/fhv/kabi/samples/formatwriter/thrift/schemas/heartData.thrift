namespace java at.fhv.kabi.samples.formatwriter.thrift

struct EcgThrift {
    1: i32 signalid,
    2: i32 afib
}

struct BloodPressureThrift {
    1: i32 diastole,
    2: i32 systole
}

struct SeriesThrift {
    1: string deviceid,
    2: i32 model,
    3: EcgThrift ecg,
    4: BloodPressureThrift bloodpressure,
    5: i32 heartrate,
    6: i64 timestamp,
    7: string timezone
}

struct BodyThrift {
    1: list<SeriesThrift> series,
    2: bool more,
    3: i32 offset
}

struct HeartDataThrift {
    1: i32 status,
    2: BodyThrift body
}