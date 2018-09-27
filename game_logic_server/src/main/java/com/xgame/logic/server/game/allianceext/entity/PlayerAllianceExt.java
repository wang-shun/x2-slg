package com.xgame.logic.server.game.allianceext.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.xgame.logic.server.core.db.cache.entity.AbstractEntity;
import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;
import com.xgame.utils.TimeUtils;


/**
 * 联盟成员扩展信息
 * @author jacky.jiang
 *
 */
public class PlayerAllianceExt extends AbstractEntity<Long> implements JBaseTransform{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2399983052482505659L;

	/**
	 * 玩家ID
	 */
	private long playerId;
	
	/**
	 * 联盟id
	 */
	private long allianceId;
	
	/**
	 * 当日累计值
	 */
	private int activityScore;
	
	/**
	 * 当日领取活跃度石油
	 */
	private int rewardActivityOil;
	
	/**
	 * 当日领取活跃度稀土
	 */
	private int rewardActivityRare;
	
	/**
	 * 当日领取活跃度钢铁
	 */
	private int rewardActivitySteel;
	
	/**
	 * 当日领取活跃度黄金
	 */
	private int rewardActivityGold;
	
	/**
	 * 科研石油捐献次数
	 */
	private int scienceOilCount;
	
	/**
	 * 科研稀土捐献次数
	 */
	private int scienceRareCount;
	
	/**
	 * 科研钢材捐献次数
	 */
	private int scienceSteelCount;
	
	/**
	 * 科研黄金捐献次数
	 */
	private int scienceGoldCount;
	
	/**
	 * 科研钻石捐献次数
	 */
	private int scienceDiamondCount;
	
	/**
	 * 刷新时间
	 */
	private long refreshTime;
	
	/**
	 * 已开宝箱列表
	 */
	private Map<Long,AwardInfo> boxIds = new ConcurrentHashMap<>();
	
	/**
	 * 军团商店道具购买次数
	 */
	private Map<Integer,AllianceGoods> commonInfo = new ConcurrentHashMap<Integer, AllianceGoods>();
	
	/**
	 * 演习开始时间
	 */
	private long exerciseStartTime;
	
	/**
	 * 演习奖励列表
	 */
	private Map<Integer,ExerciseInfo> exerciseRewards = new ConcurrentHashMap<>();

	public int getRewardActivityOil() {
		return rewardActivityOil;
	}

	public void setRewardActivityOil(int rewardActivityOil) {
		this.rewardActivityOil = rewardActivityOil;
	}

	public int getRewardActivityRare() {
		return rewardActivityRare;
	}

	public void setRewardActivityRare(int rewardActivityRare) {
		this.rewardActivityRare = rewardActivityRare;
	}

	public int getRewardActivitySteel() {
		return rewardActivitySteel;
	}

	public void setRewardActivitySteel(int rewardActivitySteel) {
		this.rewardActivitySteel = rewardActivitySteel;
	}

	public int getRewardActivityGold() {
		return rewardActivityGold;
	}

	public void setRewardActivityGold(int rewardActivityGold) {
		this.rewardActivityGold = rewardActivityGold;
	}
	
	public long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}
	
	public long getExerciseStartTime() {
		return exerciseStartTime;
	}

	public void setExerciseStartTime(long exerciseStartTime) {
		this.exerciseStartTime = exerciseStartTime;
	}

	public int getScienceOilCount() {
		return scienceOilCount;
	}

	public void setScienceOilCount(int scienceOilCount) {
		this.scienceOilCount = scienceOilCount;
	}

	public int getScienceRareCount() {
		return scienceRareCount;
	}

	public void setScienceRareCount(int scienceRareCount) {
		this.scienceRareCount = scienceRareCount;
	}

	public int getScienceSteelCount() {
		return scienceSteelCount;
	}

	public void setScienceSteelCount(int scienceSteelCount) {
		this.scienceSteelCount = scienceSteelCount;
	}

	public int getScienceGoldCount() {
		return scienceGoldCount;
	}

	public void setScienceGoldCount(int scienceGoldCount) {
		this.scienceGoldCount = scienceGoldCount;
	}

	public long getRefreshTime() {
		return refreshTime;
	}

	public void setRefreshTime(long refreshTime) {
		this.refreshTime = refreshTime;
	}
	
	public long getAllianceId() {
		return allianceId;
	}

	public void setAllianceId(long allianceId) {
		this.allianceId = allianceId;
	}

	public int getActivityScore() {
		return activityScore;
	}

	public void setActivityScore(int activityScore) {
		this.activityScore = activityScore;
	}

	public Map<Long, AwardInfo> getBoxIds() {
		return boxIds;
	}

	public void setBoxIds(Map<Long, AwardInfo> boxIds) {
		this.boxIds = boxIds;
	}
	
	public Map<Integer, ExerciseInfo> getExerciseRewards() {
		return exerciseRewards;
	}

	public void setExerciseRewards(Map<Integer, ExerciseInfo> exerciseRewards) {
		this.exerciseRewards = exerciseRewards;
	}

	public int getScienceDiamondCount() {
		return scienceDiamondCount;
	}

	public void setScienceDiamondCount(int scienceDiamondCount) {
		this.scienceDiamondCount = scienceDiamondCount;
	}
	
	public Map<Integer, AllianceGoods> getCommonInfo() {
		return commonInfo;
	}

	public void setCommonInfo(Map<Integer, AllianceGoods> commonInfo) {
		this.commonInfo = commonInfo;
	}

	@Override
	public Long getId() {
		return playerId;
	}

	@Override
	public void setId(Long k) {
		this.playerId = k;
	}
	
	
	/**
	 * 刷新数据
	 * @return
	 */
	public synchronized boolean refresh() {
		boolean today = TimeUtils.isToday(refreshTime);
		if(!today) {
			this.refreshTime = System.currentTimeMillis();
			this.activityScore = 0;
			this.rewardActivityOil = 0;
			this.rewardActivityRare = 0;
			this.rewardActivitySteel = 0;
			this.rewardActivityGold = 0;
			this.scienceOilCount = 0;
			this.scienceRareCount = 0;
			this.scienceSteelCount = 0;
			this.scienceGoldCount = 0;
			this.scienceDiamondCount = 0;
			this.commonInfo.clear();
			return true;
		}
		return false;
	}
	
	/**
	 * 清空数据
	 */
	public void clean() {
		this.allianceId = 0;
		this.activityScore = 0;
		this.rewardActivityOil = 0;
		this.rewardActivityRare = 0;
		this.rewardActivitySteel = 0;
		this.rewardActivityGold = 0;
		this.boxIds.clear();
		this.exerciseStartTime = 0;
		this.exerciseRewards.clear();
		this.commonInfo.clear();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		jbaseData.put("playerId", playerId);
		jbaseData.put("allianceId", allianceId);
		jbaseData.put("exerciseStartTime", exerciseStartTime);
		jbaseData.put("activityScore", activityScore);
		jbaseData.put("rewardActivityOil", rewardActivityOil);
		jbaseData.put("rewardActivityRare", rewardActivityRare);
		jbaseData.put("rewardActivitySteel", rewardActivitySteel);
		jbaseData.put("rewardActivityGold", rewardActivityGold);
		jbaseData.put("scienceOilCount", scienceOilCount);
		jbaseData.put("scienceRareCount", scienceRareCount);
		jbaseData.put("scienceSteelCount", scienceSteelCount);
		jbaseData.put("scienceGoldCount", scienceGoldCount);
		jbaseData.put("scienceDiamondCount", scienceDiamondCount);
		jbaseData.put("refreshTime", refreshTime);
		
		List<JBaseData> boxIdsJBaseList = new ArrayList<JBaseData>();
		for (Map.Entry entry : boxIds.entrySet()) {
			boxIdsJBaseList.add(((JBaseTransform) entry.getValue()).toJBaseData());
		}
		jbaseData.put("boxIds", boxIdsJBaseList);
		
		List<JBaseData> exerciseRewardsJBaseList = new ArrayList<JBaseData>();
		for (Map.Entry entry : exerciseRewards.entrySet()) {
			exerciseRewardsJBaseList.add(((JBaseTransform) entry.getValue()).toJBaseData());
		}
		jbaseData.put("exerciseRewards", exerciseRewardsJBaseList);
		
		List<JBaseData> commonInfoJBaseList = new ArrayList<JBaseData>();
		for (Map.Entry entry : commonInfo.entrySet()) {
			commonInfoJBaseList.add(((JBaseTransform) entry.getValue()).toJBaseData());
		}
		jbaseData.put("commonInfo", commonInfoJBaseList);
		return jbaseData;
	}

	@Override
	public void fromJBaseData(JBaseData jBaseData) {
		this.playerId = jBaseData.getLong("playerId",0);
		this.allianceId = jBaseData.getLong("allianceId",0);
		this.exerciseStartTime = jBaseData.getLong("exerciseStartTime",0);
		this.activityScore = jBaseData.getInt("activityScore",0);
		this.rewardActivityOil = jBaseData.getInt("rewardActivityOil",0);
		this.rewardActivityRare = jBaseData.getInt("rewardActivityRare",0);
		this.rewardActivitySteel = jBaseData.getInt("rewardActivitySteel",0);
		this.rewardActivityGold = jBaseData.getInt("rewardActivityGold",0);
		this.scienceOilCount = jBaseData.getInt("scienceOilCount",0);
		this.scienceRareCount = jBaseData.getInt("scienceRareCount",0);
		this.scienceSteelCount = jBaseData.getInt("scienceSteelCount",0);
		this.scienceGoldCount = jBaseData.getInt("scienceGoldCount",0);
		this.scienceDiamondCount = jBaseData.getInt("scienceDiamondCount",0);
		this.refreshTime = jBaseData.getLong("refreshTime",0);
		
		List<JBaseData> boxIdsJBaseData = jBaseData.getSeqBaseData("boxIds");
		for(JBaseData jBaseData2 : boxIdsJBaseData) {
			AwardInfo awardInfo = new AwardInfo();
			awardInfo.fromJBaseData(jBaseData2);
			this.boxIds.put(awardInfo.getId(), awardInfo);
		}
		
		List<JBaseData> exerciseRewardsJBaseData = jBaseData.getSeqBaseData("exerciseRewards");
		for(JBaseData jBaseData2 : exerciseRewardsJBaseData) {
			ExerciseInfo exerciseInfo = new ExerciseInfo();
			exerciseInfo.fromJBaseData(jBaseData2);
			this.exerciseRewards.put(exerciseInfo.getId(), exerciseInfo);
		}
		
		List<JBaseData> commonInfoJBaseData = jBaseData.getSeqBaseData("commonInfo");
		for(JBaseData jBaseData2 : commonInfoJBaseData) {
			AllianceGoods allianceGoods = new AllianceGoods();
			allianceGoods.fromJBaseData(jBaseData2);
			this.commonInfo.put(allianceGoods.getId(), allianceGoods);
		}
	}

}
