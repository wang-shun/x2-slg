package com.xgame.logic.server.game.allianceext.entity;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;


/**
 * 战事奖励
 * @author jacky.jiang
 *
 */
public class AllianceBenifit implements JBaseTransform{

	/**
	 * 奖励模版ID
	 */
	private int itemId;
	
	/**
	 * 数量
	 */
	private int num;

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	@Override
	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		jbaseData.put("itemId", itemId);
		jbaseData.put("num", num);
		return jbaseData;
	}

	@Override
	public void fromJBaseData(JBaseData jBaseData) {
		this.itemId = jBaseData.getInt("itemId", 0);
		this.num = jBaseData.getInt("num", 0);
		
	}
	
}	
