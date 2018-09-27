package com.xgame.logic.server.game.player.entity.eventmodel;

import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.GameLogEventObject;
import com.xgame.logic.server.game.player.entity.Player;

/**
 * 修理伤兵
 * @author zehong.he
 *
 */
public class RepairSoldierEventObject extends GameLogEventObject {

	
	/**
	 * 修理数量
	 */
	private int num;
	
	public RepairSoldierEventObject(Player player,int num) {
		super(player, EventTypeConst.REPAIR_SOLDIER);
		this.num = num;
	}

	/**
	 * @return the num
	 */
	public int getNum() {
		return num;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RepairSoldierEventObject [num=" + num + "]";
	}
}
