package com.xgame.logic.server.game.player.entity.eventmodel;

import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.GameLogEventObject;
import com.xgame.logic.server.game.player.entity.Player;

/**
 * 攻占他服行政中心
 * @author zehong.he
 *
 */
public class CaptureOtherServerEventObject extends GameLogEventObject {

	
	public CaptureOtherServerEventObject(Player player) {
		super(player, EventTypeConst.CAPTURE_OTHER_SERVER);
	}

}

