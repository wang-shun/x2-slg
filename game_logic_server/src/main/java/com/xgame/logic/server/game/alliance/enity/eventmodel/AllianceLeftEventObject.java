package com.xgame.logic.server.game.alliance.enity.eventmodel;

import com.xgame.logic.server.core.gamelog.event.EventObject;
import com.xgame.logic.server.game.player.entity.Player;

/**
 * 推出联盟事件
 * @author jacky.jiang
 *
 */
public class AllianceLeftEventObject extends EventObject {

	public AllianceLeftEventObject(Player player, Integer type, long allianceId, long leaderId,String abbr,String allianceName) {
		super(player, type);
		this.allianceId = allianceId;
		this.leaderId = leaderId;
		this.abbr = abbr;
		this.allianceName = allianceName;
	}

	protected long allianceId;

	private long leaderId;
	
	private String abbr;
	
	private String allianceName;
	
	public long getAllianceId() {
		return this.allianceId;
	}

	public long getLeaderId() {
		return leaderId;
	}

	public void setLeaderId(long leaderId) {
		this.leaderId = leaderId;
	}

	public void setAllianceId(long allianceId) {
		this.allianceId = allianceId;
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
	
	
}
