package com.xgame.config.C100206;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-22 21:10:45 
 */
public class C100206PirFactory extends BasePriFactory<C100206Pir>{
	

	public void init(C100206Pir pir) {
		
	}
	
	@Override
	public void load(C100206Pir pir) {
		
	}
	
	
	
	
	
	
	
	@Override
	public C100206Pir newPri() {
		return new C100206Pir();
	}
	
	public static C100206Pir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final C100206PirFactory instance = new C100206PirFactory(); 
	
	
	public static C100206PirFactory getInstance() {
		return instance;
	}
}