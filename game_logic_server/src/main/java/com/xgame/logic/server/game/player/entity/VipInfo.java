package com.xgame.logic.server.game.player.entity;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;

import io.protostuff.Tag;


/**
 * vip
 * @author jacky.jiang
 *
 */
public class VipInfo implements JBaseTransform{

	@Tag(1)
	private int vipLevel;
	
	@Tag(2)
	private int exp;

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public int getVipLevel() {
		return vipLevel;
	}

	public void setVipLevel(int vipLevel) {
		this.vipLevel = vipLevel;
	}

	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		jbaseData.put("vipLevel",vipLevel);
		jbaseData.put("exp",exp);
		return jbaseData;
	}
	
	public void fromJBaseData(JBaseData jBaseData) {
		this.vipLevel = jBaseData.getInt("vipLevel",0);
		this.exp = jBaseData.getInt("exp",0);
	}
}
