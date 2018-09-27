package com.xgame.logic.server.game.country.entity;

import java.io.Serializable;

import io.protostuff.Tag;

/**
 *格子阻挡信息 数据类
 *2016-7-18  20:06:31
 *@author ye.yuan
 *
 */
public class ObstructInfo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 阻挡类型    1 描边阻挡 2 建筑物占用阻挡  常量在  CountrySurface下
	 */
	@Tag(1)
	public int type;
	
	/**
	 * 阻挡的格子坐标
	 */
	@Tag(2)
	public String coordinate;
	
	
	public ObstructInfo(int type, String coordinate) {
		this.type = type;
		this.coordinate = coordinate;
	}


	public int getType() {
		return type;
	}


	public void setType(int type) {
		this.type = type;
	}


	public String getCoordinate() {
		return coordinate;
	}


	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}
	
}
