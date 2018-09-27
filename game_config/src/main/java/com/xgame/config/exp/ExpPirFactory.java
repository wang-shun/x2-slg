package com.xgame.config.exp;

import com.xgame.config.BasePriFactory;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2016-12-06 10:25:09 
 */
public class ExpPirFactory extends BasePriFactory<ExpPir>{
	

	public void init(ExpPir pir) {
		
	}
	
	@Override
	public void load(ExpPir pir) {
		
	}
	
	
	
	
	
	
	
	
	@Override
	public ExpPir newPri() {
		return new ExpPir();
	}
	
	public static ExpPir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final ExpPirFactory instance = new ExpPirFactory(); 
	
	
	public static ExpPirFactory getInstance() {
		return instance;
	}
}