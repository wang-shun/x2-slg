package com.xgame.logic.server.game.alliance.enity;

import com.xgame.logic.server.core.utils.sort.MapRankable;


/**
 * 联盟玩家排行
 * @author jacky.jiang
 *
 */
public class AlliancePlayerFightPowerRank extends MapRankable {
	
	/**属主id*/
	public static final String LEVEL = "level";
	/**排行名次*/
	public static final String FIGHTPOWER = "fightPower";
	
	public static AlliancePlayerFightPowerRank valueOf(long id, long fightPower) {
		AlliancePlayerFightPowerRank allianceRankable = new AlliancePlayerFightPowerRank();
		allianceRankable.setOwnerId(id);
		allianceRankable.setFightPower(fightPower);
		return allianceRankable;
	}
	
	public int getLevel() {
		return this.getInt(LEVEL);
	}

	public void setLevel(int level) {
		this.setValue(LEVEL, level);
	}

	public long getFightPower() {
		return this.getLong(FIGHTPOWER);
	}

	public void setFightPower(long fightPower) {
		this.setValue(FIGHTPOWER, fightPower);
	}

}
