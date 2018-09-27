package com.xgame.config.armyWarArmsRange;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-04 10:30:31 
 */
public class ArmyWarArmsRangePirFactory extends BasePriFactory<ArmyWarArmsRangePir>{
	

	public void init(ArmyWarArmsRangePir pir) {
		
	}
	
	@Override
	public void load(ArmyWarArmsRangePir pir) {
		
	}
	
	
	
	
	
	@Override
	public ArmyWarArmsRangePir newPri() {
		return new ArmyWarArmsRangePir();
	}
	
	public static ArmyWarArmsRangePir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final ArmyWarArmsRangePirFactory instance = new ArmyWarArmsRangePirFactory(); 
	
	
	public static ArmyWarArmsRangePirFactory getInstance() {
		return instance;
	}
}