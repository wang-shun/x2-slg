package com.xgame.config.C100105;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-22 21:10:45 
 */
public class C100105PirFactory extends BasePriFactory<C100105Pir>{
	

	public void init(C100105Pir pir) {
		
	}
	
	@Override
	public void load(C100105Pir pir) {
		
	}
	
	
	
	
	
	
	
	@Override
	public C100105Pir newPri() {
		return new C100105Pir();
	}
	
	public static C100105Pir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final C100105PirFactory instance = new C100105PirFactory(); 
	
	
	public static C100105PirFactory getInstance() {
		return instance;
	}
}