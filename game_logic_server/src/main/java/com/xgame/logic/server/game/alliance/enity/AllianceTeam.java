package com.xgame.logic.server.game.alliance.enity;

import java.util.Set;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;
import com.xgame.logic.server.core.utils.JsonUtil;
import com.xgame.logic.server.core.utils.collection.ConcurrentHashSet;

/**
 * 战队信息
 * @author jacky.jiang
 *
 */
public class AllianceTeam implements JBaseTransform{
	
	/**
	 * 战队id
	 */
	private String teamId;
	
	/**
	 * 联盟id
	 */
	private long allianceId;
	
	/**
	 * 战队名称
	 */
	private String teamName;
	
	/**
	 * 战队图标
	 */
	private String icon;
	
	/**
	 * 战队队长
	 */
	private long teamLeaderId;
	
	/**
	 * 战队成员
	 */
	private ConcurrentHashSet<Long> teamMemberIds = new ConcurrentHashSet<>();
	
	/**
	 * 编辑时间
	 */
	private long editTime;

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public long getTeamLeaderId() {
		return teamLeaderId;
	}

	public void setTeamLeaderId(long teamLeaderId) {
		this.teamLeaderId = teamLeaderId;
	}

	public ConcurrentHashSet<Long> getTeamMemberIds() {
		return teamMemberIds;
	}

	public void setTeamMemberIds(ConcurrentHashSet<Long> teamMemberIds) {
		this.teamMemberIds = teamMemberIds;
	}
	
	public int getTeamNum() {
		return teamMemberIds.size();
	}

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public long getAllianceId() {
		return allianceId;
	}

	public void setAllianceId(long allianceId) {
		this.allianceId = allianceId;
	}
	
	public long getEditTime() {
		return editTime;
	}

	public void setEditTime(long editTime) {
		this.editTime = editTime;
	}
	
	public Set<Long> getTeamMember() {
		ConcurrentHashSet<Long> member = new ConcurrentHashSet<>(); 
		member.addAll(teamMemberIds);
		member.add(this.teamLeaderId);
		return member;
	}

	public void dismissAlliance(long playerId) {
		teamMemberIds.remove(playerId);
		if(teamLeaderId == playerId) {
			teamLeaderId = 0;
		}
	}
	
	public static String factoryTeamId(long allianceId, String i) {
		return String.format("%s#%s", allianceId, i);
	}

	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		jbaseData.put("teamId", teamId);
		jbaseData.put("allianceId", allianceId);
		jbaseData.put("teamName", teamName);
		jbaseData.put("icon", icon);
		jbaseData.put("teamLeaderId", teamLeaderId);
		jbaseData.put("editTime", editTime);
		jbaseData.put("teamMemberIds", JsonUtil.toJSON(teamMemberIds));
		return jbaseData;
	}

	@SuppressWarnings("unchecked")
	public void fromJBaseData(JBaseData jBaseData) {
		this.teamId = jBaseData.getString("teamId", "");
		this.allianceId = jBaseData.getLong("allianceId", 0);
		this.teamName = jBaseData.getString("teamName", "");
		this.icon = jBaseData.getString("icon", "");
		this.teamLeaderId = jBaseData.getLong("teamLeaderId", 0);
		this.editTime = jBaseData.getLong("editTime", 0);
		
		String teamMemberStr = jBaseData.getString("teamMemberIds","");
		this.teamMemberIds = JsonUtil.fromJSON(teamMemberStr, ConcurrentHashSet.class);
	}
}
