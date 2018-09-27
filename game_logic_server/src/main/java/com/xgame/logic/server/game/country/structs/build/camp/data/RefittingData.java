package com.xgame.logic.server.game.country.structs.build.camp.data;

import java.io.Serializable;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;

import io.protostuff.Tag;


/**
 * 
 * @author jacky.jiang
 *
 */
public class RefittingData implements Serializable,JBaseTransform {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Tag(1)
	private long soldierId;
	
	@Tag(2)
	private int num;

	public long getSoldierId() {
		return soldierId;
	}

	public void setSoldierId(long sid) {
		this.soldierId = sid;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
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
