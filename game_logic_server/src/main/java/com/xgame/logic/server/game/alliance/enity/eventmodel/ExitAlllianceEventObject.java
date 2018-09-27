package com.xgame.logic.server.game.alliance.enity.eventmodel;

import com.xgame.logic.server.core.gamelog.event.GameLogEventObject;
import com.xgame.logic.server.game.player.entity.Player;


/**
 * 推出联盟
 * @author jacky.jiang
 *
 */
public class ExitAlllianceEventObject extends GameLogEventObject {
	
	/**
	 * 联盟id
	 */
	private long allianceId;
	
	/**
	 * 联盟名称
	 */
	private String allianceName;
	
	/**
	 * 联盟简称
	 */
	private String allianceAbbr;
	
	public ExitAlllianceEventObject(Player player, Integer type, long allianceId, String allianceName, String allianceAbbr) {
		super(player, type);
		this.allianceId = allianceId;
		this.allianceAbbr = allianceAbbr;
		this.allianceName = allianceName;
	}

	public long getAllianceId() {
		return allianceId;
	}

	public void setAllianceId(long allianceId) {
		this.allianceId = allianceId;
	}

	public String getAllianceName() {
		return allianceName;
	}

	public void setAllianceName(String allianceName) {
		this.allianceName = allianceName;
	}

	public String getAllianceAbbr() {
		return allianceAbbr;
	}

	public void setAllianceAbbr(String allianceAbbr) {
		this.allianceAbbr = allianceAbbr;
	}
}
