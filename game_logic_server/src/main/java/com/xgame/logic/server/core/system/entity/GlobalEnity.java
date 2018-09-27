package com.xgame.logic.server.core.system.entity;

import io.protostuff.Tag;

import com.xgame.logic.server.core.db.cache.entity.AbstractEntity;
import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;


/**
 * 全局配置表
 * @author jacky.jiang
 *
 */
public class GlobalEnity extends AbstractEntity<String> implements JBaseTransform{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3300606114930133829L;

	/**
	 * 全局变量key
	 */
	@Tag(1)
	private String key;
	
	/**
	 * 开服时间
	 */
	@Tag(2)
	private long serverOpenTime;
	
	/**
	 * 地图生成时间
	 */
	@Tag(3)
	private long mapGenerateTime;
	
	/**
	 * 当前批次
	 */
	@Tag(4)
	private int shopCurrentBatch;
	
	/**
	 * 当前阶段
	 */
	@Tag(5)
	private int shopCurrentPhase;
	
	/**
	 * 批次开始时间
	 */
	@Tag(6)
	private int batchStartTime;
	
	/**
	 * 商店活动轮数
	 */
	@Tag(7)
	private int round;
	
	/**
	 * 关服时间
	 */
	private long shutdownTime;
	
	@Override
	public String getId() {
		return key;
	}

	@Override
	public void setId(String k) {
		this.key = k;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public long getServerOpenTime() {
		return serverOpenTime;
	}

	public void setServerOpenTime(long serverOpenTime) {
		this.serverOpenTime = serverOpenTime;
	}

	public long getMapGenerateTime() {
		return mapGenerateTime;
	}

	public void setMapGenerateTime(long mapGenerateTime) {
		this.mapGenerateTime = mapGenerateTime;
	}

	public int getShopCurrentBatch() {
		return shopCurrentBatch;
	}

	public void setShopCurrentBatch(int shopCurrentBatch) {
		this.shopCurrentBatch = shopCurrentBatch;
	}

	public int getShopCurrentPhase() {
		return shopCurrentPhase;
	}

	public void setShopCurrentPhase(int shopCurrentPhase) {
		this.shopCurrentPhase = shopCurrentPhase;
	}

	public int getBatchStartTime() {
		return batchStartTime;
	}

	public void setBatchStartTime(int batchStartTime) {
		this.batchStartTime = batchStartTime;
	}

	public int getRound() {
		return round;
	}

	public void setRound(int round) {
		this.round = round;
	}
	
	public long getShutdownTime() {
		return shutdownTime;
	}

	public void setShutdownTime(long shutdownTime) {
		this.shutdownTime = shutdownTime;
	}

	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		jbaseData.put("key", key);
		jbaseData.put("serverOpenTime", serverOpenTime);
		jbaseData.put("mapGenerateTime", mapGenerateTime);
		jbaseData.put("shopCurrentBatch", shopCurrentBatch);
		jbaseData.put("shopCurrentPhase", shopCurrentPhase);
		jbaseData.put("batchStartTime", batchStartTime);
		jbaseData.put("round", round);
		jbaseData.put("shutdownTime", shutdownTime);
		return jbaseData;
	}

	public void fromJBaseData(JBaseData jBaseData) {
		this.key = jBaseData.getString("key", "");
		this.serverOpenTime = jBaseData.getLong("serverOpenTime", 0);
		this.mapGenerateTime = jBaseData.getLong("mapGenerateTime", 0);
		this.shopCurrentBatch = jBaseData.getInt("shopCurrentBatch", 0);
		this.shopCurrentPhase = jBaseData.getInt("shopCurrentPhase", 0);
		this.batchStartTime = jBaseData.getInt("batchStartTime", 0);
		this.round = jBaseData.getInt("round", 0);
		this.shutdownTime = jBaseData.getInt("shutdownTime", 0);
	}

	
}
