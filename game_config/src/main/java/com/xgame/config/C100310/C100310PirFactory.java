package com.xgame.config.C100310;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-22 21:10:45 
 */
public class C100310PirFactory extends BasePriFactory<C100310Pir>{
	

	public void init(C100310Pir pir) {
		
	}
	
	@Override
	public void load(C100310Pir pir) {
		
	}
	
	
	
	
	
	
	
	@Override
	public C100310Pir newPri() {
		return new C100310Pir();
	}
	
	public static C100310Pir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final C100310PirFactory instance = new C100310PirFactory(); 
	
	
	public static C100310PirFactory getInstance() {
		return instance;
	}
}