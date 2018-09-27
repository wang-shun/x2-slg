package com.xgame.logic.server.game.player.entity.eventmodel;

import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.GameLogEventObject;
import com.xgame.logic.server.game.player.entity.Player;


/**
 * 攻击玩家
 * @author zehong.he
 *
 */
public class AttackPlayerEventObject extends GameLogEventObject {
	
	public AttackPlayerEventObject(Player player) {
		super(player,EventTypeConst.ATTACK_PLAYER);
	}
}
