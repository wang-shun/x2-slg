package com.xgame.config.C100208;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-22 21:10:45 
 */
public class C100208PirFactory extends BasePriFactory<C100208Pir>{
	

	public void init(C100208Pir pir) {
		
	}
	
	@Override
	public void load(C100208Pir pir) {
		
	}
	
	
	
	
	
	
	
	@Override
	public C100208Pir newPri() {
		return new C100208Pir();
	}
	
	public static C100208Pir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final C100208PirFactory instance = new C100208PirFactory(); 
	
	
	public static C100208PirFactory getInstance() {
		return instance;
	}
}