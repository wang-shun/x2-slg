package com.xgame.config.touXiangBW;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-05-19 15:18:05 
 */
public class TouXiangBWPirFactory extends BasePriFactory<TouXiangBWPir>{
	

	public void init(TouXiangBWPir pir) {
		
	}
	
	@Override
	public void load(TouXiangBWPir pir) {
		
	}
	
	
	
	
	/**
	 *自定义解析  name
	 */
	@ConfParse("name")
	public void namePares(String conf,TouXiangBWPir pir){
		
	}
	
	
	@Override
	public TouXiangBWPir newPri() {
		return new TouXiangBWPir();
	}
	
	public static TouXiangBWPir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final TouXiangBWPirFactory instance = new TouXiangBWPirFactory(); 
	
	
	public static TouXiangBWPirFactory getInstance() {
		return instance;
	}
}