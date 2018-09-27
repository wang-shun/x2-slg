package com.xgame.logic.server.game.country.structs.build.camp.data;

import io.protostuff.Tag;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *2017-1-12  21:33:12
 *@author ye.yuan
 *
 */
public class BattleSoldier implements Serializable{

	/**
	 * 
	 */
	static final long serialVersionUID = 1L;

	@Tag(1)
	public int soldierId;
	
	@Tag(2)
	public volatile int num;
	
	@Tag(3)
	public String name;
	
	@Tag(4)
	public List<PeiJian> peijianList = new ArrayList<PeiJian>();
	
	@Tag(5)
	public int level;
	
	/**
	 * 建筑模版id
	 */
	@Tag(6)
	public int campType;
	
	@Tag(7)
	public int buildIndex;//自建顺序
	
	@Tag(8)
	public volatile int hurtNum;
	
	@Tag(9)
	public volatile int runNum;
	
	@Tag(10)
	public volatile int defebseNum;
	
	@Tag(11)
	public int bindPos;
	
	@Tag(12)
	public String icon;
	
//	@Tag(13)
//	public SoldierAttributeObject attributeObject;

	public int getSoldierId() {
		return soldierId;
	}

	public void setSoldierId(int soldierId) {
		this.soldierId = soldierId;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<PeiJian> getPeijianList() {
		return peijianList;
	}

	public void setPeijianList(List<PeiJian> peijianList) {
		this.peijianList = peijianList;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getCampType() {
		return campType;
	}

	public void setCampType(int campType) {
		this.campType = campType;
	}

	public int getBuildIndex() {
		return buildIndex;
	}

	public void setBuildIndex(int buildIndex) {
		this.buildIndex = buildIndex;
	}

	public int getHurtNum() {
		return hurtNum;
	}

	public void setHurtNum(int hurtNum) {
		this.hurtNum = hurtNum;
	}

	public int getRunNum() {
		return runNum;
	}

	public void setRunNum(int runNum) {
		this.runNum = runNum;
	}

	public int getDefebseNum() {
		return defebseNum;
	}

	public void setDefebseNum(int defebseNum) {
		this.defebseNum = defebseNum;
	}

	public int getBindPos() {
		return bindPos;
	}

	public void setBindPos(int bindPos) {
		this.bindPos = bindPos;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

//	public SoldierAttributeObject getAttributeObject() {
//		return attributeObject;
//	}
//
//	public void setAttributeObject(SoldierAttributeObject attributeObject) {
//		this.attributeObject = attributeObject;
//	}
}
