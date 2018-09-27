package com.xgame.logic.server.game.alliance.enity.eventmodel;

import com.xgame.logic.server.core.gamelog.event.EventObject;
import com.xgame.logic.server.game.player.entity.Player;

/**
 * 联盟简称修改事件
 * @author jacky.jiang
 *
 */
public class AllianceAbbrChangeEventObject extends EventObject {

	public AllianceAbbrChangeEventObject(Player player, Integer type, long allianceId, String oldAbbr,String abbr) {
		super(player, type);
		this.allianceId = allianceId;
		this.oldAbbr = oldAbbr;
		this.abbr = abbr;
	}

	protected long allianceId;
	
	private String oldAbbr;
	
	private String abbr;

	public long getAllianceId() {
		return allianceId;
	}

	public void setAllianceId(long allianceId) {
		this.allianceId = allianceId;
	}

	public String getOldAbbr() {
		return oldAbbr;
	}

	public void setOldAbbr(String oldAbbr) {
		this.oldAbbr = oldAbbr;
	}

	public String getAbbr() {
		return abbr;
	}

	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}
	
	
}
