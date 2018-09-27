package com.xgame.config.activityRanking;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-04 09:52:33 
 */
public class ActivityRankingPirFactory extends BasePriFactory<ActivityRankingPir>{
	

	public void init(ActivityRankingPir pir) {
		
	}
	
	@Override
	public void load(ActivityRankingPir pir) {
		
	}
	
	
	
	/**
	 *自定义解析  rewards1
	 */
	@ConfParse("rewards1")
	public void rewards1Pares(String conf,ActivityRankingPir pir){
		
	}
	
	/**
	 *自定义解析  rewards2
	 */
	@ConfParse("rewards2")
	public void rewards2Pares(String conf,ActivityRankingPir pir){
		
	}
	
	@Override
	public ActivityRankingPir newPri() {
		return new ActivityRankingPir();
	}
	
	public static ActivityRankingPir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final ActivityRankingPirFactory instance = new ActivityRankingPirFactory(); 
	
	
	public static ActivityRankingPirFactory getInstance() {
		return instance;
	}
}