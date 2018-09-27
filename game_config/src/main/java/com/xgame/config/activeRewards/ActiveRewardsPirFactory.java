package com.xgame.config.activeRewards;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.xgame.common.ItemConf;
import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2016-12-06 10:25:09 
 */
public class ActiveRewardsPirFactory extends BasePriFactory<ActiveRewardsPir>{
	
	public Map<Integer, List<ItemConf>> rewardsMap = new HashMap<Integer, List<ItemConf>>();
	
	public void init(ActiveRewardsPir pir) {
		
	}
	
	@Override
	public void load(ActiveRewardsPir pir) {
		
	}
	
	
	/**
	 *自定义解析  rewards_1
	 */
	@ConfParse("rewards_1")
	public void rewards_1Pares(String conf,ActiveRewardsPir pir){
		List<ItemConf> list = Lists.newArrayList();
		String[] itemsArr = conf.split(";");
		for(String itemCfg : itemsArr) {
			String[] itemStr = itemCfg.split("_");
			list.add(new ItemConf(Integer.valueOf(itemStr[0]), Integer.valueOf(itemStr[1])));
		}
		rewardsMap.put(rewardsMap.size()+1, list);
	}
	
	/**
	 *自定义解析  rewards_2
	 */
	@ConfParse("rewards_2")
	public void rewards_2Pares(String conf,ActiveRewardsPir pir){
		List<ItemConf> list = Lists.newArrayList();
		String[] itemsArr = conf.split(";");
		for(String itemCfg : itemsArr) {
			String[] itemStr = itemCfg.split("_");
			list.add(new ItemConf(Integer.valueOf(itemStr[0]), Integer.valueOf(itemStr[1])));
		}
		rewardsMap.put(rewardsMap.size()+1, list);
	}
	
	/**
	 *自定义解析  rewards_3
	 */
	@ConfParse("rewards_3")
	public void rewards_3Pares(String conf,ActiveRewardsPir pir){
		List<ItemConf> list = Lists.newArrayList();
		String[] itemsArr = conf.split(";");
		for(String itemCfg : itemsArr) {
			String[] itemStr = itemCfg.split("_");
			list.add(new ItemConf(Integer.valueOf(itemStr[0]), Integer.valueOf(itemStr[1])));
		}
		rewardsMap.put(rewardsMap.size()+1, list);
	}
	
	/**
	 *自定义解析  rewards_4
	 */
	@ConfParse("rewards_4")
	public void rewards_4Pares(String conf,ActiveRewardsPir pir){
		List<ItemConf> list = Lists.newArrayList();
		String[] itemsArr = conf.split(";");
		for(String itemCfg : itemsArr) {
			String[] itemStr = itemCfg.split("_");
			list.add(new ItemConf(Integer.valueOf(itemStr[0]), Integer.valueOf(itemStr[1])));
		}
		rewardsMap.put(rewardsMap.size()+1, list);
	}
	
	/**
	 *自定义解析  rewards_5
	 */
	@ConfParse("rewards_5")
	public void rewards_5Pares(String conf,ActiveRewardsPir pir){
		List<ItemConf> list = Lists.newArrayList();
		String[] itemsArr = conf.split(";");
		for(String itemCfg : itemsArr) {
			String[] itemStr = itemCfg.split("_");
			list.add(new ItemConf(Integer.valueOf(itemStr[0]), Integer.valueOf(itemStr[1])));
		}
		rewardsMap.put(rewardsMap.size()+1, list);
	}
	
	/**
	 *自定义解析  rewards_6
	 */
	@ConfParse("rewards_6")
	public void rewards_6Pares(String conf,ActiveRewardsPir pir){
		List<ItemConf> list = Lists.newArrayList();
		String[] itemsArr = conf.split(";");
		for(String itemCfg : itemsArr) {
			String[] itemStr = itemCfg.split("_");
			list.add(new ItemConf(Integer.valueOf(itemStr[0]), Integer.valueOf(itemStr[1])));
		}
		rewardsMap.put(rewardsMap.size()+1, list);
	}
	
	public List<ActiveRewardsPir> getByActive(int active){
		List<ActiveRewardsPir> list = new ArrayList<ActiveRewardsPir>();
		for(ActiveRewardsPir pir : instance.getFactory().values()){
			if(pir.getActive() <= active){
				list.add(pir);
			}
		}
		
		return list;
	}
	
	
	@Override
	public ActiveRewardsPir newPri() {
		return new ActiveRewardsPir();
	}
	
	public static ActiveRewardsPir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final ActiveRewardsPirFactory instance = new ActiveRewardsPirFactory(); 
	
	
	public static ActiveRewardsPirFactory getInstance() {
		return instance;
	}
}