package com.xgame.logic.server.game.player.entity.eventmodel;

import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.GameLogEventObject;
import com.xgame.logic.server.game.player.entity.Player;


/**
 * 头目入侵
 * @author zehong.he
 *
 */
public class LeaderInvasionEventObject extends GameLogEventObject {

	/**
	 * 1个人2军团
	 */
	private int invasionType;
	
	public LeaderInvasionEventObject(Player player,int invasionType) {
		super(player, EventTypeConst.LEADER_INVASION);
		this.invasionType = invasionType;
	}

	/**
	 * @return the invasionType
	 */
	public int getInvasionType() {
		return invasionType;
	}
}
