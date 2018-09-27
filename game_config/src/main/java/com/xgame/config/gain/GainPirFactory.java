package com.xgame.config.gain;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-04 09:52:38 
 */
public class GainPirFactory extends BasePriFactory<GainPir>{
	

	public void init(GainPir pir) {
		
	}
	
	@Override
	public void load(GainPir pir) {
		
	}
	
	
	
	
	
	/**
	 *自定义解析  icon
	 */
	@ConfParse("icon")
	public void iconPares(String conf,GainPir pir){
		
	}
	
	/**
	 *自定义解析  propsLibrary
	 */
	@ConfParse("propsLibrary")
	public void propsLibraryPares(String conf,GainPir pir){
		
	}
	
	@Override
	public GainPir newPri() {
		return new GainPir();
	}
	
	public static GainPir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final GainPirFactory instance = new GainPirFactory(); 
	
	
	public static GainPirFactory getInstance() {
		return instance;
	}
}