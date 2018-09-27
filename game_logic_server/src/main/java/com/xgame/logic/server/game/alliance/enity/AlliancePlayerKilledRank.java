package com.xgame.logic.server.game.alliance.enity;

import com.xgame.logic.server.core.utils.sort.MapRankable;


/**
 * 联盟玩家排行
 * @author jacky.jiang
 *
 */
public class AlliancePlayerKilledRank extends MapRankable {
	
	/**属主id */
	public static final String LEVEL = "level";
	/**排行名次 */
	public static final String TODAY_DONATE = "todayDonate";
	/**历史排行 */
	public static final String HISTORY_DONATE = "historyDonate";
	/**周贡献排行 */
	public static final String WEEK_DONATE = "weekDonate";
	
	public static AlliancePlayerKilledRank valueOf(long id, int todayDonate, int historyDonate) {
		AlliancePlayerKilledRank allianceRankable = new AlliancePlayerKilledRank();
		allianceRankable.setOwnerId(id);
		return allianceRankable;
	}
	
	public int getLevel() {
		return this.getInt(LEVEL);
	}

	public void setLevel(int level) {
		this.setValue(LEVEL, level);
	}

}
