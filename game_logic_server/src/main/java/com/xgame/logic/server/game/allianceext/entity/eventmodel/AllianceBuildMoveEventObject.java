package com.xgame.logic.server.game.allianceext.entity.eventmodel;

import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.GameLogEventObject;
import com.xgame.logic.server.game.country.bean.Vector2Bean;
import com.xgame.logic.server.game.player.entity.Player;

/**
 * 联盟建筑移动
 * @author dingpeng.qu
 *
 */
public class AllianceBuildMoveEventObject extends GameLogEventObject {
	/**
	 * 建筑id
	 */
	private int buildTId;
	/**
	 * 原始坐标
	 */
	private Vector2Bean originPosition;
	/**
	 * 迁移坐标
	 */
	private Vector2Bean currentPosition;
	
	public AllianceBuildMoveEventObject(Player player,int buildTId,Vector2Bean originPosition,Vector2Bean currentPosition) {
		super(player, EventTypeConst.ALLIANCE_BUILD_MOVE);
		this.buildTId = buildTId;
		this.originPosition = originPosition;
		this.currentPosition = currentPosition;
	}

	public int getBuildTId() {
		return buildTId;
	}

	public void setBuildTId(int buildTId) {
		this.buildTId = buildTId;
	}
	
	public Vector2Bean getOriginPosition() {
		return originPosition;
	}

	public void setOriginPosition(Vector2Bean originPosition) {
		this.originPosition = originPosition;
	}

	public Vector2Bean getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(Vector2Bean currentPosition) {
		this.currentPosition = currentPosition;
	}
	
}
