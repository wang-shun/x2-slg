package com.xgame.logic.server.game.war;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xgame.config.ConfigSystem;
import com.xgame.config.building.BuildingPir;
import com.xgame.config.building.BuildingPirFactory;
import com.xgame.framework.lifecycle.Startup;
import com.xgame.framework.lifecycle.StartupOrder;
import com.xgame.logic.server.core.db.cache.DbCacheService;
import com.xgame.logic.server.core.db.cache.executor.NamedThreadFactory;
import com.xgame.logic.server.core.db.redis.RedisClient;
import com.xgame.logic.server.core.language.Language;
import com.xgame.logic.server.core.language.view.error.ErrorCodeEnum;
import com.xgame.logic.server.game.country.structs.build.BuildFactory;
import com.xgame.logic.server.game.player.PlayerManager;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.war.bean.WarDefendData;
import com.xgame.logic.server.game.war.constant.WarConstant;
import com.xgame.logic.server.game.war.constant.WarType;
import com.xgame.logic.server.game.war.entity.Battle;
import com.xgame.logic.server.game.war.entity.LuaContext;
import com.xgame.logic.server.game.war.entity.WarDefender;
import com.xgame.logic.server.game.war.entity.WarFightParam;
import com.xgame.logic.server.game.war.entity.handler.IWarHandler;
import com.xgame.logic.server.game.world.WorldLogicManager;
import com.xgame.logic.server.game.world.constant.WorldConstant;
import com.xgame.utils.TimeUtils;


/**
 * 战斗管理器
 * @author jacky.jiang
 *
 */
@Slf4j
@Component
public class WarManager {

	@Autowired
	private DbCacheService dbCacheService;
	@Autowired
	private PlayerManager playerManager;
	@Autowired
	private RedisClient redisClient;
	@Autowired
	private ConfigSystem configSystem;
	@Autowired
	private WorldLogicManager worldLogicManager;
	
	/**
	 * 战斗线程池
	 */
	private ThreadPoolExecutor battleExcutor;
	
	/**
	 * 战斗缓存
	 * <ul>
	 * 	<li>battleId</li>
	 * 	<li>battle {@link Battle}</li>
	 * </ul>
	 */
	private Map<Long, Battle> battleMap = new ConcurrentHashMap<>();
	
	/**
	 * 玩家战斗缓存
	 * <ul>
	 * 		<li>playerId</li>
	 * 		<li>battleId</li>
	 * </ul>
	 */
	private Map<Long, List<Long>> playerBattleMap = new ConcurrentHashMap<>();
	
	/**
	 * 战斗处理缓存
	 */
	private Map<WarType, IWarHandler> warHandlerMap = new ConcurrentHashMap<>();
	
	// 全局战斗id生成器
	public static AtomicLong BATTLEID_GENERATOR = new AtomicLong();
	
	/**
	 * lua执行上下文
	 */
	private LuaContext luaContext = new LuaContext();
	
	@Startup(order = StartupOrder.WAR_START, desc = "战斗初始化")
	public void init() {
		luaContext.init(configSystem);
		
		// 战斗线程初始化
		battleExcutor = new ThreadPoolExecutor(
				WarConstant.CORE_THREAD_SIZE,
				WarConstant.MAX_THREAD_SIZE,
				WarConstant.KEEP_ALIVE_MILLIS, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>(Integer.MAX_VALUE),
				new NamedThreadFactory(new ThreadGroup(WarConstant.THREAD_GROUP_NAME), WarConstant.THREAD_GROUP_NAME.replace("池", "")),
				new RejectedExecutionHandler() {
					@Override
					public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
						try {
							r.run();
						} catch (Exception e) {
							log.error("排队线程池执行任务异常!", e);
						}
					}
				});
		
	}
	
	/**
	 * 战斗开始
	 * @param warFightParam
	 * @param executor 标识是一次性直接算晚的战斗
	 * @return 
	 */
	public Battle startBattle(WarFightParam warFightParam) {
		IWarHandler warHandler = warHandlerMap.get(warFightParam.getBattleType());
		Battle battle = warHandler.init(warFightParam);
		battle.setWarHandler(warHandler);
		
		// 野外战斗需要设置战斗开始时间
		this.addBattle(battle);
		if(!battle.getWarType().isRts()) {
			battle.setStartTime(TimeUtils.getCurrentTimeMillis());
			battle.setEndTime(battle.getStartTime() +  WorldConstant.BATTLE_TIME);
			battle.setAttackSoldierTypeList(warFightParam.soldierTypeList());
			warHandler.fight(battle);
		} else {
			warHandler.fight(battle);
		}
		return battle;
	}

	
	/**
	 * 进攻方离线移除战斗
	 * @param player
	 */
	public void playerOffline(Player player) {
		List<Long> battleIds = playerBattleMap.get(player.getId());
		if(battleIds != null && !battleIds.isEmpty()) {
			for(Long id : battleIds) {
				Battle battle = battleMap.get(id);
				if(battle == null) {
					battleMap.remove(id);
				}
				
				
				if(battle != null && !battle.isExecute()) {
					battle.getWarHandler().fight(battle);
				}
			}
		}
	}
	
	
	/**
	 * TODO 缺失搜多条件（从排行榜当中取数据）
	 *  条件1：防守方与进攻方不在同一个联盟，且防守方的防护罩没有打开
		条件2：abs(防守方X-进攻方X)<=P1 战斗力
		条件3：abs(防守方Y-进攻方y)<=P2 行政大楼等级
	 * @param player
	 * @return
	 */
	public WarDefendData search(Player player) {
		// 搜索全服玩家,排除自己
		Collection<Long> set = playerManager.getServerPlayerIds();
		List<Long> list = new ArrayList<>();
		list.addAll(set);
		list.remove(player.getId());
		
		Random random = new Random();
		Player defendPlayer = null;
		if(player.roleInfo().getBasics().getUserName().equals("alex03")) {
			defendPlayer = playerManager.getPlayerByUserName("alex01");
		} else if(player.roleInfo().getBasics().getUserName().equals("ff1101")) {
			defendPlayer = playerManager.getPlayerByUserName("ff1102");
		} else if(player.roleInfo().getBasics().getUserName().equals("t37")) {
			defendPlayer = playerManager.getPlayerByUserName("t40");
		} else {
			// TODO 取排行榜当中的数据
			int index = random.nextInt(list.size());
			Long defendPlayerId = list.get(index);
			defendPlayer = playerManager.getPlayer(defendPlayerId);
			if(defendPlayer == null) {
				Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE1);
				return null;
			}
		}
		
		WarDefender warDefender = new WarDefender();
		warDefender.setPlayer(defendPlayer);
		warDefender.initBulidings(defendPlayer);
		warDefender.initCitySoldier(defendPlayer);
		return warDefender.getWarDefenData();
	}
	
	/**
	 * 获取最大掠夺资源
	 * @param player
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int[] getMaxInvasionResource(Player player) {
		int moneyLevel = player.getCountryManager().getMoneyResourceControl().getDefianlBuild().getLevel();
		int oilLevel = player.getCountryManager().getOilResourceControl().getDefianlBuild().getLevel();
		int rareLevel = player.getCountryManager().getRareResourceCountrol().getDefianlBuild().getLevel();
		int steelLevel = player.getCountryManager().getSteelResourceControl().getDefianlBuild().getLevel();
		
		int[] maxResources = new int[4];
		BuildingPir moneybuildingPir = BuildingPirFactory.get(BuildFactory.MONEY_RESOURCE.getTid());
		if(moneybuildingPir  != null) {
			Map<Integer, Integer> map = (Map<Integer, Integer>)moneybuildingPir.getV1();
			if(map.get(moneyLevel) != null) {
				maxResources[0] = map.get(moneyLevel);
			}
		}
		
		BuildingPir oilBuildingPair = BuildingPirFactory.get(BuildFactory.OIL_RESOURCE.getTid());
		if(oilBuildingPair  != null) {
			Map<Integer, Integer> map = (Map<Integer, Integer>)oilBuildingPair.getV1();
			if(map.get(oilLevel) != null) {
				maxResources[1] = map.get(oilLevel);
			}
		}
		
		BuildingPir rareBuildingPair = BuildingPirFactory.get(BuildFactory.RARE_RESOURCE.getTid());
		if(rareBuildingPair  != null) {
			Map<Integer, Integer> map = (Map<Integer, Integer>)rareBuildingPair.getV1();
			if(map.get(rareLevel) != null) {
				maxResources[2] = map.get(rareLevel);
			}
		}
		
		BuildingPir steelBuilding = BuildingPirFactory.get(BuildFactory.STEEL_RESOURCE.getTid());
		if(steelBuilding  != null) {
			Map<Integer, Integer> map = (Map<Integer, Integer>)steelBuilding.getV1();
			if(map.get(steelLevel) != null) {
				maxResources[3] = map.get(steelLevel);
			}
		}
		return maxResources;
	}
	
	public void addWarHandler(IWarHandler warHandler) {
		this.warHandlerMap.put(warHandler.getWarType(), warHandler);
	}
	
	public ThreadPoolExecutor getBattleExcutor() {
		return battleExcutor;
	}

	public LuaContext getLuaContext() {
		return luaContext;
	}

	/**
	 * 添加战场
	 * @param battle
	 */
	public void addBattle(Battle battle) {
		List<Long> playerBattleList = this.playerBattleMap.get(battle.getOwnerId());
		if(playerBattleList == null) {
			playerBattleList = new ArrayList<Long>();
			playerBattleMap.put(battle.getOwnerId(), playerBattleList);
		}
		playerBattleList.add(battle.getOwnerId());
		this.battleMap.put(battle.getBattleId(), battle);
	}
	
	/**
	 * 移除战场
	 * @param battle
	 */
	public void removeBattle(Battle battle) {
		this.battleMap.remove(battle.getBattleId());
		List<Long> battleList = playerBattleMap.get(battle.getOwnerId());
		if(battleList != null && !battleList.isEmpty()) {
			battleList.remove(battle.getOwnerId());
		}
	}
	
	/**
	 *  获取战场信息
	 * @param battleId
	 * @return
	 */
	public Battle getBattle(long battleId) {
		return this.battleMap.get(battleId);
	}
}
