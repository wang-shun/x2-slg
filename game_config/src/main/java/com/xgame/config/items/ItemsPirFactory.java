package com.xgame.config.items;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
import com.xgame.config.attribute.AttributeConfMap;
import com.xgame.config.attribute.AttributeParser;
import com.xgame.config.item.ArmyBoxControl;
import com.xgame.config.item.ItemBox;
import com.xgame.config.item.ItemBoxControl;
import com.xgame.config.item.PeiJianBoxControl;
import com.xgame.data.item.ItemType;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2016-12-06 10:25:09 
 */
@Slf4j
public class ItemsPirFactory extends BasePriFactory<ItemsPir>{
	
	public void init(ItemsPir pir) {
		if(pir.type == ItemType.TREASURE_ITEM.ordinal()){
			if(pir.v1.equals("1")||pir.v1.equals("2")) {
				pir.v2 = new ItemBoxControl();
			} else if(pir.v1.equals("3")) {
				pir.v2 = new ArmyBoxControl();
			} else if(pir.v1.equals("5")) {
				pir.v2 = new PeiJianBoxControl();
			}
		}
		else if(pir.type == ItemType.BUFF_ITEM.ordinal()){
			pir.v1 = new AttributeConfMap(pir.type);
		}
	}
	
	@Override
	public void load(ItemsPir pir) {
		
	}
	
	/**
	 *自定义解析  name
	 */
	@ConfParse("name")
	public void namePares(String conf,ItemsPir pir){
	
	}
	
	/**
	 *自定义解析  desc
	 */
	@ConfParse("desc")
	public void descPares(String conf,ItemsPir pir){
	
	}
	
	
	/**
	 *自定义解析  v1
	 */
	@ConfParse("v1")
	public void v1Pares(String conf,ItemsPir pir){
		if(pir.type == ItemType.BUFF_ITEM.ordinal()){
			AttributeParser.parse(conf, pir.getV1());
		}
	}
	
	/**
	 *自定义解析  v2
	 *V2(宝箱  
	 *例1（固定宝箱）：111906_5,100131_150表示（道具ID_产出数量），
	 *例2（随机宝箱）：111906_1_5,100131_1_15;2表示（道具ID_产出数量区间_产出权重;必出类数量）
	 *例3（随机兵种箱）：1202;4_1_40,3_1_30,2_1_20,1_1_10;10表示（筑;倒数位置_产出权重;必出类数量）
	 *例4（充值活动宝箱）：表示对应armyBox表id，玩家获得此道具自动使用激活联盟对应宝箱，玩家若没加入联盟，该奖励无效)
	 */
	@ConfParse("v2")
	public void v2Pares(String conf,ItemsPir pir){
		if (pir.type == ItemType.TREASURE_ITEM.ordinal()) {
			if(pir.v1.equals("1")) {
				ItemBoxControl boxFixedControl = pir.getV2();
				for(String itemCfg : conf.split(",")) {
					String[] itemStr = itemCfg.split("_");
					ItemBox box = new ItemBox();
					box.settId(Integer.valueOf(itemStr[0]));
					box.setNum(Integer.valueOf(itemStr[1]));
					boxFixedControl.getItemBoxs().add(box);
				}
			
			// 随机宝箱 配置解析好 缓存
			} else if(pir.v1.equals("2")) {
				ItemBoxControl randomBoxControl = pir.getV2();
				String[] config = conf.split(";");
				if(config.length != 2) {
					String error = String.format("随机宝箱配置错误, value:[%s]", conf);	
					log.error(error);
					throw new RuntimeException(error);
				}
				
				String[] itemConfigs = config[0].split(",");
				for (String itemCfg : itemConfigs) {
					ItemBox box = new ItemBox();
					box.settId(Integer.valueOf(itemCfg.split("_")[0]));
					box.setNum(Integer.valueOf(itemCfg.split("_")[1]));
					
					// 权重
					box.setOdds(Integer.valueOf(itemCfg.split("_")[2]));
					// 本次权重边界计算
					randomBoxControl.getItemBoxs().add(box);
				}
				
				// 设置随机次数
				randomBoxControl.setCount(Integer.valueOf(config[1]));
			} else if(pir.v1.equals("3")) {
				ArmyBoxControl armyBoxControl = pir.getV2();
				String[] config = conf.split(";");
				if(config.length != 3) {
					String error = String.format("随机宝箱配置错误, value:[%s]", conf);	
					log.error(error);
					throw new RuntimeException(error);
				}
				
				armyBoxControl.setBuildId(Integer.valueOf(config[0]));
				armyBoxControl.setCount(Integer.valueOf(config[2]));
				String[] itemConfigs = config[1].split(",");
				for (String itemCfg : itemConfigs) {
					ItemBox box = new ItemBox();
					box.settId(Integer.valueOf(itemCfg.split("_")[0]));
					box.setNum(Integer.valueOf(itemCfg.split("_")[1]));
					
					// 权重
					box.setOdds(Integer.valueOf(itemCfg.split("_")[2]));
					// 本次权重边界计算
					armyBoxControl.getDropList().add(box);
				}
			}else if(pir.v1.equals("5")){//配件宝箱
				PeiJianBoxControl peiJianBoxControl = pir.getV2();
				String[] config = conf.split(";");
				if(config.length != 3){
					String error = String.format("配件宝箱配置错误, value:[%s]", conf);	
					log.error(error);
					throw new RuntimeException(error);
				}
				String[] itemConfigs = config[0].split(",");
				peiJianBoxControl.setItemId(Integer.valueOf(itemConfigs[0]));
				peiJianBoxControl.setItemMin(Integer.valueOf(itemConfigs[1].split("_")[0]));
				peiJianBoxControl.setItemMax(Integer.valueOf(itemConfigs[1].split("_")[1]));
				peiJianBoxControl.setLevel(Integer.valueOf(config[1]));
				
				for(String peiJianId : config[2].split(",")){
					if(null != peiJianId){
						peiJianBoxControl.getPeiJianList().add(Integer.valueOf(peiJianId));
					}
				}
			}
		}
	}
	
	
	/**
	 *自定义解析  tips
	 */
	@ConfParse("tips")
	public void tipsPares(String conf,ItemsPir pir){
	
	}
	
	/**
	 *自定义解析  icon
	 */
	@ConfParse("icon")
	public void iconPares(String conf,ItemsPir pir){
	
	}
	
	/**
	 *自定义解析  UI
	 */
	@ConfParse("UI")
	public void UIPares(String conf,ItemsPir pir){
	
	}
	
	@Override
	public ItemsPir newPri() {
		return new ItemsPir();
	}
	
	public static ItemsPir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final ItemsPirFactory instance = new ItemsPirFactory(); 
	
	
	public static ItemsPirFactory getInstance() {
		return instance;
	}
	
	
	
	/**
	 * 通过查找相同品质的相同类型的道具
	 * @param itemType
	 * @param itemQuality
	 * @return
	 */
	public List<ItemsPir> getItemsByTypeAndQuality(int itemType, int itemQuality) {
		List<ItemsPir> itemList = new ArrayList<ItemsPir>();
		
		Iterator<Map.Entry<Integer, ItemsPir>> entriesIterator = ItemsPirFactory.getInstance().getFactory().entrySet().iterator();
		
		// 获取相同品质和类型的道具
		while (entriesIterator.hasNext()) {
			ItemsPir configModel= entriesIterator.next().getValue();
			if(configModel.getType() == itemType &&  configModel.getQuality() == itemQuality)
				itemList.add(configModel);
		}
		return itemList;
	}
	
	/**
	 * 通过查找相同品质的相同类型的道具
	 * @param itemType
	 * @param itemQuality
	 * @return
	 */
	public List<ItemsPir> getItemsBySubTypeAndQuality(int itemType, int subType, int itemQuality) {
		List<ItemsPir> itemList = new ArrayList<ItemsPir>();
		Iterator<Map.Entry<Integer, ItemsPir>> entriesIterator = ItemsPirFactory.getInstance().getFactory().entrySet().iterator();
		
		// 获取相同品质和类型的道具
		while (entriesIterator.hasNext()) {
			ItemsPir configModel= entriesIterator.next().getValue();
			Object v1 = configModel.getV1();
			if(v1 != null && v1.equals(subType+"")  &&  configModel.getQuality() == itemQuality && itemType == configModel.getType())
				itemList.add(configModel);
		}
		return itemList;
	}
	
	/**
	 * 通过itemType，查找类型相同的道具
	 * @param itemType
	 * @return
	 */
	public List<ItemsPir> getItemsByType(int itemType) {
		List<ItemsPir> itemList = new ArrayList<ItemsPir>();
		Iterator<Map.Entry<Integer, ItemsPir>> entriesIterator = ItemsPirFactory.getInstance().getFactory().entrySet().iterator();
		while (entriesIterator.hasNext()) {
			ItemsPir configModel= entriesIterator.next().getValue();
			if(configModel.getType() == itemType)
				itemList.add(configModel);
		}
		return itemList;
	}
	
	/**
	 * 获取锻造材料
	 * @param itemType
	 * @param v1
	 * @return
	 */
	public List<Integer> getComposeFragment(int itemType, int v1) {
		List<Integer> itemsPirs = new ArrayList<Integer>();
		Collection<ItemsPir> collection = this.factory.values();
		if(collection != null && !collection.isEmpty()) {
			for(ItemsPir itemsPir : collection) {
				if(itemsPir.getType() == itemType && Integer.valueOf(itemsPir.getV1()) == v1) {
					itemsPirs.add(itemsPir.getId());
				}
			}
		}
		return itemsPirs;
	}
	
}