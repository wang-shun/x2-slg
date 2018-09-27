package com.xgame.config.online;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-04 09:52:38 
 */
public class OnlinePirFactory extends BasePriFactory<OnlinePir>{
	

	public void init(OnlinePir pir) {
		
	}
	
	@Override
	public void load(OnlinePir pir) {
		
	}
	
	
	
	/**
	 *自定义解析  limit
	 */
	@ConfParse("limit")
	public void limitPares(String conf,OnlinePir pir){
		
	}
	
	/**
	 *自定义解析  rewards
	 */
	@ConfParse("rewards")
	public void rewardsPares(String conf,OnlinePir pir){
		
	}
	
	
	@Override
	public OnlinePir newPri() {
		return new OnlinePir();
	}
	
	public static OnlinePir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final OnlinePirFactory instance = new OnlinePirFactory(); 
	
	
	public static OnlinePirFactory getInstance() {
		return instance;
	}
}