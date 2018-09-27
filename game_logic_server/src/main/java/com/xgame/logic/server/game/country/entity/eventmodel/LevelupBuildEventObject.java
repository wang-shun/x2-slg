package com.xgame.logic.server.game.country.entity.eventmodel;

import com.xgame.logic.server.core.gamelog.event.GameLogEventObject;
import com.xgame.logic.server.game.country.bean.Vector2Bean;
import com.xgame.logic.server.game.player.entity.Player;


/**
 * 升级建筑开始
 * @author jacky.jiang
 *
 */
public class LevelupBuildEventObject extends GameLogEventObject  {
	
	public LevelupBuildEventObject(Player player, int type, int buildTid, int costTime, Vector2Bean position, long fightPower) {
		super(player, type);
		this.buildTid = buildTid;
		this.costTime = costTime;
		this.position = position;
		this.fightPower = fightPower;
	}
	
	/**
	 * 建筑id
	 */
	private int buildTid;
	
	/**
	 * 创建建筑或者升级建筑需要话费的时间
	 */
	private int costTime;
	
	/**
	 * 家园坐标
	 */
	private Vector2Bean position;
	
	/**
	 * 战力
	 */
	private long fightPower;

	public int getBuildTid() {
		return buildTid;
	}

	public void setBuildTid(int buildTid) {
		this.buildTid = buildTid;
	}

	public int getCostTime() {
		return costTime;
	}

	public void setCostTime(int costTime) {
		this.costTime = costTime;
	}

	public Vector2Bean getPosition() {
		return position;
	}

	public void setPosition(Vector2Bean position) {
		this.position = position;
	}

	public long getFightPower() {
		return fightPower;
	}

	public void setFightPower(long fightPower) {
		this.fightPower = fightPower;
	}

	@Override
	public String toString() {
		return "LevelupBuildEventObject [buildTid=" + buildTid + ", costTime="
				+ costTime + ", position=" + position + ", fightPower="
				+ fightPower + "]";
	}
	
	
}
