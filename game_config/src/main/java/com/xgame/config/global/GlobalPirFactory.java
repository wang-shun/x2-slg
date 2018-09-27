package com.xgame.config.global;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;

import com.xgame.common.AwardConfList;
import com.xgame.common.ItemConf;
import com.xgame.common.RefreshBuildConfig;
import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2016-12-06 10:25:09 
 */
@Slf4j
public class GlobalPirFactory extends BasePriFactory<GlobalPir>{
	
	
	public final Map<Integer, int[]> attributeGroup = new HashMap<>();
	
	
	/**
	 * 国家模版数量
	 */
	public final Map<Integer, Integer> countryNums = new HashMap<>();
	
	/**
	 * 刷新自然物体配置
	 */
	public  List<RefreshBuildConfig[]>	refreshBuilds = new ArrayList<RefreshBuildConfig[]>();
	
	/**
	 * 刷新自然物体配置
	 */
	public Map<Integer, CurrencyChangeConf>	currencyChangeConfs = new HashMap<>();
	
	/**
	 * 加速
	 */
	public Map<Integer, CurrencyChangeConf>	speedChangeConfs = new HashMap<>();
	
	/**
	 * 统帅卡来源补偿概率
	 */
	public Map<String, Float>	marchingTroops = new HashMap<>();
	
	/**
	 * 矿车资源开放等级
	 * <li>资源</li>
	 * <li>开放等级</li>
	 */
	public Map<Integer, Integer> mineCarResourceOpen = new HashMap<>(); 
	
	public Map<Integer, Integer> createAllianceBuild = new HashMap<>();
	
	public Map<Integer, Integer> costResource = new HashMap<>();
	
	/**
	 * 出生点x, y
	 */
	public List<int[]> bornPosList = new ArrayList<int[]>();  
	
	public int[] composeEquipmentQuality;
	
	public int[] scout;
	
	public int[] jia_ren_ratio;
	
	public double[] occupy_ratio;
	
	public AwardConfList firstJoinRewards = new AwardConfList();
	
	public int[] help_donate = new int[2];
	
	// 新手传送 定点传送 军团传送 随机传送
	public int[] moveCityItem = new int[4];
	
	// 玩家初始数值（1石油,2稀土,3钢材,4黄金,5钻石,6经验,7体力
	public Map<Integer, Integer> initPlayerValue = new HashMap<>();
	
	// 初始出征队列数  初始建筑队列数  初始科研队列
	public int[] initQueueSize;
	
	public void init(GlobalPir pir) {
		if(pir.type == 8){
			pir.value = new HashMap<Integer,Double>();
		}
	}
	
	@Override
	public void load(GlobalPir pir) {
		
	}
	
	/**
	 *自定义解析  value
	 */
	@ConfParse("value")
	public void valuePares(String conf,GlobalPir pir){
		try {
			if(pir.type == 9||pir.type == 10||pir.type == 11){
				String[] split = conf.split(";");
				int id = Integer.parseInt(split[0]);
				int [] arr = new int[split.length-1];
				for(int i=1;i<split.length;i++){
					arr[i-1] = Integer.parseInt(split[i]);
				}
				attributeGroup.put(id, arr);
			}
			else if(pir.type == 6){
				String[] split = conf.split(";");
				for (int i = 0; i < split.length; i++) {
					String[] split2 = split[i].split("_");
					countryNums.put(Integer.parseInt(split2[1]), Integer.parseInt(split2[0]));
				}
			}
			else if(pir.type == 4){
				String[] split = conf.split(";");
				for (int i = 0; i < split.length; i++) {
					String[] split2 = split[i].split(":");
					RefreshBuildConfig [] configs = new RefreshBuildConfig[split2.length];
					for(int j=0;j<split2.length;j++){
						RefreshBuildConfig build = new RefreshBuildConfig();
						String[] split3 = split2[j].split(",");
						build.setId(Integer.parseInt(split3[0]));
						build.setArrys(new int[split3.length-1][2]);
						for(int n = 1;n<split3.length;n++){
							String[] split4 = split3[n].split("_");
							build.getArrys()[n-1][0] = Integer.parseInt(split4[0]);
							build.getArrys()[n-1][1] = Integer.parseInt(split4[1]);
						}
						configs[j] = build;
					}
					refreshBuilds.add(configs);
				}
			}
			else if(pir.type == 8) {
				Map<Integer, Double> map = pir.getValue();
				String[] split = conf.split(";");
				for(int i=0;i<split.length;i++){
					String[] split2 = split[i].split(":");
					map.put(Integer.parseInt(split2[0]), Double.parseDouble(split2[1]));
				}
			} else if(pir.type >= 500001&&pir.type <= 500025) {
				String[] split2 = conf.split(";");
				int [] systemSoldiers = new int[split2.length-3];
				for(int j=2;j<split2.length-1;j++){
					systemSoldiers[j-2]=Integer.parseInt(split2[j]);
				}
				pir.value = systemSoldiers;
			} else if (pir.type >= 500501 && pir.type <= 500506) {
				speedChangeConfs.put(pir.type, new CurrencyChangeConf(conf));
			} else if (pir.type >= 500521 && pir.type <= 500527) {
				currencyChangeConfs.put(pir.type, new CurrencyChangeConf(conf));
			} else if (pir.type == 500720) {
				HashSet<Integer>commanderStyles = new HashSet<>();
				String[] split = conf.split(";");
				for(int i=0;i<split.length;i++){
					commanderStyles.add(Integer.parseInt(split[i]));
				}
				pir.value = commanderStyles;
			}else if(pir.type == 501004||pir.type == 501005 || pir.type == 501006 || pir.type == 501003){
				String[] split = conf.split(";");
				int [] arr = new int[2];
				arr[0] = Integer.parseInt(split[0]);
				arr[1] = Integer.parseInt(split[1]);
				pir.value = arr;
			}else if(pir.type >= 501101&&pir.type <= 501104){
				String[] split = conf.split(";");
				marchingTroops.put(split[0], Float.parseFloat(split[1]));
			}else if(pir.type == 501001){
				pir.value = conf;
			}else if(pir.type == 21){
				String[] data = conf.split(";");
				// 建筑依赖
				if(!StringUtils.isBlank(data[0])) {
					String[] buildData = data[0].split(",");
					for(String build : buildData) {
						createAllianceBuild.put(Integer.valueOf(build.split("_")[0]), Integer.valueOf(build.split("_")[1]));
					}
				}
				
				// 资源消耗
				if(!StringUtils.isBlank(data[1])) {
					String[] resourceData = data[1].split(",");
					for(String resource : resourceData) {
						costResource.put(Integer.valueOf(resource.split("_")[0]), Integer.valueOf(resource.split("_")[1]));
					}
				}
				
			} else if(pir.type == 500050) {
				String[] data = conf.split(";");
				composeEquipmentQuality = new int[data.length];
				if (data != null && data.length > 0) {
					for (int i = 0; i < data.length; i++) {
						composeEquipmentQuality[i] = Integer.valueOf(data[i]);
					}
				}
			// 侦查参数	
			} else if(pir.type == 500101) {
				String[] data = conf.split(",");
				scout = new int[data.length];
				for(int i=0;i< data.length;i++) {
					scout[i] = Integer.valueOf(data[i]);
				}
			} else if(pir.type == 32) {
				String[] data = conf.split(";");
				if(data != null && data.length > 0) {
					for(String sub : data) {
						int[] pos = new int[2];
						pos[0] = Integer.valueOf(sub.split(",")[0]);
						pos[1] = Integer.valueOf(sub.split(",")[1]);
						bornPosList.add(pos);
					}
				}
			} else if(pir.type == 500722) {
				String[] data = conf.split(";");
				if(data != null && data.length > 0) {
					for(String sub : data) {
						mineCarResourceOpen.put(Integer.valueOf(sub.split(",")[0]), Integer.valueOf(sub.split(",")[1]));
					}
				}
			} else if(pir.type == 30) {
				jia_ren_ratio = new int[5];
				String[] data = conf.split(";");
				int i = 0;
				for(String index : data) {
					jia_ren_ratio[i] = Integer.valueOf(index);
					i++;
				}
			} else if(pir.type == 29) {
				occupy_ratio = new double[4];
				String[] data = conf.split(";");
				int i = 0;
				for(String index : data) {
					occupy_ratio[i] = Integer.valueOf(index);
					i++;
				}
			} else if(pir.type == 21) {
				String[] data = conf.split(";");
				// 建筑依赖
				if(!StringUtils.isBlank(data[0])) {
					String[] buildData = data[0].split(",");
					for(String build : buildData) {
						createAllianceBuild.put(Integer.valueOf(build.split("_")[0]), Integer.valueOf(build.split("_")[1]));
					}
				}
				
				// 资源消耗
				if(!StringUtils.isBlank(data[1])) {
					String[] resourceData = data[1].split(",");
					for(String resource : resourceData) {
						costResource.put(Integer.valueOf(resource.split("_")[0]), Integer.valueOf(resource.split("_")[1]));
					}
				}
				
			// 	
			} else if(pir.type == 34) {
				String[] data = conf.split(",");
				for(String column : data) {
					ItemConf itemConf = new ItemConf(Integer.valueOf(column.split("_")[0]), Integer.valueOf(column.split("_")[1]));
					firstJoinRewards.add(itemConf);
				}
			} else if(pir.type == 25) {
				String[] data = conf.split(";");
				help_donate[0] = Integer.valueOf(data[0]);
				help_donate[1] = Integer.valueOf(data[1]);
			} else if(pir.type == 27){
				String[] data = conf.split(";");
				for(int i=0;i<data.length;i++){
					moveCityItem[i] =Integer.valueOf(data[i]);
				}
			} else if(pir.type == 52) {
				String[] data = conf.split(";");
				for(String str : data) {
					initPlayerValue.put(Integer.valueOf(str.split(",")[0]), Integer.valueOf(str.split(",")[1]));
				}
			} else if(pir.type == 53) {
				String[] data = conf.split(";");
				int i = 0;
				initQueueSize = new int[data.length];
				for(String str : data) {
					initQueueSize[i] = Integer.valueOf(str);
				}
			}
		} catch(Exception e) {
			String s = String.format("gloabl value error, id:[%s], value:[%s]", pir.type, pir.value);
			log.error(s);
			throw new RuntimeException(s);
		}
		
	}
	
	/**
	 *自定义解析  desc
	 */
	@ConfParse("desc")
	public void descPares(String conf,GlobalPir pir){
	
	}
	
	@Override
	public GlobalPir newPri() {
		return new GlobalPir();
	}
	
	public static GlobalPir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final GlobalPirFactory instance = new GlobalPirFactory(); 
	
	
	public static GlobalPirFactory getInstance() {
		return instance;
	}
	
	
	public int getInt(int id) {
		GlobalPir globalPir = get(id);
		if(globalPir == null) {
			log.error("基础数据配置出错, id:[{}]", id);
			return 0;
		}
		
		return Integer.valueOf(globalPir.getValue());
	}
	
	public double getDouble(int id) {
		GlobalPir globalPir = get(id);
		if(globalPir == null) {
			log.error("基础数据配置出错, id:[{}]", id);
			return 0;
		}
		
		return Double.valueOf(globalPir.getValue());
	}
	
	public float getFloat(int id) {
		GlobalPir globalPir = get(id);
		if(globalPir == null) {
			log.error("基础数据配置出错, id:[{}]", id);
			return 0;
		}
		
		return Float.valueOf(globalPir.getValue());
	}
	
	public String getString(int id) {
		GlobalPir globalPir = get(id);
		if(globalPir == null) {
			log.error("基础数据配置出错,[{}]", id);
			return "";
		}
		
		return globalPir.getValue();
	}
	
	/**
	 * 获取资源信息
	 * @param level
	 * @return
	 */
	public List<Integer> getMineResourceOpen(int level) {
		List<Integer> list = new ArrayList<Integer>();
		for(Entry<Integer, Integer> entry : mineCarResourceOpen.entrySet()) {
			if(entry.getValue() <= level) {
				list.add(entry.getKey());
			}
		}
		return list;
	}
	
	public int getMineOpenLevel(int resourceType) {
		Integer level = mineCarResourceOpen.get(resourceType);
		if(level != null) {
			return level;
		}
		return 0;
	}
	
	public int getPlayerInitValue(int currencyType) {
		Integer currencyValue = this.initPlayerValue.get(currencyType);
		if(currencyValue != null) {
			return currencyValue;
		}
		return 0;
	}
}