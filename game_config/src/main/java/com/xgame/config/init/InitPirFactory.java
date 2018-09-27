package com.xgame.config.init;

import java.util.HashMap;
import java.util.Map;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
import com.xgame.config.util.ConfigUtil;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2016-12-13 12:16:13 
 */
public class InitPirFactory extends BasePriFactory<InitPir>{
	
	public static final int ID = 1;
	
	private Map<Integer, Integer> initDiamondCollectionSpeed = new HashMap<>();
	
	private Map<Integer, Integer> initMoneyCollectionSpeed = new HashMap<>();
	
	private Map<Integer, Integer> initRareCollectionSpeed = new HashMap<>();
	
	private Map<Integer, Integer> initSteelCollectionSpeed = new HashMap<>();
	
	private Map<Integer, Integer> initOilCollectionSpeed = new HashMap<>();
	

	public void init(InitPir pir) {
	}
	
	@Override
	public void load(InitPir pir) {
		
	}
	
	/**
	 *自定义解析  value_9
	 */
	@ConfParse("value_9")
	public void value_9Pares(String conf,InitPir pir){
		ConfigUtil.parseArrGreaterZero(conf, initDiamondCollectionSpeed);
	}
	
	/**
	 *自定义解析  value_10
	 */
	@ConfParse("value_10")
	public void value_10Pares(String conf,InitPir pir){
		ConfigUtil.parseArrGreaterZero(conf, initMoneyCollectionSpeed);
	}
	
	/**
	 *自定义解析  value_11
	 */
	@ConfParse("value_11")
	public void value_11Pares(String conf,InitPir pir){
		ConfigUtil.parseArrGreaterZero(conf, initRareCollectionSpeed);
	}
	
	/**
	 *自定义解析  value_12
	 */
	@ConfParse("value_12")
	public void value_12Pares(String conf,InitPir pir){
		ConfigUtil.parseArrGreaterZero(conf, initSteelCollectionSpeed);
	}
	
	/**
	 *自定义解析  value_13
	 */
	@ConfParse("value_13")
	public void value_13Pares(String conf,InitPir pir){
		ConfigUtil.parseArrGreaterZero(conf, initOilCollectionSpeed);
	}
	
	@Override
	public InitPir newPri() {
		return new InitPir();
	}
	
	public static InitPir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final InitPirFactory instance = new InitPirFactory(); 
	
	
	public static InitPirFactory getInstance() {
		return instance;
	}
}