package com.xgame.logic.server.game.player.entity.eventmodel;

import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.GameLogEventObject;
import com.xgame.logic.server.game.player.entity.Player;


/**
 * 消灭野外怪物
 * @author zehong.he
 *
 */
public class KillMonsterEventObject extends GameLogEventObject {

	/**
	 * 怪物等级
	 */
	private int monsterLevel;
	
	/**
	 * 杀怪数量
	 */
	private int killNum;
	
	public KillMonsterEventObject(Player player,int monsterLevel,int killNum) {
		super(player, EventTypeConst.KILL_MONSTER);
		this.monsterLevel = monsterLevel;
		this.killNum = killNum;
	}

	

	/**
	 * @return the monsterLevel
	 */
	public int getMonsterLevel() {
		return monsterLevel;
	}



	/**
	 * @param monsterLevel the monsterLevel to set
	 */
	public void setMonsterLevel(int monsterLevel) {
		this.monsterLevel = monsterLevel;
	}



	/**
	 * @return the killNum
	 */
	public int getKillNum() {
		return killNum;
	}

	/**
	 * @param killNum the killNum to set
	 */
	public void setKillNum(int killNum) {
		this.killNum = killNum;
	}

	

	
}
