package com.xgame.logic.server.game.playerattribute.entity.description;

/**
 * 属性描述
 * @author jacky.jiang
 *
 */
public class AttributeDescription {
	private int attrId;
	private long uid;
	private String name;
	private double value;
	public int getAttrId() {
		return attrId;
	}
	public void setAttrId(int attrId) {
		this.attrId = attrId;
	}
	public long getUid() {
		return uid;
	}
	public void setUid(long uid) {
		this.uid = uid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	
}
