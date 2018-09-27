package com.xgame.logic.server.game.country.structs.build.Radar.data.action;

import com.xgame.logic.server.game.country.bean.Vector2Bean;
import com.xgame.logic.server.game.country.structs.build.Radar.data.InvestigateData;
import com.xgame.logic.server.game.country.structs.build.Radar.data.RadarAction;
import com.xgame.logic.server.game.player.entity.Player;

import io.protostuff.Tag;


/**
 *
 *2016-12-22  19:45:26
 *@author ye.yuan
 *
 */
public final class PlayerRadarInvestigate extends RadarAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5593682357061381243L;
	
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
	
	public PlayerRadarInvestigate(Player activePlayer,Player passPlayer, Vector2Bean vector2,InvestigateData investigateData,
			long money, long oil, long rare, long steel) {
		super(activePlayer, passPlayer, vector2);
		this.investigateData = investigateData;
		investigateData.setVector2Bean(vector2);
		this.money = money;
		this.oil = oil;
		this.rare = rare;
		this.steel = steel;
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


}
