namespace java at.fhv.kabi.samples.formatwriter.thrift

enum ImageTypeThrift {
  PNG,
  JPEG,
  SVG,
  GIF,
  TIFF,
  PDF
}

struct ImageDescriptorThrift {
  1: string title;
  2: string description;
  3: ImageTypeThrift type;
  4: string url;
  5: i32 width;
  6: i32 height;
  7: list<string> tags;
}
