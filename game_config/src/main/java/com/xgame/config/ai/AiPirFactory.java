package com.xgame.config.ai;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-04 09:52:33 
 */
public class AiPirFactory extends BasePriFactory<AiPir>{
	

	public void init(AiPir pir) {
		
	}
	
	@Override
	public void load(AiPir pir) {
		
	}
	
	
	
	
	
	
	
	
	/**
	 *自定义解析  attackTroops
	 */
	@ConfParse("attackTroops")
	public void attackTroopsPares(String conf,AiPir pir){
		
	}
	
	/**
	 *自定义解析  attackBuildings
	 */
	@ConfParse("attackBuildings")
	public void attackBuildingsPares(String conf,AiPir pir){
		
	}
	
	/**
	 *自定义解析  priorityTroops
	 */
	@ConfParse("priorityTroops")
	public void priorityTroopsPares(String conf,AiPir pir){
		
	}
	
	/**
	 *自定义解析  priorityBuildings
	 */
	@ConfParse("priorityBuildings")
	public void priorityBuildingsPares(String conf,AiPir pir){
		
	}
	
	@Override
	public AiPir newPri() {
		return new AiPir();
	}
	
	public static AiPir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final AiPirFactory instance = new AiPirFactory(); 
	
	
	public static AiPirFactory getInstance() {
		return instance;
	}
}