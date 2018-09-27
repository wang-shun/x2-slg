package com.xgame.logic.server.game.alliance.enity;

import com.xgame.logic.server.core.utils.sort.MapRankable;


/**
 * 联盟玩家排行
 * @author jacky.jiang
 *
 */
public class AlliancePlayerDonateRank extends MapRankable {
	
	/**属主id */
	public static final String LEVEL = "level";
	/**排行名次 */
	public static final String TODAY_DONATE = "todayDonate";
	/**历史排行 */
	public static final String HISTORY_DONATE = "historyDonate";
	/**周贡献排行 */
	public static final String WEEK_DONATE = "weekDonate";
	
	public static AlliancePlayerDonateRank valueOf(long id, int todayDonate, int historyDonate) {
		AlliancePlayerDonateRank allianceRankable = new AlliancePlayerDonateRank();
		allianceRankable.setOwnerId(id);
//		allianceRankable.setFightPower(fightPower);
		return allianceRankable;
	}
	
	public int getLevel() {
		return this.getInt(LEVEL);
	}

	public void setLevel(int level) {
		this.setValue(LEVEL, level);
	}

//	public long getFightPower() {
//		return this.getLong(FIGHTPOWER);
//	}
//
//	public void setFightPower(long fightPower) {
//		this.setValue(FIGHTPOWER, fightPower);
//	}

}
