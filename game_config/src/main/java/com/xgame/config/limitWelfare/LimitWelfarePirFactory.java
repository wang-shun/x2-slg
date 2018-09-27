package com.xgame.config.limitWelfare;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-15 10:58:18 
 */
public class LimitWelfarePirFactory extends BasePriFactory<LimitWelfarePir>{
	

	public void init(LimitWelfarePir pir) {
		
	}
	
	@Override
	public void load(LimitWelfarePir pir) {
		
	}
	
	
	
	
	
	
	/**
	 *自定义解析  value
	 */
	@ConfParse("value")
	public void valuePares(String conf,LimitWelfarePir pir){
		
	}
	
	@Override
	public LimitWelfarePir newPri() {
		return new LimitWelfarePir();
	}
	
	public static LimitWelfarePir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final LimitWelfarePirFactory instance = new LimitWelfarePirFactory(); 
	
	
	public static LimitWelfarePirFactory getInstance() {
		return instance;
	}
}