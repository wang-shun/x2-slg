package com.xgame.logic.server.game.world.entity.model;

import io.protostuff.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.google.common.collect.Lists;
import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;
import com.xgame.logic.server.core.utils.collection.ConcurrentHashSet;
import com.xgame.logic.server.game.customweanpon.entity.DesignMap;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.playerattribute.constant.AttributesEnum;
import com.xgame.logic.server.game.soldier.entity.Soldier;


/**
 * 世界队列士兵信息
 * @author jacky.jiang
 *
 */
public class WorldMarchSoldier implements JBaseTransform {

	/**
	 * 玩家id
	 */
	@Tag(1)
	private long playerId;
	
	/**
	 * 士兵信息
	 */
	@Tag(2)
	private Map<String, Soldier> soldiers = new ConcurrentHashMap<String, Soldier>();
	
	/**
	 * 出征部队id
	 */
	private long marchId;
	
	/**
	 * 获取部队当前士兵
	 * @return
	 */
	public int getMarchSolderNum() {
		int num = 0;
		for(Soldier soldier : soldiers.values()) {
			num += soldier.getNum();
		}
		return  num;
	}
	
	/**
	 * 获取行军负载
	 * @return
	 */
	public int getWeight(Player player) {
		int weight = 0;
		List<Soldier> soldiers = this.querySoldierList();
		for (Soldier soldier : soldiers) {
			weight += soldier.getNum() * soldier.getAttribute(player, AttributesEnum.WEIGHT);
		}
		return weight;
	}
	

	public long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}

	public long getMarchId() {
		return marchId;
	}

	public void setMarchId(long marchId) {
		this.marchId = marchId;
	}
	
	
	public Map<String, Soldier> getSoldiers() {
		return soldiers;
	}

	public void setSoldiers(Map<String, Soldier> soldiers) {
		this.soldiers = soldiers;
	}

	public List<Soldier> querySoldierList() {
		return Lists.newArrayList(soldiers.values());
	}
	
	/**
	 * 战力
	 * @param player
	 * @param num
	 * @return
	 */
	public long getFightPower(Player player) {
		long fightPower = 0;
		for(Soldier soldier : soldiers.values()) {
			fightPower += soldier.getFightPower(player, soldier.getNum());
		}
		return fightPower;
	}
	
	
	/**
	 * 查找士兵
	 * @param soldierId
	 * @param index
	 * @return
	 */
	public Soldier querySoldierById(long soldierId, int index) {
		String battleSoldierId = Soldier.factoryBattleSoldierId(soldierId, index);
		return soldiers.get(battleSoldierId);
	}
	
	/**
	 * 获取士兵类型
	 * @return
	 */
	public Set<Integer> getSoldierTypeList() {
		Set<Integer> soldierType = new ConcurrentHashSet<Integer>();
		for(Soldier soldier : soldiers.values()) {
			DesignMap designMap = soldier.getDesignMap();
			if(designMap != null) {
				soldierType.add(designMap.getType());
			}
		}
		return soldierType;
	}

	@SuppressWarnings("rawtypes")
	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		jbaseData.put("playerId", playerId);
		List<JBaseData> soldiersJBaseList = new ArrayList<JBaseData>();
		for (Map.Entry entry : soldiers.entrySet()) {
			soldiersJBaseList.add(((JBaseTransform) entry.getValue()).toJBaseData());
		}
		jbaseData.put("soldiers", soldiersJBaseList);
		jbaseData.put("marchId", marchId);
		return jbaseData;
	}

	public void fromJBaseData(JBaseData jBaseData) {
		this.playerId = jBaseData.getLong("playerId", 0);
		List<JBaseData> jBaseDataList = jBaseData.getSeqBaseData("soldiers");
		for(JBaseData jBaseData2  : jBaseDataList) {
			Soldier soldier = new Soldier();
			soldier.fromJBaseData(jBaseData2);
			this.soldiers.put(Soldier.factoryBattleSoldierId(soldier.getSoldierId(), soldier.getIndex()), soldier);
		}
		this.marchId = jBaseData.getLong("marchId", 0);
	}

}
