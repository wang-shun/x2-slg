package com.xgame.config.armyDonate;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-02-18 17:01:16 
 */
public class ArmyDonatePirFactory extends BasePriFactory<ArmyDonatePir>{
	

	public void init(ArmyDonatePir pir) {
		
	}
	
	@Override
	public void load(ArmyDonatePir pir) {
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public ArmyDonatePir newPri() {
		return new ArmyDonatePir();
	}
	
	public static ArmyDonatePir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final ArmyDonatePirFactory instance = new ArmyDonatePirFactory(); 
	
	
	public static ArmyDonatePirFactory getInstance() {
		return instance;
	}
}