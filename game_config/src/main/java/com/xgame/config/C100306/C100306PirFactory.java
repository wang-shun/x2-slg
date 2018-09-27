package com.xgame.config.C100306;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-22 21:10:45 
 */
public class C100306PirFactory extends BasePriFactory<C100306Pir>{
	

	public void init(C100306Pir pir) {
		
	}
	
	@Override
	public void load(C100306Pir pir) {
		
	}
	
	
	
	
	
	
	
	@Override
	public C100306Pir newPri() {
		return new C100306Pir();
	}
	
	public static C100306Pir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final C100306PirFactory instance = new C100306PirFactory(); 
	
	
	public static C100306PirFactory getInstance() {
		return instance;
	}
}