package com.xgame.logic.server.core.utils;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.message.MessageSystem;
import com.xgame.config.ConfigSystem;
import com.xgame.framework.network.server.CommandProcessor;
import com.xgame.framework.schedule.manager.ScheduleSystem;
import com.xgame.logic.server.core.db.cache.DbCacheService;
import com.xgame.logic.server.core.db.redis.GlobalRedisClient;
import com.xgame.logic.server.core.db.redis.RedisClient;
import com.xgame.logic.server.core.net.gate.MultiGateManager;
import com.xgame.logic.server.core.net.gate.SessionManager;
import com.xgame.logic.server.core.sysconfig.RedisServerConfig;
import com.xgame.logic.server.core.system.GlobalManager;
import com.xgame.logic.server.core.utils.sequance.IDSequance;
import com.xgame.logic.server.core.utils.sequance.MarchSequance;
import com.xgame.logic.server.core.utils.sequance.TimerTaskSequance;
import com.xgame.logic.server.game.alliance.AllianceBattleInfoManager;
import com.xgame.logic.server.game.alliance.AllianceBattleTeamManager;
import com.xgame.logic.server.game.alliance.AllianceManager;
import com.xgame.logic.server.game.awardcenter.AwardCenterManager;
import com.xgame.logic.server.game.chat.ChatRoomManager;
import com.xgame.logic.server.game.chat.WorldChatManager;
import com.xgame.logic.server.game.cross.CrossPlayerManager;
import com.xgame.logic.server.game.email.EmailManager;
import com.xgame.logic.server.game.email.UserEmailManager;
import com.xgame.logic.server.game.gameevent.EventManager;
import com.xgame.logic.server.game.notice.NoticeManager;
import com.xgame.logic.server.game.player.LoginManager;
import com.xgame.logic.server.game.player.PlayerManager;
import com.xgame.logic.server.game.playerattribute.fightpower.FightPowerSystem;
import com.xgame.logic.server.game.shop.ShopManager;
import com.xgame.logic.server.game.timertask.SystemTimeManager;
import com.xgame.logic.server.game.timertask.TimerTaskManager;
import com.xgame.logic.server.game.war.WarManager;
import com.xgame.logic.server.game.world.ServerGroupManager;
import com.xgame.logic.server.game.world.SpriteManager;
import com.xgame.logic.server.game.world.WorldMarchManager;
import com.xgame.logic.server.game.world.entity.MapConfigLoader;
import com.xgame.logic.server.gm.GMProxySystem;
import com.xgame.logic.server.gm.IGMProxySystem;



/**
 *注入工具 方便快速获取单列
 *2016-8-08  19:19:28
 *@author ye.yuan
 *
 */
@SuppressWarnings("unchecked")
@Component
public final class InjectorUtil implements ApplicationContextAware{
	
	private static InjectorUtil injector;
	
	private ApplicationContext applicationContext;

	@Autowired
	public CommandProcessor processor;
	@Autowired
	public ScheduleSystem scheduleSystem;
	@Autowired
	public ShopManager shopManager;
	@Autowired
	public AwardCenterManager awardCenterManger;
	@Autowired
	public LoginManager loginManager;
	@Autowired
	public RedisClient redisClient;
	@Autowired
	public GlobalRedisClient globalRedisClient;
	@Autowired
	public PlayerManager playerManager;
	@Autowired
	public WarManager battleManager;
	@Autowired
	public SessionManager sessionManager;
	@Autowired
	public MapConfigLoader configLoader;
	@Autowired
	public TimerTaskManager timerTaskManager;
	@Autowired
	public SystemTimeManager systemTimeManager;
	@Autowired
	public TimerTaskSequance timerTaskSequance;
	@Autowired
	public IDSequance idSequance;
	@Autowired
	public DbCacheService dbCacheService;
	@Autowired
	public WorldChatManager chatManager;
	@Autowired
	public MultiGateManager multiGateManager;
	@Autowired
	public ChatRoomManager chatRoomManager;
	@Autowired
	public FightPowerSystem documentSystem;
	@Autowired
	public WorldMarchManager worldMarchManager;
	@Autowired
	public SpriteManager spriteManager;
	@Autowired
	public GlobalManager globalManager;
	@Autowired
	public RedisServerConfig redisServerConfig;
	@Autowired
	public CrossPlayerManager crossPlayerManager;
	@Autowired
	public AllianceBattleTeamManager allianceBattleTeamManager;
	@Autowired
	public AllianceManager allianceManager;
	@Autowired
	public ServerGroupManager serverGroupManager;
	@Autowired
	public EventManager eventManager;
	@Autowired
	public NoticeManager noticeManager;
	
	@Value("${xgame.logic.server.id}")
	public int serverId;
	@Value("${xgame.world.server.config.path}")
	public String path;
	@Value("${xgame.config.path}")
	public String configPath;
	@Value("${game.global.timeout}")
	public int redisTimeout;
	@Value("${xgame.gate.server}")
	public String gateInfo;
	
	public IGMProxySystem gmProxySystem;
	
	@Autowired
	public ConfigSystem configSystem;
	@Autowired
	public MessageSystem messageSystem;
	@Autowired
	public MarchSequance marchSequance;
	@Autowired
	public AllianceBattleInfoManager allianceBattleInfoManager;
	@Autowired
	public EmailManager emailManager;
	@Autowired
	public UserEmailManager userEmailManager;
	
	@PostConstruct
	public void init() {
		injector=this;
		injector.gmProxySystem = GMProxySystem.create();
	}
	
	public <T> T getBean(String name){
		return (T)applicationContext.getBean(name);
	}
	
	public <T> T getBean(Class<?> className){
		return (T)applicationContext.getBean(className);
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext=applicationContext;
	}


	public static InjectorUtil getInjector() {
		return injector;
	}

	public static void setInjector(InjectorUtil injector) {
		InjectorUtil.injector = injector;
	}
	
	public ApplicationContext getContext() {
		return applicationContext;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
}
