package com.xgame.logic.server.game.alliance.enity.eventmodel;

import com.xgame.logic.server.core.gamelog.event.GameLogEventObject;
import com.xgame.logic.server.game.player.entity.Player;

/**
 * 创建联盟
 * @author jacky.jiang
 *
 */
public class CreateAllianceEventObject extends GameLogEventObject {

	public CreateAllianceEventObject(Player player, int type, String allianceName, String abbr, long allianceId) {
		super(player, type);
		this.allianceId = allianceId;
		this.abbrName = abbr;
		this.allianceName = allianceName;
	}
	
	/**
	 * 联盟名称
	 */
	private String allianceName;
	
	/**
	 * 联盟简称
	 */
	private String abbrName;
	
	/**
	 * 联盟id
	 */
	private long allianceId;
	
	public String getAllianceName() {
		return allianceName;
	}

	public void setAllianceName(String allianceName) {
		this.allianceName = allianceName;
	}

	public String getAbbrName() {
		return abbrName;
	}

	public void setAbbrName(String abbrName) {
		this.abbrName = abbrName;
	}

	public long getAllianceId() {
		return allianceId;
	}

	public void setAllianceId(long allianceId) {
		this.allianceId = allianceId;
	}


	@Override
	public String toString() {
		return "CreateAllianceEventObject [allianceName=" + allianceName
				+ ", abbrName=" + abbrName + ", allianceId=" + allianceId
				+ ", type=" + type + "]";
	}
	
}
