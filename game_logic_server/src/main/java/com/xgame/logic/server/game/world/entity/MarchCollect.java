package com.xgame.logic.server.game.world.entity;

import io.protostuff.Tag;

import com.xgame.logic.server.game.constant.CurrencyEnum;


/**
 * 行军采集
 * @author jacky.jiang
 *
 */
public class MarchCollect {
	
	/**
	 * 采集数量
	 */
	@Tag(1)
	private int collectNum;
	
	/**
	 * 采集资源类型
	 */
	@Tag(2)
	private CurrencyEnum resourceType;
	
	/**
	 * 
	 */
	@Tag(3)
	private int level;

	public int getCollectNum() {
		return collectNum;
	}

	public void setCollectNum(int collectNum) {
		this.collectNum = collectNum;
	}
	
	public CurrencyEnum getResourceType() {
		return resourceType;
	}

	public void setResourceType(CurrencyEnum resourceType) {
		this.resourceType = resourceType;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
}
