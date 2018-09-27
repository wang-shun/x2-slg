package com.xgame.logic.server.game.country.entity.eventmodel;

import com.xgame.logic.server.core.gamelog.event.EventObject;
import com.xgame.logic.server.game.player.entity.Player;


/**
 * 升级建筑完成
 * @author jacky.jiang
 *
 */
public class LevelUpBulidFinishEventObject extends EventObject {

	public LevelUpBulidFinishEventObject(Player player, Integer type, int tid, int oldLevel, long fightPower,int addCombat) {
		super(player, type);
		this.oldLevel = oldLevel;
		this.tid = tid;
		this.fightPower = fightPower;
		this.addCombat = addCombat;
	}
	
	/**
	 * 该等级战力加成
	 */
	private int addCombat;

	private int oldLevel;

	private int tid;
	
	private long fightPower;
	
	public long getoldLevel() {
		return this.oldLevel;
	}

	public int getOldLevel() {
		return oldLevel;
	}

	public int getTid() {
		return tid;
	}

	public long getFightPower() {
		return fightPower;
	}

	public void setFightPower(long fightPower) {
		this.fightPower = fightPower;
	}

	public void setOldLevel(int oldLevel) {
		this.oldLevel = oldLevel;
	}

	public void setTid(int tid) {
		this.tid = tid;
	}

	/**
	 * @return the addCombat
	 */
	public int getAddCombat() {
		return addCombat;
	}

	/**
	 * @param addCombat the addCombat to set
	 */
	public void setAddCombat(int addCombat) {
		this.addCombat = addCombat;
	}
	
}
