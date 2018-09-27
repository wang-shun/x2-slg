package com.xgame.logic.server.game.bag.entity.eventmodel;

import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.GameLogEventObject;
import com.xgame.logic.server.game.player.entity.Player;


/**
 * 野外采集
 * @author zehong.he
 *
 */
public class PickEventObject extends GameLogEventObject {
	
	public PickEventObject(Player player) {
		super(player,EventTypeConst.PICK);
	}
	
	/**
	 * 1石油，2稀土，3钢材，4黄金，为空即所有资源都行
	 */
	private int subType;

	/**
	 * 当前数量
	 */
	private long currentNum;
	
	/**
	 * 本次采集数量
	 */
	private int num;


	/**
	 * @return the subType
	 */
	public int getSubType() {
		return subType;
	}

	/**
	 * @param subType the subType to set
	 */
	public void setSubType(int subType) {
		this.subType = subType;
	}

	/**
	 * @return the currentNum
	 */
	public long getCurrentNum() {
		return currentNum;
	}

	/**
	 * @param currentNum the currentNum to set
	 */
	public void setCurrentNum(long currentNum) {
		this.currentNum = currentNum;
	}

	/**
	 * @return the num
	 */
	public int getNum() {
		return num;
	}

	/**
	 * @param num the num to set
	 */
	public void setNum(int num) {
		this.num = num;
	}
	
	
	
	
}
