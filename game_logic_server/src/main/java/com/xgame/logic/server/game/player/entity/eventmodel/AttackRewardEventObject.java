package com.xgame.logic.server.game.player.entity.eventmodel;

import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.GameLogEventObject;
import com.xgame.logic.server.game.player.entity.Player;


/**
 * 攻击玩家掠夺
 * @author zehong.he
 *
 */
public class AttackRewardEventObject extends GameLogEventObject {
	
	public AttackRewardEventObject(Player player) {
		super(player,EventTypeConst.ATTACK_REWARD);
	}
	
	/**
	 * 1石油，2稀土，3钢材，4黄金，为空即所有资源都行
	 */
	private int subType;

	/**
	 * 当前数量
	 */
	private int currentNum;
	
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
	public int getCurrentNum() {
		return currentNum;
	}

	/**
	 * @param currentNum the currentNum to set
	 */
	public void setCurrentNum(int currentNum) {
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
