namespace java at.fhv.kabi.samples.formatwriter.thrift

struct HttpResponseThrift {
  1: string statusLine;
  2: list<string> headerLines;
  3: string responseBody;
}
