package com.xgame.logic.server.game.player.entity;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;
import com.xgame.logic.server.core.utils.JsonUtil;
import com.xgame.logic.server.core.utils.collection.ConcurrentHashSet;


/**
 * 玩家联盟战队信息
 * @author jacky.jiang
 *
 */
public class PlayerAllianceBattleTeam implements Serializable,JBaseTransform {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6998325725650897465L;
	/**
	 * 联盟战队id列表
	 */
	private ConcurrentHashSet<String> battleTeamIdList = new ConcurrentHashSet<>();

	public ConcurrentHashSet<String> getBattleTeamIdList() {
		return battleTeamIdList;
	}

	public void setBattleTeamIdList(ConcurrentHashSet<String> battleTeamIdList) {
		this.battleTeamIdList = battleTeamIdList;
	}
	
	public void addBattleTeamId(String teamId) {
		battleTeamIdList.add(teamId);
	}
	
	public void removeBattleTeamId(String teamId) {
		battleTeamIdList.remove(teamId);
	}

	@Override
	public JBaseData toJBaseData() {
		JBaseData jBaseData = new JBaseData();
		jBaseData.put("battleTeamIdList", JsonUtil.toJSON(battleTeamIdList));
		return jBaseData;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void fromJBaseData(JBaseData jBaseData) {
		String battleTeamIdJSonList = jBaseData.getString("battleTeamIdList", "");
		if(!StringUtils.isBlank(battleTeamIdJSonList)) {
			this.battleTeamIdList = new ConcurrentHashSet<>();
			this.battleTeamIdList.addAll(JsonUtil.fromJSON(battleTeamIdJSonList, ConcurrentHashSet.class));
		}
	}
}
