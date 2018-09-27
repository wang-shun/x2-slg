package com.xgame.config.C100308;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-22 21:10:45 
 */
public class C100308PirFactory extends BasePriFactory<C100308Pir>{
	

	public void init(C100308Pir pir) {
		
	}
	
	@Override
	public void load(C100308Pir pir) {
		
	}
	
	
	
	
	
	
	
	@Override
	public C100308Pir newPri() {
		return new C100308Pir();
	}
	
	public static C100308Pir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final C100308PirFactory instance = new C100308PirFactory(); 
	
	
	public static C100308PirFactory getInstance() {
		return instance;
	}
}