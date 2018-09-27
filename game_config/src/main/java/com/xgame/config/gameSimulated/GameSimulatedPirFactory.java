package com.xgame.config.gameSimulated;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-04 09:52:38 
 */
public class GameSimulatedPirFactory extends BasePriFactory<GameSimulatedPir>{
	

	public void init(GameSimulatedPir pir) {
		
	}
	
	@Override
	public void load(GameSimulatedPir pir) {
		
	}
	
	
	
	
	
	/**
	 *自定义解析  rewards1
	 */
	@ConfParse("rewards1")
	public void rewards1Pares(String conf,GameSimulatedPir pir){
		
	}
	
	/**
	 *自定义解析  rewards2
	 */
	@ConfParse("rewards2")
	public void rewards2Pares(String conf,GameSimulatedPir pir){
		
	}
	
	/**
	 *自定义解析  rewards3
	 */
	@ConfParse("rewards3")
	public void rewards3Pares(String conf,GameSimulatedPir pir){
		
	}
	
	/**
	 *自定义解析  rewards4
	 */
	@ConfParse("rewards4")
	public void rewards4Pares(String conf,GameSimulatedPir pir){
		
	}
	
	
	
	
	
	
	
	
	
	@Override
	public GameSimulatedPir newPri() {
		return new GameSimulatedPir();
	}
	
	public static GameSimulatedPir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final GameSimulatedPirFactory instance = new GameSimulatedPirFactory(); 
	
	
	public static GameSimulatedPirFactory getInstance() {
		return instance;
	}
}