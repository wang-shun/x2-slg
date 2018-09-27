package com.xgame.logic.server.game.player.entity;

import java.util.List;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.component.ComponentManager;
import com.xgame.logic.server.core.db.cache.entity.AbstractEntity;
import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.gamelog.event.EventBus;
import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.language.Language;
import com.xgame.logic.server.core.net.gate.MultiGateManager;
import com.xgame.logic.server.core.net.gate.SessionManager;
import com.xgame.logic.server.core.net.gate.session.PlayerSession;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.core.utils.sequance.EquipmentSequence;
import com.xgame.logic.server.core.utils.sequance.ItemSequance;
import com.xgame.logic.server.game.alliance.AllianceManager;
import com.xgame.logic.server.game.alliance.PlayerAllianceManager;
import com.xgame.logic.server.game.alliance.enity.Alliance;
import com.xgame.logic.server.game.alliance.enity.PlayerAlliance;
import com.xgame.logic.server.game.armshop.ArmyShopManager;
import com.xgame.logic.server.game.awardcenter.AwardCenterManager;
import com.xgame.logic.server.game.bag.ItemManager;
import com.xgame.logic.server.game.buff.BuffManager;
import com.xgame.logic.server.game.chat.PrivateChatManager;
import com.xgame.logic.server.game.commander.CommanderManager;
import com.xgame.logic.server.game.copy.CopyManager;
import com.xgame.logic.server.game.country.CountryManager;
import com.xgame.logic.server.game.country.structs.BuildInfo;
import com.xgame.logic.server.game.cross.CrossManager;
import com.xgame.logic.server.game.cross.CrossPlayerManager;
import com.xgame.logic.server.game.customweanpon.CustomWeaponManager;
import com.xgame.logic.server.game.email.PlayerMailInfoManager;
import com.xgame.logic.server.game.equipment.EquipmentManager;
import com.xgame.logic.server.game.friend.ReleationShipManager;
import com.xgame.logic.server.game.gameevent.EventManager;
import com.xgame.logic.server.game.modify.ModifyManager;
import com.xgame.logic.server.game.player.LoginManager;
import com.xgame.logic.server.game.player.constant.PlayerState;
import com.xgame.logic.server.game.player.entity.eventmodel.SpeedUpEventObject;
import com.xgame.logic.server.game.playerattribute.AttributeDescrpitionManager;
import com.xgame.logic.server.game.playerattribute.PlayerAttributeManager;
import com.xgame.logic.server.game.playerattribute.entity.eventmodel.AttributeRefreshEventObject;
import com.xgame.logic.server.game.radar.RadarBuildControl;
import com.xgame.logic.server.game.radar.entity.InvestigateData;
import com.xgame.logic.server.game.radar.entity.PlayerRadarBeAttacker;
import com.xgame.logic.server.game.repair.RepairManager;
import com.xgame.logic.server.game.shop.ShopManager;
import com.xgame.logic.server.game.soldier.SoldierManager;
import com.xgame.logic.server.game.task.TaskManager;
import com.xgame.logic.server.game.timertask.TimerTaskManager;
import com.xgame.logic.server.game.timertask.constant.TimerTaskCommand;
import com.xgame.logic.server.game.timertask.entity.job.ITimerTask;
import com.xgame.logic.server.game.timertask.entity.job.TimerTaskHolder;
import com.xgame.logic.server.game.timertask.entity.job.data.TimerTaskData;
import com.xgame.logic.server.game.vip.VipManager;
import com.xgame.logic.server.game.war.WarManager;
import com.xgame.logic.server.game.warehouse.WarehouseManager;
import com.xgame.logic.server.game.world.SpriteManager;
import com.xgame.logic.server.game.world.WorldLogicManager;
import com.xgame.logic.server.game.world.WorldMarchManager;
import com.xgame.logic.server.game.world.entity.SpriteInfo;
import com.xgame.logic.server.game.world.entity.Vector2;
import com.xgame.msglib.XMessage;


/**
 * 玩家信息
 * @author jacky.jiang
 *
 */
@Slf4j
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Player extends AbstractEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1209112299425028523L;
	
	@Getter
	@Accessors(fluent = true)
	private RoleInfo roleInfo;
	@Getter
	@Setter
	private PlayerState state;
	@Getter
	@Setter
	@Autowired
	private CountryManager countryManager;
	@Getter
	@Setter
	@Autowired
	private ItemSequance sequance;
	@Getter
	@Setter
	@Autowired
	private ShopManager shopManager;
	@Getter
	@Setter
	@Autowired
	private VipManager vipManager;
	@Autowired
	private ComponentManager<Player> componentManager;
	@Getter
	@Setter
	@Autowired
	private SessionManager sessionManager;
	@Autowired
	private EquipmentManager equipmentManager;
	@Autowired
	private MultiGateManager muilGateManager;
	@Autowired
	@Getter
	@Setter
	private PrivateChatManager playerChatManager;
	@Autowired
	private TimerTaskManager timerTaskManager;
	@Autowired
	@Getter
	@Setter
	private BuffManager buffManager;
	@Autowired
	@Getter
	@Setter
	private EquipmentSequence equipmentSequence;
	@Getter
	@Setter
	@Autowired
	private SoldierManager soldierManager;
	@Getter
	@Setter
	@Autowired
	private TaskManager taskManager;
	@Getter
	@Setter
	@Autowired
	private ItemManager itemManager;
	@Getter
	@Setter
	@Autowired
	private AwardCenterManager awardCenterManager;
	@Getter
	@Setter
	@Autowired
	private WorldLogicManager worldLogicManager;
	@Autowired
	@Getter
	@Setter
	private WorldMarchManager worldMarchManager;
	@Getter
	@Setter
	@Autowired
	private SpriteManager spriteManager;
	@Autowired
	@Getter
	@Setter
	private ReleationShipManager releationShipManager;
	@Getter
	@Setter
	@Autowired
	private AllianceManager allianceManager;
	@Autowired
	private WarehouseManager warehouseManager;
	@Autowired
	private CrossManager crossManager;
	@Autowired
	private CrossPlayerManager crossPlayerManager;
	@Getter
	@Setter
	@Autowired
	private WarManager warManager;
	@Getter
	@Setter
	@Autowired
	private CommanderManager commanderManager;
	@Getter
	@Setter
	@Autowired
	private LoginManager loginManager;
	@Getter
	@Setter
	@Autowired
	private PlayerMailInfoManager playerMailInfoManager;
	@Getter
	@Setter
	@Autowired
	private PlayerAllianceManager playerAllianceManager;
	@Getter
	@Setter
	@Autowired
	private ArmyShopManager armyShopManager;
	@Getter
	@Setter
	@Autowired
	private CustomWeaponManager customWeaponManager;
	@Getter
	@Setter
	@Autowired
	private RepairManager repairManager;
	@Getter
	@Setter
	@Autowired
	private ModifyManager modifyManager;
	@Getter
	@Setter
	@Autowired
	private EventManager eventManager;
	@Getter
	@Setter
	@Autowired
	private PlayerAttributeManager PlayerAttributeManager;
	@Getter
	@Setter
	@Autowired
	private AttributeDescrpitionManager attributeDescrpitionManager;
//	@Getter
//	@Setter
//	@Autowired
//	private TerroristRecordManager terroristRecordManager;
	@Getter
	@Setter
	@Autowired
	private CopyManager copyManager;
	@Getter
	@Setter
	private long updateTime;
	
	// 发送世界聊天时间
	@Getter
	@Setter
	private long sendWorldTime;
	
	@Getter
	@Setter
	private long sessionId;
	
	@Getter
	@Setter
	private int gateId;
	
	/**
	 * 数据库序列role数据的时候
	 * @param attribute
	 */
	public void init(RoleInfo roleInfo) {
		this.roleInfo = roleInfo;
		
		// 注册组件
		registerComponent();
		
		// 初始化组件
		componentManager.loadComponent(roleInfo);
		
		// 组件第一次登入游戏
		if(roleInfo.getBasics().isNew()) {
//			PlayerAttributeObject attributeObject = new PlayerAttributeObject();
//			attributeObject.addLibrary(AttributeEnum.BUILDING_QUEUE.getId(), Double.valueOf(InitPirFactory.get(InitPirFactory.ID).getValue_8()));
//			attributeAppenderManager.addAttributeObject(AttributeNodeEnum.PLAYER.ordinal(), 0, attributeObject);
			componentManager.firstLoad();
			roleInfo.getBasics().setNew(false);
			InjectorUtil.getInjector().dbCacheService.update(this);
			EventBus.getSelf().fireEvent(new AttributeRefreshEventObject(this));
		}
		
		//修改玩家状态
		updateTime = System.currentTimeMillis();
		
		PlayerSession playerSession = sessionManager.getSessionByPlayerId(roleInfo.getBasics().getRoleId());
		if(playerSession != null) {
			sessionId = playerSession.getSessionID();
			gateId = playerSession.getGateId();
		}

	}
	
	/**
	 * 注册玩家身上的组件
	 */
	public void registerComponent() {
		// 初始化玩家身上组件
		componentManager.register(VipManager.class, vipManager);
		componentManager.register(CountryManager.class, countryManager);
		componentManager.register(CommanderManager.class, commanderManager);
		componentManager.register(EquipmentManager.class, equipmentManager);
		componentManager.register(BuffManager.class, buffManager);
		componentManager.register(PrivateChatManager.class, playerChatManager);
		componentManager.register(ItemManager.class, itemManager);
		componentManager.register(AwardCenterManager.class, awardCenterManager);
		componentManager.register(WarehouseManager.class, warehouseManager);
		componentManager.register(CountryManager.class, countryManager);
		componentManager.start(this);
	}
	
	public int getLevel() {
		if (getCountryManager().getMainBuildControl() == null) {
			return 0;
		}
		return getCountryManager().getMainBuildControl().getDefianlBuild().getLevel();
	}
	
	/**
	 * 指挥官等级
	 * @return
	 */
	public int getCommandLevel() {
		return this.roleInfo.getCommanderData().getLevel();
	}

	/**
	 * 发送 消息
	 * @param message
	 */
	public void send(XMessage msg) {
		if(getState() == PlayerState.OFFLINE) {
			return;
		}
		
		PlayerSession playerSession = sessionManager.getSessionByPlayerId(getId());
		if(playerSession != null) {
			playerSession.send(msg);
		}
	}

	public long getRoleId() {
		return roleInfo.getBasics().getRoleId();
	}
	
	public String getName() {
		return roleInfo.getBasics().getRoleName();
	}
	
	public int getServer() {
		return roleInfo.getBasics().getServerArea();
	}
	
	/**
	 * 离线处理
	 */
	public void leave() {
		if (Objects.isNull(roleInfo()))
			return;
		sessionManager.removePlayerSession(getId());
		long curTime = System.currentTimeMillis();
		roleInfo().getBasics().setLogoutTime(curTime);
		this.setState(PlayerState.OFFLINE);
		roleInfo().getBasics().refreshOnlineTime();
		InjectorUtil.getInjector().dbCacheService.update(this);
		warManager.playerOffline(this);
	}

	public EquipmentManager getEquipmentManager() {
		return equipmentManager;
	}

	public void setEquipmentManager(EquipmentManager equipmentManager) {
		this.equipmentManager = equipmentManager;
	}

	public PlayerSession getPlayerSession() {
		PlayerSession playerSession = sessionManager.getSessionByPlayerId(getId());
		if(playerSession != null) {
			return playerSession;
		}
		return null;
	}

	@Override
	public String toString() {
		return String.format("key:%s-roleName:%s", roleInfo.getBasics().getRoleKey(), roleInfo.getBasics().getRoleName());
	}

	@Override
	public Long getId() {
		return roleInfo.getBasics().getRoleId();
	}

	@Override
	public void setId(Long k) {
		this.roleInfo.getBasics().setRoleId(k);
	}

	@Override
	public void onLoad() {
		this.init(this.roleInfo);
	}

	
	public static final int CALL_TIME_OUT = 3000;
	
	/**
	 * 获取玩家位置
	 * @return
	 */
	public Vector2 getLocation() {
		SpriteInfo spriteInfo = InjectorUtil.getInjector().dbCacheService.get(SpriteInfo.class, roleInfo.getBasics().getSpriteId());
		if(spriteInfo != null) {
			return spriteInfo.getVector2();
		}
		return null;
	}
	
	/**
	 * 获取世界坐标点
	 * @return
	 */
	public int getWorldPoint() {
		SpriteInfo spriteInfo = InjectorUtil.getInjector().dbCacheService.get(SpriteInfo.class, roleInfo.getBasics().getSpriteId());
		if(spriteInfo != null) {
			return spriteInfo.getIndex();
		}
		return 0;
	}
	
	/**
	 * 异步访问
	 * @param runnable
	 */
	public void async(Runnable runnable) {
		InjectorUtil.getInjector().processor.getExecutor(this.getRoleId()).execute(runnable);
	}
	
	/**
	 * 获取联盟id
	 * @return
	 */
	public long getAllianceId() {
		PlayerAlliance playerAlliance = InjectorUtil.getInjector().dbCacheService.get(PlayerAlliance.class, this.getId());
		if(playerAlliance != null) {
			return playerAlliance.getAllianceId();
		}
		return 0;
	}
	
	/**
	 * 获取玩家联盟名称
	 * @param playerId
	 * @return
	 */
	public String getAllianceName() {
		long allianceId = this.getAllianceId();
		if(allianceId > 0) {
			return allianceManager.getAllianceNameByAllianceId(allianceId);
		}
		return null;
	}
	
	/**
	 * 联盟简称
	 * @param playerId
	 * @return
	 */
	public String getAllianceAbbr() {
		long allianceId = this.getAllianceId();
		if(allianceId > 0) {
			Alliance alliance = InjectorUtil.getInjector().dbCacheService.get(Alliance.class, allianceId);
			if(alliance != null) {
				return alliance.getAbbr();
			}
		}
		return null;
	}
	
	/**
	 * 联盟简称
	 * @param playerId
	 * @return
	 */
	public String getAllianceImg() {
		long allianceId = this.getAllianceId();
		if(allianceId > 0) {
			Alliance alliance = InjectorUtil.getInjector().dbCacheService.get(Alliance.class, allianceId);
			if(alliance != null) {
				return alliance.getIcon();
			}
		}
		return null;
	}
	
	public String getHeadImg() {
		return String.valueOf(this.roleInfo.getCommanderData().getStyle());
	}
	
	public SpriteInfo getSprite() {
		SpriteInfo spriteInfo = InjectorUtil.getInjector().dbCacheService.get(SpriteInfo.class, roleInfo.getBasics().getSpriteId());
		return spriteInfo;
	}
	
	/**
	 * 
	 * 改变玩家定时任务时间
	 * 
	 */
	public void changeTaskTime(long taskId, int time) {
		TimerTaskData timerTaskData = this.getTimerTaskManager().getTimerTaskData(taskId);
		if (timerTaskData == null) {
			Language.ERRORCODE.send(this, 74010);
			log.error(" not find TimerTaskData object.");
			return;
		}
		
		ITimerTask<?> task = TimerTaskHolder.getTimerTask(timerTaskData.getQueueId());
		if (task != null) {
			task.speedUp(this, timerTaskData, time);
			EventBus.getSelf().fireEvent(new SpeedUpEventObject(this, EventTypeConst.EVENT_SPEED_UP, TimerTaskCommand.getTimerTaskCommand(timerTaskData.getQueueId()), timerTaskData));
		}
	}
	
	/**
	 * 清除雷达预警
	 * */
	public void clearRadarWarning(long marchId) {
		RadarBuildControl radarBuildControl = getCountryManager().getRadarBuildControl();
		if (radarBuildControl != null) {
			radarBuildControl.clearWarning(this , marchId);
		}
	}
	
	/**
	 * 
	 * 更新被攻击者雷达数据
	 * */
	
	public void refreshRadarInfo(PlayerRadarBeAttacker attacker) {
		RadarBuildControl radarBuildControl = this.getCountryManager().getRadarBuildControl();
		if(radarBuildControl != null) {
			radarBuildControl.refresh(this , attacker);
		}	
	}
	
	public void refreshObstructBuild(){
		this.getCountryManager().refreshObstructBuild();
	}
 
	public void addInvestigateData(InvestigateData investigateData) {
		if (investigateData != null) {
			RadarBuildControl radarBuildControl = this.getCountryManager().getRadarBuildControl();
			if (radarBuildControl != null) {
				radarBuildControl.addInvestigateData(this, investigateData);
			}
		}
	}
	
	public List<BuildInfo> getAllCountryBuildList(){
		return this.getCountryManager().getAllCountryBuildList();
	}

	public WarehouseManager getWarehouseManager() {
		return warehouseManager;
	}

	public void setWarehouseManager(WarehouseManager warehouseManager) {
		this.warehouseManager = warehouseManager;
	}

	public TimerTaskManager getTimerTaskManager() {
		return timerTaskManager;
	}

	public void setTimerTaskManager(TimerTaskManager timerTaskManager) {
		this.timerTaskManager = timerTaskManager;
	}

	@Override
	public JBaseData toJBaseData() {
		return roleInfo.toJBaseData();
	}

	@Override
	public void fromJBaseData(JBaseData jBaseData) {
		RoleInfo roleInfo = new RoleInfo();
		roleInfo.fromJBaseData(jBaseData);
		this.roleInfo = roleInfo;
	}
}
