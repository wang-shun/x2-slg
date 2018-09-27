package com.xgame.logic.server.game.alliance.enity;

import com.xgame.logic.server.core.utils.sort.MapRankableComparator;
import com.xgame.logic.server.core.utils.sort.SmartRank;

/**
 * 联盟成员排行
 * @author jacky.jiang
 *
 */
public class AllianceMemberRank {
	
	/**
	 * 战力排行榜
	 */
	private SmartRank fightPowerRank = new SmartRank(new MapRankableComparator("fightPower DESC, ownerId ASC"), -1);
	
	/**
	 * 捐献排行榜
	 */
	private SmartRank donateRank = new SmartRank(new MapRankableComparator("donate DESC, ownerId ASC") , -1);
	
	/**
	 * 杀敌排行
	 */
	private SmartRank kiledRank = new SmartRank(new MapRankableComparator("kill DESC, ownerId ASC") , -1);

	public SmartRank getFightPowerRank() {
		return fightPowerRank;
	}

	public void setFightPowerRank(SmartRank fightPowerRank) {
		this.fightPowerRank = fightPowerRank;
	}

	public SmartRank getDonateRank() {
		return donateRank;
	}

	public void setDonateRank(SmartRank donateRank) {
		this.donateRank = donateRank;
	}

	public SmartRank getKiledRank() {
		return kiledRank;
	}

	public void setKiledRank(SmartRank kiledRank) {
		this.kiledRank = kiledRank;
	}

}
