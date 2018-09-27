package com.xgame.config.ziYuanShengCheng;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xgame.config.BasePriFactory;
import com.xgame.config.util.RandomSeed;
import com.xgame.gameconst.WorldResourceType;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2016-12-06 10:25:10 
 */
public class ZiYuanShengChengPirFactory extends BasePriFactory<ZiYuanShengChengPir>{
		
	//障碍点，不可走点
	public static final int BUKEZOU = 99;
	public static final int KONGDI = 0;
	public static final int RESOURCE_LV1 = 1;
	public static final int RESOURCE_LV2 = 2;
	public static final int RESOURCE_LV3 = 3;
	public static final int RESOURCE_LV4 = 4;
	public static final int RESOURCE_LV5 = 5;
	public static final int RESOURCE_LV6 = 6;
	public static final int RESOURCE_LV7 = 7;
	
	Map<Integer, Integer> resLevelMaxRates = new HashMap<>();
	
	Map<Integer, Integer>  resCategoryMaxRates = new HashMap<>();

	Map<Integer, List<RandomSeed>> resLevelLists = new HashMap<>();
	
	Map<Integer, List<RandomSeed>>  resCategoryLists = new HashMap<>();
	
	List<ZiYuanShengChengPir> factoryList = new ArrayList<>();
	
	List<Integer> rangeList = new ArrayList<Integer>();
	
	public void init(ZiYuanShengChengPir pir) {
		
	}
	
	@Override
	public void load(ZiYuanShengChengPir pir) {
		rangeList.add(pir.range);
		factoryList.add(pir);
		// 精灵权重
		List<RandomSeed> rsList = makeSortListData(
				new int[] {BUKEZOU, KONGDI, RESOURCE_LV1, RESOURCE_LV2, RESOURCE_LV3, RESOURCE_LV4, RESOURCE_LV5, RESOURCE_LV6, RESOURCE_LV7},
				new int[] {pir.buKeZou, pir.kongDi, pir.Lv1, pir.Lv2, pir.Lv3, pir.Lv4, pir.Lv5, pir.Lv6, pir.Lv7});
		resLevelLists.put(pir.range, rsList);

		Collections.sort(rsList, new Comparator<RandomSeed>() {
			public int compare(RandomSeed x, RandomSeed y) {
				if (x.rate == y.rate)
					return 0;
				else
					return x.rate < y.rate ? -1 : 1;
			}
		});
		
		// 计算区域总概率
		int totalMaxRate = 0;
		for (int i = 0; i < rsList.size(); i++) {
			totalMaxRate += rsList.get(i).rate;
		}
		
		resLevelMaxRates.put(pir.range, totalMaxRate);
		int startRate = 1;
		for (int i = 0; i < rsList.size(); i++) {
			RandomSeed rSeed = rsList.get(i);
			int tmpRate = rSeed.rate;
			rSeed.rate = rSeed.rate + startRate - 1;
			rSeed.startRate = startRate;
			startRate += tmpRate;
		}
		
		//资源等级权重
		List<RandomSeed> kdList = makeSortListData0(
				new WorldResourceType[] {WorldResourceType.MONEY, WorldResourceType.OIL, WorldResourceType.STEELS, WorldResourceType.RARE, WorldResourceType.DIAMOND},
				new int[] {pir.dianLi, pir.shiYou, pir.gangCai, pir.xiTu, pir.zuanShi });
		resCategoryLists.put(pir.range, kdList);
		
		int kdTotalMaxRate = 0;
		for (int i = 0; i < kdList.size(); i++) {
			kdTotalMaxRate += kdList.get(i).rate;
		}
		resCategoryMaxRates.put(pir.range, kdTotalMaxRate);
		
		int kdStartRate = 1;
		for (int i = 0; i < kdList.size(); i++) {
			RandomSeed rSeed = kdList.get(i);
			int tmpRate = rSeed.rate;
			rSeed.rate = rSeed.rate + kdStartRate - 1;
			rSeed.startRate = kdStartRate;
			kdStartRate += tmpRate;
		}
		
		Collections.sort(kdList, new Comparator<RandomSeed>() {
			public int compare(RandomSeed x, RandomSeed y) {
				if (x.rate == y.rate)
					return 0;
				else
					return x.rate < y.rate ? -1 : 1;
			}
		});
		
	}
	
	List<RandomSeed> makeSortListData0(WorldResourceType[] levels, int[] rates) {
		List<RandomSeed> rsList = new ArrayList<RandomSeed>();
		for (int i = 0; i < levels.length; i++) {
			RandomSeed mrr = new RandomSeed();
			mrr.spriteType = levels[i];
			mrr.data = levels[i].ordinal();
			mrr.rate = rates[i];
			rsList.add(mrr);
		}
		return rsList;
	}

	List<RandomSeed> makeSortListData(int[] levels, int[] rates) {
		List<RandomSeed> rsList = new ArrayList<RandomSeed>();
		for (int i = 0; i < levels.length; i++) {
			RandomSeed mrr = new RandomSeed();
			mrr.data = levels[i];
			mrr.rate = rates[i];
			rsList.add(mrr);
		}
		return rsList;
	}
	
	
	@Override
	public ZiYuanShengChengPir newPri() {
		return new ZiYuanShengChengPir();
	}
	
	public static ZiYuanShengChengPir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final ZiYuanShengChengPirFactory instance = new ZiYuanShengChengPirFactory(); 
	
	
	public static ZiYuanShengChengPirFactory getInstance() {
		return instance;
	}

	public Map<Integer, List<RandomSeed>> getResLevelLists() {
		return resLevelLists;
	}

	public void setResLevelLists(Map<Integer, List<RandomSeed>> resLevelLists) {
		this.resLevelLists = resLevelLists;
	}

	public Map<Integer, List<RandomSeed>> getResCategoryLists() {
		return resCategoryLists;
	}

	public void setResCategoryLists(Map<Integer, List<RandomSeed>> resCategoryLists) {
		this.resCategoryLists = resCategoryLists;
	}

	public List<ZiYuanShengChengPir> getFactoryList() {
		return factoryList;
	}

	public void setFactoryList(List<ZiYuanShengChengPir> factoryList) {
		this.factoryList = factoryList;
	}

	public Map<Integer, Integer> getResLevelMaxRates() {
		return resLevelMaxRates;
	}

	public void setResLevelMaxRates(Map<Integer, Integer> resLevelMaxRates) {
		this.resLevelMaxRates = resLevelMaxRates;
	}

	public Map<Integer, Integer> getResCategoryMaxRates() {
		return resCategoryMaxRates;
	}

	public void setResCategoryMaxRates(Map<Integer, Integer> resCategoryMaxRates) {
		this.resCategoryMaxRates = resCategoryMaxRates;
	}
	
	public List<Integer> getRangeList() {
		return rangeList;
	}
	
}