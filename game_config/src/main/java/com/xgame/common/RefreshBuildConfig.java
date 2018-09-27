package com.xgame.common;



/**
 *刷新策略配置
 *2016-7-13  20:27:56
 *@author ye.yuan
 *
 */
 
public class RefreshBuildConfig {
	
	/**
	 * 这个id指的是 CountryMatrix的id
	 */
	private int id;
	
	
	/**
	 * 随机数组 里面是建筑物sid
	 */
	private int [][] arrys;


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int[][] getArrys() {
		return arrys;
	}

	public void setArrys(int[][] arrys) {
		this.arrys = arrys;
	}

	
}
