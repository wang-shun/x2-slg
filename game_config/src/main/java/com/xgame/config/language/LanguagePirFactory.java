package com.xgame.config.language;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-04 09:52:38 
 */
public class LanguagePirFactory extends BasePriFactory<LanguagePir>{
	

	public void init(LanguagePir pir) {
		
	}
	
	@Override
	public void load(LanguagePir pir) {
		
	}
	
	
	
	/**
	 *自定义解析  DZYM
	 */
	@ConfParse("DZYM")
	public void DZYMPares(String conf,LanguagePir pir){
		
	}
	
	/**
	 *自定义解析  DQQZ
	 */
	@ConfParse("DQQZ")
	public void DQQZPares(String conf,LanguagePir pir){
		
	}
	
	
	/**
	 *自定义解析  YYBZ
	 */
	@ConfParse("YYBZ")
	public void YYBZPares(String conf,LanguagePir pir){
		
	}
	
	/**
	 *自定义解析  txt1
	 */
	@ConfParse("txt1")
	public void txt1Pares(String conf,LanguagePir pir){
		
	}
	
	/**
	 *自定义解析  txt2
	 */
	@ConfParse("txt2")
	public void txt2Pares(String conf,LanguagePir pir){
		
	}
	
	@Override
	public LanguagePir newPri() {
		return new LanguagePir();
	}
	
	public static LanguagePir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final LanguagePirFactory instance = new LanguagePirFactory(); 
	
	
	public static LanguagePirFactory getInstance() {
		return instance;
	}
}