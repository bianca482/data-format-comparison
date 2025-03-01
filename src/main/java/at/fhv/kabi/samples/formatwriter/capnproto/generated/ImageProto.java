// Generated by Cap'n Proto compiler, DO NOT EDIT
// source: image.capnp

package at.fhv.kabi.samples.formatwriter.capnproto.generated;

public final class ImageProto {
  public static class ImageProtoStruct {
    public static final org.capnproto.StructSize STRUCT_SIZE = new org.capnproto.StructSize((short)0,(short)2);
    public static final class Factory extends org.capnproto.StructFactory<Builder, Reader> {
      public Factory() {
      }
      public final Reader constructReader(org.capnproto.SegmentReader segment, int data,int pointers, int dataSize, short pointerCount, int nestingLimit) {
        return new Reader(segment,data,pointers,dataSize,pointerCount,nestingLimit);
      }
      public final Builder constructBuilder(org.capnproto.SegmentBuilder segment, int data,int pointers, int dataSize, short pointerCount) {
        return new Builder(segment, data, pointers, dataSize, pointerCount);
      }
      public final org.capnproto.StructSize structSize() {
        return ImageProtoStruct.STRUCT_SIZE;
      }
      public final Reader asReader(Builder builder) {
        return builder.asReader();
      }
    }
    public static final Factory factory = new Factory();
    public static final org.capnproto.StructList.Factory<Builder,Reader> listFactory =
      new org.capnproto.StructList.Factory<Builder, Reader>(factory);
    public static final class Builder extends org.capnproto.StructBuilder {
      Builder(org.capnproto.SegmentBuilder segment, int data, int pointers,int dataSize, short pointerCount){
        super(segment, data, pointers, dataSize, pointerCount);
      }
      public final Reader asReader() {
        return new Reader(segment, data, pointers, dataSize, pointerCount, 0x7fffffff);
      }
      public final boolean hasName() {
        return !_pointerFieldIsNull(0);
      }
      public final org.capnproto.Text.Builder getName() {
        return _getPointerField(org.capnproto.Text.factory, 0, null, 0, 0);
      }
      public final void setName(org.capnproto.Text.Reader value) {
        _setPointerField(org.capnproto.Text.factory, 0, value);
      }
      public final void setName(String value) {
        _setPointerField(org.capnproto.Text.factory, 0, new org.capnproto.Text.Reader(value));
      }
      public final org.capnproto.Text.Builder initName(int size) {
        return _initPointerField(org.capnproto.Text.factory, 0, size);
      }
      public final boolean hasImageBytes() {
        return !_pointerFieldIsNull(1);
      }
      public final org.capnproto.Data.Builder getImageBytes() {
        return _getPointerField(org.capnproto.Data.factory, 1, null, 0, 0);
      }
      public final void setImageBytes(org.capnproto.Data.Reader value) {
        _setPointerField(org.capnproto.Data.factory, 1, value);
      }
      public final void setImageBytes(byte [] value) {
        _setPointerField(org.capnproto.Data.factory, 1, new org.capnproto.Data.Reader(value));
      }
      public final org.capnproto.Data.Builder initImageBytes(int size) {
        return _initPointerField(org.capnproto.Data.factory, 1, size);
      }
    }

    public static final class Reader extends org.capnproto.StructReader {
      Reader(org.capnproto.SegmentReader segment, int data, int pointers,int dataSize, short pointerCount, int nestingLimit){
        super(segment, data, pointers, dataSize, pointerCount, nestingLimit);
      }

      public boolean hasName() {
        return !_pointerFieldIsNull(0);
      }
      public org.capnproto.Text.Reader getName() {
        return _getPointerField(org.capnproto.Text.factory, 0, null, 0, 0);
      }

      public boolean hasImageBytes() {
        return !_pointerFieldIsNull(1);
      }
      public org.capnproto.Data.Reader getImageBytes() {
        return _getPointerField(org.capnproto.Data.factory, 1, null, 0, 0);
      }

    }

  }



public static final class Schemas {
public static final org.capnproto.SegmentReader b_89992677cdf13d60 =
   org.capnproto.GeneratedClassSupport.decodeRawBytes(
   "\u0000\u0000\u0000\u0000\u0005\u0000\u0006\u0000" +
   "\u0060\u003d\u00f1\u00cd\u0077\u0026\u0099\u0089" +
   "\u000c\u0000\u0000\u0000\u0001\u0000\u0000\u0000" +
   "\u0091\u0089\u0056\u0034\u0012\u00ef\u00cd\u00ab" +
   "\u0002\u0000\u0007\u0000\u0000\u0000\u0000\u0000" +
   "\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000" +
   "\u0015\u0000\u0000\u0000\u00ea\u0000\u0000\u0000" +
   "\u0021\u0000\u0000\u0000\u0007\u0000\u0000\u0000" +
   "\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000" +
   "\u001d\u0000\u0000\u0000\u0077\u0000\u0000\u0000" +
   "\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000" +
   "\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000" +
   "\u0069\u006d\u0061\u0067\u0065\u002e\u0063\u0061" +
   "\u0070\u006e\u0070\u003a\u0049\u006d\u0061\u0067" +
   "\u0065\u0050\u0072\u006f\u0074\u006f\u0053\u0074" +
   "\u0072\u0075\u0063\u0074\u0000\u0000\u0000\u0000" +
   "\u0000\u0000\u0000\u0000\u0001\u0000\u0001\u0000" +
   "\u0008\u0000\u0000\u0000\u0003\u0000\u0004\u0000" +
   "\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000" +
   "\u0000\u0000\u0001\u0000\u0000\u0000\u0000\u0000" +
   "\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000" +
   "\u0029\u0000\u0000\u0000\u002a\u0000\u0000\u0000" +
   "\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000" +
   "\u0024\u0000\u0000\u0000\u0003\u0000\u0001\u0000" +
   "\u0030\u0000\u0000\u0000\u0002\u0000\u0001\u0000" +
   "\u0001\u0000\u0000\u0000\u0001\u0000\u0000\u0000" +
   "\u0000\u0000\u0001\u0000\u0001\u0000\u0000\u0000" +
   "\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000" +
   "\u002d\u0000\u0000\u0000\u005a\u0000\u0000\u0000" +
   "\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000" +
   "\u002c\u0000\u0000\u0000\u0003\u0000\u0001\u0000" +
   "\u0038\u0000\u0000\u0000\u0002\u0000\u0001\u0000" +
   "\u006e\u0061\u006d\u0065\u0000\u0000\u0000\u0000" +
   "\u000c\u0000\u0000\u0000\u0000\u0000\u0000\u0000" +
   "\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000" +
   "\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000" +
   "\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000" +
   "\u000c\u0000\u0000\u0000\u0000\u0000\u0000\u0000" +
   "\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000" +
   "\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000" +
   "\u0069\u006d\u0061\u0067\u0065\u0042\u0079\u0074" +
   "\u0065\u0073\u0000\u0000\u0000\u0000\u0000\u0000" +
   "\r\u0000\u0000\u0000\u0000\u0000\u0000\u0000" +
   "\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000" +
   "\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000" +
   "\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000" +
   "\r\u0000\u0000\u0000\u0000\u0000\u0000\u0000" +
   "\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000" +
   "\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000" + "");
}
}

