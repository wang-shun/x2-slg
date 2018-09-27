package com.xgame.logic.server.game.alliance.enity;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;

/**
 * 联盟加入申请
 * @author jacky.jiang
 *
 */
public class AllianceApply implements JBaseTransform {
	
	/**
	 * 玩家id
	 */
	private long playerId;
	
	/**
	 * 留言
	 */
	private String applyMessage;
	
	/**
	 * 申请时间
	 */
	private long applyTime;

	public String getApplyMessage() {
		return applyMessage;
	}

	public void setApplyMessage(String applyMessage) {
		this.applyMessage = applyMessage;
	}

	public long getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(long applyTime) {
		this.applyTime = applyTime;
	}

	public long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}

	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		jbaseData.put("playerId", playerId);
		jbaseData.put("applyMessage", applyMessage);
		jbaseData.put("applyTime", applyTime);
		return jbaseData;
	}

	public void fromJBaseData(JBaseData jBaseData) {
		this.playerId = jBaseData.getLong("playerId", 0);
		this.applyMessage = jBaseData.getString("applyMessage", "");
		this.applyTime = jBaseData.getLong("applyTime", 0);
	}

}
