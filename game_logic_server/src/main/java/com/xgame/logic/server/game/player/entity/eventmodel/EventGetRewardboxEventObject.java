package com.xgame.logic.server.game.player.entity.eventmodel;

import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.GameLogEventObject;
import com.xgame.logic.server.game.player.entity.Player;


/**
 * 个人事件中获得宝箱
 * @author zehong.he
 *
 */
public class EventGetRewardboxEventObject extends GameLogEventObject {
	
	public EventGetRewardboxEventObject(Player player) {
		super(player,EventTypeConst.EVENT_GET_REWARDBOX);
	}
}
