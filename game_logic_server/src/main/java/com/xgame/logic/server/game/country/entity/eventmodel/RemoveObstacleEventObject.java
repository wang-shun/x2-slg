package com.xgame.logic.server.game.country.entity.eventmodel;

import com.xgame.logic.server.core.gamelog.event.GameLogEventObject;
import com.xgame.logic.server.game.country.bean.Vector2Bean;
import com.xgame.logic.server.game.player.entity.Player;


/**
 * 移除障碍物
 * @author jacky.jiang
 *
 */
public class RemoveObstacleEventObject extends GameLogEventObject {

	/**
	 * 障碍物坐标
	 */
	private Vector2Bean position;
	
	/**
	 * 障碍物等级
	 */
	private int level;
	
	public RemoveObstacleEventObject(Player player, Integer type, Vector2Bean position) {
		super(player, type);
		
		this.position = position;
	}

	public Vector2Bean getPosition() {
		return position;
	}

	public void setPosition(Vector2Bean position) {
		this.position = position;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public String toString() {
		return "RemoveObstacleEventObject [position=" + position + ", level="
				+ level + "]";
	}
	
}
