package com.xgame.logic.server.game.world.entity.model;

import java.io.Serializable;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;

/**
 * 资源信息
 * @author jacky.jiang
 *
 */
public class TerrainConfigModel implements Serializable, JBaseTransform {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *  军事管理区
	 */
	private int isFightZone;

	/**
	 *  地表 1.平原2.丘陵 3.山地4.丛林5.沼泽
	 */
	private int terrain;
	
	/**
	 * 战败是否传送
	 */
	private int battleFailRandomTransfer;

	public int getIsFightZone() {
		return isFightZone;
	}

	public void setIsFightZone(int isFightZone) {
		this.isFightZone = isFightZone;
	}

	public int getTerrain() {
		return terrain;
	}

	public void setTerrain(int terrain) {
		this.terrain = terrain;
	}

	public int getBattleFailRandomTransfer() {
		return battleFailRandomTransfer;
	}

	public void setBattleFailRandomTransfer(int battleFailRandomTransfer) {
		this.battleFailRandomTransfer = battleFailRandomTransfer;
	}
	
	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		jbaseData.put("isFightZone", isFightZone);
		jbaseData.put("terrain", terrain);
		jbaseData.put("battleFailRandomTransfer", battleFailRandomTransfer);
		return jbaseData;
	}

	public void fromJBaseData(JBaseData jBaseData) {
		this.isFightZone = jBaseData.getInt("isFightZone", 0);
		this.terrain = jBaseData.getInt("terrain", 0);
		this.battleFailRandomTransfer = jBaseData.getInt("battleFailRandomTransfer", 0);
	}
}
