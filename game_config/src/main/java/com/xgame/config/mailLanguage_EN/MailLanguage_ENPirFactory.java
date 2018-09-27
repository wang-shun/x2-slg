package com.xgame.config.mailLanguage_EN;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-05-19 15:18:03 
 */
public class MailLanguage_ENPirFactory extends BasePriFactory<MailLanguage_ENPir>{
	

	public void init(MailLanguage_ENPir pir) {
		
	}
	
	@Override
	public void load(MailLanguage_ENPir pir) {
		
	}
	
	
	
	/**
	 *自定义解析  title
	 */
	@ConfParse("title")
	public void titlePares(String conf,MailLanguage_ENPir pir){
		
	}
	
	/**
	 *自定义解析  content
	 */
	@ConfParse("content")
	public void contentPares(String conf,MailLanguage_ENPir pir){
		
	}
	
	
	
	/**
	 *自定义解析  icon1
	 */
	@ConfParse("icon1")
	public void icon1Pares(String conf,MailLanguage_ENPir pir){
		
	}
	
	@Override
	public MailLanguage_ENPir newPri() {
		return new MailLanguage_ENPir();
	}
	
	public static MailLanguage_ENPir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final MailLanguage_ENPirFactory instance = new MailLanguage_ENPirFactory(); 
	
	
	public static MailLanguage_ENPirFactory getInstance() {
		return instance;
	}
}