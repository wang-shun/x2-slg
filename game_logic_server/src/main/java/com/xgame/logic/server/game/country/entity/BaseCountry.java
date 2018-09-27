package com.xgame.logic.server.game.country.entity;

import io.protostuff.Morph;
import io.protostuff.Tag;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;
import com.xgame.logic.server.core.utils.geometry.data.transform.BuildTransform;
import com.xgame.logic.server.game.country.structs.build.BuildFactory;


/**
 *家园持久化数据
 *2016-7-15  10:57:11
 *@author ye.yuan
 *
 */
public class BaseCountry implements Serializable, JBaseTransform {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 当前使用的模版id
	 */
	@Tag(1)
	private int useTemplateId;
	
	/**
	 * 几个家园模版
	 */
	@Tag(3)
	private Map<Integer, Map<Integer, BuildTransform>> buildTransforms = new HashMap<Integer, Map<Integer, BuildTransform>>();
	
	/**
	 * 以uid存储建筑物
	 */
	@Tag(5)
	private Map<Integer, CountryBuild> allBuild = new ConcurrentHashMap<>();
	
	/**
	 * 临时仓库
	 */
	@Tag(7)
	private MineRepository mineRepository = new MineRepository();
	
	/**
	 * 障碍物
	 */
	@Tag(8)
	@Morph(false)
	private Map<Integer, BlockBuild> blocks = new ConcurrentHashMap<>();
	
	/**
	 * 障碍物待刷新的集合
	 */
	@Tag(9)
	private List<BlockDeleteInfo> blockRefreshInfos = new ArrayList<>();

	public int getUseTemplateId() {
		return useTemplateId;
	}

	public void setUseTemplateId(int useTemplateId) {
		this.useTemplateId = useTemplateId;
	}

	public Map<Integer, CountryBuild> getAllBuild() {
		return allBuild;
	}

	public void setAllBuild(Map<Integer, CountryBuild> allBuild) {
		this.allBuild = allBuild;
	}

	public MineRepository getMineRepository() {
		return mineRepository;
	}

	public void setMineRepository(MineRepository mineRepository) {
		this.mineRepository = mineRepository;
	}

	public Map<Integer, BlockBuild> getBlocks() {
		return blocks;
	}

	public void setBlocks(Map<Integer, BlockBuild> blocks) {
		this.blocks = blocks;
	}

	public List<BlockDeleteInfo> getBlockRefreshInfos() {
		return blockRefreshInfos;
	}

	public void setBlockRefreshInfos(List<BlockDeleteInfo> blockRefreshInfos) {
		this.blockRefreshInfos = blockRefreshInfos;
	}

	public Map<Integer, Map<Integer, BuildTransform>> getBuildTransforms() {
		return buildTransforms;
	}

	public void setBuildTransforms(Map<Integer, Map<Integer, BuildTransform>> buildTransforms) {
		this.buildTransforms = buildTransforms;
	}
	
	public BuildTransform getBuildTransform(int tempId, int buildUid) {
		Map<Integer, BuildTransform> map = buildTransforms.get(tempId);
		if(map != null) {
			return map.get(buildUid);
		}
		
		return null;
	}
	
	public void addBuildTransform(int tempId, int buildUid, BuildTransform buildTransform) {
		Map<Integer, BuildTransform> map = buildTransforms.get(tempId);
		if(map == null) {
			map = new HashMap<>();
			buildTransforms.put(tempId, map);
		}
		map.put(buildUid, buildTransform);
	}
	
	public List<BuildTransform> getbuBuildTransforms() {
		List<BuildTransform> reBuildTransforms = new ArrayList<BuildTransform>();
		Collection<Map<Integer, BuildTransform>> collection = buildTransforms.values();
		if(collection != null) {
			for(Map<Integer, BuildTransform> map : collection) {
				reBuildTransforms.addAll(map.values());
			}
		}
		return reBuildTransforms;
	}
	
	public List<CountryBuild> getCountryBuildByTid(int tid) {
		List<CountryBuild> countryBuilds = new ArrayList<CountryBuild>();
		Collection<CountryBuild> collection = allBuild.values();
		if(collection != null) {
			for(CountryBuild countryBuild : collection) {
				if(countryBuild.getSid() == tid) {
					countryBuilds.add(countryBuild);
				}
			}
		}
		return countryBuilds;
	}
	
	public CountryBuild getCountryBuildByUid(int uid) {
		return allBuild.get(uid);
	}

	@Override
	public JBaseData toJBaseData() {
		JBaseData jBaseData = new JBaseData();
		jBaseData.put("useTemplateId", useTemplateId);
		
		// 建筑坐标
		List<JBaseData> buildTransformsList = new ArrayList<>();
		for(Map<Integer, BuildTransform> map : this.buildTransforms.values()) {
			for(BuildTransform buildTransform : map.values()) {
				buildTransformsList.add(buildTransform.toJBaseData());
			}
		}
		jBaseData.put("buildTransforms", buildTransformsList);
		
		// 建筑信息
		List<JBaseData> allBuildList = new ArrayList<>();
		for(CountryBuild countryBuild : this.allBuild.values()) {
			if(countryBuild.getSid() == BuildFactory.DEFEBSE_SOLDIER.getTid()) {
				DefebseSoldierBuild defebseSoldierBuild = (DefebseSoldierBuild)countryBuild;
				allBuildList.add(defebseSoldierBuild.toJBaseData());
			} else {
				allBuildList.add(countryBuild.toJBaseData());
			}
		}
		jBaseData.put("allBuild", allBuildList);
		
		JBaseData mineRepository = this.mineRepository.toJBaseData();
		jBaseData.put("mineRepository",mineRepository);
		
		List<JBaseData> jBaseBlock = new ArrayList<JBaseData>();
		for(BlockBuild blockBuild : this.blocks.values()) {
			jBaseBlock.add(blockBuild.toJBaseData());
		}
		jBaseData.put("blocks", jBaseBlock);
		
		List<JBaseData> jBaseDelete = new ArrayList<JBaseData>();
		for(BlockDeleteInfo blockDeleteInfo : this.blockRefreshInfos) {
			jBaseDelete.add(blockDeleteInfo.toJBaseData());
		}
		jBaseData.put("blockRefreshInfos", jBaseDelete);
		return jBaseData;
	}

	@Override
	public void fromJBaseData(JBaseData jBaseData) {
		this.useTemplateId = jBaseData.getInt("useTemplateId", 0);
		
		List<JBaseData> buildTransformsList = jBaseData.getSeqBaseData("buildTransforms");
		for(JBaseData transformJbaseData : buildTransformsList) {
			BuildTransform buildTransform = new BuildTransform();
			buildTransform.fromJBaseData(transformJbaseData);
			this.addBuildTransform(buildTransform.getTemplateId(), Long.valueOf(buildTransform.getUid()).intValue(), buildTransform);
		}
		
		List<JBaseData> allBuildJbase = jBaseData.getSeqBaseData("allBuild");
		for(JBaseData buildJase : allBuildJbase) {
			if(buildJase.getInt("sid" , 0) == BuildFactory.DEFEBSE_SOLDIER.getTid()) {
				DefebseSoldierBuild defebseSoldierBuild = new DefebseSoldierBuild();
				defebseSoldierBuild.fromJBaseData(buildJase);
				this.allBuild.put(defebseSoldierBuild.getUid(), defebseSoldierBuild);
			} else {
				CountryBuild countryBuild = new CountryBuild();
				countryBuild.fromJBaseData(buildJase);
				this.allBuild.put(countryBuild.getUid(), countryBuild);
			}
		}
		
		JBaseData mineRepositoryJbaseData = jBaseData.getBaseData("mineRepository");
		MineRepository mineRepository = new MineRepository();
		mineRepository.fromJBaseData(mineRepositoryJbaseData);
		this.mineRepository = mineRepository;
		
		List<JBaseData> blockJbaseDataList = jBaseData.getSeqBaseData("blocks");
		for(JBaseData blockJbaseData : blockJbaseDataList) {
			BlockBuild blockBuild = new BlockBuild();
			blockBuild.fromJBaseData(blockJbaseData);
			this.blocks.put(blockBuild.getUid(), blockBuild);
		}
		
		List<JBaseData> blockRefreshInfosList = jBaseData.getSeqBaseData("blockRefreshInfos");
		for(JBaseData blockRefresh : blockRefreshInfosList) {
			BlockDeleteInfo blockDeleteInfo = new BlockDeleteInfo();
			blockDeleteInfo.fromJBaseData(blockRefresh);
			this.blockRefreshInfos.add(blockDeleteInfo);
		}
	}
	
}
