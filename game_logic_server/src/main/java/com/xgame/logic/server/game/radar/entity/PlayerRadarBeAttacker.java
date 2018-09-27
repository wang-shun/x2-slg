package com.xgame.logic.server.game.radar.entity;

import io.protostuff.Tag;

import java.util.List;

import com.xgame.logic.server.game.country.bean.Vector2Bean;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.soldier.entity.Soldier;


public class PlayerRadarBeAttacker extends RadarAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Tag(20)
	private int startTime;
	@Tag(21)
	private int time;
	@Tag(22)
	private List<Soldier> soldiers;
	@Tag(23)
	private long marchId;
	
	public PlayerRadarBeAttacker() {
		
	}
	
	public PlayerRadarBeAttacker(Player activePlayer,Player passPlayer, int startTime, int time,Vector2Bean location,List<Soldier> soldiers,long marchId) {
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

	public long getMarchId() {
		return marchId;
	}

	public void setMarchId(long marchId) {
		this.marchId = marchId;
	}

	public List<Soldier> getSoldiers() {
		return soldiers;
	}

	public void setSoldiers(List<Soldier> soldiers) {
		this.soldiers = soldiers;
	}
	
}