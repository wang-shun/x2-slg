package com.xgame.config.touXiangMR;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-05-19 15:18:05 
 */
public class TouXiangMRPirFactory extends BasePriFactory<TouXiangMRPir>{
	

	public void init(TouXiangMRPir pir) {
		
	}
	
	@Override
	public void load(TouXiangMRPir pir) {
		
	}
	
	
	
	
	/**
	 *自定义解析  zuHe
	 */
	@ConfParse("zuHe")
	public void zuHePares(String conf,TouXiangMRPir pir){
		
	}
	
	@Override
	public TouXiangMRPir newPri() {
		return new TouXiangMRPir();
	}
	
	public static TouXiangMRPir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final TouXiangMRPirFactory instance = new TouXiangMRPirFactory(); 
	
	
	public static TouXiangMRPirFactory getInstance() {
		return instance;
	}
}