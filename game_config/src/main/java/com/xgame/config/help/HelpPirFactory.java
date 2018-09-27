package com.xgame.config.help;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-04 09:52:38 
 */
public class HelpPirFactory extends BasePriFactory<HelpPir>{
	

	public void init(HelpPir pir) {
		
	}
	
	@Override
	public void load(HelpPir pir) {
		
	}
	
	
	
	
	
	
	
	
	@Override
	public HelpPir newPri() {
		return new HelpPir();
	}
	
	public static HelpPir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final HelpPirFactory instance = new HelpPirFactory(); 
	
	
	public static HelpPirFactory getInstance() {
		return instance;
	}
}