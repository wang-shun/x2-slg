package com.xgame.config.common;
/**
 *
 *2016-8-05  14:51:16
 *@author ye.yuan
 *
 */
public class CampV2 {
	
	private int globalId;
	
	private int level;
	
	/**
	 * T-n 1-t1坦克 2-t2坦克 
	 */
	private int t;
	
	public CampV2(int globalId, int level,int t) {
		super();
		this.globalId = globalId;
		this.level = level;
		this.t=t;
	}

	public int getGlobalId() {
		return globalId;
	}

	public void setGlobalId(int globalId) {
		this.globalId = globalId;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getT() {
		return t;
	}

	public void setT(int t) {
		this.t = t;
	}
	
}
