package com.xgame.logic.server.game.alliance.enity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;

import com.xgame.logic.server.core.db.cache.entity.AbstractEntity;
import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;
import com.xgame.logic.server.core.utils.JsonUtil;
import com.xgame.logic.server.core.utils.collection.ConcurrentHashSet;
import com.xgame.logic.server.game.alliance.constant.AllianceConstant;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.world.entity.territory.AllianceTerritory;
import com.xgame.utils.TimeUtils;

/**
 * 联盟id
 * @author jacky.jiang
 *
 */
public class Alliance extends AbstractEntity<Long> implements JBaseTransform{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2206644974824767061L;

	/**
	 * 联盟id
	 */
	private Long allianceId;
	
	/**
	 * 联盟名称
	 */
	private String allianceName;
	
	/**
	 * 联盟简称
	 */
	private String abbr;
	
	/**
	 * 旗帜
	 */
	private String icon;
	
	/**
	 * 修改旗帜时间
	 */
	private long reicon;
	
	/**
	 * 修改联盟简称
	 */
	private long reabbr;
	
	/**
	 * 修改名称
	 */
	private long rename;
	
	/**
	 * 宣言（json结构有内容和作者两不分构成）
	 */
	private String announce;
	
	/**
	 * 语言
	 */
	private String language;
	
	/**
	 * 国家
	 */
	private String country;
	
	/**
	 * 战力
	 */
	private long fightPower;
	
	/**
	 * 资金
	 */
	private long cash;
	
	/**
	 * 等级
	 */
	private int level;
	
	/**
	 * 是否开启自动审批
	 */
	private boolean auto;
	
	/**
	 * 盟主id
	 */
	private long leaderId;
	
	/**
	 * 
	 */
	private String leaderName;
	
	/**
	 * 创建时间
	 */
	private int createTime;
	
	/**
	 * 当前人数
	 */
	private int curMemeber;
	
	/**
	 * 联盟最大人数 
	 */
	private int maxMemeber;
	
	// 成员信息
	private ConcurrentHashSet<Long> rankOne = new ConcurrentHashSet<>();
	private ConcurrentHashSet<Long> rankTwo = new ConcurrentHashSet<>();
	private ConcurrentHashSet<Long> rankThree = new ConcurrentHashSet<>();
	private ConcurrentHashSet<Long> rankFour = new ConcurrentHashSet<>();
	private ConcurrentHashSet<Long> rankFive = new ConcurrentHashSet<>();
	
	/**
	 * 官员1
	 */
	private long office1Player;
	
	/**
	 * 官员2
	 */
	private long office2Player;
	
	/**
	 * 官员3
	 */
	private long office3Player;
	
	/**
	 * 官员4
	 */
	private long office4Player;
	
	/**
	 * 战队信息
	 */
	private Map<String, AllianceTeam> teamMap = new HashMap<>();
	
	/**
	 * 联盟申请列表
	 * <playerId> <AllianceApply>
	 */
	private Map<Long, AllianceApply> applyList = new ConcurrentHashMap<Long, AllianceApply>();
	
	/**
	 *  初始化联盟头衔名称
	 */
	private AllianceTitle allianceTitle = new AllianceTitle();
	
	/**
	 * 公告结束时间
	 */
	private long noticeEndTime;
	
	/**
	 * 邮件发送次数
	 */
	private int count;
	
	/**
	 * 刷新时间
	 */
	private long refreshTime;
	
	/**
	 * 联盟领地信息
	 */
	private AllianceTerritory allianceTerritory = new AllianceTerritory();
	
	/**
	 * 建筑列表
	 */
	//private List<String> buildingList = new ArrayList<String>();
	
	/**
	 * 援建列表
	 */
	private List<Long> helpList = new ArrayList<Long>();
	
	
	public String getAllianceName() {
		return allianceName;
	}

	public void setAllianceName(String allianceName) {
		this.allianceName = allianceName;
	}

	public String getAbbr() {
		return abbr;
	}

	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public long getReicon() {
		return reicon;
	}

	public void setReicon(long reicon) {
		this.reicon = reicon;
	}

	public long getReabbr() {
		return reabbr;
	}

	public void setReabbr(long reabbr) {
		this.reabbr = reabbr;
	}

	public long getRename() {
		return rename;
	}

	public void setRename(long rename) {
		this.rename = rename;
	}

	public String getAnnounce() {
		return announce;
	}

	public void setAnnounce(String announce) {
		this.announce = announce;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public long getFightPower() {
		return fightPower;
	}

	public void setFightPower(long fightPower) {
		this.fightPower = fightPower;
	}

	public long getCash() {
		return cash;
	}

	public void setCash(long cash) {
		this.cash = cash;
	}
	
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public boolean isAuto() {
		return auto;
	}

	public void setAuto(boolean auto) {
		this.auto = auto;
	}

	public long getLeaderId() {
		return leaderId;
	}

	public void setLeaderId(long leaderId) {
		this.leaderId = leaderId;
	}

	public int getCreateTime() {
		return createTime;
	}

	public void setCreateTime(int createTime) {
		this.createTime = createTime;
	}

	public int getCurMemeber() {
		return curMemeber;
	}

	public void setCurMemeber(int curMemeber) {
		this.curMemeber = curMemeber;
	}

	public void setAllianceId(Long allianceId) {
		this.allianceId = allianceId;
	}
	
	public Long getAllianceId() {
		return this.allianceId;
	}
	
	public Map<Long, AllianceApply> getApplyList() {
		return applyList;
	}

	public void setApplyList(Map<Long, AllianceApply> applyList) {
		this.applyList = applyList;
	}
	
	public AllianceApply getPlayerApply(long playerId) {
		return this.applyList.get(playerId);
	}
	
	public String getLeaderName() {
		return leaderName;
	}

	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}

	public int getMaxMemeber() {
		return maxMemeber;
	}

	public void setMaxMemeber(int maxMemeber) {
		this.maxMemeber = maxMemeber;
	}

	public ConcurrentHashSet<Long> getRankOne() {
		return rankOne;
	}

	public void setRankOne(ConcurrentHashSet<Long> rankOne) {
		this.rankOne = rankOne;
	}

	public ConcurrentHashSet<Long> getRankTwo() {
		return rankTwo;
	}

	public void setRankTwo(ConcurrentHashSet<Long> rankTwo) {
		this.rankTwo = rankTwo;
	}

	public ConcurrentHashSet<Long> getRankThree() {
		return rankThree;
	}

	public void setRankThree(ConcurrentHashSet<Long> rankThree) {
		this.rankThree = rankThree;
	}

	public ConcurrentHashSet<Long> getRankFour() {
		return rankFour;
	}

	public void setRankFour(ConcurrentHashSet<Long> rankFour) {
		this.rankFour = rankFour;
	}

	public ConcurrentHashSet<Long> getRankFive() {
		return rankFive;
	}

	public void setRankFive(ConcurrentHashSet<Long> rankFive) {
		this.rankFive = rankFive;
	}

	public List<Long> getHelpList() {
		return helpList;
	}

	public void setHelpList(List<Long> helpList) {
		this.helpList = helpList;
	}
	
	public void addHelpList(Long helpId) {
		this.helpList.add(helpId);
	}

	@Override
	public Long getId() {
		return allianceId;
	}

	@Override
	public void setId(Long k) {
		this.allianceId = k;
	}
	
	public Set<Long> getAllianceMember() {
		Set<Long> members = new HashSet<>();
		members.addAll(this.rankOne);
		members.addAll(this.rankTwo);
		members.addAll(this.rankThree);
		members.addAll(this.rankFour);
		members.addAll(this.rankFive);
		members.add(leaderId);
		return members;
	}
	
	/**
	 * 当前成员数量
	 * @return
	 */
	public int currentMemberSize() {
		return this.getAllianceMember().size();
	}
	
	/**
	 * 判断玩家是否是R5
	 * @param playerId
	 * @return
	 */
	public boolean checkPlayerR5(long playerId) {
		return this.rankFive.contains(playerId);
	}
	
	/**
	 * 判断玩家是否是r4
	 * @param playerId
	 * @return
	 */
	public boolean checkPlayerR4(long playerId) {
		return this.rankFour.contains(playerId);
	}
	
	/**
	 * 判断玩家是否在联盟当中
	 * @param playerId
	 * @return
	 */
	public boolean playerInAlliance(long playerId) {
		
		if(this.rankOne.contains(playerId)) {
			return true;
		}
		
		if(this.rankTwo.contains(playerId)) {
			return true;
		}
		
		if(this.rankThree.contains(playerId)) {
			return true;
		}
		
		if(this.rankFour.contains(playerId)) {
			return true;
		}
		
		if(this.rankFive.contains(playerId)) {
			return true;
		}
		
		if(leaderId == playerId) {
			return true;
		}
		
		return false;
	}
	
	public long getOffice1Player() {
		return office1Player;
	}

	public void setOffice1Player(long office1Player) {
		this.office1Player = office1Player;
	}

	public long getOffice2Player() {
		return office2Player;
	}

	public void setOffice2Player(long office2Player) {
		this.office2Player = office2Player;
	}

	public long getOffice3Player() {
		return office3Player;
	}

	public void setOffice3Player(long office3Player) {
		this.office3Player = office3Player;
	}

	public long getOffice4Player() {
		return office4Player;
	}

	public void setOffice4Player(long office4Player) {
		this.office4Player = office4Player;
	}

	public Map<String, AllianceTeam> getTeamMap() {
		return teamMap;
	}

	public void setTeamMap(Map<String, AllianceTeam> teamMap) {
		this.teamMap = teamMap;
	}

	public AllianceTitle getAllianceTitle() {
		return allianceTitle;
	}

	public void setAllianceTitle(AllianceTitle allianceTitle) {
		this.allianceTitle = allianceTitle;
	}
	
	public AllianceTeam getAllianceTeam(String teamId) {
		return this.teamMap.get(teamId);
	}
	
	public long getNoticeEndTime() {
		return noticeEndTime;
	}

	public void setNoticeEndTime(long noticeEndTime) {
		this.noticeEndTime = noticeEndTime;
	}

	/**
	 * 添加成员
	 * @param playerId
	 * @param rank
	 */
	public void addAllianceMemeber(long playerId, int rank) {
		if(rank == AllianceConstant.RANK_ONE) {
			rankOne.add(playerId);
		} else if(rank == AllianceConstant.RANK_TWO) {
			rankTwo.add(playerId);
		} else if(rank == AllianceConstant.RANK_THREE) {
			rankThree.add(playerId);
		} else if(rank == AllianceConstant.RANK_FOUR) {
			rankFour.add(playerId);
		} else if(rank == AllianceConstant.RANK_FIVE) {
			rankFive.add(playerId);
		}
	}
	
	public void removeRankMemeber(long playerId) {
		rankOne.remove(playerId);
		rankTwo.remove(playerId);
		rankThree.remove(playerId);
		rankFour.remove(playerId);
		rankFive.remove(playerId);
	}
	
	public void removeAllianceMember(long playerId) {
		rankOne.remove(playerId);
		rankTwo.remove(playerId);
		rankThree.remove(playerId);
		rankFour.remove(playerId);
		rankFive.remove(playerId);
		
		if(office1Player == playerId) {
			office1Player = 0;
		}
		
		if(office2Player == playerId) {
			office2Player = 0;
		}
		
		if(office3Player == playerId) {
			office3Player = 0;
		}
		
		if(office4Player == playerId) {
			office4Player = 0;
		}
	}
	
	public void exitAlliance(long playerId) {
		rankOne.remove(playerId);
		rankTwo.remove(playerId);
		rankThree.remove(playerId);
		rankFour.remove(playerId);
		rankFive.remove(playerId);
		
		if(office1Player == playerId) {
			office1Player = 0;
		}
		
		if(office2Player == playerId) {
			office2Player = 0;
		}
		
		if(office3Player == playerId) {
			office3Player = 0;
		}
		
		if(office4Player == playerId) {
			office4Player = 0;
		}
		
		for(AllianceTeam allianceTeam : teamMap.values()) {
			if(allianceTeam.getTeamLeaderId() == playerId) {
				allianceTeam.setTeamLeaderId(0);
				allianceTeam.getTeamMemberIds().remove(playerId);
			}
		}
	}
	
	/**
	 * 设置官员
	 * @param player
	 * @param office
	 */
	public void setOffice(Player player, String office) {
		if(office.equals("office1")) {
			office1Player = player.getId();
		} else if(office.equals("office2")) {
			office2Player = player.getId();
		} else if(office.equals("office3")) {
			office3Player = player.getId();
		} else if(office.equals("office4")) {
			office4Player = player.getId();
		}
	}
	
	/**
	 * 移除官员
	 * @param player
	 */
	public void removeOffice(long playerId) {
		if(office1Player == playerId) {
			office1Player = 0;
		} else if(office2Player == playerId) {
			office2Player = 0;
		} else if(office3Player == playerId) {
			office3Player = 0;
		} else if(office4Player == playerId) {
			office4Player = 0;
		}
	}
	
	/**
	 * 设置战队队长
	 * @param player
	 * @param teamId
	 */
	public boolean setTeamLeader(Player player, String teamId) {
		AllianceTeam allianceTeam = teamMap.get(teamId);
		if(allianceTeam != null) {
			allianceTeam.setTeamLeaderId(player.getId());
			return true;
		}
		return false;
	}
	
	/**
	 * 移除战队队长
	 * @param player
	 */
	public void removeTeamLeader(long playerId) {
		Collection<AllianceTeam> collection = teamMap.values();
		if(collection != null) {
			for(AllianceTeam allianceTeam : collection) {
				if(allianceTeam.getTeamLeaderId() == playerId) {
					allianceTeam.setTeamLeaderId(0);
				}
			}
		}
	}
	
	/**
	 * 退出所有战队
	 * @param playerId
	 */
	public void quitTeam(long playerId) {
		Collection<AllianceTeam> collection = teamMap.values();
		if(collection != null) {
			for(AllianceTeam allianceTeam : collection) {
				allianceTeam.dismissAlliance(playerId);
			}
		}
	}
	
	/**
	 * 移除申请
	 * @param playerId
	 */
	public void removeApply(long playerId) {
		this.applyList.remove(playerId);
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public long getRefreshTime() {
		return refreshTime;
	}

	public void setRefreshTime(long refreshTime) {
		this.refreshTime = refreshTime;
	}
	
	public synchronized boolean refresh() {
		boolean today = TimeUtils.isToday(refreshTime);
		if(!today) {
			this.count = 0;
			this.refreshTime = System.currentTimeMillis();
			return true;
		}
		return false;
	}
	

	/**
	 * 添加领地
	 * @param index
	 */
	public void addAllianceTerritory(int index) {
		this.allianceTerritory.getTerritory().add(index);
	}
	
	public void removeAlliaceTerritory(int index) {
		this.allianceTerritory.getTerritory().remove(index);
	}
	
	public void removeTerritoryList(List<Integer> territoryList) {
		this.allianceTerritory.getTerritory().removeAll(territoryList);
	}

	public AllianceTerritory getAllianceTerritory() {
		return allianceTerritory;
	}

	public void setAllianceTerritory(AllianceTerritory allianceTerritory) {
		this.allianceTerritory = allianceTerritory;
	}

//	public List<String> getBuildingList() {
//		return buildingList;
//	}
//
//	public void setBuildingList(List<String> buildingList) {
//		this.buildingList = buildingList;
//	}
//	
//	public void addBuilding(String buildUid) {
//		this.buildingList.add(buildUid);
//	}

	@SuppressWarnings("rawtypes")
	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		jbaseData.put("allianceId", allianceId);
		jbaseData.put("allianceName", allianceName);
		jbaseData.put("abbr", abbr);
		jbaseData.put("icon", icon);
		jbaseData.put("reicon", reicon);
		jbaseData.put("reabbr", reabbr);
		jbaseData.put("rename", rename);
		jbaseData.put("announce", announce);
		jbaseData.put("language", language);
		jbaseData.put("country", country);
		jbaseData.put("fightPower", fightPower);
		jbaseData.put("cash", cash);
		jbaseData.put("level", level);
		jbaseData.put("auto", auto);
		jbaseData.put("leaderId", leaderId);
		jbaseData.put("leaderName", leaderName);
		jbaseData.put("createTime", createTime);
		jbaseData.put("curMemeber", curMemeber);
		jbaseData.put("maxMemeber", maxMemeber);
		jbaseData.put("office1Player", office1Player);
		jbaseData.put("office2Player", office2Player);
		jbaseData.put("office3Player", office3Player);
		jbaseData.put("office4Player", office4Player);
		List<JBaseData> teamMapJBaseList = new ArrayList<JBaseData>();
		for (Map.Entry entry : teamMap.entrySet()) {
			teamMapJBaseList.add(((JBaseTransform) entry.getValue()).toJBaseData());
		}
		jbaseData.put("teamMap", teamMapJBaseList);
		
		List<JBaseData> applyListJBaseList = new ArrayList<JBaseData>();
		for (Map.Entry entry : applyList.entrySet()) {
			applyListJBaseList.add(((JBaseTransform) entry.getValue()).toJBaseData());
		}
		jbaseData.put("applyList", applyListJBaseList);
		
		jbaseData.put("noticeEndTime", noticeEndTime);
		jbaseData.put("count", count);
		jbaseData.put("refreshTime", refreshTime);
		
		jbaseData.put("allianceTitle", allianceTitle.toJBaseData());
		jbaseData.put("allianceTerritory", ((JBaseTransform) allianceTerritory).toJBaseData());
		//jbaseData.put("buildingList", JsonUtil.toJSON(buildingList));
		jbaseData.put("helpList", JsonUtil.toJSON(helpList));
		
		jbaseData.put("rankOne", JsonUtil.toJSON(rankOne));
		jbaseData.put("rankTwo", JsonUtil.toJSON(rankTwo));
		jbaseData.put("rankThree", JsonUtil.toJSON(rankThree));
		jbaseData.put("rankFour", JsonUtil.toJSON(rankFour));
		jbaseData.put("rankFive", JsonUtil.toJSON(rankFive));
		
		return jbaseData;
	}

	@SuppressWarnings("unchecked")
	public void fromJBaseData(JBaseData jBaseData) {
		this.allianceId = jBaseData.getLong("allianceId",0);
		this.allianceName = jBaseData.getString("allianceName","");
		this.abbr = jBaseData.getString("abbr","");
		this.icon = jBaseData.getString("icon","");
		this.reicon = jBaseData.getLong("reicon",0);
		this.reabbr = jBaseData.getLong("reabbr",0);
		this.rename = jBaseData.getLong("rename",0);
		this.announce = jBaseData.getString("announce","");
		this.language = jBaseData.getString("language","");
		this.country = jBaseData.getString("country","");
		this.fightPower = jBaseData.getLong("fightPower",0);
		this.cash = jBaseData.getLong("cash",0);
		this.level = jBaseData.getInt("level",0);
		this.auto = jBaseData.getBoolean("auto", false);
		this.leaderId = jBaseData.getLong("leaderId",0);
		this.leaderName = jBaseData.getString("leaderName","");
		this.createTime = jBaseData.getInt("createTime",0);
		this.curMemeber = jBaseData.getInt("curMemeber",0);
		this.maxMemeber = jBaseData.getInt("maxMemeber",0);
		this.office1Player = jBaseData.getLong("office1Player",0);
		this.office2Player = jBaseData.getLong("office2Player",0);
		this.office3Player = jBaseData.getLong("office3Player",0);
		this.office4Player = jBaseData.getLong("office4Player",0);
		this.noticeEndTime = jBaseData.getLong("noticeEndTime",0);
		this.count = jBaseData.getInt("count",0);
		this.refreshTime = jBaseData.getLong("refreshTime",0);
		
		List<JBaseData> teamMapJBaseData = jBaseData.getSeqBaseData("teamMap");
		for(JBaseData jBaseData2 : teamMapJBaseData) {
			AllianceTeam allianceTeam = new AllianceTeam();
			allianceTeam.fromJBaseData(jBaseData2);
			teamMap.put(allianceTeam.getTeamId(), allianceTeam);
		}
		
		List<JBaseData> applyListJBaseData = jBaseData.getSeqBaseData("applyList");
		for(JBaseData jBaseData2 : applyListJBaseData) {
			AllianceApply allianceApply = new AllianceApply();
			allianceApply.fromJBaseData(jBaseData2);
			applyList.put(allianceApply.getPlayerId(), allianceApply);
		}
		
		AllianceTitle allianceTitle = new AllianceTitle();
		JBaseData allianceJBaseData = jBaseData.getBaseData("allianceTitle");
		allianceTitle.fromJBaseData(allianceJBaseData);
		this.allianceTitle = allianceTitle;
		
		AllianceTerritory allianceTerritory = new AllianceTerritory();
		JBaseData allianceTerritoryJBaseData = jBaseData.getBaseData("allianceTerritory");
		allianceTerritory.fromJBaseData(allianceTerritoryJBaseData);
		this.allianceTerritory = allianceTerritory;
		
//		String builString = jBaseData.getString("buildingList", "");
//		this.buildingList = JsonUtil.fromJSON(builString, List.class);
		
		String helpString = jBaseData.getString("helpList", "");
		if(!StringUtils.isEmpty(helpString)) {
			this.helpList = JsonUtil.fromJSON(helpString, List.class);
		}
		
		String rankOneStr = jBaseData.getString("rankOne", "");
		if(!StringUtils.isEmpty(rankOneStr)) {
			this.rankOne.addAll(JsonUtil.fromJSON(rankOneStr, ConcurrentHashSet.class));
		}
		
		String rankTwoStr = jBaseData.getString("rankTwo", "");
		if(!StringUtils.isEmpty(rankTwoStr)) {
			this.rankTwo.addAll(JsonUtil.fromJSON(rankTwoStr, ConcurrentHashSet.class));
		}
		
		String rankThreeStr = jBaseData.getString("rankThree", "");
		if(!StringUtils.isEmpty(rankThreeStr)) {
			this.rankThree.addAll(JsonUtil.fromJSON(rankThreeStr, ConcurrentHashSet.class));
		}
		
		String rankFourStr = jBaseData.getString("rankFour", "");
		if(!StringUtils.isEmpty(rankOneStr)) {
			this.rankFour.addAll(JsonUtil.fromJSON(rankFourStr, ConcurrentHashSet.class));
		}
		
		String rankFiveStr = jBaseData.getString("rankFive", "");
		if(!StringUtils.isEmpty(rankFiveStr)) {
			this.rankFive.addAll(JsonUtil.fromJSON(rankFiveStr, ConcurrentHashSet.class));
		}
	}

}
