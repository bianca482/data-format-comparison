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
public final class SmartLightController_FB extends Table {
  public static void ValidateVersion() { Constants.FLATBUFFERS_24_3_25(); }
  public static SmartLightController_FB getRootAsSmartLightController_FB(ByteBuffer _bb) { return getRootAsSmartLightController_FB(_bb, new SmartLightController_FB()); }
  public static SmartLightController_FB getRootAsSmartLightController_FB(ByteBuffer _bb, SmartLightController_FB obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__assign(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public void __init(int _i, ByteBuffer _bb) { __reset(_i, _bb); }
  public SmartLightController_FB __assign(int _i, ByteBuffer _bb) { __init(_i, _bb); return this; }

  public boolean on() { int o = __offset(4); return o != 0 ? 0!=bb.get(o + bb_pos) : false; }
  public int sat() { int o = __offset(6); return o != 0 ? bb.getInt(o + bb_pos) : 0; }
  public int bri() { int o = __offset(8); return o != 0 ? bb.getInt(o + bb_pos) : 0; }
  public int hue() { int o = __offset(10); return o != 0 ? bb.getInt(o + bb_pos) : 0; }

  public static int createSmartLightController_FB(FlatBufferBuilder builder,
      boolean on,
      int sat,
      int bri,
      int hue) {
    builder.startTable(4);
    SmartLightController_FB.addHue(builder, hue);
    SmartLightController_FB.addBri(builder, bri);
    SmartLightController_FB.addSat(builder, sat);
    SmartLightController_FB.addOn(builder, on);
    return SmartLightController_FB.endSmartLightController_FB(builder);
  }

  public static void startSmartLightController_FB(FlatBufferBuilder builder) { builder.startTable(4); }
  public static void addOn(FlatBufferBuilder builder, boolean on) { builder.addBoolean(0, on, false); }
  public static void addSat(FlatBufferBuilder builder, int sat) { builder.addInt(1, sat, 0); }
  public static void addBri(FlatBufferBuilder builder, int bri) { builder.addInt(2, bri, 0); }
  public static void addHue(FlatBufferBuilder builder, int hue) { builder.addInt(3, hue, 0); }
  public static int endSmartLightController_FB(FlatBufferBuilder builder) {
    int o = builder.endTable();
    return o;
  }
  public static void finishSmartLightController_FBBuffer(FlatBufferBuilder builder, int offset) { builder.finish(offset); }
  public static void finishSizePrefixedSmartLightController_FBBuffer(FlatBufferBuilder builder, int offset) { builder.finishSizePrefixed(offset); }

  public static final class Vector extends BaseVector {
    public Vector __assign(int _vector, int _element_size, ByteBuffer _bb) { __reset(_vector, _element_size, _bb); return this; }

    public SmartLightController_FB get(int j) { return get(new SmartLightController_FB(), j); }
    public SmartLightController_FB get(SmartLightController_FB obj, int j) {  return obj.__assign(__indirect(__element(j), bb), bb); }
  }
}

