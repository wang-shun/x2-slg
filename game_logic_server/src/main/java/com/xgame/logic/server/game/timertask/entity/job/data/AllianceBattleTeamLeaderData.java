package com.xgame.logic.server.game.timertask.entity.job.data;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;

/**
 * 
 * @author jacky.jiang
 *
 */
public class AllianceBattleTeamLeaderData implements JBaseTransform {
	
	/**
	 * 队伍id
	 */
	private String teamId;
	
	/**
	 * 新军队列id
	 */
	private long marchId;
	
	
	public AllianceBattleTeamLeaderData(){
		
	}

	public AllianceBattleTeamLeaderData(String teamId, long marchId) {
		super();
		this.teamId = teamId;
		this.marchId = marchId;
	}

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public long getMarchId() {
		return marchId;
	}

	public void setMarchId(long marchId) {
		this.marchId = marchId;
	}

	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		jbaseData.put("teamId", teamId);
		jbaseData.put("marchId", marchId);
		return jbaseData;
	}

	public void fromJBaseData(JBaseData jBaseData) {
		this.teamId = jBaseData.getString("teamId", "");
		this.marchId = jBaseData.getLong("marchId", 0);
	}
	
}
