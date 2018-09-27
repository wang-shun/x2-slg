package com.xgame.config.C100201;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-22 21:10:45 
 */
public class C100201PirFactory extends BasePriFactory<C100201Pir>{
	

	public void init(C100201Pir pir) {
		
	}
	
	@Override
	public void load(C100201Pir pir) {
		
	}
	
	
	
	
	
	
	
	@Override
	public C100201Pir newPri() {
		return new C100201Pir();
	}
	
	public static C100201Pir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final C100201PirFactory instance = new C100201PirFactory(); 
	
	
	public static C100201PirFactory getInstance() {
		return instance;
	}
}