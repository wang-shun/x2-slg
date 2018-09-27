/**
 * Autogenerated by Thrift Compiler (0.9.3)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.xgame.framework.rpc;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import javax.annotation.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)", date = "2016-09-13")
public class W2lPeijianInfo implements org.apache.thrift.TBase<W2lPeijianInfo, W2lPeijianInfo._Fields>, java.io.Serializable, Cloneable, Comparable<W2lPeijianInfo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("W2lPeijianInfo");

  private static final org.apache.thrift.protocol.TField SID_FIELD_DESC = new org.apache.thrift.protocol.TField("sid", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField LOCATION_FIELD_DESC = new org.apache.thrift.protocol.TField("location", org.apache.thrift.protocol.TType.I32, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new W2lPeijianInfoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new W2lPeijianInfoTupleSchemeFactory());
  }

  public int sid; // required
  public int location; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    SID((short)1, "sid"),
    LOCATION((short)2, "location");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // SID
          return SID;
        case 2: // LOCATION
          return LOCATION;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __SID_ISSET_ID = 0;
  private static final int __LOCATION_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.SID, new org.apache.thrift.meta_data.FieldMetaData("sid", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.LOCATION, new org.apache.thrift.meta_data.FieldMetaData("location", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(W2lPeijianInfo.class, metaDataMap);
  }

  public W2lPeijianInfo() {
  }

  public W2lPeijianInfo(
    int sid,
    int location)
  {
    this();
    this.sid = sid;
    setSidIsSet(true);
    this.location = location;
    setLocationIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public W2lPeijianInfo(W2lPeijianInfo other) {
    __isset_bitfield = other.__isset_bitfield;
    this.sid = other.sid;
    this.location = other.location;
  }

  public W2lPeijianInfo deepCopy() {
    return new W2lPeijianInfo(this);
  }

  @Override
  public void clear() {
    setSidIsSet(false);
    this.sid = 0;
    setLocationIsSet(false);
    this.location = 0;
  }

  public int getSid() {
    return this.sid;
  }

  public W2lPeijianInfo setSid(int sid) {
    this.sid = sid;
    setSidIsSet(true);
    return this;
  }

  public void unsetSid() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __SID_ISSET_ID);
  }

  /** Returns true if field sid is set (has been assigned a value) and false otherwise */
  public boolean isSetSid() {
    return EncodingUtils.testBit(__isset_bitfield, __SID_ISSET_ID);
  }

  public void setSidIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __SID_ISSET_ID, value);
  }

  public int getLocation() {
    return this.location;
  }

  public W2lPeijianInfo setLocation(int location) {
    this.location = location;
    setLocationIsSet(true);
    return this;
  }

  public void unsetLocation() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __LOCATION_ISSET_ID);
  }

  /** Returns true if field location is set (has been assigned a value) and false otherwise */
  public boolean isSetLocation() {
    return EncodingUtils.testBit(__isset_bitfield, __LOCATION_ISSET_ID);
  }

  public void setLocationIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __LOCATION_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case SID:
      if (value == null) {
        unsetSid();
      } else {
        setSid((Integer)value);
      }
      break;

    case LOCATION:
      if (value == null) {
        unsetLocation();
      } else {
        setLocation((Integer)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case SID:
      return getSid();

    case LOCATION:
      return getLocation();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case SID:
      return isSetSid();
    case LOCATION:
      return isSetLocation();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof W2lPeijianInfo)
      return this.equals((W2lPeijianInfo)that);
    return false;
  }

  public boolean equals(W2lPeijianInfo that) {
    if (that == null)
      return false;

    boolean this_present_sid = true;
    boolean that_present_sid = true;
    if (this_present_sid || that_present_sid) {
      if (!(this_present_sid && that_present_sid))
        return false;
      if (this.sid != that.sid)
        return false;
    }

    boolean this_present_location = true;
    boolean that_present_location = true;
    if (this_present_location || that_present_location) {
      if (!(this_present_location && that_present_location))
        return false;
      if (this.location != that.location)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_sid = true;
    list.add(present_sid);
    if (present_sid)
      list.add(sid);

    boolean present_location = true;
    list.add(present_location);
    if (present_location)
      list.add(location);

    return list.hashCode();
  }

  @Override
  public int compareTo(W2lPeijianInfo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetSid()).compareTo(other.isSetSid());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSid()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.sid, other.sid);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetLocation()).compareTo(other.isSetLocation());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLocation()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.location, other.location);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("W2lPeijianInfo(");
    boolean first = true;

    sb.append("sid:");
    sb.append(this.sid);
    first = false;
    if (!first) sb.append(", ");
    sb.append("location:");
    sb.append(this.location);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class W2lPeijianInfoStandardSchemeFactory implements SchemeFactory {
    public W2lPeijianInfoStandardScheme getScheme() {
      return new W2lPeijianInfoStandardScheme();
    }
  }

  private static class W2lPeijianInfoStandardScheme extends StandardScheme<W2lPeijianInfo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, W2lPeijianInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // SID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.sid = iprot.readI32();
              struct.setSidIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // LOCATION
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.location = iprot.readI32();
              struct.setLocationIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, W2lPeijianInfo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(SID_FIELD_DESC);
      oprot.writeI32(struct.sid);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(LOCATION_FIELD_DESC);
      oprot.writeI32(struct.location);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class W2lPeijianInfoTupleSchemeFactory implements SchemeFactory {
    public W2lPeijianInfoTupleScheme getScheme() {
      return new W2lPeijianInfoTupleScheme();
    }
  }

  private static class W2lPeijianInfoTupleScheme extends TupleScheme<W2lPeijianInfo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, W2lPeijianInfo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetSid()) {
        optionals.set(0);
      }
      if (struct.isSetLocation()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetSid()) {
        oprot.writeI32(struct.sid);
      }
      if (struct.isSetLocation()) {
        oprot.writeI32(struct.location);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, W2lPeijianInfo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.sid = iprot.readI32();
        struct.setSidIsSet(true);
      }
      if (incoming.get(1)) {
        struct.location = iprot.readI32();
        struct.setLocationIsSet(true);
      }
    }
  }

}
