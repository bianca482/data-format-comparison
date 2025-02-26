namespace java at.fhv.kabi.samples.formatwriter.thrift

struct GeometryThrift {
  1: string type;
  2: list<double> coordinates;
}

struct LocationThrift {
  1: string type;
  2: GeometryThrift geometry;
}

struct LocationDataThrift {
  1: string name;
  2: string description;
  3: string encodingType;
  4: LocationThrift location;
}
