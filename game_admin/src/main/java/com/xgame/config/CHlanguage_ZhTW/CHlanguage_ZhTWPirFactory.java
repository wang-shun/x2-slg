package com.xgame.config.CHlanguage_ZhTW;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-04 09:52:35 
 */
public class CHlanguage_ZhTWPirFactory extends BasePriFactory<CHlanguage_ZhTWPir>{
	

	public void init(CHlanguage_ZhTWPir pir) {
		
	}
	
	@Override
	public void load(CHlanguage_ZhTWPir pir) {
		
	}
	
	
	
	/**
	 *自定义解析  ZhCN
	 */
	@ConfParse("ZhCN")
	public void ZhCNPares(String conf,CHlanguage_ZhTWPir pir){
		
	}
	
	@Override
	public CHlanguage_ZhTWPir newPri() {
		return new CHlanguage_ZhTWPir();
	}
	
	public static CHlanguage_ZhTWPir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final CHlanguage_ZhTWPirFactory instance = new CHlanguage_ZhTWPirFactory(); 
	
	
	public static CHlanguage_ZhTWPirFactory getInstance() {
		return instance;
	}
}