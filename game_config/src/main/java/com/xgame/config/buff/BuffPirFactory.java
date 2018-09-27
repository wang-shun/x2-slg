package com.xgame.config.buff;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-04 09:52:33 
 */
public class BuffPirFactory extends BasePriFactory<BuffPir>{
	

	public void init(BuffPir pir) {
		
	}
	
	@Override
	public void load(BuffPir pir) {
		
	}
	
	
	
	
	
	
	/**
	 *自定义解析  attrValue
	 */
	@ConfParse("attrValue")
	public void attrValuePares(String conf,BuffPir pir){
		
	}
	
	
	/**
	 *自定义解析  happenValue
	 */
	@ConfParse("happenValue")
	public void happenValuePares(String conf,BuffPir pir){
		
	}
	
	
	
	
	
	
	/**
	 *自定义解析  buffPicture
	 */
	@ConfParse("buffPicture")
	public void buffPicturePares(String conf,BuffPir pir){
		
	}
	
	/**
	 *自定义解析  effectId
	 */
	@ConfParse("effectId")
	public void effectIdPares(String conf,BuffPir pir){
		
	}
	
	
	@Override
	public BuffPir newPri() {
		return new BuffPir();
	}
	
	public static BuffPir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final BuffPirFactory instance = new BuffPirFactory(); 
	
	
	public static BuffPirFactory getInstance() {
		return instance;
	}
}