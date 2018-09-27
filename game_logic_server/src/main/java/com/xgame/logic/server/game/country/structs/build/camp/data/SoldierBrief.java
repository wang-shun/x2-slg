package com.xgame.logic.server.game.country.structs.build.camp.data;

import java.io.Serializable;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;

import io.protostuff.Tag;

/**
 *
 *2016-11-22  14:30:59
 *@author ye.yuan
 *
 */
public class SoldierBrief implements Serializable, JBaseTransform{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Tag(1)
	private long soldierId;
	
	@Tag(2)
	private int num;
	
	public SoldierBrief(){}
	
	public SoldierBrief(long soldierId, int num) {
		super();
		this.soldierId = soldierId;
		this.num = num;
	}
	
	public SoldierBrief toSoldierBrief(){
		return new SoldierBrief(soldierId, num);
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public long getSoldierId() {
		return soldierId;
	}

	public void setSoldierId(long soldierId) {
		this.soldierId = soldierId;
	}

	@Override
	public JBaseData toJBaseData() {
		JBaseData jBaseData = new JBaseData();
		jBaseData.put("soldierId", soldierId);
		jBaseData.put("num", num);
		return jBaseData;
	}

	@Override
	public void fromJBaseData(JBaseData jBaseData) {
		this.soldierId = jBaseData.getLong("soldierId", 0);
		this.num = jBaseData.getInt("num", 0);
	}
	
}
