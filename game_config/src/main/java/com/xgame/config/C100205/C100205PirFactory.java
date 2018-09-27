package com.xgame.config.C100205;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-22 21:10:45 
 */
public class C100205PirFactory extends BasePriFactory<C100205Pir>{
	

	public void init(C100205Pir pir) {
		
	}
	
	@Override
	public void load(C100205Pir pir) {
		
	}
	
	
	
	
	
	
	
	@Override
	public C100205Pir newPri() {
		return new C100205Pir();
	}
	
	public static C100205Pir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final C100205PirFactory instance = new C100205PirFactory(); 
	
	
	public static C100205PirFactory getInstance() {
		return instance;
	}
}