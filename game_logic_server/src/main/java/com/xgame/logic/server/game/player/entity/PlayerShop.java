package com.xgame.logic.server.game.player.entity;

import io.protostuff.Tag;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;
import com.xgame.logic.server.core.utils.JsonUtil;


/**
 * 玩家商城信息
 * @author jacky.jiang
 *
 */
public class PlayerShop implements JBaseTransform {

	/**
	 * 玩家id
	 */
	@Tag(1)
	private long playerId;
	
	/**
	 * 商城购买记录
	 */
	@Tag(2)
	private Map<Integer, Integer> shopBuyItemRecord = new HashMap<Integer, Integer>();
	
	/**
	 * 对应批次
	 */
	@Tag(3)
	private int currentBatch;
	
	/**
	 * 阶段
	 */
	@Tag(4)
	private int phase;
	
	/**
	 * 轮数
	 */
	@Tag(5)
	private int round;
	
	public int addItemRecord(int phase, int currentBatch, int round, int itemId, int num) {
		if(this.phase == phase && this.currentBatch == currentBatch && this.round == round) {
			Integer count = shopBuyItemRecord.get(itemId);
			if(count == null) {
				shopBuyItemRecord.put(itemId, num);
			} else {
				shopBuyItemRecord.put(itemId, count + num);
			}
		} else {
			this.phase = phase;
			this.currentBatch = currentBatch;
			this.round = round;
			shopBuyItemRecord.clear();
			shopBuyItemRecord.put(itemId, num);
		}
		
		return shopBuyItemRecord.get(itemId);
	}
	
	public int queryItemRecord(int phase, int currentBatch,  int round, int shopId) {
		if(this.phase == phase && this.currentBatch == currentBatch && this.round == round) {
			Integer count = shopBuyItemRecord.get(shopId);
			if(count == null) {
				return 0;
			} else {
				return count;
			}
		} else {
			return 0;
		}
	}

	public long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}

	public Map<Integer, Integer> getShopBuyItemRecord() {
		return shopBuyItemRecord;
	}

	public void setShopBuyItemRecord(Map<Integer, Integer> shopBuyItemRecord) {
		this.shopBuyItemRecord = shopBuyItemRecord;
	}

	public int getCurrentBatch() {
		return currentBatch;
	}

	public void setCurrentBatch(int currentBatch) {
		this.currentBatch = currentBatch;
	}

	public int getPhase() {
		return phase;
	}

	public void setPhase(int phase) {
		this.phase = phase;
	}

	public int getRound() {
		return round;
	}

	public void setRound(int round) {
		this.round = round;
	}

	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		jbaseData.put("playerId", playerId);
		jbaseData.put("shopBuyItemRecord", JsonUtil.toJSON(shopBuyItemRecord));
		jbaseData.put("currentBatch", currentBatch);
		jbaseData.put("phase", phase);
		jbaseData.put("round", round);
		return jbaseData;
	}

	public void fromJBaseData(JBaseData jBaseData) {
		this.playerId = jBaseData.getLong("playerId", 0);
		
		String shopBuyItemString = jBaseData.getString("shopBuyItemRecord", "");
		if(!StringUtils.isEmpty(shopBuyItemString)) {
			@SuppressWarnings("unchecked")
			Map<Integer, Integer> shopBuyItemRecord = JsonUtil.fromJSON(shopBuyItemString, Map.class);
			this.shopBuyItemRecord = shopBuyItemRecord;
		}
		
		this.currentBatch = jBaseData.getInt("currentBatch", 0);
		this.phase = jBaseData.getInt("phase", 0);
		this.round = jBaseData.getInt("round", 0);
	}

	
}
