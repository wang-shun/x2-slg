package com.xgame.logic.server.game.allianceext.entity.eventmodel;

import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.GameLogEventObject;
import com.xgame.logic.server.game.country.bean.Vector2Bean;
import com.xgame.logic.server.game.player.entity.Player;

/**
 * 联盟建筑创建
 * @author dingpeng.qu
 *
 */
public class AllianceBuildLvEventObject extends GameLogEventObject {
	/**
	 * 建筑id
	 */
	private int buildTId;
	/**
	 * 坐标
	 */
	private Vector2Bean position;
	
	/**
	 * 原等级
	 */
	private int originLv;
	
	/**
	 * 现等级
	 */
	private int currentLv;
	
	public AllianceBuildLvEventObject(Player player,int buildTId,Vector2Bean position,int originLv,int currentLv) {
		super(player, EventTypeConst.ALLIANCE_BUILD_LEVEL_UP);
		this.buildTId = buildTId;
		this.position = position;
		this.originLv = originLv;
		this.currentLv = currentLv;
	}

	public int getBuildTId() {
		return buildTId;
	}

	public void setBuildTId(int buildTId) {
		this.buildTId = buildTId;
	}

	public Vector2Bean getPosition() {
		return position;
	}

	public void setPosition(Vector2Bean position) {
		this.position = position;
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
