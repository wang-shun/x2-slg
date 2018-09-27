package com.xgame.logic.server.game.country.structs.build.Radar.data.action;

import java.util.List;

import com.xgame.logic.server.game.country.bean.Vector2Bean;
import com.xgame.logic.server.game.country.structs.build.Radar.data.RadarAction;
import com.xgame.logic.server.game.country.structs.build.Radar.data.RadarSoldier;
import com.xgame.logic.server.game.player.entity.Player;

import io.protostuff.Tag;
public final class PlayerRadarBeAttacker extends RadarAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Tag(20)
	private int startTime;
	@Tag(21)
	private int time;
	@Tag(22)
	private List<RadarSoldier> soldiers;
	@Tag(23)
	private long marchId;
	
	public PlayerRadarBeAttacker() {
		
	}
	
	public PlayerRadarBeAttacker(Player activePlayer,Player passPlayer, int startTime, int time,Vector2Bean location,List<RadarSoldier> soldiers,long marchId) {
		super(activePlayer, passPlayer, location);
		this.startTime = startTime;
		this.time = time;
		this.soldiers=soldiers;
		this.marchId=marchId;
	}
	
	public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public List<RadarSoldier> getSoldiers() {
		return soldiers;
	}

	public void setSoldiers(List<RadarSoldier> soldiers) {
		this.soldiers = soldiers;
	}

	public long getMarchId() {
		return marchId;
	}

	public void setMarchId(long marchId) {
		this.marchId = marchId;
	}

}