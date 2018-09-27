package com.xgame.logic.server.game.alliance.enity;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;

import com.xgame.data.sprite.SpriteType;
import com.xgame.logic.server.core.db.cache.entity.AbstractEntity;
import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.core.utils.JsonUtil;
import com.xgame.logic.server.game.alliance.constant.AllianceBattleTeamType;
import com.xgame.logic.server.game.alliance.constant.AllianceTeamState;
import com.xgame.logic.server.game.soldier.entity.Soldier;
import com.xgame.logic.server.game.world.constant.MarchState;
import com.xgame.logic.server.game.world.entity.WorldMarch;

/**
 * 联盟中战斗队伍id
 * @author jacky.jiang
 *
 */
public class AllianceBattleTeam extends AbstractEntity<String> implements JBaseTransform {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8356186984906005964L;
	/**
	 * 队伍编号
	 */
	private String teamId;
	/**
	 *  联盟id
	 */
	private long allianceId;
	/**
	 * 类型
	 */
	private AllianceBattleTeamType teamType;
	/**
	 * 目标id
	 */
	private int targetId;
	/**
	 * 目标唯一id
	 */
	private String targetUid;
	/**
	 * 目标类型
	 */
	private SpriteType targetType;
	/**
	 * 战队队长id
	 */
	private long leaderId;
	
	/**
	 * 队伍集结时间
	 */
	private long waitTime;
	/**
	 * 创建时间
	 */
	private long createTime;
	/**
	 * 战队行军队列
	 */
	private long marchId;
	/**
	 * 联盟战队状态
	 */
	private AllianceTeamState allianceTeamState;
	/**
	 * 成员行军队列
	 */
	private Map<Long, Long> memberMarch = new ConcurrentHashMap<Long, Long>();
	
	public String getTeamId() {
		return teamId;
	}
	
	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}
	
	public long getAllianceId() {
		return allianceId;
	}
	
	public void setAllianceId(long allianceId) {
		this.allianceId = allianceId;
	}
	
	public int getTargetId() {
		return targetId;
	}
	
	public void setTargetId(int targetId) {
		this.targetId = targetId;
	}
	
	public String getTargetUid() {
		return targetUid;
	}
	
	public void setTargetUid(String targetUid) {
		this.targetUid = targetUid;
	}
	
	public long getLeaderId() {
		return leaderId;
	}

	public void setLeaderId(long leaderId) {
		this.leaderId = leaderId;
	}

	public long getMarchId() {
		return marchId;
	}

	public void setMarchId(long marchId) {
		this.marchId = marchId;
	}
	
	
	public SpriteType getTargetType() {
		return targetType;
	}

	public void setTargetType(SpriteType targetType) {
		this.targetType = targetType;
	}
	
	public AllianceBattleTeamType getTeamType() {
		return teamType;
	}

	public void setTeamType(AllianceBattleTeamType teamType) {
		this.teamType = teamType;
	}
	
	public long getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(long waitTime) {
		this.waitTime = waitTime;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	
	public AllianceTeamState getAllianceTeamState() {
		return allianceTeamState;
	}

	public void setAllianceTeamState(AllianceTeamState allianceTeamState) {
		this.allianceTeamState = allianceTeamState;
	}
	
	public Map<Long, Long> getMemberMarch() {
		return memberMarch;
	}

	public void setMemberMarch(Map<Long, Long> memberMarch) {
		this.memberMarch = memberMarch;
	}

	/**
	 * 获取组队士兵信息
	 * @param allianceBattleTeam
	 * @return
	 */
	public int getTeamSoldierNum() {
		int soldierNum = 0;
		Map<Long, Long> marchIdMap = this.memberMarch;
		if(marchIdMap != null) {
			for(Long id : marchIdMap.values()) {
				WorldMarch worldMarch = InjectorUtil.getInjector().dbCacheService.get(WorldMarch.class, id);
				if(worldMarch != null && worldMarch.getMarchState() == MarchState.OCCUPY) {
					List<Soldier> soldierList = worldMarch.querySoldierList();
					if(soldierList != null) {
						for(Soldier battleSoldier : soldierList) {
							soldierNum += battleSoldier.getNum();
						}
					}
				}
			}
		}
		return soldierNum;
	}
	
	/**
	 * 获取集结结束剩余时间
	 * @return
	 */
	public int getRemainTime() {
		return (int)((this.getWaitTime() - System.currentTimeMillis()) / 1000);
	}

	@Override
	public String getId() {
		return teamId;
	}

	@Override
	public void setId(String k) {
		this.teamId = k;
	}

	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		jbaseData.put("teamId", teamId);
		jbaseData.put("allianceId", allianceId);
		jbaseData.put("targetId", targetId);
		jbaseData.put("targetUid", targetUid);
		jbaseData.put("leaderId", leaderId);
		jbaseData.put("waitTime", waitTime);
		jbaseData.put("createTime", createTime);
		jbaseData.put("marchId", marchId);
		jbaseData.put("memberMarch", JsonUtil.toJSON(memberMarch));
		return jbaseData;
	}

	@SuppressWarnings("unchecked")
	public void fromJBaseData(JBaseData jBaseData) {
		this.teamId = jBaseData.getString("teamId","");
		this.allianceId = jBaseData.getLong("allianceId", 0);
		this.targetId = jBaseData.getInt("targetId", 0);
		this.targetUid = jBaseData.getString("targetUid","");
		this.leaderId = jBaseData.getLong("leaderId", 0);
		this.waitTime = jBaseData.getLong("waitTime", 0);
		this.createTime = jBaseData.getLong("createTime", 0);
		this.marchId = jBaseData.getLong("marchId", 0);
		String memberMarchStr = jBaseData.getString("memberMarch", "");
		if(!StringUtils.isEmpty(memberMarchStr)) {
			this.memberMarch = JsonUtil.fromJSON(memberMarchStr, Map.class);
		}
	}
}
