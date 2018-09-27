package com.xgame.logic.server.game.world.entity.sprite;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;

import io.protostuff.Tag;

/**
 * 采集记录
 * @author jacky.jiang
 *
 */
public class ExplorerRecord implements JBaseTransform {
	
	/**
	 * 玩家名称
	 */
	@Tag(1)
	private String playerName;
	
	/**
	 * 剩余名称
	 */
	@Tag(2)
	private int plusNum;
	
	/**
	 * 采集时间秒
	 */
	@Tag(3)
	private long explorerTime;

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public int getPlusNum() {
		return plusNum;
	}

	public void setPlusNum(int plusNum) {
		this.plusNum = plusNum;
	}

	public long getExplorerTime() {
		return explorerTime;
	}

	public void setExplorerTime(long explorerTime) {
		this.explorerTime = explorerTime;
	}

	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		jbaseData.put("playerName", playerName);
		jbaseData.put("plusNum", plusNum);
		jbaseData.put("explorerTime", explorerTime);
		return jbaseData;
	}

	public void fromJBaseData(JBaseData jBaseData) {
		this.playerName = jBaseData.getString("playerName","");
		this.plusNum = jBaseData.getInt("plusNum", 0);
		this.explorerTime = jBaseData.getLong("explorerTime", 0);
	}

}
