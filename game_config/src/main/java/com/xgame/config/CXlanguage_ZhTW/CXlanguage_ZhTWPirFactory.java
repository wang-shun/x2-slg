package com.xgame.config.CXlanguage_ZhTW;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-04 09:52:37 
 */
public class CXlanguage_ZhTWPirFactory extends BasePriFactory<CXlanguage_ZhTWPir>{
	

	public void init(CXlanguage_ZhTWPir pir) {
		
	}
	
	@Override
	public void load(CXlanguage_ZhTWPir pir) {
		
	}
	
	
	
	/**
	 *自定义解析  name
	 */
	@ConfParse("name")
	public void namePares(String conf,CXlanguage_ZhTWPir pir){
		
	}
	
	/**
	 *自定义解析  desc
	 */
	@ConfParse("desc")
	public void descPares(String conf,CXlanguage_ZhTWPir pir){
		
	}
	
	@Override
	public CXlanguage_ZhTWPir newPri() {
		return new CXlanguage_ZhTWPir();
	}
	
	public static CXlanguage_ZhTWPir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final CXlanguage_ZhTWPirFactory instance = new CXlanguage_ZhTWPirFactory(); 
	
	
	public static CXlanguage_ZhTWPirFactory getInstance() {
		return instance;
	}
}