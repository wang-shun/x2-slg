package com.xgame.logic.server.game.world;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.xgame.config.util.RandomSeed;
import com.xgame.config.ziYuanShengCheng.ZiYuanShengChengPir;
import com.xgame.logic.server.game.world.constant.ResourceType;
import com.xgame.logic.server.game.world.entity.model.TerrainConfigModel;
import com.xgame.utils.Probability;


/**
 * 地表信息管理
 * @author jacky.jiang
 *
 */
@Component
public class TerrianManager {
	
	/**
	 * 地表列表
	 */
	private Map<Integer, Map<Integer, TerrainConfigModel>> terrianMap = new HashMap<Integer, Map<Integer, TerrainConfigModel>>();

	
	/**
	 * 创建地表信息
	 * @param x
	 * @param y
	 * @param cfgData
	 * @return
	 */
	public TerrainConfigModel createTerrianData(int x, int y,ZiYuanShengChengPir cfgData) {
		TerrainConfigModel terrain = new TerrainConfigModel();
		terrain.setIsFightZone(cfgData.getJunShi());
		terrain.setBattleFailRandomTransfer(cfgData.getChuanSong());
		terrain.setTerrain(this.getTerrain(cfgData));
		return terrain;
	}
	
	/**
	 * 添加地表信息
	 * @param x
	 * @param y
	 * @param terrain
	 */
	public void addTerrainData(int x, int y, TerrainConfigModel terrain) {
		Map<Integer, TerrainConfigModel> yTerrian = terrianMap.get(x);
		if(yTerrian == null) {
			yTerrian = new HashMap<>();
			terrianMap.put(x, yTerrian);
		} 
		yTerrian.put(y, terrain);
	}
	
	/**
	 * 获取地表
	 * @param cfgData
	 * @return
	 */
	public int getTerrain(ZiYuanShengChengPir cfgData) {
		int kdStartRate = 1;
		List<RandomSeed> kdList = makeSortListData(
				new int[] { ResourceType.RES_PY, ResourceType.RES_QL, ResourceType.RES_SD, ResourceType.RES_CL, ResourceType.RES_ZZ },
				new int[] { cfgData.getPingYuan(), cfgData.getQiuLing(), cfgData.getShanDi(), cfgData.getCongLin(), cfgData.getZhaoZe() });
		int kdTotalMaxRate = 0;
		for (int i = 0; i < kdList.size(); i++) {
			kdTotalMaxRate += kdList.get(i).rate;
		}
		
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
		
		int rKdNum = Probability.randomNum(1, kdTotalMaxRate);
		for (int i = 0; i < kdList.size(); i++) {
			if (rKdNum >= kdList.get(i).startRate && rKdNum <= kdList.get(i).rate) {
				return kdList.get(i).data;
			}
		}
		return -1;
	}
	
	/**
	 * 生成
	 * @param levels
	 * @param rates
	 * @return
	 */
	private List<RandomSeed> makeSortListData(int[] levels, int[] rates) {
		List<RandomSeed> rsList = new ArrayList<RandomSeed>();
		for (int i = 0; i < levels.length; i++) {
			RandomSeed mrr = new RandomSeed();
			mrr.data = levels[i];
			mrr.rate = rates[i];
			rsList.add(mrr);
		}
		return rsList;
	}
}
