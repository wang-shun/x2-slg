package com.xgame.logic.server.game.world;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.db.cache.cache.CacheProxy;
import com.xgame.logic.server.core.db.redis.RedisClient;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.core.utils.collection.ConcurrentHashSet;
import com.xgame.logic.server.game.alliance.enity.AllianceBattleTeam;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.timertask.entity.job.data.TimerTaskData;
import com.xgame.logic.server.game.world.bean.VectorInfo;
import com.xgame.logic.server.game.world.constant.MarchState;
import com.xgame.logic.server.game.world.constant.MarchType;
import com.xgame.logic.server.game.world.converter.WorldConverter;
import com.xgame.logic.server.game.world.entity.SpriteInfo;
import com.xgame.logic.server.game.world.entity.WorldMarch;
import com.xgame.logic.server.game.world.entity.march.IMarchExecutor;
import com.xgame.logic.server.game.world.entity.march.WorldMarchFactory;
import com.xgame.logic.server.game.world.entity.march.WorldMarchParam;
import com.xgame.logic.server.game.world.entity.sprite.WorldSprite;

/**
 * 世界行军管理器
 * @author jacky.jiang
 * @param <V>
 *
 */
@Slf4j
@Component
public class WorldMarchManager extends CacheProxy<WorldMarch>  {

	@Autowired
	private RedisClient redisClient;
	@Autowired
	private SpriteManager spriteManager;
	
	public static final int VIEW_MARCH_SCREEN_SIZE = 20;
	
	/**
	 * 起点的行军信息
	 */
	private Map<Integer, Set<Long>> srcMarchMap = new ConcurrentHashMap<Integer, Set<Long>>();
	
	/**
	 * 目标点的行军信息
	 */
	private Map<Integer, Set<Long>> destMarchMap = new ConcurrentHashMap<Integer, Set<Long>>();
	
	public void init() {
		List<WorldMarch> list = redisClient.hvals(WorldMarch.class);
		if(list != null && !list.isEmpty()) {
			for(WorldMarch worldMarch : list) {
				Player player = InjectorUtil.getInjector().dbCacheService.get(Player.class, Long.valueOf(worldMarch.getOwnerUid()));
				WorldMarchParam worldMarchParam = new WorldMarchParam();
				worldMarchParam.setPlayer(player);
				worldMarchParam.setMarchType(worldMarch.getMarchType());
				worldMarchParam.setWorldMarchSoldier(worldMarch.getWorldMarchSoldier());
				worldMarchParam.setTeam(worldMarch.getTeamId());
				worldMarchParam.setRemainTime(worldMarch.getRemainTime());
				worldMarchParam.setTargetPoint(worldMarch.getTargetId());
				worldMarchParam.setResourceType(worldMarch.getTargetSubType());
				worldMarchParam.setSpriteType(worldMarch.getTargetType());
				worldMarchParam.setSystemCreate(worldMarch.isSystemCreate());
				worldMarchParam.setDefSignature(worldMarch.getDefEmailSignature());
				worldMarchParam.setWorldMarchSoldier(worldMarch.getWorldMarchSoldier());
				
				// 创建行军信息
				IMarchExecutor executor = WorldMarchFactory.createWorldMarch(worldMarchParam);
				worldMarch.executor = executor;
				executor.setWorldMarch(worldMarch);
				this.add(worldMarch);
				
				// 添加到行军队列
				this.addSrcMarch(worldMarch.getOwnerPoint(), worldMarch.getId());
				this.addDestMarch(worldMarch.getTargetId(), worldMarch.getId());
			}
		}
	}
	
	/**
	 * 处理异常队伍返回
	 */
	public void dealExceptionMarch() {
		Collection<WorldMarch> collection = this.getEntityCache().values();
		if(collection != null && !collection.isEmpty()) {
			for(WorldMarch worldMarch : collection) {
				// 卡在行军队列
				if(worldMarch.getMarchState() == MarchState.MARCH && worldMarch.getTaskId() <= 0) {
					worldMarch.getExecutor().handleReturn();
					log.error(String.format("处理行军队列异常1：行军队列名称：[%s],队列类型[%s]，队列状态[%s],开始时间[%s]，结束时间[%s]", worldMarch.getOwnerName(), worldMarch.getMarchTime(), worldMarch.getMarchState(), worldMarch.getMarchStartTime(), worldMarch.getArrivalTime()));
					continue;
				}
				
				if(worldMarch.getMarchState() == MarchState.BACK && worldMarch.getTaskId() <= 0 ) {
					worldMarch.getExecutor().backHomeImmediately(worldMarch);
					log.error(String.format("处理行军队列异常21：行军队列名称：[%s],队列类型[%s]，队列状态[%s],开始时间[%s]，结束时间[%s]", worldMarch.getOwnerName(), worldMarch.getMarchTime(), worldMarch.getMarchState(), worldMarch.getMarchStartTime(), worldMarch.getArrivalTime()));
					continue;
				}
				
				// 任务队列小时
				if(worldMarch.getMarchState() == MarchState.MARCH) {
					TimerTaskData timerTaskData = InjectorUtil.getInjector().dbCacheService.get(TimerTaskData.class, worldMarch.getTaskId());
					if(timerTaskData == null) {
						worldMarch.getExecutor().handleReturn();
						log.error(String.format("处理行军队列异常2：行军队列名称：[%s],队列类型[%s]，队列状态[%s],开始时间[%s]，结束时间[%s]", worldMarch.getOwnerName(), worldMarch.getMarchTime(), worldMarch.getMarchState(), worldMarch.getMarchStartTime(), worldMarch.getArrivalTime()));
						continue;
					}
				}
				
				// 任务队列小时
				if(worldMarch.getMarchState() == MarchState.BACK) {
					TimerTaskData timerTaskData = InjectorUtil.getInjector().dbCacheService.get(TimerTaskData.class, worldMarch.getTaskId());
					if(timerTaskData == null) {
						worldMarch.getExecutor().backHomeImmediately(worldMarch);
						log.error(String.format("处理行军队列异常31：行军队列名称：[%s],队列类型[%s]，队列状态[%s],开始时间[%s]，结束时间[%s]", worldMarch.getOwnerName(), worldMarch.getMarchTime(), worldMarch.getMarchState(), worldMarch.getMarchStartTime(), worldMarch.getArrivalTime()));
						continue;
					}
				}
				
				// 占领中并且没有在目的地
				if(worldMarch.getMarchType() == MarchType.CITY_FIGHT ||  worldMarch.getMarchType() == MarchType.EXPLORER || worldMarch.getMarchType() == MarchType.CAMP || worldMarch.getMarchType() == MarchType.TERRITORY) {
					if(worldMarch.getMarchState() == MarchState.OCCUPY) {
						SpriteInfo spriteInfo = spriteManager.getGrid(worldMarch.getTargetId());
						if(spriteInfo != null) {
							WorldSprite worldSprite = spriteInfo.getParam();
							if(worldSprite != null) {
								if(worldSprite.getOwnerMarchId() != worldMarch.getId() && worldSprite.getAttackMarchId() != worldMarch.getId() && !worldSprite.getBattleWaitQueue().contains(worldMarch.getId())) {
									worldMarch.getExecutor().handleReturn();
									log.error(String.format("处理行军队列异常10：行军队列名称：[%s],队列类型[%s]，队列状态[%s],开始时间[%s]，结束时间[%s]", worldMarch.getOwnerName(), worldMarch.getMarchTime(), worldMarch.getMarchState(), worldMarch.getMarchStartTime(), worldMarch.getArrivalTime()));
								}
							} else {
								worldMarch.getExecutor().backHomeImmediately(worldMarch);
							}
						} else {
							worldMarch.getExecutor().backHomeImmediately(worldMarch);
						}
					}
				}
				
				// 处理采集中没队列
				if(worldMarch.getMarchType() == MarchType.EXPLORER && worldMarch.getExlporerTaskId() > 0) {
					TimerTaskData timerTaskData = InjectorUtil.getInjector().dbCacheService.get(TimerTaskData.class, worldMarch.getExlporerTaskId());
					if(timerTaskData  == null) {
						worldMarch.getExecutor().handleReturn();
						log.error(String.format("处理行军队列异常3：行军队列名称：[%s],队列类型[%s]，队列状态[%s],开始时间[%s]，结束时间[%s]", worldMarch.getOwnerName(), worldMarch.getMarchTime(), worldMarch.getMarchState(), worldMarch.getMarchStartTime(), worldMarch.getArrivalTime()));
						continue;
					}
				}
				
				// 处理占领中没队列
				if(worldMarch.getMarchType() == MarchType.TERRITORY && worldMarch.getOccupyTaskId() > 0) {
					TimerTaskData timerTaskData = InjectorUtil.getInjector().dbCacheService.get(TimerTaskData.class, worldMarch.getOccupyTaskId());
					if(timerTaskData  == null) {
						worldMarch.getExecutor().handleReturn();
						log.error(String.format("处理行军队列异常4：行军队列名称：[%s],队列类型[%s]，队列状态[%s],开始时间[%s]，结束时间[%s]", worldMarch.getOwnerName(), worldMarch.getMarchTime(), worldMarch.getMarchState(), worldMarch.getMarchStartTime(), worldMarch.getArrivalTime()));
						continue;
					}
				}
				
				// 集结进攻
				if(worldMarch.getMarchType() == MarchType.TEAM_ATTACK) {
					if(!StringUtils.isEmpty(worldMarch.getTeamId())) {
						AllianceBattleTeam allianceBattleTeam = InjectorUtil.getInjector().dbCacheService.get(AllianceBattleTeam.class, worldMarch.getTeamId());
						if(allianceBattleTeam == null && (worldMarch.getMarchState() == MarchState.MASS || worldMarch.getMarchState() == MarchState.MARCH)) {
							worldMarch.getExecutor().handleReturn();
							log.error(String.format("处理行军队列异常8：行军队列名称：[%s],队列类型[%s]，队列状态[%s],开始时间[%s]，结束时间[%s]", worldMarch.getOwnerName(), worldMarch.getMarchTime(), worldMarch.getMarchState(), worldMarch.getMarchStartTime(), worldMarch.getArrivalTime()));
						} else {
							TimerTaskData timerTaskData = InjectorUtil.getInjector().dbCacheService.get(TimerTaskData.class, worldMarch.getTaskId());
							if(timerTaskData == null) {
								worldMarch.getExecutor().handleReturn();
								log.error(String.format("处理行军队列异常5：行军队列名称：[%s],队列类型[%s]，队列状态[%s],开始时间[%s]，结束时间[%s]", worldMarch.getOwnerName(), worldMarch.getMarchTime(), worldMarch.getMarchState(), worldMarch.getMarchStartTime(), worldMarch.getArrivalTime()));
							}
						}
					}
				}
				
				// 集结防御
				if(worldMarch.getMarchType() == MarchType.ALLIANCE_MEMBER_ASSEMBLY) {
					if(!StringUtils.isEmpty(worldMarch.getTeamId())) {
						AllianceBattleTeam allianceBattleTeam = InjectorUtil.getInjector().dbCacheService.get(AllianceBattleTeam.class, worldMarch.getTeamId());
						if(allianceBattleTeam == null && (worldMarch.getMarchState() == MarchState.MARCH || worldMarch.getMarchState() == MarchState.OCCUPY)) {
							worldMarch.getExecutor().handleReturn();
							log.error(String.format("处理行军队列异常7：行军队列名称：[%s],队列类型[%s]，队列状态[%s],开始时间[%s]，结束时间[%s]", worldMarch.getOwnerName(), worldMarch.getMarchTime(), worldMarch.getMarchState(), worldMarch.getMarchStartTime(), worldMarch.getArrivalTime()));
						}
					}
				}
			}
		}
	}
	
	/**
	 * 获取行军队列信息
	 * @param player
	 * @param x
	 * @param y
	 * @return
	 */
	public List<VectorInfo> getVectorInfo(int x, int y) {
		List<VectorInfo> vectorInfos = new ArrayList<VectorInfo>();
		
		// 终点的行军信息
		Collection<WorldMarch> destMarchList = this.getEntityCache().values();
		if(destMarchList != null && !destMarchList.isEmpty()) {
			for(WorldMarch worldMarch : destMarchList) {
				vectorInfos.add(WorldConverter.converterVectorInfo(worldMarch, false));
			}
		}
		
		return vectorInfos;
	}
	
	/**
	 * 添加行军信息
	 * @param worldMarch
	 */
	public void addWorldMarch(WorldMarch worldMarch) {
		addSrcMarch(worldMarch.getOwnerPoint(), worldMarch.getId());
		addDestMarch(worldMarch.getTargetId(), worldMarch.getId());
		InjectorUtil.getInjector().dbCacheService.create(worldMarch);
	}
	
	/**
	 * 移除行军信息
	 * @param marchId
	 */
	public WorldMarch removeWorldMarch(long marchId) {
		WorldMarch worldMarch = InjectorUtil.getInjector().dbCacheService.get(WorldMarch.class, marchId);
		if(worldMarch != null) {
			Set<Long> srcMarch = srcMarchMap.get(worldMarch.getOwnerPoint());
			if(srcMarch != null) {
				srcMarch.remove(marchId);
			}
			
			Set<Long> destMarch= destMarchMap.get(worldMarch.getTargetId());
			if(destMarch != null) {
				destMarch.remove(marchId);
			}	
		}
		
		InjectorUtil.getInjector().dbCacheService.delete(worldMarch);
		return worldMarch;
	}
	
	/**
	 * 添加到起点
	 * @param start
	 * @param marchId
	 */
	public void addSrcMarch(int index, long marchId) {
		Set<Long> march = srcMarchMap.get(index);
		if(march == null) {
			march = new ConcurrentHashSet<>();
			srcMarchMap.put(index, march);
		}
		march.add(marchId);
	}
	
	/**
	 * 添加到终点
	 * @param end
	 * @param marchId
	 */
	public void addDestMarch(int index, long marchId) {
		Set<Long> march = destMarchMap.get(index);
		if(march == null) {
			march = new ConcurrentHashSet<Long>();
			destMarchMap.put(index, march);		
		}
		march.add(marchId);
	}
	
	/**
	 * 移除起点行军队列
	 * @param index
	 * @param marchId
	 */
	public void removeSrcMarch(int index, long marchId) {
		Set<Long> march = srcMarchMap.get(index);
		march.remove(marchId);
	}
	
	/**
	 * 移除目标点行军队列
	 * @param index
	 * @param marchId
	 */
	public void removeDestMarch(int index, long marchId) {
		Set<Long> march = destMarchMap.get(index);
		march.remove(marchId);
	}
	
	/**
	 * 获取玩家的行军队列
	 * @param playerId
	 * @return
	 */
	public List<WorldMarch> getWorldMarchByPlayerId(long playerId) {
		List<WorldMarch> worldMarchs = new ArrayList<>();
		Iterator<WorldMarch> itor = this.getEntityCache().values().iterator();
		while(itor.hasNext()) {
			WorldMarch worldMarch = itor.next();
			if(worldMarch != null && worldMarch.getOwnerUid().equals(String.valueOf(playerId))) {
				worldMarchs.add(worldMarch);
			}
		}
		return worldMarchs;
	}
	
	/**
	 * 获取玩家出征部队信息
	 * @param playerId
	 * @return
	 */
	public List<Long> getWorldMarchIdsByPlayerId(long playerId) {
		List<Long> worldMarchs = new ArrayList<>();
		Iterator<WorldMarch> itor = this.getEntityCache().values().iterator();
		while(itor.hasNext()) {
			WorldMarch worldMarch = itor.next();
			if(worldMarch != null && worldMarch.getOwnerUid().equals(String.valueOf(playerId))) {
				worldMarchs.add(worldMarch.getId());
			}
		}
		return worldMarchs;
	}
	
	/**
	 * 获取我的部队，在目标点上
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean getMyMarchOnDestination(long playerId, int index) {
		Set<Long> set = destMarchMap.get(index);
		List<Long> marchPlayerIds = getWorldMarchIdsByPlayerId(playerId);
		if(set != null) {
			return marchPlayerIds.retainAll(set);
		}
		return false;
	}

	/**
	 * 获取目标的行军队列信息
	 * @param index
	 * @return
	 */
	public List<WorldMarch> getDestinationWorldMarch(int index) {
		List<WorldMarch> marchList = new ArrayList<>();
		Set<Long> set = destMarchMap.get(index);
		if(set != null) {
			for(Long id : set) {
				WorldMarch worldMarch = InjectorUtil.getInjector().dbCacheService.get(WorldMarch.class, id);
				if(worldMarch != null) {
					marchList.add(worldMarch);
				}
			}
		}
		return marchList;
	}
	
	/**
	 * 获取集结防御的军队
	 * @param index
	 * @return
	 */
	public List<WorldMarch> getReinforceWorldMarch(int index) {
		List<WorldMarch> marchList = new ArrayList<>();
		Set<Long> set = destMarchMap.get(index);
		if(set != null) {
			for(Long id : set) {
				WorldMarch worldMarch = InjectorUtil.getInjector().dbCacheService.get(WorldMarch.class, id);
				if(worldMarch != null && worldMarch.getMarchType() == MarchType.MARCH_REINFORCE) {
					marchList.add(worldMarch);
				}
			}
		}
		return marchList;
	}
	
	/**
	 * 获取集结防御到达的部队
	 * @param index
	 * @return
	 */
	public List<WorldMarch> getReinforceOccupy(int index) {
		List<WorldMarch> marchList = new ArrayList<>();
		Set<Long> set = destMarchMap.get(index);
		if(set != null) {
			for(Long id : set) {
				WorldMarch worldMarch = InjectorUtil.getInjector().dbCacheService.get(WorldMarch.class, id);
				if(worldMarch != null && worldMarch.getMarchType() == MarchType.MARCH_REINFORCE && worldMarch.getMarchState() == MarchState.OCCUPY) {
					marchList.add(worldMarch);
				}
			}
		}
		return marchList;
	}
	
	@Override
	public Class<?> getProxyClass() {
		return WorldMarch.class;
	}

	@Override
	public void add(WorldMarch t) {
		addSrcMarch(t.getOwnerPoint(), t.getId());
		addDestMarch(t.getTargetId(), t.getId());
		super.add(t);
	}

	@Override
	public void remove(WorldMarch t) {
		this.destMarchMap.remove(t.getTargetId());
		this.srcMarchMap.remove(t.getOwnerPoint());
		super.remove(t);
	}
}
