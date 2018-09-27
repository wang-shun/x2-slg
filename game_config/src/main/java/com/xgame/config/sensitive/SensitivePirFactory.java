package com.xgame.config.sensitive;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-08 22:16:16 
 */
public class SensitivePirFactory extends BasePriFactory<SensitivePir>{
	

	public void init(SensitivePir pir) {
		
	}
	
	@Override
	public void load(SensitivePir pir) {
		
	}
	
	
	/**
	 *自定义解析  desc
	 */
	@ConfParse("desc")
	public void descPares(String conf,SensitivePir pir){
		
	}
	
	@Override
	public SensitivePir newPri() {
		return new SensitivePir();
	}
	
	public static SensitivePir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final SensitivePirFactory instance = new SensitivePirFactory(); 
	
	
	public static SensitivePirFactory getInstance() {
		return instance;
	}
}