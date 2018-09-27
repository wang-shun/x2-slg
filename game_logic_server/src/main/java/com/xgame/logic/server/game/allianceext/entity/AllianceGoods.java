package com.xgame.logic.server.game.allianceext.entity;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;

/**
 * 军团商店商品信息
 * @author dingpeng.qu
 *
 */
public class AllianceGoods implements JBaseTransform{
	/**
	 * 模版id
	 */
	private int id;
	
	/**
	 * 道具id
	 */
	private int itemId;
	
	/**
	 * 已购买次数
	 */
	private int buyCount;
	
	/**
	 * 品类
	 */
	private int type;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getBuyCount() {
		return buyCount;
	}

	public void setBuyCount(int buyCount) {
		this.buyCount = buyCount;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		jbaseData.put("id", id);
		jbaseData.put("itemId", itemId);
		jbaseData.put("buyCount", buyCount);
		jbaseData.put("type", type);
		return jbaseData;
	}

	@Override
	public void fromJBaseData(JBaseData jBaseData) {
		this.id = jBaseData.getInt("id", 0);
		this.itemId = jBaseData.getInt("itemId", 0);
		this.buyCount = jBaseData.getInt("buyCount", 0);
		this.type = jBaseData.getInt("type", 0);
	}

}
