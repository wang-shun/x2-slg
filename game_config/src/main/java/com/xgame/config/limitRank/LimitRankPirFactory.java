package com.xgame.config.limitRank;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-04 09:52:38 
 */
public class LimitRankPirFactory extends BasePriFactory<LimitRankPir>{
	

	public void init(LimitRankPir pir) {
		
	}
	
	@Override
	public void load(LimitRankPir pir) {
		
	}
	
	
	
	
	
	/**
	 *自定义解析  v1
	 */
	@ConfParse("v1")
	public void v1Pares(String conf,LimitRankPir pir){
		
	}
	
	/**
	 *自定义解析  v2
	 */
	@ConfParse("v2")
	public void v2Pares(String conf,LimitRankPir pir){
		
	}
	
	
	@Override
	public LimitRankPir newPri() {
		return new LimitRankPir();
	}
	
	public static LimitRankPir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final LimitRankPirFactory instance = new LimitRankPirFactory(); 
	
	
	public static LimitRankPirFactory getInstance() {
		return instance;
	}
}