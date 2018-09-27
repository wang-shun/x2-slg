package com.xgame.config.C100305;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-22 21:10:45 
 */
public class C100305PirFactory extends BasePriFactory<C100305Pir>{
	

	public void init(C100305Pir pir) {
		
	}
	
	@Override
	public void load(C100305Pir pir) {
		
	}
	
	
	
	
	
	
	
	@Override
	public C100305Pir newPri() {
		return new C100305Pir();
	}
	
	public static C100305Pir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final C100305PirFactory instance = new C100305PirFactory(); 
	
	
	public static C100305PirFactory getInstance() {
		return instance;
	}
}