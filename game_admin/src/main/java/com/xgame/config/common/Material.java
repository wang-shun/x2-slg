package com.xgame.config.common;


/**
 *材料配置类型
 *2016-7-18  11:52:13
 *@author ye.yuan
 *
 */
public class Material {
	
	private int type;
	
	private int num;

	public Material(int type, int num) {
		super();
		this.type = type;
		this.num = num;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

}
