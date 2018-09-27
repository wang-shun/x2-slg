package com.xgame.config.mailLanguage_ZhTW;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-05-19 15:18:03 
 */
public class MailLanguage_ZhTWPirFactory extends BasePriFactory<MailLanguage_ZhTWPir>{
	

	public void init(MailLanguage_ZhTWPir pir) {
		
	}
	
	@Override
	public void load(MailLanguage_ZhTWPir pir) {
		
	}
	
	
	
	/**
	 *自定义解析  title
	 */
	@ConfParse("title")
	public void titlePares(String conf,MailLanguage_ZhTWPir pir){
		
	}
	
	/**
	 *自定义解析  content
	 */
	@ConfParse("content")
	public void contentPares(String conf,MailLanguage_ZhTWPir pir){
		
	}
	
	/**
	 *自定义解析  main
	 */
	@ConfParse("main")
	public void mainPares(String conf,MailLanguage_ZhTWPir pir){
		
	}
	
	
	
	/**
	 *自定义解析  icon1
	 */
	@ConfParse("icon1")
	public void icon1Pares(String conf,MailLanguage_ZhTWPir pir){
		
	}
	
	@Override
	public MailLanguage_ZhTWPir newPri() {
		return new MailLanguage_ZhTWPir();
	}
	
	public static MailLanguage_ZhTWPir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final MailLanguage_ZhTWPirFactory instance = new MailLanguage_ZhTWPirFactory(); 
	
	
	public static MailLanguage_ZhTWPirFactory getInstance() {
		return instance;
	}
}