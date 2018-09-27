package com.xgame.logic.server.game.alliance.enity;

/**
 * 推荐玩家的排名
 * @author jacky.jiang
 *
 */
public class RecommandPlayerRankable extends AllianceRankable {

	/** 行政大楼等级 */
	public static final String LEVEL = "level";
	
	/** 排行名次 */
	public static final String FIGHTPOWER = "fightPower";

	/** vip等级  */
	public static final String VIP_LEVEL = "vipLevel";
	
	/** 最近一次登录时间 */
	public static final String LOGIN_TIME = "loginTime";
	
	public static RecommandPlayerRankable valueOf(long id, int level, int vipLevel, long fightPower) {
		RecommandPlayerRankable allianceRankable = new RecommandPlayerRankable();
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
	
	public int getVipLevel() {
		return this.getInt(VIP_LEVEL);
	}
	
	public void setVipLevel(int vipLevel) {
		this.setValue(VIP_LEVEL, vipLevel);
	}
	
	public long getLoginTime() {
		return this.getLong(LOGIN_TIME);
	}
	
	public void setLoginTime(long time) {
		this.setValue(LOGIN_TIME, time);
	}
	
}
