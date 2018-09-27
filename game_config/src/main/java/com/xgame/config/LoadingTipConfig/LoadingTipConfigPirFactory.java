package com.xgame.config.LoadingTipConfig;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-04 09:52:38 
 */
public class LoadingTipConfigPirFactory extends BasePriFactory<LoadingTipConfigPir>{
	

	public void init(LoadingTipConfigPir pir) {
		
	}
	
	@Override
	public void load(LoadingTipConfigPir pir) {
		
	}
	
	
	
	
	
	@Override
	public LoadingTipConfigPir newPri() {
		return new LoadingTipConfigPir();
	}
	
	public static LoadingTipConfigPir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final LoadingTipConfigPirFactory instance = new LoadingTipConfigPirFactory(); 
	
	
	public static LoadingTipConfigPirFactory getInstance() {
		return instance;
	}
}