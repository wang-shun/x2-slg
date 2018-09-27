package com.xgame.logic.server.game.alliance.enity;

import com.xgame.logic.server.core.utils.sort.MapRankable;


/**
 * 
 * @author jacky.jiang
 *
 */
public class AllianceRankable extends MapRankable {
	
	/**属主id*/
	public static final String LEVEL = "level";
	/**排行名次*/
	public static final String FIGHTPOWER = "fightPower";
	
	
	public static AllianceRankable valueOf(long id, int level, long fightPower) {
		AllianceRankable allianceRankable = new AllianceRankable();
		allianceRankable.setOwnerId(id);
		allianceRankable.setLevel(level);
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
