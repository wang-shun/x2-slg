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
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)", date = "2016-09-23")
public class W2lSpaceInfo implements org.apache.thrift.TBase<W2lSpaceInfo, W2lSpaceInfo._Fields>, java.io.Serializable, Cloneable, Comparable<W2lSpaceInfo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("W2lSpaceInfo");

  private static final org.apache.thrift.protocol.TField VIEW_LINE_SIZE_FIELD_DESC = new org.apache.thrift.protocol.TField("viewLineSize", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField VIEW_SIZE_FIELD_DESC = new org.apache.thrift.protocol.TField("viewSize", org.apache.thrift.protocol.TType.I32, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new W2lSpaceInfoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new W2lSpaceInfoTupleSchemeFactory());
  }

  public int viewLineSize; // required
  public int viewSize; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    VIEW_LINE_SIZE((short)1, "viewLineSize"),
    VIEW_SIZE((short)2, "viewSize");

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
        case 1: // VIEW_LINE_SIZE
          return VIEW_LINE_SIZE;
        case 2: // VIEW_SIZE
          return VIEW_SIZE;
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
  private static final int __VIEWLINESIZE_ISSET_ID = 0;
  private static final int __VIEWSIZE_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.VIEW_LINE_SIZE, new org.apache.thrift.meta_data.FieldMetaData("viewLineSize", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.VIEW_SIZE, new org.apache.thrift.meta_data.FieldMetaData("viewSize", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(W2lSpaceInfo.class, metaDataMap);
  }

  public W2lSpaceInfo() {
  }

  public W2lSpaceInfo(
    int viewLineSize,
    int viewSize)
  {
    this();
    this.viewLineSize = viewLineSize;
    setViewLineSizeIsSet(true);
    this.viewSize = viewSize;
    setViewSizeIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public W2lSpaceInfo(W2lSpaceInfo other) {
    __isset_bitfield = other.__isset_bitfield;
    this.viewLineSize = other.viewLineSize;
    this.viewSize = other.viewSize;
  }

  public W2lSpaceInfo deepCopy() {
    return new W2lSpaceInfo(this);
  }

  @Override
  public void clear() {
    setViewLineSizeIsSet(false);
    this.viewLineSize = 0;
    setViewSizeIsSet(false);
    this.viewSize = 0;
  }

  public int getViewLineSize() {
    return this.viewLineSize;
  }

  public W2lSpaceInfo setViewLineSize(int viewLineSize) {
    this.viewLineSize = viewLineSize;
    setViewLineSizeIsSet(true);
    return this;
  }

  public void unsetViewLineSize() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __VIEWLINESIZE_ISSET_ID);
  }

  /** Returns true if field viewLineSize is set (has been assigned a value) and false otherwise */
  public boolean isSetViewLineSize() {
    return EncodingUtils.testBit(__isset_bitfield, __VIEWLINESIZE_ISSET_ID);
  }

  public void setViewLineSizeIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __VIEWLINESIZE_ISSET_ID, value);
  }

  public int getViewSize() {
    return this.viewSize;
  }

  public W2lSpaceInfo setViewSize(int viewSize) {
    this.viewSize = viewSize;
    setViewSizeIsSet(true);
    return this;
  }

  public void unsetViewSize() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __VIEWSIZE_ISSET_ID);
  }

  /** Returns true if field viewSize is set (has been assigned a value) and false otherwise */
  public boolean isSetViewSize() {
    return EncodingUtils.testBit(__isset_bitfield, __VIEWSIZE_ISSET_ID);
  }

  public void setViewSizeIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __VIEWSIZE_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case VIEW_LINE_SIZE:
      if (value == null) {
        unsetViewLineSize();
      } else {
        setViewLineSize((Integer)value);
      }
      break;

    case VIEW_SIZE:
      if (value == null) {
        unsetViewSize();
      } else {
        setViewSize((Integer)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case VIEW_LINE_SIZE:
      return getViewLineSize();

    case VIEW_SIZE:
      return getViewSize();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case VIEW_LINE_SIZE:
      return isSetViewLineSize();
    case VIEW_SIZE:
      return isSetViewSize();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof W2lSpaceInfo)
      return this.equals((W2lSpaceInfo)that);
    return false;
  }

  public boolean equals(W2lSpaceInfo that) {
    if (that == null)
      return false;

    boolean this_present_viewLineSize = true;
    boolean that_present_viewLineSize = true;
    if (this_present_viewLineSize || that_present_viewLineSize) {
      if (!(this_present_viewLineSize && that_present_viewLineSize))
        return false;
      if (this.viewLineSize != that.viewLineSize)
        return false;
    }

    boolean this_present_viewSize = true;
    boolean that_present_viewSize = true;
    if (this_present_viewSize || that_present_viewSize) {
      if (!(this_present_viewSize && that_present_viewSize))
        return false;
      if (this.viewSize != that.viewSize)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_viewLineSize = true;
    list.add(present_viewLineSize);
    if (present_viewLineSize)
      list.add(viewLineSize);

    boolean present_viewSize = true;
    list.add(present_viewSize);
    if (present_viewSize)
      list.add(viewSize);

    return list.hashCode();
  }

  @Override
  public int compareTo(W2lSpaceInfo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetViewLineSize()).compareTo(other.isSetViewLineSize());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetViewLineSize()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.viewLineSize, other.viewLineSize);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetViewSize()).compareTo(other.isSetViewSize());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetViewSize()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.viewSize, other.viewSize);
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
    StringBuilder sb = new StringBuilder("W2lSpaceInfo(");
    boolean first = true;

    sb.append("viewLineSize:");
    sb.append(this.viewLineSize);
    first = false;
    if (!first) sb.append(", ");
    sb.append("viewSize:");
    sb.append(this.viewSize);
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

  private static class W2lSpaceInfoStandardSchemeFactory implements SchemeFactory {
    public W2lSpaceInfoStandardScheme getScheme() {
      return new W2lSpaceInfoStandardScheme();
    }
  }

  private static class W2lSpaceInfoStandardScheme extends StandardScheme<W2lSpaceInfo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, W2lSpaceInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // VIEW_LINE_SIZE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.viewLineSize = iprot.readI32();
              struct.setViewLineSizeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // VIEW_SIZE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.viewSize = iprot.readI32();
              struct.setViewSizeIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, W2lSpaceInfo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(VIEW_LINE_SIZE_FIELD_DESC);
      oprot.writeI32(struct.viewLineSize);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(VIEW_SIZE_FIELD_DESC);
      oprot.writeI32(struct.viewSize);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class W2lSpaceInfoTupleSchemeFactory implements SchemeFactory {
    public W2lSpaceInfoTupleScheme getScheme() {
      return new W2lSpaceInfoTupleScheme();
    }
  }

  private static class W2lSpaceInfoTupleScheme extends TupleScheme<W2lSpaceInfo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, W2lSpaceInfo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetViewLineSize()) {
        optionals.set(0);
      }
      if (struct.isSetViewSize()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetViewLineSize()) {
        oprot.writeI32(struct.viewLineSize);
      }
      if (struct.isSetViewSize()) {
        oprot.writeI32(struct.viewSize);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, W2lSpaceInfo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.viewLineSize = iprot.readI32();
        struct.setViewLineSizeIsSet(true);
      }
      if (incoming.get(1)) {
        struct.viewSize = iprot.readI32();
        struct.setViewSizeIsSet(true);
      }
    }
  }

}

