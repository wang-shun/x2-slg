package com.xgame.config.touXiang;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-05-19 15:18:05 
 */
public class TouXiangPirFactory extends BasePriFactory<TouXiangPir>{
	

	public void init(TouXiangPir pir) {
		
	}
	
	@Override
	public void load(TouXiangPir pir) {
		
	}
	
	
	
	/**
	 *自定义解析  icon
	 */
	@ConfParse("icon")
	public void iconPares(String conf,TouXiangPir pir){
		
	}
	
	/**
	 *自定义解析  TID
	 */
	@ConfParse("TID")
	public void TIDPares(String conf,TouXiangPir pir){
		
	}
	
	
	
	
	
	@Override
	public TouXiangPir newPri() {
		return new TouXiangPir();
	}
	
	public static TouXiangPir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final TouXiangPirFactory instance = new TouXiangPirFactory(); 
	
	
	public static TouXiangPirFactory getInstance() {
		return instance;
	}
}