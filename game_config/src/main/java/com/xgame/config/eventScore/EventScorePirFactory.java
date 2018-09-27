package com.xgame.config.eventScore;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-04-05 19:50:36 
 */
public class EventScorePirFactory extends BasePriFactory<EventScorePir>{
	

	public void init(EventScorePir pir) {
		
	}
	
	@Override
	public void load(EventScorePir pir) {
		
	}
	
	
	
	
	
	
	
	
	
	
	@Override
	public EventScorePir newPri() {
		return new EventScorePir();
	}
	
	public static EventScorePir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final EventScorePirFactory instance = new EventScorePirFactory(); 
	
	
	public static EventScorePirFactory getInstance() {
		return instance;
	}
}