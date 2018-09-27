package com.xgame.logic.server.game.player.entity.eventmodel;

import com.xgame.logic.server.core.gamelog.event.GameLogEventObject;
import com.xgame.logic.server.game.country.bean.Vector2Bean;
import com.xgame.logic.server.game.player.entity.Player;


/**
 * 创建角色时间
 * @author jacky.jiang
 *
 */
public class CreateRoleEventObject extends GameLogEventObject {
	
	
	/**
	 * 指挥官名称
	 */
	private String commanderName;
	
	/**
	 * 坐标信息
	 */
	private Vector2Bean vector2Bean;
	
	
	public CreateRoleEventObject(Player player, int type, String commmanderName, Vector2Bean vector2Bean) {
		super(player, type);
		this.commanderName = commmanderName;
		this.vector2Bean = vector2Bean;
		this.type  = type;
	}


	public String getCommanderName() {
		return commanderName;
	}

	public Vector2Bean getVector2Bean() {
		return vector2Bean;
	}


	@Override
	public String toString() {
		return "CreateRoleEventObject [commanderName=" + commanderName
				+ ", vector2Bean=" + vector2Bean + ", type=" + type + "]";
	}
	
	
}
