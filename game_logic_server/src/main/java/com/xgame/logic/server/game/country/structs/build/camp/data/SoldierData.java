package com.xgame.logic.server.game.country.structs.build.camp.data;

import io.protostuff.Tag;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.google.common.collect.Lists;
import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;
import com.xgame.logic.server.core.utils.SplitUtil;
import com.xgame.logic.server.game.soldier.entity.Soldier;

/**
 *
 *2016-8-05  10:20:46
 *@author ye.yuan
 *
 */
public class SoldierData implements Serializable, JBaseTransform{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 正常士兵信息
	 */
	@Tag(1)
	private Map<Long, Soldier> soldiers = new ConcurrentHashMap<>();	
	
	/**
	 * 正在改装的士兵信息
	 */
	@Tag(2)
	private RefittingData refittingData = new RefittingData();
	
	/**
	 * 改装厂的士兵
	 */
	@Tag(3)
	private Map<Long, ReformSoldier> reformSoldierTable = new ConcurrentHashMap<>();
	
	/**
	 * 改装厂的伤兵
	 */
	@Tag(4)
	private Map<Long, ReformSoldier> reformSoldierHurtTable = new ConcurrentHashMap<>();
	
	/**
	 * 兵营信息
	 */
	@Tag(5)
	private CampData campData = new CampData();
	
	/**
	 * 解锁的配件
	 */
	@Tag(6)
	private Map<Integer, Integer> unlockPeijians = new ConcurrentHashMap<>();
	
	/**
	 * 防守驻军兵信息
	 * <buildUid,soldierId,num>
	 */
	@Tag(7)
	private Map<Integer,Map<Long,Integer>> defenseSoldierMap = new ConcurrentHashMap<>();
	
	
	public Map<Long, Soldier> getSoldiers() {
		return soldiers;
	}

	public void setSoldiers(Map<Long, Soldier> soldiers) {
		this.soldiers = soldiers;
	}

	public RefittingData getRefittingData() {
		return refittingData;
	}

	public void setRefittingData(RefittingData refittingData) {
		this.refittingData = refittingData;
	}

	public Map<Long, ReformSoldier> getReformSoldierTable() {
		return reformSoldierTable;
	}

	public void setReformSoldierTable(Map<Long, ReformSoldier> reformSoldierTable) {
		this.reformSoldierTable = reformSoldierTable;
	}

	public Map<Long, ReformSoldier> getReformSoldierHurtTable() {
		return reformSoldierHurtTable;
	}

	public void setReformSoldierHurtTable(
			Map<Long, ReformSoldier> reformSoldierHurtTable) {
		this.reformSoldierHurtTable = reformSoldierHurtTable;
	}
	
	public CampData getCampData() {
		return campData;
	}

	public void setCampData(CampData campData) {
		this.campData = campData;
	}

	/**
	 * 查询士兵信息
	 * @param soldierId
	 * @return
	 */
	public Soldier querySoldierById(long soldierId) {
		return soldiers.get(soldierId);
	}
	
	/**
	 * 增加士兵
	 * @param soldier
	 */
	public void newSoldier(Soldier soldier) {
		soldiers.put(soldier.getSoldierId(), soldier);
	}
	
	
	/**
	 * 增加士兵数变更根据更类型
	 * @param soldierId
	 * @param num
	 * @param soldierChangeType
	 * @return
	 */
	public int addSoldier(long soldierId, int num, int soldierChangeType) {
		Soldier soldier = this.soldiers.get(soldierId);
		if(soldier != null) {
			soldier.updateSoldierByType(num, soldierChangeType);
			return soldier.getNum();
		}
		return 0;
	}
	
	/**
	 * 扣除士兵变更根据更类型
	 * @param soldierId
	 * @param num
	 * @param soldierChangeType
	 * @return
	 */
	public int decrementSoldier(long soldierId, int num, int soldierChangeType) {
		Soldier soldier = this.soldiers.get(soldierId);
		if(soldier != null) {
			soldier.updateSoldierByType(-num, soldierChangeType);
			return soldier.getNum();
		}
		return 0;
	}
	
	public void reformSoldierTable(long soldierId, int type) {
		
	}
	
	public Map<Integer, Integer> getUnlockPeijians() {
		return unlockPeijians;
	}

	public void setUnlockPeijians(Map<Integer, Integer> unlockPeijians) {
		this.unlockPeijians = unlockPeijians;
	}
	
	public Map<Integer, Map<Long, Integer>> getDefenseSoldierMap() {
		return defenseSoldierMap;
	}

	/**
	 * 驻防兵数量
	 * @param soldierId
	 * @return
	 */
	public int getDefenseSoldierNum(long soldierId){
		int num = 0;
		for(Entry<Integer,Map<Long,Integer>> entry : defenseSoldierMap.entrySet()){
			for(Entry<Long,Integer> entry0 : entry.getValue().entrySet()){
				if(String.valueOf(soldierId).equals(entry0.getValue().toString())){
					num += entry0.getValue();
				}
			}
		}
		
		return num;
	}
	
	/**
	 * 增加驻防兵数量
	 * @param buildId
	 * @param soldierId
	 * @param addNum
	 */
	public void addDefenseSoldierNum(int buildUid,long soldierId,int addNum){
		if(addNum < 0){
			return;
		}
		if(!defenseSoldierMap.containsKey(buildUid)){
			defenseSoldierMap.put(buildUid, new HashMap<>());
		}
		if(!defenseSoldierMap.get(buildUid).containsKey(soldierId)){
			defenseSoldierMap.get(buildUid).put(soldierId, addNum);
		}else{
			defenseSoldierMap.get(buildUid).put(soldierId, defenseSoldierMap.get(buildUid).get(soldierId) + addNum);
		}
	}
	
	/**
	 * 减少驻防兵数量
	 * @param buildId
	 * @param soldierId
	 * @param decrNum
	 */
	public void decrDefenseSoldierNum(int buildUid,long soldierId,int decrNum){
		if(decrNum < 0){
			return;
		}
		if(!defenseSoldierMap.containsKey(buildUid)){
			return;
		}
		if(!defenseSoldierMap.get(buildUid).containsKey(soldierId)){
			return;
		}
		int num = defenseSoldierMap.get(buildUid).get(soldierId) - decrNum;
		if(num < 0){
			num = 0;
		}
		defenseSoldierMap.get(buildUid).put(soldierId, num);
	}
	
	public ReformSoldier getReformSoldier(long id) {
		ReformSoldier reformSoldier = this.getReformSoldierTable().get(id);
		if(reformSoldier == null) {
			reformSoldier = this.getReformSoldierHurtTable().get(id);
		}
		return reformSoldier;
	}

	@Override
	public JBaseData toJBaseData() {
		JBaseData jBaseData = new JBaseData();
		List<JBaseData> soldiers = new ArrayList<JBaseData>();
		for(Soldier soldier : this.soldiers.values()) {
			soldiers.add(soldier.toJBaseData());
		}
		jBaseData.put("soldiers", soldiers);
		jBaseData.put("refittingData", refittingData.toJBaseData());
		
		List<JBaseData> reformSoldiers = new ArrayList<>();
		for(ReformSoldier reformSoldier : this.reformSoldierTable.values()) {
			reformSoldiers.add(reformSoldier.toJBaseData());
		}
		jBaseData.put("reformSoldierTable", reformSoldiers);
		
		List<JBaseData> reformSoldierHurtTable = new ArrayList<>();
		for(ReformSoldier reformSoldier : this.reformSoldierHurtTable.values()) {
			reformSoldierHurtTable.add(reformSoldier.toJBaseData());
		}
		jBaseData.put("reformSoldierHurtTable", reformSoldierHurtTable);
		
		jBaseData.put("campData", campData.toJBaseData());
		jBaseData.put("unlockPeijians", Lists.newArrayList(unlockPeijians.keySet()));
		
		jBaseData.put("defenseSoldierMap", SplitUtil.map2String1(defenseSoldierMap));
		
		return jBaseData;
	}

	@Override
	public void fromJBaseData(JBaseData jBaseData) {
		List<JBaseData> soldierList = jBaseData.getSeqBaseData("soldiers");
		for(JBaseData jBaseData2 :soldierList) {
			Soldier soldier = new Soldier();
			soldier.fromJBaseData(jBaseData2);
			this.soldiers.put(soldier.getSoldierId(), soldier);
		}
		
		JBaseData refittingData = jBaseData.getBaseData("refittingData");
		this.refittingData.fromJBaseData(refittingData);
		
		List<JBaseData> reformSoldierTable = jBaseData.getSeqBaseData("reformSoldierTable");
		for(JBaseData baseData : reformSoldierTable) {
			ReformSoldier reformSoldier = new ReformSoldier();
			reformSoldier.fromJBaseData(baseData);
			this.reformSoldierTable.put(reformSoldier.getId(), reformSoldier);
		}
		
		List<JBaseData> reformSoldierHurtTable = jBaseData.getSeqBaseData("reformSoldierHurtTable");
		for(JBaseData baseData : reformSoldierHurtTable) {
			ReformSoldier reformSoldier = new ReformSoldier();
			reformSoldier.fromJBaseData(baseData);
			this.reformSoldierHurtTable.put(reformSoldier.getId(), reformSoldier);
		}
		
		CampData campData = new CampData();
		campData.fromJBaseData(jBaseData);
		
		List<Integer> unlockPeijians = jBaseData.getSeqInt("unlockPeijians");
		for(Integer unlockPeijian : unlockPeijians) {
			this.unlockPeijians.put(unlockPeijian, unlockPeijian);
		}
		String defenseSoldierStr = jBaseData.getString("defenseSoldierMap", "");
		this.defenseSoldierMap = SplitUtil.string2Map1(defenseSoldierStr, defenseSoldierMap);
	}

}
