package com.xgame.config.rank;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-04 09:52:39 
 */
public class RankPirFactory extends BasePriFactory<RankPir>{
	

	public void init(RankPir pir) {
		
	}
	
	@Override
	public void load(RankPir pir) {
		
	}
	
	
	
	
	
	
	
	@Override
	public RankPir newPri() {
		return new RankPir();
	}
	
	public static RankPir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final RankPirFactory instance = new RankPirFactory(); 
	
	
	public static RankPirFactory getInstance() {
		return instance;
	}
}