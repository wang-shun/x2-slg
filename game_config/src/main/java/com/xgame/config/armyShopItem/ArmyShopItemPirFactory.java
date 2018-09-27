package com.xgame.config.armyShopItem;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-04 09:52:33 
 */
public class ArmyShopItemPirFactory extends BasePriFactory<ArmyShopItemPir>{
	

	public void init(ArmyShopItemPir pir) {
		
	}
	
	@Override
	public void load(ArmyShopItemPir pir) {
		
	}
	
	
	
	/**
	 *自定义解析  item_id
	 */
	@ConfParse("item_id")
	public void item_idPares(String conf,ArmyShopItemPir pir){
		
	}
	
	
	
	
	
	@Override
	public ArmyShopItemPir newPri() {
		return new ArmyShopItemPir();
	}
	
	public static ArmyShopItemPir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final ArmyShopItemPirFactory instance = new ArmyShopItemPirFactory(); 
	
	
	public static ArmyShopItemPirFactory getInstance() {
		return instance;
	}
}