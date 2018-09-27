package com.xgame.config.activityCenter;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-04-11 14:57:01 
 */
public class ActivityCenterPirFactory extends BasePriFactory<ActivityCenterPir>{
	

	public void init(ActivityCenterPir pir) {
		
	}
	
	@Override
	public void load(ActivityCenterPir pir) {
		
	}
	
	
	
	
	
	
	/**
	 *自定义解析  time
	 */
	@ConfParse("time")
	public void timePares(String conf,ActivityCenterPir pir){
		
	}
	
	/**
	 *自定义解析  picture
	 */
	@ConfParse("picture")
	public void picturePares(String conf,ActivityCenterPir pir){
		
	}
	
	
	@Override
	public ActivityCenterPir newPri() {
		return new ActivityCenterPir();
	}
	
	public static ActivityCenterPir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final ActivityCenterPirFactory instance = new ActivityCenterPirFactory(); 
	
	
	public static ActivityCenterPirFactory getInstance() {
		return instance;
	}
}