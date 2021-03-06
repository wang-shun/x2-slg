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
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)", date = "2016-09-18")
public class L2WModSoldier implements org.apache.thrift.TBase<L2WModSoldier, L2WModSoldier._Fields>, java.io.Serializable, Cloneable, Comparable<L2WModSoldier> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("L2WModSoldier");

  private static final org.apache.thrift.protocol.TField SOLDIER_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("soldierId", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField NUM_FIELD_DESC = new org.apache.thrift.protocol.TField("num", org.apache.thrift.protocol.TType.I32, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new L2WModSoldierStandardSchemeFactory());
    schemes.put(TupleScheme.class, new L2WModSoldierTupleSchemeFactory());
  }

  public long soldierId; // required
  public int num; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    SOLDIER_ID((short)1, "soldierId"),
    NUM((short)2, "num");

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
        case 1: // SOLDIER_ID
          return SOLDIER_ID;
        case 2: // NUM
          return NUM;
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
  private static final int __SOLDIERID_ISSET_ID = 0;
  private static final int __NUM_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.SOLDIER_ID, new org.apache.thrift.meta_data.FieldMetaData("soldierId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.NUM, new org.apache.thrift.meta_data.FieldMetaData("num", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(L2WModSoldier.class, metaDataMap);
  }

  public L2WModSoldier() {
  }

  public L2WModSoldier(
    long soldierId,
    int num)
  {
    this();
    this.soldierId = soldierId;
    setSoldierIdIsSet(true);
    this.num = num;
    setNumIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public L2WModSoldier(L2WModSoldier other) {
    __isset_bitfield = other.__isset_bitfield;
    this.soldierId = other.soldierId;
    this.num = other.num;
  }

  public L2WModSoldier deepCopy() {
    return new L2WModSoldier(this);
  }

  @Override
  public void clear() {
    setSoldierIdIsSet(false);
    this.soldierId = 0;
    setNumIsSet(false);
    this.num = 0;
  }

  public long getSoldierId() {
    return this.soldierId;
  }

  public L2WModSoldier setSoldierId(long soldierId) {
    this.soldierId = soldierId;
    setSoldierIdIsSet(true);
    return this;
  }

  public void unsetSoldierId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __SOLDIERID_ISSET_ID);
  }

  /** Returns true if field soldierId is set (has been assigned a value) and false otherwise */
  public boolean isSetSoldierId() {
    return EncodingUtils.testBit(__isset_bitfield, __SOLDIERID_ISSET_ID);
  }

  public void setSoldierIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __SOLDIERID_ISSET_ID, value);
  }

  public int getNum() {
    return this.num;
  }

  public L2WModSoldier setNum(int num) {
    this.num = num;
    setNumIsSet(true);
    return this;
  }

  public void unsetNum() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __NUM_ISSET_ID);
  }

  /** Returns true if field num is set (has been assigned a value) and false otherwise */
  public boolean isSetNum() {
    return EncodingUtils.testBit(__isset_bitfield, __NUM_ISSET_ID);
  }

  public void setNumIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __NUM_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case SOLDIER_ID:
      if (value == null) {
        unsetSoldierId();
      } else {
        setSoldierId((Long)value);
      }
      break;

    case NUM:
      if (value == null) {
        unsetNum();
      } else {
        setNum((Integer)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case SOLDIER_ID:
      return getSoldierId();

    case NUM:
      return getNum();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case SOLDIER_ID:
      return isSetSoldierId();
    case NUM:
      return isSetNum();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof L2WModSoldier)
      return this.equals((L2WModSoldier)that);
    return false;
  }

  public boolean equals(L2WModSoldier that) {
    if (that == null)
      return false;

    boolean this_present_soldierId = true;
    boolean that_present_soldierId = true;
    if (this_present_soldierId || that_present_soldierId) {
      if (!(this_present_soldierId && that_present_soldierId))
        return false;
      if (this.soldierId != that.soldierId)
        return false;
    }

    boolean this_present_num = true;
    boolean that_present_num = true;
    if (this_present_num || that_present_num) {
      if (!(this_present_num && that_present_num))
        return false;
      if (this.num != that.num)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_soldierId = true;
    list.add(present_soldierId);
    if (present_soldierId)
      list.add(soldierId);

    boolean present_num = true;
    list.add(present_num);
    if (present_num)
      list.add(num);

    return list.hashCode();
  }

  @Override
  public int compareTo(L2WModSoldier other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetSoldierId()).compareTo(other.isSetSoldierId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSoldierId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.soldierId, other.soldierId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetNum()).compareTo(other.isSetNum());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetNum()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.num, other.num);
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
    StringBuilder sb = new StringBuilder("L2WModSoldier(");
    boolean first = true;

    sb.append("soldierId:");
    sb.append(this.soldierId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("num:");
    sb.append(this.num);
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

  private static class L2WModSoldierStandardSchemeFactory implements SchemeFactory {
    public L2WModSoldierStandardScheme getScheme() {
      return new L2WModSoldierStandardScheme();
    }
  }

  private static class L2WModSoldierStandardScheme extends StandardScheme<L2WModSoldier> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, L2WModSoldier struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // SOLDIER_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.soldierId = iprot.readI64();
              struct.setSoldierIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // NUM
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.num = iprot.readI32();
              struct.setNumIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, L2WModSoldier struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(SOLDIER_ID_FIELD_DESC);
      oprot.writeI64(struct.soldierId);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(NUM_FIELD_DESC);
      oprot.writeI32(struct.num);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class L2WModSoldierTupleSchemeFactory implements SchemeFactory {
    public L2WModSoldierTupleScheme getScheme() {
      return new L2WModSoldierTupleScheme();
    }
  }

  private static class L2WModSoldierTupleScheme extends TupleScheme<L2WModSoldier> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, L2WModSoldier struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetSoldierId()) {
        optionals.set(0);
      }
      if (struct.isSetNum()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetSoldierId()) {
        oprot.writeI64(struct.soldierId);
      }
      if (struct.isSetNum()) {
        oprot.writeI32(struct.num);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, L2WModSoldier struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.soldierId = iprot.readI64();
        struct.setSoldierIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.num = iprot.readI32();
        struct.setNumIsSet(true);
      }
    }
  }

}

