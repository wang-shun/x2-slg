package com.xgame.logic.server.game.player.entity.eventmodel;

import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.GameLogEventObject;
import com.xgame.logic.server.game.player.entity.Player;


/**
 * 攻击玩家胜利
 * @author zehong.he
 *
 */
public class AttackBaseWinEventObject extends GameLogEventObject {
	
	public AttackBaseWinEventObject(Player player) {
		super(player,EventTypeConst.ATTACK_BASE_WIN);
	}
}
