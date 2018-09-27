package com.xgame.logic.server.core.utils.sequance;

import org.springframework.stereotype.Component;

import com.xgame.gameconst.DBKey;
import com.xgame.logic.server.core.utils.InjectorUtil;

/**
 *  联盟帮助id生成
 * @author jacky.jiang
 *
 */
@Component
public class AllianceBattleTeamSequence {

	private long allianceBattleTeamid = 0;

	private short _allianceBattleTeamid = -1;

	private void initAllianceHelpSequence() {
		allianceBattleTeamid = (InjectorUtil.getInjector().redisClient.incr(DBKey.SEQUENCE_ALLIANCE_BATTLE_TEAM) + IdSequancePrefix.ALLIANCE_BATTLE_TEAM_ID) * 100000;
	}

	public void init() {
		initAllianceHelpSequence();
	}

	private short addShortValue(short value) {
		value++;
		if (value == Short.MAX_VALUE) {
			value = 0;
		}
		return value;
	}

	public long genAllianceBattleTeamId() {
		_allianceBattleTeamid = addShortValue(_allianceBattleTeamid);
		if (_allianceBattleTeamid == 0) {
			initAllianceHelpSequence();
		}
		return allianceBattleTeamid + _allianceBattleTeamid;
	}
}
