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
 * 联盟建筑详细信息
 * @author jacky.jiang
 *
 */
public class AllianceExt extends AbstractEntity<Long> implements JBaseTransform{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8035427746376068975L;

	/**
	 * 联盟id
	 */
	private long allianceId;
	
	/**
	 * 联盟建筑信息
	 */
	private Map<String, AllianceBuild> buildInfo = new ConcurrentHashMap<String, AllianceBuild>();
	
	/**
	 * 联盟科技
	 */
	private Map<String, AllianceScience> technology = new ConcurrentHashMap<String, AllianceScience>();
	
	/**
	 * 联盟宝箱
	 */
	private Map<Long, AllianceBox>  allianceBoxs = new ConcurrentHashMap<Long, AllianceBox>();
	
	/**
	 * 战事奖励
	 */
	private Map<Integer, AllianceBenifit>  benifit = new ConcurrentHashMap<Integer, AllianceBenifit>();
	
	/**
	 * 联盟活跃任务完成情况
	 */
	private Map<Integer, AllianceActivityQuest>  activeQuest = new ConcurrentHashMap<Integer, AllianceActivityQuest>();
	
	/**
	 * 联盟活跃度
	 */
	private AllianceActivity activity = new AllianceActivity();
	
	/**
	 * 超级矿信息
	 */
	private SuperMineInfo superMine = new SuperMineInfo();
	
	/**
	 * 珍品信息
	 */
	private Map<Integer,AllianceGoods> treasureInfo = new ConcurrentHashMap<Integer, AllianceGoods>();
	
	/**
	 * 联盟宝箱等级
	 */
	private int allianceBoxLevel;
	
	/**
	 * 经验
	 */
	private int exp;
	
	/**
	 * 刷新时间
	 */
	private long refreshTime;

	public long getAllianceId() {
		return allianceId;
	}

	public void setAllianceId(long allianceId) {
		this.allianceId = allianceId;
	}
	

	public Map<String, AllianceBuild> getBuildInfo() {
		return buildInfo;
	}

	public void setBuildInfo(Map<String, AllianceBuild> buildInfo) {
		this.buildInfo = buildInfo;
	}

	public Map<String, AllianceScience> getTechnology() {
		return technology;
	}

	public void setTechnology(Map<String, AllianceScience> technology) {
		this.technology = technology;
	}

	public Map<Integer, AllianceBenifit> getBenifit() {
		return benifit;
	}

	public void setBenifit(Map<Integer, AllianceBenifit> benifit) {
		this.benifit = benifit;
	}

	public AllianceActivity getActivity() {
		return activity;
	}

	public void setActivity(AllianceActivity activity) {
		this.activity = activity;
	}

	public int getAllianceBoxLevel() {
		return allianceBoxLevel;
	}

	public void setAllianceBoxLevel(int allianceBoxLevel) {
		this.allianceBoxLevel = allianceBoxLevel;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public Map<Long, AllianceBox> getAllianceBoxs() {
		return allianceBoxs;
	}

	public void setAllianceBoxs(Map<Long, AllianceBox> allianceBoxs) {
		this.allianceBoxs = allianceBoxs;
	}

	public Map<Integer, AllianceActivityQuest> getActiveQuest() {
		return activeQuest;
	}

	public void setActiveQuest(Map<Integer, AllianceActivityQuest> activeQuest) {
		this.activeQuest = activeQuest;
	}

	public SuperMineInfo getSuperMine() {
		return superMine;
	}

	public void setSuperMine(SuperMineInfo superMine) {
		this.superMine = superMine;
	}

	@Override
	public Long getId() {
		return allianceId;
	}

	@Override
	public void setId(Long k) {
		this.allianceId = k;
	}
	
	public long getRefreshTime() {
		return refreshTime;
	}

	public void setRefreshTime(long refreshTime) {
		this.refreshTime = refreshTime;
	}

	public Map<Integer, AllianceGoods> getTreasureInfo() {
		return treasureInfo;
	}

	public void setTreasureInfo(Map<Integer, AllianceGoods> treasureInfo) {
		this.treasureInfo = treasureInfo;
	}

	public synchronized boolean refresh() {
		boolean today = TimeUtils.isToday(refreshTime);
		if(!today) {
			this.refreshTime = System.currentTimeMillis();
			this.activity.refresh();
			this.activeQuest.clear();
			return true;
		}
		return false;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		jbaseData.put("allianceId", allianceId);
		jbaseData.put("refreshTime", refreshTime);
		jbaseData.put("allianceBoxLevel", allianceBoxLevel);
		jbaseData.put("exp", exp);
		List<JBaseData> buildInfoJBaseList = new ArrayList<JBaseData>();
		for (Map.Entry entry : buildInfo.entrySet()) {
			buildInfoJBaseList.add(((JBaseTransform) entry.getValue()).toJBaseData());
		}
		jbaseData.put("buildInfo", buildInfoJBaseList);
		
		List<JBaseData> technologyJBaseList = new ArrayList<JBaseData>();
		for (Map.Entry entry : technology.entrySet()) {
			technologyJBaseList.add(((JBaseTransform) entry.getValue()).toJBaseData());
		}
		jbaseData.put("technology", technologyJBaseList);
		
		List<JBaseData> allianceBoxJBaseList = new ArrayList<JBaseData>();
		for (Map.Entry entry : allianceBoxs.entrySet()) {
			allianceBoxJBaseList.add(((JBaseTransform) entry.getValue()).toJBaseData());
		}
		jbaseData.put("allianceBoxs", allianceBoxJBaseList);
		
		List<JBaseData> benifitJBaseList = new ArrayList<JBaseData>();
		for (Map.Entry entry : benifit.entrySet()) {
			benifitJBaseList.add(((JBaseTransform) entry.getValue()).toJBaseData());
		}
		jbaseData.put("benifit", benifitJBaseList);
		
		List<JBaseData> activeQuestJBaseList = new ArrayList<JBaseData>();
		for (Map.Entry entry : activeQuest.entrySet()) {
			activeQuestJBaseList.add(((JBaseTransform) entry.getValue()).toJBaseData());
		}
		jbaseData.put("activeQuest", activeQuestJBaseList);
		
		List<JBaseData> treasureInfoJBaseList = new ArrayList<JBaseData>();
		for (Map.Entry entry : treasureInfo.entrySet()) {
			treasureInfoJBaseList.add(((JBaseTransform) entry.getValue()).toJBaseData());
		}
		jbaseData.put("treasureInfo", treasureInfoJBaseList);
		
		jbaseData.put("activity", activity.toJBaseData());
		jbaseData.put("superMine", superMine.toJBaseData());
		return jbaseData;
	}

	@Override
	public void fromJBaseData(JBaseData jBaseData) {
		this.allianceId = jBaseData.getLong("allianceId", 0);
		this.refreshTime = jBaseData.getLong("refreshTime", 0);
		this.allianceBoxLevel = jBaseData.getInt("allianceBoxLevel", 0);
		this.exp = jBaseData.getInt("exp", 0);
		List<JBaseData> buildInfoJBaseData = jBaseData.getSeqBaseData("buildInfo");
		for(JBaseData jBaseData2 : buildInfoJBaseData) {
			AllianceBuild allianceBuild = new AllianceBuild();
			allianceBuild.fromJBaseData(jBaseData2);
			this.buildInfo.put(allianceBuild.getId(), allianceBuild);
		}
		
		List<JBaseData> technologyJBaseData = jBaseData.getSeqBaseData("technology");
		for(JBaseData jBaseData2 : technologyJBaseData) {
			AllianceScience allianceScience = new AllianceScience();
			allianceScience.fromJBaseData(jBaseData2);
			this.technology.put(allianceScience.getId(), allianceScience);
		}
		
		List<JBaseData> allianceBoxsJBaseData = jBaseData.getSeqBaseData("allianceBoxs");
		for(JBaseData jBaseData2 : allianceBoxsJBaseData) {
			AllianceBox allianceBox = new AllianceBox();
			allianceBox.fromJBaseData(jBaseData2);
			this.allianceBoxs.put(allianceBox.getId(), allianceBox);
		}
		
		List<JBaseData> benifitJBaseData = jBaseData.getSeqBaseData("benifit");
		for(JBaseData jBaseData2 : benifitJBaseData) {
			AllianceBenifit allianceBenifit = new AllianceBenifit();
			allianceBenifit.fromJBaseData(jBaseData2);
			this.benifit.put(allianceBenifit.getItemId(), allianceBenifit);
		}
		
		List<JBaseData> activiQuestJBaseData = jBaseData.getSeqBaseData("activeQuest");
		for(JBaseData jBaseData2 : activiQuestJBaseData) {
			AllianceActivityQuest allianceActivityQuest = new AllianceActivityQuest();
			allianceActivityQuest.fromJBaseData(jBaseData2);
			this.activeQuest.put(allianceActivityQuest.getQuestId(), allianceActivityQuest);
		}
		
		List<JBaseData> treasureInfoJBaseData = jBaseData.getSeqBaseData("treasureInfo");
		for(JBaseData jBaseData2 : treasureInfoJBaseData) {
			AllianceGoods allianceGoods = new AllianceGoods();
			allianceGoods.fromJBaseData(jBaseData2);
			this.treasureInfo.put(allianceGoods.getId(), allianceGoods);
		}
		
		AllianceActivity activity = new AllianceActivity();
		JBaseData activityJBaseData = jBaseData.getBaseData("activity");
		activity.fromJBaseData(activityJBaseData);
		this.activity = activity;
		
		SuperMineInfo superMine = new SuperMineInfo();
		JBaseData superMineJBaseData = jBaseData.getBaseData("superMine");
		superMine.fromJBaseData(superMineJBaseData);
		this.superMine = superMine;
	}
}
