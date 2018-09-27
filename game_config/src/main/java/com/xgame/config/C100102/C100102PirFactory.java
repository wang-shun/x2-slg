package com.xgame.config.C100102;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-22 21:10:45 
 */
public class C100102PirFactory extends BasePriFactory<C100102Pir>{
	

	public void init(C100102Pir pir) {
		
	}
	
	@Override
	public void load(C100102Pir pir) {
		
	}
	
	
	
	
	
	
	
	@Override
	public C100102Pir newPri() {
		return new C100102Pir();
	}
	
	public static C100102Pir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final C100102PirFactory instance = new C100102PirFactory(); 
	
	
	public static C100102PirFactory getInstance() {
		return instance;
	}
}