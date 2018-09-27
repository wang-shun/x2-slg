package com.xgame.config.event;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-04 09:52:38 
 */
public class EventPirFactory extends BasePriFactory<EventPir>{
	

	public void init(EventPir pir) {
		
	}
	
	@Override
	public void load(EventPir pir) {
		
	}
	
	
	
	
	
	
	/**
	 *自定义解析  limit
	 */
	@ConfParse("limit")
	public void limitPares(String conf,EventPir pir){
		
	}
	
	
	
	
	/**
	 *自定义解析  rewards1
	 */
	@ConfParse("rewards1")
	public void rewards1Pares(String conf,EventPir pir){
		
	}
	
	/**
	 *自定义解析  rewards2
	 */
	@ConfParse("rewards2")
	public void rewards2Pares(String conf,EventPir pir){
		
	}
	
	/**
	 *自定义解析  rewards3
	 */
	@ConfParse("rewards3")
	public void rewards3Pares(String conf,EventPir pir){
		
	}
	
	@Override
	public EventPir newPri() {
		return new EventPir();
	}
	
	public static EventPir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final EventPirFactory instance = new EventPirFactory(); 
	
	
	public static EventPirFactory getInstance() {
		return instance;
	}
}