package com.xgame.logic.server.game.allianceext.entity;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.xgame.common.ItemConf;
import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;
import com.xgame.logic.server.core.utils.JsonUtil;

public class ExerciseInfo implements JBaseTransform {
	
	/**
	 * 演习阶段
	 */
	private int id;
	
	/**
	 * 队列id
	 */
	private long taskId;
	
	/**
	 * 奖励道具列表
	 */
	private List<ItemConf> itemConfs;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<ItemConf> getItemConfs() {
		return itemConfs;
	}

	public void setItemConfs(List<ItemConf> itemConfs) {
		this.itemConfs = itemConfs;
	}

	public long getTaskId() {
		return taskId;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

	@Override
	public JBaseData toJBaseData() {
		JBaseData jBaseData = new JBaseData();
		jBaseData.put("id", id);
		jBaseData.put("taskId", taskId);
		jBaseData.put("itemConfs", JsonUtil.toJSON(itemConfs));
		return jBaseData;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void fromJBaseData(JBaseData jBaseData) {
		this.id = jBaseData.getInt("id", 0);
		this.taskId = jBaseData.getLong("taskId", 0);
		String itemConfsString = jBaseData.getString("itemConfs", "");
		if(!StringUtils.isEmpty(itemConfsString)) {
			this.itemConfs = JsonUtil.fromJSON(itemConfsString, List.class);
		}
	}

}
