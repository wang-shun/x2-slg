package com.xgame.config.costDiamond;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-01-16 15:15:44 
 */
public class CostDiamondPirFactory extends BasePriFactory<CostDiamondPir>{
	

	public void init(CostDiamondPir pir) {
		
	}
	
	@Override
	public void load(CostDiamondPir pir) {
		
	}
	
	
	
	
	@Override
	public CostDiamondPir newPri() {
		return new CostDiamondPir();
	}
	
	public static CostDiamondPir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final CostDiamondPirFactory instance = new CostDiamondPirFactory(); 
	
	
	public static CostDiamondPirFactory getInstance() {
		return instance;
	}
}