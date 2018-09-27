package com.xgame.config.army;

import com.xgame.config.BasePriFactory;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2016-12-09 19:22:33 
 */
public class ArmyPirFactory extends BasePriFactory<ArmyPir>{
	

	public void init(ArmyPir pir) {
		
	}
	
	@Override
	public void load(ArmyPir pir) {
		
	}
	
	
	@Override
	public ArmyPir newPri() {
		return new ArmyPir();
	}
	
	public static ArmyPir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final ArmyPirFactory instance = new ArmyPirFactory(); 
	
	
	public static ArmyPirFactory getInstance() {
		return instance;
	}
}