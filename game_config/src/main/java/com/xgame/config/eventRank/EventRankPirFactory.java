package com.xgame.config.eventRank;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-04 09:52:38 
 */
public class EventRankPirFactory extends BasePriFactory<EventRankPir>{
	

	public void init(EventRankPir pir) {
		
	}
	
	@Override
	public void load(EventRankPir pir) {
		
	}
	
	
	
	
	/**
	 *自定义解析  rank
	 */
	@ConfParse("rank")
	public void rankPares(String conf,EventRankPir pir){
		
	}
	
	
	/**
	 *自定义解析  rewards
	 */
	@ConfParse("rewards")
	public void rewardsPares(String conf,EventRankPir pir){
		
	}
	
	@Override
	public EventRankPir newPri() {
		return new EventRankPir();
	}
	
	public static EventRankPir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final EventRankPirFactory instance = new EventRankPirFactory(); 
	
	
	public static EventRankPirFactory getInstance() {
		return instance;
	}
}