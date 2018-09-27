package com.xgame.config.C100309;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-22 21:10:45 
 */
public class C100309PirFactory extends BasePriFactory<C100309Pir>{
	

	public void init(C100309Pir pir) {
		
	}
	
	@Override
	public void load(C100309Pir pir) {
		
	}
	
	
	
	
	
	
	
	@Override
	public C100309Pir newPri() {
		return new C100309Pir();
	}
	
	public static C100309Pir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final C100309PirFactory instance = new C100309PirFactory(); 
	
	
	public static C100309PirFactory getInstance() {
		return instance;
	}
}