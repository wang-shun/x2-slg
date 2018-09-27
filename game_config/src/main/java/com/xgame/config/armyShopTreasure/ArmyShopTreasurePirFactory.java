package com.xgame.config.armyShopTreasure;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-04 09:52:33 
 */
public class ArmyShopTreasurePirFactory extends BasePriFactory<ArmyShopTreasurePir>{
	

	public void init(ArmyShopTreasurePir pir) {
		
	}
	
	@Override
	public void load(ArmyShopTreasurePir pir) {
		
	}
	
	
	
	/**
	 *自定义解析  item_id
	 */
	@ConfParse("item_id")
	public void item_idPares(String conf,ArmyShopTreasurePir pir){
		
	}
	
	
	
	
	
	
	@Override
	public ArmyShopTreasurePir newPri() {
		return new ArmyShopTreasurePir();
	}
	
	public static ArmyShopTreasurePir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final ArmyShopTreasurePirFactory instance = new ArmyShopTreasurePirFactory(); 
	
	
	public static ArmyShopTreasurePirFactory getInstance() {
		return instance;
	}
}