package com.xgame.config.item;

import java.util.ArrayList;
import java.util.List;

public class PeiJianBoxControl {
	/**
	 * 道具ID
	 */
	public int itemId;
	/**
	 * 最小数量
	 */
	public int itemMin;
	/**
	 * 最大数量
	 */
	public int itemMax;
	/**
	 * 品质
	 */
	public int level;
	/**
	 * 配件组ID
	 */
	public List<Integer> peiJianList = new ArrayList<Integer>();
	
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public int getItemMin() {
		return itemMin;
	}
	public void setItemMin(int itemMin) {
		this.itemMin = itemMin;
	}
	public int getItemMax() {
		return itemMax;
	}
	public void setItemMax(int itemMax) {
		this.itemMax = itemMax;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public List<Integer> getPeiJianList() {
		return peiJianList;
	}
	public void setPeiJianList(List<Integer> peiJianList) {
		this.peiJianList = peiJianList;
	}
	
	
}
