package com.xgame.config.mailLanguage;

import com.xgame.config.BasePriFactory;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2016-12-06 10:25:10 
 */
public class MailLanguagePirFactory extends BasePriFactory<MailLanguagePir>{
	

	public void init(MailLanguagePir pir) {
		
	}
	
	@Override
	public void load(MailLanguagePir pir) {
		
	}
	
	
	
	
	
	
	@Override
	public MailLanguagePir newPri() {
		return new MailLanguagePir();
	}
	
	public static MailLanguagePir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final MailLanguagePirFactory instance = new MailLanguagePirFactory(); 
	
	
	public static MailLanguagePirFactory getInstance() {
		return instance;
	}
}