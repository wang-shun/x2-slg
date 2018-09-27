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
public class AllianceBuildDeleteEventObject extends GameLogEventObject {
	/**
	 * 建筑id
	 */
	private int buildTId;
	/**
	 * 坐标
	 */
	private Vector2Bean position;
	
	public AllianceBuildDeleteEventObject(Player player,int buildTId,Vector2Bean position) {
		super(player, EventTypeConst.ALLIANCE_BUILD_REMOVE);
		this.buildTId = buildTId;
		this.position = position;
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
	
}
