package com.xgame.config.CHlanguage_ZhCN;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-04 09:52:34 
 */
public class CHlanguage_ZhCNPirFactory extends BasePriFactory<CHlanguage_ZhCNPir>{
	

	public void init(CHlanguage_ZhCNPir pir) {
		
	}
	
	@Override
	public void load(CHlanguage_ZhCNPir pir) {
		
	}
	
	
	
	/**
	 *自定义解析  ZhCN
	 */
	@ConfParse("ZhCN")
	public void ZhCNPares(String conf,CHlanguage_ZhCNPir pir){
		
	}
	
	@Override
	public CHlanguage_ZhCNPir newPri() {
		return new CHlanguage_ZhCNPir();
	}
	
	public static CHlanguage_ZhCNPir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final CHlanguage_ZhCNPirFactory instance = new CHlanguage_ZhCNPirFactory(); 
	
	
	public static CHlanguage_ZhCNPirFactory getInstance() {
		return instance;
	}
}