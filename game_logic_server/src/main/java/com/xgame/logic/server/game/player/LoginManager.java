package com.xgame.logic.server.game.player;

import java.util.concurrent.ConcurrentHashMap;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xgame.config.global.GlobalPirFactory;
import com.xgame.framework.network.server.CommandProcessor;
import com.xgame.gameconst.DBKey;
import com.xgame.logic.server.core.db.redis.GlobalRedisClient;
import com.xgame.logic.server.core.gamelog.event.EventBus;
import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.net.gate.SessionManager;
import com.xgame.logic.server.core.net.gate.session.PlayerSession;
import com.xgame.logic.server.core.utils.CurrencyUtil;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.core.utils.sequance.IDFactrorySequencer;
import com.xgame.logic.server.game.alliance.PlayerAllianceManager;
import com.xgame.logic.server.game.alliance.enity.Alliance;
import com.xgame.logic.server.game.bag.converter.ItemConverter;
import com.xgame.logic.server.game.constant.CurrencyEnum;
import com.xgame.logic.server.game.copy.CopyManager;
import com.xgame.logic.server.game.country.bean.Vector2Bean;
import com.xgame.logic.server.game.country.entity.BaseCountry;
import com.xgame.logic.server.game.country.structs.build.BuildFactory;
import com.xgame.logic.server.game.country.structs.build.camp.data.SoldierData;
import com.xgame.logic.server.game.cross.CrossPlayerManager;
import com.xgame.logic.server.game.cross.converter.SimplePlayerConverter;
import com.xgame.logic.server.game.cross.entity.SimpleRoleInfo;
import com.xgame.logic.server.game.equipment.entity.EquipmentDataManager;
import com.xgame.logic.server.game.player.constant.PlayerState;
import com.xgame.logic.server.game.player.converter.PlayerConvterver;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.player.entity.PlayerBag;
import com.xgame.logic.server.game.player.entity.RoleBasics;
import com.xgame.logic.server.game.player.entity.RoleCurrency;
import com.xgame.logic.server.game.player.entity.RoleInfo;
import com.xgame.logic.server.game.player.entity.eventmodel.CreateRoleEventObject;
import com.xgame.logic.server.game.player.entity.eventmodel.LoginEventObject;
import com.xgame.logic.server.game.playerattribute.PlayerAttributeManager;
import com.xgame.logic.server.game.playerattribute.fightpower.FightPowerKit;
import com.xgame.logic.server.game.task.TaskManager;
import com.xgame.logic.server.game.world.WorldLogicManager;

/**
 *
 *2016-8-29  19:29:06
 *@author ye.yuan
 *
 */
@Slf4j
@Component
public class LoginManager {
	
	@Autowired
	private SessionManager sessionManager;
	@Autowired
	private PlayerManager playerManager;
	@Autowired
	private CommandProcessor commandProcessor;
	@Autowired
	private WorldLogicManager worldLogicManager;
	@Autowired
	private CrossPlayerManager simpleRoleInfoManager;
	@Autowired
	private GlobalRedisClient globalRedisClient;
	@Autowired
	private IDFactrorySequencer idSequencer;
	@Autowired
	private TaskManager taskManager;
	@Autowired
	private CopyManager copyManager;
	@Autowired
	private PlayerAllianceManager playerAllianceManager;
	
	/**
	 *  登录
	 * @param session
	 * @param userName
	 */
	@SuppressWarnings("unchecked")
	public void reqLogin(PlayerSession session,String userName) {

		if(session == null) {
			log.error("玩家session为空:");
			return;
		}
		
		// 顶号处理
		RoleInfo roleInfo = null;
		Player player = null;
		synchronized (this) {
			player = playerManager.getPlayerByUserName(userName);
			if(player != null) {
				if(session != null && player.getSessionId() > 0 && player.getSessionId() != session.getSessionID()) {
					sessionManager.sendCloseSession(player.getSessionId(), player.getGateId());
				}
			}
			
			boolean createRole = false;
			String roleName = "";
			if (player == null) {
				// 生成角色id(头四位服务器id + 自增id)
				long roleId = idSequencer.createEnityID(DBKey.ROLE_ID_KEY);
				if(roleId <= 0) {
					log.error("玩家自增id越界...");
					return;
				}
				
				//自定义roleName (占地风暴+时间序列)
				roleName = StringUtils.join("fight:", System.currentTimeMillis() / 1000);
				roleInfo = createRole(roleId, userName, roleName);
				
				// 创建玩家
				player = playerManager.createPlayer(roleInfo);
				
				// 注册到地图当中
				boolean result = worldLogicManager.registerWorld(player);
				if(!result) {
					log.error("注册世界地图失败...");
					return;
				}
				
				// 添加到缓存 
				playerManager.addUserNameMap(roleInfo.getBasics().getUserName(), roleInfo.getBasics().getRoleId());
				playerManager.addPlayerNameMap(roleInfo.getBasics().getRoleName(), roleInfo.getBasics().getRoleId());
				playerManager.addPlayerIdNameMap(roleInfo.getBasics().getRoleId(), roleInfo.getBasics().getRoleName());
			
				// 注册世界地图
				InjectorUtil.getInjector().dbCacheService.create(player);
				
				//初始化任务
				taskManager.initTask(player);
				
				//初始化主线副本
				copyManager.initCopy(player);
				
				// 刷新公共服信息
				registerGlobalServer(player);
				
				//
				initDesignMap(player);
				createRole = true;
			}
			
			// 设置玩家数据
			session.setPlayerId(player.getRoleId());
			player.setSessionId(session.getSessionID());
			player.setGateId(session.getGateId());
			player.setState(PlayerState.LOGIN);	
			sessionManager.addPlayerSession(player.getId(), session.getSessionID());
			
			player.roleInfo().getBasics().setLoginTime(System.currentTimeMillis());
			player.roleInfo().getBasics().setOnlineUpdateTime(System.currentTimeMillis());
			InjectorUtil.getInjector().dbCacheService.update(player);
		
			// 创建角色日志
			EventBus.getSelf().fireEvent(new LoginEventObject(player , EventTypeConst.EVENT_PLAYER_LOGIN ,  System.currentTimeMillis()));
			if(createRole) {
				Vector2Bean vector2Bean = new Vector2Bean();
				vector2Bean.x = player.getLocation().getX();
				vector2Bean.y = player.getLocation().getY();
				EventBus.getSelf().fireEvent(new CreateRoleEventObject(player, EventTypeConst.EVENT_CREATE_ROLE, roleName, vector2Bean));
			}

			log.debug("login query player data {}, playerId: {}", userName, player.getId());
		}
		
		sendClientMessage(player);
	}
	
	/**
	 * 发送数据给客户端
	 * @param player
	 */
	private void sendClientMessage(Player player) {
		commandProcessor.runSync(new Runnable() {
			@Override
			public void run() {
				clientLoginOver(player);
			}
		});
	}
    
	/**
	 * 创建角色 
	 * @param roleId
	 * @param userId
	 * @param userName
	 * @param roleName
	 * @return
	 */
	private RoleInfo createRole(long roleId, String userName, String roleName) {
		long curTime = System.currentTimeMillis();
		RoleInfo roleInfo = new RoleInfo();
		
		RoleBasics roleBasics = new RoleBasics();
		roleBasics.setRoleId(roleId);
		roleBasics.setUserName(userName);
		roleBasics.setRoleName(roleName);
		roleBasics.setCreateTime(curTime);
		roleBasics.setServerArea(InjectorUtil.getInjector().serverId);
		roleInfo.setBasics(roleBasics);
		
		// 初始化资源
		RoleCurrency roleCurrency = new RoleCurrency();
//		InitPir initPir = InitPirFactory.get(1);
		roleCurrency.setMoney(GlobalPirFactory.getInstance().getPlayerInitValue(CurrencyEnum.GLOD.ordinal()));
		roleCurrency.setRare(GlobalPirFactory.getInstance().getPlayerInitValue(CurrencyEnum.RARE.ordinal()));
		roleCurrency.setSteel(GlobalPirFactory.getInstance().getPlayerInitValue(CurrencyEnum.STEEL.ordinal()));
		roleCurrency.setOil(GlobalPirFactory.getInstance().getPlayerInitValue(CurrencyEnum.OIL.ordinal()));
		roleCurrency.setDiamond(GlobalPirFactory.getInstance().getPlayerInitValue(CurrencyEnum.DIAMOND.ordinal()));
		roleInfo.setCurrency(roleCurrency);
		
		// 玩家初始体力值
		roleCurrency.setVitality(GlobalPirFactory.getInstance().getPlayerInitValue(CurrencyEnum.POWER.ordinal()));
		
		roleInfo.setBaseCountry(new BaseCountry());
		roleInfo.setSoldierData(new SoldierData());
		roleInfo.setPlayerBag(new PlayerBag());
		roleInfo.setTimerTaskMap(new ConcurrentHashMap<>());
		roleInfo.setEquipmentDataManager(new EquipmentDataManager());
		return roleInfo;
	}
	
	/**
	 * 刷新公共服信息
	 * @param player
	 */
	public void registerGlobalServer(Player player) {
		Alliance alliance = player.getAllianceManager().get(player.getAllianceId());
		String[] allianceTitle = playerAllianceManager.getAllianceTitle(player.getId());

		//TODO 世界政府头衔
		SimpleRoleInfo simpleRoleInfo = SimplePlayerConverter.converterSimpleRoleInfo(player.roleInfo(), alliance,allianceTitle[0],allianceTitle[1],"");
		simpleRoleInfoManager.saveSimpleRoleInfo(simpleRoleInfo);
	}
	
	
	/**
	 * 登陆后 玩家各个数据初始化 和发送给客户端 操作
	 */
	public void clientLoginOver(Player player) {
		
		// 同步定时器任务 要改
		player.getTimerTaskManager().sendAllTimerTask(player);
		
		// 推送角色基本信息
		player.send(PlayerConvterver.converterPlayerProto(player));
		
		//推送vip信息
		//player.getVipManager().send();
		
		// 发送背包数据
		player.send(ItemConverter.getMsgPlayerBag(player));

		// 推送商城信息
		player.getShopManager().topicShopConfig(player);

		//指挥官 初始化
		player.getCommanderManager().loginLoad();
		
		// 发图纸
		player.getCustomWeaponManager().send(player);
		
		// 发送兵
		player.getSoldierManager().loginSend(player);
		// 推送活跃值
		// taskManager.topicActiveInfo();

		// manager 本身初始化
		player.getCountryManager().loginLoad();
		
		// 家园建筑信息
		player.getCountryManager().send();
		
		//领奖中心
		player.getAwardCenterManager().send();
		// 联盟登录
		player.getAllianceManager().login(player);
		// 属性
//		player.getAttributeAppenderManager().loginLoad();
		PlayerAttributeManager.get().pushAttribute(player);
		// 好友关系
		player.getReleationShipManager().send(player);
		// 登录初始化聊天
		player.getPlayerChatManager().loginLoad();
		
		CurrencyUtil.send(player);
		
		FightPowerKit.sendAllFightPower(player);
		
		player.getWarehouseManager().send();
		// 登录发送行军信息
		worldLogicManager.send(player);
		// buff登录
		player.getBuffManager().loginLoad();
		//发送事件信息
		player.getEventManager().loginLoad(player);
	}
	
	/**
	 * 配置数据的初始化
	 * @param player
	 */
	private void initDesignMap(Player player) {
		player.getArmyShopManager().assembly(player, BuildFactory.SUV.getTid());
		player.getArmyShopManager().assembly(player, BuildFactory.TANK.getTid());
		player.getArmyShopManager().assembly(player, BuildFactory.PLANE.getTid());
	}
}
