package com.xgame.config.C100303;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-22 21:10:45 
 */
public class C100303PirFactory extends BasePriFactory<C100303Pir>{
	

	public void init(C100303Pir pir) {
		
	}
	
	@Override
	public void load(C100303Pir pir) {
		
	}
	
	
	
	
	
	
	
	@Override
	public C100303Pir newPri() {
		return new C100303Pir();
	}
	
	public static C100303Pir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final C100303PirFactory instance = new C100303PirFactory(); 
	
	
	public static C100303PirFactory getInstance() {
		return instance;
	}
}