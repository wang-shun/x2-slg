package com.xgame.logic.server.game.alliance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xgame.config.army.ArmyPir;
import com.xgame.config.army.ArmyPirFactory;
import com.xgame.config.armyDonate.ArmyDonatePir;
import com.xgame.config.armyDonate.ArmyDonatePirFactory;
import com.xgame.config.global.GlobalPir;
import com.xgame.config.global.GlobalPirFactory;
import com.xgame.framework.lifecycle.Startup;
import com.xgame.framework.lifecycle.StartupOrder;
import com.xgame.gameconst.DBKey;
import com.xgame.gameconst.GlobalConstant;
import com.xgame.logic.server.core.db.cache.cache.CacheProxy;
import com.xgame.logic.server.core.db.redis.RedisClient;
import com.xgame.logic.server.core.gamelog.constant.GameLogSource;
import com.xgame.logic.server.core.gamelog.event.EventBus;
import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.language.Language;
import com.xgame.logic.server.core.language.view.error.ErrorCodeEnum;
import com.xgame.logic.server.core.language.view.success.SuccessTipEnum;
import com.xgame.logic.server.core.net.gate.SessionManager;
import com.xgame.logic.server.core.utils.CurrencyUtil;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.core.utils.JsonUtil;
import com.xgame.logic.server.core.utils.ParamFormatUtils;
import com.xgame.logic.server.core.utils.collection.ConcurrentHashSet;
import com.xgame.logic.server.core.utils.sequance.IDFactrorySequencer;
import com.xgame.logic.server.core.utils.sort.MapRankableComparator;
import com.xgame.logic.server.core.utils.sort.SmartRank;
import com.xgame.logic.server.game.alliance.bean.AllianceApplyBean;
import com.xgame.logic.server.game.alliance.bean.AllianceBean;
import com.xgame.logic.server.game.alliance.bean.AllianceFightInfoBean;
import com.xgame.logic.server.game.alliance.bean.AlliancePermissionBean;
import com.xgame.logic.server.game.alliance.bean.AlliancePlayerViewBean;
import com.xgame.logic.server.game.alliance.bean.AllianceTeamBean;
import com.xgame.logic.server.game.alliance.bean.SimpleAllianceBean;
import com.xgame.logic.server.game.alliance.constant.AllianceConstant;
import com.xgame.logic.server.game.alliance.constant.AllianceDonateType;
import com.xgame.logic.server.game.alliance.converter.AllianceConverter;
import com.xgame.logic.server.game.alliance.enity.Alliance;
import com.xgame.logic.server.game.alliance.enity.AllianceAnounce;
import com.xgame.logic.server.game.alliance.enity.AllianceApply;
import com.xgame.logic.server.game.alliance.enity.AlliancePermission;
import com.xgame.logic.server.game.alliance.enity.AllianceRankable;
import com.xgame.logic.server.game.alliance.enity.AllianceReport;
import com.xgame.logic.server.game.alliance.enity.AllianceTeam;
import com.xgame.logic.server.game.alliance.enity.AllianceTitle;
import com.xgame.logic.server.game.alliance.enity.HelpInfo;
import com.xgame.logic.server.game.alliance.enity.PlayerAlliance;
import com.xgame.logic.server.game.alliance.enity.eventmodel.AllianceAbbrChangeEventObject;
import com.xgame.logic.server.game.alliance.enity.eventmodel.AllianceAidEventObject;
import com.xgame.logic.server.game.alliance.enity.eventmodel.AllianceJoinEventObject;
import com.xgame.logic.server.game.alliance.enity.eventmodel.AllianceLeftEventObject;
import com.xgame.logic.server.game.alliance.enity.eventmodel.AllianceNameChangeEventObject;
import com.xgame.logic.server.game.alliance.enity.eventmodel.CreateAllianceEventObject;
import com.xgame.logic.server.game.alliance.enity.eventmodel.DonateEventObject;
import com.xgame.logic.server.game.alliance.enity.eventmodel.LevelupAllianceEventObject;
import com.xgame.logic.server.game.alliance.enity.eventmodel.SendAllianceNoticeEventObject;
import com.xgame.logic.server.game.alliance.message.ResAddTeamMemberMessage;
import com.xgame.logic.server.game.alliance.message.ResAllianceChangeMessage;
import com.xgame.logic.server.game.alliance.message.ResAllianceDataMessage;
import com.xgame.logic.server.game.alliance.message.ResAllianceFightListMessage;
import com.xgame.logic.server.game.alliance.message.ResAllianceHelpDeleteMessage;
import com.xgame.logic.server.game.alliance.message.ResAllianceOfficeMessage;
import com.xgame.logic.server.game.alliance.message.ResApplyAllianceMessage;
import com.xgame.logic.server.game.alliance.message.ResApplyListMessage;
import com.xgame.logic.server.game.alliance.message.ResCancelApplyMessage;
import com.xgame.logic.server.game.alliance.message.ResChangeLeaderMessage;
import com.xgame.logic.server.game.alliance.message.ResChangeRankMessage;
import com.xgame.logic.server.game.alliance.message.ResCreateAllianceMessage;
import com.xgame.logic.server.game.alliance.message.ResDealApplyMessage;
import com.xgame.logic.server.game.alliance.message.ResDealInviteMessage;
import com.xgame.logic.server.game.alliance.message.ResDeleteTeamMemberMessage;
import com.xgame.logic.server.game.alliance.message.ResDimissOfficeMessage;
import com.xgame.logic.server.game.alliance.message.ResDismissAllianceMessage;
import com.xgame.logic.server.game.alliance.message.ResDonateMessage;
import com.xgame.logic.server.game.alliance.message.ResDoneHelpMessage;
import com.xgame.logic.server.game.alliance.message.ResEditAllianceMessage;
import com.xgame.logic.server.game.alliance.message.ResEditPermissionMessage;
import com.xgame.logic.server.game.alliance.message.ResEditTeamMessage;
import com.xgame.logic.server.game.alliance.message.ResEditTitleMessage;
import com.xgame.logic.server.game.alliance.message.ResGetAllianceListMessage;
import com.xgame.logic.server.game.alliance.message.ResGetHelpeListMessage;
import com.xgame.logic.server.game.alliance.message.ResInviteMessage;
import com.xgame.logic.server.game.alliance.message.ResKickMemberMessage;
import com.xgame.logic.server.game.alliance.message.ResLevelupAllianceMessage;
import com.xgame.logic.server.game.alliance.message.ResNewApplyMessage;
import com.xgame.logic.server.game.alliance.message.ResNotifyInviteMessage;
import com.xgame.logic.server.game.alliance.message.ResNotifyPermissionChangeMessage;
import com.xgame.logic.server.game.alliance.message.ResPlayerAllianceInfoMessage;
import com.xgame.logic.server.game.alliance.message.ResQueryAllianceMemeberMessage;
import com.xgame.logic.server.game.alliance.message.ResRankPlayerMessage;
import com.xgame.logic.server.game.alliance.message.ResRecommendPlayerMessage;
import com.xgame.logic.server.game.alliance.message.ResSearchAllianceMessage;
import com.xgame.logic.server.game.alliance.message.ResSendHelperMessage;
import com.xgame.logic.server.game.alliance.message.ResSetAutoJoinMessage;
import com.xgame.logic.server.game.alliance.message.ResSetOfficeMessage;
import com.xgame.logic.server.game.alliance.message.ResViewTeamMemberMessage;
import com.xgame.logic.server.game.allianceext.AllianceExtManager;
import com.xgame.logic.server.game.allianceext.PlayerAllianceExtManager;
import com.xgame.logic.server.game.allianceext.entity.AllianceExt;
import com.xgame.logic.server.game.allianceext.entity.PlayerAllianceExt;
import com.xgame.logic.server.game.awardcenter.AwardUtil;
import com.xgame.logic.server.game.chat.AllianceChatManager;
import com.xgame.logic.server.game.constant.CurrencyEnum;
import com.xgame.logic.server.game.constant.SystemEnum;
import com.xgame.logic.server.game.country.entity.XBuild;
import com.xgame.logic.server.game.country.structs.build.BuildControl;
import com.xgame.logic.server.game.cross.CrossPlayerManager;
import com.xgame.logic.server.game.cross.entity.CrossPlayer;
import com.xgame.logic.server.game.cross.entity.SimpleRoleInfo;
import com.xgame.logic.server.game.email.MailKit;
import com.xgame.logic.server.game.email.constant.EmailTemplet;
import com.xgame.logic.server.game.email.converter.EmailConverter;
import com.xgame.logic.server.game.gameevent.EventManager;
import com.xgame.logic.server.game.notice.NoticeManager;
import com.xgame.logic.server.game.notice.constant.NoticeConstant;
import com.xgame.logic.server.game.player.PlayerManager;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.playerattribute.fightpower.FightPowerKit;
import com.xgame.logic.server.game.timertask.TimerTaskManager;
import com.xgame.logic.server.game.timertask.entity.job.ITimerTask;
import com.xgame.logic.server.game.timertask.entity.job.TimerTaskHolder;
import com.xgame.logic.server.game.timertask.entity.job.data.TimerTaskData;
import com.xgame.logic.server.game.war.entity.report.AllianceBattleReport;
import com.xgame.logic.server.game.world.WorldLogicManager;
import com.xgame.utils.EnumUtils;

/**
 * 联盟
 * @author jacky.jiang
 *
 */
@Slf4j
@Component
public class AllianceManager extends CacheProxy<Alliance> {

	@Autowired
	private RedisClient redisClient;
	@Autowired
	public PlayerAllianceManager playerAllianceManager;  
	@Autowired 
	private IDFactrorySequencer idSequencer;
	@Autowired
	private NoticeManager noticeManager;
	@Autowired
	private SessionManager sessionManager;
	@Autowired
	private AllianceChatManager allianceChatManager;
	@Autowired
	private PlayerManager playerManager;
	@Autowired
	public RecommendPlayerManager recommendPlayerManager;
	@Autowired
	private AllianceBattleInfoManager allianceBattleInfoManager;
	@Autowired
	private TimerTaskManager timerTaskManager;
	@Autowired
	private EventBus gameLog;
	@Autowired
	private EventManager eventManager;
	@Autowired
	private WorldLogicManager worldLogicManager;
	@Autowired
	private CrossPlayerManager crossPlayerManager;
	@Autowired
	private HelpInfoManager helpInfoManager;
	@Autowired
	private PlayerAllianceExtManager playerAllianceExtManager;
	@Autowired
	private AllianceExtManager allianceExtManager;
	
	
	public static final int WORLD_NOTICE_LEVEL = 10;
	
	public static final int DONATE_RESOURCE_NOTICE_COUNT = 10;
	
	public static final int DONATE_DIAMOND_NOTICE_COUNT = 6;
	
	public static final int MODIFY_TIME = 30 * 24 * 3600 * 1000;
	
	private Map<Long, String> allianceIdNameMap = new ConcurrentHashMap<Long, String>();
		
	private Map<String, Long> allianceNameIdMap = new ConcurrentHashMap<String, Long>();
	
	private Map<String, Long> allianceAbbrNameIdMap = new ConcurrentHashMap<String, Long>();
	
	// 联盟排行榜
	private SmartRank smartRank = new SmartRank(new MapRankableComparator("level DESC, fightPower DESC"), -1);
	
	/**
	 * 获取联盟名称
	 * @param allianceId
	 * @return
	 */
	public String getAllianceNameByAllianceId(long allianceId) {
		return allianceIdNameMap.get(allianceId);
	}
	
	public Long getAllianceIdByAllianceName(String allianceName) {
		return allianceNameIdMap.get(allianceName);
	}
	
	public Long getAllianceIdByAllianceAbbr(String allianceAbbr) {
		return allianceAbbrNameIdMap.get(allianceAbbr);
	}
	
	public String getAllianceAbbrByAllianceId(long allianceId) {
		Alliance alliance = InjectorUtil.getInjector().dbCacheService.get(Alliance.class, allianceId);
		if(alliance != null) {
			return alliance.getAbbr();
		}
		return null;
	}
	
	@Override
	public Class<?> getProxyClass() {
		return Alliance.class;
	}
	
	/**
	 * 联盟登录消息
	 * @param player
	 */
	public void login(Player player) {
		PlayerAlliance playerAlliance = InjectorUtil.getInjector().dbCacheService.get(PlayerAlliance.class, player.getId());
		ResAllianceDataMessage resAllianceDataMessage = new ResAllianceDataMessage();
		if(playerAlliance != null && playerAlliance.getAllianceId() > 0) {
			Alliance aliAlliance = this.getRefreshAlliance(playerAlliance.getAllianceId());
			resAllianceDataMessage.alliance = AllianceConverter.converterAllianceBean(aliAlliance);
			resAllianceDataMessage.playerAlliance = AllianceConverter.converterAllianceMember(player, playerAlliance);
			player.send(resAllianceDataMessage);
		}
	}
	
	/**
	 * 联盟启动加载数据
	 */
	@Startup(order = StartupOrder.ALLIANCE_START, desc = "联盟启动加载")
	public void load() {
		List<Alliance> alliances = redisClient.hvals(Alliance.class);
		if(alliances != null && !alliances.isEmpty()) {
			for(Alliance alliance : alliances) {
				this.add(alliance);
				allianceIdNameMap.put(alliance.getId(), alliance.getAllianceName());
				allianceNameIdMap.put(alliance.getAllianceName(), alliance.getId());
				allianceAbbrNameIdMap.put(alliance.getAbbr(), alliance.getId());
			
				// 导入排行榜
				smartRank.compareAndRefresh(AllianceRankable.valueOf(alliance.getId(), alliance.getLevel(), alliance.getFightPower()));
			}
		}
	}
	
	/**
	 * 获取联盟列表
	 * @param player
	 * @param index
	 */
	public void getAllianceList(Player player, int index) {
		List<SimpleAllianceBean> allianceBeanList = new ArrayList<SimpleAllianceBean>();
		List<Object> rankList = smartRank.getRankList(index, AllianceConstant.ALLIANCE_LIST_SIZE);
		if(rankList != null) {
			for(Object obj : rankList) {
				Long id = (Long)obj;
				Alliance alliance = this.getRefreshAlliance(id);
				if(alliance != null) {
					SimpleAllianceBean simpleAllianceBean = AllianceConverter.converterSimpleAllianceBean(alliance);
					allianceBeanList.add(simpleAllianceBean);
				}
			}
		}
		
		ResGetAllianceListMessage resGetAllianceListMessage = new ResGetAllianceListMessage();
		resGetAllianceListMessage.allianceList = allianceBeanList;
		player.send(resGetAllianceListMessage);
	}
	
	/**
	 * 搜索军团
	 * @param player
	 * @param allianceName
	 */
	public void searchAlliance(Player player, String allianceName) {
		
		if(StringUtils.isEmpty(allianceName) || allianceName.length() > 200) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE15);
		}
		
		Long allianceAbbrId = allianceAbbrNameIdMap.get(allianceName);
		Long allianceNameId = allianceNameIdMap.get(allianceName);
		if(allianceAbbrId == null && allianceNameId == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE1);
			return;
		}
		ResSearchAllianceMessage resSearchAllianceMessage = new ResSearchAllianceMessage();
		if(allianceAbbrId != null){
			Alliance allianceAbbr = this.getRefreshAlliance(allianceAbbrId);
			if(allianceAbbr != null){
				resSearchAllianceMessage.allianceList.add(AllianceConverter.converterSimpleAllianceBean(allianceAbbr));
			}
		}
		
		if(allianceNameId != null){
			Alliance alliance = this.getRefreshAlliance(allianceNameId);
			if(alliance != null){
				resSearchAllianceMessage.allianceList.add(AllianceConverter.converterSimpleAllianceBean(alliance));
			}
		}
		
		player.send(resSearchAllianceMessage);
		
		Language.SUCCESSTIP.send(player, SuccessTipEnum.E1007_ALLIANCE.CODE1);
	}
	
	/**
	 * 申请加入联盟
	 * @param player
	 * @param allianceId
	 */
	public void applyAlliance(Player player, long allianceId, String message) {
		
		Alliance alliance = this.getRefreshAlliance(allianceId);
		if(alliance == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE20);
			return;
		}
		
		AllianceApply allianceApply = alliance.getPlayerApply(player.getId());
		if(allianceApply != null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE2);
			return;
		}
		
		synchronized (alliance) {
			PlayerAlliance playerAlliance = playerAllianceManager.getOrCreate(player.getId());
			if(playerAlliance.getApplyList().size() >= 10) {
				Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE3);
				return;
			}
			
			if(playerAlliance.getAllianceId() > 0) {
				Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE5);
				return;
			}
		
			if(alliance.isAuto()) {
				// 加入联盟
				doJoinAlliance(player, playerAlliance, alliance);
				
				// 返回联盟信息
				ResAllianceDataMessage resAllianceDataMessage = new ResAllianceDataMessage();
				resAllianceDataMessage.playerAlliance = AllianceConverter.converterAllianceMember(player, playerAlliance);
				resAllianceDataMessage.alliance = AllianceConverter.converterAllianceBean(alliance);
				player.send(resAllianceDataMessage);
				Set<Long> set = alliance.getAllianceMember();
				set.remove(player.getId());
				// 联盟信息变更
				ResAllianceChangeMessage resAllianceChangeMessage = new ResAllianceChangeMessage();
				resAllianceChangeMessage.alliance = AllianceConverter.converterAllianceBean(alliance);
				sessionManager.writePlayers(set, resAllianceChangeMessage);
				
				MailKit.sendSystemEmail(player.getId(), EmailTemplet.批准入团申请_MAIL_ID,ParamFormatUtils.formatParma(EmailConverter.emailOfPlayerName(alliance.getLeaderId(), alliance.getLeaderName()),alliance.getAllianceName()));
			} else{
				allianceApply = new AllianceApply();
				allianceApply.setPlayerId(player.getId());
				allianceApply.setApplyTime(System.currentTimeMillis());
				allianceApply.setApplyMessage(message);
				
				alliance.getApplyList().put(player.getId(), allianceApply);
				InjectorUtil.getInjector().dbCacheService.update(alliance);
				
				playerAlliance.getApplyList().add(allianceId);
				InjectorUtil.getInjector().dbCacheService.update(playerAlliance);

				ResApplyAllianceMessage resApplyAllianceMessage = new ResApplyAllianceMessage();
				resApplyAllianceMessage.allianceId = alliance.getId();
				player.send(resApplyAllianceMessage);
				
				// 推送R5有权限级盟主
				ResNewApplyMessage  resNewApplyMessage = new ResNewApplyMessage();
				AllianceApplyBean allianceApplyBean = new AllianceApplyBean();
				resNewApplyMessage.allianceApply = allianceApplyBean;
				allianceApplyBean.alliancePlayerViewBean = AllianceConverter.factoryAlliancePlayerViewBean(player.getId());
				allianceApplyBean.applyTime = allianceApply.getApplyTime();
				allianceApplyBean.applyDesc = allianceApply.getApplyMessage();
				resNewApplyMessage.allianceApply = allianceApplyBean;
			
				Set<Long> noticePlayer = new ConcurrentHashSet<>();
				noticePlayer.addAll(alliance.getRankFive());
				noticePlayer.add(alliance.getLeaderId());
				sessionManager.writePlayers(noticePlayer, resNewApplyMessage);
			}
		}

		Language.SUCCESSTIP.send(player, SuccessTipEnum.E1007_ALLIANCE.CODE10);
	}
	
	/**
	 * 创建联盟
	 * @param player
	 * @param allianceName
	 * @param abbrName
	 * @param announce
	 * @param icon
	 */
	public void createAlliance(Player player, String allianceName, String abbrName, String announce, String icon, String language) {
		
		if(StringUtils.isEmpty(allianceName) || StringUtils.isEmpty(abbrName) || StringUtils.isEmpty(icon)) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE15);
			return;
		}
		
		if(allianceName.length() > AllianceConstant.ALLIANCE_NAME_SIZE) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE6);
			return;
		}
		
		if(abbrName.length() != AllianceConstant.ABBR_NAME_SIZE) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE39);
			return;
		}
		
		if(allianceNameIdMap.keySet().contains(allianceName)) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE7);
			return;
		}
		
		if(allianceAbbrNameIdMap.keySet().contains(abbrName)) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE8);
			return;
		}
		
		PlayerAlliance playerAlliance = playerAllianceManager.getOrCreate(player.getId());
		if(playerAlliance != null && playerAlliance.getAllianceId() > 0) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE11);
			return;
		}
		
		// 判断建筑依赖
		boolean condition = true;
		Map<Integer, Integer> buildingdepend = GlobalPirFactory.getInstance().createAllianceBuild;
		if(buildingdepend != null && !buildingdepend.isEmpty()) {
			for(Map.Entry<Integer, Integer> entry : buildingdepend.entrySet()) {
				BuildControl buildControl = player.getCountryManager().getBuildControls().get(entry.getKey());
				if(buildControl != null) {
					XBuild build = buildControl.getMaxLevelBuild();
					if(build == null || build.getLevel() < entry.getValue()) {
						condition = false;
						break;
					}
				} else {
					condition = false;
					break;
				}
			}
		}
		
		if(!condition) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE9);
			return;
		}
		
		// 判断资源条件
		condition = true;
		Map<Integer, Integer> costResource = GlobalPirFactory.getInstance().costResource;
		if(costResource != null && !costResource.isEmpty()) {
			for(Map.Entry<Integer, Integer> entry : costResource.entrySet()) {
				if(!CurrencyUtil.verifyFinal(player, entry.getKey(), entry.getValue())) {
					condition = false;
					break;
				}
			}
		}
		
		if(!condition) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE10);
			return;
		}
		
		// 创建联盟
		Alliance alliance = new Alliance();
		long allianceId = idSequencer.createEnityID(DBKey.ALLIANCE_ID_KEY);
		alliance.setAllianceId(allianceId);
		alliance.setAbbr(abbrName);
		alliance.setAllianceName(allianceName);
		AllianceAnounce allianceAnounce = new AllianceAnounce();
		allianceAnounce.setContent(announce);
		allianceAnounce.setSignName(alliance.getLeaderName());
		String updateAnnounce = JsonUtil.toJSON(allianceAnounce);
		alliance.setAnnounce(updateAnnounce);
		alliance.setLeaderId(player.getId());
		alliance.setLeaderName(player.getName());
		alliance.setLevel(1);
		alliance.setIcon(icon);
		alliance.setLanguage(language);
		//创建联盟默认开启自动审批
		alliance.setAuto(true);
		alliance.setRefreshTime(System.currentTimeMillis());
		
		// 生成战队
		for (int i = 1; i <= 4; i++) {
			AllianceTeam allianceTeam = new AllianceTeam();
			allianceTeam.setAllianceId(allianceId);
			allianceTeam.setTeamId(String.format("%s#%s", allianceId, i));
			alliance.getTeamMap().put(allianceTeam.getTeamId(), allianceTeam);
		}
		
		ArmyPir armyPir = ArmyPirFactory.get(alliance.getLevel());
		alliance.setCurMemeber(1);
		alliance.setMaxMemeber(armyPir.getArmyMax());
		alliance.setFightPower(FightPowerKit.FINAL_POWER.getValue(player.getId()).longValue());
		InjectorUtil.getInjector().dbCacheService.create(alliance);
		
		//初始化联盟扩展信息
		AllianceExt allianceExt = new AllianceExt();
		allianceExt.setAllianceId(alliance.getId());
		allianceExt.setRefreshTime(System.currentTimeMillis());
		InjectorUtil.getInjector().dbCacheService.create(allianceExt);
		
		// 扣除玩家资源
		if(costResource != null && !costResource.isEmpty()) {
			for(Map.Entry<Integer, Integer> entry : costResource.entrySet()) {
				CurrencyUtil.decrementFinal(player, entry.getKey(), entry.getValue(), GameLogSource.CREATE_ALLILANCE);
			}
		}
		
		// 首次加入军团
		playerAlliance.setAllianceId(allianceId);
		playerAlliance.setAlliancePermission(playerAlliance.newLeaderPermission());
		playerAlliance.setJoinTime(System.currentTimeMillis());
		if(playerAlliance.isFirstRewards()) {
			AwardUtil.awardToCenter(player, GlobalPirFactory.getInstance().firstJoinRewards, SystemEnum.ALLIANCE.ordinal(), GameLogSource.ALLIANCE_JOIN);
			
			// 玩家联盟信息
			playerAlliance.setFirstRewards(false);
		}

		// 清空申请列表
		if(playerAlliance.getApplyList() != null && !playerAlliance.getApplyList().isEmpty()) {
			for(Long playerId : playerAlliance.getApplyList()) {
				Alliance alliance2 = InjectorUtil.getInjector().dbCacheService.get(Alliance.class, playerId);
				if(alliance2 != null) {
					synchronized (alliance2) {
						alliance2.getApplyList().remove(playerId);
						InjectorUtil.getInjector().dbCacheService.update(alliance2);
					}
				}
			}
			playerAlliance.getApplyList().clear();
		}
		
		playerAlliance.getInviteList().clear();
		InjectorUtil.getInjector().dbCacheService.update(playerAlliance);
		
		//设置玩家军团扩展信息
		PlayerAllianceExt playerAllianceExt = playerAllianceExtManager.getOrCreate(player.getId());
		playerAllianceExt.setAllianceId(alliance.getId());
		InjectorUtil.getInjector().dbCacheService.update(playerAllianceExt);
		
		// 联盟缓存信息
		allianceIdNameMap.put(alliance.getId(), allianceName);
		allianceNameIdMap.put(allianceName, alliance.getId());
		allianceAbbrNameIdMap.put(abbrName, alliance.getId());
		smartRank.compareAndRefresh(AllianceRankable.valueOf(allianceId, alliance.getLevel(), alliance.getFightPower()));
		recommendPlayerManager.removeRank(player.getId());
		
		AllianceJoinEventObject allianceJoinEventObject = new AllianceJoinEventObject(player , EventTypeConst.EVENT_ALLIANCE_JOIN, allianceId,alliance.getAbbr(),alliance.getAllianceName());
		this.gameLog.fireEvent(allianceJoinEventObject);
		
		// 推送资源变化
		CurrencyUtil.send(player);
		
		// 返回联盟信息
		ResCreateAllianceMessage resCreateAllianceMessage = new ResCreateAllianceMessage();
		resCreateAllianceMessage.alliance = AllianceConverter.converterAllianceBean(alliance);
		resCreateAllianceMessage.allianceMemberBean = AllianceConverter.converterAllianceMember(player, playerAlliance);
		player.send(resCreateAllianceMessage);
		
		//换团援建处理 已发送帮助的，需要判断该任务帮助次数是否已满，未满的需要在新军团自动发送一次帮助
		Map<Long,Long> map = player.roleInfo().getTimerTaskMap();
		for(Long taskId : map.keySet()){
			TimerTaskData timerTaskData = timerTaskManager.getTimerTaskData(taskId);
			if(null != timerTaskData && !StringUtils.isBlank(timerTaskData.getHelpId())){
				//获取援建信息
				HelpInfo helpInfo = helpInfoManager.get(Long.valueOf(timerTaskData.getHelpId()));
				if(null != helpInfo){
					helpInfo.setAllianceId(alliance.getAllianceId());
					InjectorUtil.getInjector().dbCacheService.create(helpInfo);
				}
				
				if(helpInfo.getNowcount() < helpInfo.getMaxcount()){
					synchronized(alliance) {
						alliance.getHelpList().add(helpInfo.getHelpId());
						InjectorUtil.getInjector().dbCacheService.update(alliance);
					}
					
					// 推送联盟玩家
					ResSendHelperMessage resSendHelperMessage = new ResSendHelperMessage();
					resSendHelperMessage.allianceHelpe = AllianceConverter.converterAllianceHelp(player, helpInfo, "",0);
					sessionManager.writePlayers(alliance.getAllianceMember(), resSendHelperMessage);
					EventBus.getSelf().fireEvent(new AllianceAidEventObject(player));
				}
			}
		}
		
		//初始化事件信息
		eventManager.allianceCreateEventInit(alliance);
		
		// 推送系统公告
		noticeManager.sendWorldNotice(NoticeConstant.CREATE_ALLIANCE, player.getName(), alliance.getAllianceName());
		
		// 推送军团创建成功
		Language.SUCCESSTIP.send(player, SuccessTipEnum.E1007_ALLIANCE.CODE3);
		
		//修改跨服玩家信息
		CrossPlayer crossPlayer = crossPlayerManager.getCrossPlayer(player.getId());
		if(null != crossPlayer){
			SimpleRoleInfo simpleRoleInfo = crossPlayer.getSimpleRoleInfo();
			simpleRoleInfo.setAllianceId(alliance.getId());
			simpleRoleInfo.setAllianceAbbr(alliance.getAbbr());
			simpleRoleInfo.setAllianceName(alliance.getAllianceName());
			String[] allianceTitle = playerAllianceManager.getAllianceTitle(player.getId());
			simpleRoleInfo.setAllianceTitle(allianceTitle[0]);
			simpleRoleInfo.setAllianceTitleName(allianceTitle[1]);
			crossPlayerManager.saveSimpleRoleInfo(simpleRoleInfo);
		}
		
		EventBus.getSelf().fireEvent(new CreateAllianceEventObject(player, EventTypeConst.EVENT_CREATE_ALLIANCE, allianceName, abbrName, allianceId));
	}
	
	/**
	 * 获取计入联盟申请邀请信息
	 * @param player
	 */
	public void getPlayerAllianceInfo(Player player) {
		PlayerAlliance playerAlliance = playerAllianceManager.getOrCreate(player.getId());
		
		ResPlayerAllianceInfoMessage resPlayerAllianceInfoMessage = new ResPlayerAllianceInfoMessage();
		// 申请列表
		for(Long allianceId : playerAlliance.getApplyList()) {
			Alliance alliance = this.getRefreshAlliance(allianceId);
			if(alliance != null) {
				resPlayerAllianceInfoMessage.applyList.add(AllianceConverter.converterSimpleAllianceBean(alliance));
			}
		}
		
		// 邀请列表
		for(Long allianceId : playerAlliance.getInviteList()) {
			Alliance alliance = this.getRefreshAlliance(allianceId);
			if(alliance != null) {
				resPlayerAllianceInfoMessage.inviteList.add(AllianceConverter.converterSimpleAllianceBean(alliance));
			}
		}

		player.send(resPlayerAllianceInfoMessage);
		Language.SUCCESSTIP.send(player, SuccessTipEnum.E1007_ALLIANCE.CODE5);
	}
	
	/**
	 * 取消申请
	 * @param player
	 * @param allianceId
	 */
	public void cancelApply(Player player, long allianceId) {
		Alliance alliance = this.getRefreshAlliance(allianceId);
		if(alliance == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE20);
			return;
		}
		
		// 联盟申请列表
		synchronized (alliance) {
			alliance.getApplyList().remove(player.getId());
			InjectorUtil.getInjector().dbCacheService.update(alliance);
		}
		
		PlayerAlliance playerAlliance = playerAllianceManager.getOrCreate(player.getId());
		playerAlliance.getApplyList().remove(allianceId);
		InjectorUtil.getInjector().dbCacheService.update(playerAlliance);
		
		// TODO 推送有权限的成员
		ResCancelApplyMessage resCancelApplyMessage = new ResCancelApplyMessage();
		resCancelApplyMessage.allianceId = allianceId;
		player.send(resCancelApplyMessage);
		
		Language.SUCCESSTIP.send(player, SuccessTipEnum.E1007_ALLIANCE.CODE6);
	}
	
	/**
	 * 处理联盟邀请
	 * @param player
	 * @param allianceId
	 * @param result
	 */
	public void dealAllianceInvite(Player player, long allianceId, boolean result) {
		
		Alliance alliance = this.getRefreshAlliance(allianceId);
		PlayerAlliance playerAlliance = playerAllianceManager.getOrCreate(player.getId());
		if(result){
			if(alliance == null) {
				Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE20);
				return;
			}
			synchronized (alliance) {
				if(alliance.getCurMemeber() >= alliance.getMaxMemeber()) {
					Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE12);
					return;
				}
				if(playerAlliance.getAllianceId() > 0) {
					Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE13);
					return;
				}
				doJoinAlliance(player, playerAlliance, alliance);
			}
		} else {
			playerAlliance.getInviteList().remove(allianceId);
			InjectorUtil.getInjector().dbCacheService.update(playerAlliance);
		}
		
		if(result){
			ResAllianceDataMessage resAllianceDataMessage = new ResAllianceDataMessage();
			resAllianceDataMessage.alliance = AllianceConverter.converterAllianceBean(alliance);
			resAllianceDataMessage.playerAlliance = AllianceConverter.converterAllianceMember(player, playerAlliance);
			player.send(resAllianceDataMessage);
		}
		
		ResDealInviteMessage resDealInviteMessage = new ResDealInviteMessage();
		resDealInviteMessage.allianceId = allianceId;
		resDealInviteMessage.result = result?1:2;
		player.send(resDealInviteMessage);

		// 联盟信息变更
		ResAllianceChangeMessage resAllianceChangeMessage = new ResAllianceChangeMessage();
		resAllianceChangeMessage.alliance = AllianceConverter.converterAllianceBean(alliance);
		sessionManager.writePlayers(alliance.getAllianceMember(), resAllianceChangeMessage);
		
		if(result){
			Language.SUCCESSTIP.send(player, SuccessTipEnum.E1007_ALLIANCE.CODE4);
		}else{
			Language.SUCCESSTIP.send(player, SuccessTipEnum.E1007_ALLIANCE.CODE24);
		}
		
	}
	
	/**
	 * 处理加入联盟(这个方法非线程安全，需要在外层加锁)
	 * @param player
	 * @param alliance
	 */
	private void doJoinAlliance(Player player, PlayerAlliance playerAlliance, Alliance alliance) {
		if(alliance.getCurMemeber() >= alliance.getMaxMemeber()) {
			log.error("人数超过上线,无法处理加入...............");
			return;
		}
		
		alliance.getRankOne().add(player.getId());
		alliance.setCurMemeber(alliance.getCurMemeber() + 1);
		alliance.setFightPower(alliance.getFightPower() + FightPowerKit.FINAL_POWER.getValue(player.getId()).longValue());
		InjectorUtil.getInjector().dbCacheService.update(alliance);
		smartRank.compareAndRefresh(AllianceRankable.valueOf(alliance.getId(), alliance.getLevel(), alliance.getFightPower()));
		
		playerAlliance.setAllianceId(alliance.getId());
		playerAlliance.setAllianceRank(1);
		playerAlliance.setJoinTime(System.currentTimeMillis());
		
		if(playerAlliance.isFirstRewards()) {
			AwardUtil.awardToCenter(player, GlobalPirFactory.getInstance().firstJoinRewards, SystemEnum.ALLIANCE.ordinal(), GameLogSource.ALLIANCE_JOIN);
			
			// 玩家联盟信息
			playerAlliance.setFirstRewards(false);
		}

		// 清空申请列表
		if (playerAlliance.getApplyList() != null && !playerAlliance.getApplyList().isEmpty()) {
			for (Long allianceId : playerAlliance.getApplyList()) {
				Alliance alliance2 = InjectorUtil.getInjector().dbCacheService.get(Alliance.class, allianceId);
				if (alliance2 != null) {
					synchronized (alliance2) {
						alliance2.getApplyList().remove(player.getId());
						InjectorUtil.getInjector().dbCacheService.update(alliance2);
					}
				}
			}
		}
		
		playerAlliance.getApplyList().clear();
		playerAlliance.getInviteList().clear();
		InjectorUtil.getInjector().dbCacheService.update(playerAlliance);
		//设置玩家军团扩展信息
		PlayerAllianceExt playerAllianceExt = playerAllianceExtManager.getOrCreate(player.getId());
		playerAllianceExt.setAllianceId(alliance.getId());
		InjectorUtil.getInjector().dbCacheService.update(playerAllianceExt);
		
		AllianceJoinEventObject allianceJoinEventObject = new AllianceJoinEventObject(player , EventTypeConst.EVENT_ALLIANCE_JOIN, alliance.getId(),alliance.getAbbr(),alliance.getAllianceName());
		this.gameLog.fireEvent(allianceJoinEventObject);
		
		//换团援建处理 已发送帮助的，需要判断该任务帮助次数是否已满，未满的需要在新军团自动发送一次帮助
		Map<Long,Long> map = player.roleInfo().getTimerTaskMap();
		for(Long taskId : map.keySet()){
			TimerTaskData timerTaskData = timerTaskManager.getTimerTaskData(taskId);
			if(null != timerTaskData && !StringUtils.isEmpty(timerTaskData.getHelpId())){
				//获取援建信息
				HelpInfo helpInfo = helpInfoManager.get(Long.valueOf(timerTaskData.getHelpId()));
				if(null != helpInfo){
					helpInfo.setAllianceId(alliance.getAllianceId());
					InjectorUtil.getInjector().dbCacheService.create(helpInfo);
				}
				
				if(helpInfo.getNowcount() < helpInfo.getMaxcount()){
					synchronized(alliance) {
						alliance.getHelpList().add(helpInfo.getHelpId());
						InjectorUtil.getInjector().dbCacheService.update(alliance);
					}
					
					// 推送联盟玩家
					ResSendHelperMessage resSendHelperMessage = new ResSendHelperMessage();
					resSendHelperMessage.allianceHelpe = AllianceConverter.converterAllianceHelp(player, helpInfo, "",0);
					//过滤已援建玩家
					Set<Long> playerIds = alliance.getAllianceMember();
					playerIds.removeAll(helpInfo.getHelperIds());
					sessionManager.writePlayers(playerIds, resSendHelperMessage);
					EventBus.getSelf().fireEvent(new AllianceAidEventObject(player));
				}
			}
		}
		
		// 移除推荐排行
		recommendPlayerManager.removeRank(player.getId());
		
		//修改跨服玩家信息
		CrossPlayer crossPlayer = crossPlayerManager.getCrossPlayer(player.getId());
		if(null != crossPlayer){
			SimpleRoleInfo simpleRoleInfo = crossPlayer.getSimpleRoleInfo();
			simpleRoleInfo.setAllianceId(alliance.getId());
			simpleRoleInfo.setAllianceAbbr(alliance.getAbbr());
			simpleRoleInfo.setAllianceName(alliance.getAllianceName());
			crossPlayerManager.saveSimpleRoleInfo(simpleRoleInfo);
		}
	}
	
	/**
	 * 联盟升级
	 * @param player
	 * @param allianceId
	 */
	public void levelup(Player player, long allianceId) {
		
		Alliance alliance = this.getRefreshAlliance(allianceId);
		if(alliance == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE20);
			return;
		}
		
		PlayerAlliance playerAlliance = playerAllianceManager.getOrCreate(player.getId());
		if(playerAlliance.getAllianceId() != alliance.getId()) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		
		if(alliance.getLeaderId() != player.getId()) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE15);
			return;
		}
		
		synchronized (alliance) {
			ArmyPir armyPir = ArmyPirFactory.get(alliance.getLevel() + 1);
			if(alliance.getCash() < armyPir.getCost_armyMoney()) {
				Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE14);
				return;
			}
			
			alliance.setCash(alliance.getCash() - armyPir.getCost_armyMoney());
			alliance.setLevel(alliance.getLevel() + 1);
			alliance.setMaxMemeber(armyPir.getArmyMax());
			
			InjectorUtil.getInjector().dbCacheService.update(alliance);
		}
		
		EventBus.getSelf().fireEvent(new LevelupAllianceEventObject(player, EventTypeConst.EVENT_LEVELUP_ALLIANCE, alliance.getLevel() - 1, alliance.getLevel()));
		
		// 推送联盟升级
		ResLevelupAllianceMessage resLevelupAllianceMessage  = new ResLevelupAllianceMessage();
		resLevelupAllianceMessage.alliance = AllianceConverter.converterAllianceBean(alliance);
		player.send(resLevelupAllianceMessage);
		
		ResAllianceChangeMessage resAllianceChangeMessage = new ResAllianceChangeMessage();
		resAllianceChangeMessage.alliance = AllianceConverter.converterAllianceBean(alliance);
		sessionManager.writePlayers(alliance.getAllianceMember(), resAllianceChangeMessage);
		
		// 发送军团公告
		noticeManager.sendAllianceNotice(player, alliance, NoticeConstant.ALLIANCE_LEVELUP, alliance.getAllianceName(), alliance.getLevel());
		
		// 返回升级成功
		Language.SUCCESSTIP.send(player, SuccessTipEnum.E1007_ALLIANCE.CODE7, alliance.getLevel());
	}
	
	/**
	 * 捐献
	 * @param player
	 * @param allianceId
	 * @param type 类型 {@link CurrencyUtil}
	 */
	public void donate(Player player, long allianceId, int type) {
		
		Alliance alliance = this.getRefreshAlliance(allianceId);
		if(alliance == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE20);
			return;
		}

		PlayerAlliance playerAlliance = playerAllianceManager.getOrCreate(player.getId());
		if(playerAlliance.getAllianceId() != alliance.getId()) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		
		int noticeDonateType = 0;
		int noticeDonateCount = 0;
		int donateLevel = 0;
		int addCash = 0;
		int addDonate = 0;
		if(type == CurrencyEnum.OIL.ordinal()) {
			ArmyDonatePir armyDonatePir = ArmyDonatePirFactory.get(playerAlliance.getOilDonateCount() + 1);
			if(armyDonatePir != null && armyDonatePir.getCost_oil() <= -1) {
				Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE17);
				return;
			}
			
			if(!CurrencyUtil.verifyFinal(player, type, armyDonatePir.getCost_oil())) {
				Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE18);
				return;
			}
			
			CurrencyUtil.decrementFinal(player, type, armyDonatePir.getCost_oil(), GameLogSource.ALLIANCE_DONATE);
		
			synchronized (alliance) {
				alliance.setCash(alliance.getCash() + armyDonatePir.getAddArmyMoney1());
				InjectorUtil.getInjector().dbCacheService.update(alliance);
			}
			
			playerAlliance.addDonate(armyDonatePir.getGX1_1());
			playerAlliance.setOilDonateCount(armyDonatePir.getNum());
			InjectorUtil.getInjector().dbCacheService.update(playerAlliance);
			
			noticeDonateType = CurrencyEnum.OIL.ordinal();
			noticeDonateCount = armyDonatePir.getCost_oil();
			addCash = armyDonatePir.getAddArmyMoney1();
			addDonate = armyDonatePir.getGX1_1();
			donateLevel = armyDonatePir.getNum();
		
		} else if(type == CurrencyEnum.RARE.ordinal()) {
			ArmyDonatePir armyDonatePir = ArmyDonatePirFactory.get(playerAlliance.getRareDonateCount() + 1);
			if(armyDonatePir != null && armyDonatePir.getCost_earth() <= -1) {
				Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE17);
				return;
			}
			
			if(!CurrencyUtil.verifyFinal(player, type, armyDonatePir.getCost_earth())) {
				Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE18);
				return;
			}
			
			CurrencyUtil.decrementFinal(player, type, armyDonatePir.getCost_earth(), GameLogSource.ALLIANCE_DONATE);
			
			synchronized (alliance) {
				alliance.setCash(alliance.getCash() + armyDonatePir.getAddArmyMoney1());
				InjectorUtil.getInjector().dbCacheService.update(alliance);
			}
			
			playerAlliance.addDonate(armyDonatePir.getGX1_1());
			playerAlliance.setRareDonateCount(armyDonatePir.getNum());
			InjectorUtil.getInjector().dbCacheService.update(playerAlliance);
			
			noticeDonateType = CurrencyEnum.RARE.ordinal();
			noticeDonateCount = armyDonatePir.getCost_earth();
			addCash = armyDonatePir.getAddArmyMoney1();
			addDonate = armyDonatePir.getGX1_1();
			donateLevel = armyDonatePir.getNum();
		
		} else if(type == CurrencyEnum.STEEL.ordinal()) {
			
			ArmyDonatePir armyDonatePir = ArmyDonatePirFactory.get(playerAlliance.getSteelDonateCount() + 1);
			if(armyDonatePir != null && armyDonatePir.getCost_steel() <= -1) {
				Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE17);
				return;
			}
			
			if(!CurrencyUtil.verifyFinal(player, type, armyDonatePir.getCost_steel())) {
				Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE18);
				return;
			}
			
			CurrencyUtil.decrementFinal(player, type, armyDonatePir.getCost_steel(), GameLogSource.ALLIANCE_DONATE);
			
			synchronized (alliance) {
				alliance.setCash(alliance.getCash() + armyDonatePir.getAddArmyMoney1());
				InjectorUtil.getInjector().dbCacheService.update(alliance);
			}
			
			playerAlliance.addDonate(armyDonatePir.getGX1_1());
			playerAlliance.setSteelDonateCount(armyDonatePir.getNum());
			InjectorUtil.getInjector().dbCacheService.update(playerAlliance);
			
			noticeDonateType = CurrencyEnum.STEEL.ordinal();
			noticeDonateCount = armyDonatePir.getCost_steel();
			addCash = armyDonatePir.getAddArmyMoney1();
			addDonate = armyDonatePir.getGX1_1();
			donateLevel = armyDonatePir.getNum();
		
		} else if(type == CurrencyEnum.GLOD.ordinal()) {
			
			ArmyDonatePir armyDonatePir = ArmyDonatePirFactory.get(playerAlliance.getMoneyDonateCount() + 1);
			if(armyDonatePir != null && armyDonatePir.getCost_cash() <= -1) {
				Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE17);
				return;
			}
			
			if(!CurrencyUtil.verifyFinal(player, type, armyDonatePir.getCost_cash())) {
				Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE18);
				return;
			}
			
			CurrencyUtil.decrementFinal(player, type, armyDonatePir.getCost_cash(), GameLogSource.ALLIANCE_DONATE);
			
			synchronized (alliance) {
				alliance.setCash(alliance.getCash() + armyDonatePir.getAddArmyMoney1());
				InjectorUtil.getInjector().dbCacheService.update(alliance);
			}
			
			playerAlliance.addDonate(armyDonatePir.getGX1_1());
			playerAlliance.setMoneyDonateCount(armyDonatePir.getNum());
			InjectorUtil.getInjector().dbCacheService.update(playerAlliance);
			
			noticeDonateType = CurrencyEnum.GLOD.ordinal();
			noticeDonateCount = armyDonatePir.getCost_cash();
			addCash = armyDonatePir.getAddArmyMoney1();
			addDonate = armyDonatePir.getGX1_1();
			donateLevel = armyDonatePir.getNum();
		
		} else if(type == CurrencyEnum.DIAMOND.ordinal()) {
			
			ArmyDonatePir armyDonatePir = ArmyDonatePirFactory.get(playerAlliance.getDiamondDonateCount() + 1);
			if(armyDonatePir == null || armyDonatePir.getCost_RMB() <= -1) {
				Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE17);
				return;
			}
			
			if(!CurrencyUtil.verifyFinal(player, type, armyDonatePir.getCost_RMB())) {
				Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE18);
				return;
			}
			
			CurrencyUtil.decrementFinal(player, type, armyDonatePir.getCost_RMB(), GameLogSource.ALLIANCE_DONATE);
			
			synchronized (alliance) {
				alliance.setCash(alliance.getCash() + armyDonatePir.getAddArmyMoney2());
				InjectorUtil.getInjector().dbCacheService.update(alliance);
			}
			
			playerAlliance.addDonate(armyDonatePir.getGX1_2());
			playerAlliance.setDiamondDonateCount(armyDonatePir.getNum());
			InjectorUtil.getInjector().dbCacheService.update(playerAlliance);
			
			noticeDonateType = CurrencyEnum.DIAMOND.ordinal();
			noticeDonateCount = armyDonatePir.getCost_RMB();
			addCash = armyDonatePir.getAddArmyMoney2();
			addDonate = armyDonatePir.getGX1_2();
			donateLevel = armyDonatePir.getNum();
		} else {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE15);
			return;
		}
		
		CurrencyUtil.send(player);
		
		if(noticeDonateType != CurrencyEnum.DIAMOND.ordinal() && donateLevel >= DONATE_RESOURCE_NOTICE_COUNT) {
			noticeManager.sendAllianceNotice(player, alliance, NoticeConstant.NOTICE_ALLIANCE_DOATE_RESOURCE, player.getName(), noticeDonateType, noticeDonateCount);
		} else if(noticeDonateType == CurrencyEnum.DIAMOND.ordinal() && donateLevel >= DONATE_DIAMOND_NOTICE_COUNT) {
			noticeManager.sendAllianceNotice(player, alliance, NoticeConstant.NOTICE_ALLIANCE_DOATE_DIAMOND, player.getName(), noticeDonateType, noticeDonateCount);
		}
		
		ResDonateMessage resDonateMessage = new ResDonateMessage();
		resDonateMessage.alliance = AllianceConverter.converterAllianceBean(alliance);
		resDonateMessage.allianceMember = AllianceConverter.converterAllianceMember(player, playerAlliance);
		player.send(resDonateMessage);
		
		ResAllianceChangeMessage resAllianceChangeMessage = new ResAllianceChangeMessage();
		resAllianceChangeMessage.alliance = AllianceConverter.converterAllianceBean(alliance);
		sessionManager.writePlayers(alliance.getAllianceMember(), resAllianceChangeMessage);
		EventBus.getSelf().fireEvent(new DonateEventObject(player, donateLevel, EnumUtils.getEnum(CurrencyEnum.class, type), noticeDonateCount, AllianceDonateType.ALLIANCE.ordinal()));
		
		Language.SUCCESSTIP.send(player, SuccessTipEnum.E1007_ALLIANCE.CODE8,  addCash, addDonate);
	}
	
	/**
	 * 获取联盟成员
	 * @param player
	 * @param allianceId
	 * @param type(1.战力2.领地3.坐标4.离线时间)
	 */
	public void getAllianceMember(Player player, long allianceId, int type) {
		
		Alliance alliance = this.getRefreshAlliance(allianceId);
		if(alliance == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE20);
			return;
		}
		
		PlayerAlliance playerAlliance = playerAllianceManager.getOrCreate(player.getId());
		if(playerAlliance.getAllianceId() != alliance.getId()) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		
		// 
		ResQueryAllianceMemeberMessage resQueryAllianceMemeberMessage = new ResQueryAllianceMemeberMessage();
		if(type == AllianceConstant.ALLIANCE_MEMBER_TYPE_FIGHTPOWER) {
			resQueryAllianceMemeberMessage.rankFive = AllianceConverter.generateFightPowerView(alliance.getRankFive());
			resQueryAllianceMemeberMessage.rankFour = AllianceConverter.generateFightPowerView(alliance.getRankFour());
			resQueryAllianceMemeberMessage.rankThree= AllianceConverter.generateFightPowerView(alliance.getRankThree());
			resQueryAllianceMemeberMessage.rankTwo = AllianceConverter.generateFightPowerView(alliance.getRankTwo());
			resQueryAllianceMemeberMessage.rankOne = AllianceConverter.generateFightPowerView(alliance.getRankOne());
			resQueryAllianceMemeberMessage.leader = AllianceConverter.factoryAlliancePlayerViewBean(alliance.getLeaderId());
		} else if(type == AllianceConstant.ALLIANCE_MEMBER_TYPE_POSITION) {
			
			if(playerAlliance.getId() != alliance.getLeaderId() && !alliance.checkPlayerR5(player.getId())) {
				Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE15);
				return;
			}
			
			resQueryAllianceMemeberMessage.rankFive = AllianceConverter.generatePositionView(alliance.getRankFive());
			resQueryAllianceMemeberMessage.rankFour = AllianceConverter.generatePositionView(alliance.getRankFour());
			resQueryAllianceMemeberMessage.rankThree= AllianceConverter.generatePositionView(alliance.getRankThree());
			resQueryAllianceMemeberMessage.rankTwo = AllianceConverter.generatePositionView(alliance.getRankTwo());
			resQueryAllianceMemeberMessage.rankOne = AllianceConverter.generatePositionView(alliance.getRankOne());
			resQueryAllianceMemeberMessage.leader = AllianceConverter.factoryPlayerPositionView(alliance.getLeaderId());
		} else if(type == AllianceConstant.ALLIANCE_MEMBER_TYPE_TERRITORY) {
			
			resQueryAllianceMemeberMessage.rankFive = AllianceConverter.generateTerritoryView(alliance.getRankFive());
			resQueryAllianceMemeberMessage.rankFour = AllianceConverter.generateTerritoryView(alliance.getRankFour());
			resQueryAllianceMemeberMessage.rankThree= AllianceConverter.generateTerritoryView(alliance.getRankThree());
			resQueryAllianceMemeberMessage.rankTwo = AllianceConverter.generateTerritoryView(alliance.getRankTwo());
			resQueryAllianceMemeberMessage.rankOne = AllianceConverter.generateTerritoryView(alliance.getRankOne());
			resQueryAllianceMemeberMessage.leader = AllianceConverter.factoryTerritoryPlayerView(alliance.getLeaderId());
		} else if(type == AllianceConstant.ALLIANCE_MEMBER_TYPE_OFFLINE) {
			
			if(playerAlliance.getId() != alliance.getLeaderId() && !alliance.checkPlayerR5(player.getId()) && !alliance.checkPlayerR4(player.getId())) {
				Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE15);
				return;
			}
			
			resQueryAllianceMemeberMessage.rankFive = AllianceConverter.generateOfflineView(alliance.getRankFive());
			resQueryAllianceMemeberMessage.rankFour = AllianceConverter.generateOfflineView(alliance.getRankFour());
			resQueryAllianceMemeberMessage.rankThree= AllianceConverter.generateOfflineView(alliance.getRankThree());
			resQueryAllianceMemeberMessage.rankTwo = AllianceConverter.generateOfflineView(alliance.getRankTwo());
			resQueryAllianceMemeberMessage.rankOne = AllianceConverter.generateOfflineView(alliance.getRankOne());
			resQueryAllianceMemeberMessage.leader = AllianceConverter.factoryPlayerOfflineView(alliance.getLeaderId());
		}
		
		player.send(resQueryAllianceMemeberMessage);
		System.out.println("------------------"+JsonUtil.toJSON(resQueryAllianceMemeberMessage));
	}
	
	/**
	 * 获取联盟官员信息
	 * @param player
	 * @param allianceId
	 */
	public void getAllianceOffice(Player player, long allianceId) {
		
		Alliance alliance = this.getRefreshAlliance(allianceId);
		if(alliance == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE20);
			return;
		}
		
		PlayerAlliance playerAlliance = playerAllianceManager.getOrCreate(player.getId());
		if(playerAlliance.getAllianceId() != alliance.getId()) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		
		ResAllianceOfficeMessage resAllianceOfficeMessage = new ResAllianceOfficeMessage();
		if(alliance.getOffice1Player() > 0) {
			AlliancePlayerViewBean alliancePlayerViewBean = AllianceConverter.factoryAlliancePlayerViewBean(alliance.getOffice1Player());
			resAllianceOfficeMessage.allianceOfficeList.add(AllianceConverter.converterAllianceOffice(alliancePlayerViewBean, alliance.getAllianceTitle().getOffice1Name(), 1));	
		}
		
		if(alliance.getOffice2Player() > 0) {
			AlliancePlayerViewBean alliancePlayerViewBean = AllianceConverter.factoryAlliancePlayerViewBean(alliance.getOffice2Player());
			resAllianceOfficeMessage.allianceOfficeList.add(AllianceConverter.converterAllianceOffice(alliancePlayerViewBean, alliance.getAllianceTitle().getOffice2Name(), 2));	
		}
		
		if(alliance.getOffice3Player() > 0) {
			AlliancePlayerViewBean alliancePlayerViewBean = AllianceConverter.factoryAlliancePlayerViewBean(alliance.getOffice3Player());
			resAllianceOfficeMessage.allianceOfficeList.add(AllianceConverter.converterAllianceOffice(alliancePlayerViewBean, alliance.getAllianceTitle().getOffice3Name(), 3));	
		}
		
		if(alliance.getOffice4Player() > 0) {
			AlliancePlayerViewBean alliancePlayerViewBean = AllianceConverter.factoryAlliancePlayerViewBean(alliance.getOffice4Player());
			resAllianceOfficeMessage.allianceOfficeList.add(AllianceConverter.converterAllianceOffice(alliancePlayerViewBean, alliance.getAllianceTitle().getOffice4Name(), 4));	
		}
		
		// 战队信息
		for(java.util.Map.Entry<String, AllianceTeam> entry : alliance.getTeamMap().entrySet()) {
			AllianceTeam aliAllianceTeam = entry.getValue();
			if(aliAllianceTeam.getTeamLeaderId() > 0) {
				AlliancePlayerViewBean leader = AllianceConverter.factoryAlliancePlayerViewBean(aliAllianceTeam.getTeamLeaderId());
				AllianceTeamBean allianceTeamBean = AllianceConverter.converterAllianceTeam(aliAllianceTeam, leader);
				resAllianceOfficeMessage.allianceTeamList.add(allianceTeamBean);
			}
		}
		
		player.send(resAllianceOfficeMessage);
	}
	
	/**
	 * 查看联盟成员列表
	 * @param player
	 * @param allianceId
	 * @param teamId
	 */
	public void viewTeamMemberList(Player player, long allianceId, String teamId) {
		
		PlayerAlliance playerAlliance = playerAllianceManager.getOrCreate(player.getId());
		if(playerAlliance.getAllianceId() <= 0) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		
		Alliance alliance = this.getRefreshAlliance(playerAlliance.getAllianceId());
		if(alliance == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE20);
			return;
		}
		
		ResViewTeamMemberMessage resViewTeamMemberMessage = new ResViewTeamMemberMessage();
		AllianceTeam allianceTeam = alliance.getTeamMap().get(teamId);
		if(allianceTeam != null) {
			for(Long id : allianceTeam.getTeamMemberIds()) {
				AlliancePlayerViewBean playerView = AllianceConverter.factoryAlliancePlayerViewBean(id);
				resViewTeamMemberMessage.allianceMember.add(playerView);
			}
		}
		resViewTeamMemberMessage.teamId = teamId;
		player.send(resViewTeamMemberMessage);
	}
	
	/**
	 * 编辑战队信息
	 * @param player
	 * @param teamId
	 * @param teamName
	 * @param icon
	 */
	public void editTeam(Player player, long allianceId, String teamId, String teamName, String icon) {
		
		if(StringUtils.isEmpty(teamName) || StringUtils.isEmpty(icon) || teamName.length() < 3 || teamName.length() > 10 || icon.length() > 100) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE15);
			return;
		}
		
		Alliance alliance = this.getRefreshAlliance(allianceId);
		if(alliance == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE20);
			return;
		}
		
		PlayerAlliance playerAlliance = playerAllianceManager.getOrCreate(player.getId());
		if(playerAlliance.getAllianceId() != alliance.getId()) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		
		AllianceTeam allianceTeam = alliance.getAllianceTeam(teamId);
		if(allianceTeam == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE19);
			return;
		}
		
		if(player.getId() != allianceTeam.getTeamLeaderId()) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE21);
			return;
		}
		
		if(allianceTeam.getEditTime() +  7 * 24 * 3600 * 1000 > System.currentTimeMillis()) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE20);
			return;
		}
		
		synchronized (alliance) {
			allianceTeam.setIcon(icon);
			allianceTeam.setTeamName(teamName);
			allianceTeam.setEditTime(System.currentTimeMillis());
			InjectorUtil.getInjector().dbCacheService.update(alliance);
			
			//修改跨服玩家信息
			this.changeSimpleRoleInfoOfAllianceTitle(allianceTeam.getTeamLeaderId());
		}	
		
		// 广播联战队编辑
		ResEditTeamMessage resEditTeamMessage = new ResEditTeamMessage();
		AlliancePlayerViewBean leaderView = AllianceConverter.factoryAlliancePlayerViewBean(allianceTeam.getTeamLeaderId());
		resEditTeamMessage.allianceTeam = AllianceConverter.converterAllianceTeam(allianceTeam, leaderView);
	
		// 广播联盟玩家
		sessionManager.writePlayers(alliance.getAllianceMember(), resEditTeamMessage);
		
		Language.SUCCESSTIP.send(player, SuccessTipEnum.E1007_ALLIANCE.CODE11);
	}
	
	/**
	 * 添加战队成员
	 * @param player
	 * @param allianceId
	 * @param targetPlayerId
	 * @param teamId
	 */
	public void addTeamMember(Player player, long allianceId, long targetPlayerId, String teamId) {
		
		Alliance alliance = this.getRefreshAlliance(allianceId);
		if(alliance == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE20);
			return;
		}
		
		PlayerAlliance playerAlliance = playerAllianceManager.getOrCreate(player.getId());
		if(playerAlliance.getAllianceId() != alliance.getId()) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		
		AllianceTeam allianceTeam = alliance.getAllianceTeam(teamId);
		if(allianceTeam == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE19);
			return;
		}
		
		if(player.getId() != allianceTeam.getTeamLeaderId()) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE21);
			return;
		}
		
		if(allianceTeam.getTeamLeaderId() == targetPlayerId) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE15);
			return;
		}
		
		Player targetPlayer = InjectorUtil.getInjector().dbCacheService.get(Player.class, targetPlayerId);
		if(targetPlayer == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE4);
			return;
		}
		
		PlayerAlliance targetPlayerAlliance = playerAllianceManager.getOrCreate(targetPlayerId);
		if(targetPlayerAlliance.getAllianceId() != alliance.getId()) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		
		if(!StringUtils.isEmpty(targetPlayerAlliance.getTeamId())) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE23);
			return;
		}
		
		ArmyPir armyPir = ArmyPirFactory.get(alliance.getLevel());
		synchronized (alliance) {
			if(allianceTeam.getTeamNum() >= armyPir.getTeamMax()) {
				Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE22);
				return;	
			}
			
			allianceTeam.getTeamMemberIds().add(targetPlayerId);
			InjectorUtil.getInjector().dbCacheService.update(alliance);
		}
		
		// 切换玩家队伍
		playerAllianceManager.changePlayerTeamId(targetPlayer, targetPlayerAlliance, teamId);
		
		MailKit.sendSystemEmail(targetPlayerId, EmailTemplet.加入战队_MAIL_ID,ParamFormatUtils.formatParma(EmailConverter.emailOfPlayerName(player.getId(), player.getName()), alliance.getAllianceName()));
		ResAddTeamMemberMessage resAddTeamMemberMessage = new ResAddTeamMemberMessage();
		resAddTeamMemberMessage.teamId = teamId;
		resAddTeamMemberMessage.joinPlayer = AllianceConverter.factoryAlliancePlayerViewBean(targetPlayerId);
		player.send(resAddTeamMemberMessage);
		
		sessionManager.writePlayers(allianceTeam.getTeamMemberIds(), resAddTeamMemberMessage);
		
		Language.SUCCESSTIP.send(player, SuccessTipEnum.E1007_ALLIANCE.CODE12);
	}
	
	/**
	 * 剔除战队成员
	 * @param player
	 * @param allianceId
	 * @param targetPlayerId
	 * @param teamId
	 */
	public void kickTeamMemeber(Player player, long allianceId, long targetPlayerId, String teamId) {
		
		if(player.getId() == targetPlayerId) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE15);
			return;
		}
		
		Alliance alliance = this.getRefreshAlliance(allianceId);
		if(alliance == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE20);
			return;
		}
		
		PlayerAlliance playerAlliance = playerAllianceManager.getOrCreate(player.getId());
		if(playerAlliance.getAllianceId() != alliance.getId()) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		
		AllianceTeam allianceTeam = alliance.getAllianceTeam(teamId);
		if(allianceTeam == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE19);
			return;
		}
		
		Player targetPlayer = InjectorUtil.getInjector().dbCacheService.get(Player.class, targetPlayerId);
		if(targetPlayer == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE4);
			return;
		}
		
		if(player.getId() != allianceTeam.getTeamLeaderId()) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE21);
			return;
		}
		
		PlayerAlliance targetPlayerAlliance = playerAllianceManager.getOrCreate(targetPlayerId);
		if(targetPlayerAlliance.getAllianceId() != alliance.getId()) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		
		if(!allianceTeam.getTeamMemberIds().contains(targetPlayerId)){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE24);
			return;
		}
		
		synchronized (alliance) {
			allianceTeam.getTeamMemberIds().remove(targetPlayerId);
			InjectorUtil.getInjector().dbCacheService.update(alliance);
		}
		
		// 切换玩家队伍
		playerAllianceManager.changePlayerTeamId(targetPlayer, targetPlayerAlliance, "");
		
		String name = (allianceTeam.getTeamName() != null && !allianceTeam.getTeamName().equals("")) ? allianceTeam.getTeamName() : teamId.split(":")[0];
		MailKit.sendSystemEmail(targetPlayerId, EmailTemplet.移出战队_MAIL_ID,ParamFormatUtils.formatParma(EmailConverter.emailOfPlayerName(player.getId(), player.getName()), name));
		ResDeleteTeamMemberMessage resDeleteTeamMemberMessage = new ResDeleteTeamMemberMessage();
		resDeleteTeamMemberMessage.teamId = teamId;
		resDeleteTeamMemberMessage.targetPlayerId = targetPlayerId;
		player.send(resDeleteTeamMemberMessage);
	
		sessionManager.writePlayers(allianceTeam.getTeamMemberIds(), resDeleteTeamMemberMessage);
		
		Language.SUCCESSTIP.send(player, SuccessTipEnum.E1007_ALLIANCE.CODE13);
	}
	
	/**
	 * 转让军团长
	 * @param player
	 * @param allianceId
	 * @param targetPlayerId 目标军团长
	 */
	public void changeAllianceLeader(Player player, long allianceId, long targetPlayerId) {
		
		Alliance alliance = this.getRefreshAlliance(allianceId);
		if(alliance == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE20);
			return;
		}
		
		Player targetPlayer = InjectorUtil.getInjector().dbCacheService.get(Player.class, targetPlayerId);
		if(targetPlayer == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE4);
			return;
		}
		
		PlayerAlliance playerAlliance = playerAllianceManager.getOrCreate(player.getId());
		if(playerAlliance.getAllianceId() != alliance.getId()) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		
		if(player.getId() != alliance.getLeaderId()) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE15);
			return;
		}
		
		PlayerAlliance targetPlayerAlliance = playerAllianceManager.getOrCreate(targetPlayerId);
		if(targetPlayerAlliance.getAllianceId() != alliance.getId()) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		
		if(!alliance.playerInAlliance(player.getId())) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		
		if(!alliance.playerInAlliance(targetPlayerId)) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		
		synchronized (alliance) {
			long orignalLeaderId = alliance.getLeaderId();
			alliance.removeAllianceMember(targetPlayerId);
			alliance.setLeaderId(targetPlayerId);
			alliance.setLeaderName(targetPlayer.getName());
			alliance.addAllianceMemeber(orignalLeaderId, AllianceConstant.RANK_ONE);
			InjectorUtil.getInjector().dbCacheService.update(alliance);
			
			//自己如果为小队队长 需要卸任队长
			Collection<AllianceTeam> collection = alliance.getTeamMap().values();
			if(collection != null) {
				for(AllianceTeam allianceTeam : collection) {
					if(allianceTeam.getTeamLeaderId() == playerAlliance.getId()) {
						allianceTeam.setTeamLeaderId(0);
						playerAlliance.setTeamId("");
					}
				}
			}
			
			//对方如果为官员需要卸任官员
			alliance.removeOffice(targetPlayerId);
			
			playerAllianceManager.changeAllianceLeader(player,playerAlliance);
			playerAllianceManager.changeLeaderPermission(targetPlayer, targetPlayerAlliance);
			
		}
		ResChangeLeaderMessage resChangeLeaderMessage = new ResChangeLeaderMessage();
		AllianceBean allianceBean = AllianceConverter.converterAllianceBean(alliance);
		resChangeLeaderMessage.alliance = allianceBean;
		player.send(resChangeLeaderMessage);
		
		// 军团信息变更 
		ResAllianceChangeMessage resAllianceChangeMessage = new ResAllianceChangeMessage();
		resAllianceChangeMessage.alliance = allianceBean;
		sessionManager.writePlayers(alliance.getAllianceMember(), resAllianceChangeMessage);
		
		//修改跨服玩家信息
		this.changeSimpleRoleInfoOfAllianceTitle(player.getId());
		this.changeSimpleRoleInfoOfAllianceTitle(targetPlayerId);
		
		// 发送军团长任命公告
		noticeManager.sendAllianceNotice(targetPlayer, alliance, NoticeConstant.NOTICE_CHANGE_LEADER, player.getName(), targetPlayer.getName(), AllianceConstant.RANK_SIX);
		Language.SUCCESSTIP.send(player, SuccessTipEnum.E1007_ALLIANCE.CODE14);
		for(Long playerId : alliance.getAllianceMember()) {
			if(!playerId.toString().equals(String.valueOf(targetPlayerId))){
				MailKit.sendSystemEmail(playerId, EmailTemplet.设置新军团_MAIL_ID, ParamFormatUtils.formatParma(EmailConverter.emailOfPlayerName(targetPlayer.getId(), targetPlayer.getName()), alliance.getAllianceName()));
			}
		}
		MailKit.sendSystemEmail(targetPlayerId, EmailTemplet.军团长职位转让_MAIL_ID, ParamFormatUtils.formatParma(EmailConverter.emailOfPlayerName(player.getId(), player.getName()), alliance.getAllianceName()));
	}
	
	/**
	 * 编辑头衔
	 * @param player
	 * @param allianceId
	 * @param office1Name
	 * @param office2Name
	 * @param office3Name
	 * @param office4Name
	 * @param rank1Name
	 * @param rank2Name
	 * @param rank3Name
	 * @param rank4Name
	 * @param rank5Name
	 */
	public void editTitle(Player player, long allianceId, String office1Name, String office2Name, String office3Name, String office4Name, String rank1Name, String rank2Name, String rank3Name, String rank4Name, String rank5Name) {
		
		if(StringUtils.isBlank(office1Name) && StringUtils.isBlank(office2Name) 
				&& StringUtils.isBlank(office3Name) && StringUtils.isBlank(office4Name) 
				&& StringUtils.isBlank(rank1Name) && StringUtils.isBlank(rank2Name) 
				&& StringUtils.isBlank(rank3Name) && StringUtils.isBlank(rank4Name) && StringUtils.isBlank(rank5Name)) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE15);
			return;
		}
		
		PlayerAlliance playerAlliance = playerAllianceManager.getOrCreate(player.getId());
		if(playerAlliance.getAllianceId() <= 0) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		
		Alliance alliance = this.getRefreshAlliance(playerAlliance.getAllianceId());
		if(alliance == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE20);
			return;
		}
		
		// 必须军团长权限
		if(player.getId() != alliance.getLeaderId()) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE15);
			return;
		}
		
		//判断头衔不能重复
		List<String> names = new ArrayList<String>();
		if(!StringUtils.isBlank(office1Name)){
			if(names.contains(office1Name)){
				Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE40);
				return;
			}else{
				names.add(office1Name);
			}
		}
		if(!StringUtils.isBlank(office2Name)){
			if(names.contains(office2Name)){
				Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE40);
				return;
			}else{
				names.add(office2Name);
			}
		}
		if(!StringUtils.isBlank(office3Name)){
			if(names.contains(office3Name)){
				Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE40);
				return;
			}else{
				names.add(office3Name);
			}
		}
		if(!StringUtils.isBlank(office4Name)){
			if(names.contains(office4Name)){
				Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE40);
				return;
			}else{
				names.add(office4Name);
			}
		}
		if(!StringUtils.isBlank(rank1Name)){
			if(names.contains(rank1Name)){
				Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE40);
				return;
			}else{
				names.add(rank1Name);
			}
		}
		if(!StringUtils.isBlank(rank2Name)){
			if(names.contains(rank2Name)){
				Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE40);
				return;
			}else{
				names.add(rank2Name);
			}
		}
		if(!StringUtils.isBlank(rank3Name)){
			if(names.contains(rank3Name)){
				Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE40);
				return;
			}else{
				names.add(rank3Name);
			}
		}
		if(!StringUtils.isBlank(rank4Name)){
			if(names.contains(rank4Name)){
				Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE40);
				return;
			}else{
				names.add(rank4Name);
			}
		}
		if(!StringUtils.isBlank(rank5Name)){
			if(names.contains(rank5Name)){
				Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE40);
				return;
			}else{
				names.add(rank5Name);
			}
		}
		
		synchronized (alliance) {
			AllianceTitle allianceTitle = alliance.getAllianceTitle();
			allianceTitle.setOffice1Name(office1Name);
			allianceTitle.setOffice2Name(office2Name);
			allianceTitle.setOffice3Name(office3Name);
			allianceTitle.setOffice4Name(office4Name);
			
			allianceTitle.setRankOneName(rank1Name);
			allianceTitle.setRankTwoName(rank2Name);
			allianceTitle.setRankThreeName(rank3Name);
			allianceTitle.setRankFourName(rank4Name);
			allianceTitle.setRankFiveName(rank5Name);
			InjectorUtil.getInjector().dbCacheService.update(alliance);
		}
		
		ResEditTitleMessage resEditTitleMessage = new ResEditTitleMessage();
		resEditTitleMessage.allianceTitle = AllianceConverter.converterAlianceTitleBean(alliance.getAllianceTitle());
		player.send(resEditTitleMessage);
		//修改跨服玩家信息
		this.changeSimpleRoleInfoOfAllianceTitle(alliance.getOffice1Player());
		this.changeSimpleRoleInfoOfAllianceTitle(alliance.getOffice2Player());
		this.changeSimpleRoleInfoOfAllianceTitle(alliance.getOffice3Player());
		this.changeSimpleRoleInfoOfAllianceTitle(alliance.getOffice4Player());
		Language.SUCCESSTIP.send(player, SuccessTipEnum.E1007_ALLIANCE.CODE15);
	}
	
	/**
	 * 修改玩家跨服头衔基本信息
	 * @param playerId
	 */
	private void changeSimpleRoleInfoOfAllianceTitle(long playerId){
		if(playerId > 0){
			CrossPlayer crossPlayer = crossPlayerManager.getCrossPlayer(playerId);
			if(null != crossPlayer){
				SimpleRoleInfo simpleRoleInfo = crossPlayer.getSimpleRoleInfo();
				String[] allianceTitle = playerAllianceManager.getAllianceTitle(playerId);
				simpleRoleInfo.setAllianceTitle(allianceTitle[0]);
				simpleRoleInfo.setAllianceTitleName(allianceTitle[1]);
				crossPlayerManager.saveSimpleRoleInfo(simpleRoleInfo);
			}
		}
	}
	
	/**
	 * 编辑联盟信息
	 * @param player
	 * @param allianceId
	 * @param allianceName
	 * @param abbr
	 * @param icon
	 * @param announce
	 * @param language
	 */
	public void editAllianceInfo(Player player, long allianceId, String allianceName, String abbr, String icon, String announce, String language) {
		
		PlayerAlliance playerAlliance = playerAllianceManager.getOrCreate(player.getId());
		if(playerAlliance.getAllianceId() <= 0) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		
		Alliance alliance = this.getRefreshAlliance(playerAlliance.getAllianceId());
		if(alliance == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE20);
			return;
		}
		
		String oldAllianceName = alliance.getAllianceName();
		String oldAllianceAbbr = alliance.getAbbr();
		
		// 必须军团长权限
		if(player.getId() != alliance.getLeaderId()) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE15);
			return;
		}
		
		synchronized (alliance) {
			boolean update = false;
			if(!StringUtils.isBlank(allianceName)) {
				if(allianceName.length() < 3 || allianceName.length() > 14) {
					Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE6);
					return;
				}
				
				//判断与现有名字是否相同
				if(allianceName.equals(oldAllianceName)) {
					Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE35);
					return;
				}
				
				if(allianceNameIdMap.keySet().contains(allianceName)) {
					Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE7);
					return;
				}
				
				if(alliance.getRename() > 0) {
					GlobalPir globalPir = GlobalPirFactory.get(GlobalConstant.CHANGE_ALLIANCE_NAME);
					if(!CurrencyUtil.verifyFinal(player, CurrencyEnum.DIAMOND.ordinal(), Long.valueOf(globalPir.getValue()))) {
						Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE15);
						return;
					}
					
					CurrencyUtil.decrementFinal(player, CurrencyEnum.DIAMOND.ordinal(), Long.valueOf(globalPir.getValue()), GameLogSource.ALLIANCE_DONATE);
					CurrencyUtil.send(player);
				}
				
				alliance.setAllianceName(allianceName);
				alliance.setRename(System.currentTimeMillis());
				update = true;
				AllianceNameChangeEventObject allianceNameChangeEventObject = new AllianceNameChangeEventObject(player , EventTypeConst.ALLIANCE_NAME_CHANGE, alliance.getId(),oldAllianceName,alliance.getAllianceName());
				this.gameLog.fireEvent(allianceNameChangeEventObject);
			} else if(!StringUtils.isBlank(abbr)) {
				if(abbr.length() != 3) {
					Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE39);
					return;
				}
				
				if(alliance.getReabbr() + MODIFY_TIME > System.currentTimeMillis()) {
					Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE37);
					return;
				}
				
				//判断与现有简称是否相同
				if(abbr.equals(oldAllianceAbbr)){
					Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE36);
					return;
				}
				
				if(allianceAbbrNameIdMap.keySet().contains(abbr)) {
					Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE8);
					return;
				}
				
				alliance.setAbbr(abbr);
				alliance.setReabbr(System.currentTimeMillis());
				update = true;
				
				AllianceAbbrChangeEventObject allianceAbbrChangeEventObject = new AllianceAbbrChangeEventObject(player , EventTypeConst.ALLIANCE_ABBR_CHANGE, alliance.getId(),oldAllianceAbbr,alliance.getAbbr());
				this.gameLog.fireEvent(allianceAbbrChangeEventObject);
			}
			
			if(!StringUtils.isBlank(icon)) {
				if(icon.length() > 200) {
					Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE15);
					return;
				}
				
				if(alliance.getReicon() + MODIFY_TIME > System.currentTimeMillis()) {
					Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE38);
					return;
				}
				
				alliance.setIcon(icon);
				alliance.setReicon(System.currentTimeMillis());
				update = true;
			}
			
			if(!StringUtils.isBlank(announce)) {
				if(announce.length() > 200) {
					Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE15);
					return;
				}
				
				AllianceAnounce allianceAnounce = new AllianceAnounce();
				allianceAnounce.setContent(announce);
				allianceAnounce.setSignName(alliance.getLeaderName());
				String updateAnnounce = JsonUtil.toJSON(allianceAnounce);
				alliance.setAnnounce(updateAnnounce);
				update = true;
			}
			
			if(!StringUtils.isBlank(language)) {
				if(language.length() > 200) {
					Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE15);
					return;
				}
				
				alliance.setLanguage(language);
				update = true;
			}
			
			// 更新联盟信息
			if(update) {
				//刷新全局联盟信息
				this.allianceIdNameMap.put(alliance.getId(), alliance.getAllianceName());
				this.allianceNameIdMap.remove(oldAllianceName);
				this.allianceNameIdMap.put(alliance.getAllianceName(), alliance.getId());
				this.allianceAbbrNameIdMap.remove(oldAllianceAbbr);
				this.allianceAbbrNameIdMap.put(alliance.getAbbr(), alliance.getId());
				
				InjectorUtil.getInjector().dbCacheService.update(alliance);
				
				AllianceBean allianceBean = AllianceConverter.converterAllianceBean(alliance);
				ResEditAllianceMessage resEditAllianceMessage = new ResEditAllianceMessage();
				resEditAllianceMessage.alliance = allianceBean;
				player.send(resEditAllianceMessage);
				
				ResAllianceChangeMessage resAllianceChangeMessage = new ResAllianceChangeMessage();
				resAllianceChangeMessage.alliance = allianceBean;
				sessionManager.writePlayers(alliance.getAllianceMember(), resAllianceChangeMessage);
				//修改跨服玩家信息
				for(long playerId : alliance.getAllianceMember()){
					CrossPlayer crossPlayer = crossPlayerManager.getCrossPlayer(playerId);
					if(null != crossPlayer){
						SimpleRoleInfo simpleRoleInfo = crossPlayer.getSimpleRoleInfo();
						simpleRoleInfo.setAllianceAbbr(alliance.getAbbr());
						simpleRoleInfo.setAllianceName(alliance.getAllianceName());
						crossPlayerManager.saveSimpleRoleInfo(simpleRoleInfo);
					}
				}
			}
			
			Language.SUCCESSTIP.send(player, SuccessTipEnum.E1007_ALLIANCE.CODE16);
		}
	}
	
	/**
	 * 获取联盟推荐列表
	 * @param player
	 * @return
	 */
	public void getAllianceRecommandList(Player player) {
		List<AlliancePlayerViewBean> list = new ArrayList<>();
		List<Object> rankList = recommendPlayerManager.getRecommandPlayerList();
		if(rankList != null && !rankList.isEmpty()) {
			for(Object obj : rankList) {
				long playerId = (Long)obj;
				if (playerId <= 0 || player.getId() == playerId) {
					continue;
				}
				
				list.add(AllianceConverter.factoryAlliancePlayerViewBean(playerId));
			}
		}
		
		ResRecommendPlayerMessage resRecommendPlayerMessage = new ResRecommendPlayerMessage();
		resRecommendPlayerMessage.playerList = list;
		player.send(resRecommendPlayerMessage);
	}
	
	/**
	 * 发送联盟公告
	 * @param player
	 * @param notice
	 */
	public void sendAllianceNotice(Player player, String notice) {
		
		PlayerAlliance playerAlliance = playerAllianceManager.getOrCreate(player.getId());
		Alliance alliance = this.getRefreshAlliance(playerAlliance.getAllianceId());
		if(alliance == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE20);
			return;
		}
		
		// 招募权限
		if(player.getId() != alliance.getLeaderId() && !playerAlliance.getAlliancePermission().isRecruit()) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE15);
			return;
		}
		
		synchronized (alliance) {
			if(alliance.getNoticeEndTime() > System.currentTimeMillis()) {
				Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE25);
				return;
			}
			
			alliance.setNoticeEndTime(System.currentTimeMillis() + 3600 * 1000);
			alliance.setCount(alliance.getCount() + 1);

			InjectorUtil.getInjector().dbCacheService.update(alliance);
		}
		
		// 发送世界聊天
		noticeManager.sendWorldNotice(NoticeConstant.RECRUIT_ALIANCE_MEMBER, notice);
		
		ResAllianceChangeMessage resAllianceChangeMessage = new ResAllianceChangeMessage();
		resAllianceChangeMessage.alliance = AllianceConverter.converterAllianceBean(alliance);
		EventBus.getSelf().fireEvent(new SendAllianceNoticeEventObject(player));
		sessionManager.writePlayers(alliance.getAllianceMember(), resAllianceChangeMessage);
		
	}
	
	/**
	 * 获取联盟申请列表
	 * @param player
	 * @param allianceId
	 */
	public void getAllianceApplyList(Player player, long allianceId) {
		
		Alliance alliance = this.getRefreshAlliance(allianceId);
		if(alliance == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE20);
			return;
		}
		
		PlayerAlliance playerAlliance = playerAllianceManager.getOrCreate(player.getId());
		if(playerAlliance.getAllianceId() != alliance.getId()) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		
		// 必须军团长权限
		if(player.getId() != alliance.getLeaderId() && !playerAlliance.getAlliancePermission().isDealApply()) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE15);
			return;
		}
		
		// 联盟申请列表
		ResApplyListMessage resApplyListMessage = new ResApplyListMessage();
		Map<Long, AllianceApply> applyList = alliance.getApplyList();
		if(applyList != null) {
			for(AllianceApply allianceApply : applyList.values()) {
				AllianceApplyBean allianceApplyBean = AllianceConverter.converterAllianceApplyBean(allianceApply);
				resApplyListMessage.applyPlayerList.add(allianceApplyBean);
			}
		}
		
		resApplyListMessage.applyTagTime = playerAlliance.getApplyReadTime();
		player.send(resApplyListMessage);
		
		playerAlliance.setApplyReadTime(System.currentTimeMillis());
		InjectorUtil.getInjector().dbCacheService.update(playerAlliance);
	}
	
	/**
	 * 处理申请
	 * @param player
	 * @param allianceId
	 * @param targetPlaeyrId
	 */
	public void dealApply(Player player, long allianceId, long targetPlaeyrId, int result) {
		
		Player targetPlayer = InjectorUtil.getInjector().dbCacheService.get(Player.class, targetPlaeyrId);
		if(targetPlayer == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE4);
			return;
		}
		
		PlayerAlliance playerAlliance = playerAllianceManager.getOrCreate(player.getId());
		if(playerAlliance == null || playerAlliance.getAllianceId() <= 0) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		
		Alliance alliance = this.getRefreshAlliance(playerAlliance.getAllianceId());
		if(alliance == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE20);
			return;
		}
		
		// 已经在联盟
		PlayerAlliance targetPlayerAlliance = playerAllianceManager.getOrCreate(targetPlaeyrId);
		if(targetPlayerAlliance.getAllianceId() > 0) {
			synchronized (alliance) {
				alliance.removeApply(targetPlaeyrId);
				InjectorUtil.getInjector().dbCacheService.update(alliance);
			}
			
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE32);
			return;
		}
		
		// 必须军团长权限
		if(player.getId() != alliance.getLeaderId() && !playerAlliance.getAlliancePermission().isDealApply()) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE15);
			return;
		}
		
		AllianceApply allianceApply = alliance.getPlayerApply(targetPlaeyrId);
		if(allianceApply == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE26);
			return;
		}
		
		synchronized (alliance) {
			
			if(result == 1) {
				if(alliance.getCurMemeber() >= alliance.getMaxMemeber()) {
					Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE12);
					return;
				}
				
				alliance.removeApply(targetPlaeyrId);
				doJoinAlliance(targetPlayer, targetPlayerAlliance, alliance);
				
				// 推送加入军团
				ResAllianceDataMessage resAllianceDataMessage = new ResAllianceDataMessage();
				resAllianceDataMessage.alliance = AllianceConverter.converterAllianceBean(alliance);
				resAllianceDataMessage.playerAlliance = AllianceConverter.converterAllianceMember(targetPlayer, targetPlayerAlliance);
				targetPlayer.send(resAllianceDataMessage);
				
				ResDealApplyMessage resDealApplyMessage = new ResDealApplyMessage();
				resDealApplyMessage.allianceMemberBean = AllianceConverter.converterAllianceMember(targetPlayer, targetPlayerAlliance);
				resDealApplyMessage.result = result;
				player.send(resDealApplyMessage);
				
				// 联盟信息变更
				ResAllianceChangeMessage resAllianceChangeMessage = new ResAllianceChangeMessage();
				resAllianceChangeMessage.alliance = AllianceConverter.converterAllianceBean(alliance);
				sessionManager.writePlayers(alliance.getAllianceMember(), resAllianceChangeMessage);
				
				MailKit.sendSystemEmail(targetPlaeyrId, EmailTemplet.批准入团申请_MAIL_ID,ParamFormatUtils.formatParma(EmailConverter.emailOfPlayerName(player.getId(), player.getName()),alliance.getAllianceName()));
				//修改跨服玩家信息
				CrossPlayer crossPlayer = crossPlayerManager.getCrossPlayer(targetPlaeyrId);
				if(null != crossPlayer){
					SimpleRoleInfo simpleRoleInfo = crossPlayer.getSimpleRoleInfo();
					simpleRoleInfo.setAllianceId(alliance.getId());
					simpleRoleInfo.setAllianceAbbr(alliance.getAbbr());
					simpleRoleInfo.setAllianceName(alliance.getAllianceName());
					crossPlayerManager.saveSimpleRoleInfo(simpleRoleInfo);
				}
				Language.SUCCESSTIP.send(player, SuccessTipEnum.E1007_ALLIANCE.CODE9);
			} else if(result == 2) {

				alliance.removeApply(targetPlaeyrId);
				InjectorUtil.getInjector().dbCacheService.update(alliance);
				
				targetPlayerAlliance.getApplyList().remove(alliance.getAllianceId());
				InjectorUtil.getInjector().dbCacheService.update(targetPlayerAlliance);
				
				// 推送加入军团
				ResAllianceDataMessage resAllianceDataMessage = new ResAllianceDataMessage();
				resAllianceDataMessage.playerAlliance = AllianceConverter.converterAllianceMember(targetPlayer, targetPlayerAlliance);
				targetPlayer.send(resAllianceDataMessage);
				
				ResDealApplyMessage resDealApplyMessage = new ResDealApplyMessage();
				resDealApplyMessage.result = result;
				player.send(resDealApplyMessage);
				MailKit.sendSystemEmail(targetPlaeyrId, EmailTemplet.拒绝入团申请_MAIL_ID,ParamFormatUtils.formatParma(EmailConverter.emailOfPlayerName(player.getId(), player.getName()),alliance.getAllianceName()));
				Language.SUCCESSTIP.send(player, SuccessTipEnum.E1007_ALLIANCE.CODE24);
			} else {
				Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE15);
				return;
			}
		}
	}
	
	/**
	 * 获取联盟详情
	 * @param player
	 * @param allianceId
	 */
	public void getAllianceBattleInfoList(Player player) {
		
		ResAllianceFightListMessage resAllianceFightListMessage = new ResAllianceFightListMessage();
		PlayerAlliance playerAlliance = playerAllianceManager.getOrCreate(player.getId());
		if(playerAlliance.getAllianceId() <= 0) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		
		Alliance alliance = this.getRefreshAlliance(playerAlliance.getAllianceId());
		if(alliance == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE20);
			return;
		}
		
		AllianceReport allianceReport = allianceBattleInfoManager.getOrCreate(alliance.getId());
		List<AllianceBattleReport> list = allianceReport.getBattleReportList();
		if(list != null && !list.isEmpty()) {
			for(AllianceBattleReport battleReport : list) {
				AllianceFightInfoBean allianceFightInfoBean = AllianceConverter.converterAllianceFightInfoBean(battleReport);
				resAllianceFightListMessage.allianceFightInfo.add(allianceFightInfoBean);
			}
		}
		
		player.send(resAllianceFightListMessage);
	}
	
	/**
	 * 发送联盟帮助
	 * @param player
	 * @param taskId
	 */
	public void sendHelp(Player player, long taskId) {
		
		PlayerAlliance playerAlliance = playerAllianceManager.getOrCreate(player.getId());
		if(playerAlliance.getAllianceId() <= 0) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		
		TimerTaskData timerTaskData = InjectorUtil.getInjector().dbCacheService.get(TimerTaskData.class, taskId);
		if(timerTaskData.getRoleId() != player.getId()) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE27);
			return;
		}
		
		// 当前队列已经援建过，无法再援建
		if(!StringUtils.isEmpty(timerTaskData.getHelpId())) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE34);
			return;
		}
		
		Alliance alliance = this.getRefreshAlliance(playerAlliance.getAllianceId());
		if(alliance == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE27);
			return;
		}
		
		//TODO 联盟援建减少时间百分比 以任务原时间为基数 存在加速后再加入军团发送援建的情况 
		//创建援助信息
		HelpInfo helpInfo = helpInfoManager.factoryAllianceHelp(player, alliance, timerTaskData);
		InjectorUtil.getInjector().dbCacheService.create(helpInfo);
		synchronized(alliance) {
			//加入联盟援助
			alliance.getHelpList().add(helpInfo.getHelpId());
			InjectorUtil.getInjector().dbCacheService.update(alliance);
		}
		//保存援助信息到队列
		timerTaskData.setHelpId(helpInfo.getHelpId().toString());
		InjectorUtil.getInjector().dbCacheService.update(timerTaskData);
		
		// 推送联盟玩家
		ResSendHelperMessage resSendHelperMessage = new ResSendHelperMessage();
		resSendHelperMessage.allianceHelpe = AllianceConverter.converterAllianceHelp(player, helpInfo, "",0);
		sessionManager.writePlayers(alliance.getAllianceMember(), resSendHelperMessage);
		EventBus.getSelf().fireEvent(new AllianceAidEventObject(player));
		Language.SUCCESSTIP.send(player, SuccessTipEnum.E1007_ALLIANCE.CODE17);
	}
	
	/**
	 * 获取联盟帮助列表
	 * @param player
	 */
	public void getAllianceHelpList(Player player) {
		ResGetHelpeListMessage resGetHelpeListMessage = new ResGetHelpeListMessage();
		PlayerAlliance playerAlliance = playerAllianceManager.getOrCreate(player.getId());
		if(playerAlliance.getAllianceId() <= 0) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		
		Alliance alliance = this.getRefreshAlliance(playerAlliance.getAllianceId());
		if(alliance == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE20);
			return;
		}
		
		for(Long helpId : alliance.getHelpList()) {
			HelpInfo helpInfo = helpInfoManager.get(helpId);
			if(helpInfo != null && (helpInfo.canHelp(player.getId()) || helpInfo.getSenderd() == player.getId())) {
				TimerTaskData timerTaskData = InjectorUtil.getInjector().dbCacheService.get(TimerTaskData.class, helpInfo.getTaskId());
				if(timerTaskData != null && (helpInfo.getNowcount() < helpInfo.getMaxcount()|| helpInfo.getSenderd() == player.getId())) {
					Player sendPlayer = InjectorUtil.getInjector().dbCacheService.get(Player.class, helpInfo.getSenderd());
					resGetHelpeListMessage.allianceHelpe.add(AllianceConverter.converterAllianceHelp(sendPlayer, helpInfo, "",0));
				}
			}
		}
		player.send(resGetHelpeListMessage);
	}
	
	/**
	 * 联盟帮助
	 * @param player
	 * @param helpId
	 */
	public void help(Player player, String helpId) {
		
		PlayerAlliance playerAlliance = playerAllianceManager.getOrCreate(player.getId());
		if(playerAlliance.getAllianceId() <= 0) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		
		Alliance alliance = this.getRefreshAlliance(playerAlliance.getAllianceId());
		if(alliance == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE27);
			return;
		}
		
		// 联盟帮助
		HelpInfo helpInfo = helpInfoManager.get(Long.valueOf(helpId));
		if(helpInfo != null && helpInfo.getSenderId() != player.getId() && helpInfo.canHelp(player.getId())) {
			synchronized (helpInfo) {
				if(helpInfo.getNowcount() < helpInfo.getMaxcount()) {
					TimerTaskData timerTaskData = InjectorUtil.getInjector().dbCacheService.get(TimerTaskData.class, helpInfo.getTaskId());
					if(timerTaskData != null) {
						ITimerTask<?> task = TimerTaskHolder.getTimerTask(timerTaskData.getQueueId());
						if(task != null) {
							Player taskPlayer = InjectorUtil.getInjector().dbCacheService.get(Player.class, timerTaskData.getRoleId());
							if(taskPlayer != null) {
								int[] help_donate = GlobalPirFactory.getInstance().help_donate;
								
								// 加速
								task.speedUp(taskPlayer, timerTaskData, helpInfo.getReduceSec());
								
								helpInfo.setNowcount(helpInfo.getNowcount() + 1);
								helpInfo.setTaskId(timerTaskData.getTaskId());
								helpInfo.help(player.getId());
								helpInfoManager.update(helpInfo);
								
								// 增加军团贡献
								if(playerAlliance.getHelpDonate() < help_donate[1]) {
									playerAlliance.addHelpDonate(help_donate[0]);
									InjectorUtil.getInjector().dbCacheService.update(playerAlliance);
									CurrencyUtil.send(player);
									// 通知成员变更
									ResNotifyPermissionChangeMessage resNotifyPermissionChangeMessage = new ResNotifyPermissionChangeMessage();
									resNotifyPermissionChangeMessage.allianceMemberBean = AllianceConverter.converterAllianceMember(player, playerAlliance);
									player.send(resNotifyPermissionChangeMessage);
								}
								
								// 推送联盟帮助数据变更
								ResDoneHelpMessage resSendHelperMessage = new ResDoneHelpMessage();
								resSendHelperMessage.allianceHelpe = AllianceConverter.converterAllianceHelp(taskPlayer, helpInfo, player.getName(),player.getId());
								sessionManager.writePlayers(alliance.getAllianceMember(), resSendHelperMessage);
							}
						}
					}
				}
			}
		}
		
		Language.SUCCESSTIP.send(player, SuccessTipEnum.E1007_ALLIANCE.CODE18);
	}
	
	/**
	 * 处理一键
	 * @param player
	 * @param helpIds
	 */
	public void helpAll(Player player, List<String> helpIds) {
		for(String helpId : helpIds) {
			this.help(player, helpId);
		}
	}
	
	/**
	 * 退出出联盟
	 * @param player
	 */
	public void dismissAlliance(Player player) {
		
		PlayerAlliance playerAlliance = playerAllianceManager.getOrCreate(player.getId());
		if(playerAlliance.getAllianceId() <= 0) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		
		Alliance alliance = this.getRefreshAlliance(playerAlliance.getAllianceId());
		if(alliance == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE20);
			return;
		}
		
		if(alliance.getCurMemeber() > 1) {
			if(alliance.getLeaderId() == player.getId()) {
				Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE28);
				return;
			}
		}
		
		synchronized (alliance) {
			doExitAlliance(player, playerAlliance, alliance);
		}
		
		// 推出联盟
		ResDismissAllianceMessage resDismissAllianceMessage = new ResDismissAllianceMessage();
		resDismissAllianceMessage.playerId = player.getId();
		player.send(resDismissAllianceMessage);
		
		
	}
	
	/**
	 * 剔除玩家
	 * @param player
	 * @param targetPlayerId
	 */
	public void kickPlayerMember(Player player, long targetPlayerId) {
		
		if(player.getId() == targetPlayerId) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE13);
			return;
		}
		
		PlayerAlliance playerAlliance = playerAllianceManager.getOrCreate(player.getId());
		if(playerAlliance.getAllianceId() <= 0) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		
		Alliance alliance = this.getRefreshAlliance(playerAlliance.getAllianceId());
		if(alliance == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE27);
			return;
		}
		
		PlayerAlliance targetPlayerAlliance = playerAllianceManager.getOrCreate(targetPlayerId);
		if(targetPlayerAlliance.getAllianceId() <= 0) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		
		if(!alliance.getAllianceMember().contains(targetPlayerId)) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		
		Player targetPlayer = InjectorUtil.getInjector().dbCacheService.get(Player.class, targetPlayerId);
		if(targetPlayer == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		
		if(alliance.getLeaderId() != playerAlliance.getId() && playerAlliance.getAlliancePermission().isKickmember()) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE15);
			return;
		}
		
		if(alliance.getLeaderId() != player.getId()) {
			if(playerAlliance.getAllianceRank() <= targetPlayerAlliance.getAllianceRank()) {
				Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE15);
				return;
			}
		}
		
		synchronized (alliance) {
			// 处理推出联盟
			doExitAlliance(targetPlayer, targetPlayerAlliance, alliance);
		}
		
		ResKickMemberMessage resKickMemberMessage = new ResKickMemberMessage();
		resKickMemberMessage.taregetPlayerId = targetPlayerId;
		player.send(resKickMemberMessage);
		MailKit.sendSystemEmail(targetPlayerId, EmailTemplet.移出军团_MAIL_ID,ParamFormatUtils.formatParma(EmailConverter.emailOfPlayerName(player.getId(), player.getName()), alliance.getAllianceName()));
	}
	
	/**
	 * 设置联盟阶级
	 * @param player
	 * @param alliance
	 * @param rank
	 */
	public void setAllianceRank(Player player, long targetPlayerId, int rank) {
		
		PlayerAlliance playerAlliance = playerAllianceManager.getOrCreate(player.getId());
		if(playerAlliance.getAllianceId() <= 0) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		
		Alliance alliance = this.getRefreshAlliance(playerAlliance.getAllianceId());
		if(alliance == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE20);
			return;
		}
		
		if(alliance.getLeaderId() != playerAlliance.getId() && !playerAlliance.getAlliancePermission().isManagerMemeberLevel()) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE15);
			return;
		}
		
		Player targetPlayer = InjectorUtil.getInjector().dbCacheService.get(Player.class, targetPlayerId);
		if(targetPlayer == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}

		PlayerAlliance targetPlayerAlliance = playerAllianceManager.getOrCreate(targetPlayerId);
		if(playerAlliance.getAllianceId() <= 0) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		
		if(targetPlayerAlliance.getAllianceId() != alliance.getId()) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		
		if(alliance.getLeaderId() != player.getId()) {
			if(playerAlliance.getAllianceRank() <= rank) {
				Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE15);
				return;
			}
		}
		
		int originRank = targetPlayerAlliance.getAllianceRank();
		boolean flag = false;
		//降到R5以下 撸掉官职
		if(originRank == AllianceConstant.RANK_FIVE && rank != AllianceConstant.RANK_FIVE) {
			alliance.removeOffice(targetPlayerAlliance.getId());
			flag = true;
		}
		//降到R4以下 撸掉队长
		if(rank < AllianceConstant.RANK_FOUR){
			Collection<AllianceTeam> collection = alliance.getTeamMap().values();
			if(collection != null) {
				for(AllianceTeam allianceTeam : collection) {
					if(allianceTeam.getTeamLeaderId() == targetPlayerAlliance.getId()) {
						allianceTeam.setTeamLeaderId(0);
						targetPlayerAlliance.setTeamId("");
					}
				}
			}
			flag = true;
		}
		alliance.removeRankMemeber(targetPlayerId);
		alliance.addAllianceMemeber(targetPlayerId, rank);
		InjectorUtil.getInjector().dbCacheService.update(alliance);
		
		targetPlayerAlliance.setAllianceRank(rank);
		playerAllianceManager.update(targetPlayerAlliance);
		if(flag){
			//修改跨服玩家信息
			this.changeSimpleRoleInfoOfAllianceTitle(targetPlayerAlliance.getId());
		}
		MailKit.sendSystemEmail(targetPlayerId, EmailTemplet.军团成员调级_MAIL_ID,ParamFormatUtils.formatParma(EmailConverter.emailOfPlayerName(targetPlayer.getId(), targetPlayer.getName()), rank+""));
		AllianceBean allianceBean = AllianceConverter.converterAllianceBean(alliance);
		ResChangeRankMessage resSetOfficeMessage = new ResChangeRankMessage();
		resSetOfficeMessage.alliance = allianceBean;
		player.send(resSetOfficeMessage);
	}
	
	/**
	 * 设置官员
	 * @param player
	 */
	public void setOfficeRank(Player player, String office, int officePos, int teamPos, long targetPlayerId) {
		//判断操作玩家军团信息
		PlayerAlliance playerAlliance = playerAllianceManager.getOrCreate(player.getId());
		if(playerAlliance.getAllianceId() <= 0) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		
		Alliance alliance = this.getRefreshAlliance(playerAlliance.getAllianceId());
		if(alliance == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE20);
			return;
		}
		//判断目标玩家军团信息
		Player targePlayer = InjectorUtil.getInjector().dbCacheService.get(Player.class, targetPlayerId);
		if(targePlayer == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE4);
			return;
		}
		
		PlayerAlliance targetPlayerAlliance = playerAllianceManager.getOrCreate(targetPlayerId);
		if(targetPlayerAlliance.getAllianceId() <= 0) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		
		if(targetPlayerAlliance.getAllianceId() != alliance.getAllianceId()){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		
		synchronized (alliance) {
			if(officePos > 0) {
				if(alliance.getLeaderId() != playerAlliance.getId()) {
					Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE15);
					return;
				}
				//阶级5的玩家才能分配官职
				if(targetPlayerAlliance.getAllianceRank() != AllianceConstant.RANK_FIVE){
					Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE41);
					return;
				}
				//移除现有官职
				alliance.removeOffice(targePlayer.getRoleId());
				//设置新的官职
				alliance.setOffice(targePlayer, "office"+officePos);
				//修改跨服玩家信息
				this.changeSimpleRoleInfoOfAllianceTitle(targetPlayerId);
			}
			
			if(teamPos > 0) {
				if(alliance.getLeaderId() != playerAlliance.getId() && !playerAlliance.getAlliancePermission().isAssignTeamLeader()) {
					Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE15);
					return;
				}
				
				//阶级4以上的玩家或者军团长才能分配队长
				if(targetPlayerAlliance.getAllianceRank() < AllianceConstant.RANK_FOUR && targetPlayerId != alliance.getLeaderId()){
					Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE42);
					return;
				}
				//移除以前队长的小队信息
				String teamId = AllianceTeam.factoryTeamId(alliance.getId(), teamPos+"");
				AllianceTeam allianceTeam = alliance.getAllianceTeam(teamId);
				if(allianceTeam.getTeamLeaderId() > 0){
					PlayerAlliance oldPlayerAlliance = playerAllianceManager.getOrCreate(allianceTeam.getTeamLeaderId());
					if(oldPlayerAlliance != null) {
						oldPlayerAlliance.setTeamId("");
						playerAllianceManager.update(oldPlayerAlliance);
					}
				}
				//处理目标玩家退出战队
				alliance.quitTeam(targePlayer.getRoleId());
				//设置战队队长
				boolean success = alliance.setTeamLeader(targePlayer, teamId);
				if(success) {
					targetPlayerAlliance.setTeamId(teamId);
					playerAllianceManager.update(targetPlayerAlliance);
					
					//修改跨服玩家信息
					this.changeSimpleRoleInfoOfAllianceTitle(targetPlayerId);
				}
			}
			InjectorUtil.getInjector().dbCacheService.update(alliance);
		}
		
		AllianceBean allianceBean= AllianceConverter.converterAllianceBean(alliance);

		ResSetOfficeMessage resSetOfficeMessage = new ResSetOfficeMessage();
		resSetOfficeMessage.alliance = allianceBean;
		player.send(resSetOfficeMessage);

		// 军团公告
		noticeManager.sendAllianceNotice(targePlayer, alliance, NoticeConstant.SET_ALLIANCE_RANK, player.getName(), targePlayer.getName(), officePos, teamPos);
		MailKit.sendSystemEmail(targetPlayerId, EmailTemplet.军团任命_MAIL_ID,ParamFormatUtils.formatParma(EmailConverter.emailOfPlayerName(targePlayer.getId(), targePlayer.getName()), officePos+";"+teamPos));
		// 推送联盟信息
		ResAllianceChangeMessage resAllianceChangeMessage = new ResAllianceChangeMessage();
		resAllianceChangeMessage.alliance = AllianceConverter.converterAllianceBean(alliance);
		sessionManager.writePlayers(alliance.getAllianceMember(), resAllianceChangeMessage);
	}
	
	/**
	 * 退出联盟
	 * @param player
	 * @param playerAlliance
	 * @param alliance
	 */
	private void doExitAlliance(Player player, PlayerAlliance playerAlliance, Alliance alliance) {
		AllianceExt allianceExt = allianceExtManager.getRefreshAllianceExt(alliance.getAllianceId());
		if(alliance.getCurMemeber() != 1) {
			alliance.exitAlliance(player.getId());
			alliance.setCurMemeber(alliance.getCurMemeber() - 1);
			long fightPower = 0;
			for(Long id : alliance.getAllianceMember()) {
				fightPower += FightPowerKit.FINAL_POWER.getValue(id).longValue();
			}
			alliance.setFightPower(fightPower) ;
			
			// 队伍
			if(!StringUtils.isEmpty(playerAlliance.getTeamId())) {
				AllianceTeam allianceTeam = alliance.getAllianceTeam(playerAlliance.getTeamId());
				if(allianceTeam != null) {
					allianceTeam.dismissAlliance(player.getId());
				}
			}
			
			//退出联盟 联盟援建信息处理
			Map<Long,Long> map = player.roleInfo().getTimerTaskMap();
			List<String> helpIds = new ArrayList<String>();
			for(Long taskId : map.keySet()){
				TimerTaskData timerTaskData = timerTaskManager.getTimerTaskData(taskId);
				if(null != timerTaskData && !StringUtils.isBlank(timerTaskData.getHelpId())){
					//获取援建信息
					HelpInfo helpInfo = helpInfoManager.get(Long.valueOf(timerTaskData.getHelpId()));
					if(null != helpInfo){
						helpInfo.setAllianceId(0);
						helpInfoManager.update(helpInfo);
						
						alliance.getHelpList().remove(helpInfo.getHelpId());
						
						helpIds.add(timerTaskData.getHelpId());
					}
				}
			}
			
			if (null != helpIds && helpIds.size() > 0) {
				// 推送联盟玩家援助变更
				ResAllianceHelpDeleteMessage resAllianceHelpDeleteMessage = new ResAllianceHelpDeleteMessage();
				resAllianceHelpDeleteMessage.helpIds = helpIds;
				InjectorUtil.getInjector().sessionManager.writePlayers(alliance.getAllianceMember(), resAllianceHelpDeleteMessage);
			}
			
			InjectorUtil.getInjector().dbCacheService.update(alliance);
			smartRank.compareAndRefresh(AllianceRankable.valueOf(alliance.getId(), alliance.getLevel(), alliance.getFightPower()));
			if(null != allianceExt){
				allianceExt.getSuperMine().getMarchs().remove(player.getId());
				InjectorUtil.getInjector().dbCacheService.update(allianceExt);
			}
		// 解散联盟
		} else {
			
			allianceNameIdMap.remove(alliance.getAllianceName());
			allianceAbbrNameIdMap.remove(alliance.getAbbr());
			allianceIdNameMap.remove(alliance.getId());
			
			// 解散联盟处理(先处理联盟领地才能推出解散军团)
			worldLogicManager.dismissAlliance(player, alliance);
			
			//退出联盟 联盟援建信息处理
			Map<Long,Long> map = player.roleInfo().getTimerTaskMap();
			for(Long taskId : map.keySet()){
				TimerTaskData timerTaskData = timerTaskManager.getTimerTaskData(taskId);
				// 处理军团帮助
				if(null != timerTaskData && !StringUtils.isBlank(timerTaskData.getHelpId())){
					//获取援建信息
					HelpInfo helpInfo = helpInfoManager.get(Long.valueOf(timerTaskData.getHelpId()));
					if(null != helpInfo){
						helpInfo.setAllianceId(0);
						helpInfoManager.update(helpInfo);
					}
				}
			}
			
			// 删除联盟
			smartRank.removeAndRefresh(alliance.getId());
			InjectorUtil.getInjector().dbCacheService.delete(alliance);
			if(null != allianceExt){
				InjectorUtil.getInjector().dbCacheService.delete(allianceExt);
			}
		}
		
		// 清空玩家联盟数据
		playerAlliance.clean();
		InjectorUtil.getInjector().dbCacheService.update(playerAlliance);
		
		//设置玩家军团扩展信息
		PlayerAllianceExt playerAllianceExt = playerAllianceExtManager.getOrCreate(player.getId());
		playerAllianceExt.clean();
		InjectorUtil.getInjector().dbCacheService.update(playerAllianceExt);
		
		// 退出加入推荐排行
		recommendPlayerManager.refreshRank(player);
		
		this.gameLog.fireEvent(new AllianceLeftEventObject(player, EventTypeConst.EVENT_ALLIANCE_LEFT, alliance.getId(), alliance.getLeaderId(),alliance.getAbbr(),alliance.getAllianceName()));
		
		ResDismissAllianceMessage resDismissAllianceMessage = new ResDismissAllianceMessage();
		resDismissAllianceMessage.playerId = player.getRoleId();
		player.send(resDismissAllianceMessage);
		
		// 联盟信息变更
		ResAllianceChangeMessage resAllianceChangeMessage = new ResAllianceChangeMessage();
		resAllianceChangeMessage.alliance = AllianceConverter.converterAllianceBean(alliance);
		sessionManager.writePlayers(alliance.getAllianceMember(), resAllianceChangeMessage);
		
		//修改跨服玩家信息
		CrossPlayer crossPlayer = crossPlayerManager.getCrossPlayer(player.getId());
		if(null != crossPlayer){
			SimpleRoleInfo simpleRoleInfo = crossPlayer.getSimpleRoleInfo();
			simpleRoleInfo.setAllianceId(0);
			simpleRoleInfo.setAllianceTitle("");
			simpleRoleInfo.setAllianceTitleName("");
			simpleRoleInfo.setAllianceAbbr("");
			simpleRoleInfo.setAllianceName("");
			crossPlayerManager.saveSimpleRoleInfo(simpleRoleInfo);
		}
	}
	
	/**
	 * 邀请玩家
	 * @param player
	 * @param targetPlayerId
	 */
	public void invite(Player player , long targetPlayerId) {
		
		PlayerAlliance playerAlliance = playerAllianceManager.getOrCreate(player.getId());
		if(playerAlliance.getAllianceId() <= 0) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		
		Alliance alliance = this.getRefreshAlliance(playerAlliance.getAllianceId());
		if(alliance == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE20);
			return;
		}
		
		Player targetPlayer = InjectorUtil.getInjector().dbCacheService.get(Player.class, targetPlayerId);
		if(targetPlayer == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE4);
			return;
		}
		PlayerAlliance tarPlayerAlliance = playerAllianceManager.getOrCreate(targetPlayerId);
		if(tarPlayerAlliance != null && tarPlayerAlliance.getAllianceId() > 0) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE32);
			return;
		}
		
		synchronized (alliance) {
			if(alliance.getCurMemeber() >= alliance.getMaxMemeber()) {
				Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE12);
				return;
			}
		
			if(tarPlayerAlliance.getInviteList().contains(alliance.getAllianceId())) {
				Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE4);
				return;
			}
			
			tarPlayerAlliance.getInviteList().add(alliance.getAllianceId());
			InjectorUtil.getInjector().dbCacheService.update(tarPlayerAlliance);
			
			ResInviteMessage resApplyAllianceMessage = new ResInviteMessage();
			resApplyAllianceMessage.allianceId = alliance.getId();
			player.send(resApplyAllianceMessage);
			
			ResNotifyInviteMessage resNotifyInviteMessage = new ResNotifyInviteMessage();
			resNotifyInviteMessage.alliance = AllianceConverter.converterSimpleAllianceBean(alliance);
			targetPlayer.send(resNotifyInviteMessage);
		}
		MailKit.sendSystemEmail(targetPlayerId, EmailTemplet.军团邀请_MAIL_ID,ParamFormatUtils.formatParma(EmailConverter.emailOfPlayerName(player.getId(), player.getName()), alliance.getAllianceName()));
	}
	
	/**
	 * 编辑权限
	 * @param player
	 * @param targetPlayerId
	 * @param alliancePermissionBean
	 */
	public void editPermission(Player player , long targetPlayerId, AlliancePermissionBean alliancePermissionBean) {
		
		PlayerAlliance playerAlliance = playerAllianceManager.getOrCreate(player.getId());
		if(playerAlliance.getAllianceId() <= 0) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE13);
			return;
		}
		
		Alliance alliance = this.getRefreshAlliance(playerAlliance.getAllianceId());
		if(alliance == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE20);
			return;
		}
		
		PlayerAlliance tarPlayerAlliance = InjectorUtil.getInjector().dbCacheService.get(PlayerAlliance.class, targetPlayerId);
		if(tarPlayerAlliance == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE4);
			return;
		}
		
		Player targetPlayer = InjectorUtil.getInjector().dbCacheService.get(Player.class, targetPlayerId);
		
		if(alliance.getLeaderId() != playerAlliance.getId()) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE15);
			return;
		}
		
		// 权限变更
		targetPlayer.async(new Runnable() {
			@Override
			public void run() {
				AlliancePermission alliancepermission = tarPlayerAlliance.getAlliancePermission();
				alliancepermission.setAssignAllianceReward(alliancePermissionBean.assignAllianceReward > 0 ? true : false);
				alliancepermission.setAssignTeamLeader(alliancePermissionBean.assignTeamLeader > 0 ? true : false);
				alliancepermission.setCreateAllianceBuild(alliancePermissionBean.createAllianceBuild > 0 ? true : false);
				alliancepermission.setDealApply(alliancePermissionBean.dealApply > 0 ? true : false);
				alliancepermission.setInvite(alliancePermissionBean.invite > 0 ? true : false);
				alliancepermission.setKickmember(alliancePermissionBean.kickmember > 0 ? true : false);
				alliancepermission.setManagerAllianceBuild(alliancePermissionBean.managerAllianceBuild > 0 ? true : false);
				alliancepermission.setManagerMemeberLevel(alliancePermissionBean.managerMemeberLevel > 0 ? true : false);
				alliancepermission.setRecruit(alliancePermissionBean.recruit > 0 ? true : false);
				alliancepermission.setSendMail(alliancePermissionBean.sendMail > 0 ? true : false);
				InjectorUtil.getInjector().dbCacheService.update(tarPlayerAlliance);
				
//				MailKit.sendSystemEmailWithContent(targetPlayerId, EmailTemplet.MAIL_SET_PERMISSION_ON, JsonUtil.toJSON(alliancePermissionBean));
				
				ResEditPermissionMessage resEditPermissionMessage = new ResEditPermissionMessage();
				player.send(resEditPermissionMessage);
				
				ResNotifyPermissionChangeMessage resNotifyPermissionChangeMessage = new ResNotifyPermissionChangeMessage();
				resNotifyPermissionChangeMessage.allianceMemberBean = AllianceConverter.converterAllianceMember(targetPlayer, tarPlayerAlliance);
				targetPlayer.send(resNotifyPermissionChangeMessage);
				player.send(resNotifyPermissionChangeMessage);
			}
		});
		
		Language.SUCCESSTIP.send(player, SuccessTipEnum.E1007_ALLIANCE.CODE20);
	}
	
	/**
	 * 查询联盟排行玩家
	 * @param player
	 * @param allianceId
	 */
	public void queryAlliancePlayerRank(Player player, long allianceId) {
		
		PlayerAlliance playerAlliance = playerAllianceManager.getOrCreate(player.getId());
		if(playerAlliance.getAllianceId() <= 0) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE13);
			return;
		}
		
		Alliance alliance = this.getRefreshAlliance(playerAlliance.getAllianceId());
		if(alliance == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE20);
			return;
		}
		
		// 查询排行玩家
		ResRankPlayerMessage resRankPlayerMessage = new ResRankPlayerMessage();
		for(Long id : alliance.getAllianceMember()) {
			Player rankPlayer = InjectorUtil.getInjector().dbCacheService.get(Player.class, id);
			PlayerAlliance rankPlayerAlliance = playerAllianceManager.getOrCreate(id);
			resRankPlayerMessage.alliance.add(AllianceConverter.converterAllianceRankPlayerBean(rankPlayer, rankPlayerAlliance));
		}
		
		player.send(resRankPlayerMessage);
	}
	
	/**
	 * 设置自动
	 * @param player
	 * @param allianceId
	 */
	public void setAuto(Player player, long allianceId) {
		PlayerAlliance playerAlliance = playerAllianceManager.getOrCreate(player.getId());
		if(playerAlliance.getAllianceId() <= 0) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		
		
		Alliance alliance = this.getRefreshAlliance(playerAlliance.getAllianceId());
		if(alliance == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE20);
			return;
		}
		
		if(alliance.getLeaderId() != playerAlliance.getId()) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE15);
			return;
		}
		
		// 设置联盟是否自动
		if(alliance.isAuto()) {
			alliance.setAuto(false);
		} else {
			alliance.setAuto(true);
		}
		
		InjectorUtil.getInjector().dbCacheService.update(alliance);
		
		ResSetAutoJoinMessage resSetAutoJoinMessage = new ResSetAutoJoinMessage();
		resSetAutoJoinMessage.auto = alliance.isAuto() ?  1 : 2;
		player.send(resSetAutoJoinMessage);
		if(alliance.isAuto()){
			Language.SUCCESSTIP.send(player, SuccessTipEnum.E1007_ALLIANCE.CODE21);
		}else{
			Language.SUCCESSTIP.send(player, SuccessTipEnum.E1007_ALLIANCE.CODE25);
		}
		
	}
	
	/**
	 * 撤职
	 * @param player
	 * @param officePos
	 * @param teamPos
	 */
	public void dismissOffice(Player player, int officePos, int teamPos) {
		
		PlayerAlliance playerAlliance = playerAllianceManager.getOrCreate(player.getId());
		if(playerAlliance.getAllianceId() <= 0) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE13);
			return;
		}
		
		Alliance alliance = this.getRefreshAlliance(playerAlliance.getAllianceId());
		if(alliance == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE20);
			return;
		}
		
		if(alliance.getLeaderId() != playerAlliance.getId()) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE15);
			return;
		}
		
		if (officePos <= 0 && teamPos <= 0) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE1);
			return;
		}
		
		long targetPlayerId = 0;
		if(officePos == 1) {
			targetPlayerId = alliance.getOffice1Player();
			alliance.setOffice1Player(0);
		} else if(officePos == 2) {
			targetPlayerId = alliance.getOffice2Player();
			alliance.setOffice2Player(0);
		} else if(officePos == 3) {
			targetPlayerId = alliance.getOffice3Player();
			alliance.setOffice3Player(0);
		} else if(officePos == 4) {
			targetPlayerId = alliance.getOffice4Player();
			alliance.setOffice4Player(0);
		}
		
		if(teamPos > 0) {
			AllianceTeam allianceTeam = alliance.getTeamMap().get(AllianceTeam.factoryTeamId(alliance.getId(), String.valueOf(teamPos)));
			if(allianceTeam != null) {
				targetPlayerId = allianceTeam.getTeamLeaderId();
				//重置原队长的teamId
				if(targetPlayerId > 0){
					PlayerAlliance teamLeaderPlayerAlliance = playerAllianceManager.getOrCreate(targetPlayerId);
					if(teamLeaderPlayerAlliance != null) {
						teamLeaderPlayerAlliance.setTeamId("");
						playerAllianceManager.update(teamLeaderPlayerAlliance);
					}
				}
				allianceTeam.setTeamLeaderId(0);
			}
		}
		
		InjectorUtil.getInjector().dbCacheService.update(alliance);
		MailKit.sendSystemEmail(targetPlayerId, EmailTemplet.军团撤销任命_MAIL_ID,ParamFormatUtils.formatParma(EmailConverter.emailOfPlayerName(player.getId(),player.getName()), officePos +";"+ teamPos));
		ResDimissOfficeMessage resDimissOfficeMessage = new ResDimissOfficeMessage();
		resDimissOfficeMessage.alliance = AllianceConverter.converterAllianceBean(alliance);
		player.send(resDimissOfficeMessage);
		
		ResAllianceChangeMessage resAllianceChangeMessage = new ResAllianceChangeMessage();
		resAllianceChangeMessage.alliance = AllianceConverter.converterAllianceBean(alliance);
		sessionManager.writePlayers(alliance.getAllianceMember(), resAllianceChangeMessage);
		//修改跨服玩家信息
		this.changeSimpleRoleInfoOfAllianceTitle(targetPlayerId);
		
		Language.SUCCESSTIP.send(player, SuccessTipEnum.E1007_ALLIANCE.CODE19);
	}
	
	/**
	 * 获取联盟排行列表
	 * @param start
	 * @param count
	 * @return
	 */
	public List<Object> getAllianceRankList(int start, int count) {
		return smartRank.getRankList(start, count);
	}
	
	/**
	 * 获取玩家当前排行信息
	 * @param allianceId
	 * @return
	 */
	public int getAllianceRank(long allianceId) {
		return smartRank.getOwnerRank(allianceId);
	}
	
	public void refreshAllianceFightPower(long allianceId) {
		Alliance alliance = getRefreshAlliance(allianceId);
		if(alliance == null) {
			return;
		}
		
		long oldFightPower = alliance.getFightPower();
		synchronized (alliance) {
			long fightPower = 0;
			for(Long id : alliance.getAllianceMember()) {
				fightPower += FightPowerKit.FINAL_POWER.getValue(id).longValue();
			}
			alliance.setFightPower(fightPower);
			InjectorUtil.getInjector().dbCacheService.update(alliance);
		}
	
		// TODO 刷新联盟战力 可能消耗io资源
		if(alliance.getFightPower() != oldFightPower) {
			ResAllianceChangeMessage resAllianceChangeMessage = new ResAllianceChangeMessage();
			AllianceBean allianceBean = AllianceConverter.converterAllianceBean(alliance);
			resAllianceChangeMessage.alliance = allianceBean;
			sessionManager.writePlayers(alliance.getAllianceMember(), resAllianceChangeMessage);
		}
	}
	
	/**
	 * 获取玩家联盟数据
	 * @param playerId
	 * @return
	 */
	public Alliance getRefreshAlliance(long allianceId) {
		Alliance alliance = InjectorUtil.getInjector().dbCacheService.get(Alliance.class, allianceId);
		if(alliance == null) {
			return null;
		}
		
		synchronized(alliance) {
			boolean update  = alliance.refresh();
			if(update) {
				InjectorUtil.getInjector().dbCacheService.update(alliance);
			}
		}
		
		return alliance;
	}
	
	/**
	 * 军团长改名
	 * @param alliance
	 * @param newName
	 */
	public void allianceLeaderRename(Alliance alliance, String newName) {
		alliance.setLeaderName(newName);
		InjectorUtil.getInjector().dbCacheService.update(alliance);
		AllianceBean allianceBean = AllianceConverter.converterAllianceBean(alliance);
		ResAllianceChangeMessage resAllianceChangeMessage = new ResAllianceChangeMessage();
		resAllianceChangeMessage.alliance = allianceBean;
		
		// 刷新玩家军团
		sessionManager.writePlayers(alliance.getAllianceMember(), resAllianceChangeMessage);
	}
	
	
	/**
	 * 根据玩家id获取所在联盟
	 * @param playerId
	 * @return
	 */
	public Alliance getAllianceByPlayerId(long playerId) {
		PlayerAlliance playerAlliance = playerAllianceManager.getOrCreate(playerId);
		if(playerAlliance.getAllianceId() > 0) {
			return getRefreshAlliance(playerAlliance.getAllianceId());
		}
		
		return null;
	}
	
	/**
	 * 获取所有联盟ID
	 * @return
	 */
	public Set<Long> getAllianceIds() {
		return allianceIdNameMap.keySet();
	}
	
	/**
	 * 搜索玩家
	 * @param player
	 * @param playerName
	 */
	public void searchPlayer(Player player, String playerName) {
		
		if(StringUtils.isEmpty(playerName) || playerName.length() > 200) {
			return;
		}
		List<AlliancePlayerViewBean> list = new ArrayList<>();
		List<Long> playerIds = playerManager.getPlayerNamePattern(playerName);
		for(long playerId : playerIds){
			if(playerId > 0){
				Player searchPlayer = playerManager.get(playerId);
				//过滤联盟玩家
				if(searchPlayer != null && searchPlayer.getAllianceId() <=0){
					list.add(AllianceConverter.factoryAlliancePlayerViewBean(playerId));
				}
			}
		}
	}
	
}
