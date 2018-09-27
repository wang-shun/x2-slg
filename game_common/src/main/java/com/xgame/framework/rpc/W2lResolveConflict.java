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
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)", date = "2016-10-09")
public class W2lResolveConflict implements org.apache.thrift.TBase<W2lResolveConflict, W2lResolveConflict._Fields>, java.io.Serializable, Cloneable, Comparable<W2lResolveConflict> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("W2lResolveConflict");

  private static final org.apache.thrift.protocol.TField PASSIER_FIELD_DESC = new org.apache.thrift.protocol.TField("passier", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField ACTIVER_FIELD_DESC = new org.apache.thrift.protocol.TField("activer", org.apache.thrift.protocol.TType.STRUCT, (short)2);
  private static final org.apache.thrift.protocol.TField PASSIER_SOLDIER_JSON_FIELD_DESC = new org.apache.thrift.protocol.TField("passierSoldierJson", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField ACTIVER_SOLDIER_JSON_FIELD_DESC = new org.apache.thrift.protocol.TField("activerSoldierJson", org.apache.thrift.protocol.TType.STRING, (short)4);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new W2lResolveConflictStandardSchemeFactory());
    schemes.put(TupleScheme.class, new W2lResolveConflictTupleSchemeFactory());
  }

  public RPC_Sprite passier; // required
  public RPC_Sprite activer; // required
  public String passierSoldierJson; // required
  public String activerSoldierJson; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    PASSIER((short)1, "passier"),
    ACTIVER((short)2, "activer"),
    PASSIER_SOLDIER_JSON((short)3, "passierSoldierJson"),
    ACTIVER_SOLDIER_JSON((short)4, "activerSoldierJson");

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
        case 1: // PASSIER
          return PASSIER;
        case 2: // ACTIVER
          return ACTIVER;
        case 3: // PASSIER_SOLDIER_JSON
          return PASSIER_SOLDIER_JSON;
        case 4: // ACTIVER_SOLDIER_JSON
          return ACTIVER_SOLDIER_JSON;
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
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.PASSIER, new org.apache.thrift.meta_data.FieldMetaData("passier", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, RPC_Sprite.class)));
    tmpMap.put(_Fields.ACTIVER, new org.apache.thrift.meta_data.FieldMetaData("activer", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, RPC_Sprite.class)));
    tmpMap.put(_Fields.PASSIER_SOLDIER_JSON, new org.apache.thrift.meta_data.FieldMetaData("passierSoldierJson", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.ACTIVER_SOLDIER_JSON, new org.apache.thrift.meta_data.FieldMetaData("activerSoldierJson", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(W2lResolveConflict.class, metaDataMap);
  }

  public W2lResolveConflict() {
  }

  public W2lResolveConflict(
    RPC_Sprite passier,
    RPC_Sprite activer,
    String passierSoldierJson,
    String activerSoldierJson)
  {
    this();
    this.passier = passier;
    this.activer = activer;
    this.passierSoldierJson = passierSoldierJson;
    this.activerSoldierJson = activerSoldierJson;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public W2lResolveConflict(W2lResolveConflict other) {
    if (other.isSetPassier()) {
      this.passier = new RPC_Sprite(other.passier);
    }
    if (other.isSetActiver()) {
      this.activer = new RPC_Sprite(other.activer);
    }
    if (other.isSetPassierSoldierJson()) {
      this.passierSoldierJson = other.passierSoldierJson;
    }
    if (other.isSetActiverSoldierJson()) {
      this.activerSoldierJson = other.activerSoldierJson;
    }
  }

  public W2lResolveConflict deepCopy() {
    return new W2lResolveConflict(this);
  }

  @Override
  public void clear() {
    this.passier = null;
    this.activer = null;
    this.passierSoldierJson = null;
    this.activerSoldierJson = null;
  }

  public RPC_Sprite getPassier() {
    return this.passier;
  }

  public W2lResolveConflict setPassier(RPC_Sprite passier) {
    this.passier = passier;
    return this;
  }

  public void unsetPassier() {
    this.passier = null;
  }

  /** Returns true if field passier is set (has been assigned a value) and false otherwise */
  public boolean isSetPassier() {
    return this.passier != null;
  }

  public void setPassierIsSet(boolean value) {
    if (!value) {
      this.passier = null;
    }
  }

  public RPC_Sprite getActiver() {
    return this.activer;
  }

  public W2lResolveConflict setActiver(RPC_Sprite activer) {
    this.activer = activer;
    return this;
  }

  public void unsetActiver() {
    this.activer = null;
  }

  /** Returns true if field activer is set (has been assigned a value) and false otherwise */
  public boolean isSetActiver() {
    return this.activer != null;
  }

  public void setActiverIsSet(boolean value) {
    if (!value) {
      this.activer = null;
    }
  }

  public String getPassierSoldierJson() {
    return this.passierSoldierJson;
  }

  public W2lResolveConflict setPassierSoldierJson(String passierSoldierJson) {
    this.passierSoldierJson = passierSoldierJson;
    return this;
  }

  public void unsetPassierSoldierJson() {
    this.passierSoldierJson = null;
  }

  /** Returns true if field passierSoldierJson is set (has been assigned a value) and false otherwise */
  public boolean isSetPassierSoldierJson() {
    return this.passierSoldierJson != null;
  }

  public void setPassierSoldierJsonIsSet(boolean value) {
    if (!value) {
      this.passierSoldierJson = null;
    }
  }

  public String getActiverSoldierJson() {
    return this.activerSoldierJson;
  }

  public W2lResolveConflict setActiverSoldierJson(String activerSoldierJson) {
    this.activerSoldierJson = activerSoldierJson;
    return this;
  }

  public void unsetActiverSoldierJson() {
    this.activerSoldierJson = null;
  }

  /** Returns true if field activerSoldierJson is set (has been assigned a value) and false otherwise */
  public boolean isSetActiverSoldierJson() {
    return this.activerSoldierJson != null;
  }

  public void setActiverSoldierJsonIsSet(boolean value) {
    if (!value) {
      this.activerSoldierJson = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case PASSIER:
      if (value == null) {
        unsetPassier();
      } else {
        setPassier((RPC_Sprite)value);
      }
      break;

    case ACTIVER:
      if (value == null) {
        unsetActiver();
      } else {
        setActiver((RPC_Sprite)value);
      }
      break;

    case PASSIER_SOLDIER_JSON:
      if (value == null) {
        unsetPassierSoldierJson();
      } else {
        setPassierSoldierJson((String)value);
      }
      break;

    case ACTIVER_SOLDIER_JSON:
      if (value == null) {
        unsetActiverSoldierJson();
      } else {
        setActiverSoldierJson((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case PASSIER:
      return getPassier();

    case ACTIVER:
      return getActiver();

    case PASSIER_SOLDIER_JSON:
      return getPassierSoldierJson();

    case ACTIVER_SOLDIER_JSON:
      return getActiverSoldierJson();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case PASSIER:
      return isSetPassier();
    case ACTIVER:
      return isSetActiver();
    case PASSIER_SOLDIER_JSON:
      return isSetPassierSoldierJson();
    case ACTIVER_SOLDIER_JSON:
      return isSetActiverSoldierJson();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof W2lResolveConflict)
      return this.equals((W2lResolveConflict)that);
    return false;
  }

  public boolean equals(W2lResolveConflict that) {
    if (that == null)
      return false;

    boolean this_present_passier = true && this.isSetPassier();
    boolean that_present_passier = true && that.isSetPassier();
    if (this_present_passier || that_present_passier) {
      if (!(this_present_passier && that_present_passier))
        return false;
      if (!this.passier.equals(that.passier))
        return false;
    }

    boolean this_present_activer = true && this.isSetActiver();
    boolean that_present_activer = true && that.isSetActiver();
    if (this_present_activer || that_present_activer) {
      if (!(this_present_activer && that_present_activer))
        return false;
      if (!this.activer.equals(that.activer))
        return false;
    }

    boolean this_present_passierSoldierJson = true && this.isSetPassierSoldierJson();
    boolean that_present_passierSoldierJson = true && that.isSetPassierSoldierJson();
    if (this_present_passierSoldierJson || that_present_passierSoldierJson) {
      if (!(this_present_passierSoldierJson && that_present_passierSoldierJson))
        return false;
      if (!this.passierSoldierJson.equals(that.passierSoldierJson))
        return false;
    }

    boolean this_present_activerSoldierJson = true && this.isSetActiverSoldierJson();
    boolean that_present_activerSoldierJson = true && that.isSetActiverSoldierJson();
    if (this_present_activerSoldierJson || that_present_activerSoldierJson) {
      if (!(this_present_activerSoldierJson && that_present_activerSoldierJson))
        return false;
      if (!this.activerSoldierJson.equals(that.activerSoldierJson))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_passier = true && (isSetPassier());
    list.add(present_passier);
    if (present_passier)
      list.add(passier);

    boolean present_activer = true && (isSetActiver());
    list.add(present_activer);
    if (present_activer)
      list.add(activer);

    boolean present_passierSoldierJson = true && (isSetPassierSoldierJson());
    list.add(present_passierSoldierJson);
    if (present_passierSoldierJson)
      list.add(passierSoldierJson);

    boolean present_activerSoldierJson = true && (isSetActiverSoldierJson());
    list.add(present_activerSoldierJson);
    if (present_activerSoldierJson)
      list.add(activerSoldierJson);

    return list.hashCode();
  }

  @Override
  public int compareTo(W2lResolveConflict other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetPassier()).compareTo(other.isSetPassier());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPassier()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.passier, other.passier);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetActiver()).compareTo(other.isSetActiver());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetActiver()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.activer, other.activer);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPassierSoldierJson()).compareTo(other.isSetPassierSoldierJson());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPassierSoldierJson()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.passierSoldierJson, other.passierSoldierJson);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetActiverSoldierJson()).compareTo(other.isSetActiverSoldierJson());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetActiverSoldierJson()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.activerSoldierJson, other.activerSoldierJson);
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
    StringBuilder sb = new StringBuilder("W2lResolveConflict(");
    boolean first = true;

    sb.append("passier:");
    if (this.passier == null) {
      sb.append("null");
    } else {
      sb.append(this.passier);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("activer:");
    if (this.activer == null) {
      sb.append("null");
    } else {
      sb.append(this.activer);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("passierSoldierJson:");
    if (this.passierSoldierJson == null) {
      sb.append("null");
    } else {
      sb.append(this.passierSoldierJson);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("activerSoldierJson:");
    if (this.activerSoldierJson == null) {
      sb.append("null");
    } else {
      sb.append(this.activerSoldierJson);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
    if (passier != null) {
      passier.validate();
    }
    if (activer != null) {
      activer.validate();
    }
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
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class W2lResolveConflictStandardSchemeFactory implements SchemeFactory {
    public W2lResolveConflictStandardScheme getScheme() {
      return new W2lResolveConflictStandardScheme();
    }
  }

  private static class W2lResolveConflictStandardScheme extends StandardScheme<W2lResolveConflict> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, W2lResolveConflict struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // PASSIER
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.passier = new RPC_Sprite();
              struct.passier.read(iprot);
              struct.setPassierIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // ACTIVER
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.activer = new RPC_Sprite();
              struct.activer.read(iprot);
              struct.setActiverIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // PASSIER_SOLDIER_JSON
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.passierSoldierJson = iprot.readString();
              struct.setPassierSoldierJsonIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // ACTIVER_SOLDIER_JSON
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.activerSoldierJson = iprot.readString();
              struct.setActiverSoldierJsonIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, W2lResolveConflict struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.passier != null) {
        oprot.writeFieldBegin(PASSIER_FIELD_DESC);
        struct.passier.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.activer != null) {
        oprot.writeFieldBegin(ACTIVER_FIELD_DESC);
        struct.activer.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.passierSoldierJson != null) {
        oprot.writeFieldBegin(PASSIER_SOLDIER_JSON_FIELD_DESC);
        oprot.writeString(struct.passierSoldierJson);
        oprot.writeFieldEnd();
      }
      if (struct.activerSoldierJson != null) {
        oprot.writeFieldBegin(ACTIVER_SOLDIER_JSON_FIELD_DESC);
        oprot.writeString(struct.activerSoldierJson);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class W2lResolveConflictTupleSchemeFactory implements SchemeFactory {
    public W2lResolveConflictTupleScheme getScheme() {
      return new W2lResolveConflictTupleScheme();
    }
  }

  private static class W2lResolveConflictTupleScheme extends TupleScheme<W2lResolveConflict> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, W2lResolveConflict struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetPassier()) {
        optionals.set(0);
      }
      if (struct.isSetActiver()) {
        optionals.set(1);
      }
      if (struct.isSetPassierSoldierJson()) {
        optionals.set(2);
      }
      if (struct.isSetActiverSoldierJson()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetPassier()) {
        struct.passier.write(oprot);
      }
      if (struct.isSetActiver()) {
        struct.activer.write(oprot);
      }
      if (struct.isSetPassierSoldierJson()) {
        oprot.writeString(struct.passierSoldierJson);
      }
      if (struct.isSetActiverSoldierJson()) {
        oprot.writeString(struct.activerSoldierJson);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, W2lResolveConflict struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        struct.passier = new RPC_Sprite();
        struct.passier.read(iprot);
        struct.setPassierIsSet(true);
      }
      if (incoming.get(1)) {
        struct.activer = new RPC_Sprite();
        struct.activer.read(iprot);
        struct.setActiverIsSet(true);
      }
      if (incoming.get(2)) {
        struct.passierSoldierJson = iprot.readString();
        struct.setPassierSoldierJsonIsSet(true);
      }
      if (incoming.get(3)) {
        struct.activerSoldierJson = iprot.readString();
        struct.setActiverSoldierJsonIsSet(true);
      }
    }
  }

}
