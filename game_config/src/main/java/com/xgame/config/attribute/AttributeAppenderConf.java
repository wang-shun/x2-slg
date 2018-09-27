package com.xgame.config.attribute;



/**
 *
 *2016-7-25  10:37:00
 *@author ye.yuan
 *
 */
public class AttributeAppenderConf{

	/**
	 * 属性id
	 */
	private int id;
	
	/**
	 * 目标节点
	 */
	private int[] carriers;
	
	/**
	 * 没等级的值
	 */
	private double[] levelAttributes;
	
	public double get(int level){
		if(levelAttributes!=null&&level>=0&&level<levelAttributes.length){
			return levelAttributes[level];
		}
		return 0;
	}
	
	/**
	 * 获取第一个 全等级一个值的时候使用
	 * @return
	 */
	public int getDefaultCarrier() {
		if(carriers!=null&&0<carriers.length) {
			return carriers[0];
		}
		return -1;
	}
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int[] getCarriers() {
		return carriers;
	}

	public void setCarriers(int[] carriers) {
		this.carriers = carriers;
	}

	public double[] getLevelAttributes() {
		return levelAttributes;
	}

	public void setLevelAttributes(double[] levelAttributes) {
		this.levelAttributes = levelAttributes;
	}
	
}
