package com.xgame.config.building;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
import com.xgame.config.attribute.AttributeConfMap;
import com.xgame.config.attribute.AttributeParser;
import com.xgame.config.common.AwardConfList;
import com.xgame.config.common.BuildCondtitionBean;
import com.xgame.config.common.CampV2;
import com.xgame.config.common.ItemConf;
import com.xgame.config.common.Material;
import com.xgame.config.util.ConfigUtil;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2016-12-06 10:25:09 
 */
public class BuildingPirFactory extends BasePriFactory<BuildingPir>{
	

	public void init(BuildingPir pir) {
		pir.main_num = new HashMap<Integer, Integer>();
		pir.require_id = new HashMap<Integer, BuildCondtitionBean>();
		pir.cost_cash = new HashMap<Integer, Integer>();
		pir.cost_earth = new HashMap<Integer, Integer>();
		pir.cost_oil = new HashMap<Integer, Integer>();
		pir.cost_steel = new HashMap<Integer, Integer>();
		pir.cost_time = new HashMap<Integer, Integer>();
		pir.cost_item = new HashMap<Integer, ItemConf>();
		pir.cost_type = new Material[0];
		pir.exp = new HashMap<Integer, AwardConfList>();
		pir.clearance = new ArrayList<ItemConf>();
		pir.attr = new AttributeConfMap(pir.id);
		pir.strength = new HashMap<Integer, Integer>();
		if(pir.id==1108||pir.id==1102||pir.id==1104||pir.id==1200) {
			pir.v1 = new AttributeConfMap(pir.id);
		} else if(pir.id == 1201||pir.id == 1202||pir.id == 1203||pir.id == 1204) {
			pir.v1 = new HashMap<Integer, Integer>();
		} else if(pir.id==1110) {//"float;float;float"
			pir.v1 = new HashMap<Integer, Float>();
		} else if (pir.id == 1405) {
			pir.v1 = new HashMap<Integer, Integer>();
		} else if (pir.id == 1406) {
			pir.v1 = new HashMap<Integer, Map<Integer, Float>>();
		} else if(pir.id == 1200) {
			pir.v1 = new HashMap<Integer, Integer>();
		} else if(pir.id == 1300) {
			pir.v1 = new HashMap<Integer, Integer>();
		} else if(pir.id >= 1401&&pir.id <= 1404) {
			pir.v1 = new HashMap<Integer, Integer>();
		} else if(pir.id == 1104) {
			pir.v1 = new int[30];
		}
		
		if(pir.id==1100){
			pir.v2 = new AttributeConfMap(pir.id);
			pir.v1 = new HashMap<Integer, Integer>();
		} else if(pir.id == 1201||pir.id == 1202||pir.id == 1203){
			pir.v2 = new HashMap<Integer, CampV2>();
		} else if(pir.id == 1110||pir.id == 1204){
			pir.v2 = new HashMap<Integer, Integer>();
		} else if(pir.id == 1405) {
			pir.v2 = new HashMap<Integer, Integer>();
		} 
	}
	
	@Override
	public void load(BuildingPir pir) {
		
	}
	
	
	/**
	 *自定义解析  main_num
	 */
	@ConfParse("main_num")
	public void main_numPares(String conf,BuildingPir pir){
		ConfigUtil.parseArrGreaterZero(conf, pir.getMain_num());
	}
	
	/**
	 *自定义解析  require_id
	 */
	@ConfParse("require_id")
	public void require_idPares(String conf,BuildingPir pir){
		Map<Integer, BuildCondtitionBean> condtition = pir.getRequire_id();
		String[] split = conf.split(";");
		// "0" 代表没有前置建筑条件
		if(split[0].contains("0")) {
			return;
		}
		// 代表有前置建筑条件
		for (int i = 0; i < split.length; i++) {
			String[] split2 = split[i].split(",");
			condtition.put(i+1, new BuildCondtitionBean(split2[i]));
		}
	}
	
	/**
	 *自定义解析  cost_cash
	 */
	@ConfParse("cost_cash")
	public void cost_cashPares(String conf,BuildingPir pir){
		ConfigUtil.parseArrGreaterZero(conf, pir.getCost_cash());
	}
	
	/**
	 *自定义解析  cost_earth
	 */
	@ConfParse("cost_earth")
	public void cost_earthPares(String conf,BuildingPir pir){
		ConfigUtil.parseArrGreaterZero(conf, pir.getCost_earth());
	}
	
	/**
	 *自定义解析  cost_steel
	 */
	@ConfParse("cost_steel")
	public void cost_steelPares(String conf,BuildingPir pir){
		ConfigUtil.parseArrGreaterZero(conf, pir.getCost_steel());
	}
	
	/**
	 *自定义解析  cost_oil
	 */
	@ConfParse("cost_oil")
	public void cost_oilPares(String conf,BuildingPir pir){
		ConfigUtil.parseArrGreaterZero(conf, pir.getCost_oil());
	}
	
	/**
	 *自定义解析  cost_time
	 */
	@ConfParse("cost_time")
	public void cost_timePares(String conf,BuildingPir pir){
		ConfigUtil.parseArrGreaterZero(conf, pir.getCost_time());
	}
	
	/**
	 *自定义解析  cost_item
	 */
	@ConfParse("cost_item")
	public void cost_itemPares(String conf,BuildingPir pir){
		Map<Integer, ItemConf> costItemMap = pir.getCost_item();
		String[] split = conf.split(";");
		if(split != null) {
			for(String s : split) {
				String[] build = s.split(",");
				if(build != null) {
					String[] t_build = build[1].split("_");
					ItemConf itemConf = new ItemConf(Integer.valueOf(t_build[0]), Integer.valueOf(t_build[1]));
					costItemMap.put(Integer.valueOf(build[0]), itemConf);
				}
			}
		}
	}
	
	
	/**
	 *自定义解析  cost_type
	 */
	@ConfParse("cost_type")
	public void cost_typePares(String conf,BuildingPir pir){
		String[] split = ((String)conf).split(";");
		Material [] cost_types = new Material[split.length];
		for(int i=0;i<split.length;i++){
			String[] split2 = split[i].split(",");
			cost_types[i] = new Material(Integer.parseInt(split2[0]),Integer.parseInt(split2[1]));
		}
		pir.cost_type = cost_types;
	}
	
	
	/**
	 *自定义解析  v1
	 */
	@ConfParse("v1")
	public void v1Pares(String conf, BuildingPir pir) {
		if(pir.id==1108) {
			AttributeParser.parse(conf, pir.getV1());
		} else if(pir.id == 1201||pir.id == 1202||pir.id == 1203||pir.id == 1204) {
			Map<Integer, Integer> map = pir.getV1();
			String[] split = conf.split(";");
			for(int i=0;i<split.length;i++){
				map.put(i+1, Integer.parseInt(split[i]));
			}
		} else if(pir.id==1110) {//"float;float;float"
			Map<Integer, Float> map = pir.getV1();
			String[] split = conf.split(";");
			for(int i=0;i<split.length;i++){
				map.put(i+1, Float.parseFloat(split[i]));
			}
		} else if (pir.id == 1405) {
			Map<Integer, Integer> map = pir.getV1();
			String[] split = conf.split(";");
			for(int i=0;i<split.length;i++){
				map.put(i+1, Integer.valueOf(split[i]));
			}
		} else if (pir.id == 1406) {
			// 矿车对某一种资源 
			//  每一级每秒产出资源数量
			Map<Integer, Map<Integer, Float>> map = pir.getV1();
			String[] splits = conf.split(";");
			for(String str : splits) {
				String[] outputStr = str.split(":")[1].split(",");
				Map<Integer, Float> outputMap = new HashMap<Integer, Float>();
				int i = 1;
				for(String subStr : outputStr) {
					outputMap.put(i, Float.valueOf(subStr));	
					i++;
				}
				map.put(Integer.valueOf(str.split(":")[0]), outputMap);
			}
		} else if(pir.id == 1200) {
			Map<Integer, Integer> map = pir.getV1();
			String[] split = conf.split(";");
			for(int i=0;i<split.length;i++) {
				map.put(i+1, Integer.valueOf(split[i]));
			}
		} else if(pir.id == 1300) {
			Map<Integer, Integer> map = pir.getV1();
			String[] split = conf.split(";");
			for(int i=0;i<split.length;i++) {
				map.put(i+1, Integer.valueOf(split[i]));
			}
		} else if(pir.id >= 1401&&pir.id <= 1404) {
			ConfigUtil.parseArrGreaterZero(conf, pir.getV1());
		} else if(pir.id == 1100) {
			Map<Integer, Integer> map = pir.getV1();
			String[] data = conf.split(";");
			for (int i = 0; i < 30; i++) {
				map.put(i + 1, Integer.valueOf(data[i]));
			}
		} else if(pir.id == 1104) {
			AttributeConfMap attributeConfMap = new AttributeConfMap(pir.id);
			pir.v1 = attributeConfMap;
			AttributeParser.parse(conf, attributeConfMap);
		}
	}
	
	/**
	 *自定义解析  v2
	 */
	@ConfParse("v2")
	public void v2Pares(String conf,BuildingPir pir){
		if(pir.id==1100){
			AttributeConfMap confMap = pir.getV2();
			AttributeParser.parse(conf, confMap);
		}
		else if(pir.id == 1201||pir.id == 1202||pir.id == 1203){
			HashMap<Object, Object> map = pir.getV2();
			String[] split = conf.split(";");
			for(int i=0;i<split.length;i++) {
				String[] split2 = split[i].split(":");
				CampV2 campV2 = new CampV2(Integer.parseInt(split2[0]),Integer.parseInt(split2[1]),i+1);
				map.put(campV2.getLevel(), campV2);
			}
		}else if(pir.id == 1110||pir.id == 1204){
			HashMap<Integer, Integer> map = pir.getV2();
			String[] split = conf.split(";");
			for(int i=0;i<split.length;i++){
				map.put(i+1, Integer.parseInt(split[i]));
			}
		} else if(pir.id == 1405) {
			HashMap<Integer, Integer> map = pir.getV2();
			String[] split = conf.split(";");
			for(int i=0;i<split.length;i++){
				map.put(i+1, Integer.parseInt(split[i]));
			}
		}
	}
	
	public int getFightPower(int sid,int level){
		BuildingPir buildingPir = factory.get(sid);
		if(buildingPir!=null){
			Map<Integer, Integer> map = buildingPir.getStrength();
			if(map!=null){
				return map.get(level);
			}
		}
		return 0;
	}
	
	/**
	 *自定义解析  strength
	 */
	@ConfParse("strength")
	public void strengthPares(String conf,BuildingPir pir){
		ConfigUtil.parseArrGreaterZero(conf, pir.getStrength());
	}
	
	/**
	 *自定义解析  exp
	 */
	@ConfParse("exp")
	public void expPares(String conf,BuildingPir pir){
		Map<Integer, AwardConfList> levelUpGiveLeaderExp= pir.getExp();
		int level;
		String[] split = conf.split(";");
		for(int i=0;i<split.length;i++){
			AwardConfList itemConfs = new AwardConfList();
			String[] split2 = split[i].split(",");
			level = Integer.parseInt(split2[0]);
			for(int j=1;j<split2.length;j++){
				String[] split3 = split2[j].split("_");
				itemConfs.add(new ItemConf(Integer.parseInt(split3[0]), Integer.parseInt(split3[1])));
			}
			levelUpGiveLeaderExp.put(level, itemConfs);
		} 
	}
	
	/**
	 *自定义解析  clearance
	 */
	@ConfParse("clearance")
	public void clearancePares(String conf,BuildingPir pir){
		List<ItemConf> clearAwardItems = pir.getClearance();
		String[] split = conf.split(";");
		for(int i=0;i<split.length;i++){
			String[] split2 = split[i].split("_");
			clearAwardItems.add(new ItemConf(Integer.parseInt(split2[0]), Integer.parseInt(split2[1])));
		}
	}
	
	/**
	 *自定义解析  attr
	 */
	@ConfParse("attr")
	public void attrPares(String conf,BuildingPir pir){
		AttributeParser.parse(conf, pir.getAttr());
	}
	
	/**
	 *自定义解析  function
	 */
	@ConfParse("function")
	public void functionPares(String conf,BuildingPir pir){
	
	}
	
	
	/**
	 *自定义解析  sound
	 */
	@ConfParse("sound")
	public void soundPares(String conf,BuildingPir pir){
	
	}
	
	
	/**
	 *自定义解析  info
	 */
	@ConfParse("info")
	public void infoPares(String conf,BuildingPir pir){
	
	}
	
	@Override
	public BuildingPir newPri() {
		return new BuildingPir();
	}
	
	public static BuildingPir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final BuildingPirFactory instance = new BuildingPirFactory(); 
	
	
	public static BuildingPirFactory getInstance() {
		return instance;
	}
}