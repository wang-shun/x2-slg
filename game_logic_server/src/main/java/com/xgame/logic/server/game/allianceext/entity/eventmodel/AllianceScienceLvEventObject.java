package com.xgame.logic.server.game.allianceext.entity.eventmodel;

import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.GameLogEventObject;
import com.xgame.logic.server.game.player.entity.Player;

/**
 * 联盟建筑创建
 * @author dingpeng.qu
 *
 */
public class AllianceScienceLvEventObject extends GameLogEventObject {
	/**
	 * 建筑id
	 */
	private int scienceTid;
	
	/**
	 * 原等级
	 */
	private int originLv;
	
	/**
	 * 现等级
	 */
	private int currentLv;
	
	public AllianceScienceLvEventObject(Player player,int scienceTid,int originLv,int currentLv) {
		super(player, EventTypeConst.ALLIANCE_BUILD_CREATE);
		this.scienceTid = scienceTid;
		this.originLv = originLv;
		this.currentLv = currentLv;
	}

	public int getScienceTid() {
		return scienceTid;
	}

	public void setScienceTid(int scienceTid) {
		this.scienceTid = scienceTid;
	}

	public int getOriginLv() {
		return originLv;
	}

	public void setOriginLv(int originLv) {
		this.originLv = originLv;
	}

	public int getCurrentLv() {
		return currentLv;
	}

	public void setCurrentLv(int currentLv) {
		this.currentLv = currentLv;
	}
	
}
