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
public final class Location_FB extends Table {
  public static void ValidateVersion() { Constants.FLATBUFFERS_24_3_25(); }
  public static Location_FB getRootAsLocation_FB(ByteBuffer _bb) { return getRootAsLocation_FB(_bb, new Location_FB()); }
  public static Location_FB getRootAsLocation_FB(ByteBuffer _bb, Location_FB obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__assign(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public void __init(int _i, ByteBuffer _bb) { __reset(_i, _bb); }
  public Location_FB __assign(int _i, ByteBuffer _bb) { __init(_i, _bb); return this; }

  public String type() { int o = __offset(4); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer typeAsByteBuffer() { return __vector_as_bytebuffer(4, 1); }
  public ByteBuffer typeInByteBuffer(ByteBuffer _bb) { return __vector_in_bytebuffer(_bb, 4, 1); }
  public at.fhv.kabi.samples.formatwriter.flatbuffers.at.fhv.kabi.samples.formatwriter.flatbuffers.Geometry_FB geometry() { return geometry(new at.fhv.kabi.samples.formatwriter.flatbuffers.at.fhv.kabi.samples.formatwriter.flatbuffers.Geometry_FB()); }
  public at.fhv.kabi.samples.formatwriter.flatbuffers.at.fhv.kabi.samples.formatwriter.flatbuffers.Geometry_FB geometry(at.fhv.kabi.samples.formatwriter.flatbuffers.at.fhv.kabi.samples.formatwriter.flatbuffers.Geometry_FB obj) { int o = __offset(6); return o != 0 ? obj.__assign(__indirect(o + bb_pos), bb) : null; }

  public static int createLocation_FB(FlatBufferBuilder builder,
      int typeOffset,
      int geometryOffset) {
    builder.startTable(2);
    Location_FB.addGeometry(builder, geometryOffset);
    Location_FB.addType(builder, typeOffset);
    return Location_FB.endLocation_FB(builder);
  }

  public static void startLocation_FB(FlatBufferBuilder builder) { builder.startTable(2); }
  public static void addType(FlatBufferBuilder builder, int typeOffset) { builder.addOffset(0, typeOffset, 0); }
  public static void addGeometry(FlatBufferBuilder builder, int geometryOffset) { builder.addOffset(1, geometryOffset, 0); }
  public static int endLocation_FB(FlatBufferBuilder builder) {
    int o = builder.endTable();
    return o;
  }

  public static final class Vector extends BaseVector {
    public Vector __assign(int _vector, int _element_size, ByteBuffer _bb) { __reset(_vector, _element_size, _bb); return this; }

    public Location_FB get(int j) { return get(new Location_FB(), j); }
    public Location_FB get(Location_FB obj, int j) {  return obj.__assign(__indirect(__element(j), bb), bb); }
  }
}

