package com.xgame.logic.server.game.player.entity.eventmodel;

import java.util.Map;

import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.GameLogEventObject;
import com.xgame.logic.server.game.player.entity.Player;


/**
 * 摧毁敌方部队
 * @author zehong.he
 *
 */
public class DestroySideEventObject extends GameLogEventObject {

	/**
	 * 是否是在本服击杀
	 */
	private boolean isCurrentServer;
	
	/**
	 * key:士兵等级
	 * value:数量
	 */
	private Map<Long,Integer> damageSideSoldier;
	
	public DestroySideEventObject(Player player,boolean isCurrentServer,Map<Long,Integer> damageSideSoldier) {
		super(player, EventTypeConst.DESTROY_SIDE);
		this.isCurrentServer = isCurrentServer;
		this.damageSideSoldier = damageSideSoldier;
	}

	/**
	 * @return the damageSideSoldier
	 */
	public Map<Long, Integer> getDamageSideSoldier() {
		return damageSideSoldier;
	}

	/**
	 * @param damageSideSoldier the damageSideSoldier to set
	 */
	public void setDamageSideSoldier(Map<Long, Integer> damageSideSoldier) {
		this.damageSideSoldier = damageSideSoldier;
	}

	/**
	 * @return the isCurrentServer
	 */
	public boolean isCurrentServer() {
		return isCurrentServer;
	}

	/**
	 * @param isCurrentServer the isCurrentServer to set
	 */
	public void setCurrentServer(boolean isCurrentServer) {
		this.isCurrentServer = isCurrentServer;
	}

	
}
