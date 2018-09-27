package com.xgame.config.item;

import java.util.List;

import com.google.common.collect.Lists;

/**
 *
 *2016-7-28  11:08:02
 *@author ye.yuan
 *
 */
public class RandItem {

	private int itemId;
	private int min = 1;
	private int max = 1;
	private int rate;
	
	private List<Integer> rateList;
	
	public RandItem(String itemId, String min, String max, String rate) {
		super();
		this.itemId = Integer.valueOf(itemId);
		this.min = Integer.valueOf(min);
		this.max = Integer.valueOf(max);
		this.rate = Integer.valueOf(rate);
		
		rateList = Lists.newArrayList();
		for(int i=0; i < this.rate; i++) {
			rateList.add(this.itemId);
		}
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public List<Integer> getRateList() {
		return rateList;
	}

	public void setRateList(List<Integer> rateList) {
		this.rateList = rateList;
	}
	
}
