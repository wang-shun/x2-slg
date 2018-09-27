package com.xgame.logic.server.game.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.xgame.logic.server.core.db.cache.cache.LRUCacheProxy;
import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.gamelog.event.EventBus;
import com.xgame.logic.server.core.net.gate.SessionManager;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.core.utils.JsonUtil;
import com.xgame.logic.server.core.utils.scheduler.ScheduleTasks;
import com.xgame.logic.server.core.utils.scheduler.Scheduled;
import com.xgame.logic.server.game.alliance.PlayerAllianceManager;
import com.xgame.logic.server.game.alliance.RecommendPlayerManager;
import com.xgame.logic.server.game.alliance.enity.Alliance;
import com.xgame.logic.server.game.alliance.enity.PlayerAlliance;
import com.xgame.logic.server.game.cross.CrossPlayerManager;
import com.xgame.logic.server.game.cross.converter.SimplePlayerConverter;
import com.xgame.logic.server.game.cross.entity.SimpleRoleInfo;
import com.xgame.logic.server.game.player.constant.PlayerState;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.player.entity.RoleInfo;


/**
 * 玩家管理器
 * @author jacky.jiang
 *
 */
@Slf4j
@Component
public class PlayerManager extends LRUCacheProxy<Player> {

	public static final int PLAYER_CACHE_SIZE = 6000;
	
	@Autowired
	private ObjectFactory<Player> playerFactory;
	@Autowired
	private SessionManager sessionManager;
	@Autowired
	private CrossPlayerManager crossPlayerManager;
	@Autowired
	private RecommendPlayerManager recommendPlayerManager;
	@Autowired
	private PlayerAllianceManager playerAllianceManager;
	@Autowired
	private EventBus gameLog;
	
	/**
	 * 玩家名称-玩家id缓存
	 * <ul>
	 * 	<li>玩家名称</li>
	 *  <li>玩家id</li>
	 * </ul>
	 */
	private Map<String, Long> playerNameIdMap = new ConcurrentHashMap<String, Long>();
	
	/**
	 * 玩家id-玩家名称缓存
	 */
	private Map<Long, String> playerIdNameMap = new ConcurrentHashMap<Long, String>();
	
	/**
	 * 玩家id-帐号名缓存
	 * <ul>
	 * 	<li>userName</li>
	 *  <li>playerId</li>
	 * </ul>
	 */
	private Map<String, Long> userNameIdMaps = new ConcurrentHashMap<String, Long>();
	
	public boolean init() {
		super.init(PLAYER_CACHE_SIZE);
		initPlayer();
		return true;
	}

	// 将某个角色踢下线
	public synchronized void offlineRole(long roleId) {
		Player player = this.getPlayer(roleId);
		if (Objects.nonNull(player)) {
			player.roleInfo().getBasics().setLogoutTime(System.currentTimeMillis());
			player.setState(PlayerState.OFFLINE);
			player.roleInfo().getBasics().refreshOnlineTime();
			InjectorUtil.getInjector().dbCacheService.update(player);
		} else {
			log.error("logicserver offlineRole error");
		}
	}

	public void initPlayer() {
		playerIdNameMap.clear();
		playerNameIdMap.clear();
		userNameIdMaps.clear();
		
		// 缓存玩家数据
		loadPlayers();
	}
	
	/**
	 * 起服加载用户数据
	 * @return
	 */
	public void loadPlayers() {
		List<RoleInfo> playerList= InjectorUtil.getInjector().redisClient.hvals(RoleInfo.class, Player.class.getSimpleName());
		if(playerList != null && !playerList.isEmpty()) {
			for(RoleInfo roleInfo : playerList) {
				userNameIdMaps.put(roleInfo.getBasics().getUserName(), roleInfo.getBasics().getRoleId());
				playerNameIdMap.put(roleInfo.getBasics().getRoleName(), roleInfo.getBasics().getRoleId());
				playerIdNameMap.put(roleInfo.getBasics().getRoleId(), roleInfo.getBasics().getRoleName());
				
				// TODO 同步global与 本地数据库玩家信息
				SimpleRoleInfo simpleRoleInfo = InjectorUtil.getInjector().globalRedisClient.hget(SimpleRoleInfo.class, roleInfo.getBasics().getRoleId());
				
				PlayerAlliance playerAlliance = InjectorUtil.getInjector().redisClient.hget(PlayerAlliance.class, roleInfo.getBasics().getRoleId());
				if(playerAlliance == null || playerAlliance.getAllianceId() <= 0){
					//只将未加入军团的人员加入推荐
					recommendPlayerManager.refreshRank(roleInfo.getBasics().getRoleId(), roleInfo.getBasics().getLoginTime(), roleInfo.getVipInfo().getVipLevel(), roleInfo.getCurrency().getPower(), roleInfo.getBasics().getLevel());
				}
				//TODO 世界政府头衔
				if(simpleRoleInfo != null) {
					if(playerAlliance != null) {
						Alliance alliance = InjectorUtil.getInjector().redisClient.hget(Alliance.class, playerAlliance.getAllianceId());
						String[] allianceTitle = playerAllianceManager.getAllianceTitle(playerAlliance.getPlayerId());
						boolean update = false;
						if(alliance != null){
							update = simpleRoleInfo.compareRoleInfo(roleInfo, alliance.getAllianceId(),alliance.getAllianceName(),alliance.getAbbr(),allianceTitle[0],allianceTitle[1],"");
						}else{
							update = simpleRoleInfo.compareRoleInfo(roleInfo, 0,"","","","","");
						}
						if(update) {
							simpleRoleInfo = SimplePlayerConverter.converterSimpleRoleInfo(roleInfo, alliance,allianceTitle[0],allianceTitle[1],"");
							crossPlayerManager.saveSimpleRoleInfo(simpleRoleInfo);
						}
					} else {
						boolean update = simpleRoleInfo.compareRoleInfo(roleInfo, 0,"","","","","");
						if(update) {
							simpleRoleInfo = SimplePlayerConverter.converterSimpleRoleInfo(roleInfo, null,"","","");
							crossPlayerManager.saveSimpleRoleInfo(simpleRoleInfo);
						}
					}
				} else {
					if(playerAlliance != null) {
						Alliance alliance = InjectorUtil.getInjector().redisClient.hget(Alliance.class, playerAlliance.getAllianceId());
						String[] allianceTitle = playerAllianceManager.getAllianceTitle(playerAlliance.getPlayerId());
						simpleRoleInfo = SimplePlayerConverter.converterSimpleRoleInfo(roleInfo, alliance,allianceTitle[0],allianceTitle[1],"");
						crossPlayerManager.saveSimpleRoleInfo(simpleRoleInfo);
					} else {
						simpleRoleInfo = SimplePlayerConverter.converterSimpleRoleInfo(roleInfo, null,"","","");
						crossPlayerManager.saveSimpleRoleInfo(simpleRoleInfo);
					}
				}
			}
		}
	}
	
	/**
	 * 获取玩家根据roleId
	 * @param roleId
	 * @return
	 */
	public Player getPlayer(long roleId) {
		Player player = InjectorUtil.getInjector().dbCacheService.get(Player.class, roleId);
		return player;
	}
	
	/**
	 * 初始化player
	 * @param roleInfo
	 * @return
	 */
	public Player createPlayer(RoleInfo roleInfo) {
		Player player = playerFactory.getObject();
		player.init(roleInfo);
		player.setState(PlayerState.OFFLINE);
		return player;
	}

	/**
	 * 判断用户名是否存在
	 * @param userName
	 * @return true 代表用户存在, false
	 */
	public boolean checkUserNameExist(String userName) {
		Long playerId = userNameIdMaps.get(userName);
		if(playerId != null) {
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param playerName
	 * @return
	 */
	public boolean checkPlayerNameExist(String playerName) {
		Long playerId = playerNameIdMap.get(playerName);
		if(playerId != null) {
			return true;
		}
		return false;
	}
	
	/**
	 * 获取在线玩家信息
	 * @return
	 */
	public List<Player> getOnlinePlayers() {
		List<Player> list = new ArrayList<>();
		List<Long> playerIds = sessionManager.getOnlinePlayerIds();
		if(playerIds != null && !playerIds.isEmpty()) {
			for(Long playerId : playerIds) {
				Player player = InjectorUtil.getInjector().dbCacheService.get(Player.class, playerId);
				list.add(player);
			}
		}
		return list;
	}
	
	/**
	 * 获取在线玩家
	 * @return
	 */
	public List<Long> getOnlinePlayerIds() {
		return Lists.newArrayList(sessionManager.getOnlinePlayerIds());
	}
	
	/**
	 * 根据角色名获取玩家信息
	 * @param userName
	 * @return
	 */
	public Player getPlayerByUserName(String userName) {
		Long playerId = userNameIdMaps.get(userName);
		if(playerId != null) {
			return getPlayer(playerId);
		}
		return null;
	}
	
	/**
	 * 添加用户名
	 * @param userName
	 * @param id
	 */
	public void addUserNameMap(String userName, long id) {
		userNameIdMaps.put(userName, id);
	}
	
	public void addPlayerNameMap(String playerName, long id) {
		playerNameIdMap.put(playerName, id);
	}
	
	public void addPlayerIdNameMap(long id, String playerName) {
		playerIdNameMap.put(id, playerName);
	}
	
	public Long getPlayerIdByPlayerName(String playerName) {
		return playerNameIdMap.get(playerName);
	}
	
	public String getPlayerNameByPlayerId(long playerId) {
		return playerIdNameMap.get(playerId);
	}
	
	/**
	 * 获取玩家匹配信息
	 * @param playerName
	 * @return
	 */
	public List<Long> getPlayerNamePattern(String playerName) {
		List<Long> playerIds = new ArrayList<Long>();
		for(Entry<String, Long> patternPlayerName : playerNameIdMap.entrySet()) {
			if(patternPlayerName.getKey().contains(playerName)) {
				playerIds.add(patternPlayerName.getValue());
			}
		}
		return playerIds;
	}
	
	public Collection<Long> getServerPlayerIds() {
		return userNameIdMaps.values();
	}

	@Override
	public Class<?> getProxyClass() {
		return Player.class;
	}

	@Override
	public Player deserialize(String value) {
		JBaseData roleInfoJson = JsonUtil.fromJSON(value, JBaseData.class);
		Player player = playerFactory.getObject();
		RoleInfo roleInfo = new RoleInfo();
		roleInfo.fromJBaseData(roleInfoJson);
		player.init(roleInfo);
		return player;
	}

	@Override
	public String serialize(Player t) {
		RoleInfo roleInfo = t.roleInfo();
		JBaseData jBaseData = roleInfo.toJBaseData();
		return JsonUtil.toJSON(jBaseData);
	}

	@Override
	public void update(Player t) {
		t.setUpdateTime(System.currentTimeMillis());
		super.update(t);
	}
	
	public Map<String, Long> getPlayerNameIdMap() {
		return this.playerNameIdMap;
	}
	
	public Map<Long, String> getPlayerIdNameMap() {
		return playerIdNameMap;
	}

	public void setPlayerIdNameMap(Map<Long, String> playerIdNameMap) {
		this.playerIdNameMap = playerIdNameMap;
	}

	@Scheduled(name = "家园数据刷新", value = ScheduleTasks.COUNTRY_OBSTACLE)
	public void countryRefresh() {
		Iterator<Player> iterator = InjectorUtil.getInjector().playerManager.getOnlinePlayers().iterator();
		while (iterator.hasNext()) {
			Player player = (Player) iterator.next();
			if(player != null) {
				// 恢复体力, 每6分钟加一次体力
				boolean flag = false;
				if(player.roleInfo().getCommanderData().getAddEnergyTime() + 6 * 60 * 1000 < System.currentTimeMillis()) {
					if(player.getCommanderManager().restoreEnergy()) {
						flag = true;
					}
				}
				
				// 每30秒结算一次资源
				flag = player.getCountryManager().getMineBuildControl().dealMineCarResouce(player, false);
				
				// 更新玩家信息
				if(flag) {
					InjectorUtil.getInjector().dbCacheService.update(player);
				}
				
				//检测障碍物是否刷新
				player.refreshObstructBuild();
			}
		}
	}
	
}