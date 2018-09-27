package com.xgame.logic.server.game.bag.entity;

import java.util.ArrayList;
import java.util.List;

import com.xgame.common.ItemConf;

/**
 * 奖励上下问题
 * @author jacky.jiang
 *
 */
public class RewardContext {
	
	/**
	 *  奖励是否成功
	 */
	private int errorCode;
	
	/**
	 * 道具列表
	 */
	private List<ItemConf> itemConfList = new ArrayList<>();
	
	/**
	 * 装备列表
	 */
	private List<ItemConf> equipmentList = new ArrayList<>();

	/**
	 * 配件列表
	 */
	private List<ItemConf> peijianLsit = new ArrayList<ItemConf>();
	
	public boolean isOk() {
		return errorCode >= 0;
	}

	public List<ItemConf> getItemConfList() {
		return itemConfList;
	}

	public void setItemConfList(List<ItemConf> itemConfList) {
		this.itemConfList = itemConfList;
	}
	
	public List<ItemConf> getEquipmentList() {
		return equipmentList;
	}

	public void setEquipmentList(List<ItemConf> equipmentList) {
		this.equipmentList = equipmentList;
	}

	public List<ItemConf> getPeijianLsit() {
		return peijianLsit;
	}

	public void setPeijianLsit(List<ItemConf> peijianLsit) {
		this.peijianLsit = peijianLsit;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

}
