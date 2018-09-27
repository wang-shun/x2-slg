package com.xgame.logic.server.game.world.entity.sprite;

import io.protostuff.Tag;

import java.io.Serializable;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.lang3.StringUtils;

import com.xgame.data.sprite.SpriteType;
import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;
import com.xgame.logic.server.core.utils.JsonUtil;
import com.xgame.logic.server.core.utils.collection.ConcurrentHashSet;


/**
 * 空地信息
 * @author jacky.jiang
 *
 */
public class WorldSprite implements Serializable, JBaseTransform {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7753097413225493465L;

	/**
	 * 所属id
	 */
	@Tag(1)
	private String ownerId;
	
	/**
	 * 联盟id
	 */
	@Tag(4)
	private long allianceId;
	
	/**
	 * 上一次联盟id
	 */
	private long lastAllianceId;
	
	/**
	 * 驻军信息
	 */
	@Tag(5)
	private long ownerMarchId;

	/**
	 * 占领开始时间(扎营、占领、采集)
	 */
	@Tag(6)
	private long startTime;
	
	/**
	 * 占领结束时间(扎营、占领、采集)
	 */
	@Tag(7)
	private long endTime;
	
	/**
	 * 正在进行战斗的队伍id
	 */
	private long attackMarchId;
	
	/**
	 * 占领玩家id
	 */
	private long territoryPlayerId;
	
	/**
	 * 战斗等待队伍队列
	 */
	private Queue<Long> battleWaitQueue = new ConcurrentLinkedQueue<>();
	
	/**
	 * 是否迁城
	 */
	private boolean moveCity;
	
	
	/**
	 * 当前战斗开始时间
	 */
	private long currentBattleStartTime;
	
	/**
	 * 当前战斗结束时间
	 */
	private long currentBattleEndTime;
	
	/**
	 * 战斗状态
	 */
	private boolean fight;
	
	/*
	 * 战场当中的士兵信息
	 */
	private Set<Integer> battleSoldierIds = new ConcurrentHashSet<Integer>();
	
	/**
	 * 处理
	 */
	private CountDownLatch countDownLatch;
	
	
	public WorldSprite() {
		super();
	}

	public SpriteType getBlankSpriteType() {
		if(ownerMarchId > 0 || allianceId > 0) {
			return SpriteType.CAMP;
		}
		return SpriteType.NONE;
	}
	
	/**
	 * 清空信息
	 */
	public void clean() {
		this.allianceId = 0;
		this.endTime = 0;
		this.ownerMarchId = 0;
		this.ownerId = "";
		this.startTime = 0;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	
	public long getAllianceId() {
		return allianceId;
	}

	public void setAllianceId(long allianceId) {
		this.allianceId = allianceId;
	}

	public long getOwnerMarchId() {
		return ownerMarchId;
	}

	public void setOwnerMarchId(long ownerMarchId) {
		this.ownerMarchId = ownerMarchId;
	}

	public Queue<Long> getBattleWaitQueue() {
		return battleWaitQueue;
	}

	public void setBattleWaitQueue(Queue<Long> battleWaitQueue) {
		this.battleWaitQueue = battleWaitQueue;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public long getCurrentBattleStartTime() {
		return currentBattleStartTime;
	}

	public void setCurrentBattleStartTime(long currentBattleStartTime) {
		this.currentBattleStartTime = currentBattleStartTime;
	}

	public long getCurrentBattleEndTime() {
		return currentBattleEndTime;
	}

	public void setCurrentBattleEndTime(long currentBattleEndTime) {
		this.currentBattleEndTime = currentBattleEndTime;
	}

	public long getAttackMarchId() {
		return attackMarchId;
	}

	public void setAttackMarchId(long attackMarchId) {
		this.attackMarchId = attackMarchId;
	}
	
	public Set<Integer> getBattleSoldierIds() {
		return battleSoldierIds;
	}

	public void setBattleSoldierIds(Set<Integer> battleSoldierIds) {
		this.battleSoldierIds = battleSoldierIds;
	}
	
	public boolean isMoveCity() {
		return moveCity;
	}

	public void setMoveCity(boolean moveCity) {
		this.moveCity = moveCity;
	}

	public CountDownLatch getCountDownLatch() {
		return countDownLatch;
	}

	public void setCountDownLatch(CountDownLatch countDownLatch) {
		this.countDownLatch = countDownLatch;
	}
	
	public long getTerritoryPlayerId() {
		return territoryPlayerId;
	}

	public void setTerritoryPlayerId(long territoryPlayerId) {
		this.territoryPlayerId = territoryPlayerId;
	}
	
	public long getLastAllianceId() {
		return lastAllianceId;
	}

	public void setLastAllianceId(long lastAllianceId) {
		this.lastAllianceId = lastAllianceId;
	}

	/**
	 * 添加到战斗队列
	 * @param worldMarchId
	 */
	public boolean addBattleAction(long worldMarchId) {
		if(!checkFight()) {
			this.attackMarchId = worldMarchId;
			return true;
		} else {
			this.battleWaitQueue.add(worldMarchId);
			return false;
		}
	}
	
	/**
	 * 处理下一场战斗
	 */
	public long dealNextBattleAction() {
		long marchId = this.battleWaitQueue.poll();
		if(marchId > 0) {
			this.attackMarchId = marchId;
			return marchId;
		}
		return 0;
	}
	
	/**
	 * 移除行军信息
	 * @param marchId
	 */
	public void removeBattleAction(long marchId) {
		this.battleWaitQueue.remove(Long.valueOf(marchId));
	}
	
	/**
	 * 判断野地是否在战斗中
	 * @return true 战斗中，false 不再战斗中
	 */
	public boolean checkFight() {
		return fight;
	}
	
	/**
	 * 判断是否在迁城中
	 * @return
	 */
	public boolean checkMovingCity() {
		if(moveCity || countDownLatch != null && countDownLatch.getCount() > 0) {
			return true;
		}
		return false;
	}
	
	public void dealMoveCityFightEnd() {
		CountDownLatch countDownLatch = getCountDownLatch();
		if(countDownLatch != null) {
			countDownLatch.countDown();
		}
	}
	
	public boolean isFight() {
		return fight;
	}

	public void setFight(boolean fight) {
		this.fight = fight;
	}

	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		jbaseData.put("ownerId", ownerId);
		jbaseData.put("allianceId", allianceId);
		jbaseData.put("ownerMarchId", ownerMarchId);
		jbaseData.put("startTime", startTime);
		jbaseData.put("endTime", endTime);
		jbaseData.put("attackMarchId", attackMarchId);
		jbaseData.put("territoryPlayerId", territoryPlayerId);
		jbaseData.put("lastAllianceId", lastAllianceId);
		
		jbaseData.put("battleWaitQueue", JsonUtil.toJSON(battleWaitQueue));
		return jbaseData;
	}

	@SuppressWarnings("unchecked")
	public void fromJBaseData(JBaseData jBaseData) {
		this.ownerId = jBaseData.getString("ownerId", "");
		this.allianceId = jBaseData.getLong("allianceId", 0);
		this.ownerMarchId = jBaseData.getLong("ownerMarchId", 0);
		this.startTime = jBaseData.getLong("startTime", 0);
		this.endTime = jBaseData.getLong("endTime", 0);
		this.attackMarchId = jBaseData.getLong("attackMarchId", 0);
		this.territoryPlayerId = jBaseData.getLong("territoryPlayerId", 0);
		this.lastAllianceId = jBaseData.getLong("lastAllianceId", 0);
		
		String battleWaitQueue = jBaseData.getString("battleWaitQueue", "");
		if(!StringUtils.isEmpty(battleWaitQueue)) {
			List<Long> queue = JsonUtil.fromJSON(battleWaitQueue, List.class);
			this.battleWaitQueue.addAll(queue);
		}
	}
}
