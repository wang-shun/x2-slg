package com.xgame.config.radar;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2016-12-06 10:25:10 
 */
public class RadarPirFactory extends BasePriFactory<RadarPir>{
	

	public void init(RadarPir pir) {
		
	}
	
	@Override
	public void load(RadarPir pir) {
		
	}
	
	
	
	
	
	
	
	/**
	 *自定义解析  garrison_num
	 */
	@ConfParse("garrison_num")
	public void garrison_numPares(String conf,RadarPir pir){
	
	}
	
	
	/**
	 *自定义解析  defense_level
	 */
	@ConfParse("defense_level")
	public void defense_levelPares(String conf,RadarPir pir){
	
	}
	
	
	/**
	 *自定义解析  enemy_num
	 */
	@ConfParse("enemy_num")
	public void enemy_numPares(String conf,RadarPir pir){
	
	}
	
	@Override
	public RadarPir newPri() {
		return new RadarPir();
	}
	
	public static RadarPir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final RadarPirFactory instance = new RadarPirFactory(); 
	
	
	public static RadarPirFactory getInstance() {
		return instance;
	}
}