package com.xgame.logic.server.game.country.structs.build.Radar.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.xgame.logic.server.game.country.structs.build.camp.data.PeiJian;

import io.protostuff.Tag;

/**
 *
 * 2017-1-06 16:41:04
 *
 * @author ye.yuan
 *
 */
public class RadarDefebseSoldier implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 唯一id
	@Tag(1)
	private int soldierId;

	// 正常数量
	@Tag(2)
	private int num;

	//
	@Tag(3)
	private String name;

	//
	@Tag(4)
	private int buildIndex;

	//
	@Tag(5)
	private int campType;

	//
	@Tag(6)
	private List<PeiJian> peijians = new ArrayList<PeiJian>();
	
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

	public int getBuildIndex() {
		return buildIndex;
	}

	public void setBuildIndex(int buildIndex) {
		this.buildIndex = buildIndex;
	}

	public int getCampType() {
		return campType;
	}

	public void setCampType(int campType) {
		this.campType = campType;
	}

	public List<PeiJian> getPeijians() {
		return peijians;
	}

	public void setPeijians(List<PeiJian> peijians) {
		this.peijians = peijians;
	}

}
