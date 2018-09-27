package com.xgame.config.item;

import java.util.ArrayList;
import java.util.List;

/**
 * 士兵宝箱
 * @author jacky.jiang
 *
 */
public class ArmyBoxControl {

	/**
	 * 数量
	 */
	private int count;

	/**
	 * 建筑id
	 */
	private int buildId;
	
	/**
	 * 掉落列表
	 */
	private List<ItemBox> dropList = new ArrayList<>();

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getBuildId() {
		return buildId;
	}

	public void setBuildId(int buildId) {
		this.buildId = buildId;
	}

	public List<ItemBox> getDropList() {
		return dropList;
	}

	public void setDropList(List<ItemBox> dropList) {
		this.dropList = dropList;
	}
}
