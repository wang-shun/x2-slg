package com.xgame.config.shop;

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
 * @date 2016-12-06 16:02:45 
 */
public class ShopPirFactory extends BasePriFactory<ShopPir>{
	
	public Map<Integer, List<ShopPir>> batch1Map = new HashMap<Integer, List<ShopPir>>();
	public Map<Integer, Integer> batch1Time = new HashMap<>();
	
	public Map<Integer, List<ShopPir>> batch2Map = new HashMap<>();
	public Map<Integer, Integer> batch2Time = new HashMap<>();

	public void init(ShopPir pir) {
		List<ItemConf> list = Lists.newArrayList();
		pir.item_id = list;
		
		List<Integer> list2 = Lists.newArrayList();
		pir.special_number = list2;
		
		list2 = Lists.newArrayList();
		pir.special_number2 = list2;
	}
	
	@Override
	public void load(ShopPir pir) {
		// 物品分为两批次，N个档，第一批次是开服用的，第二批次是第一批次所有结束之后用第二批次循环
		// 每个批次里面都有每个档的结束时间
		for(java.util.Map.Entry<Integer, List<ShopPir>> batchEntry : batch1Map.entrySet()) {
			int time = 0;
			List<ShopPir> shopList = batchEntry.getValue();
			if(shopList != null) {
				for(ShopPir shopPir : shopList) {
					if(time < shopPir.time) {
						time = shopPir.time;
					}
				}
				batch1Time.put(batchEntry.getKey(), time);
			}
		}

		for(java.util.Map.Entry<Integer, List<ShopPir>> batchEntry : batch2Map.entrySet()) {
			int time = 0;
			List<ShopPir> shopList = batchEntry.getValue();
			if(shopList != null) {
				for(ShopPir shopPir : shopList) {
					if(time < shopPir.time) {
						time = shopPir.time;
					}
				}
				batch2Time.put(batchEntry.getKey(), time);
			}
		}
	}
	
	/**
	 *自定义解析  item_id
	 */
	@ConfParse("item_id")
	public void item_idPares(String conf,ShopPir pir){
		List<ItemConf> list = pir.getItem_id();
		String[] itemsArr = conf.split(";");
		for(String itemCfg : itemsArr) {
			String[] itemStr = itemCfg.split("_");
			list.add(new ItemConf(Integer.valueOf(itemStr[0]), Integer.valueOf(itemStr[1])));
		}
	}
	
	/**
	 *自定义解析  special_number
	 */
	@ConfParse("special_number")
	public void special_numberPares(String conf,ShopPir pir){
		List<Integer> list = pir.getSpecial_number();
		for(String d : conf.split(";")) {
			list.add(Integer.valueOf(d));
		}
		addBatch(batch1Map, pir, list);
	}
	
	/**
	 *自定义解析  special_number2
	 */
	@ConfParse("special_number2")
	public void special_number2Pares(String conf,ShopPir pir){
		List<Integer> list = pir.getSpecial_number2();
		for(String d : conf.split(";")) {
			list.add(Integer.valueOf(d));
		}
		addBatch(batch2Map, pir, list);
	}
	
	/**
	 * 添加
	 * @param model
	 * @param batchNums
	 */
	public void addBatch(Map<Integer, List<ShopPir>> batchMap, ShopPir model, List<Integer> batchNums) {
		for (int batch : batchNums) {
			List<ShopPir> list = batchMap.get(batch);
			if (list == null) {
				list = new ArrayList<>();
				list.add(model);
				batchMap.put(batch, list);
			} else {
				list.add(model);
			}
		}
	}
	
	/**
	 * 获取批次信息
	 * @param grade
	 * @param batch
	 * @return
	 */
	public List<ShopPir> getBatchList(int phase, int batch) {
		if(phase <= 1) {
			List<ShopPir> shopPirsList = batch1Map.get(batch);
			return shopPirsList;
		} else {
			List<ShopPir> shopPirs = batch2Map.get(batch);
			return shopPirs;
		}
	}
	
	@Override
	public ShopPir newPri() {
		return new ShopPir();
	}
	
	public static ShopPir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final ShopPirFactory instance = new ShopPirFactory(); 
	
	
	public static ShopPirFactory getInstance() {
		return instance;
	}
}