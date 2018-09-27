package com.xgame.config.signIn;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-04 09:52:40 
 */
public class SignInPirFactory extends BasePriFactory<SignInPir>{
	

	public void init(SignInPir pir) {
		
	}
	
	@Override
	public void load(SignInPir pir) {
		
	}
	
	
	
	
	
	
	/**
	 *自定义解析  rewards1
	 */
	@ConfParse("rewards1")
	public void rewards1Pares(String conf,SignInPir pir){
		
	}
	
	/**
	 *自定义解析  rewards2
	 */
	@ConfParse("rewards2")
	public void rewards2Pares(String conf,SignInPir pir){
		
	}
	
	/**
	 *自定义解析  rewards3
	 */
	@ConfParse("rewards3")
	public void rewards3Pares(String conf,SignInPir pir){
		
	}
	
	/**
	 *自定义解析  rewards4
	 */
	@ConfParse("rewards4")
	public void rewards4Pares(String conf,SignInPir pir){
		
	}
	
	@Override
	public SignInPir newPri() {
		return new SignInPir();
	}
	
	public static SignInPir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final SignInPirFactory instance = new SignInPirFactory(); 
	
	
	public static SignInPirFactory getInstance() {
		return instance;
	}
}