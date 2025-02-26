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
public final class Series_FB extends Table {
  public static void ValidateVersion() { Constants.FLATBUFFERS_24_3_25(); }
  public static Series_FB getRootAsSeries_FB(ByteBuffer _bb) { return getRootAsSeries_FB(_bb, new Series_FB()); }
  public static Series_FB getRootAsSeries_FB(ByteBuffer _bb, Series_FB obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__assign(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public void __init(int _i, ByteBuffer _bb) { __reset(_i, _bb); }
  public Series_FB __assign(int _i, ByteBuffer _bb) { __init(_i, _bb); return this; }

  public String deviceid() { int o = __offset(4); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer deviceidAsByteBuffer() { return __vector_as_bytebuffer(4, 1); }
  public ByteBuffer deviceidInByteBuffer(ByteBuffer _bb) { return __vector_in_bytebuffer(_bb, 4, 1); }
  public int model() { int o = __offset(6); return o != 0 ? bb.getInt(o + bb_pos) : 0; }
  public at.fhv.kabi.samples.formatwriter.flatbuffers.at.fhv.kabi.samples.formatwriter.flatbuffers.Ecg_FB ecg() { return ecg(new at.fhv.kabi.samples.formatwriter.flatbuffers.at.fhv.kabi.samples.formatwriter.flatbuffers.Ecg_FB()); }
  public at.fhv.kabi.samples.formatwriter.flatbuffers.at.fhv.kabi.samples.formatwriter.flatbuffers.Ecg_FB ecg(at.fhv.kabi.samples.formatwriter.flatbuffers.at.fhv.kabi.samples.formatwriter.flatbuffers.Ecg_FB obj) { int o = __offset(8); return o != 0 ? obj.__assign(__indirect(o + bb_pos), bb) : null; }
  public at.fhv.kabi.samples.formatwriter.flatbuffers.at.fhv.kabi.samples.formatwriter.flatbuffers.BloodPressure_FB bloodpressure() { return bloodpressure(new at.fhv.kabi.samples.formatwriter.flatbuffers.at.fhv.kabi.samples.formatwriter.flatbuffers.BloodPressure_FB()); }
  public at.fhv.kabi.samples.formatwriter.flatbuffers.at.fhv.kabi.samples.formatwriter.flatbuffers.BloodPressure_FB bloodpressure(at.fhv.kabi.samples.formatwriter.flatbuffers.at.fhv.kabi.samples.formatwriter.flatbuffers.BloodPressure_FB obj) { int o = __offset(10); return o != 0 ? obj.__assign(__indirect(o + bb_pos), bb) : null; }
  public int heartRate() { int o = __offset(12); return o != 0 ? bb.getInt(o + bb_pos) : 0; }
  public long timestamp() { int o = __offset(14); return o != 0 ? bb.getLong(o + bb_pos) : 0L; }
  public String timezone() { int o = __offset(16); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer timezoneAsByteBuffer() { return __vector_as_bytebuffer(16, 1); }
  public ByteBuffer timezoneInByteBuffer(ByteBuffer _bb) { return __vector_in_bytebuffer(_bb, 16, 1); }

  public static int createSeries_FB(FlatBufferBuilder builder,
      int deviceidOffset,
      int model,
      int ecgOffset,
      int bloodpressureOffset,
      int heartRate,
      long timestamp,
      int timezoneOffset) {
    builder.startTable(7);
    Series_FB.addTimestamp(builder, timestamp);
    Series_FB.addTimezone(builder, timezoneOffset);
    Series_FB.addHeartRate(builder, heartRate);
    Series_FB.addBloodpressure(builder, bloodpressureOffset);
    Series_FB.addEcg(builder, ecgOffset);
    Series_FB.addModel(builder, model);
    Series_FB.addDeviceid(builder, deviceidOffset);
    return Series_FB.endSeries_FB(builder);
  }

  public static void startSeries_FB(FlatBufferBuilder builder) { builder.startTable(7); }
  public static void addDeviceid(FlatBufferBuilder builder, int deviceidOffset) { builder.addOffset(0, deviceidOffset, 0); }
  public static void addModel(FlatBufferBuilder builder, int model) { builder.addInt(1, model, 0); }
  public static void addEcg(FlatBufferBuilder builder, int ecgOffset) { builder.addOffset(2, ecgOffset, 0); }
  public static void addBloodpressure(FlatBufferBuilder builder, int bloodpressureOffset) { builder.addOffset(3, bloodpressureOffset, 0); }
  public static void addHeartRate(FlatBufferBuilder builder, int heartRate) { builder.addInt(4, heartRate, 0); }
  public static void addTimestamp(FlatBufferBuilder builder, long timestamp) { builder.addLong(5, timestamp, 0L); }
  public static void addTimezone(FlatBufferBuilder builder, int timezoneOffset) { builder.addOffset(6, timezoneOffset, 0); }
  public static int endSeries_FB(FlatBufferBuilder builder) {
    int o = builder.endTable();
    return o;
  }

  public static final class Vector extends BaseVector {
    public Vector __assign(int _vector, int _element_size, ByteBuffer _bb) { __reset(_vector, _element_size, _bb); return this; }

    public Series_FB get(int j) { return get(new Series_FB(), j); }
    public Series_FB get(Series_FB obj, int j) {  return obj.__assign(__indirect(__element(j), bb), bb); }
  }
}

