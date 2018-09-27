package com.xgame.logic.server.game.player.entity.eventmodel;

import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.GameLogEventObject;
import com.xgame.logic.server.game.player.entity.Player;


/**
 * 统计玩家累计在线时间
 * @author zehong.he
 *
 */
public class PlayerOnlineTimeEventObject extends GameLogEventObject {

	/**
	 * 当前累计在线时间
	 */
	private long onlineTime;
	
	public PlayerOnlineTimeEventObject(Player player,long onlineTime) {
		super(player, EventTypeConst.PLAYER_ONLIE_TIME);
		this.onlineTime = onlineTime;
	}

	/**
	 * @return the onlineTime
	 */
	public long getOnlineTime() {
		return onlineTime;
	}

	/**
	 * @param onlineTime the onlineTime to set
	 */
	public void setOnlineTime(long onlineTime) {
		this.onlineTime = onlineTime;
	}
	
}
