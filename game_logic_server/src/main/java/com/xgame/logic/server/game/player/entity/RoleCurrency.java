package com.xgame.logic.server.game.player.entity;

import io.protostuff.Tag;

import java.io.Serializable;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;

/**
 * Created by vyang on 6/21/16.
 */
public class RoleCurrency implements Serializable, JBaseTransform {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1;
	@Tag(1)
	private volatile long power;
	
	@Tag(2)
	private volatile long money;// 黄金

	@Tag(3)
	private volatile long steel;// 钢材

	@Tag(4)
	private volatile long oil;// 石油

	@Tag(5)
	private volatile long rare;// 稀土

	@Tag(6)
	private volatile long diamond;// 钻石
	
	@Tag(8)
	private volatile long vitality;// 体力

	public long getPower() {
		return power;
	}

	public void setPower(long power) {
		this.power = power;
	}

	public long getMoney() {
		return money;
	}

	public void setMoney(long money) {
		this.money = money;
	}

	public long getSteel() {
		return steel;
	}

	public void setSteel(long steel) {
		this.steel = steel;
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

	public long getDiamond() {
		return diamond;
	}

	public void setDiamond(long diamond) {
		this.diamond = diamond;
	}

	public long getVitality() {
		return vitality;
	}

	public void setVitality(long vitality) {
		this.vitality = vitality;
	}

	@Override
	public JBaseData toJBaseData() {
		JBaseData jBaseData = new JBaseData();
		jBaseData.put("power",power);
		jBaseData.put("money",money);
		jBaseData.put("steel",steel);
		jBaseData.put("oil",oil);
		jBaseData.put("rare",rare);
		jBaseData.put("diamond",diamond);
		jBaseData.put("vitality",vitality);
		return jBaseData;
	}

	@Override
	public void fromJBaseData(JBaseData jBaseData) {
		this.power = jBaseData.getLong("power", 0);
		this.money = jBaseData.getLong("money", 0);
		this.steel = jBaseData.getLong("steel", 0);
		this.oil = jBaseData.getLong("oil", 0);
		this.rare = jBaseData.getLong("rare", 0);
		this.diamond = jBaseData.getLong("diamond", 0);
		this.vitality = jBaseData.getLong("vitality", 0);
	}
	
}
