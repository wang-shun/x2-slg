package com.xgame.logic.server.game.alliance.enity.eventmodel;

import com.xgame.logic.server.core.gamelog.event.EventObject;
import com.xgame.logic.server.game.player.entity.Player;

public class AllianceJoinEventObject extends EventObject {

	public AllianceJoinEventObject(Player player, Integer type, long allianceId,String abbr,String allianceName) {
		super(player, type);
		this.allianceId = allianceId;
		this.abbr = abbr;
		this.allianceName = allianceName;
	}

	protected long allianceId;
	
	private String abbr;
	
	private String allianceName;

	public long getAllianceId() {
		return this.allianceId;
	}

	public String getAbbr() {
		return abbr;
	}

	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}

	public String getAllianceName() {
		return allianceName;
	}

	public void setAllianceName(String allianceName) {
		this.allianceName = allianceName;
	}

	public void setAllianceId(long allianceId) {
		this.allianceId = allianceId;
	}
	
}
