package com.xgame.config.limitActivity;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-04 09:52:38 
 */
public class LimitActivityPirFactory extends BasePriFactory<LimitActivityPir>{
	

	public void init(LimitActivityPir pir) {
		
	}
	
	@Override
	public void load(LimitActivityPir pir) {
		
	}
	
	
	
	
	/**
	 *自定义解析  table
	 */
	@ConfParse("table")
	public void tablePares(String conf,LimitActivityPir pir){
		
	}
	
	
	
	
	
	
	
	@Override
	public LimitActivityPir newPri() {
		return new LimitActivityPir();
	}
	
	public static LimitActivityPir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final LimitActivityPirFactory instance = new LimitActivityPirFactory(); 
	
	
	public static LimitActivityPirFactory getInstance() {
		return instance;
	}
}