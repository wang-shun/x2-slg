package com.xgame.config.recharge;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-04 09:52:39 
 */
public class RechargePirFactory extends BasePriFactory<RechargePir>{
	

	public void init(RechargePir pir) {
		
	}
	
	@Override
	public void load(RechargePir pir) {
		
	}
	
	
	
	
	
	
	
	
	
	
	/**
	 *自定义解析  dailyGems
	 */
	@ConfParse("dailyGems")
	public void dailyGemsPares(String conf,RechargePir pir){
		
	}
	
	/**
	 *自定义解析  show
	 */
	@ConfParse("show")
	public void showPares(String conf,RechargePir pir){
		
	}
	
	/**
	 *自定义解析  background
	 */
	@ConfParse("background")
	public void backgroundPares(String conf,RechargePir pir){
		
	}
	
	/**
	 *自定义解析  rewards
	 */
	@ConfParse("rewards")
	public void rewardsPares(String conf,RechargePir pir){
		
	}
	
	/**
	 *自定义解析  openDays
	 */
	@ConfParse("openDays")
	public void openDaysPares(String conf,RechargePir pir){
		
	}
	
	
	@Override
	public RechargePir newPri() {
		return new RechargePir();
	}
	
	public static RechargePir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final RechargePirFactory instance = new RechargePirFactory(); 
	
	
	public static RechargePirFactory getInstance() {
		return instance;
	}
}