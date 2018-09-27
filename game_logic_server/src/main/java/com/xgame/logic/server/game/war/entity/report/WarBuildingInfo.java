package com.xgame.logic.server.game.war.entity.report;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.xgame.logic.server.game.country.entity.CountryBuild;




/**
 * 建筑报告
 * @author jacky.jiang
 *
 */
public class WarBuildingInfo {
	
	
	/**
	 * 建筑id
	 * {@linkplain CountryBuild}
	 */
	private int uid;
	
	/**
	 * 资源类型
	 */
	private int resourceType;

	/**
	 * 当前血量
	 */
	private int lostHp;
	
	/**
	 * 玩家攻击伤害对应每个玩家
	 * <ul>
	 * 		<li><playeId</li>
	 * 		<li><damage</li>
	 * </ul>
	 */
	private Map<String, Integer> playerDamagerMap = new ConcurrentHashMap<>();
	
	public int getLostHp() {
		return lostHp;
	}

	public void setLostHp(int lostHp) {
		this.lostHp = lostHp;
	}

	public Map<String, Integer> getPlayerDamagerMap() {
		return playerDamagerMap;
	}

	public void setPlayerDamagerMap(Map<String, Integer> playerDamagerMap) {
		this.playerDamagerMap = playerDamagerMap;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}
	
	public int getResourceType() {
		return resourceType;
	}

	public void setResourceType(int resourceType) {
		this.resourceType = resourceType;
	}

	/**
	 * 获取总血量
	 * @return
	 */
	public int getTotalHp() {
		int totalHp = 0;
		if(playerDamagerMap != null && !playerDamagerMap.isEmpty()) {
			for(Integer hp : playerDamagerMap.values()) {
				totalHp += hp;
			}
			totalHp += lostHp;
		}
		return totalHp;
	}
	
	/**
	 * 获取玩家伤害
	 * @param playerId
	 * @return
	 */
	public int getPlayerDamange(long playerId) {
		Integer damager = playerDamagerMap.get(String.valueOf(playerId));
		if(damager != null) {
			return damager;
		}
		return 0;
	}
	
}
