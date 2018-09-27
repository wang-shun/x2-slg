package com.xgame.logic.server.game.allianceext.entity;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;

/**
 * 联盟活跃任务信息
 * @author dingpeng.qu
 *
 */
public class AllianceActivityQuest implements JBaseTransform{
	
	/**
	 * 活跃任务模版ID
	 */
	private int questId;
	
	/**
	 * 完成次数
	 */
	private int count;
	
	public int getQuestId() {
		return questId;
	}

	public void setQuestId(int questId) {
		this.questId = questId;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		jbaseData.put("questId", questId);
		jbaseData.put("count", count);
		return jbaseData;
	}

	@Override
	public void fromJBaseData(JBaseData jBaseData) {
		this.questId = jBaseData.getInt("questId", 0);
		this.count = jBaseData.getInt("count", 0);
	}

}
