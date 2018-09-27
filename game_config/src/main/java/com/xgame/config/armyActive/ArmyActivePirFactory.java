package com.xgame.config.armyActive;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-02-18 17:01:16 
 */
public class ArmyActivePirFactory extends BasePriFactory<ArmyActivePir>{
	

	public void init(ArmyActivePir pir) {
		
	}
	
	@Override
	public void load(ArmyActivePir pir) {
		
	}
	
	
	
	
	
	
	
	@Override
	public ArmyActivePir newPri() {
		return new ArmyActivePir();
	}
	
	public static ArmyActivePir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final ArmyActivePirFactory instance = new ArmyActivePirFactory(); 
	
	
	public static ArmyActivePirFactory getInstance() {
		return instance;
	}
}