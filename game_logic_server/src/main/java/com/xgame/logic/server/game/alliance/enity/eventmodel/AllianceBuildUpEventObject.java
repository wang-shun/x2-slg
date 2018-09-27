package com.xgame.logic.server.game.alliance.enity.eventmodel;

import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.GameLogEventObject;
import com.xgame.logic.server.game.player.entity.Player;

/**
 * 发布集结
 * @author zehong.he
 *
 */
public class AllianceBuildUpEventObject extends GameLogEventObject {

	
	/** 1进攻，2防御*/
	private int buildUpType;
	
	public AllianceBuildUpEventObject(Player player,int buildUpType) {
		super(player, EventTypeConst.ALLIANCE_BUILD_UP);
		this.buildUpType = buildUpType;
	}

	/**
	 * @return the buildUpType
	 */
	public int getBuildUpType() {
		return buildUpType;
	}
	
	
	
}
