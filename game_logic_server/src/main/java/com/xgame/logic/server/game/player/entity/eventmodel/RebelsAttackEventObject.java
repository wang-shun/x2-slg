package com.xgame.logic.server.game.player.entity.eventmodel;

import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.GameLogEventObject;
import com.xgame.logic.server.game.player.entity.Player;

/**
 * 叛军攻打/击杀
 * @author zehong.he
 *
 */
public class RebelsAttackEventObject extends GameLogEventObject {

	/**
	 * 1攻打2击杀
	 */
	private int attackType;
	
	public RebelsAttackEventObject(Player player,int attackType) {
		super(player, EventTypeConst.REBELS_ATTACK);
		this.attackType = attackType;
	}

	/**
	 * @return the attackType
	 */
	public int getAttackType() {
		return attackType;
	}

	
	
}

