package com.xgame.config.C100203;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-22 21:10:45 
 */
public class C100203PirFactory extends BasePriFactory<C100203Pir>{
	

	public void init(C100203Pir pir) {
		
	}
	
	@Override
	public void load(C100203Pir pir) {
		
	}
	
	
	
	
	
	
	
	@Override
	public C100203Pir newPri() {
		return new C100203Pir();
	}
	
	public static C100203Pir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final C100203PirFactory instance = new C100203PirFactory(); 
	
	
	public static C100203PirFactory getInstance() {
		return instance;
	}
}