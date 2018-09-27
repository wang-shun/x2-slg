package com.xgame.config.C101101;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-22 21:10:45 
 */
public class C101101PirFactory extends BasePriFactory<C101101Pir>{
	

	public void init(C101101Pir pir) {
		
	}
	
	@Override
	public void load(C101101Pir pir) {
		
	}
	
	
	
	
	
	
	
	@Override
	public C101101Pir newPri() {
		return new C101101Pir();
	}
	
	public static C101101Pir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final C101101PirFactory instance = new C101101PirFactory(); 
	
	
	public static C101101PirFactory getInstance() {
		return instance;
	}
}