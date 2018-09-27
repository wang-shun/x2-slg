package com.xgame.logic.server.game.country.structs.build.tach.eventmodel;

import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.GameLogEventObject;
import com.xgame.logic.server.game.player.entity.Player;


/**
 * 科研升级
 * @author zehong.he
 *
 */
public class ResearchLevelUpEndEventObject extends GameLogEventObject {

	/**
	 * 科研ID
	 */
	private int scienceId;
	
	/**
	 * 当前等级
	 */
	private int currLevel;
	
	/**
	 * 该等级战力加成
	 */
	private int addCombat;
	
	public ResearchLevelUpEndEventObject(Player player,int scienceId,int currLevel,int addCombat) {
		super(player, EventTypeConst.RESEARCH_LEVEL_UP_END);
		this.scienceId = scienceId;
		this.currLevel = currLevel;
		this.addCombat = addCombat;
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




	/**
	 * @return the scienceId
	 */
	public int getScienceId() {
		return scienceId;
	}




	/**
	 * @param scienceId the scienceId to set
	 */
	public void setScienceId(int scienceId) {
		this.scienceId = scienceId;
	}




	/**
	 * @return the currLevel
	 */
	public int getCurrLevel() {
		return currLevel;
	}

	/**
	 * @param currLevel the currLevel to set
	 */
	public void setCurrLevel(int currLevel) {
		this.currLevel = currLevel;
	}
}
