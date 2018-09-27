package com.xgame.config.CXlanguage_ZhCN;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-04 09:52:37 
 */
public class CXlanguage_ZhCNPirFactory extends BasePriFactory<CXlanguage_ZhCNPir>{
	

	public void init(CXlanguage_ZhCNPir pir) {
		
	}
	
	@Override
	public void load(CXlanguage_ZhCNPir pir) {
		
	}
	
	
	
	/**
	 *自定义解析  name
	 */
	@ConfParse("name")
	public void namePares(String conf,CXlanguage_ZhCNPir pir){
		
	}
	
	/**
	 *自定义解析  desc
	 */
	@ConfParse("desc")
	public void descPares(String conf,CXlanguage_ZhCNPir pir){
		
	}
	
	@Override
	public CXlanguage_ZhCNPir newPri() {
		return new CXlanguage_ZhCNPir();
	}
	
	public static CXlanguage_ZhCNPir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final CXlanguage_ZhCNPirFactory instance = new CXlanguage_ZhCNPirFactory(); 
	
	
	public static CXlanguage_ZhCNPirFactory getInstance() {
		return instance;
	}
}