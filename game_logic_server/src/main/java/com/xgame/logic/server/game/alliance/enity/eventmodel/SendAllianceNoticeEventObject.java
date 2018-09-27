package com.xgame.logic.server.game.alliance.enity.eventmodel;

import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.GameLogEventObject;
import com.xgame.logic.server.game.player.entity.Player;

/**
 * 发布军团招募
 * @author zehong.he
 *
 */
public class SendAllianceNoticeEventObject extends GameLogEventObject {

	public SendAllianceNoticeEventObject(Player player) {
		super(player, EventTypeConst.ALLIANCE_PUBLISH_RECRUIT);
		
	}
}
