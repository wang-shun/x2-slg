package com.xgame.config.C100307;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-22 21:10:45 
 */
public class C100307PirFactory extends BasePriFactory<C100307Pir>{
	

	public void init(C100307Pir pir) {
		
	}
	
	@Override
	public void load(C100307Pir pir) {
		
	}
	
	
	
	
	
	
	
	@Override
	public C100307Pir newPri() {
		return new C100307Pir();
	}
	
	public static C100307Pir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final C100307PirFactory instance = new C100307PirFactory(); 
	
	
	public static C100307PirFactory getInstance() {
		return instance;
	}
}