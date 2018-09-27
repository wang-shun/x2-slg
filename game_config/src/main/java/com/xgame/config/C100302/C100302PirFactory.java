package com.xgame.config.C100302;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-22 21:10:45 
 */
public class C100302PirFactory extends BasePriFactory<C100302Pir>{
	

	public void init(C100302Pir pir) {
		
	}
	
	@Override
	public void load(C100302Pir pir) {
		
	}
	
	
	
	
	
	
	
	@Override
	public C100302Pir newPri() {
		return new C100302Pir();
	}
	
	public static C100302Pir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final C100302PirFactory instance = new C100302PirFactory(); 
	
	
	public static C100302PirFactory getInstance() {
		return instance;
	}
}