package com.xgame.config.RtsType;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-15 10:43:53 
 */
public class RtsTypePirFactory extends BasePriFactory<RtsTypePir>{
	

	public void init(RtsTypePir pir) {
		
	}
	
	@Override
	public void load(RtsTypePir pir) {
		
	}
	
	
	
	
	
	
	
	@Override
	public RtsTypePir newPri() {
		return new RtsTypePir();
	}
	
	public static RtsTypePir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final RtsTypePirFactory instance = new RtsTypePirFactory(); 
	
	
	public static RtsTypePirFactory getInstance() {
		return instance;
	}
}