package com.xgame.logic.server.game.radar.entity;

import java.io.Serializable;

import io.protostuff.Tag;

import com.xgame.logic.server.game.country.bean.Vector2Bean;
import com.xgame.logic.server.game.player.entity.Player;


/**
 * 玩家雷达侦查
 * @author jacky.jiang
 *
 */
public final class PlayerRadarInvestigate implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4908497096945057648L;

	@Tag(12)
	private long money;
	
	@Tag(13)
	private long oil;
	
	@Tag(14)
	private long rare;
	
	@Tag(15)
	private long steel; 
	
	@Tag(16)
	private InvestigateData investigateData;
	
	/**
	 * 被侦查玩家id
	 */
	private long beScoutPlayerId;
	
	/**
	 * 坐标
	 */
	private Vector2Bean vector2Bean;
	
	public PlayerRadarInvestigate(Player beScoutPlayer, Vector2Bean vector2, InvestigateData investigateData, long money, long oil, long rare, long steel) {
		this.investigateData = investigateData;
		this.money = money;
		this.oil = oil;
		this.rare = rare;
		this.steel = steel;
		this.beScoutPlayerId = beScoutPlayer.getId();
		this.vector2Bean = vector2;
	}
	
	
	public PlayerRadarInvestigate() {
		
	}
	
	
	public long getMoney() {
		return money;
	}

	public void setMoney(long money) {
		this.money = money;
	}

	public long getOil() {
		return oil;
	}

	public void setOil(long oil) {
		this.oil = oil;
	}

	public long getRare() {
		return rare;
	}

	public void setRare(long rare) {
		this.rare = rare;
	}

	public long getSteel() {
		return steel;
	}

	public void setSteel(long steel) {
		this.steel = steel;
	}

	public InvestigateData getInvestigateData() {
		return investigateData;
	}

	public void setInvestigateData(InvestigateData investigateData) {
		this.investigateData = investigateData;
	}

	public long getBeScoutPlayerId() {
		return beScoutPlayerId;
	}

	public void setBeScoutPlayerId(long beScoutPlayerId) {
		this.beScoutPlayerId = beScoutPlayerId;
	}

	public Vector2Bean getVector2Bean() {
		return vector2Bean;
	}

	public void setVector2Bean(Vector2Bean vector2Bean) {
		this.vector2Bean = vector2Bean;
	}
	
}
