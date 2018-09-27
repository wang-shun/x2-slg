package com.xgame.logic.server.game.alliance.enity.eventmodel;

import com.xgame.logic.server.core.gamelog.event.EventObject;
import com.xgame.logic.server.game.player.entity.Player;

/**
 * 联盟改名事件
 * @author jacky.jiang
 *
 */
public class AllianceNameChangeEventObject extends EventObject {

	public AllianceNameChangeEventObject(Player player, Integer type, long allianceId, String oldName,String allianceName) {
		super(player, type);
		this.allianceId = allianceId;
		this.oldName = oldName;
		this.allianceName = allianceName;
	}

	protected long allianceId;
	
	private String oldName;
	
	private String allianceName;

	public long getAllianceId() {
		return allianceId;
	}

	public void setAllianceId(long allianceId) {
		this.allianceId = allianceId;
	}

	public String getOldName() {
		return oldName;
	}

	public void setOldName(String oldName) {
		this.oldName = oldName;
	}

	public String getAllianceName() {
		return allianceName;
	}

	public void setAllianceName(String allianceName) {
		this.allianceName = allianceName;
	}
	
}
