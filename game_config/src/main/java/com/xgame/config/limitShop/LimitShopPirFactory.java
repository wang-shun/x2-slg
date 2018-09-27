package com.xgame.config.limitShop;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-08 22:16:14 
 */
public class LimitShopPirFactory extends BasePriFactory<LimitShopPir>{
	

	public void init(LimitShopPir pir) {
		
	}
	
	@Override
	public void load(LimitShopPir pir) {
		
	}
	
	
	
	
	/**
	 *自定义解析  serverTime
	 */
	@ConfParse("serverTime")
	public void serverTimePares(String conf,LimitShopPir pir){
		
	}
	
	
	/**
	 *自定义解析  item_id
	 */
	@ConfParse("item_id")
	public void item_idPares(String conf,LimitShopPir pir){
		
	}
	
	
	
	
	
	
	
	
	
	/**
	 *自定义解析  special_number
	 */
	@ConfParse("special_number")
	public void special_numberPares(String conf,LimitShopPir pir){
		
	}
	
	/**
	 *自定义解析  special_number2
	 */
	@ConfParse("special_number2")
	public void special_number2Pares(String conf,LimitShopPir pir){
		
	}
	
	
	/**
	 *自定义解析  icon
	 */
	@ConfParse("icon")
	public void iconPares(String conf,LimitShopPir pir){
		
	}
	
	
	@Override
	public LimitShopPir newPri() {
		return new LimitShopPir();
	}
	
	public static LimitShopPir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final LimitShopPirFactory instance = new LimitShopPirFactory(); 
	
	
	public static LimitShopPirFactory getInstance() {
		return instance;
	}
}