package com.xgame.config.equipment;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
import com.xgame.config.attribute.AttributeConfMap;
import com.xgame.config.attribute.AttributeParser;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2016-12-06 10:25:09 
 */
public class EquipmentPirFactory extends BasePriFactory<EquipmentPir>{
	

	public void init(EquipmentPir pir) {
		pir.attr_1 = new AttributeConfMap(pir.id);
		pir.consumable_equipment = new HashMap<Integer,Integer>();
		pir.consumable_materials = new HashMap<Integer,Integer>();
	}
	
	@Override
	public void load(EquipmentPir pir) {
	}
	
	/**
	 *自定义解析  icon
	 */
	@ConfParse("icon")
	public void iconPares(String conf,EquipmentPir pir){
	
	}
	
	/**
	 *自定义解析  attr_1
	 */
	@ConfParse("attr_1")
	public void attr_1Pares(String conf,EquipmentPir pir){
		AttributeParser.parse(conf, pir.getAttr_1());
	}
	
	
	/**
	 *自定义解析  consumable_materials
	 */
	@ConfParse("consumable_materials")
	public void consumable_materialsPares(String conf,EquipmentPir pir){
		if (conf.equals("") || conf.equals(null)) {
			return;
		}
		String[] array = conf.split(";");
		if(array != null && array.length > 0) {
			Map<Integer, Integer> materialsMap = pir.getConsumable_materials();
			for(String str : array) {
				materialsMap.put(Integer.valueOf(str.split("_")[0]), Integer.valueOf(str.split("_")[1]));
			}
		}
	}
	
	/**
	 *自定义解析  consumable_equipment
	 */
	@ConfParse("consumable_equipment")
	public void consumable_equipmentPares(String conf,EquipmentPir pir){
		if (conf.equals("") || conf.equals(null)) {
			return;
		}
		String[] array = conf.split(";");
		if(array != null && array.length > 0) {
			Map<Integer, Integer> map = pir.getConsumable_equipment();
			map.put(Integer.valueOf(array[0]), Integer.valueOf(array[1]));
		}
	}
	
	
	/**
	 * 通过合成线获取装备配置
	 * @param synthes
	 */
	public Map<Integer, EquipmentPir> getEquipmentConfigBySynthes(int synthes){
		Map<Integer, EquipmentPir> equipmentCondfigs = new HashMap<>();
		Iterator<Entry<Integer, EquipmentPir>> entries = factory.entrySet().iterator();
		while (entries.hasNext()) {
			Map.Entry<java.lang.Integer, EquipmentPir> entry = entries.next();
			if (entry.getValue().getSynthesis() == synthes) {
				equipmentCondfigs.put(entry.getKey(), entry.getValue());
			}
		}
		return equipmentCondfigs;
	}
	
	
	@Override
	public EquipmentPir newPri() {
		return new EquipmentPir();
	}
	
	public static EquipmentPir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final EquipmentPirFactory instance = new EquipmentPirFactory(); 
	
	
	public static EquipmentPirFactory getInstance() {
		return instance;
	}
}