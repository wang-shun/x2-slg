package com.xgame.logic.server.game.alliance.enity;

import org.apache.commons.lang3.StringUtils;

import com.xgame.logic.server.core.db.cache.entity.AbstractEntity;
import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;
import com.xgame.logic.server.core.utils.JsonUtil;
import com.xgame.logic.server.core.utils.collection.ConcurrentHashSet;
import com.xgame.utils.TimeUtils;

/**
 * 玩家联盟信息
 * @author jacky.jiang
 *
 */
public class PlayerAlliance extends AbstractEntity<Long> implements JBaseTransform {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1615063209691733197L;

	/**
	 * 玩家id
	 */
	private Long playerId;
	
	/**
	 * 联盟id
	 */
	private long allianceId;
	
	/**
	 * 队伍编号
	 */
	private String teamId;
	
	/**
	 * 联盟阶级
	 */
	private int allianceRank;
	
	/**
	 * 首次加入联盟
	 */
	private boolean firstRewards = true;
	
	/**
	 * 联盟贡献
	 */
	private int donate;
	
	/**
	 * 今日贡献值（跨军团不清空）
	 */
	private int dayDonate;
	
	/**
	 * 周贡献
	 */
	private int weekDonate;
	
	/**
	 * 历史贡献排行
	 */
	private int historyDonate;
	
	
	/**
	 * 今日帮助获得贡献值
	 */
	private int helpDonate;
	
	
	/**
	 * 石油捐献次数
	 */
	private int oilDonateCount;
	
	/**
	 * 稀土捐献次数
	 */
	private int rareDonateCount;
	
	/**
	 * 钢铁捐献次数
	 */
	private int steelDonateCount;
	
	/**
	 * 黄金捐献次数
	 */
	private int moneyDonateCount;
	
	/**
	 * 钻石捐献次数
	 */
	private int diamondDonateCount;
	
	/**
	 * 击杀数量
	 */
	private int killNum;
	
	/**
	 * 联盟权限
	 */
	private AlliancePermission alliancePermission = new AlliancePermission();
	
	/**
	 * 申请列表
	 */
	private ConcurrentHashSet<Long> applyList = new ConcurrentHashSet<>();
	
	/**
	 * 邀请列表
	 */
	private ConcurrentHashSet<Long> inviteList = new ConcurrentHashSet<>();
	
	/**
	 * 上一次刷新时间
	 */
	private long refreshTime = System.currentTimeMillis();
	
	/**
	 * 周刷新时间
	 */
	private long weekRefreshTime = System.currentTimeMillis();
	
	/**
	 * 加入联盟时间
	 */
	private long joinTime;
	
	
	/**
	 * 联盟申请阅读查看时间
	 */
	private long applyReadTime;
	
	
	@Override
	public Long getId() {
		return playerId;
	}
	
	@Override
	public void setId(Long k) {
		this.playerId = k;
	}

	public Long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}

	public long getAllianceId() {
		return allianceId;
	}

	public void setAllianceId(long allianceId) {
		this.allianceId = allianceId;
	}

	public ConcurrentHashSet<Long> getApplyList() {
		return applyList;
	}

	public void setApplyList(ConcurrentHashSet<Long> applyList) {
		this.applyList = applyList;
	}

	public ConcurrentHashSet<Long> getInviteList() {
		return inviteList;
	}

	public void setInviteList(ConcurrentHashSet<Long> inviteList) {
		this.inviteList = inviteList;
	}

	public boolean isFirstRewards() {
		return firstRewards;
	}

	public void setFirstRewards(boolean firstRewards) {
		this.firstRewards = firstRewards;
	}

	public AlliancePermission getAlliancePermission() {
		return alliancePermission;
	}

	public void setAlliancePermission(AlliancePermission alliancePermission) {
		this.alliancePermission = alliancePermission;
	}

	public int getOilDonateCount() {
		return oilDonateCount;
	}

	public void setOilDonateCount(int oilDonateCount) {
		this.oilDonateCount = oilDonateCount;
	}

	public int getRareDonateCount() {
		return rareDonateCount;
	}

	public void setRareDonateCount(int rareDonateCount) {
		this.rareDonateCount = rareDonateCount;
	}

	public int getSteelDonateCount() {
		return steelDonateCount;
	}

	public void setSteelDonateCount(int steelDonateCount) {
		this.steelDonateCount = steelDonateCount;
	}

	public int getMoneyDonateCount() {
		return moneyDonateCount;
	}

	public void setMoneyDonateCount(int moneyDonateCount) {
		this.moneyDonateCount = moneyDonateCount;
	}

	public int getDiamondDonateCount() {
		return diamondDonateCount;
	}

	public void setDiamondDonateCount(int diamondDonateCount) {
		this.diamondDonateCount = diamondDonateCount;
	}

	public long getRefreshTime() {
		return refreshTime;
	}

	public void setRefreshTime(long refreshTime) {
		this.refreshTime = refreshTime;
	}

	public int getDonate() {
		return donate;
	}

	public int getDayDonate() {
		return dayDonate;
	}

	public void setDayDonate(int dayDonate) {
		this.dayDonate = dayDonate;
	}

	public int getAllianceRank() {
		return allianceRank;
	}

	public void setAllianceRank(int allianceRank) {
		this.allianceRank = allianceRank;
	}

	public int getHistoryDonate() {
		return historyDonate;
	}

	public void setHistoryDonate(int historyDonate) {
		this.historyDonate = historyDonate;
	}

	public long getJoinTime() {
		return joinTime;
	}

	public void setJoinTime(long joinTime) {
		this.joinTime = joinTime;
	}
	
	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}
	
	public int getHelpDonate() {
		return helpDonate;
	}

	public void setHelpDonate(int helpDonate) {
		this.helpDonate = helpDonate;
	}
	
	public void addDonate(int num) {
		this.donate = this.donate + num;
		this.dayDonate = this.dayDonate + num;
		this.weekDonate = this.weekDonate + num; 
		this.historyDonate = this.historyDonate + num;
	}
	
	public void useDonate(int num){
		this.donate = this.donate - num;
	}

	public synchronized boolean refresh() {
		boolean today = TimeUtils.isToday(refreshTime);
		if(!today) {
			dayDonate = 0;
			oilDonateCount = 0;
			rareDonateCount = 0;
			steelDonateCount = 0;
			moneyDonateCount = 0;
			diamondDonateCount = 0;
			helpDonate = 0;
			refreshTime = System.currentTimeMillis();
		
			if(weekRefreshTime + TimeUtils.WEEK_MILLIS < System.currentTimeMillis()) {
				weekRefreshTime = TimeUtils.getMondayTimeMillis();
				weekDonate = 0;
			}
			
			return true;
		}
		return false;
	}
	
	public int getKillNum() {
		return killNum;
	}

	public void setKillNum(int killNum) {
		this.killNum = killNum;
	}
	
	public int getWeekDonate() {
		return weekDonate;
	}

	public void setWeekDonate(int weekDonate) {
		this.weekDonate = weekDonate;
	}
	
	public long getApplyReadTime() {
		return applyReadTime;
	}

	public void setApplyReadTime(long applyReadTime) {
		this.applyReadTime = applyReadTime;
	}
	
	
	public long getWeekRefreshTime() {
		return weekRefreshTime;
	}

	public void setWeekRefreshTime(long weekRefreshTime) {
		this.weekRefreshTime = weekRefreshTime;
	}
	

	public void addHelpDonate(int num) {
		this.donate = this.donate + num;
		this.dayDonate = this.dayDonate + num;
		this.helpDonate = this.helpDonate + num;
		this.weekDonate = this.weekDonate + num; 
		this.historyDonate = this.historyDonate + num;
	}
	
	public AlliancePermission newLeaderPermission() {
		AlliancePermission alliancePermission = new AlliancePermission();
		alliancePermission.setAssignAllianceReward(true);
		alliancePermission.setAssignTeamLeader(true);
		alliancePermission.setCreateAllianceBuild(true);
		alliancePermission.setDealApply(true);
		alliancePermission.setInvite(true);
		alliancePermission.setKickmember(true);
		alliancePermission.setManagerAllianceBuild(true);
		alliancePermission.setManagerMemeberLevel(true);
		alliancePermission.setRecruit(true);
		alliancePermission.setSendMail(true);
		return alliancePermission;
	}
	
	public void clean() {
		alliancePermission.setAssignAllianceReward(false);
		alliancePermission.setAssignTeamLeader(false);
		alliancePermission.setCreateAllianceBuild(false);
		alliancePermission.setDealApply(false);
		alliancePermission.setInvite(false);
		alliancePermission.setKickmember(false);
		alliancePermission.setManagerAllianceBuild(false);
		alliancePermission.setManagerMemeberLevel(false);
		alliancePermission.setRecruit(false);
		alliancePermission.setSendMail(false);
		
		this.allianceId = 0;
		this.setAllianceRank(0);
		this.setTeamId("");
		this.setDayDonate(0);
		this.setWeekDonate(0);
		this.setHistoryDonate(0);
	}
	
	public void changeLeader() {
		alliancePermission.setAssignAllianceReward(false);
		alliancePermission.setAssignTeamLeader(false);
		alliancePermission.setCreateAllianceBuild(false);
		alliancePermission.setDealApply(false);
		alliancePermission.setInvite(false);
		alliancePermission.setKickmember(false);
		alliancePermission.setManagerAllianceBuild(false);
		alliancePermission.setManagerMemeberLevel(false);
		alliancePermission.setRecruit(false);
		alliancePermission.setSendMail(false);
		this.setAllianceRank(1);
	}
	
	@Override
	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		jbaseData.put("playerId", playerId);
		jbaseData.put("allianceId", allianceId);
		jbaseData.put("teamId", teamId);
		jbaseData.put("allianceRank", allianceRank);
		jbaseData.put("firstRewards", firstRewards);
		jbaseData.put("donate", donate);
		jbaseData.put("dayDonate", dayDonate);
		jbaseData.put("weekDonate", weekDonate);
		jbaseData.put("historyDonate", historyDonate);
		jbaseData.put("helpDonate", helpDonate);
		jbaseData.put("oilDonateCount", oilDonateCount);
		jbaseData.put("rareDonateCount", rareDonateCount);
		jbaseData.put("steelDonateCount", steelDonateCount);
		jbaseData.put("moneyDonateCount", moneyDonateCount);
		jbaseData.put("diamondDonateCount", diamondDonateCount);
		jbaseData.put("killNum", killNum);
		jbaseData.put("alliancePermission", ((JBaseTransform) alliancePermission).toJBaseData());
		
		jbaseData.put("refreshTime", refreshTime);
		jbaseData.put("weekRefreshTime", weekRefreshTime);
		jbaseData.put("joinTime", joinTime);
		jbaseData.put("applyReadTime", applyReadTime);
		
		jbaseData.put("applyList", JsonUtil.toJSON(this.applyList));
		jbaseData.put("inviteList", JsonUtil.toJSON(this.inviteList));
		
		return jbaseData;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void fromJBaseData(JBaseData jBaseData) {
		this.playerId = jBaseData.getLong("playerId",0);
		this.allianceId = jBaseData.getLong("allianceId",0);
		this.teamId = jBaseData.getString("teamId","");
		this.allianceRank = jBaseData.getInt("allianceRank",0);
		this.firstRewards = jBaseData.getBoolean("firstRewards", false);
		this.donate = jBaseData.getInt("donate",0);
		this.dayDonate = jBaseData.getInt("dayDonate",0);
		this.weekDonate = jBaseData.getInt("weekDonate",0);
		this.historyDonate = jBaseData.getInt("historyDonate",0);
		this.helpDonate = jBaseData.getInt("helpDonate",0);
		this.oilDonateCount = jBaseData.getInt("oilDonateCount",0);
		this.rareDonateCount = jBaseData.getInt("rareDonateCount",0);
		this.steelDonateCount = jBaseData.getInt("steelDonateCount",0);
		this.moneyDonateCount = jBaseData.getInt("moneyDonateCount",0);
		this.diamondDonateCount = jBaseData.getInt("diamondDonateCount",0);
		this.killNum = jBaseData.getInt("killNum",0);
		
		JBaseData jBaseData2 = jBaseData.getBaseData("alliancePermission");
		AlliancePermission alliancePermission = new AlliancePermission();
		alliancePermission.fromJBaseData(jBaseData2);
		this.alliancePermission = alliancePermission;
		
		this.refreshTime = jBaseData.getLong("refreshTime",0);
		this.weekRefreshTime = jBaseData.getLong("weekRefreshTime",0);
		this.joinTime = jBaseData.getLong("joinTime",0);
		this.applyReadTime = jBaseData.getLong("applyReadTime",0);
		
		String applyListStr = jBaseData.getString("applyList", "");
		if(!StringUtils.isEmpty(applyListStr)) {
			this.applyList.addAll(JsonUtil.fromJSON(applyListStr, ConcurrentHashSet.class));
		}
	
		String inviteListStr = jBaseData.getString("inviteList", "");
		if(!StringUtils.isEmpty(inviteListStr)) {
			this.inviteList.addAll(JsonUtil.fromJSON(inviteListStr, ConcurrentHashSet.class));
		}
	}

}
