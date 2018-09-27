package com.xgame.logic.server.game.player.entity;

import io.protostuff.Tag;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.common.collect.Lists;
import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;
import com.xgame.logic.server.game.bag.entity.Item;
import com.xgame.logic.server.game.equipment.entity.Equipment;

/**
 * 背包
 * @author lmj
 *
 */
public class PlayerBag implements JBaseTransform, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2218927870042815321L;
	
	@Tag(1)
	private int max = 50; //最大容量
	
	@Tag(2)
	private Map<Long,Item> itemTable = new ConcurrentHashMap<>();	//道具背包列表
	
	@Tag(3)
	private Map<Long,Equipment> equipmentMap = new ConcurrentHashMap<>();	// 装备背包列表
	
	public Map<Long, Equipment> getEquipmentMap() {
		return equipmentMap;
	}
	
	public void setMax(int max) {
		this.max = max;
	}
	
	/**
	 * 查询玩家单个物品
	 * @param id
	 * @return
	 */
	public Item getPlayerItem(long id) {
		Item item = itemTable.get(id);
		return item;
	}
	
	/**
	 * 查询玩家某类物品
	 * @param itemId
	 * @return
	 */
	public List<Item> getPlayerItem(int itemId) {
		List<Item> list = Lists.newArrayList();
		for (Item item : getItemTable().values()) {
			if (item.getItemId() == itemId) {
				list.add(item);
			}
		}
		return list;
	}
	
	/**
	 * 获取道具数量
	 * @param itemId
	 * @return
	 */
	public int getItemNum(int itemId) {
		int num = 0;
		for (Item item : getItemTable().values()) {
			if (item.getItemId() == itemId) {
				num += item.getNum();
			}
		}
		return num;
	}

	public Map<Long, Item> getItemTable() {
		return itemTable;
	}

	public void setItemTable(Map<Long, Item> itemTable) {
		this.itemTable = itemTable;
	}

	public int getMax() {
		return max;
	}

	public void setEquipmentMap(Map<Long, Equipment> equipmentMap) {
		this.equipmentMap = equipmentMap;
	}

	@Override
	public JBaseData toJBaseData() {
		JBaseData jBaseData = new JBaseData();
		List<JBaseData> itemBaseDataList = new ArrayList<JBaseData>();
		jBaseData.put("itemTableList", itemBaseDataList);
		for(Item item : itemTable.values()) {
			itemBaseDataList.add(item.toJBaseData());
		}
		
		List<JBaseData> equipBaseDataList = new ArrayList<JBaseData>();
		jBaseData.put("equipmentMap", equipBaseDataList);
		for(Equipment equipment : equipmentMap.values()) {
			equipBaseDataList.add(equipment.toJBaseData());
		}
		
		return jBaseData;
	}

	@Override
	public void fromJBaseData(JBaseData jBaseData) {
		List<JBaseData> itemTableList = jBaseData.getSeqBaseData("itemTableList");
		for(JBaseData itemData : itemTableList) {
			Item item = new Item();
			item.fromJBaseData(itemData);
			this.itemTable.put(item.getId(), item);
		}
		
		
		List<JBaseData> equipmentMap = jBaseData.getSeqBaseData("equipmentMap");
		for(JBaseData equipData : equipmentMap) {
			Equipment equipment = new Equipment();
			equipment.fromJBaseData(equipData);
			this.equipmentMap.put(equipment.getEquipmentSequenceId(), equipment);
		}
		
	}
	
}
