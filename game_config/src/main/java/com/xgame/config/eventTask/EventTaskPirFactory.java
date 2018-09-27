package com.xgame.config.eventTask;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-04 09:52:38 
 */
public class EventTaskPirFactory extends BasePriFactory<EventTaskPir>{
	

	public void init(EventTaskPir pir) {
		
	}
	
	@Override
	public void load(EventTaskPir pir) {
		
	}
	
	
	
	
	
	/**
	 *自定义解析  v1
	 */
	@ConfParse("v1")
	public void v1Pares(String conf,EventTaskPir pir){
		
	}
	
	/**
	 *自定义解析  v2
	 */
	@ConfParse("v2")
	public void v2Pares(String conf,EventTaskPir pir){
		
	}
	
	
	
	@Override
	public EventTaskPir newPri() {
		return new EventTaskPir();
	}
	
	public static EventTaskPir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final EventTaskPirFactory instance = new EventTaskPirFactory(); 
	
	
	public static EventTaskPirFactory getInstance() {
		return instance;
	}
}