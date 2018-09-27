package com.xgame.logic.server.game.world;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xgame.config.global.GlobalPirFactory;
import com.xgame.config.util.RandomSeed;
import com.xgame.config.ziYuanDian.ZiYuanDianPir;
import com.xgame.config.ziYuanDian.ZiYuanDianPirFactory;
import com.xgame.config.ziYuanShengCheng.ZiYuanShengChengPir;
import com.xgame.config.ziYuanShengCheng.ZiYuanShengChengPirFactory;
import com.xgame.data.sprite.SpriteType;
import com.xgame.framework.lifecycle.Startup;
import com.xgame.framework.lifecycle.StartupOrder;
import com.xgame.gameconst.GlobalConstant;
import com.xgame.gameconst.WorldResourceType;
import com.xgame.logic.server.core.db.cache.cache.CacheProxy;
import com.xgame.logic.server.core.db.cache.entity.IEntity;
import com.xgame.logic.server.core.db.redis.RedisClient;
import com.xgame.logic.server.core.system.GlobalManager;
import com.xgame.logic.server.core.system.entity.GlobalEnity;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.core.utils.collection.ConcurrentHashSet;
import com.xgame.logic.server.core.utils.scheduler.ScheduleTasks;
import com.xgame.logic.server.core.utils.scheduler.Scheduled;
import com.xgame.logic.server.game.constant.CurrencyEnum;
import com.xgame.logic.server.game.country.bean.Vector2Bean;
import com.xgame.logic.server.game.world.constant.SpriteFeatureType;
import com.xgame.logic.server.game.world.constant.WorldConstant;
import com.xgame.logic.server.game.world.entity.MapJsonData;
import com.xgame.logic.server.game.world.entity.SpriteInfo;
import com.xgame.logic.server.game.world.entity.Vector2;
import com.xgame.logic.server.game.world.entity.WorldMarch;
import com.xgame.logic.server.game.world.entity.model.ResourceRangeIndex;
import com.xgame.logic.server.game.world.entity.model.TerrainConfigModel;
import com.xgame.logic.server.game.world.entity.sprite.ResourceSprite;
import com.xgame.logic.server.game.world.entity.sprite.WorldSprite;
import com.xgame.utils.EnumUtils;
import com.xgame.utils.Probability;
import com.xgame.utils.TimeUtils;

/**
 * 精灵管理器(地图上玩家，资源信息等等)
 * @author jacky.jiang
 *
 */
@Slf4j
@Component
public class SpriteManager extends CacheProxy<SpriteInfo> {
	
	@Autowired
	private RedisClient redisClient;
	@Autowired
	private ResourceRangeIndex resourceRangeIndex;
	@Autowired
	private GlobalManager globalManager;
	@Autowired
	private TerrianManager terrianManager;
	
	/**
	 * x的坐标系
	 */
	public static final int xGridNum = 512;
	
	/**
	 * y的坐标系
	 */
	public static final int yGridNum = 512;
	
	/**
	 * 资源精灵id信息
	 */
	private Set<Long> resourceSprites = new ConcurrentHashSet<>();
	
	/**
	 * 已经回收的精灵id列表
	 */
	private Set<Long> recoverResource = new ConcurrentHashSet<>();
	
	/**
	 * 世界地图格子信息
	 */
	private SpriteInfo[][] gridSpriteInfo = new SpriteInfo[512][512];
	
	/**
	 * 可以使用的格子列表
	 */
	private Map<Integer, SpriteInfo> canUseGrid = new ConcurrentHashMap<>();
	
	/**
	 * 地图生成的不可走点
	 */
	private Set<Integer> mapBlock = new ConcurrentHashSet<Integer>();
	
	/**
	 * 初始化战斗
	 */
	private Set<Integer> battleSprite = new ConcurrentHashSet<Integer>();
	
	/**
	 * 地图锁
	 */
	private Object lock = new Object();
	
	@Startup(order = StartupOrder.WORLD_START, desc = "世界服加载项")
	public void init() {
		GlobalEnity globalEnity = globalManager.getGlobalEntity();
		if(globalEnity != null && globalEnity.getMapGenerateTime() > 0) {
			// 从数据库加载精灵
			loadSprite();
			
			// 初始化最大范围
			resourceRangeIndex.init();
			
			// 刷新精灵信息
			startRefreshSprite();
		} else {
			// 刷地图
			initMap();
			
			// 初始化最大范围
			resourceRangeIndex.init();
			
			// 刷资源
			makeResource();
			
			// 加载地图的障碍点
			loadMapBlock();
			
			// 生成地图结束,统一入库.
			saveDb();
		}
	}
	
	/**
	 * 初始化地图数据
	 * @param blockData
	 */
	private void initMap() {
		// 初始化地表
		for (int i = 0; i < 512; i++) {
			for (int j = 0; j < 512; j++) {
				if (i == 0 || j == 0 || i == 511 || j == 511) {
					// 清空之前格子信息
					addSprite(j * xGridNum + i, SpriteType.BLOCK, new WorldSprite(), false);
					removeCanUse(i, j);
					continue;
				}
				
				SpriteInfo spriteInfo = addSprite(j * xGridNum + i, SpriteType.NONE, new WorldSprite(), false);
				
				// 添加到可视列表当中
				addCanUse(spriteInfo.getX(), spriteInfo.getY());
			}
		}
		
	}
	
	/**
	 * 加载地图障碍点
	 */
	private void loadMapBlock() {
		// 初始化地图数据当中的不可走点
		List<MapJsonData> jsonData = InjectorUtil.getInjector().configLoader.loadMapConfig();
		if(jsonData == null || jsonData.isEmpty()) {
			throw new RuntimeException("加载地图配置信息出错。");
		}
		
		for(MapJsonData mapJsonData : jsonData) {
			SpriteInfo spriteInfo = gridSpriteInfo[mapJsonData.getX()][mapJsonData.getY()];
			if(!mapJsonData.isCanMove()) {
				spriteInfo.setSpriteType(SpriteType.BLOCK.getType());
				removeCanUse(spriteInfo.getIndex());
			}
		}
	}
	
	
	/**
	 * 创建精灵
	 * @param uid
	 * @param index
	 * @param spriteType
	 * @param worldSprite
	 * @param save
	 * @return
	 */
	public SpriteInfo addSprite(int index, SpriteType spriteType, WorldSprite worldSprite, boolean save) {
		long uid = InjectorUtil.getInjector().idSequance.genId();
		SpriteInfo spriteInfo = SpriteInfo.valueOf(uid, index, spriteType, worldSprite);
		gridSpriteInfo[spriteInfo.getX()][spriteInfo.getY()] = spriteInfo;
		if(save) {
			InjectorUtil.getInjector().dbCacheService.create(spriteInfo);
		} else {
			this.add(spriteInfo);
		}
	
		if(spriteType == SpriteType.RESOURCE) {
			this.resourceSprites.add(uid);
		}
		return spriteInfo;
	}
	
	/**
	 * 创建精灵
	 * @param uid
	 * @param spriteType
	 * @param vector2
	 * @param obj 创建精灵所需参数
	 */
	public SpriteInfo createSpriteInfo(int index, SpriteType spriteType, WorldSprite obj) {
		long uid = InjectorUtil.getInjector().idSequance.genId();
		SpriteInfo spriteInfo = SpriteInfo.valueOf(uid, index, spriteType, obj);
		return spriteInfo;
	}
	
	/**
	 * 加载精灵
	 * @param data
	 */
	public void loadSprite() {
		List<SpriteInfo> spriteInfos = redisClient.hvals(SpriteInfo.class);
		if(spriteInfos != null && !spriteInfos.isEmpty()) {
			for(SpriteInfo spriteInfo : spriteInfos) {
				if(spriteInfo.getEnumSpriteType() == SpriteType.RESOURCE) {
					if(spriteInfo.getIndex() <= 0) {
						recoverResource.add(spriteInfo.getId());
						continue;
					}
					
					// 精灵信息
					resourceSprites.add(spriteInfo.getId());
					
					// 插入地图
					this.add(spriteInfo);
					gridSpriteInfo[spriteInfo.getX()][spriteInfo.getY()] = spriteInfo;
					removeCanUse(spriteInfo.getIndex());
				} else if(spriteInfo.getEnumSpriteType() == SpriteType.MONSTER || spriteInfo.getEnumSpriteType() == SpriteType.PLAYER) {
					this.add(spriteInfo);
					gridSpriteInfo[spriteInfo.getX()][spriteInfo.getY()] = spriteInfo;
					removeCanUse(spriteInfo.getIndex());
				} else if(spriteInfo.getEnumSpriteType() == SpriteType.TERRITORY) {
					this.add(spriteInfo);
					gridSpriteInfo[spriteInfo.getX()][spriteInfo.getY()] = spriteInfo;
					
					WorldSprite worldSprite = spriteInfo.getParam();
					if (worldSprite != null && worldSprite.getOwnerMarchId() > 0) {
						removeCanUse(spriteInfo.getX(), spriteInfo.getY());
					} else {
						addCanUse(spriteInfo.getX(), spriteInfo.getY());
					}
				} else if(spriteInfo.getEnumSpriteType() == SpriteType.CAMP) {
					this.add(spriteInfo);
					gridSpriteInfo[spriteInfo.getX()][spriteInfo.getY()] = spriteInfo;
					WorldSprite worldSprite = spriteInfo.getParam();
					
					if (worldSprite != null && worldSprite.getOwnerMarchId() > 0) {
						removeCanUse(spriteInfo.getX(), spriteInfo.getY());
					} else {
						addCanUse(spriteInfo.getX(), spriteInfo.getY());
					}
				} else if(spriteInfo.getEnumSpriteType() == SpriteType.NONE) {
					this.add(spriteInfo);
					gridSpriteInfo[spriteInfo.getX()][spriteInfo.getY()] = spriteInfo;
					
					addCanUse(spriteInfo.getX(), spriteInfo.getY());					
				} else if(spriteInfo.getEnumSpriteType() == SpriteType.BLOCK) {
					this.add(spriteInfo);
					gridSpriteInfo[spriteInfo.getX()][spriteInfo.getY()] = spriteInfo;
					
					removeCanUse(spriteInfo.getX(), spriteInfo.getY());
				}
				
				// 精灵初始化信息 
				WorldSprite worldSprite = spriteInfo.getParam();
				if(worldSprite != null && (worldSprite.getAttackMarchId() > 0 || worldSprite.getOwnerMarchId() > 0)) {
					battleSprite.add(spriteInfo.getIndex());
				}
			}
		}
	}
	
	/**
	 * 启服刷新精灵
	 * @param mapInfo
	 */
	public void startRefreshSprite() {
		float f = GlobalPirFactory.getInstance().getFloat(GlobalConstant.RESOURCE_RESTART_REFRESH_RATIO);
		List<IEntity<?>> updateList = new ArrayList<IEntity<?>>();
		Set<Long> refreshTemp = new ConcurrentHashSet<>();
		refreshTemp.addAll(recoverResource);
		recoverResource.clear();
		for(Long id : refreshTemp) {
			SpriteInfo spriteInfo = InjectorUtil.getInjector().dbCacheService.get(SpriteInfo.class, id);
			if(spriteInfo == null) {
				continue;
			}
			
			if(spriteInfo.getEnumSpriteType() != SpriteType.RESOURCE) {
				continue;
			}
			
			// 资源
			ResourceSprite resourceSprite = spriteInfo.getParam();
			if(resourceSprite == null) {
				continue;
			}
			
			if(resourceSprite.getOwnerMarchId() > 0) {
				WorldMarch worldMarch = InjectorUtil.getInjector().dbCacheService.get(WorldMarch.class, resourceSprite.getOwnerMarchId());
				if(worldMarch == null) {
					resourceSprite.setOwnerMarchId(0);
					updateList.add(spriteInfo);
				}
			}
			
			SpriteInfo blankSprite = getResourceRangeGrid(resourceRangeIndex, resourceSprite.getRange());
			if(blankSprite == null || !blankSprite.canUse()) {
				log.error("刷新资源地图上没有找到可用的点.");
				throw new RuntimeException(String.format("没有获得range:[%s]的格子", resourceSprite.getRange()));
			}
			
			transferGridToSprite(blankSprite.getX(), blankSprite.getY(), spriteInfo, true, true);
			
			ZiYuanDianPir ziYuanDianPir = ZiYuanDianPirFactory.getInstance().getByTypeAndLevel(resourceSprite.getResourceType().ordinal(), resourceSprite.getLevel());
			int minResource = Float.valueOf(ziYuanDianPir.getMAX() * f).intValue();
			if(ziYuanDianPir != null && resourceSprite.getCurNum() < minResource) {
				resourceSprite.setCurNum(ziYuanDianPir.getMAX());
				updateList.add(spriteInfo);
			}
		}

		// 刷新被采集剩余少量的资源
		for(Long id : resourceSprites) {
			SpriteInfo spriteInfo = InjectorUtil.getInjector().dbCacheService.get(SpriteInfo.class, id);
			if(spriteInfo != null && spriteInfo.getEnumSpriteType() == SpriteType.RESOURCE) {
				ResourceSprite resourceSprite = spriteInfo.getParam();
				ZiYuanDianPir ziYuanDianPir = ZiYuanDianPirFactory.getInstance().getByTypeAndLevel(spriteInfo.getEnumSpriteType().getType(), resourceSprite.getLevel());
				int minResource = Float.valueOf(ziYuanDianPir.getMAX() * f).intValue();
				if(ziYuanDianPir != null && resourceSprite.getCurNum() < minResource) {
					resourceSprite.setCurNum(ziYuanDianPir.getMAX());
					updateList.add(spriteInfo);
				}
				
				// 容错，存在没有刷新到的资源，重新刷新.
				if(spriteInfo.getIndex() <= 0) {
					SpriteInfo blankSprite = getResourceRangeGrid(resourceRangeIndex, resourceSprite.getRange());
					if(blankSprite == null || !blankSprite.canUse()) {
						log.error("没有找到格子:在范围：[{}]", resourceSprite.getRange());
						continue;
					} 
					
					// 生成资源
					this.transferGridToSprite(blankSprite.getX(), blankSprite.getY(), spriteInfo, true, false);
					
					// 还原初始值
					resourceSprite.setCurNum(ziYuanDianPir.getMAX());
					updateList.add(spriteInfo);
				}
			}
		}
		
		// 保存更新
		redisClient.saveBatch(updateList);
	}
	
	/**
	 * 根据时间刷新资源
	 * @param mapInfo
	 */
	@Scheduled(name = "刷新精灵", value = ScheduleTasks.REFRESH_SPRITE)
	public void timeRefreshSprite() {
		float f = GlobalPirFactory.getInstance().getFloat(GlobalConstant.RESOURCE_REFRESH_RATIO);
		Set<Long> refreshTemp = new ConcurrentHashSet<>();
		refreshTemp.addAll(recoverResource);
		// 采集完的的精灵格子
		if(refreshTemp != null && !refreshTemp.isEmpty()) {
			for(Long id : refreshTemp) {
				SpriteInfo spriteInfo = InjectorUtil.getInjector().dbCacheService.get(SpriteInfo.class, id);
				if(spriteInfo == null) {
					log.error("精灵不存在,精灵id:[{}]", id);
					continue;
				}
				
				if(spriteInfo.getEnumSpriteType() != SpriteType.RESOURCE) {
					continue;
				}
				
				ResourceSprite resourceSprite = spriteInfo.getParam();
				SpriteInfo blankSprite = this.getResourceRangeGrid(resourceRangeIndex, resourceSprite.getRange());
				if(blankSprite == null || !blankSprite.canUse()) {
					log.error("没有找到格子:在范围：[{}]", resourceSprite.getRange());
					continue;
				}
				
				transferGridToSprite(blankSprite.getX(), blankSprite.getY(), spriteInfo, true, true);
				
				ZiYuanDianPir ziYuanDianPir = ZiYuanDianPirFactory.getInstance().getByTypeAndLevel(resourceSprite.getResourceType().ordinal(), resourceSprite.getLevel());
				if(ziYuanDianPir == null) {
					log.error("资源点基础数据不存在:类型：[{}],等级:[{}]", spriteInfo.getSpriteType(), resourceSprite.getLevel());
					continue;
				}
				
				int minResource = Float.valueOf(ziYuanDianPir.getMAX() * f).intValue();
				if(ziYuanDianPir != null && resourceSprite.getCurNum() < minResource) {
					resourceSprite.setCurNum(ziYuanDianPir.getMAX());
					InjectorUtil.getInjector().dbCacheService.update(blankSprite);
				}
			}
		}
		
	}
	

	/**
	 * 资源创建
	 * @param mapInfo
	 */
	private void makeResource() {
		// 生成初始资源, 只会在开服时运行一次, 之后一直通过数据库维护
		log.debug("-----------make resource-----------");
		int RES_CENTER_X = xGridNum / 2;
		int RES_CENTER_Y = yGridNum / 2;
		Iterator<ZiYuanShengChengPir> iterator = ZiYuanShengChengPirFactory.getInstance().getFactoryList().iterator();
		while (iterator.hasNext()) {
			ZiYuanShengChengPir cfgData = iterator.next();

			int scanXMin = RES_CENTER_X - cfgData.getRange();
			int scanXMax = RES_CENTER_X + cfgData.getRange();
			int scanYMin = RES_CENTER_Y - cfgData.getRange();
			int scanYMax = RES_CENTER_Y + cfgData.getRange();

			List<Integer> gridList = resourceRangeIndex.getListByRange(cfgData.getRange());
			log.debug("make:{}", cfgData.getRange()+"***********"+scanXMin+"**"+scanXMax+"***"+scanYMin+"***"+scanYMax+",格子数量:"+gridList.size());
			
			// 生成地图资源
			createResource(cfgData.getRange(), gridList, cfgData);
		}
	}
	
	/**
	 * 保存入库
	 */
	private void saveDb() {
		GlobalEnity globalEnity = globalManager.getGlobalEntity();
		synchronized (globalEnity) {
			if(globalEnity != null) {
				globalEnity.setMapGenerateTime(TimeUtils.getCurrentTimeMillis());
				globalManager.saveGlobalEntity(globalEnity);
			}
		}
		
		// 精灵
		List<IEntity<?>> updateList = new ArrayList<>();

		for(int i=0;i<xGridNum;i++) {
			for(int j=0;j<yGridNum;j++) {
				SpriteInfo spriteInfo = this.gridSpriteInfo[i][j];
				updateList.add(spriteInfo);
			}
		}
		
		// 批量保存
		redisClient.hMset(SpriteInfo.class.getSimpleName(), updateList);
		
		// 保存不可走点信息
		log.info("save map success, sprite count:.........." + updateList.size());
	}
	
	/**
	 * 创建地图资源
	 * @param range
	 * @param gridList
	 * @param cfgData
	 */
	private void createResource(int range, List<Integer> gridList, ZiYuanShengChengPir cfgData) {
		MapStatics mapStatics = new MapStatics();
		for(Integer grid : gridList) {
			int y = grid / xGridNum;
			int x = grid % xGridNum;
			
			if (x == -1 || y == -1)
				continue;
			// 
			if(!isObstacle(x, y, SpriteFeatureType.SPRITE_1X1)) {
				// 产生精灵
				createSprite(cfgData, x, y, mapStatics);
			}
		}

		log.info(String.format("当前区域资源生成完成：距离中心点范围大小: [%s], 资源信息:%s", range,  mapStatics.toString()));
	}
	
	
	/**
	 * 随机坐标范围内的一个点
	 * @param resourceRangeIndex
	 * @param range
	 * @return
	 */
	private SpriteInfo getResourceRangeGrid(ResourceRangeIndex resourceRangeIndex, int range) {
		synchronized (lock) {
			for (int i = 0; i <= 100; i++) {
				List<Integer> rangeGride = resourceRangeIndex.getListByRange(range);
				Set<Integer> canUseGride = canUseGrid.keySet();
				
				List<Integer> newList = new ArrayList<Integer>();
				if(rangeGride != null) {
					newList.addAll(rangeGride);
				}
				newList.retainAll(canUseGride);
				
				int index = Probability.randomNum(newList.size());
				try {
					Integer gridIndex = newList.get(index);
					
					SpriteInfo spriteInfo = canUseGrid.get(gridIndex);
					if(spriteInfo == null || !spriteInfo.canUse()) {
						log.error("没有找到格子:在范围：[{}]", range);
						continue;
					} 
					return spriteInfo;
				} catch(Exception e) {
					System.out.println("");
				}
			}
		}
		return null;
	}
	
	/**
	 * 获取随机坐标点
	 * @return
	 */
	public Vector2Bean getRandomPosition() { 
		Set<Integer> set = canUseGrid.keySet();
		List<Integer> newList = new ArrayList<Integer>();
		newList.addAll(set);
		
		int index = Probability.randomNum(newList.size());
		Integer gridIndex = newList.get(index);
		
		Vector2Bean vector2Bean = new Vector2Bean();
		vector2Bean.y = gridIndex / xGridNum;
		vector2Bean.x = gridIndex % xGridNum;
		return vector2Bean;
	}

	/**
	 * 计算随机资源,地形
	 * @param cfgData
	 * @param x
	 * @param y
	 * @return
	 */
	private SpriteInfo createSprite(ZiYuanShengChengPir cfgData, int x, int y, MapStatics mapStatics) {
		
		Integer totalMaxRate = ZiYuanShengChengPirFactory.getInstance().getResLevelMaxRates().get(cfgData.getRange());
		int rNum = Probability.randomNum(1, totalMaxRate);
		
		SpriteInfo originSprite = gridSpriteInfo[x][y];
		TerrainConfigModel terrainConfigModel = terrianManager.createTerrianData(x, y, cfgData);
		if(originSprite != null) {
			originSprite.setTerrainConfigModel(terrainConfigModel);
		}
		
		List<RandomSeed> rsList = ZiYuanShengChengPirFactory.getInstance().getResLevelLists().get(cfgData.getRange());
		List<RandomSeed> kdList = ZiYuanShengChengPirFactory.getInstance().getResCategoryLists().get(cfgData.getRange());
		Integer kdTotalMaxRate = ZiYuanShengChengPirFactory.getInstance().getResCategoryMaxRates().get(cfgData.getRange());
		for (int j = 0; j < rsList.size(); j++) {
			if (rNum >= rsList.get(j).startRate && rNum <= rsList.get(j).rate) {
				// 随机资源等级 ， 根据等级再看是否是实际资源
				int level = rsList.get(j).data;
				if (level == 0) {
					// 空地
					mapStatics.addKongdi();
					
				} else if (level == 99) {
					// x
					setBlock(x, y);
					mapStatics.addBukezou();
					break;
				} else {
					// 随机实际资源
					int rKdNum = Probability.randomNum(1, kdTotalMaxRate);
					WorldResourceType type = null;
					int rlevel = 0;
					for (int i = 0; i < kdList.size(); i++) {
						if (rKdNum >= kdList.get(i).startRate && rKdNum <= kdList.get(i).rate) {
							type = kdList.get(i).spriteType;
							rlevel = rsList.get(j).data;
							
							ZiYuanDianPir resCfgData = ZiYuanDianPirFactory.getInstance().getByTypeAndLevel(type.ordinal(), rlevel);
							ResourceSprite resourceSprite = new ResourceSprite(resCfgData.getMAX(), rlevel, cfgData.getRange(), EnumUtils.getEnum(CurrencyEnum.class, type.ordinal()));
							mapStatics.addziyuan(rlevel);
							
							// 清空之前格子信息
							SpriteInfo spriteInfo = SpriteInfo.valueOf(InjectorUtil.getInjector().idSequance.genId(), y * xGridNum +x, SpriteType.RESOURCE, resourceSprite);
							
							// 转换地图格子
							transferGridToSprite(x, y, spriteInfo, true, false);
							return spriteInfo;
						}
					}
					break;
				}
			}
		}
		return null;
	}
	
	/**
	 * 添加到可用列表当中
	 * @param x
	 * @param y
	 * @param gridInfo
	 */
	public void addCanUse(int x, int y) {
		SpriteInfo spriteInfo = gridSpriteInfo[x][y];
		canUseGrid.put(spriteInfo.getIndex(), spriteInfo);	
	}
	
	/**
	 * 删除可用
	 * @param spriteInfo
	 */
	public void removeCanUse(int index) {
		canUseGrid.remove(index);
	}
	
	/**
	 * 移除可用节点
	 * @param x
	 * @param y
	 */
	public void removeCanUse(int x, int y) {
		canUseGrid.remove(y * xGridNum + x);
	}
	
	/**
	 * 获取可用的格子信息
	 * @param index
	 * @return
	 */
	public SpriteInfo getCanUseGrid(int index) {
		return canUseGrid.get(index);
	}
	
	public SpriteInfo getCanUseGrid(int x, int y) {
		return canUseGrid.get(y * xGridNum + x);
	}
	
	public SpriteInfo getGrid(int x, int y) {
		return gridSpriteInfo[x][y];
	}
	
	/**
	 * 获取当前格子上有
	 * @param x
	 * @param y
	 * @return
	 */
	public SpriteInfo getVisibleGrid(int x, int y) {
		SpriteInfo spriteInfo = gridSpriteInfo[x][y];
		if(spriteInfo != null && spriteInfo.canMove()) {
			return spriteInfo;
		}
		return null;
	}
	
	/**
	 * 获取当前格子上有
	 * @param x
	 * @param y
	 * @return
	 */
	public SpriteInfo getVisibleGrid(int index) {
		SpriteInfo spriteInfo = gridSpriteInfo[index % xGridNum][index / xGridNum];
		if(spriteInfo != null && spriteInfo.canMove()) {
			return spriteInfo;
		}
		return null;
	}
	
	public SpriteInfo getGrid(int index) {
		return gridSpriteInfo[index % xGridNum][index / xGridNum];
	}
	
	/**
	 * 能够使用的格子
	 * @param index
	 * @return
	 */
	public boolean canUseGrid(int index) {
		return canUseGrid.containsKey(index);
	}
	
	/**
	 * 生成障碍点
	 */
	public void setBlock(int x, int y) {
		SpriteInfo spriteInfo = gridSpriteInfo[x][y];
		spriteInfo.setSpriteType(SpriteType.BLOCK.getType());
		mapBlock.add(y * xGridNum + x);	
	}
	
	/**
	 * 回收精灵
	 * @param id
	 */
	public void recoverSprite(SpriteInfo spriteInfo) {
		recoverResource.add(spriteInfo.getId());
	}
	
	/**
	 * 建筑是否在障碍点上
	 * @param uid
	 * @param x
	 * @param y
	 * @param featureType
	 * @return true 表示障碍点 false 标识不是障碍点
	 */
	public boolean isObstacle(int x, int y, SpriteFeatureType featureType) {
		for(Vector2 vector2 : featureType.getRange(x, y)) {
			if(gridSpriteInfo[vector2.getX()][vector2.getY()].getEnumSpriteType() == SpriteType.BLOCK) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断一个正方形范围内的格子信息
	 * @param allianceId
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean checkRangeAlliance(long allianceId, int x, int y) { 
		for(int i = x=-1;i< x + 1;i++) {
			for(int j = y - 1;j< y+1;j++) {
				SpriteInfo spriteInfo = gridSpriteInfo[i][j];
				Object obj = spriteInfo.getParam();
				if(obj != null) {
					WorldSprite worldSprite = (WorldSprite)obj;
					if(worldSprite != null) {
						if(worldSprite.getAllianceId() == allianceId) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * 替换当前格子上的精灵
	 * @param x
	 * @param y
	 * @param newSpriteInfo
	 * @param use 需要使用格子 true 需要 false 不需要
	 */
	public void transferGridToSprite(int x, int y, SpriteInfo newSpriteInfo, boolean use, boolean update) {
		int index = y * xGridNum + x;
		SpriteInfo gridSprite = gridSpriteInfo[x][y];
		
		newSpriteInfo.setIndex(index);
		newSpriteInfo.setLandform(gridSprite.getLandform());
		newSpriteInfo.setTerrainConfigModel(gridSprite.getTerrainConfigModel());
		
		// 更新格子
		gridSpriteInfo[x][y] = newSpriteInfo;
		
		if(!use) {
			addCanUse(x, y);
		} else {
			removeCanUse(index);
		}
		
		if(update) {
			InjectorUtil.getInjector().dbCacheService.delete(gridSprite);
			InjectorUtil.getInjector().dbCacheService.create(newSpriteInfo);
		} else {
			this.remove(gridSprite);
			this.add(newSpriteInfo);
		}
	}
	
	@Override
	public Class<?> getProxyClass() {
		return SpriteInfo.class;
	}
	
	/**
	 * 获取出生点
	 * @param uid
	 * @return
	 */
	public SpriteInfo getPlayerBorn(long uid) {
		long time = globalManager.getMapGeneratorTime();
		List<int[]> bornList = GlobalPirFactory.getInstance().bornPosList;
		int changTime = GlobalPirFactory.getInstance().getInt(GlobalConstant.BORN_CHANG_POS_TIME);
		int timeCell = (int)((TimeUtils.getCurrentTimeMillis() - time) / (changTime * 60 * 1000));
		timeCell = timeCell % bornList.size();
		if(bornList != null && !bornList.isEmpty()) {
			int[] pos = bornList.get(timeCell);
			// 空格子
			for (int i = pos[0]; i >= 0; i--) {
				for (int j = pos[1]; j >= 0; j--) {
					if (this.canUseGrid(j * SpriteManager.xGridNum + i)) {
						return getGrid(i, j);
					}
				}
			}

			// 空格子
			for (int m = pos[0]; m <= 512; m++) {
				for (int n = pos[1]; n <= 512; n++) {
					if (canUseGrid(n * SpriteManager.xGridNum + m)) {
						return getGrid(m, n);
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * 空地扎营
	 * @param x
	 * @param y
	 * @param marchId
	 * @return
	 */
	public SpriteInfo createCampSprite(int x, int y, long marchId) {
		SpriteInfo spriteInfo = getCanUseGrid(x, y);
		if(spriteInfo != null) {
			WorldSprite worldSprite = new WorldSprite();
			
			// 创建精灵
			spriteInfo = createSpriteInfo(spriteInfo.getIndex(), SpriteType.CAMP, worldSprite);
			transferGridToSprite(x, y, spriteInfo, true, true);
			return spriteInfo;
		}
		return null;
	}
	
	/**
	 * 创建领地
	 * @param x
	 * @param y
	 * @param marchId
	 * @return
	 */
	public SpriteInfo createOccupySprite(int x, int y, long marchId) {
		SpriteInfo spriteInfo = getCanUseGrid(x, y);
		if(spriteInfo != null) {
			WorldSprite worldSprite = new WorldSprite();
			
			// 创建精灵
			spriteInfo = createSpriteInfo(spriteInfo.getIndex(), SpriteType.CAMP, worldSprite);
			transferGridToSprite(x, y, spriteInfo, true, true);
			return spriteInfo;
		}
		return null;
	}
	
	
	/**
	 * 获取当前屏幕精灵信息
	 * @param x
	 * @param y
	 * @return
	 */
	public List<SpriteInfo> getScreeeSpriteInfo(int x, int y) {
		List<SpriteInfo> spriteInfos = new ArrayList<SpriteInfo>();
		for (int i = x - WorldConstant.VIEW_GRID_SIZE / 2; i < x +  WorldConstant.VIEW_GRID_SIZE / 2; i++) {
			for (int j = y -  WorldConstant.VIEW_GRID_SIZE / 2; j < y +  WorldConstant.VIEW_GRID_SIZE / 2; j++) {
				
				if(i < 0 || j < 0 || i >= SpriteManager.xGridNum || j >= SpriteManager.yGridNum) {
					continue;
				}
				
				SpriteInfo gridInfo = getGrid(i, j);
				if(gridInfo != null) {
					WorldSprite worldSprite = gridInfo.getParam();
					if(worldSprite == null) {
						continue;
					}
					
					spriteInfos.add(gridInfo);
				}
			}
		}
		
		return spriteInfos;
	}
	
	
	/**
	 * 地图生成的不可走点
	 * @param index
	 * @return
	 */
	public boolean checkMapBlock(int index) {
		return mapBlock.contains(index);
	}
	

	public void setBattleSprite(Set<Integer> battleSprite) {
		this.battleSprite = battleSprite;
	}

	/**
	 * 刷新资源成空地
	 * @param resouceInfo
	 */
	public SpriteInfo explorerRefreshResource(SpriteInfo resouceInfo) {
		ResourceSprite resourceSprite = (ResourceSprite)resouceInfo.getParam();
		WorldSprite worldSprite = new WorldSprite();
		
		// 可以使用
		if(resourceSprite != null) {
			// 刷新资源信息
			worldSprite = resourceSprite.refresh();
		}
		
		// 刷新资源点
		this.recoverSprite(resouceInfo);
		
		// 世界精灵信息
		if(worldSprite != null) {
			SpriteInfo blankSprite = createSpriteInfo(resouceInfo.getIndex(), SpriteType.NONE, worldSprite);
			
			// 使用地图格子
			transferGridToSprite(resouceInfo.getX(), resouceInfo.getY(), blankSprite, false, true);
			InjectorUtil.getInjector().dbCacheService.create(blankSprite);
			return blankSprite;
		} else {
			// 空的精灵
			SpriteInfo blankSprite = createSpriteInfo(resouceInfo.getIndex(), SpriteType.NONE, worldSprite);
			
			// 使用地图格子
			transferGridToSprite(resouceInfo.getX(), resouceInfo.getY(), blankSprite, false, true);
			InjectorUtil.getInjector().dbCacheService.create(blankSprite);
			return blankSprite;
		}
	}
	
	/**
	 * 处理精灵异常信息
	 */
	public void dealExceptionSpriteInfo() {
		for(Integer targetId : battleSprite) {
			dealSingleExceptionSprite(targetId);
		}
	}
	
	/**
	 * 处理单个卡住的精灵
	 * @param targetId
	 */
	public void dealSingleExceptionSprite(int targetId) {
		SpriteInfo spriteInfo = getVisibleGrid(targetId);
		if(spriteInfo != null) {
			WorldSprite worldSprite = spriteInfo.getParam();
			if (worldSprite != null) {
				if(worldSprite.getAttackMarchId() > 0) {
					WorldMarch defendWorldMarch = InjectorUtil.getInjector().dbCacheService.get(WorldMarch.class, worldSprite.getAttackMarchId());
					if(defendWorldMarch == null) {
						worldSprite.setAttackMarchId(0);
					}
				}
				
				Queue<Long> marchBakQueue = new ConcurrentLinkedQueue<Long>();
				for(Long id : worldSprite.getBattleWaitQueue()) {
					WorldMarch defendWorldMarch = InjectorUtil.getInjector().dbCacheService.get(WorldMarch.class, id);
					if(defendWorldMarch != null) {
						marchBakQueue.add(id);
					}
				}
				
				worldSprite.setBattleWaitQueue(marchBakQueue);
				
				// 处理行军队列异常
				if(spriteInfo.getSpriteType() == SpriteType.PLAYER.getType() || spriteInfo.getSpriteType() == SpriteType.MONSTER.getType()) {
					// 进攻方部队id
					if(worldSprite.getAttackMarchId() <= 0) {
						if(worldSprite.getBattleWaitQueue().size() > 0) {
							long attackMarchId = worldSprite.getBattleWaitQueue().poll();
							worldSprite.setAttackMarchId(attackMarchId);
						}
					}
					
					if(worldSprite.getAttackMarchId() > 0) {
						WorldMarch attackMarch = InjectorUtil.getInjector().dbCacheService.get(WorldMarch.class, worldSprite.getAttackMarchId());
						attackMarch.getExecutor().initBattle();
					}
					
					// 更新精灵信息
					InjectorUtil.getInjector().dbCacheService.update(spriteInfo);
				} else {
					if (worldSprite.getOwnerMarchId() <= 0) {
						if(worldSprite.getBattleWaitQueue().size() > 0) {
							long ownerMarchId = worldSprite.getBattleWaitQueue().poll();
							worldSprite.setOwnerMarchId(ownerMarchId);
						}
					}
					
					// 进攻方部队id
					if(worldSprite.getAttackMarchId() <= 0) {
						if(worldSprite.getBattleWaitQueue().size() > 0) {
							long attackMarchId = worldSprite.getBattleWaitQueue().poll();
							worldSprite.setAttackMarchId(attackMarchId);
						}
					}
					
					if(worldSprite.getOwnerMarchId() > 0 && worldSprite.getAttackMarchId() > 0) {
						WorldMarch attackMarch = InjectorUtil.getInjector().dbCacheService.get(WorldMarch.class, worldSprite.getAttackMarchId());
						attackMarch.getExecutor().initBattle();
					}
					
					// 更新精灵信息
					InjectorUtil.getInjector().dbCacheService.update(spriteInfo);
				}
			}
		}
	}
}


/**
 * 统计数据
 * @author jacky.jiang
 *
 */
class MapStatics {
	// 资源数量
	public int ziyuan_lv1;
	public int ziyuan_lv2;
	public int ziyuan_lv3;
	public int ziyuan_lv4;
	public int ziyuan_lv5;
	public int ziyuan_lv6;
	public int ziyuan_lv7;
	
	// 空地
	public int kongdi;
	// 不可走
	public int bukezou;
	
	public void addziyuan(int level) {
		if (level == 1) {
			addZiyuan_lv1();
		} else if (level == 2) {
			addZiyuan_lv2();
		} else if (level == 3) {
			addZiyuan_lv3();
		} else if (level == 4) {
			addZiyuan_lv4();
		} else if (level == 5) {
			addZiyuan_lv5();
		} else if (level == 6) {
			addZiyuan_lv6();
		} else if (level == 7) {
			addZiyuan_lv7();
		}
	}

	public void addZiyuan_lv1() {
		ziyuan_lv1 += 1;
	}
	
	public void addZiyuan_lv2() {
		ziyuan_lv2 += 1;
	}
	
	public void addZiyuan_lv3() {
		ziyuan_lv3 += 1;
	}
	
	public void addZiyuan_lv4() {
		ziyuan_lv4 += 1;
	}
	
	public void addZiyuan_lv5() {
		ziyuan_lv5 += 1;
	}
	
	public void addZiyuan_lv6() {
		ziyuan_lv6 += 1;
	}
	
	public void addZiyuan_lv7() {
		ziyuan_lv7 += 1;
	}

	public void addKongdi() {
		kongdi += 1;
	}

	public void addBukezou() {
		bukezou += 1;
	}

	@Override
	public String toString() {
		return "MapStatics [ziyuan_lv1=" + ziyuan_lv1 + ", ziyuan_lv2="
				+ ziyuan_lv2 + ", ziyuan_lv3=" + ziyuan_lv3
				+ ", ziyuan_lv4=" + ziyuan_lv4 + ", ziyuan_lv5="
				+ ziyuan_lv5 + ", ziyuan_lv6=" + ziyuan_lv6
				+ ", ziyuan_lv7=" + ziyuan_lv7 + ", kongdi=" + kongdi
				+ ", bukezou=" + bukezou + "]";
	}
}
