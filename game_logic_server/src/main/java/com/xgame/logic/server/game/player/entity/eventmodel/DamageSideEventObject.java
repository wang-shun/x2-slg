package com.xgame.logic.server.game.player.entity.eventmodel;

import java.util.Map;

import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.GameLogEventObject;
import com.xgame.logic.server.game.player.entity.Player;


/**
 * 损坏敌方部队
 * @author zehong.he
 *
 */
public class DamageSideEventObject extends GameLogEventObject {

	/**
	 * key:士兵等级
	 * value:数量
	 */
	private Map<Integer,Integer> damageSideSoldier;
	
	public DamageSideEventObject(Player player,Map<Integer,Integer> damageSideSoldier) {
		super(player, EventTypeConst.DAMAGE_SIDE);
		this.damageSideSoldier = damageSideSoldier;
	}

	/**
	 * @return the damageSideSoldier
	 */
	public Map<Integer, Integer> getDamageSideSoldier() {
		return damageSideSoldier;
	}

	/**
	 * @param damageSideSoldier the damageSideSoldier to set
	 */
	public void setDamageSideSoldier(Map<Integer, Integer> damageSideSoldier) {
		this.damageSideSoldier = damageSideSoldier;
	}

}
