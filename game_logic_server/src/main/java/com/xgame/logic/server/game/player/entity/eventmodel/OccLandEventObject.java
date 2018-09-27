package com.xgame.logic.server.game.player.entity.eventmodel;

import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.GameLogEventObject;
import com.xgame.logic.server.game.player.entity.Player;


/**
 * 占领领土
 * @author zehong.he
 *
 */
public class OccLandEventObject extends GameLogEventObject {
	
	public static final int OLDOWNER_KONGDI = 1;//空地
	public static final int OLDOWNER_PLAYER = 2;//玩家

	/**
	 * 1空地2非本军团玩家，为空表示不是同军团的都行
	 */
	private int oldOwner;
	
	public OccLandEventObject(Player player,int oldOwner) {
		super(player, EventTypeConst.OCC_LAND);
		this.oldOwner = oldOwner;
	}

	/**
	 * @return the oldOwner
	 */
	public int getOldOwner() {
		return oldOwner;
	}

	
}
