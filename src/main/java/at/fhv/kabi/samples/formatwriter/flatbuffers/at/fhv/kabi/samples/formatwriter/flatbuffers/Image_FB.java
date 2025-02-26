// automatically generated by the FlatBuffers compiler, do not modify

package at.fhv.kabi.samples.formatwriter.flatbuffers.at.fhv.kabi.samples.formatwriter.flatbuffers;

import com.google.flatbuffers.BaseVector;
import com.google.flatbuffers.BooleanVector;
import com.google.flatbuffers.ByteVector;
import com.google.flatbuffers.Constants;
import com.google.flatbuffers.DoubleVector;
import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.FloatVector;
import com.google.flatbuffers.IntVector;
import com.google.flatbuffers.LongVector;
import com.google.flatbuffers.ShortVector;
import com.google.flatbuffers.StringVector;
import com.google.flatbuffers.Struct;
import com.google.flatbuffers.Table;
import com.google.flatbuffers.UnionVector;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

@SuppressWarnings("unused")
public final class Image_FB extends Table {
  public static void ValidateVersion() { Constants.FLATBUFFERS_24_3_25(); }
  public static Image_FB getRootAsImage_FB(ByteBuffer _bb) { return getRootAsImage_FB(_bb, new Image_FB()); }
  public static Image_FB getRootAsImage_FB(ByteBuffer _bb, Image_FB obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__assign(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public void __init(int _i, ByteBuffer _bb) { __reset(_i, _bb); }
  public Image_FB __assign(int _i, ByteBuffer _bb) { __init(_i, _bb); return this; }

  public String name() { int o = __offset(4); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer nameAsByteBuffer() { return __vector_as_bytebuffer(4, 1); }
  public ByteBuffer nameInByteBuffer(ByteBuffer _bb) { return __vector_in_bytebuffer(_bb, 4, 1); }
  public byte imageBytes(int j) { int o = __offset(6); return o != 0 ? bb.get(__vector(o) + j * 1) : 0; }
  public int imageBytesLength() { int o = __offset(6); return o != 0 ? __vector_len(o) : 0; }
  public ByteVector imageBytesVector() { return imageBytesVector(new ByteVector()); }
  public ByteVector imageBytesVector(ByteVector obj) { int o = __offset(6); return o != 0 ? obj.__assign(__vector(o), bb) : null; }
  public ByteBuffer imageBytesAsByteBuffer() { return __vector_as_bytebuffer(6, 1); }
  public ByteBuffer imageBytesInByteBuffer(ByteBuffer _bb) { return __vector_in_bytebuffer(_bb, 6, 1); }

  public static int createImage_FB(FlatBufferBuilder builder,
      int nameOffset,
      int imageBytesOffset) {
    builder.startTable(2);
    Image_FB.addImageBytes(builder, imageBytesOffset);
    Image_FB.addName(builder, nameOffset);
    return Image_FB.endImage_FB(builder);
  }

  public static void startImage_FB(FlatBufferBuilder builder) { builder.startTable(2); }
  public static void addName(FlatBufferBuilder builder, int nameOffset) { builder.addOffset(0, nameOffset, 0); }
  public static void addImageBytes(FlatBufferBuilder builder, int imageBytesOffset) { builder.addOffset(1, imageBytesOffset, 0); }
  public static int createImageBytesVector(FlatBufferBuilder builder, byte[] data) { return builder.createByteVector(data); }
  public static int createImageBytesVector(FlatBufferBuilder builder, ByteBuffer data) { return builder.createByteVector(data); }
  public static void startImageBytesVector(FlatBufferBuilder builder, int numElems) { builder.startVector(1, numElems, 1); }
  public static int endImage_FB(FlatBufferBuilder builder) {
    int o = builder.endTable();
    return o;
  }
  public static void finishImage_FBBuffer(FlatBufferBuilder builder, int offset) { builder.finish(offset); }
  public static void finishSizePrefixedImage_FBBuffer(FlatBufferBuilder builder, int offset) { builder.finishSizePrefixed(offset); }

  public static final class Vector extends BaseVector {
    public Vector __assign(int _vector, int _element_size, ByteBuffer _bb) { __reset(_vector, _element_size, _bb); return this; }

    public Image_FB get(int j) { return get(new Image_FB(), j); }
    public Image_FB get(Image_FB obj, int j) {  return obj.__assign(__indirect(__element(j), bb), bb); }
  }
}

