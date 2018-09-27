package com.xgame.config.C100202;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-22 21:10:45 
 */
public class C100202PirFactory extends BasePriFactory<C100202Pir>{
	

	public void init(C100202Pir pir) {
		
	}
	
	@Override
	public void load(C100202Pir pir) {
		
	}
	
	
	
	
	
	
	
	@Override
	public C100202Pir newPri() {
		return new C100202Pir();
	}
	
	public static C100202Pir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final C100202PirFactory instance = new C100202PirFactory(); 
	
	
	public static C100202PirFactory getInstance() {
		return instance;
	}
}