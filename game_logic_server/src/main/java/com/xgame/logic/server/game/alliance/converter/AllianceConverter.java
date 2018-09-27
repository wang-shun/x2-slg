package com.xgame.logic.server.game.alliance.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.alliance.bean.AllianceApplyBean;
import com.xgame.logic.server.game.alliance.bean.AllianceBean;
import com.xgame.logic.server.game.alliance.bean.AllianceFightInfoBean;
import com.xgame.logic.server.game.alliance.bean.AllianceHelpeBean;
import com.xgame.logic.server.game.alliance.bean.AllianceMemberBean;
import com.xgame.logic.server.game.alliance.bean.AllianceOfficeBean;
import com.xgame.logic.server.game.alliance.bean.AlliancePermissionBean;
import com.xgame.logic.server.game.alliance.bean.AlliancePlayerViewBean;
import com.xgame.logic.server.game.alliance.bean.AllianceRankPlayerBean;
import com.xgame.logic.server.game.alliance.bean.AllianceTeamBean;
import com.xgame.logic.server.game.alliance.bean.AllianceTitleBean;
import com.xgame.logic.server.game.alliance.bean.SimpleAllianceBean;
import com.xgame.logic.server.game.alliance.enity.Alliance;
import com.xgame.logic.server.game.alliance.enity.AllianceApply;
import com.xgame.logic.server.game.alliance.enity.AlliancePermission;
import com.xgame.logic.server.game.alliance.enity.AllianceTeam;
import com.xgame.logic.server.game.alliance.enity.AllianceTitle;
import com.xgame.logic.server.game.alliance.enity.HelpInfo;
import com.xgame.logic.server.game.alliance.enity.PlayerAlliance;
import com.xgame.logic.server.game.country.bean.Vector2Bean;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.playerattribute.fightpower.FightPowerKit;
import com.xgame.logic.server.game.timertask.entity.job.data.TimerTaskData;
import com.xgame.logic.server.game.war.entity.report.AllianceBattleReport;

/**
 * 联盟转换器
 * @author jacky.jiang
 *
 */
public class AllianceConverter {
	
	/**
	 * 联盟简易信息
	 * @param alliance
	 * @return
	 */
	public static SimpleAllianceBean converterSimpleAllianceBean(Alliance alliance) {
		SimpleAllianceBean simpleAllianceBean = new SimpleAllianceBean();
		simpleAllianceBean.allianceId = alliance.getAllianceId();
		simpleAllianceBean.abbr = alliance.getAbbr();
		simpleAllianceBean.allianceName = alliance.getAllianceName();
		simpleAllianceBean.createTime = alliance.getCreateTime();
		simpleAllianceBean.curMember = alliance.getCurMemeber();
		simpleAllianceBean.icon = alliance.getIcon();
		simpleAllianceBean.maxMember = alliance.getMaxMemeber();
		simpleAllianceBean.language = alliance.getLanguage();
		simpleAllianceBean.leaderName = alliance.getLeaderName();
		simpleAllianceBean.fightPower = alliance.getFightPower();
		simpleAllianceBean.level = alliance.getLevel();
		simpleAllianceBean.auto = alliance.isAuto() ? 1 : 0;
		return simpleAllianceBean;
	}
	
	/**
	 * 转换联盟对象
	 * @param alliance
	 * @param leaderName
	 * @param maxMember
	 * @return
	 */
	public static AllianceBean converterAllianceBean(Alliance alliance) {
		AllianceBean allianceBean = new AllianceBean();
		allianceBean.abbr = alliance.getAbbr();
		allianceBean.allianceId = alliance.getId();
		allianceBean.allianceName = alliance.getAllianceName();
		allianceBean.announce = alliance.getAnnounce();
		allianceBean.auto = alliance.isAuto() ? 1 : 0;
		allianceBean.cash = alliance.getCash();
		allianceBean.country = alliance.getCountry();
		allianceBean.createTime = alliance.getCreateTime();
		allianceBean.curMember = alliance.currentMemberSize();
		allianceBean.fightPower = alliance.getFightPower();
		allianceBean.icon = alliance.getIcon();
		allianceBean.language = alliance.getLanguage();
		allianceBean.level = alliance.getLevel();
		allianceBean.reabbr = alliance.getReabbr();
		allianceBean.reicon = alliance.getReicon();
		allianceBean.rename = alliance.getRename();
		allianceBean.leaderName = alliance.getLeaderName();
		allianceBean.maxMember = alliance.getMaxMemeber();
		allianceBean.noticeEndTime = alliance.getNoticeEndTime();
		allianceBean.leaderId = alliance.getLeaderId();
		if(alliance.getOffice1Player() > 0) {
			AlliancePlayerViewBean alliancePlayerViewBean = AllianceConverter.factoryAlliancePlayerViewBean(alliance.getOffice1Player());
			allianceBean.allianceOfficeList.add(AllianceConverter.converterAllianceOffice(alliancePlayerViewBean, alliance.getAllianceTitle().getOffice1Name(), 1));	
		}
		
		if(alliance.getOffice2Player() > 0) {
			AlliancePlayerViewBean alliancePlayerViewBean = AllianceConverter.factoryAlliancePlayerViewBean(alliance.getOffice2Player());
			allianceBean.allianceOfficeList.add(AllianceConverter.converterAllianceOffice(alliancePlayerViewBean, alliance.getAllianceTitle().getOffice2Name(), 2));	
		}
		
		if(alliance.getOffice3Player() > 0) {
			AlliancePlayerViewBean alliancePlayerViewBean = AllianceConverter.factoryAlliancePlayerViewBean(alliance.getOffice3Player());
			allianceBean.allianceOfficeList.add(AllianceConverter.converterAllianceOffice(alliancePlayerViewBean, alliance.getAllianceTitle().getOffice3Name(), 3));	
		}
		
		if(alliance.getOffice4Player() > 0) {
			AlliancePlayerViewBean alliancePlayerViewBean = AllianceConverter.factoryAlliancePlayerViewBean(alliance.getOffice4Player());
			allianceBean.allianceOfficeList.add(AllianceConverter.converterAllianceOffice(alliancePlayerViewBean, alliance.getAllianceTitle().getOffice4Name(), 4));	
		}
		
		// 战队信息
		for(java.util.Map.Entry<String, AllianceTeam> entry : alliance.getTeamMap().entrySet()) {
			AllianceTeam aliAllianceTeam = entry.getValue();
			if(aliAllianceTeam.getTeamLeaderId() > 0 || aliAllianceTeam.getTeamMemberIds().size() > 0) {
				AlliancePlayerViewBean leader = AllianceConverter.factoryAlliancePlayerViewBean(aliAllianceTeam.getTeamLeaderId());
				AllianceTeamBean allianceTeamBean = AllianceConverter.converterAllianceTeam(aliAllianceTeam, leader);
				allianceBean.allianceTeamList.add(allianceTeamBean);
			}
		}
		
		
		if(alliance.getAllianceTitle() != null) {
			allianceBean.allianceTitleBean = converterAlianceTitleBean(alliance.getAllianceTitle());
		}
		return allianceBean;
	}
	
	/**
	 * 联盟申请
	 * @param allianceApply
	 * @param player
	 * @return
	 */
	public static AllianceApplyBean converterAllianceApply(AllianceApply allianceApply, Player player) {
		AllianceApplyBean allianceApplyBean = new AllianceApplyBean();
		allianceApplyBean.applyDesc = allianceApply.getApplyMessage();
		allianceApplyBean.applyTime = allianceApply.getApplyTime();
		allianceApplyBean.alliancePlayerViewBean = factoryAlliancePlayerViewBean(player.getId());
		return allianceApplyBean;
	}
	
	public static AllianceMemberBean converterAllianceMember(Player player, PlayerAlliance playerAlliance) {
		AllianceMemberBean allianceMemberBean = new AllianceMemberBean();
		if(playerAlliance.getAlliancePermission() != null) {
			allianceMemberBean.alliancePermissionBean = converterAlliancePermission(playerAlliance.getAlliancePermission());
		}
		allianceMemberBean.allianceRank = playerAlliance.getAllianceRank();
		allianceMemberBean.dayDonate = playerAlliance.getDayDonate();
		allianceMemberBean.diamondCount = playerAlliance.getDiamondDonateCount();
		allianceMemberBean.donate = playerAlliance.getDonate();
		allianceMemberBean.helpDonate = playerAlliance.getHelpDonate();
		allianceMemberBean.fightPower = player.roleInfo().getCurrency().getPower();
		allianceMemberBean.historyDonate = playerAlliance.getHistoryDonate();
		allianceMemberBean.joinTime = playerAlliance.getJoinTime();
		allianceMemberBean.moneyCount = playerAlliance.getMoneyDonateCount();
		allianceMemberBean.oilCount = playerAlliance.getOilDonateCount();
		allianceMemberBean.rareCount = playerAlliance.getRareDonateCount();
		allianceMemberBean.refreshTime = playerAlliance.getRefreshTime();
		allianceMemberBean.steelCount = playerAlliance.getSteelDonateCount();
		allianceMemberBean.teamId = playerAlliance.getTeamId();
		allianceMemberBean.playerId = playerAlliance.getId();
		return allianceMemberBean;
	}
	
	private static AlliancePermissionBean converterAlliancePermission(AlliancePermission alliancePermission) {
		AlliancePermissionBean alliancePermissionBean = new AlliancePermissionBean();
		alliancePermissionBean.assignAllianceReward = alliancePermission.isAssignAllianceReward() ? 1 : 0;
		alliancePermissionBean.assignTeamLeader = alliancePermission.isAssignTeamLeader() ? 1 : 0;
		alliancePermissionBean.createAllianceBuild = alliancePermission.isAssignAllianceReward() ? 1 : 0;
		alliancePermissionBean.invite = alliancePermission.isInvite() ? 1 : 0;
		alliancePermissionBean.kickmember = alliancePermission.isKickmember() ? 1 : 0;
		alliancePermissionBean.test = "test";
		alliancePermissionBean.managerAllianceBuild = alliancePermission.isManagerAllianceBuild() ? 1 : 0;
		alliancePermissionBean.recruit = alliancePermission.isRecruit() ? 1 : 0;
		alliancePermissionBean.sendMail = alliancePermission.isSendMail() ? 1 : 0;
		alliancePermissionBean.dealApply = alliancePermission.isDealApply() ? 1 : 0;
		alliancePermissionBean.managerMemeberLevel = alliancePermission.isManagerMemeberLevel() ? 1 : 0;
		return alliancePermissionBean;
	}
	
	public static AllianceTeamBean converterAllianceTeam(AllianceTeam allianceTeam, AlliancePlayerViewBean leader) {
		AllianceTeamBean allianceTeamBean = new AllianceTeamBean();
		allianceTeamBean.allianceId = allianceTeam.getAllianceId();
		allianceTeamBean.icon = allianceTeam.getIcon();
		allianceTeamBean.teamId = allianceTeam.getTeamId();
		allianceTeamBean.teamLeader = leader;
		allianceTeamBean.teamName = allianceTeam.getTeamName();
		return allianceTeamBean;
	}
	
	public static AllianceOfficeBean converterAllianceOffice(AlliancePlayerViewBean alliancePlayerViewBean, String officeName, int i) {
		AllianceOfficeBean allianceOfficeBean = new AllianceOfficeBean();
		allianceOfficeBean.allianceOffice = alliancePlayerViewBean;
		allianceOfficeBean.officeName = officeName;
		allianceOfficeBean.type = i;
		return allianceOfficeBean;
	}
	
	
	/**
	 * 生成战力排行信息
	 * @param set
	 * @return
	 */
	public static List<AlliancePlayerViewBean> generateFightPowerView(Set<Long> set) {
		List<AlliancePlayerViewBean> viewList = new ArrayList<>();
		if(set != null && !set.isEmpty()) {
			for(long id : set) {
				AlliancePlayerViewBean alliancePlayerViewBean = factoryAlliancePlayerViewBean(id);
				viewList.add(alliancePlayerViewBean);
			}
		}
		return viewList;
	}
	
	
	/**
	 * 
	 * @param playerId
	 * @return
	 */
	public static AlliancePlayerViewBean factoryAlliancePlayerViewBean(long playerId) {
		AlliancePlayerViewBean alliancePlayerViewBean = new AlliancePlayerViewBean();
		alliancePlayerViewBean.playerId = playerId;
		Player rowPlayer = InjectorUtil.getInjector().dbCacheService.get(Player.class, playerId);
		if(rowPlayer != null) {
			alliancePlayerViewBean.name = rowPlayer.getName();
			alliancePlayerViewBean.img = String.valueOf(rowPlayer.roleInfo().getCommanderData().getStyle());
			alliancePlayerViewBean.fightPower = FightPowerKit.FINAL_POWER.getValue(rowPlayer.getId()).longValue();
			alliancePlayerViewBean.online = InjectorUtil.getInjector().sessionManager.checkOnline(rowPlayer.getId());
		}
		
		PlayerAlliance playerAlliance = InjectorUtil.getInjector().dbCacheService.get(PlayerAlliance.class, playerId);
		if(playerAlliance != null) {
			alliancePlayerViewBean.alliancePermissionBean = converterAlliancePermission(playerAlliance.getAlliancePermission());
		}
		return alliancePlayerViewBean;
	}
	
	/**
	 * 生成联盟坐标信息
	 * @param set
	 * @return
	 */
	public static List<AlliancePlayerViewBean> generatePositionView(Set<Long> set) {
		List<AlliancePlayerViewBean> viewList = new ArrayList<>();
		if(set != null && !set.isEmpty()) {
			for(long id : set) {
				AlliancePlayerViewBean alliancePlayerViewBean = factoryPlayerPositionView(id);
				viewList.add(alliancePlayerViewBean);
			}
		}
		return viewList;
	}
	
	public static AlliancePlayerViewBean factoryPlayerPositionView(long playerId) {
		AlliancePlayerViewBean alliancePlayerViewBean = new AlliancePlayerViewBean();
		alliancePlayerViewBean.playerId = playerId;
		Player rowPlayer = InjectorUtil.getInjector().dbCacheService.get(Player.class, playerId);
		if(rowPlayer != null) {
			alliancePlayerViewBean.name = rowPlayer.getName();
			alliancePlayerViewBean.img = String.valueOf(rowPlayer.roleInfo().getCommanderData().getStyle());
			if(rowPlayer.getLocation() != null) {
				Vector2Bean vector2Bean = new Vector2Bean();
				vector2Bean.x = rowPlayer.getLocation().getX();
				vector2Bean.y = rowPlayer.getLocation().getY();
				alliancePlayerViewBean.vector2Bean = vector2Bean;
			}
			alliancePlayerViewBean.online = InjectorUtil.getInjector().sessionManager.checkOnline(rowPlayer.getId());
		}
		
		PlayerAlliance playerAlliance = InjectorUtil.getInjector().dbCacheService.get(PlayerAlliance.class, playerId);
		if(playerAlliance != null) {
			alliancePlayerViewBean.alliancePermissionBean = converterAlliancePermission(playerAlliance.getAlliancePermission());
		}
		return alliancePlayerViewBean;
	}
	
	
	/**
	 * 生成领地排行信息
	 * @param set
	 * @return
	 */
	public static List<AlliancePlayerViewBean> generateTerritoryView(Set<Long> set) {
		List<AlliancePlayerViewBean> viewList = new ArrayList<>();
		if(set != null && !set.isEmpty()) {
			for(long id : set) {
				AlliancePlayerViewBean alliancePlayerViewBean = factoryTerritoryPlayerView(id);
				viewList.add(alliancePlayerViewBean);
			}
		}
		return viewList;
	}
	
	/**
	 * 玩家领地
	 * @param playerId
	 * @return
	 */
	public static AlliancePlayerViewBean factoryTerritoryPlayerView(long playerId) {
		AlliancePlayerViewBean alliancePlayerViewBean = new AlliancePlayerViewBean();
		alliancePlayerViewBean.playerId = playerId;
		Player rowPlayer = InjectorUtil.getInjector().dbCacheService.get(Player.class, playerId);
		if(rowPlayer != null) {
			alliancePlayerViewBean.name = rowPlayer.getName();
			alliancePlayerViewBean.img = String.valueOf(rowPlayer.roleInfo().getCommanderData().getStyle());
			alliancePlayerViewBean.online = InjectorUtil.getInjector().sessionManager.checkOnline(rowPlayer.getId());
		}
		alliancePlayerViewBean.territoryNum = rowPlayer.roleInfo().getPlayerTerritory().getTerritoryNum();
		
		PlayerAlliance playerAlliance = InjectorUtil.getInjector().dbCacheService.get(PlayerAlliance.class, playerId);
		if(playerAlliance != null) {
			alliancePlayerViewBean.alliancePermissionBean = converterAlliancePermission(playerAlliance.getAlliancePermission());
		}
		return alliancePlayerViewBean;
	}
	
	/**
	 * 生成联盟坐标信息
	 * @param set
	 * @return
	 */
	public static List<AlliancePlayerViewBean> generateOfflineView(Set<Long> set) {
		List<AlliancePlayerViewBean> viewList = new ArrayList<>();
		if(set != null && !set.isEmpty()) {
			for(long id : set) {
				AlliancePlayerViewBean alliancePlayerViewBean = factoryPlayerOfflineView(id);
				viewList.add(alliancePlayerViewBean);
			}
		}
		return viewList;
	}
	
	/**
	 * 生成官员信息
	 * @param playerId
	 * @return
	 */
	public static AlliancePlayerViewBean factoryPlayerOfflineView(long playerId) {
		AlliancePlayerViewBean alliancePlayerViewBean = new AlliancePlayerViewBean();
		alliancePlayerViewBean.playerId = playerId;
		Player rowPlayer = InjectorUtil.getInjector().dbCacheService.get(Player.class, playerId);
		if(rowPlayer != null) {
			alliancePlayerViewBean.name = rowPlayer.getName();
			alliancePlayerViewBean.img = String.valueOf(rowPlayer.roleInfo().getCommanderData().getStyle());
			alliancePlayerViewBean.offlineTime = rowPlayer.roleInfo().getBasics().getLogoutTime();
			alliancePlayerViewBean.online = InjectorUtil.getInjector().sessionManager.checkOnline(rowPlayer.getId());
		}
		
		PlayerAlliance playerAlliance = InjectorUtil.getInjector().dbCacheService.get(PlayerAlliance.class, playerId);
		if(playerAlliance != null) {
			alliancePlayerViewBean.alliancePermissionBean = converterAlliancePermission(playerAlliance.getAlliancePermission());
		}
		return alliancePlayerViewBean;
	}
	
	/**
	 * 头衔转换
	 * @param allianceTitle
	 * @return
	 */
	public static AllianceTitleBean converterAlianceTitleBean(AllianceTitle allianceTitle) {
		AllianceTitleBean allianceTitleBean = new AllianceTitleBean();
		allianceTitleBean.office1 = allianceTitle.getOffice1Name();
		allianceTitleBean.office2 = allianceTitle.getOffice2Name();
		allianceTitleBean.office3 = allianceTitle.getOffice3Name();
		allianceTitleBean.office4 = allianceTitle.getOffice4Name();
		allianceTitleBean.rankName1 = allianceTitle.getRankOneName();
		allianceTitleBean.rankName2 = allianceTitle.getRankTwoName();
		allianceTitleBean.rankName3 = allianceTitle.getRankThreeName();
		allianceTitleBean.rankName4 = allianceTitle.getRankFourName();
		allianceTitleBean.rankName5 = allianceTitle.getRankFiveName();
		return allianceTitleBean;
	}
	
	/**
	 * 联盟申请
	 * @param allianceApply
	 * @return
	 */
	public static AllianceApplyBean converterAllianceApplyBean(AllianceApply allianceApply) {
		AllianceApplyBean allianceApplyBean = new AllianceApplyBean();
		allianceApplyBean.alliancePlayerViewBean = factoryAlliancePlayerViewBean(allianceApply.getPlayerId());
		allianceApplyBean.applyDesc = allianceApply.getApplyMessage();
		allianceApplyBean.applyTime = allianceApply.getApplyTime();
		return allianceApplyBean;
	}
	
	/**
	 * 战斗详情
	 * @param battleReport
	 * @return
	 */
	public static AllianceFightInfoBean converterAllianceFightInfoBean(AllianceBattleReport battleReport) {
		AllianceFightInfoBean allianceFightInfoBean = new AllianceFightInfoBean();
		allianceFightInfoBean.attackPlayerId = battleReport.getAttackId();
		allianceFightInfoBean.attackPlayerName = battleReport.getAttackName();
		allianceFightInfoBean.target = battleReport.getDefendName();
		allianceFightInfoBean.result = battleReport.getResult();
		return allianceFightInfoBean;
	}
	
	public static AllianceHelpeBean converterAllianceHelp(Player player, HelpInfo helpInfo, String helpName,long helpId) {
		AllianceHelpeBean allianceHelpBean = new AllianceHelpeBean();
		allianceHelpBean.allianceId = helpInfo.getAllianceId();
		allianceHelpBean.helpid = helpInfo.getHelpId().toString();
		allianceHelpBean.maxcount = helpInfo.getMaxcount();
		allianceHelpBean.nowcount = helpInfo.getNowcount();
		allianceHelpBean.reduceSec = helpInfo.getReduceSec();
		allianceHelpBean.playerName = player.getName();
		allianceHelpBean.sendPlayerId = player.getId();
		allianceHelpBean.startTime = helpInfo.getStartTime();
		allianceHelpBean.type = helpInfo.getType();
		allianceHelpBean.taskId = helpInfo.getTaskId();
		allianceHelpBean.level = helpInfo.getLevel();
		allianceHelpBean.sid = helpInfo.getSid();
		allianceHelpBean.helpName = helpName;
		allianceHelpBean.helpPlayerId = helpId;
		allianceHelpBean.allianceId = helpInfo.getAllianceId();
		allianceHelpBean.allianceName = InjectorUtil.getInjector().allianceManager.getAllianceNameByAllianceId(helpInfo.getAllianceId());
		TimerTaskData timerTaskData = InjectorUtil.getInjector().dbCacheService.get(TimerTaskData.class, helpInfo.getTaskId());
		if(timerTaskData != null && helpInfo.getStartTime() > 0) {
			allianceHelpBean.endTime = timerTaskData.getTriggerTime();
		} else {
			allianceHelpBean.endTime = 0;
		}
		return allianceHelpBean;
	}
	
	public static AllianceRankPlayerBean converterAllianceRankPlayerBean(Player player, PlayerAlliance playerAlliance) {
		AllianceRankPlayerBean allianceRankPlayerBean = new AllianceRankPlayerBean();
		allianceRankPlayerBean.donate = playerAlliance.getDayDonate();
		allianceRankPlayerBean.fightPower = FightPowerKit.FINAL_POWER.getValue(player.getId()).longValue();
		allianceRankPlayerBean.name = player.getName();
		allianceRankPlayerBean.img = String.valueOf(player.roleInfo().getCommanderData().getStyle());
		allianceRankPlayerBean.historyDonate = playerAlliance.getHistoryDonate();
		allianceRankPlayerBean.killNum = playerAlliance.getKillNum();
		allianceRankPlayerBean.fightPower = FightPowerKit.FINAL_POWER.getValue(player.getId()).longValue();
		allianceRankPlayerBean.playerId = player.getId();
		allianceRankPlayerBean.weekDonate = playerAlliance.getWeekDonate();
		return allianceRankPlayerBean;
	}
}
