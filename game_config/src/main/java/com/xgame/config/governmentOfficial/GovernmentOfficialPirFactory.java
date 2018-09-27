package com.xgame.config.governmentOfficial;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-04 09:52:38 
 */
public class GovernmentOfficialPirFactory extends BasePriFactory<GovernmentOfficialPir>{
	

	public void init(GovernmentOfficialPir pir) {
		
	}
	
	@Override
	public void load(GovernmentOfficialPir pir) {
		
	}
	
	
	
	
	/**
	 *自定义解析  icon
	 */
	@ConfParse("icon")
	public void iconPares(String conf,GovernmentOfficialPir pir){
		
	}
	
	
	
	/**
	 *自定义解析  attr
	 */
	@ConfParse("attr")
	public void attrPares(String conf,GovernmentOfficialPir pir){
		
	}
	
	@Override
	public GovernmentOfficialPir newPri() {
		return new GovernmentOfficialPir();
	}
	
	public static GovernmentOfficialPir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final GovernmentOfficialPirFactory instance = new GovernmentOfficialPirFactory(); 
	
	
	public static GovernmentOfficialPirFactory getInstance() {
		return instance;
	}
}