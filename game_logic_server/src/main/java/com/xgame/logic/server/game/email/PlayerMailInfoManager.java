package com.xgame.logic.server.game.email;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xgame.data.sprite.SpriteType;
import com.xgame.framework.lifecycle.Startup;
import com.xgame.framework.lifecycle.StartupOrder;
import com.xgame.logic.server.core.db.cache.cache.CacheProxy;
import com.xgame.logic.server.core.db.cache.entity.IEntity;
import com.xgame.logic.server.core.db.redis.RedisClient;
import com.xgame.logic.server.core.gamelog.event.EventBus;
import com.xgame.logic.server.core.language.Language;
import com.xgame.logic.server.core.language.view.error.ErrorCodeEnum;
import com.xgame.logic.server.core.language.view.success.SuccessTipEnum;
import com.xgame.logic.server.core.net.gate.SessionManager;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.core.utils.JsonUtil;
import com.xgame.logic.server.core.utils.scheduler.ScheduleTasks;
import com.xgame.logic.server.core.utils.scheduler.Scheduled;
import com.xgame.logic.server.core.utils.sort.CompareBuilder;
import com.xgame.logic.server.game.alliance.AllianceManager;
import com.xgame.logic.server.game.alliance.PlayerAllianceManager;
import com.xgame.logic.server.game.alliance.constant.AllianceConstant;
import com.xgame.logic.server.game.alliance.enity.Alliance;
import com.xgame.logic.server.game.alliance.enity.PlayerAlliance;
import com.xgame.logic.server.game.country.bean.Vector2Bean;
import com.xgame.logic.server.game.email.bean.AttackEmailInfo;
import com.xgame.logic.server.game.email.bean.BeScoutEmailInfo;
import com.xgame.logic.server.game.email.bean.CollectionEmailInfo;
import com.xgame.logic.server.game.email.bean.DefensiveEmailInfo;
import com.xgame.logic.server.game.email.bean.EmailInfo;
import com.xgame.logic.server.game.email.bean.EmailSignature;
import com.xgame.logic.server.game.email.bean.ReinforceEmailInfo;
import com.xgame.logic.server.game.email.bean.ScoutEmailInfo;
import com.xgame.logic.server.game.email.bean.TeamAttackEmailInfo;
import com.xgame.logic.server.game.email.bean.TerritoryEmailInfo;
import com.xgame.logic.server.game.email.bean.TradeEmailInfo;
import com.xgame.logic.server.game.email.constant.EmailTemplet;
import com.xgame.logic.server.game.email.constant.EmailType;
import com.xgame.logic.server.game.email.constant.MailConstant;
import com.xgame.logic.server.game.email.converter.EmailConverter;
import com.xgame.logic.server.game.email.data.investigate.InvestigateCommonMailData;
import com.xgame.logic.server.game.email.data.investigate.InvestigateResourceMailData;
import com.xgame.logic.server.game.email.entity.Email;
import com.xgame.logic.server.game.email.entity.PlayerEmaiInfo;
import com.xgame.logic.server.game.email.entity.UserEmail;
import com.xgame.logic.server.game.email.entity.UserEmailEntityFactory;
import com.xgame.logic.server.game.email.message.ResQueryAllEmailMessage;
import com.xgame.logic.server.game.friend.entity.ReleationShip;
import com.xgame.logic.server.game.friend.repository.ReleationShipRepository;
import com.xgame.logic.server.game.player.PlayerManager;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.radar.entity.PlayerRadarInvestigate;
import com.xgame.logic.server.game.war.bean.WarResourceBean;
import com.xgame.logic.server.game.war.constant.WarType;
import com.xgame.logic.server.game.war.entity.Battle;
import com.xgame.logic.server.game.war.entity.WarAttacker;
import com.xgame.logic.server.game.war.entity.WarDefender;
import com.xgame.logic.server.game.war.entity.WarFightParam;
import com.xgame.logic.server.game.world.entity.MarchCollect;
import com.xgame.logic.server.game.world.entity.WorldMarch;
import com.xgame.logic.server.game.world.entity.model.WorldMarchSoldier;


/**
 * 玩家邮件缓存
 * @author jacky.jiang
 *
 */
@Component
public class PlayerMailInfoManager extends CacheProxy<PlayerEmaiInfo> {

	@Autowired
	private EventBus eventBus;
	@Autowired
	private RedisClient redisClient;
	@Autowired
	private EmailManager mailManager;
	@Autowired
	private AllianceManager allianceManager;
	@Autowired
	private PlayerAllianceManager playerAllianceManager;
	@Autowired
	private UserEmailEntityFactory userEmailEntityFactory;
	@Autowired
	private PlayerManager playerManager;
	@Autowired
	private ReleationShipRepository releationShipRepository;
	@Autowired
	private SessionManager sessionManager;
	
	@Override
	public Class<?> getProxyClass() {
		return PlayerEmaiInfo.class;
	}
	
	public PlayerEmaiInfo getOrCreate(long playerId) {
		PlayerEmaiInfo playerEmaiInfo = InjectorUtil.getInjector().dbCacheService.get(PlayerEmaiInfo.class, playerId);
		if(playerEmaiInfo == null) {
			playerEmaiInfo = new PlayerEmaiInfo();
			playerEmaiInfo.setId(playerId);
			playerEmaiInfo = InjectorUtil.getInjector().dbCacheService.create(playerEmaiInfo);
		}
		return playerEmaiInfo;
	}
	
	
	@Startup(order = StartupOrder.EMAIL_START, desc = "邮件启动加载")
	public void init() {
		List<Email> emails = redisClient.hvals(Email.class);
		if(emails != null && !emails.isEmpty()) {
			for(Email email : emails) {
				mailManager.add(email);
			}
		}
	}
	
	/** 邮件清理，定时表达式，默认每日4点进行清理 */
	@Scheduled(name = "定时清除过期邮件", value = ScheduleTasks.CLEAN_EMAILS)
	public void scheduledCleanEmail() {
		// 删除过期邮件
		List<IEntity<?>> userMailUpdateList = new ArrayList<IEntity<?>>();
		List<PlayerEmaiInfo> userEmailList = (List<PlayerEmaiInfo>)redisClient.hvals(PlayerEmaiInfo.class);
		if(userEmailList != null && userEmailList.size() > 0) {
			for(PlayerEmaiInfo playerEmaiInfo : userEmailList) {
				boolean update = false;
				Set<Long> emailMap = playerEmaiInfo.getReceMailTag();
				for(Long id : emailMap) {
					UserEmail userEmail = InjectorUtil.getInjector().dbCacheService.get(UserEmail.class, id);
					if(userEmail.getEndTime().getTime() < System.currentTimeMillis()) {
						emailMap.remove(userEmail.getId());
						delete(userEmail,true,true);
						update = true;
					}
				}
				this.remove(playerEmaiInfo);
				userMailUpdateList.add(playerEmaiInfo);
				
				if(update) {
					InjectorUtil.getInjector().dbCacheService.update(playerEmaiInfo);
				}
			}

		}
		
		redisClient.saveBatch(userMailUpdateList);
		
		//删除过期系统邮件
		List<Email> emails = redisClient.hvals(Email.class);
		if (emails != null && !emails.isEmpty()) {
			for(Email email: emails) {
				if(email.getEndTime().getTime() < System.currentTimeMillis()) {
					InjectorUtil.getInjector().dbCacheService.delete(email);
				}
			}
		}
	}
	
	/**
	 * 查询邮件
	 * @param player
	 * @param tag 1-邮件；2-报告；3-保存；4-已发送
	 * @return
	 */
	public void listAllUserEmail(Player player,int tag) {
		ResQueryAllEmailMessage info = getResQueryAllEmailMessage(player,tag);
		if(info != null){
			player.send(info);
		}
	}
	
	/**
	 * 读取邮件
	 * @param emailIds
	 */
	public void readEamil(Player player,List<Long> emailIds){
		for(Long id : emailIds){
			UserEmail userEmail = InjectorUtil.getInjector().dbCacheService.get(UserEmail.class, id);
			if(userEmail != null){
				userEmail.setReaded(true);
				InjectorUtil.getInjector().dbCacheService.update(userEmail);
			}
		}
		pushNewEmailFlag(player);
	}
	
	/**
	 * 获取邮件列表信息
	 * @param player
	 * @param tag
	 * @return
	 */
	private ResQueryAllEmailMessage getResQueryAllEmailMessage(Player player,int tag){
		if(tag < 1 || tag > 4){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE10);
			return null;
		}
		int tag1Num=0,tag2Num=0,tag3Num=0,tag4Num=0;//未读邮件数量
		// 查询没有过期的全服邮件
		Collection<Email> emails = mailManager.getEntityCache().values();
		PlayerEmaiInfo playerEmaiInfo = this.getOrCreate(player.getId());
		List<UserEmail> userEmailList = new ArrayList<UserEmail>();
		boolean tagRefreshed = false;
		// 根据全服邮件生成玩家邮件
		if(emails != null && !emails.isEmpty()) {
			for(Email email: emails) {
				int tag0 = 0;//1-邮件；2-报告；3-保存；4-已发送
				Map<Long, Long> emailTagMail = playerEmaiInfo.getMailTag();
				if(emailTagMail.keySet().contains(email.getId().longValue())) {
					continue;
				}
				if(email.getEndTime().getTime() < System.currentTimeMillis()) {
					InjectorUtil.getInjector().dbCacheService.delete(email);
					if(tagRefreshed == false){
						tagRefreshed = emailTagMail.remove(email.getId()) != null;
					}
				} else {
					// 玩家创建时间必须小于邮件创建时间才能收到邮件
					if(player.roleInfo().getBasics().getCreateTime() < email.getCreateTime().getTime()) {
						if(email.getTargetId() > 0 && email.getEmailType() == EmailType.ALLIANCE) {
							Alliance alliance = allianceManager.getAllianceByPlayerId(player.getId());
							if(alliance != null && alliance.getId().equals(email.getTargetId())) {
								PlayerAlliance playerAlliance = playerAllianceManager.getOrCreate(player.getId());
								if(playerAlliance != null && playerAlliance.getJoinTime() < email.getCreateTime().getTime()) {
									UserEmail userEmail = userEmailEntityFactory.create(player.getId(), email);
									InjectorUtil.getInjector().dbCacheService.create(userEmail);
									if(userEmail.getType() != 4){
										tag0 = 1;
										tag1Num++;
									}else{
										tag2Num++;
									}
									if(tag0 == tag){
										userEmailList.add(userEmail);
									}
									playerEmaiInfo.addTag(email.getId(), userEmail.getId());
									playerEmaiInfo.addReceEmail(userEmail);
									if(userEmail.getSenderId().toString().equals(player.getId().toString())){
										playerEmaiInfo.addSendEmail(userEmail);
									}
									tagRefreshed = true;
								}
							}
						} else {
							UserEmail userEmail = userEmailEntityFactory.create(player.getId(), email);
							InjectorUtil.getInjector().dbCacheService.create(userEmail);
							if(userEmail.getType() != 4){
								tag0 = 1;
								tag1Num++;
							}else{
								tag2Num++;
							}
							if(tag0 == tag){
								userEmailList.add(userEmail);
							}
							playerEmaiInfo.addReceEmail(userEmail);
							playerEmaiInfo.addTag(email.getId(), userEmail.getId());
							if(userEmail.getSenderId().toString().equals(player.getId().toString())){
								playerEmaiInfo.addSendEmail(userEmail);
							}
							tagRefreshed = true;
						}
					}
				}
			}
		}
		List<Long> userEmailIdList = null;
		Set<Long> sendDels = new HashSet<Long>();
		Set<Long> ReceDels = new HashSet<Long>();
		userEmailIdList = new ArrayList<Long>(playerEmaiInfo.getReceMailTag());
		if (userEmailIdList != null && !userEmailIdList.isEmpty()) {
			for(Long userEmailId: userEmailIdList) {
				int tag0 = 0;//1-邮件；2-报告；3-保存；4-已发送
				UserEmail userEmail = InjectorUtil.getInjector().dbCacheService.get(UserEmail.class, userEmailId);
				if(userEmail != null) {
					// 判断玩家邮件是否过期，过期删除
					if(userEmail.getEndTime().getTime() <= System.currentTimeMillis()) {
						delete(userEmail,true,true);
						ReceDels.add(userEmail.getId());
					} else {
						if(userEmail.getType() == 4){//type:4-->tag:2,3
							if(userEmail.getMailState() == 1){
								tag0 = 2;
								if(!userEmail.isReaded()){
									tag2Num++;
								}
							}else if(userEmail.getMailState() == 2){
								tag0 = 3;
								if(!userEmail.isReaded()){
									tag3Num++;
								}
							}
							if(tag == tag0){
								userEmailList.add(userEmail);
							}else if(tag0 == 0){
								if(tag == 2 || tag == 3){
									if(tag == 2 && !userEmail.isReaded()){
										tag2Num++;
									}
									userEmailList.add(userEmail);
								}
							}
						}else{//type:1.2.3-->tag:1,3
							if(userEmail.getMailState() == 1){
								tag0 = 1;
								if(!userEmail.isReaded()){
									tag1Num++;
								}
							}else if(userEmail.getMailState() == 2){
								tag0 = 3;
								if(!userEmail.isReaded()){
									tag3Num++;
								}
							}
							if(tag == tag0){
								userEmailList.add(userEmail);
							}else if(tag0 == 0){//tag 1、3都有
								if(tag == 1 || tag == 3){
									if(tag == 1 && !userEmail.isReaded()){
										tag1Num++;
									}
									userEmailList.add(userEmail);
								}
							}
						}
					}
				}
			}
		}
		if(tag == 4){//已发送
			userEmailList.clear();
			userEmailIdList = new ArrayList<Long>(playerEmaiInfo.getSendMailTag());
			if(userEmailIdList != null && !userEmailIdList.isEmpty()){
				for(Long userEmailId : userEmailIdList){
					UserEmail userEmail = InjectorUtil.getInjector().dbCacheService.get(UserEmail.class, userEmailId);
					if(userEmail != null) {
						if(userEmail.getEndTime().getTime() <= System.currentTimeMillis()) {
							delete(userEmail,false,true);
							sendDels.add(userEmail.getId());
						} else {
							userEmailList.add(userEmail);
						}
					}
				}
			}
		}
		if(ReceDels.size() > 0 || sendDels.size() > 0){
			playerEmaiInfo.getReceMailTag().removeAll(ReceDels);
			playerEmaiInfo.getSendMailTag().removeAll(sendDels);
			tagRefreshed = true;
		}
		if(tagRefreshed){
			InjectorUtil.getInjector().dbCacheService.update(playerEmaiInfo);
		}
		// 处理邮件排序
		if(userEmailList != null && !userEmailList.isEmpty()) {
			int maxEmail = MailConstant.MAX_MAIL_NUM;
			if(userEmailList.size() > maxEmail) {
				// 已阅读 > 未阅读， 按有附件 > 没有附件 ，在按结束时间排序
				userEmialCompare(userEmailList);
				Collections.reverse(userEmailList);
				userEmailList = userEmailList.subList(0, maxEmail);
			}
		}
		// 按时间先后先后置顶显示
		List<EmailInfo> emailInfoList = EmailConverter.converterEmailInfoList(userEmailList,tag != 4);
		ResQueryAllEmailMessage info = EmailConverter.resQueryAllEmailMessageBuilder(emailInfoList, tag1Num, tag2Num, tag3Num, tag4Num,tag);
		return info;
	}
	
	public void setUserEmailRead(Set<Long> userEmailIds,Player player) {
		List<EmailInfo> emailInfoList = new ArrayList<EmailInfo>();
		List<UserEmail> emailList = new ArrayList<UserEmail>();
		for(Long userEmailId : userEmailIds) {
			UserEmail userEmail = InjectorUtil.getInjector().dbCacheService.get(UserEmail.class, userEmailId);
			if(userEmail == null || userEmail.getTargetId() != player.getId()) {
				continue;
			}
			emailList.add(userEmail);
		}

		for (UserEmail userEmail : emailList) {
			userEmail.setReaded(true);
			InjectorUtil.getInjector().dbCacheService.update(userEmail);
			EmailInfo emailInfo = EmailConverter.converterEmailInfo(userEmail,true);
			emailInfoList.add(emailInfo);
		}
	}
	
	/**
	 * 给单个玩家发送信件
	 * @param templateId
	 * @param sendUserId
	 * @param receiverUserId
	 * @param receiverName
	 * @param theme
	 * @param content
	 * @param mailType
	 * @param emailAttach
	 * @param sendNotice
	 * @return
	 */
	public void sendUserEmail(Player sendPlayer, String playerName,String subject, String content) {
		Long recePlayerId = playerManager.getPlayerIdByPlayerName(playerName);
		if(recePlayerId == null || recePlayerId <= 0) {
			Language.ERRORCODE.send(sendPlayer, ErrorCodeEnum.E001_LOGIN.CODE15);
			return;
		}
		
		Player receiverUserInfo  = InjectorUtil.getInjector().dbCacheService.get(Player.class, recePlayerId);
		if(receiverUserInfo == null) {
			Language.ERRORCODE.send(sendPlayer, ErrorCodeEnum.E001_LOGIN.CODE2);
			return;
		}
		
		//他的联系人信息
		ReleationShip releationShipOther = releationShipRepository.getReleationShip(recePlayerId);
		//他黑名单里面有没有我
		if(releationShipOther!=null&&releationShipOther.existBlackList(sendPlayer.getRoleId())){
			Language.ERRORCODE.send(sendPlayer, ErrorCodeEnum.E410_MAIL.CODE7);
			return;
		}
		//自己的联系人信息
		ReleationShip releationShipSeft = releationShipRepository.getReleationShip(sendPlayer.getRoleId());
		//自己的联系人信息有没有他
		if(releationShipSeft!=null&&releationShipSeft.existBlackList(recePlayerId)){
			Language.ERRORCODE.send(sendPlayer, ErrorCodeEnum.E410_MAIL.CODE8);
			return;
		}
		
		// 最大邮件数量
		List<Long> userEmailIds = new ArrayList<>();
		if(userEmailIds!=null && userEmailIds.size() >= MailConstant.MAX_MAIL_NUM) {//邮件满了删除最早的一封
			List<UserEmail> userEmailList = new ArrayList<UserEmail>();
			for(Long id: userEmailIds) {
				UserEmail receiverEmail = InjectorUtil.getInjector().dbCacheService.get(UserEmail.class, id);
				if(receiverEmail  != null) {
					userEmailList.add(receiverEmail);
				}
			}
			
			if(userEmailList != null && !userEmailList.isEmpty()) {
				userEmialCompare(userEmailList);
				UserEmail deleteEmail = userEmailList.get(0);
				InjectorUtil.getInjector().dbCacheService.delete(deleteEmail);
			}
		}
		MailKit.sendUserEmail(sendPlayer.getId(), sendPlayer.getName(), sendPlayer.getLevel(), recePlayerId, subject, content);
	}
	
	/**
	 * 邮件排序
	 * @param userEmailList
	 */
	private void userEmialCompare(List<UserEmail> userEmailList) {
		
		// 在按结束时间排序, 已阅读  > 未阅读，按有附件 > 没有附件
		Collections.sort(userEmailList, new Comparator<UserEmail>() {
			@Override
			public int compare(UserEmail o1, UserEmail o2) {
				if(o1 != null && o2 != null) {
					if(o1.isReaded() && !o2.isReaded()) {
						return -1;
					} else if(!o1.isReaded() && o2.isReaded()) {
						return 1;
					} else {
						if(o2.getAddition() == null && o1.getAddition() != null) {
							return -1;
						} else if(o2.getAddition() != null && o1.getAddition() == null) {
							return 1;
						} else {
							if(o1.getAddition() != null && o2.getAddition() != null) {
								if(StringUtils.isBlank(o2.getAddition()) && !StringUtils.isBlank(o1.getAddition())) {
									return -1;
								} else {
									return new CompareBuilder().asc(o1.getEndTime().getTime(), o2.getEndTime().getTime()).compare();
								}
							} else {
								return new CompareBuilder().asc(o1.getEndTime().getTime(), o2.getEndTime().getTime()).compare();
							}
						}
					}
				}
				return 0;
			}
		});
		
	}
	
	
	public void sendAllianceEmail(Player player, String theme, String content) {
		
		PlayerAlliance playerAlliance = playerAllianceManager.getOrCreate(player.getId());
		if(playerAlliance.getAllianceId() <= 0) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		
		Alliance alliance = allianceManager.getRefreshAlliance(playerAlliance.getAllianceId());
		if(alliance == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE27);
			return;
		}
		
		if(playerAlliance.getId() != alliance.getLeaderId() && !playerAlliance.getAlliancePermission().isSendMail()) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE15);
			return;
		}
		
		if(alliance.getCount() >= AllianceConstant.ALLIANCE_EMAIL_MAX_SIZE) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE31);
			return;
		}
		
		alliance.setCount(alliance.getCount() + 1);
		InjectorUtil.getInjector().dbCacheService.update(alliance);
		
		//因为player拥有发公会邮件权限，故直接以player的名义发送，不需要特意变为用会长的名义来发送
		MailKit.sendAllianceEmail(theme, content, alliance.getId(), player.getName(),player.getId(), player.getLevel());
		
		//  发送军团邮件提示
		Language.SUCCESSTIP.send(player, SuccessTipEnum.E1007_ALLIANCE.CODE22, AllianceConstant.ALLIANCE_EMAIL_MAX_SIZE - alliance.getCount());
	}

	
	/**
	 * 删除邮件
	 * @param userEmaillIds
	 * @param player
	 */
	public void deleteUserEmail(Set<Long> userEmaillIds, Player player) {
		// 校验邮件接收者是否是自己
		List<UserEmail> emailList = new ArrayList<UserEmail>();
		for(Long userEmailId : userEmaillIds) {
			UserEmail userEmail = InjectorUtil.getInjector().dbCacheService.get(UserEmail.class, userEmailId);
			if(userEmail != null) {
				if(!String.valueOf(userEmail.getTargetId()).equals(player.getId().toString()) && !String.valueOf(userEmail.getSenderId()).equals(player.getId().toString())) {
					Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE15);
					return;
				}
				emailList.add(userEmail);
			} else {
				Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE15);
				return;
			}
		}
		PlayerEmaiInfo playerEmaiInfo = this.getOrCreate(player.getId());
		Set<Long> emailMap = playerEmaiInfo.getReceMailTag();
		// 删除操作
		List<Long> ids = new ArrayList<>();
		for(UserEmail userEmail : emailList) {
			ids.add(userEmail.getId());
			boolean isReceiver = String.valueOf(userEmail.getTargetId()).equals(player.getId().toString());
			boolean isDel = delete(userEmail,isReceiver,false);
			if(isDel){
				emailMap.remove(userEmail.getId());
			}
			if(!isReceiver){
				playerEmaiInfo.getSendMailTag().remove(userEmail.getId());
			}
		}
		InjectorUtil.getInjector().dbCacheService.update(playerEmaiInfo);
		player.send(EmailConverter.resDeleteEmailMessageBuilder(ids));
		pushNewEmailFlag(player);
	}
	
	/**
	 * 删除邮件
	 * @param userEmail
	 * @param receiver 是否是接收者
	 * @param forcedDel 强制删除
	 */
	private boolean delete(UserEmail userEmail,boolean isReceiver,boolean forcedDel){
		if(forcedDel){
			InjectorUtil.getInjector().dbCacheService.delete(userEmail);
			return true;
		}else{
			if(userEmail.getMailState() == 3){
				userEmail.setMailState(2);
				InjectorUtil.getInjector().dbCacheService.update(userEmail);
				return false;
			}else{
				if(userEmail.getEmailDeleteState() == 0){
					InjectorUtil.getInjector().dbCacheService.delete(userEmail);
					return true;
				}else{
					if(userEmail.getEmailDeleteState() == 1){
						if(!isReceiver){
							userEmail.setEmailDeleteState(2);
						}else{
							userEmail.setEmailDeleteState(3);
						}
						InjectorUtil.getInjector().dbCacheService.update(userEmail);
						return false;
					}else{
						InjectorUtil.getInjector().dbCacheService.delete(userEmail);
						return true;
					}
				}
			}
		}
	}
	
	/**
	 * 保存邮件
	 * @param player
	 * @param emailId
	 * @param isSave true-保存；false-取消保存
	 */
	public void saveEmail(Player player,long emailId,boolean isSave){
		UserEmail userEmail = InjectorUtil.getInjector().dbCacheService.get(UserEmail.class, emailId);
		if(userEmail == null){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E410_MAIL.CODE9);
			return;
		}
		if(isSave){//保存
			userEmail.setMailState(3);
		}else{//取消保存
			if(userEmail.getMailState() == 2){
				delete(userEmail,true,false);
				PlayerEmaiInfo playerEmaiInfo = this.getOrCreate(player.getId());
				Set<Long> emailMap = playerEmaiInfo.getReceMailTag();
				emailMap.remove(userEmail.getId());
				InjectorUtil.getInjector().dbCacheService.update(playerEmaiInfo);
			}else{
				userEmail.setMailState(1);
			}
		}
		InjectorUtil.getInjector().dbCacheService.update(userEmail);
		player.send(EmailConverter.resSaveEmailBuilder(EmailConverter.converterEmailInfo(userEmail,true),isSave));
	}

	/**
	 * 被侦查玩家邮件
	 * @param attackPlayer
	 * @param beAttackPlayer
	 * @param x
	 * @param y
	 * @param level
	 * @param emailTempletId
	 */
	public void beScout(Player attackPlayer,Player beAttackPlayer, int x,int y, int type, int level,int emailTempletId) {
		// 发送邮件
		beAttackPlayer.async(new Runnable() {
			@Override
			public void run() {
				BeScoutEmailInfo beScoutEmailInfo = EmailConverter.beScoutEmailInfoBuilder(attackPlayer, beAttackPlayer, x, y, type, level, emailTempletId);
				String content = JsonUtil.toJSON(beScoutEmailInfo);
				MailKit.sendReportEmail(beAttackPlayer.getId(), emailTempletId, content);
			}
		});
	}
	
	public void sendScoutProtect(Player attackPlayer) {
		// 发送邮件
		attackPlayer.async(new Runnable() {
			@Override
			public void run() {
				MailKit.sendReportEmail(attackPlayer.getId(), EmailTemplet.侦查保护报告_MAIL_ID, null);
			}
		});
	}
	
	/**
	 * 侦查资源报告
	 * @param player
	 * @param beScoutPlayer
	 * @param investigateResourceMailData
	 */
	public void scoutOfResource(Player player,InvestigateResourceMailData investigateResourceMailData,int x,int y){
		player.async(new Runnable() {
			@Override
			public void run() {
				ScoutEmailInfo scoutEmailInfo = EmailConverter.scoutEmailInfoBuilder(player, investigateResourceMailData, x, y);
				String content = JsonUtil.toJSON(scoutEmailInfo);
				MailKit.sendReportEmail(player.getId(), EmailTemplet.资源点侦察报告_MAIL_ID, content);
			}
		});
	}
	
	/**
	 * 侦查通用报告（扎营，领地）
	 * @param player
	 * @param investigateCampMailData
	 */
	public void scoutOfCommon(Player player, InvestigateCommonMailData investigateCampMailData) {
		player.async(new Runnable() {
			@Override
			public void run() {
				ScoutEmailInfo scoutEmailInfo = EmailConverter.scoutEmailInfoBuilder(player, investigateCampMailData);
				String content = JsonUtil.toJSON(scoutEmailInfo);
				int emailId = 0;
				if(investigateCampMailData.getSpriteType() == SpriteType.CAMP){
					emailId = EmailTemplet.营地侦察报告_MAIL_ID;
				}else if(investigateCampMailData.getSpriteType() == SpriteType.TERRITORY){
					emailId = EmailTemplet.领地侦察报告_MAIL_ID;
				}
				MailKit.sendReportEmail(player.getId(),emailId, content);
			}
		});
	}
	
	/**
	 * 基地侦查报告
	 * @param player
	 * @param investigateResourceMailData
	 * @param x
	 * @param y
	 */
	public void scoutOfPlayer(Player player,PlayerRadarInvestigate playerRadarInvestigate,int x,int y){
		player.async(new Runnable() {
			@Override
			public void run() {
				ScoutEmailInfo scoutEmailInfo = EmailConverter.scoutEmailInfoBuilder(player, playerRadarInvestigate, x, y);
				String content = JsonUtil.toJSON(scoutEmailInfo);
				MailKit.sendReportEmail(player.getId(), EmailTemplet.基地侦察报告_MAIL_ID, content);
			}
		});
	}
	
	/**
	 * 侦查、进攻坐标点异常
	 * @param player
	 * @param emailId
	 * @param x
	 * @param y
	 */
	public void sendPositionException(Player player,EmailSignature defSignature,int emailId){
		player.async(new Runnable() {
			@Override
			public void run() {
				String content = JsonUtil.toJSON(defSignature);
				MailKit.sendReportEmail(player.getId(), emailId,content);
			}
		});
	}
	
	/**
	 * 驻防报告
	 * @param player
	 * @param beReinforce
	 * @param attackWorldMarch
	 * @param targetX
	 * @param targetY
	 * @param isReinforce 
	 */
	public void sendReinforceEmail(Player player, Player beReinforce,WorldMarch attackWorldMarch,int targetX,int targetY,boolean isReinforce){
		beReinforce.async(new Runnable() {
			@Override
			public void run() {
				ReinforceEmailInfo info =EmailConverter.reinforceEmailInfoBuilder(player, beReinforce, attackWorldMarch, targetX, targetY);
				String content = JsonUtil.toJSON(info);
				int emailId;
				if(isReinforce){
					emailId = EmailTemplet.驻防报告_MAIL_ID;
				}else{
					emailId = EmailTemplet.撤防报告_MAIL_ID;
				}
				MailKit.sendReportEmail(beReinforce.getId(),emailId, content);
			}
		});
	}
	
	/**
	 * 占领成功邮件
	 * @param battle
	 */
	public void sendTerritoryEmail(Player player,WorldMarch attackWorldMarch,int targetX,int targetY){
		player.async(new Runnable() {
			@Override
			public void run() {
				TerritoryEmailInfo info = EmailConverter.territoryEmailInfoBuidler(player, attackWorldMarch, targetX, targetY);
				String content = JsonUtil.toJSON(info);
				MailKit.sendReportEmail(player.getId(),EmailTemplet.占领成功_MAIL_ID, content);
			}
		});
	}
	
	/**
	 * 通用战报
	 * @param battle
	 */
	public void sendBattleEmail(Battle battle,int attEmailId,int defEmail,WarType warType,Object... params) {
		WarAttacker warAttacker = battle.getWarAttacker();
		WarDefender warDefender = battle.getWarDefender();
		AttackEmailInfo attackEmailInfo = EmailConverter.attackEmailInfoBuilder(battle, attEmailId, defEmail, warType, params);
		String content = JsonUtil.toJSON(attackEmailInfo);
		
		// 发送邮件
		Player sendPlayer = InjectorUtil.getInjector().dbCacheService.get(Player.class, warAttacker.getPlayer().getRoleId());
		
		MailKit.sendReportEmail(sendPlayer.getId(),attEmailId, content);
		MailKit.sendReportEmail(warDefender.getPlayer().getId(), defEmail, content);
	}
	
	/**
	 * 交易报告
	 * @param player
	 * @param targetPlayerId
	 * @param resMoney
	 * @param resOil
	 * @param resRare
	 * @param resSteel
	 */
	public void sendTradeEmail(Player player,long targetPlayerId,int resMoney,int resOil,int resRare,int resSteel) {
		TradeEmailInfo tradeEmailInfo = EmailConverter.tradeEmailInfoBuilder(player, resMoney, resOil, resRare, resSteel);
		String content = JsonUtil.toJSON(tradeEmailInfo);
		MailKit.sendReportEmail(targetPlayerId,EmailTemplet.贸易报告_MAIL_ID, content);
	}
	
	/**
	 * 收索战斗防守报告
	 * @param player
	 * @param targetPlayerId
	 * @param list
	 * @param resMoney
	 * @param resOil
	 * @param resRare
	 * @param resSteel
	 * @param isWin
	 */
	public void sendDefensiveEmail(Player player,long targetPlayerId,Battle battle,boolean defSuccess){
		long resMoney = 0;
		long resOil = 0;
		long resRare = 0;
		long resSteel = 0;
		int emailId;
		if(!defSuccess){
			emailId = EmailTemplet.防守失败_MAIL_ID;
			WarResourceBean warResourceBean = battle.getWarResource().get(player.getRoleId());
			if(warResourceBean != null) {
				resMoney = warResourceBean.moneyNum;
				resOil = warResourceBean.oilNum;
				resRare = warResourceBean.rareNum;
				resSteel = warResourceBean.steelNum;
			}
		}else{
			emailId = EmailTemplet.防守胜利_MAIL_ID;
		}
		WorldMarchSoldier worldMarchSoldier = battle.getWarAttacker().getWorldMarchSoldierMap().get(player.getId());
		DefensiveEmailInfo info = EmailConverter.defensiveEmailInfoBuilder(player, worldMarchSoldier.querySoldierList(), resMoney, resOil, resRare, resSteel);
		String content = JsonUtil.toJSON(info);
		MailKit.sendReportEmail(targetPlayerId,emailId, content);
	}
	
	/**
	 * 采集报告
	 * @param player
	 * @param position
	 * @param marchCollect
	 */
	public void sendCollectionEmailInfo(Player player,Vector2Bean position,MarchCollect marchCollect){
		CollectionEmailInfo collectionEmailInfo = EmailConverter.collectionEmailInfoBuilder(player, position, marchCollect);
		String content = JsonUtil.toJSON(collectionEmailInfo);
		MailKit.sendReportEmail(player.getId(), EmailTemplet.采集报告_MAIL_ID, content);
	}
	
	/**
	 * 集结站报告
	 * @param battle
	 */
	public void sendTeamAttackEmailInfo(Battle battle){
		WarFightParam warFightParam = battle.getWarFightParam();
		TeamAttackEmailInfo info = EmailConverter.teamAttackEmailInfo(battle);
		String content = JsonUtil.toJSON(info);
		int emailId = info.winner == 1 ? EmailTemplet.基地集结进攻报告_MAIL_ID : EmailTemplet.基地集结防守报告_MAIL_ID;
		//进攻方发送战报
		for(WorldMarch wm : warFightParam.getAttackMarchList()){
			long playerId = Long.parseLong(wm.getOwnerUid());
			MailKit.sendReportEmail(playerId,emailId,content);
		}
		WarDefender warDefender = battle.getWarDefender();
		//防守方发送战报
		MailKit.sendReportEmail(warDefender.getPlayer().getId(),emailId,content);
		for(Long playerId : warDefender.getReinforce().keySet()){
			MailKit.sendReportEmail(playerId,emailId,content);
		}
	}
	
	/**
	 * 推送新邮件标识(收到新邮件)
	 * @param playerId
	 */
	public void pushNewEmailFlag(long playerId){
		Player player = InjectorUtil.getInjector().dbCacheService.get(Player.class, playerId);
		player.send(EmailConverter.resNewEmailFlagBuilder(true));
	}
	
	/**
	 * 推送新邮件标识（判断是否有新邮件）
	 * @param player
	 */
	public void pushNewEmailFlag(Player player){
		ResQueryAllEmailMessage info = getResQueryAllEmailMessage(player,1);
		boolean hasNewEmail = false;
		if(info != null){
			hasNewEmail = info.tag1Num > 0 || info.tag2Num > 0;
		}
		player.send(EmailConverter.resNewEmailFlagBuilder(hasNewEmail));
	}
}
