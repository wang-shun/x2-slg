package com.xgame.config.C100304;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-22 21:10:45 
 */
public class C100304PirFactory extends BasePriFactory<C100304Pir>{
	

	public void init(C100304Pir pir) {
		
	}
	
	@Override
	public void load(C100304Pir pir) {
		
	}
	
	
	
	
	
	
	
	@Override
	public C100304Pir newPri() {
		return new C100304Pir();
	}
	
	public static C100304Pir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final C100304PirFactory instance = new C100304PirFactory(); 
	
	
	public static C100304PirFactory getInstance() {
		return instance;
	}
}