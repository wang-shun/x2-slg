package com.xgame.config.activityRewards;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-04 09:52:33 
 */
public class ActivityRewardsPirFactory extends BasePriFactory<ActivityRewardsPir>{
	

	public void init(ActivityRewardsPir pir) {
		
	}
	
	@Override
	public void load(ActivityRewardsPir pir) {
		
	}
	
	
	
	
	
	
	/**
	 *自定义解析  rewards
	 */
	@ConfParse("rewards")
	public void rewardsPares(String conf,ActivityRewardsPir pir){
		
	}
	
	@Override
	public ActivityRewardsPir newPri() {
		return new ActivityRewardsPir();
	}
	
	public static ActivityRewardsPir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final ActivityRewardsPirFactory instance = new ActivityRewardsPirFactory(); 
	
	
	public static ActivityRewardsPirFactory getInstance() {
		return instance;
	}
}