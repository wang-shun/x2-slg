package com.xgame.logic.server.game.bag.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.xgame.common.ItemConf;


/**
 * 奖励上下文
 * @author jacky.jiang
 *
 */
public class ItemContext {
	
	/**
	 * 数值变更
	 * 
	 * <ul>资源对应id</ul>
	 * <ul>对应变更数量</ul>
	 */
	private Map<Integer, Long> updateCurrency = new HashMap<Integer, Long>();
	
	/**
	 * 变更的道具数量
	 */
	private List<ItemConf> updateItemList = new ArrayList<ItemConf>();
	
	/**
	 * 道具最终数量信息
	 */
	private List<Item> finalItemList = new ArrayList<>();
	
	
	public ItemContext() {
		
	}
	
	/**
	 * 添加资源
	 * @param id
	 * @param value
	 */
	public void addCurrency(int id, long value) {
		Long before = updateCurrency.get(id);
		if(before != null) {
			before += value;
			updateCurrency.put(id, before);
		} else {
			updateCurrency.put(id, value);
		}
	}
	
	/**
	 * 添加配置
	 * @param itemId
	 * @param num
	 * @param itemList
	 */
	public void addItemConf(int itemId, int num, List<Item> itemList) {
		ItemConf itemConf = new ItemConf(itemId, num);
		this.updateItemList.add(itemConf);
		this.finalItemList.addAll(itemList);
	}
	
	/**
	 * 更新
	 * @param update
	 */
	 private void updateCurrency(Map<Integer, Long> update) {
		if(update != null) {
			for(Entry<Integer, Long> entry : update.entrySet()) {
				addCurrency(entry.getKey(), entry.getValue());
			}
		}
	}
	
	/**
	 * 合并
	 * @param rewardContext
	 */
	public void combine(ItemContext rewardContext) {
		updateCurrency(rewardContext.updateCurrency);
		updateItemList.addAll(rewardContext.updateItemList);
		finalItemList.addAll(rewardContext.finalItemList);
	}
	
	/**
	 * 增加道具信息
	 * @param updateList
	 * @param itemList
	 */
	public void addItemList(List<ItemConf> updateList, List<Item> itemList) {
		updateItemList.addAll(updateList);
		finalItemList.addAll(itemList);
	}

	public List<Item> getFinalItemList() {
		return finalItemList;
	}

	public void setFinalItemList(List<Item> finalItemList) {
		this.finalItemList = finalItemList;
	}

	public Map<Integer, Long> getUpdateCurrency() {
		return updateCurrency;
	}

	public void setUpdateCurrency(Map<Integer, Long> updateCurrency) {
		this.updateCurrency = updateCurrency;
	}

	public List<ItemConf> getUpdateItemList() {
		return updateItemList;
	}

	public void setUpdateItemList(List<ItemConf> updateItemList) {
		this.updateItemList = updateItemList;
	}
}
