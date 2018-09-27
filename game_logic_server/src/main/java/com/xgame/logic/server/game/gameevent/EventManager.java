package com.xgame.logic.server.game.gameevent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Tuple;

import com.xgame.config.event.EventPir;
import com.xgame.config.event.EventPirFactory;
import com.xgame.config.eventRank.EventRankPir;
import com.xgame.config.eventScore.EventScorePir;
import com.xgame.config.eventScore.EventScorePirFactory;
import com.xgame.config.eventTrim.EventTrimPir;
import com.xgame.config.eventTrim.EventTrimPirFactory;
import com.xgame.framework.lifecycle.Startup;
import com.xgame.framework.lifecycle.StartupOrder;
import com.xgame.gameconst.DBKey;
import com.xgame.logic.server.core.db.cache.cache.CacheProxy;
import com.xgame.logic.server.core.db.redis.GlobalRedisClient;
import com.xgame.logic.server.core.db.redis.RedisClient;
import com.xgame.logic.server.core.gamelog.constant.GameLogSource;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.core.utils.ParamFormatUtils;
import com.xgame.logic.server.core.utils.scheduler.ScheduledTask;
import com.xgame.logic.server.core.utils.scheduler.Scheduler;
import com.xgame.logic.server.core.utils.sequance.IDFactrorySequencer;
import com.xgame.logic.server.core.utils.sort.MapRankableComparator;
import com.xgame.logic.server.core.utils.sort.RefreshResult;
import com.xgame.logic.server.core.utils.sort.SmartRank;
import com.xgame.logic.server.game.alliance.AllianceManager;
import com.xgame.logic.server.game.alliance.enity.Alliance;
import com.xgame.logic.server.game.awardcenter.AwardUtil;
import com.xgame.logic.server.game.constant.SystemEnum;
import com.xgame.logic.server.game.email.MailKit;
import com.xgame.logic.server.game.email.constant.EmailTemplet;
import com.xgame.logic.server.game.gameevent.bean.EventBean;
import com.xgame.logic.server.game.gameevent.bean.EventRankInfoBean;
import com.xgame.logic.server.game.gameevent.bean.EventRankInfoBeanList;
import com.xgame.logic.server.game.gameevent.config.ConfigResolve;
import com.xgame.logic.server.game.gameevent.constant.EventTypeEnum;
import com.xgame.logic.server.game.gameevent.converter.EventConverter;
import com.xgame.logic.server.game.gameevent.entity.AllianceEvent;
import com.xgame.logic.server.game.gameevent.entity.Event;
import com.xgame.logic.server.game.gameevent.entity.EventMaxScore;
import com.xgame.logic.server.game.gameevent.entity.EventRankable;
import com.xgame.logic.server.game.gameevent.entity.EventScoreInfo;
import com.xgame.logic.server.game.gameevent.entity.PlayerEvent;
import com.xgame.logic.server.game.gameevent.message.ResEventDetailMessage;
import com.xgame.logic.server.game.gameevent.message.ResEventListMessage;
import com.xgame.logic.server.game.gameevent.message.ResHistoryRankMessage;
import com.xgame.logic.server.game.player.PlayerManager;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.timertask.constant.SystemTimerCommand;
import com.xgame.logic.server.game.timertask.entity.system.SystemTimerTaskHolder;
import com.xgame.utils.TimeUtils;

/**
 * 联盟
 * @author jacky.jiang
 *
 */
@Slf4j
@Component
public class EventManager extends CacheProxy<Event> {

	@Autowired
	private RedisClient redisClient;
	@Autowired
	private GlobalRedisClient globalRedisClient;
	@Autowired
	private PlayerEventManager playerEventManager;
	@Autowired
	private AllianceEventManager allianceEventManager;
	@Autowired
	private EventMaxScoreManager eventMaxScoreManager;
	@Autowired
	private ConfigResolve configResolve;
	@Autowired
	private PlayerManager playerManager;
	@Autowired
	private AllianceManager allianceManager;
	@Autowired
	private Scheduler scheduler;
	@Autowired 
	private IDFactrorySequencer idSequencer;
	/**
	 * 维护三个事件的本地排行
	 */
	private SmartRank smartRankGroup = new SmartRank(new MapRankableComparator("score DESC"), DBKey.CURRENT_EVENT_RANK_SIZE);
	private SmartRank smartRankPlayer = new SmartRank(new MapRankableComparator("score DESC"), DBKey.CURRENT_EVENT_RANK_SIZE);
	private SmartRank smartRankHonour = new SmartRank(new MapRankableComparator("score DESC"), DBKey.CURRENT_EVENT_RANK_SIZE);

	@Override
	public Class<?> getProxyClass() {
		return Event.class;
	}
	
	/**
	 * 事件启动加载
	 * 1.扫描事件开启事件
	 * 2.启动定时扫描
	 */
	@Startup(order = StartupOrder.EVENT_START, desc = "事件启动加载")
	public void load() {
		startEvent();
		//启动定时器
		scheduler.scheduleAtFixedRate(new ScheduledTask(){
			public String getName() {
				return Event.class.getName();
			}

			public void run() {
				startEvent();
			}
		}, new Date(), 60000);
	}
	
	/**
	 * 事件开始触发
	 */
	public void startEvent(){
		log.info("开始事件扫描");
		//加载event数据
		List<Event> eventList = configResolve.getEventList();
		List<Event> list = getDbEventList();
		for(Event event : eventList){
			Boolean flag = true;
			if(list != null){
				for(Event dbEvent : list){
					if(dbEvent.getEventType() == event.getEventType()){
						flag = false;
					}
				}
			}
			
			if(flag){
				long eventId = event.getId();
				event = InjectorUtil.getInjector().dbCacheService.create(event);
				if(event.getEventType()==EventTypeEnum.Group.getValue()){
					//遍历所有联盟添加eventinfo
					Set<Long> allianceIds = allianceManager.getAllianceIds();
					if(allianceIds != null){
						for(long allianceId : allianceIds){
							Alliance alliance = allianceManager.get(allianceId);
							AllianceEvent allianceEvent = allianceEventManager.getOrCreate(alliance.getId());
							//根据联盟信息初始化EventScoreInfo
							EventScoreInfo esi = getAllianceEventScoreInfo(event,alliance);
							allianceEvent.getEventScoreInfoMap().put(event.getId(), esi);
							InjectorUtil.getInjector().dbCacheService.update(allianceEvent);
						}
					}
				}else{
					//遍历在线玩家添加eventinfo 未在线的玩家登录时进行初始化
					List<Long> playerIds = playerManager.getOnlinePlayerIds();
					if(playerIds != null){
						for(long playerId : playerIds){
							Player player = playerManager.getPlayer(playerId);
							PlayerEvent playerEvent = playerEventManager.getOrCreate(playerId);
							//根据用户信息初始化EventScoreInfo
							EventScoreInfo esi = getPlayerEventScoreInfo(event,player);
							playerEvent.getEventScoreInfoMap().put(event.getId(), esi);
							InjectorUtil.getInjector().dbCacheService.update(playerEvent);
						}
					}
				}
				//注册事件结束任务
				SystemTimerTaskHolder.getTimerTask(SystemTimerCommand.GAME_EVENT).register((int)(event.getEndTime()/1000-TimeUtils.getCurrentTime()),eventId);
			}
		}
		log.info("结束事件扫描");
	}
	
	/**
	 * 事件结束触发
	 * 删除完成事件
	 * 维护历史排行榜
	 * 维护最大积分表
	 * 发放排行奖励
	 * @param eventId
	 */
	public void stopEvent(long eventId){
		log.info("开始事件完成逻辑");
		Event event = InjectorUtil.getInjector().dbCacheService.get(Event.class, eventId);
		if(null != event){
			if(event.getEventType()==EventTypeEnum.Group.getValue()){
				this.allianceEventStop(event);
				smartRankGroup.clear();
			}else if(event.getEventType()==EventTypeEnum.Player.getValue()){
				this.playerEventStop(event);
				smartRankPlayer.clear();
			}else if(event.getEventType()==EventTypeEnum.Honour.getValue()){
				this.playerEventStop(event);
				smartRankHonour.clear();
			}
			
			//eventRankKey公共服当前事件排行的键值 redis结构Sorted Sets
			String eventRankKey = DBKey.GLOBAL_RANK_KEY+":"+eventId;
			//获取排行榜前50的数据
			Set<Tuple> rankSet = globalRedisClient.zrevrangewithscore(eventRankKey, 0, 49);
			if(null != rankSet && !rankSet.isEmpty()){
				//获取最大积分 给玩家发奖
				this.getMaxScoreAndRewards(rankSet,event);
				//serverRankKey用于存储历史排行 redis结构Lists
				String serverRankKey = DBKey.GLOBAL_RANK_KEY+configResolve.getServerEventId();
				//serverRankKeyType用于存储事件关联信息 redis结构Hashes
				String serverRankKeyType = DBKey.GLOBAL_RANK_KEY+configResolve.getServerEventId()+"_TYPE";
				//维护历史排行
				//首先判断历史排行榜中是否已经存在该事件 不存在则继续
				if(!eventRankKey.equals(globalRedisClient.lindex(serverRankKey, 0))){
					//将当前事件id加入历史排行榜
					globalRedisClient.lpush(serverRankKey, eventRankKey);
					//存储事件关联信息
					globalRedisClient.hset(serverRankKeyType, eventRankKey, String.valueOf(event.getEventId()));
					if(globalRedisClient.llen(serverRankKey) != -1 && globalRedisClient.llen(serverRankKey)>10){
						String fstElement = globalRedisClient.rpop(serverRankKey);
						if(fstElement != null){
							globalRedisClient.del(fstElement);
							globalRedisClient.hdel(serverRankKeyType, fstElement);
						}
					}
				}
			}
			//删除事件表中事件
			InjectorUtil.getInjector().dbCacheService.delete(event);
		}
		
		log.info("结束事件完成逻辑");
	}
	
	/**
	 * 玩家登录加载事件信息
	 * @param player
	 */
	public void loginLoad(Player player) {
		List<Event> list = getDbEventList();
		for(Event event : list){
			if(event.getEventType()==EventTypeEnum.Player.getValue() || event.getEventType()==EventTypeEnum.Honour.getValue()){
				PlayerEvent playerEvent = playerEventManager.getOrCreate(player.getId());
				//根据用户信息初始化EventScoreInfo
				if(playerEvent.getEventScoreInfoMap() == null || playerEvent.getEventScoreInfoMap().get(event.getId()) == null){
					EventScoreInfo esi = getPlayerEventScoreInfo(event,player);
					playerEvent.getEventScoreInfoMap().put(event.getId(), esi);
					InjectorUtil.getInjector().dbCacheService.update(playerEvent);
				}
			}
		}
	}
	
	/**
	 * 军团创建加载事件信息
	 * @param alliance
	 */
	public void allianceCreateEventInit(Alliance alliance){
		List<Event> list = getDbEventList();
		for(Event event : list){
			if(event.getEventType()==EventTypeEnum.Group.getValue()){
				AllianceEvent allianceEvent = allianceEventManager.getOrCreate(alliance.getId());
				//根据联盟信息初始化EventScoreInfo
				EventScoreInfo esi = getAllianceEventScoreInfo(event,alliance);
				allianceEvent.getEventScoreInfoMap().put(event.getId(), esi);
				InjectorUtil.getInjector().dbCacheService.update(allianceEvent);
			}
		}
	}
	
	/**
	 * 个人及荣耀事件结束
	 */
	private void playerEventStop(Event event) {
		//遍历所有用户添加eventinfo
		Collection<Long> playerIds = playerManager.getServerPlayerIds();
		if(playerIds != null){
			for(long playerId : playerIds){
				PlayerEvent playerEvent = playerEventManager.getOrCreate(playerId);
				//删除事件
				if(playerEvent.getEventScoreInfoMap() != null){
					playerEvent.getEventScoreInfoMap().remove(event.getId());
				}
				InjectorUtil.getInjector().dbCacheService.update(playerEvent);
			}
		}
	}
	
	/**
	 * 军团事件结束
	 */
	private void allianceEventStop(Event event){
		//遍历所有联盟添加eventinfo
		Set<Long> allianceIds = allianceManager.getAllianceIds();
		if(allianceIds != null){
			for(long allianceId : allianceIds){
				AllianceEvent allianceEvent = allianceEventManager.getOrCreate(allianceId);
				//删除事件
				if(allianceEvent.getEventScoreInfoMap() != null){
					allianceEvent.getEventScoreInfoMap().remove(event.getId());
				}
				InjectorUtil.getInjector().dbCacheService.update(allianceEvent);
			}
		}
	}
	
	/**
	 * 发放本服玩家奖励及获取最大玩家积分
	 * @param rankSet
	 */
	private void getMaxScoreAndRewards(Set<Tuple> rankSet,Event event){
		EventMaxScore ems = eventMaxScoreManager.get(event.getEventId());
		if(ems == null){
			ems = new EventMaxScore();
			ems.setEventId((long)event.getEventId());
		}
		int maxScore = 0;
		int count = 1;
		for(Tuple keyValue : rankSet){
			if(count == 1){
				maxScore = (int)keyValue.getScore();
			}
			String key = keyValue.getElement();
			int serverId = Integer.parseInt(key.substring(key.indexOf("#")+1, key.indexOf("[")));
			long playerOrAllianceId = Long.parseLong(key.split("#")[0]);
			//判断是否本服务器
			if(serverId == InjectorUtil.getInjector().serverId){
				List<EventRankPir> list = configResolve.getEvenRank(event.getEventId());
				for(EventRankPir erp : list){
					int min,max = 0;
					if(erp.getRank().toString().contains("-")){
						min = Integer.parseInt(erp.getRank().toString().split("-")[0]);
						max = Integer.parseInt(erp.getRank().toString().split("-")[1]);
					}else{
						min = Integer.parseInt(erp.getRank().toString());
						max = Integer.parseInt(erp.getRank().toString());
					}
					//符合排名及积分要求
					if(count>=min && count<=max && keyValue.getScore()>=erp.getLimit()){
						//判断事件类型,决定发给玩家还是联盟玩家
						if(event.getEventType()==EventTypeEnum.Group.getValue()){
							Alliance alliance = allianceManager.get(playerOrAllianceId);
							Set<Long> members = alliance.getAllianceMember();
							for(Long member : members){
								Player player = playerManager.get(member);
								
								for(String item : erp.getRewards().toString().split(",")){
									String[] itemInfo = item.split("_");
									AwardUtil.ITEM.giveCenter(player, Integer.parseInt(itemInfo[0]), Integer.parseInt(itemInfo[1]), SystemEnum.EVENT.getId(), GameLogSource.EVENT_ACHIVE);
								}
							}
							MailKit.sendAllianceEmail("", EmailTemplet.军团事件排名奖励1_MAIL_ID, ParamFormatUtils.formatParma(count), erp.getRewards().toString(), playerOrAllianceId, "",0, 0);
						}else{
							Player player = playerManager.get(playerOrAllianceId);
							for(String item : erp.getRewards().toString().split(",")){
								String[] itemInfo = item.split("_");
								AwardUtil.ITEM.giveCenter(player, Integer.parseInt(itemInfo[0]), Integer.parseInt(itemInfo[1]), SystemEnum.EVENT.getId(), GameLogSource.EVENT_ACHIVE);
							}
							if(event.getEventType()==EventTypeEnum.Player.getValue()){
								MailKit.sendSystemEmail(playerOrAllianceId, EmailTemplet.个人事件排名奖励_MAIL_ID,ParamFormatUtils.formatParma(count),erp.getRewards().toString());
							}else if(event.getEventType()==EventTypeEnum.Honour.getValue()){
								MailKit.sendSystemEmail(playerOrAllianceId, EmailTemplet.荣耀事件排名奖励_MAIL_ID,ParamFormatUtils.formatParma(count),erp.getRewards().toString());
							}
						}
					}
				}
			}
			count++;
			
		}
		
		ems.setMaxScore(maxScore);
		eventMaxScoreManager.update(ems);
	}
	
	/**
	 * 获取数据库事件信息
	 * @return
	 */
	private List<Event> getDbEventList(){
		List<Event> reList = new ArrayList<Event>();
		List<Event> list = redisClient.hvals(Event.class);
		if(list != null){
			for(Event event : list){
				if(Calendar.getInstance().getTimeInMillis()<event.getEndTime()){
					reList.add(event);
				}
			}
		}
		return reList;
	}
	/**
	 * 获取玩家积分段信息及宝箱信息
	 * @param eventId
	 * @param player
	 * @return
	 */
	private EventScoreInfo getPlayerEventScoreInfo(Event event,Player player){
		EventMaxScore ems = eventMaxScoreManager.getEventMaxScore(event.getEventId());
		EventPir eventPir = EventPirFactory.getInstance().getFactory().get(event.getEventId());
		int score1 = eventPir.getInitScore();
		int rewards1 = 0;
		int rewards2 = 0;
		int rewards3 = 0;
		//根据eventscore表调整积分档位
		int maxScore = ems != null?ems.getMaxScore():score1;
		Map<Integer,EventScorePir> esmap = EventScorePirFactory.getInstance().getFactory();
		for(EventScorePir esp : esmap.values()){
			if(esp.getType()==event.getEventId() && (maxScore>=esp.getScoreMin() && (maxScore <= esp.getScoreMax() || esp.getScoreMax() == -1))){
				score1 = esp.getScorebase();
				rewards1 += esp.getRewardsNum1();
				rewards2 += esp.getRewardsNum2();
				rewards3 += esp.getRewardsNum3();
			}
		}
		//根据玩家信息修正积分要求和宝箱奖励数目
		Map<Integer,EventTrimPir> etmap = EventTrimPirFactory.getInstance().getFactory();
		for(EventTrimPir etp : etmap.values()){
			if(etp.getType()==event.getEventId() && player.getLevel()>=etp.getParaMin() && player.getLevel()<=etp.getParaMax()){
				score1 = (int)(score1*etp.getScoreTimes());
				rewards1 += etp.getRewardsNumAdd1();
				rewards2 += etp.getRewardsNum2();
				rewards3 += etp.getRewardsNum3();
			}
		}
		EventScoreInfo esi = new EventScoreInfo();
		esi.setScore1(score1);
		esi.setScore2((int)(score1*eventPir.getTrim2()));
		esi.setScore3((int)(score1*eventPir.getTrim3()));
		esi.setRewards1(eventPir.getRewards1()+"_"+rewards1);
		esi.setRewards2(eventPir.getRewards2()+"_"+rewards2);
		esi.setRewards3(eventPir.getRewards3()+"_"+rewards3);
		esi.setStatu1(0);
		esi.setStatu2(0);
		esi.setStatu3(0);
		esi.setCurrentScore(0);
		return esi;
	}
	/**
	 * 获取联盟积分段信息及宝箱信息
	 * @param eventId
	 * @param alliance
	 * @return
	 */
	private EventScoreInfo getAllianceEventScoreInfo(Event event,Alliance alliance){
		EventMaxScore ems = eventMaxScoreManager.getEventMaxScore(event.getEventId());
		EventPir eventPir = EventPirFactory.getInstance().getFactory().get(event.getEventId());
		int score1 = eventPir.getInitScore();
		int rewards1 = 0;
		int rewards2 = 0;
		int rewards3 = 0;
		//根据eventscore表调整积分档位
		int maxScore = ems != null?ems.getMaxScore():score1;
		Map<Integer,EventScorePir> esmap = EventScorePirFactory.getInstance().getFactory();
		for(EventScorePir esp : esmap.values()){
			if(esp.getType()==event.getEventId() && (maxScore>=esp.getScoreMin() && (maxScore <= esp.getScoreMax() || esp.getScoreMax() == -1))){
				score1 = esp.getScorebase();
				rewards1 += esp.getRewardsNum1();
				rewards2 += esp.getRewardsNum2();
				rewards3 += esp.getRewardsNum3();
			}
		}
		//根据联盟信息修正积分要求和宝箱奖励数目
		Map<Integer,EventTrimPir> etmap = EventTrimPirFactory.getInstance().getFactory();
		for(EventTrimPir etp : etmap.values()){
			if(etp.getType()==event.getEventId() && alliance.getCurMemeber()>=etp.getParaMin() && alliance.getCurMemeber()<=etp.getParaMax()){
				score1 = (int)(score1*etp.getScoreTimes());
				rewards1 += etp.getRewardsNumAdd1();
				rewards2 += etp.getRewardsNum2();
				rewards3 += etp.getRewardsNum3();
			}
		}
		EventScoreInfo esi = new EventScoreInfo();
		esi.setScore1(score1);
		esi.setScore2((int)(score1*eventPir.getTrim2()));
		esi.setScore3((int)(score1*eventPir.getTrim3()));
		esi.setRewards1(eventPir.getRewards1()+"_"+rewards1);
		esi.setRewards2(eventPir.getRewards2()+"_"+rewards2);
		esi.setRewards3(eventPir.getRewards3()+"_"+rewards3);
		esi.setStatu1(0);
		esi.setStatu2(0);
		esi.setStatu3(0);
		esi.setCurrentScore(0);
		return esi;
	}
	
	/**
	 * 获取玩家事件列表
	 * @param player
	 */
	public void getEventList(Player player) {
		List<EventBean> list = new ArrayList<EventBean>();
		//个人及荣耀事件
		PlayerEvent playerEvent = playerEventManager.getOrCreate(player.getId());
		Map<Long,EventScoreInfo> playerEventScoreInfoMap = playerEvent.getEventScoreInfoMap();
		if(playerEventScoreInfoMap != null){
			for(long eventId : playerEventScoreInfoMap.keySet()){
				Event event = InjectorUtil.getInjector().dbCacheService.get(Event.class, eventId);
				EventScoreInfo eventScoreInfo = playerEventScoreInfoMap.get(eventId);
				EventBean eventBean = EventConverter.converterEventBean(event, eventScoreInfo);
				list.add(eventBean);
			}
		}
		//联盟事件
		if(player.getAllianceId() > 0){
			AllianceEvent allianceEvent = allianceEventManager.getOrCreate(player.getAllianceId());
			Map<Long,EventScoreInfo> allianceEventScoreInfoMap = allianceEvent.getEventScoreInfoMap();
			if(allianceEventScoreInfoMap != null){
				for(long eventId : allianceEventScoreInfoMap.keySet()){
					Event event = InjectorUtil.getInjector().dbCacheService.get(Event.class, eventId);
					EventScoreInfo eventScoreInfo = allianceEventScoreInfoMap.get(eventId);
					EventBean eventBean = EventConverter.converterEventBean(event, eventScoreInfo);
					list.add(eventBean);
				}
			}
		}
		ResEventListMessage resEventListMessage = new ResEventListMessage();
		resEventListMessage.eventList = list;
		player.send(resEventListMessage);
	}
	/**
	 * 获取事件详细信息
	 * @param player
	 * @param eventId
	 */
	public void getEventDetail(Player player,long eventId){
		ResEventDetailMessage resEventDetailMessage = new ResEventDetailMessage();
		Event event = InjectorUtil.getInjector().dbCacheService.get(Event.class, eventId);
		String name = "";
		long currentScore = 0;
		if(event.getEventType()==EventTypeEnum.Group.getValue()){
			name =player.getAllianceId()+"#"+InjectorUtil.getInjector().serverId+"["+(player.getAllianceAbbr()==null?"":player.getAllianceAbbr())+"]"+player.getAllianceName();
			currentScore = allianceEventManager.get(player.getAllianceId()).getEventScoreInfoMap().get(eventId).getCurrentScore();
		}else{
			name = player.getId()+"#"+InjectorUtil.getInjector().serverId+"["+(player.getAllianceAbbr()==null?"":player.getAllianceAbbr())+"]"+player.getName();
			currentScore = playerEventManager.get(player.getId()).getEventScoreInfoMap().get(eventId).getCurrentScore();
		}
		int currentRank = (int)globalRedisClient.zrevrank(DBKey.GLOBAL_RANK_KEY+":"+eventId, name);
		if(-1 == currentRank){
			currentRank = 100;
		}
		EventRankInfoBean currentRankBean = new EventRankInfoBean();
		currentRankBean.rank = currentRank+1;
		currentRankBean.name = name.split("#")[1];
		currentRankBean.score = currentScore;
		resEventDetailMessage.currentRank = currentRankBean;
		resEventDetailMessage.currentFirstTenRank = this.getEventRankInfoBeanList(player, DBKey.GLOBAL_RANK_KEY+":"+eventId);
		player.send(resEventDetailMessage);
	}
	/**
	 * 获取事件历史排行信息
	 * @param player
	 */
	public void getHistoryRank(Player player){
		List<EventRankInfoBeanList> eventRankInfoBeanList= new ArrayList<EventRankInfoBeanList>();
		ResHistoryRankMessage resHistoryRankMessage = new ResHistoryRankMessage();
		List<String> eventIds = globalRedisClient.lrang(DBKey.GLOBAL_RANK_KEY+configResolve.getServerEventId(), 0, 9);
		if(eventIds != null){
			for(int i=0;i<eventIds.size() && i<10 ;i++){
				EventRankInfoBeanList eventRankInfoBean = new EventRankInfoBeanList();
				eventRankInfoBean.eventRankInfoBean = getEventRankInfoBeanList(player,eventIds.get(i));
				eventRankInfoBean.eventId = Integer.parseInt(globalRedisClient.hget(DBKey.GLOBAL_RANK_KEY+configResolve.getServerEventId()+"_TYPE", eventIds.get(i)));
				eventRankInfoBean.itemNo = i;
				eventRankInfoBeanList.add(eventRankInfoBean);
			}
		}
		resHistoryRankMessage.historyRankList = eventRankInfoBeanList;
		player.send(resHistoryRankMessage);
	}
	
	/**
	 * 根据player eventId获取EventRankInfoBean list
	 * @param player
	 * @param eventId
	 * @return
	 */
	private List<EventRankInfoBean> getEventRankInfoBeanList(Player player,String rankKey){
		List<EventRankInfoBean> eventRankInfoBean = new ArrayList<EventRankInfoBean>();
		Set<String> set = globalRedisClient.zrevrange(rankKey, 0, 9);
		int count = 0;
		for(String key :set){
			EventRankInfoBean eri = new EventRankInfoBean();
			eri.rank = ++ count;
			eri.name = key.split("#")[1];
			eri.score = (long)globalRedisClient.zscore(rankKey, key);
			eventRankInfoBean.add(eri);
		}
		return eventRankInfoBean;
	}
	
	/**
	 * 添加排行数据
	 * @param eventType
	 * @param name
	 * @param score
	 * @return
	 */
	public Boolean compareAndRefresh(int eventType ,String name ,int score){
		if(eventType==EventTypeEnum.Group.getValue()){
			RefreshResult rr = smartRankGroup.compareAndRefresh(EventRankable.valueOf(name, score));
			if(null != rr.getUpdate()){
				return true;
			}
			return rr.isRefreshed();
		}else if(eventType==EventTypeEnum.Player.getValue()){
			RefreshResult rr = smartRankPlayer.compareAndRefresh(EventRankable.valueOf(name, score));
			if(null != rr.getUpdate()){
				return true;
			}
			return rr.isRefreshed();
		}else if(eventType==EventTypeEnum.Honour.getValue()){
			RefreshResult rr = smartRankHonour.compareAndRefresh(EventRankable.valueOf(name, score));
			if(null != rr.getUpdate()){
				return true;
			}
			return rr.isRefreshed();
		} 
		return false;
	}
	
	/**
	 * 获取本地排行数据
	 * @param eventType
	 * @param startIndex
	 * @param fetchCount
	 * @return
	 */
	public List<Object> getSmartRank(int eventType,int startIndex,int fetchCount){
		if(eventType==EventTypeEnum.Group.getValue()){
			return smartRankGroup.getRankList(startIndex, fetchCount);
		}else if(eventType==EventTypeEnum.Player.getValue()){
			return smartRankPlayer.getRankList(startIndex, fetchCount);
		}else if(eventType==EventTypeEnum.Honour.getValue()){
			return smartRankHonour.getRankList(startIndex, fetchCount);
		}
		return null;
	}
}
