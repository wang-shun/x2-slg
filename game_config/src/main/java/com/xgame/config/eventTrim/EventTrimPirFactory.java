package com.xgame.config.eventTrim;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-04-05 19:50:36 
 */
public class EventTrimPirFactory extends BasePriFactory<EventTrimPir>{
	

	public void init(EventTrimPir pir) {
		
	}
	
	@Override
	public void load(EventTrimPir pir) {
		
	}
	
	
	
	
	
	
	
	
	
	
	@Override
	public EventTrimPir newPri() {
		return new EventTrimPir();
	}
	
	public static EventTrimPir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final EventTrimPirFactory instance = new EventTrimPirFactory(); 
	
	
	public static EventTrimPirFactory getInstance() {
		return instance;
	}
}