package com.xgame.logic.server.game.player.entity.eventmodel;

import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.GameLogEventObject;
import com.xgame.logic.server.game.player.entity.Player;


/**
 * 通关副本
 * @author zehong.he
 *
 */
public class CopyPassEventObject extends GameLogEventObject {

	private int copyId;
	
	public CopyPassEventObject(Player player,int copyId) {
		super(player, EventTypeConst.COPY_PASS);
		this.copyId = copyId;
	}

	/**
	 * @return the copyId
	 */
	public int getCopyId() {
		return copyId;
	}

	/**
	 * @param copyId the copyId to set
	 */
	public void setCopyId(int copyId) {
		this.copyId = copyId;
	}

	
}
