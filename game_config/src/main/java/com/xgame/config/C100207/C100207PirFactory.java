package com.xgame.config.C100207;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-22 21:10:45 
 */
public class C100207PirFactory extends BasePriFactory<C100207Pir>{
	

	public void init(C100207Pir pir) {
		
	}
	
	@Override
	public void load(C100207Pir pir) {
		
	}
	
	
	
	
	
	
	
	@Override
	public C100207Pir newPri() {
		return new C100207Pir();
	}
	
	public static C100207Pir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final C100207PirFactory instance = new C100207PirFactory(); 
	
	
	public static C100207PirFactory getInstance() {
		return instance;
	}
}