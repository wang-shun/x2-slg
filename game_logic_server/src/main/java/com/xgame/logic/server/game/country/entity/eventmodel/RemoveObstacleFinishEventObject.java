package com.xgame.logic.server.game.country.entity.eventmodel;

import java.util.HashMap;
import java.util.Map;

import com.xgame.logic.server.core.gamelog.event.GameLogEventObject;
import com.xgame.logic.server.game.country.bean.Vector2Bean;
import com.xgame.logic.server.game.player.entity.Player;



/**
 * 移除障碍物完成
 * @author jacky.jiang
 *
 */
public class RemoveObstacleFinishEventObject extends GameLogEventObject {
	
	private Map<Integer, Integer> rewardMap = new HashMap<>();
	
	private int level;
	
	private Vector2Bean position;
	
	public RemoveObstacleFinishEventObject(Player player, Integer type, Vector2Bean position, Map<Integer, Integer> reward) {
		super(player, type);
		this.position = position;
		this.rewardMap = reward;
	}

	public Vector2Bean getPosition() {
		return position;
	}

	public void setPosition(Vector2Bean position) {
		this.position = position;
	}

	public Map<Integer, Integer> getRewardMap() {
		return rewardMap;
	}

	public void setRewardMap(Map<Integer, Integer> rewardMap) {
		this.rewardMap = rewardMap;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public String toString() {
		return "RemoveObstacleFinishEventObject [rewardMap=" + rewardMap
				+ ", level=" + level + ", position=" + position + "]";
	}
	
}
