package com.xgame.logic.server.game.country.structs.build.Radar.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.xgame.logic.server.core.utils.geometry.data.Vector2;
import com.xgame.logic.server.game.country.structs.build.camp.data.PeiJian;

import io.protostuff.Tag;

/**
 *雷达使用的士兵
 *2016-12-28  11:19:51
 *@author ye.yuan
 *
 */
public final class RadarSoldier implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Tag(1)
	private int soldierId;
	
	@Tag(2)
	private int num;
	
	@Tag(3)
	private String icon;
	
	@Tag(4)
	private int color;
	
	@Tag(5)
	private Vector2 vector2;
	
	@Tag(6)
	private String name;
	
	@Tag(7)
	private int buildIndex;
	
	@Tag(8)
	private int campType;
	
	@Tag(9)
	private List<PeiJian> peijians = new ArrayList<>();
	
	
	public RadarSoldier(int soldierId, int num, String icon, int color,
			Vector2 vector2, String name, int buildIndex, int campType,
			List<PeiJian> peijians) {
		super();
		this.soldierId = soldierId;
		this.num = num;
		this.icon = icon;
		this.color = color;
		this.vector2 = vector2;
		this.name = name;
		this.buildIndex = buildIndex;
		this.campType = campType;
		this.peijians = peijians;
	}
	

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

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Vector2 getVector2() {
		return vector2;
	}

	public void setVector2(Vector2 vector2) {
		this.vector2 = vector2;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
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
