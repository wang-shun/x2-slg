package com.xgame.logic.server.game.allianceext.entity;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.xgame.common.ItemConf;
import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;
import com.xgame.logic.server.core.utils.JsonUtil;

public class AwardInfo implements JBaseTransform{
	
	/**
	 * 宝箱id
	 */
	private long id;
	
	/**
	 * 宝箱点数
	 */
	private int boxNum;
	
	/**
	 * 宝箱显示标识 0显示 1不显示
	 */
	private int showFlag;
	
	/**
	 * 奖励道具列表
	 */
	private List<ItemConf> itemConfs = new ArrayList<>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getBoxNum() {
		return boxNum;
	}

	public void setBoxNum(int boxNum) {
		this.boxNum = boxNum;
	}

	public List<ItemConf> getItemConfs() {
		return itemConfs;
	}

	public void setItemConfs(List<ItemConf> itemConfs) {
		this.itemConfs = itemConfs;
	}

	public int getShowFlag() {
		return showFlag;
	}

	public void setShowFlag(int showFlag) {
		this.showFlag = showFlag;
	}

	@Override
	public JBaseData toJBaseData() {
		JBaseData jBaseData = new JBaseData();
		jBaseData.put("id", id);
		jBaseData.put("boxNum", boxNum);
		jBaseData.put("showFlag", showFlag);
		jBaseData.put("itemConfs", JsonUtil.toJSON(itemConfs));
		return jBaseData;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void fromJBaseData(JBaseData jBaseData) {
		this.id = jBaseData.getLong("id", 0);
		this.boxNum = jBaseData.getInt("boxNum", 0);
		this.showFlag = jBaseData.getInt("showFlag", 0);
		String itemConfsString = jBaseData.getString("itemConfs", "");
		if(!StringUtils.isEmpty(itemConfsString)) {
			this.itemConfs = JsonUtil.fromJSON(itemConfsString, List.class);
		}
	}

}
