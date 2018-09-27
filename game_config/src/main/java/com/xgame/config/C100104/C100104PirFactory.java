package com.xgame.config.C100104;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-22 21:10:45 
 */
public class C100104PirFactory extends BasePriFactory<C100104Pir>{
	

	public void init(C100104Pir pir) {
		
	}
	
	@Override
	public void load(C100104Pir pir) {
		
	}
	
	
	
	
	
	
	
	@Override
	public C100104Pir newPri() {
		return new C100104Pir();
	}
	
	public static C100104Pir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final C100104PirFactory instance = new C100104PirFactory(); 
	
	
	public static C100104PirFactory getInstance() {
		return instance;
	}
}