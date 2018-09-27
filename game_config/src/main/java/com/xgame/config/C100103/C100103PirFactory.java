package com.xgame.config.C100103;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-22 21:10:45 
 */
public class C100103PirFactory extends BasePriFactory<C100103Pir>{
	

	public void init(C100103Pir pir) {
		
	}
	
	@Override
	public void load(C100103Pir pir) {
		
	}
	
	
	
	
	
	
	
	@Override
	public C100103Pir newPri() {
		return new C100103Pir();
	}
	
	public static C100103Pir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final C100103PirFactory instance = new C100103PirFactory(); 
	
	
	public static C100103PirFactory getInstance() {
		return instance;
	}
}