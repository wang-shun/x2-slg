package com.xgame.config.item;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *2016-7-28  11:48:30
 *@author ye.yuan
 *
 */
public class ItemBoxControl {
	
	/**
	 * 随机次数
	 */
	private int count;
	
	/**
	 * 本次宝箱配置物品
	 */
	private List<ItemBox> itemBoxs = new ArrayList<>();
	
	
	public ItemBoxControl() {
		
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<ItemBox> getItemBoxs() {
		return itemBoxs;
	}

	public void setItemBoxs(List<ItemBox> itemBoxs) {
		this.itemBoxs = itemBoxs;
	}
}
